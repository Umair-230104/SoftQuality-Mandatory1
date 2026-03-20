Her er en **clean og nem README**, som dine gruppemedlemmer kan følge uden at blive forvirret:

---

# 🚀 FakeInfo Backend – Setup Guide

Denne guide viser, hvordan du sætter projektet op lokalt og får det til at virke med PostgreSQL og Docker.

---

## 📥 1. Clone projektet

Start med at hente projektet ned på din computer:

```bash
git clone <repo-url>
cd <repo-navn>
```

---

## 🐘 2. Opret database i pgAdmin

1. Åbn **pgAdmin**
2. Find din **Postgres (Docker container)**
3. Højreklik på **Databases → Create → Database**
4. Navngiv databasen:

```text
fakeinfodb
```

---

## 🧾 3. Indsæt SQL data

1. Gå til mappen i projektet:

```text
SQL-Script/addresses.sql
```

2. Kopiér hele indholdet

3. Gå tilbage til **pgAdmin**

4. Højreklik på `fakeinfodb` → **Query Tool**

5. Indsæt scriptet

6. Tryk **Execute (▶)**

✅ Nu er dine adresser og postnumre indsat i databasen

---

## 🔌 4. Tilføj database i IntelliJ

1. Åbn projektet i IntelliJ
2. Gå til højre side → **Database**
3. Klik på `+` → **Data Source → PostgreSQL**
4. Udfyld:

* Host: `localhost`
* Port: `5432`
* Database: `fakeinfodb`
* User: (din postgres user)
* Password: (din postgres password)

5. Klik **Test Connection → OK**

---

## ▶️ 5. Kør backend

1. Find din `Main` klasse
2. Klik **Run**

Serveren starter på:

```text
http://localhost:7770
```

---

## 🌐 6. Test endpoints

Du kan nu teste i browser eller Postman:

```text
http://localhost:7770/fakeinfo/cpr
http://localhost:7770/fakeinfo/name-gender
http://localhost:7770/fakeinfo/name-gender-dob
http://localhost:7770/fakeinfo/cpr-name-gender
http://localhost:7770/fakeinfo/cpr-name-gender-dob
http://localhost:7770/fakeinfo/address
http://localhost:7770/fakeinfo/phone
http://localhost:7770/fakeinfo/person
http://localhost:7770/fakeinfo/persons/5
```

---

## ✅ Done!

Hvis alt er sat korrekt op:

* Du får data fra databasen
* Adresser er realistiske (fra Postgres)
* Alle endpoints virker

---

Hvis noget ikke virker:

* Tjek at databasen hedder `fakeinfodb`
* Tjek at SQL-scriptet er kørt
* Tjek at serveren kører på port `7770`

---

Sig til hvis du vil have:

* README med billeder (til GitHub)
* eller en endnu kortere version til aflevering 👍
