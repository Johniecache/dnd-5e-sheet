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
    private final FileManager file_manager = new FileManager(); // instantiates a new FileManager object
    /**
     * Instantiate the user_settings
     */
    private final UserSettings user_settings = new UserSettings(); // instantiates a new UserSettings object
    /**
     * Initialize settings
     */
    private final Settings settings; // declares a final field to hold the Settings object
    /**
     * Initialize the controller
     */
    private final Controller controller; // declares a final field to hold the Controller object
    /**
     * Initialize the character_manager
     */
    private final CharacterManager character_manager; // declares a final field to hold the CharacterManager object
    /**
     * Initialize inventory
     */
    private final Inventory inventory; // declares a final field to hold the Inventory object
    /**
     * Initialize spells
     */
    private final Spells spells; // declares a final field to hold the Spells object
    /**
     * Initialize information
     */
    private final Information information; // declares a final field to hold the Information object
    /**
     * Instantiate the randomizer
     */
    private final Randomizer randomizer = new Randomizer(); // instantiates a new Randomizer object
    /**
     * Initialize and set dark mode to false
     */
    private boolean dark_mode_check = false; // declares and initializes a boolean to track dark mode status to false

    /**
     * Default constructor for the ButtonManager class
     * @param settings settings class
     * @param controller controller class
     * @param inventory inventory class
     * @param spells spells class
     * @param information information class
     */
    public ButtonManager(Settings settings, Controller controller, Inventory inventory, Spells spells, Information information){ // defines the constructor for the ButtonManager class
        this.settings = settings; // sets the instance's settings field with the provided settings object
        this.controller = controller; // sets the instance's controller field with the provided controller object
        this.inventory = inventory; // sets the instance's inventory field with the provided inventory object
        this.spells = spells; // sets the instance's spells field with the provided spells object
        this.information = information; // sets the instance's information field with the provided information object
        character_manager = new CharacterManager(); // instantiates a new CharacterManager object
    }

    /**
     * Saves the character to a new .json file
     */
    public void saveAsCharacter() { // defines a method to save the current character as a new JSON file
        System.out.println("Saving Character As..."); // prints a message to the console indicating the action

        file_manager.saveUserSettings(user_settings); // saves the user's settings (including the save directory) using the file manager

        if (!user_settings.getSaveDirectory().exists()) { // checks if the specified save directory does not exist
            Alert alert = new Alert(Alert.AlertType.ERROR, "Save directory does not exist! Please set a valid directory in settings.", ButtonType.OK); // creates an error alert for the user
            alert.showAndWait(); // displays the alert and waits for the user to dismiss it
            userSettings(); // calls the userSettings method to prompt the user to set a valid directory
            return; // exits the method early
        }

        TextInputDialog dialog = new TextInputDialog(settings.getCurrCharacter().getName()); // creates a text input dialog, pre-filling it with the current character's name
        dialog.setTitle("Save Character"); // sets the title of the dialog box
        dialog.setHeaderText("Enter a name for your character:"); // sets the header text instructing the user
        dialog.setContentText("Character Name:"); // sets the content text (prompt) for the input field

        Optional<String> result = dialog.showAndWait(); // displays the dialog and waits for the user's input or cancellation
        result.ifPresent(name -> { // if a result (name) is present from the dialog, execute the following code
            String file_name = name.replaceAll("[^a-zA-Z0-9_-]", "_"); // sanitizes the entered name, replacing invalid characters with underscores to form a valid file name
            File save_file = new File(user_settings.getSaveDirectory(), file_name + ".json"); // creates a new File object representing the save path in the user's save directory, with a .json extension

            file_manager.saveToFile(save_file, settings.getCurrCharacter(), controller.getScene().getWindow(), inventory, spells, information); // calls the file manager to save the character data to the specified file, along with other UI components
            settings.setCurrFile(save_file); // updates the settings to track the newly saved file as the current file
        });
    }

    /**
     * Overrides old save of character or creates a new save if no .json file is found
     */
    public void saveCharacter() { // defines a method to save the current character
        System.out.println("Saving Character..."); // prints a message to the console indicating the action

        if(settings.getCurrFile() != null){ // checks if there is a currently loaded file (meaning the character has been saved before)
            file_manager.saveToFile(settings.getCurrFile(), settings.getCurrCharacter(), controller.getScene().getWindow(), inventory, spells, information); // saves the current character data to the existing file
        }
        else{saveAsCharacter();} // if no current file exists, calls saveAsCharacter to prompt the user for a new file name and location
    }

    /**
     * Loads a characters .json file from directory
     */
    public void loadCharacter() { // defines a method to load a character from a JSON file
        System.out.println("Loading Character..."); // prints a message to the console indicating the action

        file_manager.loadUserSettings(user_settings); // loads the user's settings, which include the default save directory

        if (!user_settings.getSaveDirectory().exists() || !user_settings.getSaveDirectory().isDirectory()) { // checks if the user's save directory does not exist or is not a directory
            System.out.println("Character save folder does not exist."); // prints a message to the console
            return; // exits the method early if the directory is invalid
        }

        List<File> r_files = Arrays.stream(user_settings.getSaveDirectory().listFiles((dir, name) -> name.endsWith(".json"))) // gets a stream of files in the save directory that end with ".json"
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified())) // sorts the files by their last modified date in descending order (most recent first)
                .limit(10) // limits the list to the 10 most recently modified files
                .collect(Collectors.toList()); // collects the filtered and sorted files into a List

        if (r_files.isEmpty()) { // checks if the list of found JSON files is empty
            System.out.println("No saved characters found."); // prints a message to the console
            Notification.showNotification(controller.getScene().getWindow(), // displays a notification on the main window
                    "No Characters", // sets the title of the notification
                    "No character save files found, check directory.", // sets the content message of the notification
                    settings.getWarningColor(), // sets the warning color for the notification
                    settings.getBackgroundColor(), // sets the background color for the notification
                    0.9, // sets the opacity of the notification
                    Notification.NotificationType.WARNING); // sets the type of notification to WARNING
            return; // exits the method early
        }

        Stage stage = new Stage(); // creates a new JavaFX Stage (window)
        stage.setTitle("Select a Character to Load"); // sets the title of the new window

        ListView<String> list_view = new ListView<>(); // creates a new ListView to display character names
        for (File file : r_files) { // iterates through each file in the list of recent files
            String file_name = file.getName(); // gets the name of the current file
            if (file_name.endsWith(".json")) { // checks if the file name ends with ".json"
                file_name = file_name.substring(0, file_name.length() - 5); // removes the last 5 characters (".json") to get just the character name
            }
            list_view.getItems().add(file_name); // adds the (modified) file name to the ListView
        }

        list_view.setOnMouseClicked(event -> { // sets an event handler for mouse clicks on the ListView
            if (event.getClickCount() == 2) { // checks if the mouse was double-clicked
                int index_selected = list_view.getSelectionModel().getSelectedIndex(); // gets the index of the item that was double-clicked
                if (index_selected >= 0) { // checks if a valid item was selected (index is not negative)
                    File file_selected = r_files.get(index_selected); // retrieves the actual File object corresponding to the selected index
                    Character loaded_char = file_manager.loadFromFile(file_selected, controller.getScene().getWindow(), inventory, spells, information); // attempts to load the character from the selected file using the file manager, updating UI elements

                    if (loaded_char != null) { // checks if the character was loaded successfully (not null)
                        settings.setCurrCharacter(loaded_char); // sets the loaded character as the current character in settings
                        settings.setCurrFile(file_selected); // sets the loaded file as the current file in settings
                        controller.loadToSheet(loaded_char); // updates the main character sheet UI with the data from the loaded character

                        System.out.println("Character loaded successfully from: " + file_selected.getAbsolutePath()); // prints a success message to the console with the file path

                        stage.close(); // closes the character selection window
                    } else { // if loading the character failed
                        System.out.println("Failed to load character."); // prints an error message to the console
                        Notification.showNotification(controller.getScene().getWindow(), // displays an error notification on the main window
                                "Error loading character", // sets the title of the notification
                                "There was an error loading character, check save file and save directory.", // sets the content message of the notification
                                settings.getErrorColor(), // sets the error color for the notification
                                settings.getBackgroundColor(), // sets the background color for the notification
                                0.9, // sets the opacity of the notification
                                Notification.NotificationType.ERROR); // sets the type of notification to ERROR
                    }
                }
            }
        });

        Button close_button = new Button("Close"); // creates a new button labeled "Close"
        close_button.setOnAction(e -> stage.close()); // sets the action for the close button to close the current stage (window)

        Button delete_button = new Button("Delete"); // creates a new button labeled "Delete"
        delete_button.setOnAction(e -> { // sets the action for the delete button
            File file = r_files.get(list_view.getSelectionModel().getSelectedIndex()); // retrieves the File object corresponding to the currently selected item in the ListView
            if (file != null) { // checks if a file was actually selected
                character_manager.deleteCharacter(file, stage); // calls the character manager to handle the deletion of the selected character file, passing the current stage for potential dialogs
            }
        });

        Button rename_button = new Button("Rename"); // creates a new button labeled "Rename"
        rename_button.setOnAction(e -> { // sets the action for the rename button
            File file = r_files.get(list_view.getSelectionModel().getSelectedIndex()); // retrieves the File object corresponding to the currently selected item in the ListView
            if (file != null) { // checks if a file was actually selected
                character_manager.renameCharacter(file, stage); // calls the character manager to handle renaming the selected character file, passing the current stage for potential dialogs
            }
        });

        HBox button_box = new HBox(10, close_button, delete_button, rename_button); // creates an HBox (horizontal box) with 10 pixels spacing, containing the close, delete, and rename buttons
        VBox layout = new VBox(10, list_view, button_box); // creates a VBox (vertical box) with 10 pixels spacing, containing the ListView and the button HBox
        Scene scene = new Scene(layout, 350, 450); // creates a new Scene with the VBox as its root node and specified dimensions
        stage.setScene(scene); // sets the created scene as the scene for the current stage
        stage.show(); // displays the character selection window
    }

    /**
     * Handles the creation of a new character
     */
    public void newCharacter() { // defines a method to handle the creation of a new character
        System.out.println("Creating New Character..."); // prints a message to the console indicating the action

        settings.setCurrCharacter(new Character("New Character")); // sets the current character in settings to a new Character object named "New Character"
        settings.setCurrFile(null); // sets the current file in settings to null, indicating no saved file for this new character
        controller.loadToSheet(settings.getCurrCharacter()); // loads the new, empty character's data onto the main character sheet UI

        inventory.clearInventory(); // clears all data from the inventory window
        spells.clearSpells(); // clears all data from the spells window
        information.clearInformation(); // clears all data from the information window
    }

    /**
     * Handles the User Settings save location, and soon to be more
     */
    public void userSettings() { // defines a method to handle user settings (like save location)
        Stage s_stage = new Stage(); // creates a new JavaFX Stage (window) for settings
        s_stage.initModality(Modality.APPLICATION_MODAL); // sets the modality to APPLICATION_MODAL, blocking interaction with other windows until this one is closed
        s_stage.setTitle("Settings"); // sets the title of the settings window

        Label save_label = new Label("Save Location:"); // creates a new Label for "Save Location:"

        TextField save_textfield = new TextField(user_settings.getSaveDirectory().getAbsolutePath()); // creates a TextField pre-filled with the absolute path of the current save directory
        save_textfield.setPrefWidth(350); // sets the preferred width of the save location text field to 350 pixels

        Button pick_file_button = new Button("\uD83D\uDCC2"); // creates a new button with a folder icon as its text
        pick_file_button.setOnAction(e -> { // sets the action to be performed when the pick file button is pressed
            DirectoryChooser directory = new DirectoryChooser(); // creates a new DirectoryChooser dialog
            directory.setTitle("Select Save Directory"); // sets the title of the directory chooser
            File new_directory = directory.showDialog(s_stage); // displays the directory chooser and waits for the user to select a directory
            if (new_directory != null) { // checks if a new directory was successfully selected
                user_settings.setSaveDirectory(new_directory); // sets the new directory as the save directory in user settings
                save_textfield.setText(new_directory.getAbsolutePath()); // updates the text field with the absolute path of the newly selected directory
            }
        });

        save_textfield.setOnAction(e -> { // sets the action to be performed when the user presses Enter in the save text field
            File new_directory = new File(save_textfield.getText()); // creates a new File object from the text entered in the save text field
            if (new_directory.exists() && new_directory.isDirectory()) { // validates if the entered path exists and is indeed a directory
                user_settings.setSaveDirectory(new_directory); // if valid, sets the user's save directory to this new directory
            } else { // if the entered directory is invalid
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid directory!", ButtonType.OK); // creates an error alert
                alert.showAndWait(); // displays the alert and waits for user acknowledgment
                save_textfield.setText(user_settings.getSaveDirectory().getAbsolutePath()); // reverts the text field to the previously valid save directory's absolute path
            }
        });

        ToggleButton toggle_dm = new ToggleButton("🌙 Dark Mode"); // creates a new ToggleButton labeled "🌙 Dark Mode"
        toggle_dm.setSelected(dark_mode_check); // sets the initial selected state of the toggle button based on the dark_mode_check boolean
        updateTheme(toggle_dm.isSelected()); // immediately applies the theme based on the initial selected state of the toggle button

        toggle_dm.setOnAction(e -> { // sets the action to be performed when the toggle button is pressed
            dark_mode_check = toggle_dm.isSelected(); // updates the dark_mode_check boolean to match the selected state of the toggle button
            updateTheme(dark_mode_check); // calls updateTheme to apply the new theme (dark or light mode)
        });

        HBox s_row = new HBox(10, save_label, save_textfield, pick_file_button); // creates an HBox with 10 pixels spacing, containing the save label, text field, and pick file button
        VBox layout = new VBox(15, s_row, toggle_dm); // creates a VBox with 15 pixels spacing, containing the HBox and the dark mode toggle button
        layout.setStyle("-fx-padding: 15px;"); // applies inline CSS to set padding around the layout to 15 pixels

        Scene scene = new Scene(layout, 550, 150); // creates a new Scene with the VBox as its root node and specified dimensions
        s_stage.setScene(scene); // sets the created scene as the scene for the settings stage
        s_stage.showAndWait(); // displays the settings window and waits for the user to close it
    }

    /**
     * Handles the quitting of the program
     */
    public void quitProgram() { // defines a method to handle quitting the program
        System.out.println("Quitting Program..."); // outputs a message to the console

        System.exit(0); // terminates the Java Virtual Machine with a status code of 0 (indicating successful execution)
    }

    /**
     * Handles the randomization of the characters stats by sending it over to the randomizer class
     * @throws JSONException JSONException
     */
    public void randomizeCharacter() throws JSONException { // defines a method to randomize character statistics, potentially throwing a JSONException
        System.out.println("Randomizing Character..."); // prints a message to the console indicating the action

        Character curr_char = settings.getCurrCharacter(); // retrieves the current character object from settings
        if (curr_char != null) { // checks if there is a current character loaded (not null)
            randomizer.applyRandomization(settings, controller); // calls the randomizer to apply randomization to the character via settings and controller
            controller.loadToSheet(curr_char); // updates the main character sheet UI with the newly randomized character data
        } else { // if no current character is loaded
            System.out.println("No current character loaded to randomize."); // prints a message to the console
            Notification.showNotification(controller.getScene().getWindow(), // displays a notification on the main window
                    "Cannot randomize null character", // sets the title of the notification
                    "Character is null and cannot be randomized, select a character.", // sets the content message of the notification
                    settings.getErrorColor(), // sets the error color for the notification
                    settings.getBackgroundColor(), // sets the background color for the notification
                    0.9, // sets the opacity of the notification
                    Notification.NotificationType.ERROR); // sets the type of notification to ERROR
        }
    }

    /**
     * Update the theme based on passed value
     * @param dark_mode on/off
     */
    private void updateTheme(boolean dark_mode) { // defines a private method to update the application's theme based on a boolean value
        Scene scene = controller.getScene(); // retrieves the current scene from the controller
        if (scene != null) { // checks if the scene is not null
            scene.getStylesheets().clear(); // clears all existing stylesheets from the scene
            if (dark_mode) { // if dark_mode is true
                scene.getStylesheets().add(getClass().getResource("/themes/darkmode.css").toExternalForm()); // adds the dark mode CSS stylesheet to the scene
            } else { // if dark_mode is false
                scene.getStylesheets().add(getClass().getResource("/themes/lightmode.css").toExternalForm()); // adds the light mode CSS stylesheet to the scene
            }
        }
    }
}