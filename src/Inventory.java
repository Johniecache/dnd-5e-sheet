import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates a new window of a grid that will be editable by the user which will act as their inventory.
 * 3 columns (name, attributes, worth) but these can be whatever the user wants.
 */
public class Inventory extends Stage {
    /**
     * Importing and instantiating settings
     */
    private static final Settings settings = new Settings();
    /**
     * Importing and instantiating controller
     */
    private final Controller controller;
    /**
     * Importing rows as a static integer from settings
     */
    private static final int ROWS = settings.getInventoryRows();
    /**
     * Importing columns as a static integer from settings
     */
    private static final int COLS = settings.getInventoryColumns();
    /**
     * Instantiating a 2d array that will hold the rows and columns that where imported.
     */
    private final TextField[][] fields = new TextField[ROWS][COLS];
    /**
     * Checks to see if window is open and listening. Used for binding windows
     */
    private boolean check_pos_listener = false;

    /**
     * Default constructor for the Inventory class. Requires controller for binding windows.
     * @param controller controller
     */
    public Inventory(Controller controller) {
        this.controller = controller; // set passed controller to instance controller
        initModality(Modality.NONE); //
        setTitle("Inventory"); // set the title of the window

        GridPane grid = new GridPane(); // instantiate a grid for the inventory window

        for (int i=0;i<ROWS;i++) {
            for (int j=0;j<COLS;j++) { // loop through 2d array (rows & columns)
                fields[i][j] = new TextField(); // create a new text field per row/column area
                fields[i][j].setPromptText(switch (j){ // add prompt text in each row/column and change on each column
                    case 0 -> "Name"; // first column is name section
                    case 1 -> "Attribute"; // second column is attribute section
                    case 2 -> "Worth"; // last column is worth section
                    default -> ""; // if the section is unknown then make it empty
                });
                grid.add(fields[i][j],j,i); // add these to the grid
            }
        }

        setScene(new Scene(grid)); // add grid to the scene from the controller
    }

    /**
     * Checks whether the listeners are attached or not
     * @return boolean of check_pos_listener
     */
    public boolean checkListenersAttached() {
        return check_pos_listener;
    }

    /**
     * Attaches position listeners to find the x and y cord of attached window.
     */
    public void addPosListeners() {
        if (controller != null && controller.getScene() != null && controller.getScene().getWindow() instanceof Stage main_stage) { // checks the controller for scene exists and checks main window to see if it exists
            main_stage.xProperty().addListener((obs, old_val, new_val) -> { // create a new listener for x cord
                setX(new_val.doubleValue() + main_stage.getWidth()); // set old x cord with new x cord
            });
            main_stage.yProperty().addListener((obs, old_val, new_val) -> { // create a new listener for y cord
                setY(new_val.doubleValue()); // set old y cord with new y cord
            });
            check_pos_listener = true; // set the listener to true
        }
    }

    /**
     * If the main window exists and is showing then it will show the inventory window
     */
    public void showInventory() {
        if (getOwner() == null && controller != null && controller.getScene() != null && controller.getScene().getWindow() != null) { // makes sure the main window exists and is showing
            initOwner(controller.getScene().getWindow()); // set the window's main as the controller window
        }
        super.show(); // call parent class of the show method
    }

    /**
     * Hides the inventory window
     */
    public void hideInventory() {
        super.hide(); // call parent class of the hide method
    }

    /**
     * Gets the data at each row/column and returns that as a new 2d array
     * @return data
     */
    public String[][] getInventoryData() {
        String[][] data = new String[ROWS][COLS]; // create new array that will hold the return data
        for (int i=0;i<ROWS;i++) { // loop through rows
            for (int j=0;j<COLS;j++) { // loop through columns
                data[i][j] = fields[i][j].getText(); // set the text inside the inventory 2d array as the return data array
            }
        }
        return data; // return array once all data is put from inventory to data array
    }

    /**
     * Sets the inventory with passed data
     * @param data data
     */
    public void setInventoryData(String[][] data) {
        for (int i=0;i<Math.min(ROWS,data.length);i++) { // loop through rows
            for (int j=0;j<Math.min(COLS,data[i].length);j++) { // loop through columns
                fields[i][j].setText(data[i][j]); // set the inventory 2d array to that of the input data 2d array
            }
        }
    }

    /**
     * Checks whether or not the inventory is showing currently
     * @return boolean isShowing() method
     */
    public boolean isShowingInventory() {
        return isShowing(); // window method that checks if window is currently showing on screen
    }

    /**
     * Clears the inventory, usually when a new character is created
     */
    public void clearInventory(){
        for (int i = 0; i < ROWS; i++) { // loop through each row
            for (int j = 0; j < COLS; j++) { // loop through each column
                fields[i][j].setText(""); // set the text of the current TextField to an empty string
            }
        }
    }
}