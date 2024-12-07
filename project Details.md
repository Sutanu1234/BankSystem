# Bank Management System JDBC Project (Java & MySQL)

## 1. Language Used:
- Java (JDBC for database interaction)

## 2. Tools Used:
- **MySQL**: Database management system
- **JDBC**: Java Database Connectivity
- **NetBeans IDE**: Development environment for Java
- **XAMPP/WAMP**: For setting up MySQL server
- **JDBC Drivers**: For MySQL connection in Java

## 3. Concepts Covered:
- **JDBC (Java Database Connectivity)**: Connecting Java applications with MySQL databases.
- **Database Design**: Designing tables, schemas, and relationships in MySQL.
- **CRUD Operations**: Creating, Reading, Updating, and Deleting records in the database.
- **Prepared Statements**: Using prepared statements to interact with the database securely.
- **Exception Handling**: Handling SQL exceptions and other potential errors.

## 4. Project Description:
### 1. **Database Connection Setup**:
   - Set up a MySQL database for the bank management system.
   - Create tables such as `Customers`, `Transactions`, `Accounts`, etc.
   - Establish a connection from Java using JDBC to interact with the database.

### 2. **User Authentication**:
   - The system allows login for bank staff and users.
   - User authentication is managed through stored usernames and passwords in the `Users` table.

### 3. **Customer Management**:
   - Add, update, and delete customer details.
   - Store customer information such as name, address, phone number, and account number.
   - The system checks if the customer already exists before adding new ones.

### 4. **Account Management**:
   - Users can create new bank accounts, deposit or withdraw funds, and view account balances.
   - Each transaction (deposit/withdrawal) is stored in the `Transactions` table.
   - Each account has a unique account number linked to a specific customer.

### 5. **Transaction Management**:
   - Records transactions such as deposits and withdrawals.
   - Transaction data includes account number, amount, type (deposit/withdrawal), and date/time.
   - The transaction log is updated after each action.

### 6. **Balance Inquiry**:
   - Users can check their current account balance.
   - The system fetches the balance from the `Accounts` table based on the account number.

### 7. **Reporting**:
   - Generate reports on customer details, transactions, and account balances.
   - A simple reporting feature allows the user to view or print customer information.

### 9. **Exception Handling**:
   - Proper exception handling is implemented to catch SQL exceptions and display user-friendly error messages.
   - Handling invalid inputs and transactions such as insufficient balance.

## 5. DataBase Images:

![Screenshot 2024-12-07 214357](https://github.com/user-attachments/assets/53bcef02-fc61-4ee1-add5-d082cc7e8806)
![Screenshot 2024-12-07 214347](https://github.com/user-attachments/assets/71d952d5-8927-4b7c-8673-e741d23cce9b)


## 6. Output Images:

![Screenshot 2024-12-07 214630](https://github.com/user-attachments/assets/b10a5b45-7604-4c0b-a8e1-fcbc3c6cc5af)
![Screenshot 2024-12-07 214648](https://github.com/user-attachments/assets/2c3f6a5b-e4e5-47e6-b3d7-0d5a650a63e7)
![Screenshot 2024-12-07 214710](https://github.com/user-attachments/assets/59771258-53a0-4ca1-b2b0-371290ad53dd)








