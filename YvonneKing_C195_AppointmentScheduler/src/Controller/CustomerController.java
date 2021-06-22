package Controller;

import DBAccess.DBCustomers;
import Main.Main;
import Model.Customers;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer tableview controller. Allows user to view customers, and navigate to add, modify or delete customers.
 */
public class CustomerController implements Initializable {

    /**
     * Customer tableview field.
     */
    @FXML
    private TableView<Customers> customerTable;

    /**
     * Customer id column field.
     */
    @FXML
    private TableColumn<Customers, Integer> customerIdCol;

    /**
     * Customer name column field.
     */
    @FXML
    private TableColumn<Customers, String> customerNameCol;

    /**
     * Customer address column field.
     */
    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    /**
     * Customer postal code column field.
     */
    @FXML
    private TableColumn<Customers, String> customerPostalCodeCol;

    /**
     * Customer phone number column field.
     */
    @FXML
    private TableColumn<Customers, String> customerPhoneCol;

    /**
     * Customer country id column field.
     */
    @FXML
    private TableColumn<Customers, String> customerCountryCol;

    /**
     * Customer division column field.
     */
    @FXML
    private TableColumn<Customers, String> customerDivisionCol;

    /**
     * Brings customer to the add customer form.
     * @param event
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) {
        Main.stageAndScene(event, "/view/AddCustomer.fxml");
    }

    /**
     * Deletes customers from the customer tableview and customer database. Shows an alert to confirm customer deletion, and when no customer is selected.
     * @param event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        Alert alert;

        try{
            if (this.customerTable.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Selection Made");
                alert.setContentText("Please select a customer to delete");
                alert.showAndWait();
            } else if (this.customerTable.getSelectionModel().getSelectedItem() != null) {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Customers cust = this.customerTable.getSelectionModel().getSelectedItem();
                    Customers.deleteCustomer(cust);
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Customer deleted");
                    alert.setContentText("Customer: " + cust.getName() + " was deleted.");
                    alert.showAndWait();
                }
            }
        } catch(SQLException throwables){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't Delete Customer");
            alert.setContentText("Please delete customer appointments before removing customer.");
            alert.showAndWait();
        }
        Main.stageAndScene(event, "/view/CustomerView.fxml");
    }

    /**
     * Brings user to main appointment screen.
     * @param event
     */
    @FXML
    void onActionGoHome(ActionEvent event) {
    Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     * Brings user to modify customer screen. Uses the sendCustomer method from Modify Customer Controller to send the selected customer information.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        if (this.customerTable.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setContentText("No selection made. Select a customer to modify.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();
            ModifyCustomerController modifyCustomerController = (ModifyCustomerController) loader.getController();
            modifyCustomerController.sendCustomer(this.customerTable.getSelectionModel().getSelectedItem(), this.customerTable.getSelectionModel().getSelectedItem().getCountry(), this.customerTable.getSelectionModel().getSelectedItem().getDivision());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = (Parent) loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("table select " + this.customerTable.getSelectionModel().getSelectedItem());
            System.out.println("countryid" + this.customerTable.getSelectionModel().getSelectedItem().getCountry());
            System.out.println("divisionid " + this.customerTable.getSelectionModel().getSelectedItem().getDivision());
        }
    }

    /**
     * Initializes the customer table with customer information.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.customerTable.setItems(DBCustomers.getAllCustomers());

        this.customerIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.customerNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.customerAddressCol.setCellValueFactory(new PropertyValueFactory("address"));
        this.customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory("postalCode"));
        this.customerPhoneCol.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        this.customerCountryCol.setCellValueFactory(new PropertyValueFactory("country"));
        this.customerDivisionCol.setCellValueFactory(new PropertyValueFactory("division"));
    }

}
