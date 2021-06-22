package Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivision;
import Main.Main;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Allows user to add customers to database. Error labels control blank text fields.
 * @author Yvonne King
 */
public class AddCustomerController implements Initializable {

    /**
     * Text area for customer id, disabled.
     */
    @FXML
    private TextArea customerIDTxt;

    /**
     * Text area for customer name.
     */
    @FXML
    private TextArea nameTxt;

    /**
     * Text area for customer address.
     */
    @FXML
    private TextArea addressTxt;

    /**
     * Text area for postal code.
     */
    @FXML
    private TextArea postalCodeTxt;

    /**
     * Text area for phone number.
     */
    @FXML
    private TextArea phoneNumberTxt;

    /**
     * Combo box for countries.
     */
    @FXML
    private ComboBox<Countries> countryDropDown;

    /**
     * Combo box for divisions.
     */
    @FXML
    private ComboBox<Divisions> divisionDropDown;

    /**
     * Error label for customer name.
     */
    @FXML
    private Label errNameLbl;

    /**
     * Error label for customer address.
     */
    @FXML
    private Label errAddressLbl;

    /**
     * Error label for customer postal code.
     */
    @FXML
    private Label errPostLbl;

    /**
     * Error label for customer phone number.
     */
    @FXML
    private Label errPhoneLbl;

    /**
     * Error label for customer country.
     */
    @FXML
    private Label errCountryLbl;

    /**
     * Error label for customer division.
     */
    @FXML
    private Label errDivLbl;


    /**
     * Create new division object.
     */
    Divisions newDiv = new Divisions(0, "", 1);

    /**
     * Create new country object.
     */
    Countries country = new Countries(0, "");

    /**
     * Create new customer object.
     */
    Customers customer = new Customers(0, "", "", "", "", 1, 1);

    /**
     * Observable list with all divisions.
     */
    ObservableList<Divisions> divisions = DBDivision.getAllDivisions();


    /**
     * Saves the added cusetomer information to the tableview and database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException {

        customer.setId(10);

        try{

            if (nameTxt.getText().isEmpty() &&
                    addressTxt.getText().isEmpty() &&
                    postalCodeTxt.getText().isEmpty() &&
                    phoneNumberTxt.getText().isEmpty() &&
                    divisionDropDown.getValue() == null &&
                    countryDropDown.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Please fill in the form before saving");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            //ignore
        }//end catch

        if (nameTxt.getText().isEmpty() || nameTxt.getText().equals(" ")) { //error if name is empty
            errNameLbl.setText("Please enter a name");
        } else {
            errNameLbl.setText("");
            customer.setName(nameTxt.getText()); //setting name
        }
        if (addressTxt.getText().isEmpty() || addressTxt.getText().equals(" ")) { //error if address is empty
            errAddressLbl.setText("Please enter an address");
        } else {
            errAddressLbl.setText("");
            customer.setAddress(addressTxt.getText()); //setting address
        }
        if (postalCodeTxt.getText().isEmpty() || postalCodeTxt.getText().equals(" ")) { //error if postal code is empty
            errPostLbl.setText("Please enter a postal code");
        } else {
            errPostLbl.setText("");
            customer.setPostalCode(postalCodeTxt.getText()); //setting postal code
        }
        if (phoneNumberTxt.getText().isEmpty() || phoneNumberTxt.getText().equals(" ")) { //error if phone number is empty
            errPhoneLbl.setText("Please enter a phone number");
        } else {
            errPhoneLbl.setText("");
            customer.setPhoneNumber(phoneNumberTxt.getText()); //setting phone number
        }
        if (countryDropDown.getValue() == null) { //error if country is null
            errCountryLbl.setText("Please select a country");
        } else {
            country.setId(countryDropDown.getValue().getId());
            errCountryLbl.setText("");
            customer.setCountry(country.getId()); //setting country
        }
        if (divisionDropDown.getValue() == null) { //error if division is null
            errDivLbl.setText("Please select a division");
        } else {
            newDiv.setId(divisionDropDown.getValue().getId());
            errDivLbl.setText("");
            customer.setDivision(newDiv.getId());//setting division
        }

        try {
            if (nameTxt.getText().isEmpty() ||
                    addressTxt.getText().isEmpty() ||
                    postalCodeTxt.getText().isEmpty() ||
                    postalCodeTxt.getText().isEmpty() ||
                    countryDropDown.getValue() == null) {
                wait();
            }else if (!nameTxt.getText().isEmpty() &&
                    !addressTxt.getText().isEmpty() &&
                    !postalCodeTxt.getText().isEmpty() &&
                    !phoneNumberTxt.getText().isEmpty() &&
                    countryDropDown.getValue() != null &&
                    divisionDropDown.getValue() != null) {
                DBCustomers.addCustomer(customer);
                System.out.println(customer);
                System.out.println(DBCustomers.getAllCustomers());
                Main.stageAndScene(event, "/view/CustomerView.fxml");
            }
            }catch(IllegalMonitorStateException | InterruptedException e) {
            //ignore
        }//end catch
    }//end onaction save

    /**
     * Cancels the current action and brings user to customer tableview.
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        Main.stageAndScene(event, "/view/CustomerView.fxml");
    }

    /**
     * Initializes the country and division combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> country = DBCountries.getAllCountries();
        countryDropDown.setItems(country);
        countryDropDown.setPromptText("Select Country");

        ObservableList<Divisions> division = DBDivision.getAllDivisions();
        divisionDropDown.setItems(division);
        divisionDropDown.setPromptText("Select First Level Division");
    }

    /**
     * Selecting a country filters division combo box.
     *  <b>Lambda Expression uses selected customer country to set the filtered divisions for the country.</b> This improved code
     *  by allowing the filter to occur at the onAction method instead of creating three separate methods in DBAppointments, each filtering
     *  for one country, then running each method at the onAction.
     */
    @FXML
    void onCountryDropDownAction() {
        FilteredList<Divisions> filteredDiv = new FilteredList<>(divisions);
        filteredDiv.setPredicate(row->  {
            int newC = countryDropDown.getSelectionModel().getSelectedItem().getId();
            int newCD = row.getCountry(); //get country of the division
            return (newC == newCD);
        });
        divisionDropDown.setItems(filteredDiv);
       }
    @FXML
    void onDivisionDropDownAction() {
        }
}



