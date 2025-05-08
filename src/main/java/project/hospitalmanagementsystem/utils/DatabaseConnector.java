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
    public DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            logger.info("Connected to database");
            return  connection;

        } catch (ClassNotFoundException e) {
            throw  new ClassNotFoundException("JDBC Driver Not Found");
        } catch (SQLException e){
            throw  new SQLException("connection to database failed");
        }
    }
}
