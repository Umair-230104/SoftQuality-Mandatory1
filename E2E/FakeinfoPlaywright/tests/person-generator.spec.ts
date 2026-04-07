import { test, expect, Locator } from '@playwright/test';

test.describe('partial_generation', () => {
  test('generates_name_gender_and_date_of_birth', async ({ page }) => {
    await page.goto('/');

    // Switch to partial generation and choose the requested output type.
    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('name-gender-dob');

    // Generate the partial person data.     -     Name + Gender + DOB

    await generateButton.click();

    await expect(page.locator('.firstNameValue')).toBeVisible();
    await expect(page.locator('.lastNameValue')).toBeVisible();
    await expect(page.locator('.genderValue')).toBeVisible();
    await expect(page.locator('.dobValue')).toBeVisible();

    await expect_meaningful_text(page.locator('.firstNameValue'));
    await expect_meaningful_text(page.locator('.lastNameValue'));
    await expect(page.locator('.genderValue')).toHaveText(/male|female/i);
    await expect_meaningful_text(page.locator('.dobValue'));
  });

  test('generates_cpr_name_gender_and_date_of_birth', async ({ page }) => {
    await page.goto('/');

    // Switch to partial generation and choose the requested output type.
    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('cpr-name-gender-dob');

    // Generate the partial person data.    -    CPR + Name + Gender + DOB

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

    // Switch to partial generation and choose the address option.
    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('address');

    // Generate the address data.
    await generateButton.click();

    const street = page.locator('.streetValue');
    const town = page.locator('.townValue');

    await expect(street).toBeVisible();
    await expect(town).toBeVisible();

    await expect_meaningful_text(street);
    await expect_meaningful_text(town);
  });

  test('generates_phone_number_with_exactly_8_digits', async ({ page }) => {
    await page.goto('/');

    // Switch to partial generation and choose the phone option.
    await page.getByRole('radio', { name: /partial generation/i }).check();

    const partialOptions = page.locator('#cmbPartialOptions');
    const generateButton = page.getByRole('button', { name: /generate/i });

    await partialOptions.selectOption('phone');

    // Generate the phone number.
    await generateButton.click();

    await expect(page.locator('.phoneNumberValue')).toHaveText(/^\d{8}$/);
  });
});

test.describe('full_person_generation', () => {
  test('generates_one_valid_fake_person', async ({ page }) => {
    await page.goto('/');

    // Generate a single full fake person.
    await page.locator('#txtNumberPersons').fill('1');
    await page.getByRole('button', { name: /generate/i }).click();

    const person = page.locator('.personCard').first();
    await expect(person).toBeVisible();

    await expect_full_person(person);
  });

  test('regenerating_one_person_returns_different_values', async ({ page }) => {
    await page.goto('/');

    // Generate the first full fake person.
    await page.locator('#txtNumberPersons').fill('1');
    await page.getByRole('button', { name: /generate/i }).click();

    const firstPerson = await get_person_values(page.locator('.personCard').first());
    await expect_meaningful_text(page.locator('.firstNameValue').first());
    await expect_meaningful_text(page.locator('.lastNameValue').first());

    // Generate a new person and compare it with the previous result.
    await page.getByRole('button', { name: /generate/i }).click();

    const person = page.locator('.personCard').first();
    await expect(person).toBeVisible();
    await expect_full_person(person);

    const secondPerson = await get_person_values(person);
    expect(secondPerson.cpr).not.toBe(firstPerson.cpr);
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
  await expect_meaningful_text(firstName);
  await expect_meaningful_text(lastName);
  await expect(gender).toHaveText(/male|female/i);
  await expect_meaningful_text(dob);
  await expect_meaningful_text(street);
  await expect_meaningful_text(town);
  await expect(phone).toHaveText(/^\d{8}$/);

  await expect_cpr_gender_consistency(cpr, gender);
}

async function expect_meaningful_text(locator: Locator) {
  const text = (await locator.textContent())?.trim() ?? '';
  expect(text.length).toBeGreaterThan(1);
}

async function get_person_values(person: Locator) {
  return {
    cpr: (await person.locator('.cprValue').textContent())?.trim() ?? '',
    firstName: (await person.locator('.firstNameValue').textContent())?.trim() ?? '',
    lastName: (await person.locator('.lastNameValue').textContent())?.trim() ?? '',
    gender: (await person.locator('.genderValue').textContent())?.trim() ?? '',
    dob: (await person.locator('.dobValue').textContent())?.trim() ?? '',
    street: (await person.locator('.streetValue').textContent())?.trim() ?? '',
    town: (await person.locator('.townValue').textContent())?.trim() ?? '',
    phone: (await person.locator('.phoneNumberValue').textContent())?.trim() ?? '',
  };
}