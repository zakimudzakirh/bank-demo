package demo;

import java.sql.*;
import java.util.Scanner;

public class Hello {
    public static void main(String[] args) {

        String url = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        // ✅ Validasi config
        if (url == null || dbUser == null || dbPassword == null) {
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {

            String username = sc.nextLine();

            // ✅ Validasi input
            if (username == null || username.isBlank() || username.length() > 50) {
                return;
            }

            String query = "SELECT username FROM users WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                // ✅ Timeout query (detik)
                pstmt.setQueryTimeout(5);

                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                    } else {
                    }
                }

            }

        } catch (SQLException e) {
            // ❌ jangan print stacktrace ke user
        } catch (Exception e) {
        }
    }
}