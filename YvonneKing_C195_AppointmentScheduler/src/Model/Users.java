package Model;

/**
 * Contains members for manipulating user objects.
 * @author Yvonne King.
 */
public class Users{
    /**
     * User id field.
     */
    private int id;
    /**
     * User name field.
     */
    private String name;

    /**
     * Constructor for creating new users.
     * @param id user id
     * @param name user name
     */
    public Users (int id, String name) {
        this.id =  id;
        this.name = name;
    }
    /**
     * Sets user id.
     * @param id user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets user name.
     * @param name user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user id.
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Allows user id and name to show as a string in the user combo box.
     * @return user id and name string
     */
    @Override
    public String toString() {
        return("User ID: " +id +" " + name );
    }
}
