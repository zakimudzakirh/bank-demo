package demo;

import java.sql.*;
import java.util.Scanner;

public class Hello {
    public static void main(String[] args) {

        String url = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            String query = "SELECT * FROM users WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, username);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.println("User found: " + rs.getString("username"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}