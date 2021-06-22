
package Controller;

import DBAccess.DBAppointments;
import Main.Main;
import Model.Appointments;
import Model.Users;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the appointment tableview in the main screen.
 * @author Yvonne King
 */
public class MainController implements Initializable {

    /**
     * Appointment tableview field.
     */
    @FXML
    private TableView<Appointments> dateTable;

    /**
     * Appointment id column field.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIdCol;

    /**
     * Appointment title column field.
     */
    @FXML
    private TableColumn<Appointments, String> titleCol;

    /**
     * Appointment description column field.
     */
    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    /**
     * Appointment location column field.
     */
    @FXML
    private TableColumn<Appointments, String> locationCol;

    /**
     * Appointment type column field.
     */
    @FXML
    private TableColumn<Appointments, String> typeCol;

    /**
     * Appointment start time column field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> startTimeCol;

    /**
     * Appointment end time column field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> endTimeCol;

    /**
     * Appointment customer id column field.
     */
    @FXML
    private TableColumn<Appointments, String> custIdCol;

    /**
     * Appointment contact name column field.
     */
    @FXML
    private TableColumn<Appointments, String> contactCol;

    /**
     * Appointment date column field.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> dateCol;

    /**
     * Appointment user column field.
     */
    @FXML
    private TableColumn<Users, Integer> userCol;

    /**
     * Month combo box field.
     */
    @FXML
    private ComboBox<String> monthApptDropDown;

    /**
     * Week radio button field.
     */
    @FXML
    private RadioButton weekRadioBtn;

    /**
     * All appointment radio button field.
     */
    @FXML
    private RadioButton allRadioBtn;

    /**
     * Used to get month and week day names.
     * Researched here: https://memorynotfound.com/java-get-list-month-names-locale/
     */
    private static final DateFormatSymbols dfs = new DateFormatSymbols();

    /**
     * Initializes main appointment table. 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.dateTable.setItems(DBAppointments.getAllAppointments());

        this.apptIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        this.locationCol.setCellValueFactory(new PropertyValueFactory("location"));
        this.typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        this.startTimeCol.setCellValueFactory(new PropertyValueFactory("startTime"));
        this.endTimeCol.setCellValueFactory(new PropertyValueFactory("endTime"));
        this.custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        this.contactCol.setCellValueFactory(new PropertyValueFactory("contactName"));
        this.userCol.setCellValueFactory(new PropertyValueFactory("userID"));

        String[] testMonths = dfs.getMonths();
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll(testMonths);
        monthApptDropDown.setItems(months);
        allRadioBtn.setSelected(true);
    }//end initialize

    /**
     * Brings user to customer view screen.
     * @param event
     */
    @FXML
    void onActionCustomerView(ActionEvent event) {
        Main.stageAndScene(event, "/view/CustomerView.fxml");
    }//end customer view

    /**
     * Logs user out and bring user back to login screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        loader.setResources(rb);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }//end log out

    /**
     * Deletes appointment from table and database. Shows an alert confirming delete action, and an error alert when no selection has been made.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) throws SQLException {

        Alert alert;
        if (this.dateTable.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection Made");
            alert.setContentText("Please select an appointment to cancel");

            alert.showAndWait();
        } else if (this.dateTable.getSelectionModel().getSelectedItem() != null) {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setContentText("Are you sure you want to cancel this appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Appointments appt = this.dateTable.getSelectionModel().getSelectedItem();
                Appointments.deleteAppointment(appt);
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment deleted");
                alert.setContentText("ID: " + appt.getId() + " " + appt.getType() + " appointment was cancelled");
                alert.showAndWait();

            }//end if result.get
        }//end else if
        Main.stageAndScene(event, "/view/Main.fxml");
    }//end delete appt

    /**
     * Brings user to add appointment form.
     * @param event
     */
    @FXML
    void onActionAddAppt(ActionEvent event) {
        Main.stageAndScene(event, "/view/AddAppointment.fxml");
    }//end add appt

    /**
     * Brings user to modify appointment screen. The onActionModAppt method uses the Modify Appointment Controller
     * sendAppointment method to send the selected appointment information to the modify screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModAppt(ActionEvent event) throws IOException {

        if(this.dateTable.getSelectionModel().getSelectedItem() == null) {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("No selection");
        alert.setContentText("No selection made. Select an appointment to modify.");
        alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();
            ModifyAppointmentController modifyAppointmentController = loader.getController();
            modifyAppointmentController.sendAppointment(

                    this.dateTable.getSelectionModel().getSelectedItem(),
                    this.dateTable.getSelectionModel().getSelectedItem().getStartTime(),
                    this.dateTable.getSelectionModel().getSelectedItem().getEndTime(),
                    this.dateTable.getSelectionModel().getSelectedItem().getCustomerID(),
                    this.dateTable.getSelectionModel().getSelectedItem().getContactName(),
                    this.dateTable.getSelectionModel().getSelectedItem().getUserID());

            System.out.println(this.dateTable.getSelectionModel().getSelectedItem().getStartTime());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }//end else
    }//end mod appt

    /**
     * Displays all appointments in main screen tableview. This button is selected as default.
     */
    @FXML
    void onActionAllDisplay() {
        monthApptDropDown.setValue("");
        allRadioBtn.setSelected(true);
        dateTable.setItems(DBAppointments.getAllAppointments());
    }//end all appt dispolay

    /**
     * Displays appointment by selected month.
     * @throws IOException
     */
    @FXML
    void onActionMonthDisplay() throws IOException {
        allRadioBtn.setSelected(false);
        weekRadioBtn.setSelected(false);
        dateTable.setItems(DBAppointments.getMonthAppts(monthApptDropDown.getValue()));
    }//end month display

    /**
     * Brings user to main report screen.
     * @param event
     */
    @FXML
    void onActionReports(ActionEvent event) {
        Main.stageAndScene(event, "/view/ReportPopUP.fxml");
    }//end report

    /**
     * Populates table with appointments for next seven days. Appointments will show if the they fall within the seven day (168 hour) window at the time of viewing.
     * <b>Lambda expression in this method filters the appointment table by week.</b> <br>
     *     This expression improved code by allowing the filter to occur in this onActionWeekDisplay method,
     *     rather than creating a separate method in the DBAppointments with a query, and calling it in the onAction method.
     *     How to convert date to ObservableValue found here : <b><a href="https://stackoverflow.com/questions/14413040/converting-integer-to-observablevalueinteger-in-javafx">Stack Overflow</a> </b>
     */
    @FXML
    void onActionWeekDisplay() {
        monthApptDropDown.setValue("");
        weekRadioBtn.setSelected(true);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisWeek = today.plusDays(7);
        FilteredList<Appointments> week = new FilteredList<>(DBAppointments.getAllAppointments());
        week.setPredicate(row -> {
            ObservableValue<LocalDateTime> idk = new ReadOnlyObjectWrapper<>(LocalDateTime.of(row.getDate(), row.getStartTime()));
            LocalDateTime rowDate =idk.getValue();
            return  rowDate.isAfter(today) && rowDate.isBefore(thisWeek);
        });
        dateTable.setItems(week);
    }//end week display

    }//end main controller class





