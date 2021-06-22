package Model;

/**
 * Contains members for manipulating contact objects.
 * @author Yvonne King
 */
public class Contacts{

    /**
     * Contact id field.
     */
    private int id;

    /**
     * Contact name field.
     */
    private String name;

    /**
     * Contact constructor for creating new contact objects.
     * @param id contact id
     * @param name contact name
     */
    public Contacts (int id, String name) {
        this.id =  id;
        this.name = name;
    }

    /**
     * Setter for contact id.
     * @param id contact id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for contact name.
     * @param name contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for contact id.
     * @return contact id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for contact name.
     * @return contact name
     */
    public String getName() {
        return name;
    }

    /**
     * Allows contact name to be shown as a string in contact combo box.
     * @return name string
     */
    @Override
    public String toString() {
        return( name );
    }
}
