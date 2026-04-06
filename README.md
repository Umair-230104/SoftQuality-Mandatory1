
---

# 🚀 FakeInfo Backend – Setup Guide

This guide shows how to set up the project locally and get it running with PostgreSQL and Docker.

---

## 📥 1. Clone the project

Start by cloning the project to your computer:

```bash
git clone <repo-url>
cd <repo-name>
```

---

## 🐘 2. Create database in pgAdmin

1. Open **pgAdmin**
2. Locate your **Postgres (Docker container)**
3. Right-click on **Databases → Create → Database**
4. Name the database:

```text
fakeinfodb
```

---

## 🧾 3. Insert SQL data

1. Go to the project folder:

```text
SQL-Script/addresses.sql
```

2. Copy the entire content

3. Go back to **pgAdmin**

4. Right-click on `fakeinfodb` → **Query Tool**

5. Paste the script

6. Click **Execute (▶)**

✅ Your addresses and postal codes are now inserted into the database

---

## 🔌 4. Add database in IntelliJ

1. Open the project in IntelliJ
2. Go to the right panel → **Database**
3. Click `+` → **Data Source → PostgreSQL**
4. Fill in:

* Host: `localhost`
* Port: `5432`
* Database: `fakeinfodb`
* User: (your postgres user)
* Password: (your postgres password)

5. Click **Test Connection → OK**

---

## ▶️ 5. Run backend

1. Locate your `Main` class
2. Click **Run**

The server starts at:

```text
http://localhost:7770
```

---

## 🌐 6. Test endpoints

You can now test using a browser or Postman:

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

If everything is set up correctly:

* You receive data from the database
* Addresses are realistic (from Postgres)
* All endpoints work

---

## ❗ Troubleshooting

If something doesn’t work:

* Make sure the database name is `fakeinfodb`
* Ensure the SQL script has been executed
* Verify that the server is running on port `7770`

---
