import java.io.File;

/**
 * User defined settings that act like settings however are not hard coded and can be manipulated by the user
 */
public class UserSettings {
    /**
     * Set by default but will be updated and changed by the user at a later date
     */
    private File save_directory = new File("C:/Users/Caleb/School-Projects/DnD_5e_Sheet_v1/res/saves/characters"); // default save location

    /**
     * Gets the current save directory
     * @return save_directory
     */
    public File getSaveDirectory(){return this.save_directory;}

    /**
     * Sets the current save directory
     * @param save_directory save directory
     */
    public void setSaveDirectory(File save_directory){
        this.save_directory = save_directory; // sets instance save_directory as passed value
        System.out.println("Save directory updated to: " + save_directory.getAbsolutePath()); // output to console the successful updated location
    }
}
