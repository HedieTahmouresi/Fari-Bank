# 🏦 Fari-Bank: NeoBank Simulator

> A comprehensive, multithreaded digital banking platform built from scratch in Java.

---

### 📌 Overview
Developed as a capstone project for an **Advanced Programming** course, Fari-Bank simulates a modern digital banking ecosystem (NeoBank). It goes beyond basic deposit and withdrawal systems by implementing complex, real-world financial operations including multi-tiered user roles, diverse fund types, and concurrent transaction processing.

---

### ✨ Core Features
* **Role-Based Architecture:** Distinct access levels and capabilities for System Admins, Branch Managers, and standard Users.
* **Multithreaded Operations:** Utilizes Java Threads (`TransactionThread`, `BonusThread`) to handle secure authentication and concurrent financial transactions safely.
* **Comprehensive Financial Tools:** * Supports multiple transaction types (Wire, Internal Transfer, SIM Card Charging).
  * Manages standard Credit Cards alongside specialized Savings and Bonus Funds.
  * Allows users to maintain an internal Contact list for rapid peer-to-peer transfers.

---

### 🛠️ Tech Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

---

### 🚀 Quick Start

Ensure you have Java installed, then build and run the project using the included Gradle wrapper:

```bash
# Clone the repository and navigate to the directory
./gradlew build
./gradlew run

```
