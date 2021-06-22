package Controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Main.Main;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Contact schedule report controller class. Users can select contact from a dropdown to get the contact's list of appointments.
 */
public class ContactScheduleReportController implements Initializable {

    /**
     * Appointment tableview field.
     */
    @FXML
    private TableView<Appointments> contactScheduleTable;

    /**
     * Contacts combo box field.
     */
    @FXML
    private ComboBox<Contacts> contactComboBox;

    /**
     * Appointment id column field.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

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
     * Appointment type column field.
     */
    @FXML
    private TableColumn<Appointments, String> typeCol;

    /**
     * Appointment date column field.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> dateCol;

    /**
     * Appointment start time column field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> startCol;

    /**
     * Appointment end time column field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> endCol;

    /**
     * Appointment customer id column field.
     */
    @FXML
    private TableColumn<Appointments, Integer> custIDCol;

    /**
     * Observable list for all contacts used to set contacts combo box.
     */
    ObservableList<Contacts> allContacts = DBContacts.getAllContacts();

    /**
     * Brings the user back to the main report screen.
     * @param event
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Main.stageAndScene(event, "/view/ReportPopUP.fxml");
    }

    /**
     *Allows user to select a contact from combo box.
     */
    @FXML
    void onActionContactDropDown() {
        this.contactScheduleTable.setItems(DBAppointments.getContactSchedule(contactComboBox.getSelectionModel().getSelectedItem().getId()));
    }

    /**
     * Brings user back to the main appointment screen.
     * @param event
     */
    @FXML
    void onActionHome(ActionEvent event) {
        Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     *Initializes the appointment table columns, but no appointments will show until a contact is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactComboBox.setItems(allContacts);
        this.apptIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        this.typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        this.startCol.setCellValueFactory(new PropertyValueFactory("startTime"));
        this.endCol.setCellValueFactory(new PropertyValueFactory("endTime"));
        this.custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }
}
