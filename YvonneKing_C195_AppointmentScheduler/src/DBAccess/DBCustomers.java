package DBAccess;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for customer database queries.
 * @author Yvonne King
 */
public class DBCustomers {

    /**
     * Adds customer to customer table using an insert SQL statement. Used in the AddCustomerController class.
     * @param customer customer object
     * @throws SQLException
     */
    public static void addCustomer(Customers customer)
            throws SQLException
    {
        String query = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID ) VALUES (?, ?, ?, ?, '" +DBUser.userN+ "', '" +DBUser.userN+ "', ?)";

        try {
            // set all the preparedstatement parameters
            PreparedStatement st = DBConnection.getConnection().prepareStatement(query);

            st.setString(1, customer.getName());
            st.setString(2, customer.getAddress());
            st.setString(3, customer.getPostalCode());
            st.setString(4, customer.getPhoneNumber());
            st.setInt(5, customer.getDivision());

            // execute the preparedstatement insert
            st.executeUpdate();
            st.close();
        }
        catch (SQLException se)
        {
            // log exception
            throw se;
        }
    }//end add customer

    /**
     * Removes customer from customer table using an delete SQL statement. Used in the Customer deleteCustomer method.
     * @param customerID customer id
     * @throws SQLException
     */
    public static void deleteCustomer(int customerID ) throws SQLException {

        String query = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);

        ps.setInt(1, customerID);

        ps.execute();

    }//end delete customer

    /**
     * Modifies the customer to the customer table using an update SQL statement. Used in the ModifyCustomerController class.
     * @param customer customer object
     */
    public static void updateCustomer(Customers customer)  {

    try{
        String query = "UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Created_By = '" +DBUser.userN+ "', Last_Updated_By = '" +DBUser.userN+ "', Division_ID =? WHERE Customer_ID =?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);

        // ps.setInt(1, customerID);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhoneNumber());
       // ps.setString(5, customer.getUserID()); FIXME: created by and last updated is hard coded
      //  ps.setString(6, customer.getUserID());
        ps.setInt(5, customer.getDivision());
        ps.setInt(6, customer.getId());

        ps.executeUpdate();
    }
    catch(SQLException e) {
        //ignore
    }
    }//end update customer

    /**
     * Selects all customers from customer table by using a select SQL statement.
     * @return list of customers
     */
    public static ObservableList<Customers> getAllCustomers() {
            ObservableList<Customers> customerList = FXCollections.observableArrayList();
            try {
                String sql = "SELECT * FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID;";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    Customers customer = new Customers(0, "", "", "", "",  1, 1);
                    int customerID = rs.getInt("Customer_ID");
                    String name = rs.getString("Customer_Name");
                    String address = rs.getString("Address");
                    String postalCode = rs.getString("Postal_Code");
                    String phone = rs.getString("Phone");
                    int country = rs.getInt("Country_ID");   //FIXME this needs to be fixed where division filters, not in requriements to be displayed
                    int divisionId =  rs.getInt("Division_ID"); //must be shown for requirements

                 customer = new Customers(customerID, name, address, postalCode, phone, country, divisionId);

                    customerList.add(customer);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return customerList;
        }
    }



