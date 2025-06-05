import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONException;

import java.util.function.BiConsumer;

public class Controller {
    /**
     * 2D array of sheet components
     */
    @FXML private TextField[][] sheet; // declares a 2D array to hold all TextField components of the character sheet, injected by FXML
    /**
     * Header components
     */
    @FXML private TextField name_tf, player_tf, class_tf, race_tf, background_tf, deity_tf, level_tf, align_tf; // declares TextFields for character header information, injected by FXML
    /**
     * Array for header components
     */
    @FXML private TextField[] headers; // declares an array to group the header TextFields
    /**
     * Stat components
     */
    @FXML private TextField ac_tf, hp_tf, in_tf, maxhp_tf, hpdie_tf, temphp_tf, movespeed_tf, passperception_tf; // declares TextFields for character statistics, injected by FXML
    /**
     * Array for stats
     */
    @FXML private TextField[] stats; // declares an array to group the statistics TextFields
    /**
     * Score components
     */
    @FXML private TextField str_tf, dex_tf, con_tf, int_tf, wis_tf, char_tf; // declares TextFields for ability scores, injected by FXML
    /**
     * Arrayy of scores
     */
    @FXML private TextField[] scores; // declares an array to group the ability score TextFields
    /**
     * Proficiency components
     */
    @FXML private TextField prof_s1_tf, prof_s2_tf, prof_s3_tf, prof_s4_tf, prof_s5_tf, prof_s6_tf,
            prof_s7_tf, prof_s8_tf, prof_s9_tf, prof_s10_tf, prof_s11_tf, prof_s12_tf; // declares TextFields for proficiency slots, injected by FXML
    /**
     * Array of proficiency slots
     */
    @FXML private TextField[] prof_slots; // declares an array to group the proficiency slot TextFields
    /**
     * Skill components
     */
    @FXML private TextField athletics_tf, acrobatics_tf, sleightofhand_tf, stealth_tf, arcana_tf, history_tf, investigation_tf,
            nature_tf, religion_tf, animal_tf, insight_tf, medicine_tf, perception_tf, survival_tf, deception_tf,
            intimidation_tf, performance_tf, persuasion_tf; // declares TextFields for various skills, injected by FXML
    /**
     * Array of skills
     */
    @FXML private TextField[] skills; // declares an array to group the skill TextFields
    /**
     * Modifier components
     */
    @FXML private TextField str_mod_tf, str_save_tf, dex_mod_tf, dex_save_tf, con_mod_tf, con_save_tf, int_mod_tf,
            int_save_tf, wis_mod_tf, wis_save_tf, char_mod_tf, char_save_tf; // declares TextFields for ability modifiers and saving throws, injected by FXML
    /**
     * Array of modifiers and saves
     */
    @FXML private TextField[] mods, saves; // declares arrays to group the modifier and saving throw TextFields
    /**
     * Equipped Equipment components
     */
    @FXML private TextField ee_name_s1, ee_name_s2, ee_name_s3, ee_name_s4, ee_name_s5, ee_name_s6, ee_name_s7,
            ee_attack_s1, ee_attack_s2, ee_attack_s3, ee_attack_s4, ee_attack_s5, ee_attack_s6, ee_attack_s7,
            ee_damage_s1, ee_damage_s2, ee_damage_s3, ee_damage_s4, ee_damage_s5, ee_damage_s6, ee_damage_s7; // declares TextFields for equipped equipment details, injected by FXML
    /**
     * Array of equipped equipment
     */
    @FXML private TextField[] equipped_equipment; // declares an array to group the equipped equipment TextFields
    /**
     * Currency components
     */
    @FXML private TextField cp_tf, sp_tf, gp_tf, pp_tf; // declares TextFields for different currency types, injected by FXML
    /**
     * Array of currency
     */
    @FXML private TextField[] currencies; // declares an array to group the currency TextFields
    /**
     * Text area for passives
     */
    @FXML private TextArea passives_tf; // declares a TextArea for passive abilities, injected by FXML
    /**
     * Array for text area
     */
    @FXML private TextArea[] misc; // declares an array to group miscellaneous TextAreas

    /**
     * Buttons
     */
    @FXML private Button save_as_button, save_button, load_button, new_button, settings_button, quit_button, randomize_button, inventory_button, spells_button, information_button; // declares all Button components, injected by FXML
    /**
     * Window
     */
    @FXML private Scene scene; // declares a Scene object to represent the main application window's scene

    /**
     * ButtonManager initialization
     */
    private ButtonManager button; // declares a private field for the ButtonManager
    /**
     * Settings initialization
     */
    private Settings settings; // declares a private field for the Settings
    /**
     * Inventory initialization
     */
    private Inventory inventory; // declares a private field for the Inventory window
    /**
     * Spell book initialization
     */
    private Spells spells; // declares a private field for the Spells window
    /**
     * Information initialization
     */
    private Information information; // declares a private field for the Information window
    /**
     * Check if inventory is being shown, default false
     */
    private boolean is_inventory_showing = false; // declares a boolean to track if the inventory window is currently open, initialized to false
    /**
     * Check if spell book is showing, default false
     */
    private boolean is_spells_showing = false; // declares a boolean to track if the spells window is currently open, initialized to false
    /**
     * Check if information window is showing
     */
    private boolean is_information_showing = false; // declares a boolean to track if the information window is currently open, initialized to false

    /**
     * initializes the character sheet
     */
    public void initialize(){ // defines the initialize method, which is automatically called after FXML injection
        settings = new Settings(); // instantiates a new Settings object
        inventory = new Inventory(this); // instantiates a new Inventory window, passing this controller as its parent
        spells = new Spells(this); // instantiates a new Spells window, passing this controller as its parent
        information = new Information(this); // instantiates a new Information window, passing this controller as its parent
        button = new ButtonManager(settings, this, inventory, spells, information); // instantiates a new ButtonManager, passing relevant objects to it

        save_as_button.setOnAction(event -> button.saveAsCharacter()); // sets the action for the save_as_button to call the saveAsCharacter method in button
        save_button.setOnAction(event -> {button.saveCharacter();}); // sets the action for the save_button to call the saveCharacter method in button
        load_button.setOnAction(event -> button.loadCharacter()); // sets the action for the load_button to call the loadCharacter method in button
        new_button.setOnAction(event -> button.newCharacter()); // sets the action for the new_button to call the newCharacter method in button
        settings_button.setOnAction(event -> button.userSettings()); // sets the action for the settings_button to call the userSettings method in button
        randomize_button.setOnAction(event -> { // sets the action for the randomize_button
            try {button.randomizeCharacter();} // attempts to call the randomizeCharacter method in button, which might throw a JSONException
            catch (JSONException e) {throw new RuntimeException(e);} // catches a JSONException and re-throws it as an unchecked RuntimeException
        });

        inventory_button.setOnAction(event -> { // sets the action for the inventory_button
            Stage main_stage = (Stage) scene.getWindow(); // gets the main application window (Stage) from the current scene
            if (inventory == null) { // checks if the inventory window object is null
                inventory = new Inventory(this); // if null, instantiates a new Inventory window, binding it to this controller
            }

            if (inventory.isShowingInventory()) { // checks if the inventory window is currently visible
                inventory.hideInventory(); // hides the inventory window
                is_inventory_showing = false; // updates the boolean flag to false
            } else { // if the inventory window is not currently visible
                inventory.showInventory(); // displays the inventory window
                inventory.setX(main_stage.getX() + main_stage.getWidth()); // sets the X coordinate of the inventory window to be immediately to the right of the main window
                inventory.setY(main_stage.getY()); // sets the Y coordinate of the inventory window to be the same as the main window
                is_inventory_showing = true; // updates the boolean flag to true
                if (!inventory.checkListenersAttached()) { // checks if position listeners are not already attached to the inventory window
                    inventory.addPosListeners(); // adds position listeners to the inventory window, linking its movement to the main window
                }
            }
        });

        spells_button.setOnAction(event -> { // sets the action for the spells_button
            Stage main_stage = (Stage) scene.getWindow(); // gets the main application window (Stage) from the current scene
            if (spells == null) { // checks if the spells window object is null
                spells = new Spells(this); // if null, instantiates a new Spells window, binding it to this controller
            }

            if (spells.isShowingSpells()) { // checks if the spells window is currently visible
                spells.hideSpells(); // hides the spells window
                is_spells_showing = false; // updates the boolean flag to false
            } else { // if the spells window is not currently visible
                spells.showSpells(); // displays the spells window
                spells.setX(main_stage.getX() - spells.getWidth()); // sets the X coordinate of the spells window to be immediately to the left of the main window
                spells.setY(main_stage.getY()); // sets the Y coordinate of the spells window to be the same as the main window
                is_spells_showing = true; // updates the boolean flag to true
                if (!spells.checkListenersAttached()) { // checks if position listeners are not already attached to the spells window
                    spells.addPosListeners(main_stage); // adds position listeners to the spells window, linking its movement to the main window
                }
            }
        });

        information_button.setOnAction(event -> { // sets the action for the information_button
            Stage main_stage = (Stage) scene.getWindow(); // gets the main application window (Stage) from the current scene
            if (information == null) { // checks if the information window object is null
                information = new Information(this); // if null, instantiates a new Information window, binding it to this controller
            }

            if (information.isShowingInformation()) { // checks if the information window is currently visible
                information.hideInformation(); // hides the information window
                is_information_showing = false; // updates the boolean flag to false
            } else { // if the information window is not currently visible
                information.showInformation(); // displays the information window
                // Position the information window directly above the main window
                information.setX(main_stage.getX()); // sets the X position of the information window to be the same as the main window
                information.setY(main_stage.getY() - information.getHeight()); // sets the Y position of the information window to be directly above the main window
                is_information_showing = true; // updates the boolean flag to true
                if (!information.checkListenersAttached()) { // checks if position listeners are not already attached to the information window
                    information.addPosListeners(main_stage); // adds position listeners to the information window, linking its movement to the main window
                }
            }
        });


        quit_button.setOnAction(event -> button.quitProgram()); // sets the action for the quit_button to call the quitProgram method in button

        headers = new TextField[]{name_tf, player_tf, class_tf, race_tf, background_tf, deity_tf, level_tf, align_tf}; // initializes the 'headers' array with references to the header TextFields
        stats = new TextField[]{ac_tf, hp_tf, in_tf, maxhp_tf, hpdie_tf, temphp_tf, movespeed_tf, passperception_tf}; // initializes the 'stats' array with references to the statistics TextFields
        scores = new TextField[]{str_tf, dex_tf, con_tf, int_tf, wis_tf, char_tf}; // initializes the 'scores' array with references to the ability score TextFields
        prof_slots = new TextField[]{prof_s1_tf, prof_s2_tf, prof_s3_tf, prof_s4_tf, prof_s5_tf, prof_s6_tf, prof_s7_tf,
                prof_s8_tf, prof_s9_tf, prof_s10_tf, prof_s11_tf, prof_s12_tf}; // initializes the 'prof_slots' array with references to the proficiency slot TextFields
        skills = new TextField[]{athletics_tf, acrobatics_tf, sleightofhand_tf, stealth_tf, arcana_tf, history_tf, investigation_tf,
                nature_tf, religion_tf, animal_tf, insight_tf, medicine_tf, perception_tf, survival_tf, deception_tf,
                intimidation_tf, performance_tf, persuasion_tf}; // initializes the 'skills' array with references to the skill TextFields
        mods = new TextField[]{str_mod_tf, dex_mod_tf, con_mod_tf, int_mod_tf, wis_mod_tf, char_mod_tf}; // initializes the 'mods' array with references to the modifier TextFields
        saves = new TextField[]{str_save_tf, dex_save_tf, con_save_tf, int_save_tf, wis_save_tf, char_save_tf}; // initializes the 'saves' array with references to the saving throw TextFields
        equipped_equipment = new TextField[]{ee_name_s1, ee_name_s2, ee_name_s3, ee_name_s4, ee_name_s5, ee_name_s6, ee_name_s7,
                ee_attack_s1, ee_attack_s2, ee_attack_s3, ee_attack_s4, ee_attack_s5, ee_attack_s6, ee_attack_s7,
                ee_damage_s1, ee_damage_s2, ee_damage_s3, ee_damage_s4, ee_damage_s5, ee_damage_s6, ee_damage_s7}; // initializes the 'equipped_equipment' array with references to the equipped equipment TextFields
        currencies = new TextField[]{cp_tf, sp_tf, gp_tf, pp_tf}; // initializes the 'currencies' array with references to the currency TextFields
        misc = new TextArea[]{passives_tf}; // initializes the 'misc' array with references to the miscellaneous TextArea

        sheet = new TextField[][]{headers, stats, scores, prof_slots, skills, equipped_equipment, currencies}; // initializes the 'sheet' 2D array, grouping all TextField arrays

        for (int i = 0; i < sheet.length; i++) { // iterates through each row (array of TextFields) in the 'sheet' 2D array
            final int index = i; // declares a final variable 'index' to capture the outer loop's 'i' for use in the lambda expression
            addListener(sheet[i], (index_index, value) -> { // calls addListener for the current row's TextField array and a lambda function
                if (index == 0) {settings.getCurrCharacter().setHeaders(index_index, value); // if the current row is for headers, updates the character's header at the given inner index with the new value
                }else if(index == 1){settings.getCurrCharacter().setStats(index_index, value); // if the current row is for stats, updates the character's stat at the given inner index with the new value
                }else if(index == 2){settings.getCurrCharacter().setScores(index_index, value); // if the current row is for scores, updates the character's score at the given inner index with the new value
                }else if(index == 3){settings.getCurrCharacter().setProfSlots(index_index, value); // if the current row is for proficiency slots, updates the character's proficiency slot at the given inner index with the new value
                }else if(index == 4){settings.getCurrCharacter().setSkills(index_index, value); // if the current row is for skills, updates the character's skill at the given inner index with the new value
                }else if(index == 5){settings.getCurrCharacter().setEquippedEquipment(index_index, value); // if the current row is for equipped equipment, updates the character's equipped equipment at the given inner index with the new value
                }else if(index == 6){settings.getCurrCharacter().setCurrencies(index_index, value); // if the current row is for currencies, updates the character's currency at the given inner index with the new value
                }
            });
        }
        // Listener for the misc TextArea (since it's not part of the `sheet` 2D array of TextFields)
        if (misc != null && misc.length > 0) { // checks if the misc array is initialized and not empty
            misc[0].textProperty().addListener((obs, old_value, new_value) -> { // adds a listener to the textProperty of the first (and only) TextArea in the misc array
                settings.getCurrCharacter().setMisc(0, new_value); // updates the character's miscellaneous data at index 0 with the new value
            });
        }


        // Add listeners to the score fields
        for (int i = 0; i < scores.length; i++) {addScoreListener(scores[i], mods[i], saves[i], i);} // iterates through the 'scores' array and adds a specific listener for score, modifier, and save fields

        // Set initial values
        for (int i = 0; i < mods.length; i++) { // iterates through the 'mods' array
            mods[i].setText(String.valueOf(settings.getCurrCharacter().getMods(i))); // sets the text of the current modifier TextField to the character's corresponding modifier value
            saves[i].setText(String.valueOf(settings.getCurrCharacter().getSaves(i))); // sets the text of the current save TextField to the character's corresponding save value
        }

        javafx.application.Platform.runLater(this::setWindowIcon); // schedules the setWindowIcon method to be executed on the JavaFX Application Thread later
    }

    /**
     * Handles the loading of data to the sheet
     * @param character current character
     */
    public void loadToSheet(Character character){ // defines a method to load character data onto the UI sheet
        System.out.println("--------------------------------------------------"); // prints a separator line to the console for debugging
        for (int i = 0; i < sheet.length; i++) { // iterates through each category of TextFields in the 'sheet' 2D array
            for (int j = 0; j < sheet[i].length; j++) { // iterates through each TextField within the current category
                if (i == 0) {  // headers
                    System.out.println(headers[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getHeaders(j)); // debugging: prints the ID and value of the current header field to the console
                    sheet[i][j].setText(character.getHeaders(j)); // sets the text of the current header TextField from the character's data
                } else if (i == 1) {  // stats
                    System.out.println(stats[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getStats(j)); // debugging: prints the ID and value of the current stat field to the console
                    sheet[i][j].setText(String.valueOf(character.getStats(j))); // sets the text of the current stat TextField from the character's data, converting it to a String
                } else if (i == 2) {  // scores
                    System.out.println(scores[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getScores(j)); // debugging: prints the ID and value of the current score field to the console
                    sheet[i][j].setText(String.valueOf(character.getScores(j))); // sets the text of the current score TextField from the character's data, converting it to a String
                } else if (i == 3){ // prof slots
                    System.out.println(prof_slots[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getProfSlots(j)); // debugging: prints the ID and value of the current proficiency slot field to the console
                    sheet[i][j].setText(character.getProfSlots(j)); // sets the text of the current proficiency slot TextField from the character's data
                } else if(i == 4){ // skills
                    System.out.println(skills[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getSkills(j)); // debugging: prints the ID and value of the current skill field to the console
                    sheet[i][j].setText(String.valueOf(character.getSkills(j))); // sets the text of the current skill TextField from the character's data, converting it to a String
                    if(sheet[i][j].getText().equals("0")){sheet[i][j].setText("");} // if the skill value is "0", sets the TextField text to an empty string for cleaner display
                } else if(i == 5){ // equipped equipment
                    System.out.println(equipped_equipment[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getEquippedEquipment(j)); // debugging: prints the ID and value of the current equipped equipment field to the console
                    sheet[i][j].setText(character.getEquippedEquipment(j)); // sets the text of the current equipped equipment TextField from the character's data
                } else if(i == 6){ // currencies
                    System.out.println(currencies[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getCurrencies(j)); // debugging: prints the ID and value of the current currency field to the console
                    sheet[i][j].setText((String.valueOf(character.getCurrencies(j)))); // sets the text of the current currency TextField from the character's data, converting it to a String
                }
            }
        }
        // Load the miscellaneous text area separately, as it's a TextArea, not a TextField, and not part of the `sheet` 2D array.
        if (misc != null && misc.length > 0) { // checks if the misc array is initialized and not empty
            System.out.println(misc[0].idProperty().getValue() + ": " + settings.getCurrCharacter().getMisc(0)); // debugging: prints the ID and value of the misc text area to the console
            misc[0].setText(character.getMisc(0)); // sets the text of the misc TextArea from the character's data
        }
        System.out.println("--------------------------------------------------"); // prints another separator line to the console for debugging
    }

    boolean is_updating = true; // declares a boolean flag to control updates, initialized to true

    /**
     * Basic listener for all but modifier and save text fields
     * @param tf_arr text field array
     * @param update_function function to update
     */
    private void addListener(TextField[] tf_arr, BiConsumer<Integer, String> update_function) { // defines a private method to add a generic listener to an array of TextFields
        if (tf_arr == null) return; // if the input TextField array is null, exits the method early
        for (int i = 0; i < tf_arr.length; i++) { // iterates through each TextField in the array
            final int index = i; // declares a final variable 'index' to capture the current loop iteration's 'i' for use in the lambda expression
            tf_arr[i].textProperty().addListener((obs, old_value, new_value) -> { // adds a listener to the textProperty of the current TextField
                update_function.accept(index, new_value); // calls the provided update_function, passing the current index and the new text value
            });
        }
    }

    /**
     * Listener for modifiers and saves text field to update based on score number
     * @param score_field Score
     * @param mod_field Modifier
     * @param save_field Save
     * @param index position
     */
    private void addScoreListener(TextField score_field, TextField mod_field, TextField save_field, int index) { // defines a private method to add a specific listener for ability score fields
        score_field.textProperty().addListener((obs, old_val, new_val) -> { // adds a listener to the textProperty of the score TextField
            if (new_val.isEmpty()) return; // if the new value of the score field is empty, exits the method early

            try { // starts a try block to handle potential NumberFormatException
                int score = Integer.parseInt(new_val); // attempts to parse the new text value as an integer (the ability score)
                int mod = settings.getScoreMod(score); // calculates the ability modifier based on the parsed score using the settings object

                settings.getCurrCharacter().setScores(index, new_val); // updates the character's score at the given index with the new value
                settings.getCurrCharacter().setMods(index, String.valueOf(mod)); // updates the character's modifier at the given index with the calculated modifier (as a String)
                settings.getCurrCharacter().setSaves(index, String.valueOf(mod)); // updates the character's save at the given index with the calculated modifier (as a String)

                is_updating = true; // sets the 'is_updating' flag to true (to prevent re-triggering listeners while programmatically setting text)

                javafx.application.Platform.runLater(() -> { // schedules the following code to be executed on the JavaFX Application Thread
                    mod_field.setText(String.valueOf(mod)); // sets the text of the modifier TextField to the calculated modifier (as a String)
                    save_field.setText(String.valueOf(mod)); // sets the text of the save TextField to the calculated modifier (as a String)
                });

            } catch (NumberFormatException e) { // catches a NumberFormatException if the new value is not a valid integer
                javafx.application.Platform.runLater(() -> score_field.setText(old_val)); // schedules the score TextField's text to be reverted to its old value on the JavaFX Application Thread
            } finally {
                is_updating = false; // sets the 'is_updating' flag to false, regardless of whether an error occurred
            }
        });
    }

    /**
     * Getter for controllers main window
     * @return scene
     */
    public Scene getScene(){return this.scene;} // defines a public getter method for the main application Scene

    /**
     * Gets the name text from text field
     * @return name text field text
     */
    public String getNameTF(){return this.name_tf.getText();} // defines a public getter method to retrieve the text from the name TextField

    /**
     * Sets the main window scene
     * @param scene scene
     */
    public void setScene(Scene scene){this.scene = scene;} // defines a public setter method to set the main application Scene

    /**
     * Sets the window icon.
     */
    private void setWindowIcon() { // defines a private method to set the icon of the main application window
        Window window = scene.getWindow(); // gets the Window object associated with the current scene
        if (window instanceof Stage) { // checks if the Window object is an instance of a Stage
            Stage stage = (Stage) window; // casts the Window object to a Stage
            stage.getIcons().add(settings.getAppIcon()); // adds the application icon (obtained from settings) to the stage's icon list
            System.out.println("Current Window: " + window); // debugging: prints the current Window object to the console
            System.out.println("Current Stage: " + stage); // debugging: prints the current Stage object to the console
        } else {System.out.println("Stage and Window are not the same");} // if the window is not a Stage, prints a message to the console
    }
}