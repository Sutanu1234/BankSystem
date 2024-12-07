//package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class BankSystem {
    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Sbera@2003";
    private static final Logger logger = Logger.getLogger(BankSystem.class.getName());

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Scanner scanner = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;
            String phone_number;

            while (true) {
                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice1 = scanner.nextInt();
                switch (choice1) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        phone_number = user.getPhoneNumber(email);
                        if (email != null) {
                            System.out.println("\nUser Logged In!");
                            if (!accounts.accountExist(email)) {
                                System.out.println("\n1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                if (scanner.nextInt() == 1) {
                                    account_number = accounts.openAccount(email, phone_number);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                }
                            }
                            account_number = accounts.getAccountNumber(email);
                            handleUserAccount(scanner, accountManager, account_number);
                        } else {
                            System.out.println("Incorrect Email or Password!");
                        }
                        break;
                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    private static void handleUserAccount(Scanner scanner, AccountManager accountManager, long account_number) {
        int choice2 = 0;
        while (choice2 != 5) {
            System.out.println("\n1. Debit Money");
            System.out.println("2. Credit Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Log Out");
            System.out.print("Enter your choice: ");
            choice2 = scanner.nextInt();
            switch (choice2) {
                case 1:
                    accountManager.debitMoney(account_number);
                    break;
                case 2:
                    accountManager.creditMoney(account_number);
                    break;
                case 3:
                    accountManager.transferMoney(account_number);
                    break;
                case 4:
                    accountManager.getBalance(account_number);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Enter Valid Choice!");
                    break;
            }
        }
    }
}
