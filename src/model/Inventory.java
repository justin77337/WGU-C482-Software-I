package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Locale;


/**
 * Inventory Model
 * @author Justonna Naing
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * search by partID
     * @param partId The Part ID
     * @return if matched, Return the search Part else Return null
     */
    public static Part lookupPart(int partId){
        ObservableList<Part> parts = Inventory.getAllParts();

        //for(int i = 0; i < parts.size(); i++){
        //    Part search = parts.get(i);
        for (Part search: parts){
            if(search.getId() == partId){
           // if(Integer.toString(search.getId()).contains(Integer.toString(partId))){
                return search;
            }
        }

        return null;
    }

    /**
     * search by productID
     * @param productId The Product ID
     * @return if matched, Return the search Product else Return null
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> products = Inventory.getAllProducts();

      //  for(int i = 0; i < products.size(); i++){
           // Product search = products.get(i);
        for (Product search: products){
            if(search.getId() == productId){
           // if(Integer.toString(search.getId()).contains(Integer.toString(productId))){
                return search;
            }
        }

        return null;
    }

    /**
     * search by partName
     * @param partName The Part Name
     * @return Return the found parts
     */
    public static ObservableList<Part> lookupPart(String partName){
       // ObservableList<Part> found = null;
       // return found;

        ObservableList<Part> found = FXCollections.observableArrayList();
        //ObservableList<Part> parts = Inventory.getAllParts();

        for (Part search: allParts){
            if(search.getName().toLowerCase().contains(partName)){
                found.add(search);
            }
        }

        return found;
    }

    /**
     * search by productName
     * @param productName The Product Name
     * @return Return the found Products
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> found = FXCollections.observableArrayList();
        ObservableList<Product> products = Inventory.getAllProducts();

        for (Product search: products){
            if(search.getName().toLowerCase().contains(productName)){
                found.add(search);
            }
        }

        return found;
    }

    //comeback later
    public static void updatePart(int index, Part selectedPart){

    }

    //comeback later
    public static void updateProduct(int index, Product selectedProduct){

    }

    /**
     * Remove selected Part from the part list
     * @param selectedPart The part to be removed
     * @return Remove selected part from the part list
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * Remove selected Product from the product list
     * @param selectedProduct The product to be removed
     * @return Remove selected product from the product list
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
}
