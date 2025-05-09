package project.hospitalmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.hospitalmanagementsystem.CRUDOperations.PatientCRUD;
import project.hospitalmanagementsystem.utils.DatabaseConnector;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HospitalManagementSystem {
    private static  final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    public static void main(String[] args) {

        PatientCRUD patientCRUD = new PatientCRUD();

        // 1. Test: Add a patient (Normal Case)
        System.out.println("---- Adding Valid Patient ----");
        boolean added = patientCRUD.addPatient("Kwame", "Mensah", "0244000000", "Accra");
        System.out.println("Add Success: " + added);

        // 2. Test: Add a patient (Missing first name - edge case)
        try {
            System.out.println("---- Adding Invalid Patient (missing first name) ----");
            patientCRUD.addPatient(null, "Doe", "0555123456", "Kumasi");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // 3. Test: View an existing patient (Assume ID 1 exists)
        System.out.println("---- Viewing Patient with ID 1 ----");
        patientCRUD.viewPatient(2);

        // 4. Test: View non-existing patient (Edge Case)
        System.out.println("---- Viewing Patient with Invalid ID (9999) ----");
        patientCRUD.viewPatient(9999);

        // 5. Test: Update patient's phone
        System.out.println("---- Updating Patient's Phone (ID=1) ----");
        boolean updated = patientCRUD.updatePatient(2, "phone_number", "0200000000");
        System.out.println("Update Success: " + updated);

        // 6. Test: Update with invalid column name (Edge Case)
        try {
            System.out.println("---- Updating with Invalid Column ----");
            patientCRUD.updatePatient(2, "invalid_column", "value");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // 7. Test: Delete existing patient (Assume ID 1 exists)
        try {
            System.out.println("---- Deleting Patient with ID 1 ----");
            boolean deleted = patientCRUD.deletePatient(2);
            System.out.println("Delete Success: " + deleted);
        } catch (Exception e) {
            System.out.println("Unexpected error while deleting: " + e.getMessage());
        }

        // 8. Test: Delete non-existing patient (Edge Case)
        try {
            System.out.println("---- Deleting Non-existing Patient (ID=9999) ----");
            boolean deleted = patientCRUD.deletePatient(9999);
            System.out.println("Delete Success: " + deleted);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // 9. Test: Invalid ID for view/update/delete (negative number)
        try {
            patientCRUD.viewPatient(-5);
        } catch (Exception e) {
            System.out.println("Expected error for invalid ID: " + e.getMessage());
        }
    }
}
