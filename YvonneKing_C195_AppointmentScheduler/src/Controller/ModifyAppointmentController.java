package Controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUser;
import Main.Main;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;


/**
 *Allows users to modify an appointment. Error labels prompt user to fill in blank text fields, or if there is an overlapping appointment.
 * @author Yvonne King
 */
public class ModifyAppointmentController implements Initializable {

    /**
     * Appointment id text field, disabled.
     */
    @FXML
    private TextArea apptTxt;

    /**
     * Appointment title text area field.
     */
    @FXML
    private TextArea titleTxt;

    /**
     * Appointment description text area field.
     */
    @FXML
    private TextArea descriptionTxt;

    /**
     * Appointment location text area field.
     */
    @FXML
    private TextArea locationTxt;

    /**
     * Appointment type combo box field.
     */
    @FXML
    private ComboBox<String> typeDropDown;

    /**
     * Appointment date picker field.
     */
    @FXML
    private DatePicker apptDatePicker;

    /**
     * Appointment start time combo box field.
     */
    @FXML
    private ComboBox<LocalTime> startTimeDropDown;

    /**
     * Appointment end time combo box field.
     */
    @FXML
    private ComboBox<LocalTime> endTimeDropDown;

    /**
     * Appointment customer id combo box field.
     */
    @FXML
    private ComboBox<Customers> customerIDDropDown;

    /**
     * Appointment contact combo box field.
     */
    @FXML
    private ComboBox<Contacts> contactIDDropDown;

    /**
     * Appointment user combo box field.
     */
    @FXML
    private ComboBox<Users> userDropDown;

    /**
     * Error title label.
     */
    @FXML
    private Label errTitleLbl;

    /**
     * Error description label.
     */
    @FXML
    private Label errDescLbl;

    /**
     * Error location label.
     */
    @FXML
    private Label errLocLbl;

    /**
     * Error type label.
     */
    @FXML
    private Label errTypeLbl;

    /**
     * Error date label.
     */
    @FXML
    private Label errDateLbl;

    /**
     * Error start time label.
     */
    @FXML
    private Label errStartLbl;

    /**
     * Error end time label.
     */
    @FXML
    private Label errEndLbl;

    /**
     * Error customer id label.
     */
    @FXML
    private Label errCustIdLbl;

    /**
     * Error contact label.
     */
    @FXML
    private Label errContactLbl;

    /**
     * Error user label.
     */
    @FXML
    private Label errUserLbl;

    /**
     * Creates a new appointment object.
     */
    Appointments appointment = new Appointments(0, "", "", "", "", null, null, null, 0, 0, 0);

    /**
     * Creates a new customer object.
     */
    Customers newCust = new Customers(0, "", "", "", "", 1, 1);

    /**
     * Creates a new user object.
     */
    Users user = new Users(0, DBUser.userN);

    /**
     * Creates a new contact object.
     */
    Contacts newContact = new Contacts(0, "");

    /**
     * Saves the modified information in the modify form to the database and tableview.
     * @param event
     * @throws InterruptedException
     */
    @FXML
    void onActionSave(ActionEvent event) throws InterruptedException {

        appointment.setID(Integer.parseInt(apptTxt.getText()));

        try {

            if (titleTxt.getText().isEmpty() &&
                    descriptionTxt.getText().isEmpty() &&
                    locationTxt.getText().isEmpty() &&
                    typeDropDown.getValue() == null &&
                    apptDatePicker.getValue() == null &&
                    startTimeDropDown.getValue() == null &&
                    endTimeDropDown.getValue() == null &&
                    customerIDDropDown.getValue() == null &&
                    contactIDDropDown.getValue() == null &&
                    userDropDown.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Please fill in the form before saving");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            //ignore
        }//end catch

        LocalDate apptDate = LocalDate.now();
        LocalTime st = LocalTime.now();
        LocalTime et = LocalTime.now();

        //checking if title is empty ***********************************************************
        if (titleTxt.getText().isEmpty() || titleTxt.getText().equals(" ")) { //error if title is empty
            errTitleLbl.setText("Please enter a title");
            return;
        } else {
            errTitleLbl.setText("");
            appointment.setTitle(titleTxt.getText()); //setting title
        }

        //checking if description is empty *****************************************************
        if (descriptionTxt.getText().isEmpty() || descriptionTxt.getText().equals(" ")) {//error if description is empty
            errDescLbl.setText("Please enter a description");
            return;
        }else {
            errDescLbl.setText("");
            appointment.setDescription(descriptionTxt.getText()); //setting description
        }

        //checking if location is empty *********************************************************
        if (locationTxt.getText().isEmpty() || locationTxt.getText().equals(" ")) {//error if location is empty
            errLocLbl.setText("Please enter a location");
            return;
        }else {
            errLocLbl.setText("");
            appointment.setLocation(locationTxt.getText()); //setting location
        }

        //checking if no selection for type *****************************************************
        if (typeDropDown.getValue() == null) {//error if no type selection is made
            errTypeLbl.setText("Please choose type of meeting");
            return;
        }else {
            errTypeLbl.setText("");
            appointment.setType(typeDropDown.getSelectionModel().getSelectedItem()); //setting type to selected item
        }

        //checking if no date was picked ********************************************************
        if (apptDatePicker.getValue() == null) { //error if no date was selected
            errDateLbl.setText("Please use date picker to choose a date");
            return;
        }
        //checking if no start time was selected ************************************************
        if (startTimeDropDown.getValue() == null) { //error if no start time was selected
            errStartLbl.setText("Please use drop down to select a start time");
            return;
        }

        //checking if no end time was selected **************************************************
        if (endTimeDropDown.getValue() == null) { //error if no end time was selected
            errEndLbl.setText("Please use drop down to select an end time");
            return;
        } else {
            apptDate = LocalDate.of(apptDatePicker.getValue().getYear(), apptDatePicker.getValue().getMonth(), apptDatePicker.getValue().getDayOfMonth());
            st = LocalTime.of(startTimeDropDown.getSelectionModel().getSelectedItem().getHour(), startTimeDropDown.getSelectionModel().getSelectedItem().getMinute());
            et = LocalTime.of(endTimeDropDown.getSelectionModel().getSelectedItem().getHour(), endTimeDropDown.getSelectionModel().getSelectedItem().getMinute());

            //setting up local time for business start and end hours
            LocalTime bizStart = LocalTime.of(8, 0);  //business open time
            LocalTime bizEnd = LocalTime.of(22, 0); // business close time

            //converting selected date/time to LocalDateTime
            LocalDateTime apptDTST = LocalDateTime.of(apptDate, st);//selected start time
            LocalDateTime apptDTET = LocalDateTime.of(apptDate, et); //selected end time

            //converting business hours to LocalDateTime
            LocalDateTime bizDTST = LocalDateTime.of(apptDate, bizStart);
            LocalDateTime bizDTET = LocalDateTime.of(apptDate, bizEnd);

            //setting a variable to the system default zone
            ZoneId systemZoneId = ZoneId.systemDefault();

            //setting variable to eastern time for business hours
            ZoneId eST = ZoneId.of("America/New_York");

            //converting the selected dates, times, and business hours to ZonedDateTime
            ZonedDateTime myZDTST = ZonedDateTime.of(apptDTST, systemZoneId); // system zoned date time appt start time
            ZonedDateTime myZDTET = ZonedDateTime.of(apptDTET, systemZoneId);// system zoned date time appt  end time
            ZonedDateTime easternZDTST = ZonedDateTime.of(bizDTST, eST);//eastern zoned date time for biz start time
            ZonedDateTime easternZDTET = ZonedDateTime.of(bizDTET, eST);//eastern zoned date tme for biz end time

            //getting day of week for no weekend day requirement
            //DayOfWeek day = myZDTST.getDayOfWeek();

            //setting variable for utc zone
            ZoneId utcZoneID = ZoneId.of("UTC");

            //converting ZonedDateTime to UTC
            ZonedDateTime utcZDTST = ZonedDateTime.ofInstant(myZDTST.toInstant(), utcZoneID);
            ZonedDateTime utcZDTET = ZonedDateTime.ofInstant(myZDTET.toInstant(), utcZoneID);

            //checking if date does not fall on weekend
            if (myZDTST.getDayOfWeek() == DayOfWeek.SUNDAY || myZDTST.getDayOfWeek() == DayOfWeek.SATURDAY) {
                errDateLbl.setText("Please choose a non-weekend day");
                return;
            } else {
                errDateLbl.setText("");
                appointment.setDate(apptDatePicker.getValue()); //setting date to selected date
            }

            if(myZDTST.isBefore(easternZDTST))  {
                errStartLbl.setText("Appointment can't be before business hours 8am-10pm EST");
                return;
            } else if(myZDTST.isAfter(easternZDTET)) {
                errStartLbl.setText("Appointment can't be after business hours 8am-10pm EST");
                return;
            } else if (myZDTET.isAfter(easternZDTET)) {
                errEndLbl.setText("Please choose between business hours 8am-10pm EST");
                return;
            }

            if (startTimeDropDown.getValue() == endTimeDropDown.getValue()) {
                errEndLbl.setText("End time can't be the same as start time");
                return;
            } else if (startTimeDropDown.getValue().isAfter(endTimeDropDown.getValue())) {
                errEndLbl.setText("End time can't be before start time");
                return;
            }
            if(startTimeDropDown.getValue() != null && endTimeDropDown.getValue() !=null) {

                //for loop to iterate through appt to get local date time to see if overlapping
                for (Appointments appt : DBAppointments.getAllAppointments()) {
                    LocalDateTime i = LocalDateTime.of(appt.getDate(), appt.getStartTime());
                    ZonedDateTime apptZDTST = ZonedDateTime.of(i, systemZoneId);
                    LocalDateTime j = LocalDateTime.of(appt.getDate(), appt.getEndTime());
                    ZonedDateTime apptZDTET = ZonedDateTime.of(j, systemZoneId);

                    //check for overlap
                    if (myZDTST.equals(apptZDTST) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errStartLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if ((myZDTST.isAfter(apptZDTST) && (myZDTET.isBefore(apptZDTET))) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errStartLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if (myZDTET.equals(apptZDTET) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errStartLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if ((myZDTST.isBefore(apptZDTST) && (myZDTET.isAfter(apptZDTET))) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errEndLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if(((myZDTST.isBefore(apptZDTST)) && (myZDTET.isBefore(apptZDTET)) && (myZDTET.isAfter(apptZDTST))) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errEndLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if((myZDTST.isAfter(apptZDTST) && myZDTST.isBefore(apptZDTET)) &&
                        (customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()) &&
                        (Integer.parseInt(apptTxt.getText()) != appt.getId())) {
                        errEndLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    }  else if(((myZDTST.equals(apptZDTET) && (Integer.parseInt(apptTxt.getText()) != appt.getId()) && customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()))
                            || ((myZDTET.equals(apptZDTST)) && (Integer.parseInt(apptTxt.getText()) != appt.getId()) && customerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCustomerID()))
                       {
                        errEndLbl.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else {
                        errStartLbl.setText("");
                        appointment.setStartTime(utcZDTST.toLocalTime());
                        errEndLbl.setText("");
                        appointment.setEndTime(utcZDTET.toLocalTime());
                    }
                }//end for loop
            } //end else if starttimedropdown
        }//end else if outer

        //checking if no customer was selected **************************************************
        if (customerIDDropDown.getValue() == null) {
            errCustIdLbl.setText("Please use drop down to select a customer");
            return;
        } else {
            errCustIdLbl.setText("");
            newCust.setId(customerIDDropDown.getValue().getId());
            appointment.setCustomerID(newCust.getId());
        }

        //checking if no contact was selected ***************************************************
        if (contactIDDropDown.getValue() == null) {
            errContactLbl.setText("Please use drop down to select a contact");
            return;
        } else {
            errContactLbl.setText("");
            newContact.setId(contactIDDropDown.getValue().getId());
            appointment.setContactID(newContact.getId());
        }
        //checking if no user was selected *******************************************************
        if (userDropDown.getValue() == null) {
            errUserLbl.setText("Please use drop down to select a user");
            return;
        } else {
            errContactLbl.setText("");
            user.setId(userDropDown.getValue().getId());
            appointment.setUserID(user.getId());
        }

        try {
            if (titleTxt.getText().isEmpty() ||
                    descriptionTxt.getText().isEmpty() ||
                    locationTxt.getText().isEmpty() ||
                    typeDropDown.getValue() == null ||
                    apptDatePicker.getValue() == null ||
                    startTimeDropDown.getValue() == null ||
                    endTimeDropDown.getValue() == null ||
                    customerIDDropDown.getValue() == null ||
                    contactIDDropDown.getValue() == null ||
                    userDropDown.getValue() == null   ) {
                    wait();
            }//end if
            else if (!titleTxt.getText().isEmpty() &&
                    !descriptionTxt.getText().isEmpty() &&
                    !locationTxt.getText().isEmpty() &&
                    typeDropDown.getValue() != null &&
                    apptDatePicker.getValue() != null &&
                    startTimeDropDown.getValue() != null &&
                    endTimeDropDown.getValue() != null &&
                    customerIDDropDown.getValue() != null &&
                    contactIDDropDown.getValue() != null &&
                    userDropDown.getValue() != null){
                DBAppointments.updateAppointment(appointment);

            } //else if
        }//end try
        catch (IllegalMonitorStateException e) {
            wait();
        }//end catch
        Main.stageAndScene(event, "/view/Main.fxml");
    }//end onaction save

    /**
     * Cancels the current action and brings user back to the main appointment screen.
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     * Initializes the combo boxes and date picker.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customers> customer = DBCustomers.getAllCustomers();
        customerIDDropDown.setItems(customer);
        customerIDDropDown.setPromptText("Select a customer");

        ObservableList<Contacts> contact = DBContacts.getAllContacts();
        contactIDDropDown.setItems(contact);
        contactIDDropDown.setPromptText("Select a contact");

        ObservableList<Users> users = DBUser.getAllUsers();
        userDropDown.setItems(users);
        //userDropDown.setValue(DBUser.getUserName());
        userDropDown.setPromptText("Select a user");

        typeDropDown.setItems(Main.typeList());
        typeDropDown.setPromptText("Select meeting type");

        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23, 30);
        while(start.isBefore(end.plusSeconds(1))) {
            startTimeDropDown.getItems().add(start);
            start = start.plusMinutes(15);
            endTimeDropDown.getItems().add(start);
        }
        startTimeDropDown.setPromptText("Select start time");
        endTimeDropDown.setPromptText("Select end time");
    }

    /**
     * Fills in the modify appointment form with the selected appointment information.
     * @param appointments appointment object
     * @param start start time
     * @param end end time
     * @param customerID customer id
     * @param contactName contact name
     * @param userID user id
     */
    public void sendAppointment(Appointments appointments, LocalTime start, LocalTime end, int customerID, String contactName, int userID) {

        apptTxt.setText(String.valueOf(appointments.getId()));
        titleTxt.setText(appointments.getTitle());
        descriptionTxt.setText(appointments.getDescription());
        apptDatePicker.setValue(appointments.getDate());
        locationTxt.setText(appointments.getLocation());
        typeDropDown.setValue(appointments.getType());
        startTimeDropDown.setValue(start);
        endTimeDropDown.setValue(end);

        for(Customers cust : customerIDDropDown.getItems()) {
                if(customerID == cust.getId()) {
                    customerIDDropDown.setValue(cust);
                    break;
                }
            }//end customer loop
            for(Contacts contact : contactIDDropDown.getItems()) {
                if(contactName.equals(contact.getName())) {
                    contactIDDropDown.setValue(contact);
                    break;
                }
            } // end contact loop
        for(Users user : userDropDown.getItems()) {
            if(userID == user.getId()) {
                userDropDown.setValue(user);
                break;
            }
        } // end user loop
    } //end send appointment
} //end modify appt controller class
