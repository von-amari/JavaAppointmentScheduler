package Controller;


import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Main report screen controller class.
 * @author Yvonne King
 */
public class ReportPopUPController {

    /**
     * Sends user back to main reports screen.
     * @param event
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Main.stageAndScene(event, "/view/Main.fxml");
    }

    /**
     * Sends user to the contact schedule report.
     * @param event
     */
    @FXML
    void onActionContact(ActionEvent event) {
        Main.stageAndScene(event, "/view/ContactScheduleReport.fxml");
    }

    /**
     * Sends user to the month and type report.
     * @param event
     */
    @FXML
    void onActionMonthType(ActionEvent event) {
        Main.stageAndScene(event, "/view/MonthTypeReport.fxml");
    }

    /**
     * Sends user to the customer report.
     * @param event
     */
    public void onActionThirdReport(ActionEvent event) {
        Main.stageAndScene(event, "/view/CustomerReport.fxml");
    }
}