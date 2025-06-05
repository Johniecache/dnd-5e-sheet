import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;

/**
 * Creates a new window of a grid that will be editable by the user which will act as their spellbook.
 * 4 columns: name, level, slot, description
 */
public class Spells extends Stage {
    /**
     * instantiate new settings variable
     */
    private static final Settings settings = new Settings();
    /**
     * instantiate controller variable
     */
    private final Controller controller;
    /**
     * set instance rows to settings hard coded value, plus 3 for the header rows
     */
    private static final int ROWS = settings.getSpellRows();
    /**
     * set instance columns to settings hard coded value, now 4 instead of 5
     */
    private static final int COLS = settings.getSpellColumns();
    /**
     * create 2d array to hold name, level, slot, description of specific spell
     */
    private final TextField[][] fields = new TextField[ROWS][COLS];

    /**
     * boolean to check listener
     */
    private boolean check_pos_listener = false;

    /**
     * textfield for the casting ability
     */
    private final TextField casting_ability_field;
    /**
     * textfield for the save DC
     */
    private final TextField save_dc_field;
    /**
     * textfield for the attack bonus
     */
    private final TextField attack_bonus_field;
    /**
     * array to hold the top three text fields
     */
    private TextField[] top_fields;

    /**
     * default constructor for the spells class
     * @param controller instance controller
     */
    public Spells(Controller controller) {
        this.controller = controller; // initializes the controller

        Stage main_stage; // declares a Stage variable for the main window
        if (controller != null && controller.getScene() != null && controller.getScene().getWindow() instanceof Stage stage) { // checks if the controller, scene, and window are valid
            main_stage = stage; // sets the main_stage to the controller's window
            initOwner(main_stage); // sets the owner of this stage to the main_stage
        } else {
            main_stage = null; // sets main_stage to null if conditions are not met
        }

        initModality(Modality.NONE); // sets the modality of the stage to NONE
        setTitle("Spell book"); // sets the title of the spellbook window

        GridPane grid = new GridPane(); // creates a new GridPane layout
        grid.setHgap(5); // sets the horizontal gap between columns
        grid.setVgap(5); // sets the vertical gap between rows
        grid.setPadding(new Insets(10)); // sets the padding around the grid

        ColumnConstraints name_col = new ColumnConstraints(); // creates column constraints for the name column
        name_col.setPrefWidth(120); // sets the preferred width for the name column
        name_col.setHgrow(Priority.NEVER); // prevents the name column from growing horizontally

        ColumnConstraints level_col = new ColumnConstraints(); // creates column constraints for the level column
        level_col.setPrefWidth(35); // sets the preferred width for the level column
        level_col.setHgrow(Priority.NEVER); // prevents the level column from growing horizontally

        ColumnConstraints slot_col = new ColumnConstraints(); // creates column constraints for the slot column
        slot_col.setPrefWidth(35); // sets the preferred width for the slot column
        slot_col.setHgrow(Priority.NEVER); // prevents the slot column from growing horizontally

        ColumnConstraints description_col = new ColumnConstraints(); // creates column constraints for the description column
        description_col.setPrefWidth(350); // sets the preferred width for the description column
        description_col.setHgrow(Priority.ALWAYS); // allows the description column to grow horizontally

        grid.getColumnConstraints().addAll(name_col, level_col, slot_col, description_col); // adds all defined column constraints to the grid

        HBox casting_ability_box = new HBox(5); // creates an HBox for the casting ability section
        casting_ability_box.setAlignment(Pos.CENTER); // centers the contents of the HBox
        Label casting_ability_label = new Label("Casting Ability"); // creates a label for casting ability
        casting_ability_field = new TextField(); // creates a text field for casting ability
        casting_ability_field.setPrefWidth(80); // sets the preferred width for the casting ability text field
        casting_ability_box.getChildren().addAll(casting_ability_label, casting_ability_field); // adds the label and text field to the HBox
        grid.add(casting_ability_box, 0, 0, COLS, 1); // adds the HBox to the grid, spanning all columns

        HBox save_dc_box = new HBox(5); // creates an HBox for the save DC section
        save_dc_box.setAlignment(Pos.CENTER); // centers the contents of the HBox
        Label save_dc_label = new Label("Save DC"); // creates a label for save DC
        save_dc_field = new TextField(); // creates a text field for save DC
        save_dc_field.setPrefWidth(80); // sets the preferred width for the save DC text field
        save_dc_box.getChildren().addAll(save_dc_label, save_dc_field); // adds the label and text field to the HBox
        grid.add(save_dc_box, 0, 1, COLS, 1); // adds the HBox to the grid, spanning all columns

        HBox attack_bonus_box = new HBox(5); // creates an HBox for the attack bonus section
        attack_bonus_box.setAlignment(Pos.CENTER); // centers the contents of the HBox
        Label attack_bonus_label = new Label("Attack Bonus"); // creates a label for attack bonus
        attack_bonus_field = new TextField(); // creates a text field for attack bonus
        attack_bonus_field.setPrefWidth(80); // sets the preferred width for the attack bonus text field
        attack_bonus_box.getChildren().addAll(attack_bonus_label, attack_bonus_field); // adds the label and text field to the HBox
        grid.add(attack_bonus_box, 0, 2, COLS, 1); // adds the HBox to the grid, spanning all columns

        grid.add(new Label("Name"), 0, 3); // adds a label for "Name" to the grid
        grid.add(new Label("Level"), 1, 3); // adds a label for "Level" to the grid
        grid.add(new Label("Slot"), 2, 3); // adds a label for "Slot" to the grid
        grid.add(new Label("Description"), 3, 3); // adds a label for "Description" to the grid

        for (int i = 0; i < ROWS; i++) { // iterates through each row
            for (int j = 0; j < COLS; j++) { // iterates through each column
                fields[i][j] = new TextField(); // initializes a new TextField for each cell
                fields[i][j].setPromptText(switch (j) { // sets the prompt text based on the column index
                    case 0 -> "Name";
                    case 1 -> "Level";
                    case 2 -> "Slot";
                    case 3 -> "Description";
                    default -> "";
                });

                switch (j) { // sets the preferred width of the text field based on the column
                    case 0 -> fields[i][j].setPrefWidth(name_col.getPrefWidth());
                    case 1 -> fields[i][j].setPrefWidth(level_col.getPrefWidth());
                    case 2 -> fields[i][j].setPrefWidth(slot_col.getPrefWidth());
                    case 3 -> fields[i][j].setPrefWidth(description_col.getPrefWidth());
                }
                grid.add(fields[i][j], j, i + 4); // adds the text field to the grid, offset by 4 rows for headers
            }

            top_fields = new TextField[]{casting_ability_field, save_dc_field, attack_bonus_field}; // initializes the top_fields array
        }

        double total_width = name_col.getPrefWidth() + level_col.getPrefWidth() + slot_col.getPrefWidth() + description_col.getPrefWidth() + (grid.getHgap() * (COLS - 1)) + (grid.getPadding().getLeft() + grid.getPadding().getRight()); // calculates the total width of the scene
        double total_height = grid.getPadding().getTop() + grid.getPadding().getBottom() + (ROWS * 34); // calculates the total height of the scene

        setScene(new Scene(grid, total_width, total_height)); // sets the scene for the stage with the calculated dimensions
        setResizable(false); // makes the window not resizable

        if (main_stage != null) { // checks if a main_stage exists
            main_stage.xProperty().addListener((obs, oldX, newX) -> setX(newX.doubleValue() - getWidth())); // adds a listener to keep the spellbook to the left of the main window's x position
            main_stage.yProperty().addListener((obs, oldY, newY) -> setY(newY.doubleValue())); // adds a listener to keep the spellbook aligned with the main window's y position
            main_stage.widthProperty().addListener((obs, oldW, newW) -> setX(main_stage.getX() - getWidth())); // adds a listener to adjust x position if main window's width changes
            main_stage.heightProperty().addListener((obs, oldH, newH) -> setY(main_stage.getY())); // adds a listener to adjust y position if main window's height changes
            check_pos_listener = true; // sets the listener flag to true
        }
    }

    /**
     * returns the boolean of check_pos_listener for public access
     * @return check_pos_listener
     */
    public boolean checkListenersAttached() {
        return check_pos_listener; // returns the status of the listener
    }

    /**
     * Adds position listeners to the window. This will allow for binding it to the main window when the main window is moved
     */
    public void addPosListeners(Stage main_stage) {
        if (!check_pos_listener) { // checks if listeners are not already attached
            main_stage.xProperty().addListener((obs, oldX, newX) -> setX(newX.doubleValue() - getWidth())); // adds a listener to keep the spellbook to the left of the main window's x position
            main_stage.yProperty().addListener((obs, oldY, newY) -> setY(newY.doubleValue())); // adds a listener to keep the spellbook aligned with the main window's y position
            main_stage.widthProperty().addListener((obs, oldW, newW) -> setX(main_stage.getX() - getWidth())); // adds a listener to adjust x position if main window's width changes
            main_stage.heightProperty().addListener((obs, oldH, newH) -> setY(main_stage.getY())); // adds a listener to adjust y position if main window's height changes
            check_pos_listener = true; // sets the listener flag to true
        }
    }

    /**
     * Shows the spells window
     */
    public void showSpells() {
        if (getOwner() == null && controller != null && controller.getScene() != null && controller.getScene().getWindow() != null) { // checks if there is no owner and if the controller, scene, and window are valid
            initOwner(controller.getScene().getWindow()); // initializes the owner of this stage
        }
        super.show(); // calls the superclass's show method to display the stage
    }

    /**
     * Hides the spell window
     */
    public void hideSpells() {
        super.hide(); // calls the superclass's hide method to conceal the stage
    }

    /**
     * Boolean to check if the window is currently showing
     * @return isShowing
     */
    public boolean isShowingSpells() {
        return isShowing(); // returns whether the stage is currently showing
    }

    /**
     * Gets the data from the window
     * @return spell data
     */
    public String[][] getSpellData() {
        String[][] data = new String[ROWS][COLS]; // creates a new 2D array to hold spell data
        for (int i = 0; i < ROWS; i++) { // iterates through each row
            for (int j = 0; j < COLS; j++) { // iterates through each column
                data[i][j] = fields[i][j].getText(); // gets the text from each TextField and stores it in the array
            }
        }
        return data; // returns the 2D array of spell data
    }

    /**
     * Gets the data from the top three text fields (casting ability, save DC, attack bonus).
     * @return an array of strings containing the top data
     */
    public String[] getTopData() {
        String[] data = new String[3]; // creates a new array to hold the top data
        for (int i = 0; i < 3; i++){ // iterates through the top three fields
            data[i] = top_fields[i].getText(); // gets the text from each top TextField and stores it
        }
        return data; // returns the array of top data
    }

    /**
     * Sets the data to the window
     * @param data spell data
     */
    public void setSpellData(String[][] data) {
        for (int i = 0; i < Math.min(ROWS, data.length); i++) { // iterates through rows, ensuring not to exceed bounds
            for (int j = 0; j < Math.min(COLS, data[i].length); j++) { // iterates through columns, ensuring not to exceed bounds
                fields[i][j].setText(data[i][j]); // sets the text of each TextField with the provided data
            }
        }
    }

    /**
     * Sets the data for the top three text fields (casting ability, save DC, attack bonus).
     * @param data an array of strings containing the data to set
     */
    public void setTopData(String[] data) {
        for (int i = 0; i < 3; i++){ // iterates through the top three fields
            top_fields[i].setText(data[i]); // sets the text of each top TextField with the provided data
        }
    }

    /**
     * Clears all spells and spell information
     */
    public void clearSpells() {
        casting_ability_field.setText(""); // set casting ability field to empty
        save_dc_field.setText(""); // set save dc field to empty
        attack_bonus_field.setText(""); // set attack bonus field to empty

        for (int i = 0; i < ROWS; i++) { // loop through 2d array
            for (int j = 0; j < COLS; j++) {
                fields[i][j].setText(""); // set all fields to empty string
            }
        }
    }
}