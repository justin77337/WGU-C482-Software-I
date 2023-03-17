package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * The JavaDocs folder is located in the same project folder (C482).
 * Inventory Management Program.
 * Future enhancements that would extend functionality is to integrate Database System.
 * Logical Error I encountered was in the AddPart Controller, passing wrong argument (max instaed of min).
 * Run Time Error and preventing error can be found in MainScreen Controller. (passing "null" value.)
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1110, 600));
        stage.show();
    }

    /**
     * Test Data
     */
    private static void addTestData(){
        InHouse inHousePart1 = new InHouse(1,"Bicycle_Part",799.99,12,1,15,111);
        Inventory.addPart(inHousePart1);

        Outsourced outsourcedPart1 = new Outsourced(2,"Car_Part", 12888.33,12,1,15,"Toyota");
        Inventory.addPart(outsourcedPart1);

        Product inHousePart2 = new Product(1,"Bicycle_Product",799.99,12,1,15);
        Inventory.addProduct(inHousePart2);

        Product outsourcedPart2 = new Product(2,"Car_Product", 12888.33,12,1,15);
        Inventory.addProduct(outsourcedPart2);

    }

    /**
     * Entry point of the application and launch the application
     * @param args
     */
    public static void main(String[] args){
        //addTestData();
        launch(args);
    }
}
