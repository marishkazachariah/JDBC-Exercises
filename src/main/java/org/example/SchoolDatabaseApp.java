package org.example;

import java.sql.*;

public class SchoolDatabaseApp {
    public static void main(String[] args) {
        try {
            // 1. Connect to the database
            String url = "jdbc:mysql://localhost:3306/School";
            String user = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, user, password);

            // 2. Insert Data: Insert a new course
            insertCourse(connection, "Science", 34, 3423.32);
            insertCourse(connection, "Math", 9, 3423.32);
            insertCourse(connection, "Art History", 12, 654.34);
            insertCourse(connection, "Physical Education", 23, 34.32);

            // 3. Update Data: Update course fee
            updateCourseFee(connection, 1, 543.34);

            // 4. Query Data: Fetch and display all courses
            queryAllCourses(connection);

            // 5. Delete Data: Delete a course
            deleteCourse(connection, 8);
            deleteCourse(connection, 11);

            // Final query to show updates
            queryAllCourses(connection);

            // 6. Close the database connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCourse(Connection connection, String courseName, int courseDuration, double courseFee) throws SQLException {
        String insertQuery = "INSERT INTO Courses (courseName, courseDuration, courseFee) VALUES (?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
            pst.setString(1, courseName);
            pst.setInt(2, courseDuration);
            pst.setDouble(3, courseFee);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    private static void updateCourseFee(Connection connection, int courseId, double newCourseFee) throws SQLException {
        String updateQuery = "UPDATE Courses SET courseFee = ? WHERE courseID = ?";

        try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
            pst.setDouble(1, newCourseFee);
            pst.setInt(2, courseId);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }

    private static void queryAllCourses(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM Courses";

        try (PreparedStatement pst = connection.prepareStatement(selectQuery);
             ResultSet rs = pst.executeQuery()) {
            System.out.println("Courses:");

            while (rs.next()) {
                int courseId = rs.getInt("courseID");
                String courseName = rs.getString("courseName");
                double courseFee = rs.getDouble("courseFee");

                System.out.println("Course ID: " + courseId + ", Name: " + courseName + ", Fee: " + courseFee);
            }
        }
    }

    private static void deleteCourse(Connection connection, int courseId) throws SQLException {
        String deleteQuery = "DELETE FROM Courses WHERE courseID = ?";

        try (PreparedStatement pst = connection.prepareStatement(deleteQuery)) {
            pst.setInt(1, courseId);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
}
