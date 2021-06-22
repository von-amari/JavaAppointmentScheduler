package DBAccess;

import Main.Main;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;


/**
 * Class for appointment database queries. 
 * @author Yvonne King
 */
public class DBAppointments {

    /**
     * Modifies appointment in the appointment table using an update SQL statement. Used in the ModifyAppointmentController class. 
     * @param appt appointment object
     */
    public static void updateAppointment(Appointments appt){

        try{
            String query = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End  =?,  Customer_ID =?, Contact_ID =? WHERE Appointment_ID =?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);

            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());

            ps.setString(5, appt.getDate()+ " " + appt.getStartTime() + ":00");
            ps.setString(6, appt.getDate() + " "+ appt.getEndTime() + ":00");
            ps.setInt(7, appt.getCustomerID());
            ps.setInt(8, appt.getContactID());
            ps.setInt(9, appt.getId());

            ps.executeUpdate();
        }
        catch(SQLException e) {
            //ignored
        }
    }

    /**
     * Removes the appointment from the database using the delete SQL statement. Used in the Appointment deleteAppointment method. 
     * @param appointmentID appointment id
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID ) throws SQLException {

        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);

        ps.setInt(1, appointmentID);

        ps.execute();

    }

    /**
     * Adds appointment to appointment table using an insert SQL statement. Used in the AddAppointmentController class. 
     * @param appointments appointment object
     * @throws SQLException
     */
    public static void addAppointments(Appointments appointments)
            throws SQLException {

        String query = "INSERT INTO appointments (  Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID ) VALUES ( ?, ?, ?, ?, ?, ?,'" +DBUser.userN+ "','" +DBUser.userN+ "', ?, ?, ?)";

        try {
            // set all the preparedstatement parameters
            PreparedStatement st = DBConnection.getConnection().prepareStatement(query);

            st.setString(1, appointments.getTitle());
            st.setString(2, appointments.getDescription());
            st.setString(3, appointments.getLocation());
            st.setString(4, appointments.getType());
            st.setString(5, appointments.getDate()+ " " + appointments.getStartTime() + ":00"); //one hour from showing correctly in table
            st.setString(6, appointments.getDate() + " "+ appointments.getEndTime() + ":00");//storing correctly in db
           // st.setString(7, DBUser.userN);
            st.setInt(7, appointments.getCustomerID());
            st.setInt(8, appointments.getUserID());
            st.setInt(9, appointments.getContactID());

            // execute the preparedstatement insert
            st.executeUpdate();
            st.close();
        }
        catch (SQLException  se)
        {
            // log exception
            throw se;
        }
    }

    /**
     * Selects all appointments from the appointment table using a select SQL statement. Start and end times switched to local time
     * using the Main.getLocalFromUTC function.
     * @return list of all appointments
     */
        public static ObservableList<Appointments> getAllAppointments() {

            ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

            try {
                String sql = "SELECT * from appointments";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                //getting value from database
                while(rs.next()) {
                    int appointmentID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDate date = rs.getDate("Start").toLocalDate();
                    LocalTime startTime = rs.getTime("Start").toLocalTime(); //utc
                    LocalTime endTime = rs.getTime("End").toLocalTime(); //utc
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");

                   // get the contact name from contact id
                    String cnctName = "ID:";
                    for(Contacts cnct : DBContacts.getAllContacts()) {
                        if(contactID == cnct.getId()) {
                            cnctName = (cnct.getName());
                        }
                    }
                    //utc start and end times are switched to local time in the Main.getLocalFromUTC function
                   Appointments appointment = new Appointments(appointmentID, title, description, location, type, date, Main.getLocalFromUTC(startTime, date), Main.getLocalFromUTC(endTime, date), customerID, cnctName, userID);

                    //adding appointment objects to appointment list
                    appointmentList.add(appointment);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return appointmentList;
        }

    /**
     * Gets a list of appointments by month name using the select and where statements. Used in the MainController month combo box to filter appointments by month in the tableview. 
     * @param month month name
     * @return list of appointments by selected month name
     * @throws IOException
     */
    public static ObservableList<Appointments> getMonthAppts(String month) throws IOException {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE MONTHNAME(Start) = '" +month+ "'"; // or place a ?

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            //try this one
            //ResultSet rs = statement.getResultSet();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDate date = rs.getDate("Start").toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointment = new Appointments(appointmentID, title, description, location, type, date,Main.getLocalFromUTC(startTime, date), Main.getLocalFromUTC(endTime, date), customerID, contactID);
                
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Returns all appointments by type. Used in the MonthTypeReportController class. 
     * * @return a list of appointments matching the type and month selected
     */
    public static ObservableList<Appointments> getPlanningType(String selectedMonth, String selectedType) {
        ObservableList<Appointments>planning = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE MONTHNAME(Start) = '" +selectedMonth+ "' AND Type = '" +selectedType+ "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            //getting value from database
            while (rs.next()) {
                Month month = rs.getDate("Start").toLocalDate().getMonth();
                String type = rs.getString("Type");
                Appointments newAppt = new Appointments(month, type);
                planning.add(newAppt);
            }
        }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        return planning;
    }

    /**
     * Gets a list of all appointments by contact. Used in the ContactScheduleReportController class.
     * @param contactID contact id
     * @return list of all appointments for selected contact.
     */
    public static ObservableList<Appointments> getContactSchedule (int contactID) {
        ObservableList<Appointments>contacts= FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Contact_ID= '" +contactID+  "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            //getting value from database
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDate date = rs.getDate("Start").toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                int customerID = rs.getInt("Customer_ID");

                Appointments appt = new Appointments(appointmentID, title, description, type, date, Main.getLocalFromUTC(startTime, date), Main.getLocalFromUTC(endTime, date), customerID);
                contacts.add(appt);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }

    /**
     * Gets all appointments by customer. Used in the CustomerReportController class.
     * @param customerID customer id
     * @return a list of appointments by customer
     */
    public static ObservableList<Appointments> getCustomerReport (int customerID) {
        ObservableList<Appointments> customers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID= '" + customerID + "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            //getting value from database
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDate date = rs.getDate("Start").toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                int contact = rs.getInt("Contact_ID");

                String cnctName = "";
                for (Contacts cnct : DBContacts.getAllContacts()) {
                    if (contact == cnct.getId()) {

                        cnctName = (cnct.getName());
                    }
                }
                Appointments appt = new Appointments(appointmentID, title, description, type, date, Main.getLocalFromUTC(startTime, date), Main.getLocalFromUTC(endTime, date), cnctName);
                customers.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customers;

    }
    }



