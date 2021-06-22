package DBAccess;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for division database queries.
 * @author Yvonne King
 */
public class DBDivision {

    /**
     * Selects all divisions from the first level division table using a select SQL query. Used in the Add and ModifyCustomerControllers.
     * @return returns a list of all divisions
     */
        public static ObservableList<Divisions> getAllDivisions() {
            ObservableList<Divisions> divisionList = FXCollections.observableArrayList();

            try {
                String sql = "SELECT * from first_level_divisions";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int divisionID = rs.getInt("Division_ID");
                    String divisionName = rs.getString("Division");
                    int country = rs.getInt("COUNTRY_ID");
                    Divisions division = new Divisions(divisionID, divisionName, country);
                    divisionList.add(division);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return divisionList;
        }
        }


