import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Creates a new window to display and edit character information including
 * physical attributes, languages, and a character picture.
 */
public class Information extends Stage {
    /**
     * instantiate controller variable
     */
    private final Controller controller; // declares a final field to hold the controller instance
    /**
     * text field for character height
     */
    private final TextField height_field; // declares a final text field for character height
    /**
     * text field for character weight
     */
    private final TextField weight_field; // declares a final text field for character weight
    /**
     * text field for character gender
     */
    private final TextField gender_field; // declares a final text field for character gender
    /**
     * text field for character age
     */
    private final TextField age_field; // declares a final text field for character age
    /**
     * text field for character hair color
     */
    private final TextField hair_color_field; // declares a final text field for character hair color
    /**
     * text field for character eye color
     */
    private final TextField eye_color_field; // declares a final text field for character eye color
    /**
     * text field for character skin color
     */
    private final TextField skin_color_field; // declares a final text field for character skin color
    /**
     * list view to display known languages
     */
    private final ListView<String> languages_list_view; // declares a final list view to display known languages
    /**
     * image view to display the character's picture
     */
    private final ImageView character_image_view; // declares a final image view to display the character's picture
    /**
     * boolean to check listener attachment
     */
    private boolean check_pos_listener = false; // declares a boolean to track if position listeners are attached

    // Variables to store the offset from the main window
    private double offset_x; // declares a double to store the x-offset from the main window
    private double offset_y; // declares a double to store the y-offset from the main window


    /**
     * default constructor for the Information class
     * @param controller instance controller
     */
    public Information(Controller controller) { // defines the constructor for the Information class, taking a Controller object as input
        this.controller = controller; // assigns the passed controller to the instance variable

        Stage main_stage; // declares a Stage variable for the main application window
        if (controller != null && controller.getScene() != null && controller.getScene().getWindow() instanceof Stage stage) { // checks if the controller, its scene, and the scene's window are valid and if the window is an instance of Stage
            main_stage = stage; // casts the window to a Stage and assigns it to main_stage
            initOwner(main_stage); // sets the owner of this Information stage to the main application stage
        } else { // if the conditions for a valid main stage are not met
            main_stage = null; // sets main_stage to null
        }

        initModality(Modality.NONE); // sets the modality of this stage to NONE, meaning it doesn't block interaction with other windows
        setTitle("Character Information"); // sets the title of the Information window

        GridPane grid = new GridPane(); // creates a new GridPane layout for arranging UI elements
        grid.setHgap(10); // sets the horizontal gap between columns in the grid to 10 pixels
        grid.setVgap(10); // sets the vertical gap between rows in the grid to 10 pixels
        grid.setPadding(new Insets(20)); // sets the padding around all edges of the grid to 20 pixels

        ColumnConstraints label_col = new ColumnConstraints(); // creates new column constraints for the label column
        label_col.setPrefWidth(80); // sets the preferred width for the label column to 80 pixels
        label_col.setHgrow(Priority.NEVER); // prevents the label column from growing horizontally

        ColumnConstraints field_col = new ColumnConstraints(); // creates new column constraints for the text field column
        field_col.setPrefWidth(100); // sets the preferred width for the text field column to 100 pixels
        field_col.setHgrow(Priority.NEVER); // prevents the text field column from growing horizontally

        ColumnConstraints languages_panel_col = new ColumnConstraints(); // creates new column constraints for the languages panel column
        languages_panel_col.setPrefWidth(200); // sets the preferred width for the languages panel column to 200 pixels
        languages_panel_col.setHgrow(Priority.ALWAYS); // allows the languages panel column to grow horizontally

        ColumnConstraints picture_panel_col = new ColumnConstraints(); // creates new column constraints for the picture panel column
        picture_panel_col.setPrefWidth(150); // sets the preferred width for the picture panel column to 150 pixels
        picture_panel_col.setHgrow(Priority.ALWAYS); // allows the picture panel column to grow horizontally

        grid.getColumnConstraints().addAll(label_col, field_col, languages_panel_col, picture_panel_col); // adds all defined column constraints to the GridPane

        int personal_info_row = 0; // initializes a counter for the current row for personal information fields

        grid.add(new Label("Height:"), 0, personal_info_row); // adds a "Height:" label to the grid at column 0, current row
        height_field = new TextField(); // instantiates a new TextField for height input
        grid.add(height_field, 1, personal_info_row++); // adds the height TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Weight:"), 0, personal_info_row); // adds a "Weight:" label to the grid at column 0, current row
        weight_field = new TextField(); // instantiates a new TextField for weight input
        grid.add(weight_field, 1, personal_info_row++); // adds the weight TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Gender:"), 0, personal_info_row); // adds a "Gender:" label to the grid at column 0, current row
        gender_field = new TextField(); // instantiates a new TextField for gender input
        grid.add(gender_field, 1, personal_info_row++); // adds the gender TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Age:"), 0, personal_info_row); // adds an "Age:" label to the grid at column 0, current row
        age_field = new TextField(); // instantiates a new TextField for age input
        grid.add(age_field, 1, personal_info_row++); // adds the age TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Hair Color:"), 0, personal_info_row); // adds a "Hair Color:" label to the grid at column 0, current row
        hair_color_field = new TextField(); // instantiates a new TextField for hair color input
        grid.add(hair_color_field, 1, personal_info_row++); // adds the hair color TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Eye Color:"), 0, personal_info_row); // adds an "Eye Color:" label to the grid at column 0, current row
        eye_color_field = new TextField(); // instantiates a new TextField for eye color input
        grid.add(eye_color_field, 1, personal_info_row++); // adds the eye color TextField to the grid at column 1, current row, then increments the row counter

        grid.add(new Label("Skin Color:"), 0, personal_info_row); // adds a "Skin Color:" label to the grid at column 0, current row
        skin_color_field = new TextField(); // instantiates a new TextField for skin color input
        grid.add(skin_color_field, 1, personal_info_row++); // adds the skin color TextField to the grid at column 1, current row, then increments the row counter

        final int num_personal_info_rows = personal_info_row; // stores the total number of rows used for personal information fields

        languages_list_view = new ListView<>(); // instantiates a new ListView to display languages
        languages_list_view.setPrefHeight(6 * 25); // sets the preferred height of the ListView to accommodate 6 items
        languages_list_view.setMaxHeight(6 * 25); // sets the maximum height of the ListView to accommodate 6 items
        languages_list_view.setPrefWidth(180); // sets the preferred width of the ListView to 180 pixels
        languages_list_view.setMinWidth(120); // sets the minimum width of the ListView to 120 pixels


        Button add_language_button = new Button("Add Language"); // creates a new button labeled "Add Language"
        add_language_button.setOnAction(e -> { // sets the action to be performed when the "Add Language" button is clicked
            if (languages_list_view.getItems().size() < 6) { // checks if the number of languages in the list is less than 6
                TextInputDialog dialog = new TextInputDialog(); // creates a new TextInputDialog for entering a language
                dialog.setTitle("Add Language"); // sets the title of the dialog
                dialog.setHeaderText("Enter a new language:"); // sets the header text of the dialog
                dialog.setContentText("Language:"); // sets the content text (prompt) of the dialog
                dialog.showAndWait().ifPresent(lang -> { // displays the dialog and processes the result if a value is present
                    if (lang != null && !lang.trim().isEmpty()) { // checks if the entered language is not null and not empty after trimming whitespace
                        languages_list_view.getItems().add(lang.trim()); // adds the trimmed language to the ListView
                    }
                });
            } else { // if the language limit (6) has been reached
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // creates a new information alert
                alert.setTitle("Language Limit"); // sets the title of the alert
                alert.setHeaderText(null); // sets the header text to null (no header)
                alert.setContentText("You can know a maximum of 6 languages."); // sets the content text of the alert
                alert.showAndWait(); // displays the alert and waits for it to be closed
            }
        });

        VBox languages_vbox = new VBox(5); // creates a new VBox with 5 pixels spacing
        languages_vbox.getChildren().addAll(new Label("Languages (max 6):"), languages_list_view, add_language_button); // adds a label, the languages ListView, and the add language button to the VBox
        GridPane.setValignment(languages_vbox, VPos.TOP); // sets the vertical alignment of the VBox within its grid cell to TOP
        grid.add(languages_vbox, 2, 0, 1, num_personal_info_rows + 1); // adds the VBox to the grid at column 2, row 0, spanning 1 column and (num_personal_info_rows + 1) rows


        // Character Picture Section (in a VBox, placed in Column 3)
        character_image_view = new ImageView(); // instantiates a new ImageView for displaying the character picture
        character_image_view.setFitWidth(120); // sets the preferred width for the image to fit within
        character_image_view.setFitHeight(180); // sets the preferred height for the image to fit within
        character_image_view.setPreserveRatio(true); // maintains the aspect ratio of the image when resized
        character_image_view.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;"); // applies CSS style for a light gray border
        // Removed: VBox.setHgrow(characterImageView, Priority.ALWAYS); // (Comment indicating previously removed code)
        // Removed: characterImageView.setMaxWidth(Double.MAX_VALUE); // (Comment indicating previously removed code)


        Button upload_picture_button = new Button("Upload Picture"); // creates a new button labeled "Upload Picture"
        upload_picture_button.setOnAction(e -> { // sets the action to be performed when the "Upload Picture" button is clicked
            FileChooser file_chooser = new FileChooser(); // creates a new FileChooser dialog
            file_chooser.setTitle("Select Character Picture"); // sets the title of the file chooser dialog
            file_chooser.getExtensionFilters().addAll( // adds file extension filters for image files
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif") // defines an image file filter
            );
            File selected_file = file_chooser.showOpenDialog(this); // displays the file chooser dialog and gets the selected file
            if (selected_file != null) { // checks if a file was selected
                try { // starts a try block to handle potential MalformedURLException
                    character_image_view.setImage(new Image(selected_file.toURI().toURL().toString())); // sets the image in the ImageView from the selected file's URL
                } catch (MalformedURLException ex) { // catches a MalformedURLException if the URL is invalid
                    ex.printStackTrace(); // prints the stack trace of the exception
                }
            }
        });

        VBox picture_vbox = new VBox(5); // creates a new VBox with 5 pixels spacing for the picture section
        picture_vbox.getChildren().addAll(new Label("Character Picture:"), character_image_view, upload_picture_button); // adds a label, the character ImageView, and the upload picture button to the VBox
        GridPane.setValignment(picture_vbox, VPos.TOP); // sets the vertical alignment of the VBox within its grid cell to TOP
        grid.add(picture_vbox, 3, 0, 1, num_personal_info_rows + 1); // adds the VBox to the grid at column 3, row 0, spanning 1 column and (num_personal_info_rows + 1) rows


        Scene info_scene = new Scene(grid); // creates a new Scene with the GridPane as its root node
        setScene(info_scene); // sets the created scene as the scene for this Stage

        this.setWidth(700); // sets the width of the Information window to 700 pixels
        this.setHeight(400); // sets the height of the Information window to 400 pixels
        setResizable(false); // prevents the user from resizing the Information window
    }

    /**
     * returns the boolean of check_pos_listener for public access
     * @return check_pos_listener
     */
    public boolean checkListenersAttached() { // defines a public method to check if position listeners are attached
        return check_pos_listener; // returns the value of the check_pos_listener boolean
    }

    /**
     * Adds position listeners to the window. This will allow for binding it to the main window when the main window is moved
     * @param main_stage the primary stage of the application
     */
    public void addPosListeners(Stage main_stage) { // defines a public method to add position listeners, taking the main application Stage as input
        if (!check_pos_listener) { // checks if position listeners are not already attached
            offset_x = getX() - main_stage.getX(); // calculates the initial x-offset between this window and the main stage
            offset_y = getY() - main_stage.getY(); // calculates the initial y-offset between this window and the main stage

            main_stage.xProperty().addListener((obs, oldX, newX) -> { // adds a listener to the main stage's x-coordinate property
                setX(newX.doubleValue() + offset_x); // updates this window's x-coordinate to maintain its relative position to the main stage
            });
            main_stage.yProperty().addListener((obs, oldY, newY) -> { // adds a listener to the main stage's y-coordinate property
                setY(newY.doubleValue() + offset_y); // updates this window's y-coordinate to maintain its relative position to the main stage
            });

            check_pos_listener = true; // sets the listener attached flag to true
        }
    }

    /**
     * Shows the information window.
     */
    public void showInformation() { // defines a public method to show the information window
        if (getOwner() == null && controller != null && controller.getScene() != null && controller.getScene().getWindow() != null) { // checks if there's no owner set and if the controller, its scene, and the scene's window are valid
            initOwner(controller.getScene().getWindow()); // sets the owner of this stage to the main application window
        }
        super.show(); // calls the show method of the superclass (Stage) to display the window
    }

    /**
     * Hides the information window.
     */
    public void hideInformation() { // defines a public method to hide the information window
        super.hide(); // calls the hide method of the superclass (Stage) to conceal the window
    }

    /**
     * Boolean to check if the information window is currently showing.
     * @return true if the window is showing, false otherwise
     */
    public boolean isShowingInformation() { // defines a public method to check if the information window is currently visible
        return isShowing(); // returns the result of the superclass's isShowing method
    }

    /**
     * Gets all character information from the text fields and list view.
     * @return a String array containing height, weight, gender, age, hair color, eye color, skin color, and languages (comma-separated)
     */
    public String[] getInformationData() { // defines a public method to retrieve all character information as a String array
        String[] data = new String[8]; // creates a new String array of size 8 to hold the character data
        data[0] = height_field.getText(); // gets the text from the height field and stores it at index 0
        data[1] = weight_field.getText(); // gets the text from the weight field and stores it at index 1
        data[2] = gender_field.getText(); // gets the text from the gender field and stores it at index 2
        data[3] = age_field.getText(); // gets the text from the age field and stores it at index 3
        data[4] = hair_color_field.getText(); // gets the text from the hair color field and stores it at index 4
        data[5] = eye_color_field.getText(); // gets the text from the eye color field and stores it at index 5
        data[6] = skin_color_field.getText(); // gets the text from the skin color field and stores it at index 6
        data[7] = String.join(",", languages_list_view.getItems()); // joins all items from the languages ListView into a single string separated by commas and stores it at index 7
        return data; // returns the array containing all character information
    }

    /**
     * Sets character information to the text fields and list view.
     * @param data a String array containing height, weight, gender, age, hair color, eye color, skin color, and languages (comma-separated)
     */
    public void setInformationData(String[] data) { // defines a public method to set character information from a String array
        if (data.length > 0) height_field.setText(data[0]); // if data exists at index 0, set the height field's text
        if (data.length > 1) weight_field.setText(data[1]); // if data exists at index 1, set the weight field's text
        if (data.length > 2) gender_field.setText(data[2]); // if data exists at index 2, set the gender field's text
        if (data.length > 3) age_field.setText(data[3]); // if data exists at index 3, set the age field's text
        if (data.length > 4) hair_color_field.setText(data[4]); // if data exists at index 4, set the hair color field's text
        if (data.length > 5) eye_color_field.setText(data[5]); // if data exists at index 5, set the eye color field's text
        if (data.length > 6) skin_color_field.setText(data[6]); // if data exists at index 6, set the skin color field's text
        if (data.length > 7) { // if data exists at index 7 (languages)
            languages_list_view.getItems().clear(); // clears all existing items from the languages ListView
            String[] languages = data[7].split(","); // splits the comma-separated language string into individual language strings
            for (String lang : languages) { // iterates through each language string
                if (!lang.trim().isEmpty()) { // checks if the language string is not empty after trimming whitespace
                    languages_list_view.getItems().add(lang.trim()); // adds the trimmed language to the languages ListView
                }
            }
        }
    }

    /**
     * Gets the path of the character's picture.
     * @return the URL string of the character image, or null if no image is set
     */
    public String getCharacterPicturePath() { // defines a public method to get the URL path of the character's picture
        if (character_image_view.getImage() != null) { // checks if an image is currently set in the ImageView
            return character_image_view.getImage().getUrl(); // returns the URL string of the image
        }
        return null; // returns null if no image is set
    }

    /**
     * Sets the character's picture from a given URL path.
     * @param path the URL path of the image
     */
    public void setCharacterPicture(String path) { // defines a public method to set the character's picture from a URL path
        if (path != null && !path.isEmpty()) { // checks if the provided path is not null and not empty
            try { // starts a try block to handle potential exceptions during image loading
                character_image_view.setImage(new Image(path)); // sets the image in the ImageView using the provided URL path
            } catch (Exception e) { // catches any exception that occurs during image loading
                e.printStackTrace(); // prints the stack trace of the exception
                character_image_view.setImage(null); // sets the image to null if an error occurs
            }
        } else { // if the path is null or empty
            character_image_view.setImage(null); // sets the image in the ImageView to null, effectively clearing it
        }
    }

    /**
     * Clears all information for current character
     */
    public void clearInformation(){ // defines a public method to clear all character information displayed in the window
        height_field.setText(""); // clears the text from the height field
        weight_field.setText(""); // clears the text from the weight field
        gender_field.setText(""); // clears the text from the gender field
        age_field.setText(""); // clears the text from the age field
        hair_color_field.setText(""); // clears the text from the hair color field
        eye_color_field.setText(""); // clears the text from the eye color field
        skin_color_field.setText(""); // clears the text from the skin color field

        languages_list_view.getItems().clear(); // clears all items from the languages list view

        character_image_view.setImage(null); // removes the image from the character image view
    }
}