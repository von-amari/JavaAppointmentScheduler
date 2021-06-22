package Main;

import DBAccess.DBAppointments;
import Model.Appointments;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/* Connection String
http://wgudb.ucertify.com/?ref_id=U07xEh_038Nc_53689160857&db=WJ

Server name: wgudb.ucertify.com
Port: 3306
Database name: WJ07xEh
Username: U07xEh
Password: 53689160857*/


/**
 * Main class for Appointment Scheduler application.
 *
 * <br/> Javadoc folder, README.txt, and login_activity.txt is located at the root folder.
 * @author Yvonne King
 * */
public class Main extends Application {
static Stage stage;

    /**
     * Starting place for all JavaFX _applications.
     * @param stage sets application scene
     */
    @Override
    public void start(Stage stage){

        this.stage = stage;

        Locale.setDefault(Locale.getDefault());
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat");
        Group root = new Group();
        try {

           FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
            loader.setResources(rb);
            Parent roots = loader.load();
            root.getChildren().add(roots);
            stage.setScene(new Scene(root,  450, 500 ));
            stage.setX(100);
            stage.setY(0);
            stage.show();

        } catch(NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starting point of scheduler application. Calls the database start connection method
     * @param args
     * @throws SQLException
     * @throws NullPointerException
     * @throws IOException
     */
    public static void main(String[] args) throws SQLException, NullPointerException, IOException {

            //log in controller
            DBConnection.startConnection();
            Connection conn = DBConnection.getConnection();

            DBQuery.setStatement(conn); // create statement object
            launch(args);

            //close connection
            DBConnection.closeConnection();
        }

    /**
     * Method to change scenes/screens.
     * @param event
     * @param string string of resource path
     * @return string of resource path
     */
    public static String stageAndScene(ActionEvent event, String string){
        Stage stage;
        Parent scene = null;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Main.class.getResource(string));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
        return string;
    }

    /**
     * Method that logs user login activity in the log_activity.txt file.
     * @param inItem successful or unsuccessful string
     * @param inDay local date in string format
     * @param inTime local time in string format
     * @throws IOException if an input or output exception occurred
     */
    public static void log(String inItem, String inDay, String inTime) throws IOException {

        //Filename and item variable
        String filename = "login_activity.txt", item, day, time;

        //create file writer object
        FileWriter fwriter = new FileWriter(filename, true);

        //create and open file
        PrintWriter outputFile = new PrintWriter(fwriter); //use the file writer variable to append

            item = inItem;
            day = inDay;
            time = inTime;
            outputFile.println(item +" "+ day + " " + time);

        //close file
        outputFile.close();
        System.out.println("file written");
    }

    public static Stage getStage() {
        return stage;
    }

    /**
     * Method creates an upcoming appointment alert when user logs in.
     */
    public static void getAlert() {

        Alert alert;
        int counter = 0;
        for(Appointments appt :  DBAppointments.getAllAppointments()) {
            LocalTime st = appt.getStartTime();
            LocalTime ct = LocalTime.now();

            long timeDiff = ChronoUnit.MINUTES.between(ct, st);
            long interval = timeDiff ;

            if (interval > 0 && interval <= 15 && LocalDate.now().equals( appt.getDate())) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setContentText("Appointment ID: " + appt.getId() + " " + "on " + appt.getDate() + " at " + appt.getStartTime());
                alert.showAndWait();
                counter ++;
            }
        }
        if (counter == 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setContentText("No upcoming appointments");
            alert.showAndWait();
        }
    } // end getAlert

    /**
     * Defines the type list for combo boxes.
     * @return the type list
     */
    public static ObservableList<String> typeList () {
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Planning Session");
        type.add("De-Briefing");
        return type;
    }

    /**
     * Get UTC start and end time from DBAppointments.getAllAppointments and return the local time.
     * @param utcT UTC start time in LocalTime
     * @param utcD UTC date in LocalDate
     * @return
     */
    public static LocalTime getLocalFromUTC(LocalTime utcT, LocalDate utcD) {

        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcD,utcT, utcZoneId);
        Instant localToUTCInstant = utcZDT.toInstant();
        ZonedDateTime utcToLocalZDT = localToUTCInstant.atZone(localZoneID);
        LocalTime utcToLocal = utcToLocalZDT.toLocalTime();
        return utcToLocal;
    }

    }


