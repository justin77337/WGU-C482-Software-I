package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddPart Controller Class
 * I ran into Logical Error. I accidentally passed wrong argument "max" instead of "min" vice versa in Inhouse Constructor.
 * It can be found in onClickSavePart Method.
 * @author Justonna Naing
 */
public class AddPart implements Initializable {
    public Label addPartTextField;
    public TextField partNameField;
    public TextField partPriceField;
    public TextField partInventoryField;
    public TextField partMaxField;
    public TextField partMinField;
    public TextField partIDField;
    public RadioButton inHouseRadioIcon;
    public RadioButton outSourcedRadioIcon;
    public TextField addPartDataField;

    /**
     * Initialize the Controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am AddPart!");
    }

    /**
     * The Add Part Form closes, and the application returns to the MainScreen
     * @param actionEvent cancel button action
     * @throws IOException
     */
    public void onClickCancelPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1110, 600);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Set Text Label to "Machine ID"
     * @param actionEvent InHouse Radio Button
     */
    public void onClickInHouseRadio(ActionEvent actionEvent) {
        addPartTextField.setText("Machine ID");
    }

    /**
     * Set Text Label to "Company Name"
     * @param actionEvent OutSourced Radio Button
     */
    public void onClickOutSourcedRadio(ActionEvent actionEvent) {
        addPartTextField.setText("Company Name");
    }

    /**
     * Add new part and Return to Main Screen after validating and logic are checked.
     *
     * Logical Error
     * I ran into Logical Error. I accidentally passed wrong argument "max" instead of "min" vice versa in Inhouse Constructor.
     * @param actionEvent Save button action
     * @throws IOException
     */
    public void onClickSavePart(ActionEvent actionEvent) throws IOException {
        /*Test SaveButton With Predefined Data*/
        //InHouse inHousePart1 = new InHouse(1,"Bicycle_Part",799.99,12,1,5,111);
        //Inventory.addPart(inHousePart1);

        ObservableList<Part> parts = Inventory.getAllParts();
        int id = 1000+parts.size();
        partIDField = new TextField(String.valueOf(++id));


        String name = partNameField.getText();
        String price = partPriceField.getText();
        String inventory = partInventoryField.getText();
        String max = partMaxField.getText();
        String min = partMinField.getText();
        String machineID = addPartDataField.getText();

        boolean passed = false;
        try {

            boolean isinventoryvalid = true;
            boolean isminvalid = true;

            if(Integer.parseInt(min) < 0 || Integer.parseInt(max) < 0 || Integer.parseInt(inventory) < 0 || Double.parseDouble(price) < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Form contains Invalid data!");
                alert.setContentText("Negative number is unacceptable!");
                alert.showAndWait();
            }

            if (Integer.parseInt(min) > 0 && Integer.parseInt(max) > 0 && Integer.parseInt(inventory) > 0 && Double.parseDouble(price) > 0) {
                if (Integer.parseInt(min) >= Integer.parseInt(max) || Integer.parseInt(min) <= 0) {
                    isminvalid = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Form contains Invalid data!");
                    alert.setContentText("Min cannot be greater than Max.");
                    alert.showAndWait();
                } else if (Integer.parseInt(inventory) > Integer.parseInt(max) || Integer.parseInt(inventory) < Integer.parseInt(min)) {
                    isinventoryvalid = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Form contains Invalid data!");
                    alert.setContentText("Inventory must be between Min and Max.");
                    alert.showAndWait();
                }
            } else {
                isminvalid = false;
                isinventoryvalid = false;
            }

            if (isminvalid && isinventoryvalid) {
                if (inHouseRadioIcon.isSelected()) {
                    InHouse inHousePart = new InHouse(
                            Integer.parseInt(partIDField.getText())
                            , name
                            , Double.parseDouble(price)
                            , Integer.parseInt(inventory)
                            , Integer.parseInt(min)  /* Logical Error -- accidentally passed max instead of min */
                            , Integer.parseInt(max)  /* Logical Error  -- accidentally passed min instead of max */
                            , Integer.parseInt(machineID));
                    Inventory.addPart(inHousePart);
                    passed = true;
                }
                if (outSourcedRadioIcon.isSelected()) {
                    Outsourced outsourced = new Outsourced(
                            Integer.parseInt(partIDField.getText())
                            , partNameField.getText()
                            , Double.parseDouble(partPriceField.getText())
                            , Integer.parseInt(partInventoryField.getText())
                            , Integer.parseInt(partMinField.getText())
                            , Integer.parseInt(partMaxField.getText())
                            , addPartDataField.getText());
                    Inventory.addPart(outsourced);
                    passed = true;
                }
            }
            } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Error adding part!");
                alert.setContentText("Form contains blank fields or Invalid data.");
                alert.showAndWait();
            }

        if (passed) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1110, 600);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }
}
