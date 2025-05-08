package project.hospitalmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.hospitalmanagementsystem.utils.DatabaseConnector;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HospitalManagementSystem {
    private static  final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    public static void main(String[] args) {
        try {
            DatabaseConnector jdbc = new DatabaseConnector("jdbc:mysql://localhost:3306/hospital_databadr", "kaytee07", "");
            jdbc.connect();
        } catch (ClassNotFoundException | SQLException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
