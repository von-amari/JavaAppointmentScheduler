package DBAccess;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class for user table in database.
 * @author Yvonne King
 */
public class DBUser {

    /**
     * Username variable.
     */
    public static String userN;

    /**
     * Selects users and passwords from user table for login validation.
     * @param user username
     * @param pass password
     * @return boolean true if username and password match, false if they do not
     */
    public static boolean validateLogin(String user, String pass) {
        try {
            String sql = "SELECT * from users WHERE User_Name = ? and Password = ? ";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userN = user;

                return true;
            } else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Selects all users from user table using a select SQL statement.
     * @return list of users
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                Users user = new Users(userID, userName);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
