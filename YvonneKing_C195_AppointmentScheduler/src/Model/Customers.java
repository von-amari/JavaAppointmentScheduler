package Model;

import DBAccess.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Contains members for manipulating customer objects.
 * @author Yvonne King.
 */
public class Customers {
    /**
     * Customer id field.
     */
    private int id;

    /**
     * Customer name field.
     */
    private String name;

    /**
     * Customer address field.
     */
    private String address;

    /**
     * Customer postal code field.
     */
    private String postalCode;

    /**
     * Customer phone number field.
     */
    private String phoneNumber;

    /**
     * Customer country field.
     */
    private int country;

    /**
     * Customer division field
     */
    private int division;

    /**
     * User id field.
     */
    private int userID;

    /**
     * List of all customers.
     */
    public static ObservableList<Customers>allCustomers = FXCollections.observableArrayList();

    /**
     * Parameterized constructor for creating new customer objects.
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phoneNumber customer phone number
     * @param country customer country id
     * @param division customer first level division id
     */
    public Customers(int id, String name, String address, String postalCode, String phoneNumber, int country, int division) {
        this.id =  id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.division = division;
    }

    /**
     * Overloaded constructor used in the DBAppointments.getAllAppointments method.
     * @param id customer id
     * @param name customer name
     */
    public Customers(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for customer id.
     * @return customer id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for customer id.
     * @param id customer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for customer name.
     * @return customer name string
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for customer name.
     * @param name customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for customer address.
     * @return customer address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for customer address.
     * @param address customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *Getter for postal code.
     * @return postal code string
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for postal code.
     * @param postalCode postal code string
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Setter for phone number.
     * @param phoneNumber phone number string
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for phone number.
     * @return phone number string
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for country id.
     * @param country country id
     */
    public void setCountry(int country) {
        this.country = country;
    }

    /**
     * Getter for country id.
     * @return country id
     */
    public int getCountry() {
        return country;
    }

    /**
     * Setter for division id.
     * @param division division id
     */
    public void setDivision(int division) {
        this.division = division;
    }

    /**
     * Getter for division id.
     * @return division id
     */
    public int getDivision() {
        return division;
    }

    /**
     * Setter for user id.
     * @param userID user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for user id.
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns all customers.
     * @return allCustomer Observable List
     */
    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Deletes customer from table and database.
     * @param customer customer object
     * @return true
     * @throws SQLException provides information on a database access error
     */
    public static boolean deleteCustomer(Customers customer) throws SQLException {
        //FIXME: delete class in DBCustomer

        allCustomers.remove(customer);
        DBCustomers.deleteCustomer(customer.getId());
        return true;
    }

    /**
     * Allows customer id and name to show as a string in the customer combo box.
     * @return customer id and name in a string format
     */
    @Override
     public String toString() {
        return((id + " " + name));
    }
    }//end customer model class




