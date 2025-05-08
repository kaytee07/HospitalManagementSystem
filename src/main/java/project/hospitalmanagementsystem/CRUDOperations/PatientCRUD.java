package project.hospitalmanagementsystem.CRUDOperations;

import project.hospitalmanagementsystem.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientCRUD {
    private static final Logger logger = LoggerFactory.getLogger(PatientCRUD.class);
    DatabaseConnector jdbc;


    public PatientCRUD() {
        jdbc = new DatabaseConnector("jdbc:mysql://localhost:3306/hospital_database", "root", "Hhdh573)&@/dg");
    }

    public boolean addPatient(int patient_id, String first_name, String last_name, String phone, String address) {
        if (first_name == null || last_name == null) {
            logger.error("Invalid input parameters for addPatient");
            throw new IllegalArgumentException("First name, last name, and valid email are required");
        }

        String sqlCommands = "INSERT INTO Patients (patient_id, first_name, last_name,  phone_number, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = jdbc.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(sqlCommands)) {
            prepareStatement.setString(1, Object.toS);
            prepareStatement.setString(2, first_name);
            prepareStatement.setString(3, last_name);
            prepareStatement.setString(4, phone);
            prepareStatement.setString(5, address);

            int rowsAffected = prepareStatement.executeUpdate();
            logger.info("Patient added successfully: name={}", first_name);
            return rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found: {}", e.getMessage());
            throw new RuntimeException("Database driver error", e);
        } catch (SQLException e) {
            logger.error("Failed to add patient: {}", e.getMessage());
            throw new RuntimeException("Database error while adding patient", e);
        }
    }

    public boolean deletePatient(int patientId) throws SQLException, ClassNotFoundException {
        if (patientId <= 0) {
            logger.error("Invalid patient ID provided for deletion: {}", patientId);
            throw new IllegalArgumentException("Patient ID must be a positive integer");
        }

        String sqlCommand = "DELETE FROM Patient WHERE patient_id = ?";

        try (Connection conn = jdbc.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(sqlCommand)) {

            prepareStatement.setInt(1, patientId);
            int rowsAffected = prepareStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Patient deleted successfully: ID={}", patientId);
                return true;
            } else {
                logger.warn("No patient found with ID={} to delete", patientId);
                return false;
            }

        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found: {}", e.getMessage());
            throw new ClassNotFoundException("Database driver error", e);
        } catch (SQLException e) {
            logger.error("Failed to delete patient: {}", e.getMessage());
            throw new SQLException("Database error while deleting patient", e);
        }
    }


    public boolean updatePatient(int patientId, String column, String newValue) {
        if (patientId <= 0 || column == null || newValue == null) {
            logger.error("Invalid input for updatePatient: ID={}, column={}, value={}", patientId, column, newValue);
            throw new IllegalArgumentException("Valid patient ID, column name, and new value are required");
        }


        Set<String> allowedColumns = Set.of("first_name", "last_name", "phone", "address");
        if (!allowedColumns.contains(column)) {
            logger.error("Attempt to update disallowed column: {}", column);
            throw new IllegalArgumentException("Cannot update column: " + column);
        }

        String sql = "UPDATE Patient SET " + column + " = ? WHERE patient_id = ?";

        try (Connection conn = jdbc.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newValue);
            stmt.setInt(2, patientId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Updated column '{}' for patient ID={}", column, patientId);
                return true;
            } else {
                logger.warn("No patient found with ID={} to update", patientId);
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to update patient ID={}: {}", patientId, e.getMessage());
            throw new RuntimeException("Error while updating patient", e);
        }
    }


    public void viewPatient(int patientId) {
        if (patientId <= 0) {
            logger.error("Invalid patient ID for viewPatient: {}", patientId);
            throw new IllegalArgumentException("Patient ID must be a positive integer");
        }

        String sqlCommand = "SELECT * FROM Patient WHERE patient_id = ?";

        try (Connection conn = jdbc.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(sqlCommand)) {

            prepareStatement.setInt(1, patientId);
            ResultSet result = prepareStatement.executeQuery();

            if (result.next()) {
                logger.info("Patient found:");
                System.out.println("ID: " + result.getInt("patient_id"));
                System.out.println("Name: " + result.getString("first_name") + " " + result.getString("last_name"));
                System.out.println("Gender: " + result.getString("gender"));
                System.out.println("Phone: " + result.getString("phone"));
                System.out.println("Address: " + result.getString("address"));
            } else {
                logger.warn("No patient found with ID={}", patientId);
                System.out.println("No patient found with ID: " + patientId);
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to view patient ID={}: {}", patientId, e.getMessage());
            throw new RuntimeException("Error while viewing patient", e);
        }
    }

}
