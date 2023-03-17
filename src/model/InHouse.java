package model;

/**
 * InHouse Model
 * @author Justonna Naing
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor for the new instance of Inhouse part
     * @param id ID of the part
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory level of the part
     * @param min Minimum value of the part
     * @param max Maximum value of the part
     * @param machineId Machine ID of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for the machine id
     * @return machine Id of the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for the machine id
     * @param machineId Machine ID of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
