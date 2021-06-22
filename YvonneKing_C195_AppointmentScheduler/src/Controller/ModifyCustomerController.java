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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Allows user to modify customers and save changed information into the database.  Error labels control blank text fields.
 * @author Yvonne King
 */
public class ModifyCustomerController implements Initializable {

    /**
     * Text area for customer id.
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
     * Create new country object.
     */
    Countries country = new Countries(0, "");

    /**
     *Create new division object.
     */
    Divisions newDiv = new Divisions(0, "", 1);

    /**
     * Create new customer object.
     */
    Customers customer = new Customers(0, "", "", "", "", 1, 1);

    /**
     * Observable list of all divisions.
     */
    ObservableList<Divisions> divisions = DBDivision.getAllDivisions();

    /**
     * Saves the modified customer information to the tableview and customer table.
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event){
        customer.setId(Integer.parseInt(customerIDTxt.getText()));
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
            return;
        } else {
            errNameLbl.setText("");
            customer.setName(nameTxt.getText()); //setting name
        }
        if (addressTxt.getText().isEmpty() || addressTxt.getText().equals(" ")) { //error if address is empty
            errAddressLbl.setText("Please enter an address");
            return;
        } else {
            errAddressLbl.setText("");
            customer.setAddress(addressTxt.getText()); //setting address
        }
        if (postalCodeTxt.getText().isEmpty() || postalCodeTxt.getText().equals(" ")) { //error if postal code is empty
            errPostLbl.setText("Please enter a postal code");
            return;
        } else {
            errPostLbl.setText("");
            customer.setPostalCode(postalCodeTxt.getText()); //setting postal code
        }
        if (phoneNumberTxt.getText().isEmpty() || phoneNumberTxt.getText().equals(" ")) { //error if phone number is empty
            errPhoneLbl.setText("Please enter a phone number");
            return;
        } else {
            errPhoneLbl.setText("");
            customer.setPhoneNumber(phoneNumberTxt.getText()); //setting phone number
        }
        if (countryDropDown.getValue() == null) { //error if country is null
            errCountryLbl.setText("Please select a country");
            return;
        } else {
            country.setId(countryDropDown.getValue().getId());
            errCountryLbl.setText("");
            customer.setCountry(country.getId()); //setting country
        }
        if (divisionDropDown.getValue() == null) { //error if division is null
            errDivLbl.setText("Please select a division");
            return;
        } else {
            newDiv.setId(divisionDropDown.getValue().getId());
            errDivLbl.setText("");
            customer.setDivision(newDiv.getId());//setting division
        }

        try {
            if (nameTxt.getText().isEmpty() ||
                    addressTxt.getText().isEmpty() ||
                    postalCodeTxt.getText().isEmpty() ||
                    phoneNumberTxt.getText().isEmpty() ||
                    countryDropDown.getValue() == null ||
                    divisionDropDown.getValue() == null) {
                wait();
            }else if (!nameTxt.getText().isEmpty() &&
                    !addressTxt.getText().isEmpty() &&
                    !postalCodeTxt.getText().isEmpty() &&
                    !phoneNumberTxt.getText().isEmpty() &&
                    countryDropDown.getValue() != null &&
                    divisionDropDown.getValue() != null) {
                DBCustomers.updateCustomer(customer);
                System.out.println(customer);
                System.out.println(DBCustomers.getAllCustomers());
                Main.stageAndScene(event, "/view/CustomerView.fxml");
            }
        }catch(IllegalMonitorStateException | InterruptedException e) {
            //ignore
        }//end catch
    }

    /**
     * Cancels current action and sends user back to the customer view table.
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
        countryDropDown.setPromptText("Select a country");

        ObservableList<Divisions> division = DBDivision.getAllDivisions();
        divisionDropDown.setItems(division);
        divisionDropDown.setPromptText("Select a division");
        }

    /**
     * Sends the customer information from the customer view to the modify customer screen. Used in the Main Controller.
     * @param customer customer object
     * @param countryID country id
     * @param divisionID division id
     */
    public void sendCustomer(Customers customer, int countryID, int divisionID ) {

        for(Countries ctry : countryDropDown.getItems()) {
            if(countryID == ctry.getId()) {
                countryDropDown.setValue(ctry);
                break;
            }
        }

        for(Divisions div :divisionDropDown.getItems()) {
            if(divisionID == div.getId()) {
                divisionDropDown.setValue(div);
                break;
            }
        }

        customerIDTxt.setText(String.valueOf(customer.getId()));
        nameTxt.setText(customer.getName());
        addressTxt.setText(customer.getAddress());
        postalCodeTxt.setText(customer.getPostalCode());
        phoneNumberTxt.setText(customer.getPhoneNumber());

    }

    /**
     * Selecting a country filters division combo box.
     * <b>Lambda Expression uses selected customer country to set the filtered divisions for the country.</b> This improved code
     * by allowing the filter to occur at the onAction method instead of creating three separate methods in DBAppointments, each filtering
     * for one country, then running each method at the onAction.
     */
    @FXML
    void onCountryDropDownAction() {
        FilteredList<Divisions> filteredDiv = new FilteredList<>(divisions);
        filteredDiv.setPredicate(row->  {
            int newC = countryDropDown.getSelectionModel().getSelectedItem().getId();
            int newCD = row.getCountry(); //get country of the division
        return newC == newCD;
        });
        divisionDropDown.setItems(filteredDiv);
    }
    @FXML
    void onDivisionDropDownAction() {
    }
}
