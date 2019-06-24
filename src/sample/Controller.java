package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Controller {

    ObservableList<String> lengths = FXCollections.observableArrayList("week/s", "month/s", "year/s");

    /**
     * Initial amount to calculate interest on.
     */
    @FXML
    private TextField amount;

    /**
     * final amount and instructions label.
     */
    @FXML
    private Label finalAmount, instructions;

    /**
     * Interest to be applied to amount.
     */
    @FXML
    private TextField interest;

    /**
     * The time fraction to calculate interest on, week/s, month/s, year/s.
     */
    @FXML
    private ChoiceBox timeLength;

    /**
     * The amount of fractions to calculate interest on. e.g. 2 x weeks
     */
    @FXML
    private TextField amountOfTime;

    /**
     * Submit button for calculation.
     */
    @FXML
    private Button submit;

    /**
     * set array for choice box and assigned present value on load.
     * disables submit button until amount entered.
     */
    @FXML
    private void initialize() {
        timeLength.setItems(lengths);
        timeLength.setValue(lengths.get(0));
        submit.setDisable(true);
    }

    /**
     * When amount entered submit becomes available.
     */
    @FXML
    public void handleRelease() {
        String text = amount.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        submit.setDisable(disableButtons);
    }

    /**
     * Take entered figures and calculate amount of interest earned
     * This only calculates based on p/a interest with no compounding.
     *
     * @param event The submit button being pressed.
     */
    @FXML
    public void buttonPress(Event event) {
        try {
            double amountInt = Double.parseDouble(amount.getText());
            double interestInt = Double.parseDouble(interest.getText()) / 100;
            double amountOfTimeInt = Double.parseDouble(amountOfTime.getText());
            int timeLengthInt = 0;
            switch (timeLength.getValue().toString()) {
                case "month/s":
                    timeLengthInt = 12;
                    break;
                case "week/s":
                    timeLengthInt = 52;
                    break;
                default:
                    timeLengthInt = 1;
                    break;
            }
            double finalInterest = ((amountInt * interestInt) / timeLengthInt) * amountOfTimeInt;
            String s = String.format("%.2f", finalInterest);
            finalAmount.setText(s);
        } catch (NumberFormatException nfe) {
            instructions.setText("Please complete all fields!");
        }
    }
}
