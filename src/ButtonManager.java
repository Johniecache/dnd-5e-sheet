import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ButtonManager {
    /**
     * Instantiate the file_manager
     */
    private final FileManager file_manager = new FileManager();
    /**
     * Instantiate the user_settings
     */
    private final UserSettings user_settings = new UserSettings();
    /**
     * Initialize settings
     */
    private final Settings settings;
    /**
     * Initialize the controller
     */
    private final Controller controller;
    /**
     * Initialize the character_manager
     */
    private final CharacterManager character_manager;
    /**
     * Initialize inventory
     */
    private final Inventory inventory;
    /**
     * Initialize spells
     */
    private final Spells spells;
    /**
     * Instantiate the randomizer
     */
    private final Randomizer randomizer = new Randomizer();
    /**
     * Initialize and set dark mode to false
     */
    private boolean dark_mode_check = false;

    /**
     * Default constructor for the ButtonManager class
     * @param settings settings class
     * @param controller controller class
     * @param inventory inventory class
     */
    public ButtonManager(Settings settings, Controller controller, Inventory inventory, Spells spells){
        this.settings = settings; // set passed as instance settings
        this.controller = controller; // set passed as instance controller
        this.inventory = inventory; // set passed as instance inventory
        this.spells = spells; // set passed as instance spells
        character_manager = new CharacterManager(); // set character manager as a new manager passing user_settings and button manager instance
    }

    /**
     * Saves the character to a new .json file
     * @param inventory instance inventory
     */
    public void saveAsCharacter(Inventory inventory) {
        System.out.println("Saving Character As..."); // print to console the character is getting saved as

        file_manager.saveUserSettings(user_settings); // pass the user settings (save directory) to file manager

        if (!user_settings.getSaveDirectory().exists()) { // if a save directory doesn't exist tell user and tell them to enter a valid one
            Alert alert = new Alert(Alert.AlertType.ERROR, "Save directory does not exist! Please set a valid directory in settings.", ButtonType.OK); // show user
            alert.showAndWait(); // wait for user to do something
            userSettings(); // open settings to prompt user
            return; // return early
        }

        TextInputDialog dialog = new TextInputDialog(settings.getCurrCharacter().getName()); // get text from user
        dialog.setTitle("Save Character"); // title of the dialog box
        dialog.setHeaderText("Enter a name for your character:"); // tell user what to do
        dialog.setContentText("Character Name:"); // and where to do it

        Optional<String> result = dialog.showAndWait(); // wait for user to enter or hit okay
        result.ifPresent(name -> { // if there is input from user then...
            String file_name = name.replaceAll("[^a-zA-Z0-9_-]", "_"); // sanitize the file name
            File save_file = new File(user_settings.getSaveDirectory(), file_name + ".json"); // set the save file in the directory with the new file name adding a .json

            file_manager.saveToFile(save_file, settings.getCurrCharacter(), controller.getScene().getWindow(), inventory, spells); // pass new save file to file manager along with other necessary parameters
            settings.setCurrFile(save_file); // track the currently loaded file
        });
    }

    /**
     * Overrides old save of character or creates a new save if no .json file is found
     * @param inventory instance of inventory
     */
    public void saveCharacter(Inventory inventory) {
        System.out.println("Saving Character..."); // show in console whats happening

        if(settings.getCurrFile() != null){ // if there is a current file then...
            file_manager.saveToFile(settings.getCurrFile(), settings.getCurrCharacter(), controller.getScene().getWindow(), inventory, spells); // send parameters to file_manager
        }
        else{saveAsCharacter(inventory);} // otherwise send to save as for new .json file save
    }

    /**
     * Loads a characters .json file from directory
     * @param inventory instance of inventory
     */
    public void loadCharacter(Inventory inventory) {
        System.out.println("Loading Character..."); // show in console whats happening

        file_manager.loadUserSettings(user_settings); // send user settings (save directory) to file manager

        if (!user_settings.getSaveDirectory().exists() || !user_settings.getSaveDirectory().isDirectory()) { // if the save directory doesnt exist
            System.out.println("Character save folder does not exist."); // tell user
            return; // return early
        }

        List<File> r_files = Arrays.stream(user_settings.getSaveDirectory().listFiles((dir, name) -> name.endsWith(".json"))) // get the most recent files
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified())) // check which one was last modified
                .limit(10) // set the limit on the page to 10
                .collect(Collectors.toList()); //set them to the list

        if (r_files.isEmpty()) { // if there are no files
            System.out.println("No saved characters found."); // show in console
            Notification.showNotification(controller.getScene().getWindow(), // show user in main screen
                    "No Characters",
                    "No character save files found, check directory.",
                    settings.getWarningColor(),
                    settings.getBackgroundColor(),
                    0.9,
                    Notification.NotificationType.WARNING);
            return; // return early
        }

        Stage stage = new Stage(); // create new window
        stage.setTitle("Select a Character to Load"); // set title of the new window

        ListView<String> list_view = new ListView<>(); // create a new list view that will hold the characters
        for (File file : r_files) { // for all the files in the directory
            String file_name = file.getName(); // set the variable as the file name
            if (file_name.endsWith(".json")) { // check to make sure it ends with a .json
                file_name = file_name.substring(0, file_name.length() - 5); // subtract the last 5 characters (. j s o n) to just show the name
            }
            list_view.getItems().add(file_name); // add this file name to the list view
        }

        list_view.setOnMouseClicked(event -> { // new event for each mouse click
            if (event.getClickCount() == 2) { // if the times clicked is twice
                int index_selected = list_view.getSelectionModel().getSelectedIndex(); // set the index selected as the double tapped file
                if (index_selected >= 0) { // check if its a valid location in the list view
                    File file_selected = r_files.get(index_selected); // create a new file with the name of the selected name
                    Character loaded_char = file_manager.loadFromFile(file_selected, controller.getScene().getWindow(), inventory, spells); // set variable as passed parameters from selected index

                    if (loaded_char != null) { // check if the character and make sure it isn't empty
                        settings.setCurrCharacter(loaded_char); // pass parameters to settings to set the current character
                        settings.setCurrFile(file_selected); // pass parameters to set the current file
                        controller.loadToSheet(loaded_char); // pass to controller to set each text field with the saved data

                        System.out.println("Character loaded successfully from: " + file_selected.getAbsolutePath()); // debugging - show in console

                        stage.close(); // close window after picking a character
                    } else {
                        System.out.println("Failed to load character."); // if there was a problem then print to console
                        Notification.showNotification(controller.getScene().getWindow(), // show user in main window
                                "Error loading character",
                                "There was an error loading character, check save file and save direectory.",
                                settings.getErrorColor(),
                                settings.getBackgroundColor(),
                                0.9,
                                Notification.NotificationType.ERROR);
                    }
                }
            }
        });

        Button close_button = new Button("Close"); // instantiate close button with text close
        close_button.setOnAction(e -> stage.close()); // when clicked pass to stage.close() (closes window)

        Button delete_button = new Button("Delete"); // instantiate delete button with text Delete
        delete_button.setOnAction(e -> { // set the when pressed action
            File file = r_files.get(list_view.getSelectionModel().getSelectedIndex()); // set variable as the selected file
            if (file != null) { // if the file selected exists
                character_manager.deleteCharacter(file, stage); // pass to delete charater to handle the deletion of character
            }
        });

        Button rename_button = new Button("Rename"); // instantiate rename button with text Rename
        rename_button.setOnAction(e -> { // set when pressed action
            File file = r_files.get(list_view.getSelectionModel().getSelectedIndex()); // set file as the selected file
            if (file != null) { // if there was a file selected
                character_manager.renameCharacter(file, stage); // send to rename in the character manager to handle
            }
        });

        HBox button_box = new HBox(10, close_button, delete_button, rename_button); // create a new button box
        VBox layout = new VBox(10, list_view, button_box); // set the list view and button box in the layout
        Scene scene = new Scene(layout, 350, 450); // new window
        stage.setScene(scene); // new stage with the scene
        stage.show(); // ensure window is showing
    }

    /**
     * Handles the creation of a new character
     */
    public void newCharacter() {
        System.out.println("Creating New Character..."); // show in console whats happening

        settings.setCurrCharacter(new Character("New Character")); // send to settings a new character
        settings.setCurrFile(null); // set current file as null
        controller.loadToSheet(settings.getCurrCharacter()); // since character is empty it will initialize all to 0 or empty
    }

    /**
     * Handles the User Settings save location, and soon to be more
     */
    public void userSettings() {
        Stage s_stage = new Stage(); // new window
        s_stage.initModality(Modality.APPLICATION_MODAL); // make user interact with window before moving
        s_stage.setTitle("Settings"); // setting title

        Label save_label = new Label("Save Location:"); // create new label

        TextField save_textfield = new TextField(user_settings.getSaveDirectory().getAbsolutePath()); // pre-filled with current save directory
        save_textfield.setPrefWidth(350); // width of text field

        Button pick_file_button = new Button("\uD83D\uDCC2"); // new button with folder icon as text
        pick_file_button.setOnAction(e -> { // event for when button is pressed
            DirectoryChooser directory = new DirectoryChooser(); // create new directory
            directory.setTitle("Select Save Directory"); // set title
            File new_directory = directory.showDialog(s_stage); // wait for user to pick a directory
            if (new_directory != null) { // check to make sure a valid directory
                user_settings.setSaveDirectory(new_directory); // set the user directory in user settings to this new directory
                save_textfield.setText(new_directory.getAbsolutePath()); // get the absolute path for this directory and save it
            }
        });

        save_textfield.setOnAction(e -> { // new action for the textfield
            File new_directory = new File(save_textfield.getText()); // new directory as the text inside the textfield
            if (new_directory.exists() && new_directory.isDirectory()) { // validate the path exists
                user_settings.setSaveDirectory(new_directory); // if valid then set the save directory as the new directory
            } else { // otherwise
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid directory!", ButtonType.OK); // new alert notification
                alert.showAndWait(); // wait for user input
                save_textfield.setText(user_settings.getSaveDirectory().getAbsolutePath()); // save text field
            }
        });

        ToggleButton toggle_dm = new ToggleButton("🌙 Dark Mode"); // set a new button with dark mode and emoji
        toggle_dm.setSelected(dark_mode_check); // sets state based on whether dark mode is active
        updateTheme(toggle_dm.isSelected()); // applies dark mode theme immediately

        toggle_dm.setOnAction(e -> { // on button press action
            dark_mode_check = toggle_dm.isSelected(); // toggle the dark mode if selected
            updateTheme(dark_mode_check); // update the theme
        });

        HBox s_row = new HBox(10, save_label, save_textfield, pick_file_button); // horizontal layout containing buttons, etc
        VBox layout = new VBox(15, s_row, toggle_dm); // new vertical layout with toggles
        layout.setStyle("-fx-padding: 15px;"); // padding

        Scene scene = new Scene(layout, 550, 150); // window size
        s_stage.setScene(scene); // set window
        s_stage.showAndWait(); // wait for user input
    }

    /**
     * Handles the quitting of the program
     */
    public void quitProgram() {
        System.out.println("Quitting Program..."); // output to console whats happening

        System.exit(0); // exit program with status of 0
    }

    /**
     * Handles the randomization of the characters stats by sending it over to the randomizer class
     * @throws JSONException JSONException
     */
    public void randomizeCharacter() throws JSONException {
        System.out.println("Randomizing Character..."); // show in console what is happening

        Character curr_char = settings.getCurrCharacter(); // set instance variable as current character
        if (curr_char != null) { // check if character is not null
            randomizer.applyRandomization(settings, controller); // send over to randomizer class to handle
            controller.loadToSheet(curr_char); // send new character over to controller
        } else { // otherwise
            System.out.println("No current character loaded to randomize."); // tell user there is no current character to load
            Notification.showNotification(controller.getScene().getWindow(), // show notification to user
                    "Cannot randomize null character",
                    "Character is null and cannot be randomized, select a character.",
                    settings.getErrorColor(),
                    settings.getBackgroundColor(),
                    0.9,
                    Notification.NotificationType.ERROR);
        }
    }

    /**
     * Update the theme based on passed value
     * @param dark_mode on/off
     */
    private void updateTheme(boolean dark_mode) {
        Scene scene = controller.getScene(); // get the controller window
        if (scene != null) { // check if scene is not null
            scene.getStylesheets().clear(); // clear current style sheet
            if (dark_mode) { // if its dark mode
                scene.getStylesheets().add(getClass().getResource("/themes/darkmode.css").toExternalForm()); // set to dark mode
            } else { // otherwise light mode
                scene.getStylesheets().add(getClass().getResource("/themes/lightmode.css").toExternalForm()); // set to light mode
            }
        }
    }
}