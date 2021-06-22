package Model;

import DBAccess.DBAppointments;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;


/**
 * Methods and fields for manipulating appointment objects.
 * @author Yvonne King
 */
public class Appointments {


    /**
     * Appointment id field.
     */
    private int id;

    /**
     * Appointment title field.
     */
    private String title;

    /**
     * Appointment description field.
     */
    private String description;

    /**
     * Appointment location field.
     */
    private String location;

    /**
     * Appointment type field.
     */
    private String type;

    /**
     * Appointment date field.
     */
    private LocalDate date;

    /**
     * Appointment start time field.
     */
    private LocalTime startTime;

    /**
     * Appointment end time field.
     */
    private LocalTime endTime;

    /**
     * Appointment customer id field.
     */
    private int customerID;

    /**
     * Appointment customer name field.
     */
    private String customerName;

    /**
     * Appointment contact id field.
     */
    private int contactID;

    /**
     * Appointment contact name field.
     */
    private String contactName;

    /**
     * Appointment user id.
     */
    private int userID;

    /**
     * Appointment month
     */
    private Month month;


    /**
     * Overloaded constructor used in the DBAppointments.getMonthAppts method.
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param date appointment date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerID appointment customer id
     * @param contactID appointment contact id
     */
    public Appointments (int id, String title, String description, String location, String type, LocalDate date, LocalTime startTime, LocalTime endTime, int customerID, int contactID) {
        this.id =  id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.contactID = contactID;
    }

    /**
     * Constructor for creating new appointments with contact and customer ids. Used in the Add and Modify Appointment controller classes.
     * @param id appointment id, auto generated in database
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param date appointment date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerID appointment customer id
     * @param contactID appointment contact id
     * @param userID appointment user id
     */
public Appointments (int id, String title, String description, String location, String type, LocalDate date, LocalTime startTime, LocalTime endTime, int customerID, int contactID, int userID) {
    this.id =  id;
    this.title = title;
    this.description = description;
    this.type = type;
    this.date = date;
    this.location = location;
    this.startTime = startTime;
    this.endTime = endTime;
    this.customerID = customerID;
    this.contactID = contactID;
    this.userID = userID;
}

    /**
     * Overloaded constructor for DBAppointments.getPlanningType method.
     * @param month appointment month
     * @param type appointment type
     */
    public Appointments (Month month, String type) {
    this.month = month;
    this.type = type;
}

    /**
     * Overloaded constructor for new appointments with contact names. Used in DBAppointments.getAllAppointments method to set Appointment Tableview.
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param date appointment date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerID appointment customer id
     * @param contactName appointment contact name
     * @param userID appointment user id
     */
    public Appointments (int id, String title, String description, String location, String type, LocalDate date, LocalTime startTime, LocalTime endTime, int customerID, String contactName, int userID) {
        this.id =  id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.contactName = contactName;
        this.userID = userID;
    }

    /**
     * Overloaded constructor used in the DBAppointments.getContactSchedule method.
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param type appointment type
     * @param date appointment date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerID appointment customer id
     */
    public Appointments(int id, String title, String description, String type, LocalDate date, LocalTime startTime, LocalTime endTime, int customerID){
        this.id =  id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;

    }

    /**
     * Overloaded constructor for DBAppointments.getCustomerReport method. Used in Customer Report Tableview.
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param type appointment type
     * @param date appointment date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param contactName appointment contact name
     */
    public Appointments (int id, String title, String description, String type, LocalDate date, LocalTime startTime, LocalTime endTime, String contactName) {
        this.id =  id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contactName = contactName;
    }

    /**
     * Getter for appointment id.
     * @return appointment id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for appointment id. Auto generated in appointment database table.
     * @param id appointment id
     */
    public void setID(int id) {
        this.id= id;
    }

    /**
     * Getter for appointment title.
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for appointment title.
     * @param title appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for appointment description.
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for appointment description.
     * @param description appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for appointment location.
     * @return appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for appointment location.
     * @param location appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for appointment meeting type.
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for appointment meeting type.
     * @param type appointment type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Getter for appointment start time.
     * @return appointment start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Setter for appointment start time.
     * @param startTime appointment start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for appointment end time.
     * @return appointment end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Setter for appointment end time.
     * @param endTime appointment end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter for appointment customer id.
     * @return appointment customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for appointment customer id.
     * @param customerID appointment customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for appointment customer name.
     * @return appointment customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for appointment customer name.
     * @param customerName appointment customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for appointment contact name.
     * @return appointment contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for appointment contact name.
     * @param contactName appointment contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for appointment contact id.
     * @return appointment contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for appointment contact id.
     * @param contactID appointment contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    /**
     * Getter for appointment date.
     * @return appointment date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Setter for appointment date.
     * @param date appointment date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }


    /**
     * Getter for appointment user id.
     * @return appointment user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for appointment user id.
     * @param userID appointment user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for appointment month.
     * @return appointment monthh
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Setter for appointment month.
     * @param month appointment month
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Deletes appointment from appointment database table and appointment tableview.
     * @param appointment appointment object
     * @return true
     * @throws SQLException provides information on a database access error
     */
    public static boolean deleteAppointment(Appointments appointment) throws SQLException {
        DBAppointments.deleteAppointment(appointment.getId());
        return true;
    }

    }




