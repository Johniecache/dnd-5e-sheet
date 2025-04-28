import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.File;

/**
 * Hard coded settings mainly file locations and hard set values.
 */
public class Settings {

    private Character curr_character = new Character("Default Character"); // default character
    private File curr_file = null; // default file

    private final Color SUCCESS = Color.GREEN; // successful color for notification
    private final Color ERROR = Color.RED; // unsuccessful color for notification
    private final Color WARNING = Color.ORANGE; // not all there color for notification
    private final Color BACKGROUND = Color.LIGHTGRAY; // background color for notification

    private final int inventory_rows = 32; // hard coded number of row lines in inventory
    private final int inventory_columns = 3; // hard coded number of column lines in inventory

    private final Image app_icon = new Image(getClass().getResourceAsStream("/images/icon.png")); // used as application icon
    private final Image success_icon = new Image(getClass().getResourceAsStream("/images/success_icon.png")); // used as successful icon for notification
    private final Image warning_icon = new Image(getClass().getResourceAsStream("/images/warning_icon.png")); // used as not all there icon for notification
    private final Image error_icon = new Image(getClass().getResourceAsStream("/images/error_icon.png")); // used as unsuccessful icon for notification

    /**
     * Gets the score modifier from passed score value using the DnD equation.
     * @param score score
     * @return modifier value
     */
    public int getScoreMod(int score){
        double mod = Math.floor((score - 10) / 2); // equation to pass score through to get modifier
        return (int)mod; // return the integer value of the float
    }

    /**
     * Gets the current character
     * @return curr_character
     */
    public Character getCurrCharacter(){return this.curr_character;}
    /**
     * Gets the current file
     * @return curr_file
     */
    public File getCurrFile(){return this.curr_file;}

    /**
     * Gets the successful color
     * @return SUCCESS
     */
    public Color getSuccessColor(){return this.SUCCESS;}

    /**
     * Gets the error color
     * @return ERROR
     */
    public Color getErrorColor(){return this.ERROR;}

    /**
     * Gets the warning color
     * @return WARNING
     */
    public Color getWarningColor(){return this.WARNING;}

    /**
     * Gets the background color
     * @return BACKGROUND
     */
    public Color getBackgroundColor(){return this.BACKGROUND;}

    /**
     * Gets the application icon
     * @return app_icon
     */
    public Image getAppIcon(){return this.app_icon;}

    /**
     * Gets the success icon
     * @return success_icon
     */
    public Image getSuccessIcon(){return this.success_icon;}

    /**
     * Gets the warning icon
     * @return warning_icon
     */
    public Image getWarningIcon(){return this.warning_icon;}

    /**
     * Gets the error icon
     * @return error_icon
     */
    public Image getErrorIcon(){return this.error_icon;}

    /**
     * Gets the total inventory rows
     * @return inventory_rows
     */
    public int getInventoryRows(){return this.inventory_rows;}

    /**
     * Gets the total inventory columns
     * @return inventory_columns
     */
    public int getInventoryColumns(){return this.inventory_columns;}


    /**
     * Sets the instance current character object
     * @param curr_character Current Character
     */
    public void setCurrCharacter(Character curr_character){this.curr_character = curr_character;}

    /**
     * Sets the current instance file object
     * @param file curr_file
     */
    public void setCurrFile(File file){curr_file = file;}
}
