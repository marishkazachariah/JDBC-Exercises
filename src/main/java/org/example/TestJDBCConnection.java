package org.example;

import java.sql.*;

public class TestJDBCConnection {

    public static void main(String[] args) {
        TestJDBCConnection main = new TestJDBCConnection();
        main.display();
    }

    private void display() {
        String url = "jdbc:mysql://localhost:3306/School";
        String user = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database successfully");
            String query = "SELECT * FROM Courses";
            try (PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int courseId = rs.getInt("courseID");
                    int courseDuration = rs.getInt("courseDuration");
                    double courseFee = rs.getDouble("courseFee");
                    // Process the retrieved values
                    System.out.println("Course ID: " + courseId);
                    System.out.println("Course Duration: " + courseDuration);
                    System.out.println("Course Fee: " + courseFee);

                    // Close connections unless it is handled already with try-resources
                    // rs.close();
                    // pst.close();
                    // conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }
    }
}