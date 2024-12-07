//package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class Accounts {
    private static final Logger logger = Logger.getLogger(Accounts.class.getName());
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long openAccount(String email, String phone_number) {
        if (!accountExist(email)) {
            String openAccountQuery = "INSERT INTO Accounts(account_number, full_name, email, balance, security_pin, phone_number) VALUES(?, ?, ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security Pin (size of 4): ");
            String securityPin = scanner.nextLine();

            try {
                long accountNumber = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery);
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, securityPin);
                preparedStatement.setString(6, phone_number);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return accountNumber;
                } else {
                    throw new RuntimeException("Account Creation failed!!");
                }
            } catch (SQLException e) {
                logger.severe("Error creating account: " + e.getMessage());
            }
        }
        throw new RuntimeException("Account Already Exist");
    }

    public long getAccountNumber(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("account_number");
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving account number: " + e.getMessage());
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }

    private long generateAccountNumber() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT account_number FROM Accounts ORDER BY account_number DESC LIMIT 1");
            if (resultSet.next()) {
                long lastAccountNumber = resultSet.getLong("account_number");
                return lastAccountNumber + 1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            logger.severe("Error generating account number: " + e.getMessage());
        }
        return 10000100;
    }

    public boolean accountExist(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.severe("Error checking account existence: " + e.getMessage());
        }
        return false;
    }
}
