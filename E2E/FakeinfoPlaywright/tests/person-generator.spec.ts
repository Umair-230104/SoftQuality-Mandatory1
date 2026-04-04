import { test, expect, Locator } from '@playwright/test';

test.describe('partial_generation', () => {
  test('generates_name_gender_and_date_of_birth', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('name-gender-dob');
    await generateButton.click();

    await expect(page.locator('.firstNameValue')).toBeVisible();
    await expect(page.locator('.lastNameValue')).toBeVisible();
    await expect(page.locator('.genderValue')).toBeVisible();
    await expect(page.locator('.dobValue')).toBeVisible();

    await expect(page.locator('.firstNameValue')).not.toHaveText('');
    await expect(page.locator('.lastNameValue')).not.toHaveText('');
    await expect(page.locator('.genderValue')).toHaveText(/male|female/i);
  });

  test('generates_cpr_name_gender_and_date_of_birth', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('cpr-name-gender-dob');
    await generateButton.click();

    const cpr = page.locator('.cprValue');
    const gender = page.locator('.genderValue');

    await expect(cpr).toBeVisible();
    await expect(cpr).toHaveText(/^\d{10}$/);

    await expect(page.locator('.firstNameValue')).toBeVisible();
    await expect(page.locator('.lastNameValue')).toBeVisible();
    await expect(gender).toBeVisible();
    await expect(page.locator('.dobValue')).toBeVisible();

    await expect_cpr_gender_consistency(cpr, gender);
  });

  test('generates_address_with_street_and_town', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('address');
    await generateButton.click();

    const street = page.locator('.streetValue');
    const town = page.locator('.townValue');

    await expect(street).toBeVisible();
    await expect(town).toBeVisible();

    await expect(street).not.toHaveText('');
    await expect(town).not.toHaveText('');
  });

  test('generates_phone_number_with_exactly_8_digits', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('phone');
    await generateButton.click();

    await expect(page.locator('.phoneNumberValue')).toHaveText(/^\d{8}$/);
  });

  test('keeps_cpr_and_gender_consistent_in_partial_person_generation', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();
    await page.locator('#cmbPartialOptions').selectOption('cpr-name-gender-dob');
    await page.getByRole('button', { name: /generate/i }).click();

    const cpr = page.locator('.cprValue');
    const gender = page.locator('.genderValue');

    await expect(cpr).toHaveText(/^\d{10}$/);
    await expect(gender).toHaveText(/male|female/i);

    await expect_cpr_gender_consistency(cpr, gender);
  });

  test('changes_output_when_switching_partial_generation_type', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('phone');
    await generateButton.click();
    await expect(page.locator('.phoneNumberValue')).toHaveText(/^\d{8}$/);

    await partialOptions.selectOption('address');
    await generateButton.click();
    await expect(page.locator('.streetValue')).not.toHaveText('');
    await expect(page.locator('.townValue')).not.toHaveText('');
  });
});

test.describe('person_count_validation_and_boundaries', () => {
  test('rejects_below_minimum_count_of_0', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('0');
    await page.getByRole('button', { name: /generate/i }).click();

    await expect(page.locator('.personCard')).toHaveCount(0);
  });

  test('allows_minimum_valid_count_of_1', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('1');
    await page.getByRole('button', { name: /generate/i }).click();

    const person = page.locator('.personCard').first();
    await expect(person).toBeVisible();
    await expect(page.locator('.personCard')).toHaveCount(1);

    await expect_full_person(person);
  });

  test('allows_maximum_valid_count_of_100', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('100');
    await page.getByRole('button', { name: /generate/i }).click();

    const persons = page.locator('.personCard');
    await expect(persons).toHaveCount(100);

    await expect_full_person(persons.first());
    await expect_full_person(persons.nth(99));
  });

  test('rejects_above_maximum_count_of_101', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('101');
    await page.getByRole('button', { name: /generate/i }).click();

    await expect(page.locator('.personCard')).toHaveCount(0);
  });
});

test.describe('full_person_generation', () => {
  test('generates_one_valid_fake_person', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('1');
    await page.getByRole('button', { name: /generate/i }).click();

    const person = page.locator('.personCard').first();
    await expect(person).toBeVisible();

    await expect_full_person(person);
  });

  test('regenerating_one_person_still_returns_a_valid_person', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('1');
    await page.getByRole('button', { name: /generate/i }).click();

    const firstCpr = ((await page.locator('.cprValue').first().textContent()) ?? '').trim();
    expect(firstCpr).toMatch(/^\d{10}$/);

    await page.getByRole('button', { name: /generate/i }).click();

    const secondCpr = ((await page.locator('.cprValue').first().textContent()) ?? '').trim();
    expect(secondCpr).toMatch(/^\d{10}$/);

    const person = page.locator('.personCard').first();
    await expect(person).toBeVisible();

    await expect_full_person(person);
  });

  test('generates_exactly_3_valid_fake_persons', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('3');
    await page.getByRole('button', { name: /generate/i }).click();

    const persons = page.locator('.personCard');
    await expect(persons).toHaveCount(3);

    for (let i = 0; i < 3; i++) {
      await expect_full_person(persons.nth(i));
    }
  });

  test('keeps_cpr_and_gender_consistent_for_multiple_generated_persons', async ({ page }) => {
    await page.goto('/');

    await page.locator('#txtNumberPersons').fill('5');
    await page.getByRole('button', { name: /generate/i }).click();

    const persons = page.locator('.personCard');
    await expect(persons).toHaveCount(5);

    for (let i = 0; i < 5; i++) {
      const person = persons.nth(i);
      await expect_cpr_gender_consistency(
        person.locator('.cprValue'),
        person.locator('.genderValue')
      );
    }
  });
});



async function expect_cpr_gender_consistency(cprLocator: Locator, genderLocator: Locator) {
  const cprText = (await cprLocator.textContent())?.trim() ?? '';
  const genderText = (await genderLocator.textContent())?.trim().toLowerCase() ?? '';

  expect(cprText).toMatch(/^\d{10}$/);
  expect(genderText).toMatch(/^(male|female)$/);

  const lastDigit = Number(cprText[cprText.length - 1]);

  if (lastDigit % 2 === 0) {
    expect(genderText).toBe('female');
  } else {
    expect(genderText).toBe('male');
  }
}

async function expect_full_person(person: Locator) {
  const cpr = person.locator('.cprValue');
  const firstName = person.locator('.firstNameValue');
  const lastName = person.locator('.lastNameValue');
  const gender = person.locator('.genderValue');
  const dob = person.locator('.dobValue');
  const street = person.locator('.streetValue');
  const town = person.locator('.townValue');
  const phone = person.locator('.phoneNumberValue');

  await expect(cpr).toHaveText(/^\d{10}$/);
  await expect(firstName).not.toHaveText('');
  await expect(lastName).not.toHaveText('');
  await expect(gender).toHaveText(/male|female/i);
  await expect(dob).not.toHaveText('');
  await expect(street).not.toHaveText('');
  await expect(town).not.toHaveText('');
  await expect(phone).toHaveText(/^\d{8}$/);

  await expect_cpr_gender_consistency(cpr, gender);
}