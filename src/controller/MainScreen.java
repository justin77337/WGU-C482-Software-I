package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainScreen Controller Class.
 *
 * Run time error occurred if user select no "Part" or "Product" when clicking "Modify" Buttons.
 * This error was caused due to passing "null" value.
 * It can be found in onClickModifyPart Method and onClickModifyProduct Method.
 * @author Justonna Naing
 */
public class MainScreen implements Initializable {
    public Button addPartB;
    public Button addProductB;
    public TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;
    public TableView productTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productPrice;
    public TextField searchPartString;
    public TextField searchProductString;

    private static Part selectedpart;
    private static Product selectedproduct;

    /**
     * get selected part to modify
     * @return selected part
     */
    public static Part partmodify() {
        return selectedpart;
    }

    /**
     * get selected product to modify
     * @return selected product
     */
    public static Product productmodify() {
        return selectedproduct;
    }

    /**
     * Initialize the controller and populate the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am MainScreen!");

        partTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Load the AddPart Controller
     * @param actionEvent Add button Action
     * @throws IOException
     */
    public void onClickaddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load the AddProduct Controller
     * @param actionEvent Add Button Action
     * @throws IOException
     */
    public void onClickaddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,920,670);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exit the Program
     * @param actionEvent Exit button action
     */
    public void onClickExit(ActionEvent actionEvent) {
        System.exit(0);
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

        partTable.setItems(parts);
    }

    /**
     * search based on value in the search box text field
     * @param keyEvent
     */
    public void onSearchProduct(KeyEvent keyEvent) {
        String q = searchProductString.getText().toLowerCase();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        try {
            if (products.size() == 0){
                int id = Integer.parseInt(q);
                Product search = Inventory.lookupProduct(id);
                if(search != null)
                    products.add(search);
            }
        } catch (NumberFormatException e){

        }

        productTable.setItems(products);
    }

    /**
     * Delete selected part
     * if part was not selected, the Warning Dialog box shows
     * Return to MainScreen
     * @param actionEvent Delete action button
     * @throws IOException
     */
    public void onDeletePart(ActionEvent actionEvent) throws IOException {
        boolean deleted = false;
        Part selectedItem = (Part) partTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to delete!");
            alert.setContentText("Part was not selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Selected part will be deleted!");
            alert.setContentText("Are you sure you want to delete selected part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Inventory.deletePart(selectedItem);
                deleted = true;
                if (deleted) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1110, 600);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1110, 600);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }

    }

    /**
     * Delete selected Product
     * if product was not selected, Warning Dialog Box shows
     * Return to MainScreen
     * @param actionEvent Delete Button action
     * @throws IOException
     */
    public void onDeleteProduct(ActionEvent actionEvent) throws IOException {
        boolean deleted = false;
        Product selectedItem = (Product) productTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to delete!");
            alert.setContentText("Product was not selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Selected product will be deleted!");
            alert.setContentText("Are you sure you want to delete selected product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                ObservableList<Part> associatedParts = selectedItem.getAllAssociatedPart();

                if (associatedParts.size() >= 1) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Product has associataed part(s)");
                    alert.setContentText("All associated parts must be removed before deletion");
                    alert.showAndWait();
                }
                else {
                    Inventory.deleteProduct(selectedItem);
                    deleted = true;
                    if (deleted) {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1110, 600);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1110, 600);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }

    }

    /**
     * if part was selected, Load the ModifyPart Controller
     * if part was not selected Warning Dialog Box shows
     * @param actionEvent Modify Button Action
     * @throws IOException
     */
    public void onClickModifyPart(ActionEvent actionEvent) throws IOException {

        selectedpart = (Part) partTable.getSelectionModel().getSelectedItem();

        /*
         * Run Time Error.
         * Error was corrected by preventing User from passing "null" value.
         */
        if (selectedpart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to modify!");
            alert.setContentText("Part was not selected");
            alert.showAndWait();
        }

        if (selectedpart != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 920, 670);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * if product was selected, Load the ModifyProduct Controller
     * if product was not selected, Warning Dialog Box shows
     * @param actionEvent Modify Button action
     * @throws IOException
     */
    public void onClickModifyProduct(ActionEvent actionEvent) throws IOException {

        selectedproduct = (Product) productTable.getSelectionModel().getSelectedItem();

        /*
         * Run Time Error.
         * Error was corrected by preventing User from passing "null" value.
         */
        if (selectedproduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Select an item to modify!");
            alert.setContentText("Product was not selected");
            alert.showAndWait();
        }

        if (selectedproduct != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 920, 670);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

}
