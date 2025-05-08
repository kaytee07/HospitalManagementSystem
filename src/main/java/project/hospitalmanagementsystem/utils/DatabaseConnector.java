package project.hospitalmanagementsystem.utils;
import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static  final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private String url;
    private String user;
    private String password ;
    DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            logger.info("Connected to database");

        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver Not Found");
            e.printStackTrace();
        } catch (SQLException e){
            logger.error("connection to database failed");
            e.printStackTrace();
        }
    }
}
