import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates a new window of a grid that will be editable by the user which will act as their spellbook.
 * 5 columns: name, level, slot, description, proficient
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
     * set instance rows to settings hard coded value
     */
    private static final int ROWS = settings.getSpellRows();
    /**
     * set instance columns to settings hard coded value
     */
    private static final int COLS = settings.getSpellColumns();
    /**
     * create 2d array to hold name, level, slot, description of specific spell
     */
    private final TextField[][] fields = new TextField[ROWS][COLS - 1];
    /**
     * create another array to hold checkboxes
     */
    private final CheckBox[] prof_checks = new CheckBox[ROWS];
    /**
     * boolean to check listener
     */
    private boolean check_pos_listener = false;

    /**
     * default constructor for the spells class
     * @param controller instance controller
     */
    public Spells(Controller controller) {
        this.controller = controller; // set passed controller as instance controller
        initModality(Modality.NONE); // create how the stage interacts with other windows ie keep all other windows as interactive
        setTitle("Spell book"); // set title of window

        GridPane grid = new GridPane(); // create new grid to hold text fields

        for (int i = 0; i < ROWS; i++) { // cycle through rows
            for (int j = 0; j < COLS - 1; j++) { // cycle through columns (2d array)
                fields[i][j] = new TextField(); // set new text field at each value
                fields[i][j].setPromptText(switch (j) { // set prompt text in each text field depending on column
                    case 0 -> "Name"; // first column is names
                    case 1 -> "Level"; // second column is levels
                    case 2 -> "Slot"; // third column is slots
                    case 3 -> "Description"; // fourth column is descriptions
                    default -> ""; // if not in case then set it as empty
                });
                grid.add(fields[i][j], j, i); // set these as text fields in grid
            }
            prof_checks[i] = new CheckBox(); // create new checkboxes for each row
            grid.add(prof_checks[i], COLS - 1, i); // add it to the grid
        }

        setScene(new Scene(grid)); // create new scene with grid

        addPosListeners(); // add listeners to the text fields to update values
    }

    /**
     * returns the boolean of check_pos_listener for public access
     * @return check_pos_listener
     */
    public boolean checkListenersAttached() {
        return check_pos_listener;
    }

    /**
     * Adds position listeners to the window. This will allow for binding it to the main window when the main window is moved
     */
    public void addPosListeners() {
        if (controller == null || controller.getScene() == null || !(controller.getScene().getWindow() instanceof Stage mainStage)) return; // check if the main window exists, if not return early

        initOwner(mainStage);
        mainStage.xProperty().addListener((obs, old_x, new_x) -> setX(new_x.doubleValue() - getWidth())); // set x as new x value
        mainStage.yProperty().addListener((obs, old_y, new_y) -> setY(new_y.doubleValue())); // set y as new y value
        mainStage.widthProperty().addListener((obs, old_w, new_w) -> setX(mainStage.getX() - getWidth())); // set w as new x value
        mainStage.heightProperty().addListener((obs, old_h, new_h) -> setY(mainStage.getY())); // set h as new y value

        check_pos_listener = true; // set pos listener to true
    }

    /**
     * Shows the spells window
     */
    public void showSpells() {
        if (getOwner() == null && controller != null && controller.getScene() != null && controller.getScene().getWindow() != null) { // check if the main window is showing
            initOwner(controller.getScene().getWindow()); // if not then try to get the window scene
        }
        super.show(); // show the window
    }

    /**
     * Hides the spell window
     */
    public void hideSpells() {
        super.hide(); // hide the window
    }

    /**
     * Boolean to check if the window is currently showing
     * @return isShowing
     */
    public boolean isShowingSpells() {
        return isShowing();
    }

    /**
     * Gets the data from the window
     * @return spell data
     */
    public String[][] getSpellData() {
        String[][] data = new String[ROWS][COLS]; // create 2d array
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 1; j++) { // loop through 2d array
                data[i][j] = fields[i][j].getText(); // set field values as the 2d array position
            }
            data[i][COLS - 1] = String.valueOf(prof_checks[i].isSelected()); // create the string value of this
        }
        return data; // return the data
    }

    /**
     * Sets the data to the window
     * @param data spell data
     */
    public void setSpellData(String[][] data) {
        for (int i = 0; i < Math.min(ROWS, data.length); i++) {
            for (int j = 0; j < Math.min(COLS - 1, data[i].length); j++) { // loop through the passed 2d array
                fields[i][j].setText(data[i][j]); // set the fields as the passed data
            }
            if (data[i].length == COLS) { // check if data has same number of columns
                prof_checks[i].setSelected(Boolean.parseBoolean(data[i][COLS - 1])); // if so select and parse
            }
        }
    }

}
