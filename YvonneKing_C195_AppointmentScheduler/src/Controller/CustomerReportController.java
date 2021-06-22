package Controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Main.Main;
import Model.Appointments;
import Model.Customers;
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
 * Customer report controller class. This report allows the user to select a customer in the drop down box and show all appointments for that customer.
 */
public class CustomerReportController implements Initializable {

    /**
     * All customers combo box field.
     */
    @FXML
    private ComboBox<Customers> customerComboBox;

    /**
     * Customer table field.
     */
    @FXML
    private TableView<Appointments> customerTable;

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
     * Appointment contact name column field.
     */
    @FXML
    private TableColumn<Appointments, String> contactCol;


    /**
     * List of all customers to use for customer combo box.
     */
    ObservableList<Customers>allCustomers = DBCustomers.getAllCustomers();

    /**
     * Brings user back to the main report screen.
     * @param event
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Main.stageAndScene(event, "/view/ReportPopUP.fxml");
    }

    /**
     * Sets the appointment tableview to show all appointments for the selected customer.
     */
    @FXML
    void onActionCustomerDropDown() {
    this.customerTable.setItems(DBAppointments.getCustomerReport(customerComboBox.getSelectionModel().getSelectedItem().getId()));
    }

    /**
     * Brings user to main appointment screen.
     * @param event
     */
    @FXML
    void onActionHome(ActionEvent event) {
        Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     * Initializes the appointment table columns, but no appointments will show until a customer is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(allCustomers);
        this.apptIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        this.typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        this.startCol.setCellValueFactory(new PropertyValueFactory("startTime"));
        this.endCol.setCellValueFactory(new PropertyValueFactory("endTime"));
        this.contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

    }
}
