package DBAccess;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for country database queries.
 * @author Yvonne King
 */
public class DBCountries {

    /**
     * Selects all countries from the country table using a select SQL statement. Used in the Add and ModifyCustomerController classes.
     * @return list of all countries
     */
        public static ObservableList<Countries> getAllCountries() {
            ObservableList<Countries> countryList = FXCollections.observableArrayList();
            try {
                String sql = "SELECT * from countries ";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int countryID = rs.getInt("Country_ID");
                    String countryName = rs.getString("Country");
                    Countries country = new Countries(countryID, countryName);
                    countryList.add(country);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return countryList;
        }
}
