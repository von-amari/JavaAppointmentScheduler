package Model;
/**
 * Contains members for manipulating division objects.
 * @author Yvonne King.
 */
public class Divisions {
    /**
     * Division id field.
     */
    private int id;

    /**
     * Division name field.
     */
    private String name;

    /**
     * Associated country id.
     */
    private int country;

    /**
     * Constructor for creating new division objects.
     * @param id division id
     * @param name division name
     * @param country country id
     */
    public Divisions(int id, String name, int country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     * Getter for division id.
     * @return division id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for division id.
     * @param id division id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for division name.
     * @param name division name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for division name.
     * @return division name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for associated country.
     * @return country id
     */
    public int getCountry() {
        return country;
    }

    /**
     * Setter for associated country.
     * @param country country id
     */
    public void setCountry(int country) {
        this.country = country;
    }

    /**
     * Allows division id and name to show as a string in the division combo box.
     * @return string values for division id and name
     */
    @Override
    public String toString() {
        return("Division ID: "+ id +" " + name );
    }
}