//package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountManager {
    private static final Logger logger = Logger.getLogger(AccountManager.class.getName());
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void creditMoney(long accountNumber) {
        processTransaction(accountNumber, "credit");
    }

    public void debitMoney(long accountNumber) {
        processTransaction(accountNumber, "debit");
    }

    private void processTransaction(long accountNumber, String transactionType) {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String securityPin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            if (accountNumber != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? and security_pin = ?");
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, securityPin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("balance");
                    if (transactionType.equals("debit") && amount <= currentBalance || transactionType.equals("credit")) {
                        String updateQuery = transactionType.equals("debit") ? "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?" : "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                        PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                        updateStmt.setDouble(1, amount);
                        updateStmt.setLong(2, accountNumber);
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs." + amount + " " + transactionType + "ed Successfully");
                            connection.commit();
                        } else {
                            System.out.println("Transaction Failed!");
                            connection.rollback();
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                    }
                } else {
                    System.out.println("Invalid Security Pin!");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error processing transaction: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.severe("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }

    public void transferMoney(long senderAccountNumber) {
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiverAccountNumber = scanner.nextLong();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String securityPin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            if (senderAccountNumber != 0 && receiverAccountNumber != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?");
                preparedStatement.setLong(1, senderAccountNumber);
                preparedStatement.setString(2, securityPin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("balance");
                    if (amount <= currentBalance) {
                        String debitQuery = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                        String creditQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";

                        PreparedStatement debitStmt = connection.prepareStatement(debitQuery);
                        PreparedStatement creditStmt = connection.prepareStatement(creditQuery);
                        debitStmt.setDouble(1, amount);
                        debitStmt.setLong(2, senderAccountNumber);
                        creditStmt.setDouble(1, amount);
                        creditStmt.setLong(2, receiverAccountNumber);

                        int rowsAffected1 = debitStmt.executeUpdate();
                        int rowsAffected2 = creditStmt.executeUpdate();

                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Rs." + amount + " Transferred Successfully");
                            connection.commit();
                        } else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                    }
                } else {
                    System.out.println("Invalid Security Pin!");
                }
            } else {
                System.out.println("Invalid account number");
            }
        } catch (SQLException e) {
            logger.severe("Error transferring money: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.severe("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }

    public void getBalance(long accountNumber) {
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String securityPin = scanner.nextLine();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, securityPin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Current Balance: Rs." + balance);
            } else {
                System.out.println("Invalid Security Pin!");
            }
        } catch (SQLException e) {
            logger.severe("Error getting balance: " + e.getMessage());
        }
    }
}
