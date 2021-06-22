package Model;

/**
 * Contains members for manipulating country objects.
 * @author Yvonne King
 */
public class Countries {

    /**
     * Country id field.
     */
    private int id;

    /**
     * Country name field.
     */
    private String name;

    /**
     * Constructor to create new country objects.
     * @param id country id
     * @param name country name
     */
    public Countries (int id, String name) {
        this.id =  id;
        this.name = name;
    }

    /**
     * Setter for country id.
     * @param id country id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for country id.
     * @return country id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for country name.
     * @param name country name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for country name.
     * @return country name
     */
    public String getName() {
        return name;
    }

    /**
     * Allows country id and name to show as a string in the country combo box.
     * @return country id and name string
     */
    @Override
    public String toString() {
        return("Country ID: "+ id +" " + name );
    }
}
