package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *Class creates and returns statement object.
 * @author Yvonne King
 */
public class DBQuery {


    private static Statement statement; //statement reference

    /**
     * Sets statement object.
     * @param connection
     * @throws SQLException
     */
    public static void setStatement(Connection connection) throws SQLException {

        statement = connection.createStatement();
    }

    /**
     * Returns statement object.
     * @return
     */
    public static Statement getStatement() {
        return statement;
    }
}
