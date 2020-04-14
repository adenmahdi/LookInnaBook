import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.*;

public class Login {
	public static void UserLogin() {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
				Statement statement = connection.createStatement();) {
				
				Scanner keyboard = new Scanner(System.in);
				System.out.println("Email:");
				String username = keyboard.nextLine();
				System.out.println("Password:");
				String password = keyboard.nextLine();
				String temp = "select pass " + "from username " + "where email =?";
				PreparedStatement statement1 = connection.prepareStatement(temp);
				statement1.setString(1, username);
				ResultSet resultSet = statement1.executeQuery();
				while (resultSet.next()) {
					if (resultSet.getString("pass").equals(password)) {
						System.out.println("Login successful!");
					}
				}
				keyboard.close();
		}
		catch (SQLException sqle) {
        	System.err.format("SQL State: %s\n%s", sqle.getSQLState(), sqle.getMessage());
        }
	}
	
	public static void UserSignUp() {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
				Statement statement = connection.createStatement();) {
				
				Scanner keyboard = new Scanner(System.in);
				System.out.println("First Name:");
				String first = keyboard.nextLine();
				System.out.println("Last Name:");
				String last = keyboard.nextLine();
				System.out.println("Email:");
				String username = keyboard.nextLine();
				System.out.println("Password:");
				String password = keyboard.nextLine();
				System.out.println("Address Number:");
				int addr_no = Integer.parseInt(keyboard.nextLine());
				System.out.println("Address:");
				String address = keyboard.nextLine();
				System.out.println("Postal Code:");
				String post = keyboard.nextLine();
				System.out.println("City:");
				String city = keyboard.nextLine();
				System.out.println("Country:");
				String country = keyboard.nextLine();
				System.out.println("State:");
				String state = keyboard.nextLine();
				
				String temp = "insert into username values "+"(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement1 = connection.prepareStatement(temp);
				statement1.setString(1, first);
				statement1.setString(2, last);
				statement1.setString(3, username);
				statement1.setString(4, password);
				statement1.setInt(5, addr_no);
				statement1.setString(6, address);
				statement1.setString(7, post);
				statement1.setString(8, city);
				statement1.setString(9, country);
				statement1.setString(10, state);
				
				statement1.executeQuery();
				keyboard.close();
		}
		catch (SQLException sqle) {
        	System.err.format("SQL State: %s\n%s", sqle.getSQLState(), sqle.getMessage());
        }
	}
	
	public static void main(String[] args) {
		String temp;
		while(true) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Login or Sign Up?");
			temp = keyboard.nextLine();
			//keyboard.close();
			if (temp.toLowerCase().equals("sign up")) {
				UserSignUp();
			}
			else if (temp.toLowerCase().equals("login")) {
				UserLogin();
			}
			else {
				keyboard.close();
				return;
			}
		}
	}
}
