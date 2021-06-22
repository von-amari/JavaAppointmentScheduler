package Controller;

import DBAccess.DBAppointments;
import Main.Main;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ResourceBundle;

/**
 * Month and type report controller class.
 * @author Yvonne King
 */
public class MonthTypeReportController implements Initializable {


    private static final DateFormatSymbols dfs = new DateFormatSymbols();

    /**
     * Month drop down.
     */
    @FXML
    private ComboBox<String> monthComboBox;

    /**
     * Type drop down.
     */
    @FXML
    private ComboBox<String> typeComboBox;

    /**
     * Text field for count.
     */
    @FXML
    private TextField countTxt;

    /**
     *Back button event sends user back to main reports screen.
     * @param event
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Main.stageAndScene(event, "/view/ReportPopUP.fxml");
    }

    /**
     * Home button event sends user back to main appointments screen.
     * @param event
     */
    @FXML
    void onActionHome(ActionEvent event) {
        Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     *Iterates through each appointment, matching month and type and setting the count text box.
     */
    @FXML
    void onActionMonthDropDown() {
        countTxt.setText(String.valueOf(0));
        int planningSession = 0;
        for(Appointments ignored :  DBAppointments.getPlanningType(monthComboBox.getValue(), typeComboBox.getValue())) {
            planningSession++;
            countTxt.setText(String.valueOf(planningSession));
        }
    }

    /**
     *Iterates through each appointment, matching month and type and setting the count text box.
     */
    @FXML
    void onActionTypeDropDown() {
        countTxt.setText(String.valueOf(0));
        int planningSession = 0;
        //iterate through each appointment
        for(Appointments ignored :  DBAppointments.getPlanningType(monthComboBox.getValue(), typeComboBox.getValue())) {
            planningSession ++;
            countTxt.setText(String.valueOf(planningSession));
        }
    }

    /**
     * Initializes the month and type combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] testMonths = dfs.getMonths();
        String[] testDays = dfs.getWeekdays();
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll(testMonths);
        ObservableList<String> weekDays = FXCollections.observableArrayList();
        weekDays.addAll(testDays);

        monthComboBox.setItems(months);
        monthComboBox.setPromptText("Select month");
        typeComboBox.setItems(Main.typeList());
        typeComboBox.setPromptText("Select type");
        countTxt.setText("0");
    }
}
