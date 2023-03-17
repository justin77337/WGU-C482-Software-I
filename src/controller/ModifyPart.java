package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyPart Controller Class
 * @author Justonna Naing
 */
public class ModifyPart implements Initializable {
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

    private Part selectedPart;

    /**
     * Initialize the Controller
     * Populate the text fields with selected part data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am Modify Part!");
        selectedPart =  MainScreen.partmodify();

        if (selectedPart instanceof InHouse) {
            inHouseRadioIcon.setSelected(true);
            addPartTextField.setText("Machine ID");
            addPartDataField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            outSourcedRadioIcon.setSelected(true);
            addPartTextField.setText("Company Name");
            addPartDataField.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partIDField.setText(String.valueOf(selectedPart.getId()));
        partNameField.setText(selectedPart.getName());
        partInventoryField.setText(String.valueOf(selectedPart.getStock()));
        partPriceField.setText(String.valueOf(selectedPart.getPrice()));
        partMaxField.setText(String.valueOf(selectedPart.getMax()));
        partMinField.setText(String.valueOf(selectedPart.getMin()));

    }

    /**
     * Modify Part Form closes, and the application returns to the MainScreen
     * @param actionEvent
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
     * set Text Label to "Machine ID"
     * @param actionEvent Inhouse Radio Button
     */
    public void onClickInHouseRadio(ActionEvent actionEvent) {
        addPartTextField.setText("Machine ID");
    }

    /**
     * set Text Label to "Company Name"
     * @param actionEvent outsourced Radio Button
     */
    public void onClickOutSourcedRadio(ActionEvent actionEvent) {
        addPartTextField.setText("Company Name");
    }

    /**
     * Part was modified and Return to Main Screen after validating and logic are checked
     * @param actionEvent Save button action
     * @throws IOException
     */
    public void onClickSavePart(ActionEvent actionEvent) throws IOException {
        ObservableList<Part> parts = Inventory.getAllParts();
        int id = selectedPart.getId();
        String name = partNameField.getText();
        Double price = Double.parseDouble(partPriceField.getText());
        int inventory = Integer.parseInt(partInventoryField.getText());
        int max = Integer.parseInt(partMaxField.getText());
        int min = Integer.parseInt(partMinField.getText());
        int machineID;
        String CompanyName;

        boolean passed = false;
        try {

            boolean isinventoryvalid = true;
            boolean isminvalid = true;

            if(min < 0 || max < 0 || inventory < 0 || price < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Form contains Invalid data!");
                alert.setContentText("Negative number is unacceptable!");
                alert.showAndWait();
            }

            if (min > 0 && max > 0 && inventory > 0 && price > 0) {
                if (min >= max || min <= 0) {
                    isminvalid = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Form contains Invalid data!");
                    alert.setContentText("Min cannot be greater than Max.");
                    alert.showAndWait();
                } else if (inventory > max || inventory < min) {
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
                //machineID = Integer.parseInt(addPartDataField.getText());
                if (inHouseRadioIcon.isSelected()) {
                    machineID = Integer.parseInt(addPartDataField.getText());
                    InHouse inHousePart = new InHouse(
                              id
                            , name
                            , price
                            , inventory
                            , min
                            , max
                            , machineID);
                    Inventory.addPart(inHousePart);
                    passed = true;
                }
                if (outSourcedRadioIcon.isSelected()) {
                    CompanyName = addPartDataField.getText();
                    Outsourced outsourced = new Outsourced(
                            id
                            ,name
                            ,price
                            ,inventory
                            ,min
                            ,max
                            ,CompanyName);
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
            Inventory.deletePart(selectedPart);
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1110, 600);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }
}
