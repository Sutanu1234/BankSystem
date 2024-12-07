//package BankingManagementSystem;

//import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone_number = scanner.nextLine();
        if(user_exist(email)) {
            System.out.println("User Already Exists for this Email Address!!");
            return;
        }
        String register_query = "INSERT INTO User(full_name, email, password, phone_number) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, phone_number);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registration Successfull!");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneNumber(String email){
        String query = "SELECT phone_number FROM user WHERE email = ?";
        String phoneNumber = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                phoneNumber = resultSet.getString("phone_number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return phoneNumber;
    }

    public boolean user_exist(String email){
        String query = "SELECT * FROM user WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
