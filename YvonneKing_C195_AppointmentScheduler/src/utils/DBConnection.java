package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Sets up the database connection.
 * @author Yvonne King
 */
public class DBConnection {

    //JBDC url parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress =  "//wgudb.ucertify.com/";
    private static final String dbName = "WJ07xEh";

    //url
    //private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone = SERVER";

    //driver interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection con = null;

    /**
     * Database user name
     */
    private static final String userName = "U07xEh"; //username
    /**
     * Database password
     */
    private static final String password = "53689160857"; //password

    /**
     * Starts the database connection.
     * @return connection variable
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return  con;
    }

    /**
     * Getter for Connection con variable.
     * @return connection variable
     */
    public static Connection getConnection() {
        return con;
    }

    /**
     * Closes connection.
     */
    public static void closeConnection() {
        try {
            con.close();
            System.out.println("closed successful");
        } catch (SQLException throwables) {
            System.out.println( throwables.getMessage());
            //just ingore this catch
        }
    }

}
