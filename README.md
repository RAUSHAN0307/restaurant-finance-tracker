# FinanceTracker

A full-stack restaurant finance and operations management platform built with **Angular, Spring Boot, and MySQL**.

FinanceTracker helps restaurant owners manage billing, menu items, inventory, vendors, expenses, employee salaries, vouchers, and financial analytics from a single system.

---

## 🚀 Features

- JWT-based authentication and role-based access control
- Roles: **Owner, Manager, and Waiter**
- Restaurant profile management
- Menu item management
- Bill generation with item-wise calculation
- Automatic **18% GST calculation**
- Voucher and discount support
- Expense tracking
- Vendor and pending-payment management
- Inventory management with low-stock alerts
- Employee and salary payment management
- Dashboard analytics for income, expenses, and net profit
- Financial reports with charts
- PDF and Excel report export
- Low-stock and vendor pending-payment notifications

---

## 🛠️ Tech Stack

### Frontend

- Angular 16
- TypeScript
- RxJS
- Chart.js
- ApexCharts
- jsPDF
- XLSX

### Backend

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

---

## 🏗️ Architecture

```text
Angular Frontend
       |
       | HTTP Requests + JWT Bearer Token
       v
Spring Boot REST API
       |
       v
Controllers
       |
       v
Services / Business Logic
       |
       v
Spring Data JPA Repositories
       |
       v
MySQL Database
```

---

## 🔐 Authentication Flow

```text
User Login
   |
   v
Spring Security AuthenticationManager
   |
   v
CustomUserDetailsService
   |
   v
BCrypt Password Verification
   |
   v
JWT Token Generation
   |
   v
Angular stores token in localStorage
   |
   v
HTTP Interceptor adds Authorization: Bearer <token>
   |
   v
JwtAuthFilter validates token on every protected request
   |
   v
Protected API Access
```

---

## 💰 Billing Workflow

```text
Create Bill
   |
   v
Add Bill Items
   |
   v
Calculate Item Total
Quantity × Cost
   |
   v
Update Bill Total
   |
   v
Calculate GST
18% of Total Amount
   |
   v
Apply Voucher Discount
if minimum amount is satisfied
   |
   v
Calculate Final Net Amount
```

---

## 📦 Main Modules

| Module | Description |
|---|---|
| **Authentication** | Owner registration, login, JWT session handling |
| **Dashboard** | Income, expense, profit, monthly and yearly analytics |
| **Billing** | Bill creation, bill items, GST, payment mode, voucher discounts |
| **Menu** | Add, update, delete, and view restaurant menu items |
| **Expenses** | Record and manage restaurant expenses |
| **Inventory** | Track stock quantity, usage, vendors, and low-stock levels |
| **Vendors** | Manage supplier information, pending amounts, and due dates |
| **Employees** | Manage employee records and staff login credentials |
| **Salary** | Record monthly salary payments |
| **Vouchers** | Create and manage percentage-based discount vouchers |
| **Reports** | Financial charts with PDF and Excel export |
| **Notifications** | Low-stock and vendor pending-payment alerts |

---

## 🔗 Important API Endpoints

### Authentication

```http
POST /api/users/register-owner
POST /api/auth/v1/sign-in
```

### Menu

```http
POST   /menu/{userId}
GET    /menu/user/{userId}
PUT    /menu/{itemId}
DELETE /menu/{itemId}
```

### Bills

```http
POST   /bills/user/{userId}/voucher/{voucherId}
GET    /bills/user/{userId}
GET    /bills/{billId}
DELETE /bills/{billId}
```

### Bill Items

```http
POST /bill-items/bill/{billId}/item/{itemId}
GET  /bill-items/bill/{billId}
```

### Expenses

```http
POST   /expenses/{userId}
GET    /expenses/user/{userId}
PUT    /expenses/{expenseId}
DELETE /expenses/{expenseId}
```

### Inventory

```http
POST   /inventory/{userId}/add
GET    /inventory/{userId}/all
PUT    /inventory/{userId}/update/{inventoryId}
DELETE /inventory/{userId}/delete/{inventoryId}
```

### Vendors

```http
POST   /vendors/{userId}/add
GET    /vendors/{userId}/all
PUT    /vendors/{userId}/update/{vendorId}
DELETE /vendors/{userId}/delete/{vendorId}
```

### Analytics

```http
GET /analytics/user/{userId}?startDate=dd-MM-yyyy&endDate=dd-MM-yyyy

GET /analytics/income-expense?ownerId={userId}&period=MONTHLY&category=ALL
```

---

## 🗄️ Database Entities

- User
- Employee
- Expense
- Bill
- BillItem
- MenuItem
- Voucher
- SalaryPayments
- Vendor
- Inventory
- Restaurant
- Message

---

## ⚙️ Setup Instructions

### Prerequisites

Make sure the following are installed:

- Java 17
- Maven
- Node.js
- npm
- Angular CLI
- MySQL
- Git

---

### 🔧 Backend Setup

#### 1. Open the backend folder

```bash
cd financeTracker
```

#### 2. Configure MySQL

Open:

```text
src/main/resources/application.properties
```

Configure your MySQL database username, password, and other required properties.

#### 3. Create the database

Open MySQL and execute:

```sql
CREATE DATABASE financeTracker;
```

#### 4. Start the Spring Boot backend

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:3000
```

> **Note:** Make sure your `application.properties` contains `server.port=3000`. Otherwise, Spring Boot normally runs on port `8080`.

---

### 🎨 Frontend Setup

#### 1. Open the frontend folder

```bash
cd finance-tracker
```

#### 2. Install dependencies

```bash
npm install
```

#### 3. Start Angular application

```bash
npm start
```

Or:

```bash
ng serve
```

Frontend runs on:

```text
http://localhost:4200
```

---

## 🔒 Security

FinanceTracker implements multiple security mechanisms:

- Passwords are stored using **BCrypt hashing**
- JWT token is generated after successful login
- Angular HTTP Interceptor attaches JWT to protected API requests
- Spring Security validates JWT before allowing protected routes
- Role-based authorization is implemented using `@PreAuthorize`
- Protected APIs require valid authentication

---

## 📊 Analytics

The dashboard provides financial insights such as:

- Total Income
- Total Expenses
- Net Profit
- Monthly Income
- Monthly Expenses
- Yearly Financial Data
- Expense Categories
- Revenue Trends
- Inventory Status
- Pending Vendor Payments

Charts are implemented using:

- Chart.js
- ApexCharts

---

## 📄 Reports

FinanceTracker supports financial report generation and export.

### Supported Formats

- 📄 PDF
- 📊 Excel

Reports can contain information related to:

- Income
- Expenses
- Profit
- Bills
- Inventory
- Vendor payments
- Salary payments

---

## 📁 Project Structure

```text
FinanceTracker/
│
├── financeTracker/                 # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   ├── test/
│   │   └── pom.xml
│   │
│   └── mvnw
│
├── finance-tracker/                # Angular Frontend
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── tsconfig.json
│
└── README.md
```

---

## 🔮 Future Improvements

- Add centralized exception handling and standardized API error responses
- Add database migration support using Flyway or Liquibase
- Improve tenant-level authorization using the authenticated user instead of only path variables
- Add automated unit and integration tests
- Add pagination for large datasets
- Add database-level aggregation for large analytics data
- Move credentials and JWT secret to environment variables
- Add Docker configuration
- Add CI/CD pipeline
- Improve API documentation using Swagger/OpenAPI

---

## 👨‍💻 Author

**Raushan Kumar Jha**

Java Full-Stack Developer

📧 **jharaushan0307@gmail.com**

---

## ⭐ Project

If you find this project useful, consider giving it a **star ⭐** on GitHub.
