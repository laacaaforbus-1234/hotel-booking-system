# 🏨 Hotel Booking System

<div align="center">

### A Modern Java Swing & SQLite Hotel Reservation Application

Book rooms, manage reservations, and experience dynamic seasonal pricing in a desktop-based hotel management system.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge)
![SQLite](https://img.shields.io/badge/Database-SQLite-green?style=for-the-badge)
![JDBC](https://img.shields.io/badge/JDBC-Driver-red?style=for-the-badge)
![OOP](https://img.shields.io/badge/OOP-Fully%20Implemented-purple?style=for-the-badge)

</div>

---

# 👨‍💻 Developers

* **Chala Mosisa**
* **Filmon Haile**
* **Sisay Hulugeta**
* **Nahom Berhanu**
* **Natnael Getachew**

---

Course: **OOP**

Institution: **AAU**

---

## 📖 Overview

The **Hotel Booking System** is a desktop application developed using **Java Swing** and **SQLite**. It allows customers to browse available rooms, create reservations, manage bookings, and experience dynamic seasonal pricing.

The project was designed to demonstrate **Object-Oriented Programming (OOP) principles**, database integration, and GUI development in Java.

---

## ✨ Key Features

### 🔐 User Authentication

* User registration (Sign Up)
* Secure Login system
* Personalized customer profiles

### 🏨 Room Management

* Browse all available rooms
* View room details and pricing
* 27 rooms available:

  * 12 Standard Rooms
  * 15 Suite Rooms

### 🔍 Smart Search

* Filter by room type
* Search by price range
* Quick room discovery

### 💰 Dynamic Seasonal Pricing

* Automatic price adjustments based on season
* Discounts during promotional periods
* Holiday surcharge support

### 📅 Booking System

* Select check-in and check-out dates
* Automatic price calculation
* Detailed booking summary

### 📋 Reservation Management

* View booking history
* Cancel reservations
* Real-time booking updates

### 👤 User Profile

* Automatic booking count updates
* Personal booking records
* Reservation tracking

---

# 🧠 Object-Oriented Programming Concepts

| OOP Principle     | Implementation                                     |
| ----------------- | -------------------------------------------------- |
| **Abstraction**   | `Room` abstract class                              |
| **Inheritance**   | `StandardRoom` and `SuiteRoom` extend `Room`       |
| **Polymorphism**  | `calculatePrice()` method overridden in subclasses |
| **Encapsulation** | Private fields with getters and setters            |
| **Interface**     | `SeasonalPricing` interface                        |

---

# 🛠️ Technology Stack

| Technology | Purpose                   |
| ---------- | ------------------------- |
| Java 17    | Core Programming Language |
| Java Swing | Graphical User Interface  |
| SQLite     | Database Storage          |
| JDBC       | Database Connectivity     |
| VS Code    | Development Environment   |

---

# 📂 Project Structure

```text
hotel-booking-system/
│
├── src/
│   ├── models/
│   │   ├── Room.java
│   │   ├── StandardRoom.java
│   │   ├── SuiteRoom.java
│   │   ├── User.java
│   │   └── Booking.java
│   │
│   ├── services/
│   │   ├── DatabaseService.java
│   │   ├── SeasonalPricing.java
│   │   └── PricingCalculator.java
│   │
│   └── ui/
│       ├── LoginUI.java
│       ├── SignupUI.java
│       └── CustomerDashboard.java
│
├── lib/
│   └── sqlite-jdbc-3.44.1.0.jar
│
├── images/
│   └── background-images
│
└── README.md
```

---

# 🏨 Room Inventory

## Standard Rooms

| Room Numbers | Price Range           |
| ------------ | --------------------- |
| 101 – 112    | $120 – $190 per night |

### Total:

**12 Rooms**

---

## Suite Rooms

| Room Numbers | Price Range           |
| ------------ | --------------------- |
| 201 – 215    | $290 – $600 per night |

### Total:

**15 Rooms**

---

# 📈 Seasonal Pricing Rules

| Period                    | Adjustment            |
| ------------------------- | --------------------- |
| January – June            | No Change             |
| July – August             | 15% Discount          |
| September – December 19   | 25% Discount          |
| December 20 – December 31 | 50% Holiday Surcharge |

---

# 🚀 Installation & Setup

## Prerequisites

* Java 17 or newer
* VS Code (Recommended)
* SQLite JDBC Driver

---

## Step 1: Download JDBC Driver

Download the SQLite JDBC Driver:

https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/

Place the JAR file inside the **lib/** folder.

---

## Step 2: Configure VS Code

Create:

```text
.vscode/settings.json
```

Add:

```json
{
    "java.project.sourcePaths": [
        "src"
    ],
    "java.project.referencedLibraries": [
        "lib/sqlite-jdbc-3.44.1.0.jar"
    ]
}
```

---

## Step 3: Run the Application

Open:

```text
Main.java
```

Run the program.

---

# 🗄️ Database

The SQLite database is:

✅ Automatically created on first launch

✅ Requires no manual setup

✅ Stores users, rooms, and bookings

---

# 🎯 Learning Outcomes

This project demonstrates:

* Object-Oriented Programming
* Java GUI Development
* Database Design
* JDBC Connectivity
* Event-Driven Programming
* File & Data Management
* Software Architecture Principles

---

# 📸 Screenshots

Add screenshots here after completing the project:

```text
images/
├── hotel-bg (2).png
├── hotel-bg.png

```

Example:

![Login Screen](images/hotel-bg (2).png)

---



# 👨‍💻 Developers

* **Chala Mosisa**
* **Filmon Haile**
* **Sisay Hulugeta**
* **Nahom Berhanu**
* **Natnael Getachew**
Course: **OOP**

Institution: **AAU**

---

# 🌟 GitHub Repository

```text
https://github.com/laacaaforbus-1234/hotel-booking-system
```

---

<div align="center">

### ⭐ If you like this project, consider giving it a star!

**Built with Java, Swing, SQLite, and OOP Principles**

</div>
