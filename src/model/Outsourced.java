package model;

/**
 * Outsourced Model
 * @author Justonna Naing
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor for the new instance of Outsourced part
     * @param id ID of the part
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory level of the part
     * @param min Minimum value of the part
     * @param max Maximum value of the part
     * @param companyName Company name of the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for the company name
     * @return Company Name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for the company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
