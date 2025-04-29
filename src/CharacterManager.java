import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class CharacterManager {
    /**
     * Instantiate the settings class
     */
    private final Settings settings = new Settings();

    /**
     * Default constructor of the CharacterManager class
     */
    public CharacterManager(){}

    /**
     * Handles the deletion of a character
     * @param file file
     * @param stage stage
     */
    public void deleteCharacter(File file, Stage stage) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this character?", ButtonType.YES, ButtonType.NO); // prompt user confirming they want to delete
        confirmation.setTitle("Delete Character"); // set title
        confirmation.setHeaderText(null); // no header text

        Optional<ButtonType> result = confirmation.showAndWait(); // new button that held as result of what user enters
        if (result.isPresent() && result.get() == ButtonType.YES) { // if user hit yes
            if (file.delete()) { // if they want to delete that file
                Notification.showNotification( // confirm to user that the character has been deleted
                        stage, // on the passed stage x, y cord
                        "Deletion Successful", // title
                        "Character deleted successfully!", // message
                        settings.getBackgroundColor(), // background color from settings
                        settings.getSuccessColor(), // successful color from settings
                        0.9, // opacity
                        Notification.NotificationType.SUCCESS // notification type
                );
                stage.close(); // close the stage
            } else { // if the file cannot be deleted
                Notification.showNotification( // show user that there was an error
                        stage, // on the passed stage x, y cord
                        "Deletion Failed", // title
                        "Failed to delete the character file.", // message
                        settings.getBackgroundColor(), // background color from settings
                        settings.getSuccessColor(), // successful color from settings
                        0.9, // opacity
                        Notification.NotificationType.ERROR // notification type
                );
            }
        }
    }

    /**
     * Renames a file to a character
     * @param file file
     * @param stage stage
     */
    public void renameCharacter(File file, Stage stage) {
        TextInputDialog rename = new TextInputDialog(file.getName().replace(".json", "")); // instantiate new text input dialog box
        rename.setTitle("Rename Character"); // title
        rename.setHeaderText("Enter a new name for the character:"); // tell user what to do
        rename.setContentText("New Character Name:"); // tell user where to do it

        Optional<String> result = rename.showAndWait(); // instantiate result
        result.ifPresent(new_name -> { // ensure the user actually entered something
            String sanitized_name = new_name.replaceAll("[^a-zA-Z0-9_-]", "_"); // make sure the name is valid for file format

            File renamed_file = new File(file.getParent(), sanitized_name + ".json"); // instantiate new file with the new name

            if (file.renameTo(renamed_file)) {
                Notification.showNotification( // show user that there was an error
                        stage, // on the passed stage x, y cord
                        "Rename Successful", // title
                        "Character renamed successfully!", // message
                        settings.getBackgroundColor(), // background color from settings
                        settings.getSuccessColor(), // successful color from settings
                        0.9, // opacity
                        Notification.NotificationType.SUCCESS // notification type
                );
                stage.close(); // close the stage
            } else {
                Notification.showNotification( // show user that there was an error
                        stage, // on the passed stage x, y cord
                        "Rename Failed", // title
                        "Failed to rename the character file.", // message
                        settings.getBackgroundColor(), // background color from settings
                        settings.getErrorColor(), // successful color from settings
                        0.9, // opacity
                        Notification.NotificationType.ERROR // notification type
                );
            }
        });
    }
}
