package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Model
 * @author Justonna Naing
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for the new instance of product
     * @param id ID of the product
     * @param name Name of the product
     * @param price Price of the Product
     * @param stock Inventory level of the product
     * @param min Minimum value of the product
     * @param max Maximum value of the product
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Get a list of associated parts for the product
     * @return a list of parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     *
     * @param associatedParts associaatedParts to set
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add the associated parts list for the product
     * @param part The part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Delet associated parts from the Product
     * @param selectedAssociated The part to delete
     * @return Boolean indication for the associated part
     */
    public boolean deleteAssociatedPart(Part selectedAssociated){
        return true;
    }

    /**
     * Get a list of assocaitaed parts for the product
     * @return return a list of associated Parts
     */
    public ObservableList<Part> getAllAssociatedPart(){
        return associatedParts;
    }
}