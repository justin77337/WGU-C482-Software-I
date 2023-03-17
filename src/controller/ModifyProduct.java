package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * ModifyProduct Controller Class
 * @author Justonna Naing
 */
public class ModifyProduct implements Initializable {
    public TableView allTable;
    public TableColumn allPartIDCol;
    public TableColumn allNameCol;
    public TableColumn allInventoryCol;
    public TableColumn allPriceCol;
    public TableView fewTable;
    public TableColumn fewPartIDCol;
    public TableColumn fewNameCol;
    public TableColumn fewInventoryCol;
    public TableColumn fewPriceCol;
    public Button addAssociatedPartB;
    public Button deleteAssociatedPartB;
    public TextField productIDField;
    public TextField productMinField;
    public TextField productNameField;
    public TextField productInventoryField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField searchPartString;

    Product selectedProduct;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initialize the Controller
     * Populate the text fields with selected product data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("I am AddProduct!");
        selectedProduct =  MainScreen.productmodify();
        associatedParts = selectedProduct.getAssociatedParts();

        allTable.setItems(Inventory.getAllParts());
        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        fewTable.setItems(MainScreen.productmodify().getAllAssociatedPart());
        fewPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fewNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        fewInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        fewPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDField.setText(String.valueOf(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productInventoryField.setText(String.valueOf(selectedProduct.getStock()));
        productPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxField.setText(String.valueOf(selectedProduct.getMax()));
        productMinField.setText(String.valueOf(selectedProduct.getMin()));
    }

    /**
     *  add selected parts to the associated part table
     *  if part was not selected the Warning Dialog box shows
     * @param actionEvent add button action
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        System.out.println("On Add Button!");
        Part selectedItem = (Part) allTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to add!");
            alert.setContentText("Part was not selected");
            alert.showAndWait();
        }
        else {
            associatedParts.add(selectedItem);
            fewTable.setItems(associatedParts);
        }
    }

    /**
     * remove selected parts from the associated Table
     * if part was not selected the Warning Dialog box showed
     * @param actionEvent Remove Associated Button
     */
    public void deleteAssociatedPart(ActionEvent actionEvent) {
        System.out.println("On Remove Button!");
        Part selectedItem = (Part) fewTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to remove!");
            alert.setContentText("Part was not selected");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Selected part will be deleted!");
            alert.setContentText("Are you sure you want to delete selected part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedItem);
                fewTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Modify Product Form closes, and the application returns to the MainScreen
     * @param actionEvent Cancel Button Action
     * @throws IOException
     */
    public void onClickCancelProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1110, 600);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Product was modified and Return to Main Screen after validating and logic are checked
     * @param actionEvent Save Button Action
     * @throws IOException
     */
    public void onClickSaveProduct(ActionEvent actionEvent) throws IOException{
        ObservableList<Product> products = Inventory.getAllProducts();
        //int id = 7000+products.size();
        //productIDField = new TextField(String.valueOf(++id));

        int id = selectedProduct.getId();
        String name = productNameField.getText();
        String price = productPriceField.getText();
        String inventory = productInventoryField.getText();
        String max = productMaxField.getText();
        String min = productMinField.getText();

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
                Product newProduct = new Product(
                        //Integer.parseInt(productIDField.getText())
                        id
                        , name
                        , Double.parseDouble(price)
                        , Integer.parseInt(inventory)
                        , Integer.parseInt(min)
                        , Integer.parseInt(max));

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(newProduct);
                Inventory.deleteProduct(selectedProduct);
                passed = true;
            }

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error adding product!");
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

    /**
     * search based on value in the search box text field
     * @param keyEvent
     */
    public void onSearchPart(KeyEvent keyEvent) {
            String q = searchPartString.getText().toLowerCase();

            ObservableList<Part> parts = Inventory.lookupPart(q);

            try {
                if (parts.size() == 0){
                    int id = Integer.parseInt(q);
                    Part search = Inventory.lookupPart(id);
                    if(search != null)
                        parts.add(search);
                }
            } catch (NumberFormatException e){

            }

            allTable.setItems(parts);
        }
}
