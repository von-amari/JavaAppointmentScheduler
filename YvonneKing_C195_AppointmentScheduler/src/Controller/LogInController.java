package Controller;

import DBAccess.DBUser;
import Main.Main;
import com.mysql.cj.exceptions.CJCommunicationsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Logs user in to application with correct credentials.
 * @author Yvonne King
 */
public class LogInController implements Initializable {

    /**
     * Main title label field.
     */
    @FXML
    private Label loginTitleLbl;

    /**
     * User name label field.
     */
    @FXML
    private Label userNameLbl;

    /**
     * Password label field.
     */
    @FXML
    private Label passwordLbl;

    /**
     * Log in button field.
     */
    @FXML
    private Button loginBtn;

    /**
     * User name text area field.
     */
    @FXML
    private TextArea userNameTxt;

    /**
     * Password text area field.
     */
    @FXML
    private TextArea passwordTxt;

    /**
     * Error label field.
     */
    @FXML
    private Label invalidLoginLbl;

    /**
     * Zone ID label field.
     */
    @FXML
    private Label zoneIDLbl;


    ResourceBundle rb;

    /**
     * Initializes the login screen elements. Keys listed are translated in the Resource Bundle Nat in Main package.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.rb = rb;
        System.out.println(Locale.getDefault());
        loginTitleLbl.setText(rb.getString("login"));
        Main.getStage().setTitle(rb.getString("title"));
        userNameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        loginBtn.setText(rb.getString("login"));
        zoneIDLbl.setText(rb.getString("Location") + ZoneId.systemDefault() );
    }

    /**
     * Login validation. Shows alert if user name and password are left blank, or if they are incorrect. Also contains logic for logging login activity.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
     try {

         if (userNameTxt.getText().length() == 0 && passwordTxt.getText().length() == 0) {
             invalidLoginLbl.setText(rb.getString("noUserOrPass"));
             Main.log("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
         }
         else if(userNameTxt.getText().length() == 0) {
             invalidLoginLbl.setText(rb.getString("noUsername"));
             Main.log("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
         }
         else if( passwordTxt.getText().length() == 0) {
             invalidLoginLbl.setText(rb.getString("noPassword"));
             Main.log("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
         }

         else {
             String user = userNameTxt.getText();
             String pass = passwordTxt.getText();
             if(DBUser.validateLogin(user, pass)) {
                 Main.log("Successful log in attempt by: " + DBUser.userN, LocalDate.now().toString(), LocalTime.now().toString());
                 Main.stageAndScene(event, "/view/Main.fxml");
                 Main.getAlert();
             }
             else {
                 invalidLoginLbl.setText(rb.getString("incorrect"));
                 Main.log("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
             }
         }
     }catch (EOFException | CJCommunicationsException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Timed out");
         alert.setContentText("Timed out. Please close and reopen application");
         alert.showAndWait();
     }
    }

}