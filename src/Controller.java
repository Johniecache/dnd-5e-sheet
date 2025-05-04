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
    @FXML private TextField[][] sheet;
    /**
     * Header components
     */
    @FXML private TextField name_tf, player_tf, class_tf, race_tf, background_tf, deity_tf, level_tf, align_tf;
    /**
     * Array for header components
     */
    @FXML private TextField[] headers;
    /**
     * Stat components
     */
    @FXML private TextField ac_tf, hp_tf, in_tf, maxhp_tf, hpdie_tf, temphp_tf, movespeed_tf, passperception_tf;
    /**
     * Array for stats
     */
    @FXML private TextField[] stats;
    /**
     * Score components
     */
    @FXML private TextField str_tf, dex_tf, con_tf, int_tf, wis_tf, char_tf;
    /**
     * Arrayy of scores
     */
    @FXML private TextField[] scores;
    /**
     * Proficiency components
     */
    @FXML private TextField prof_s1_tf, prof_s2_tf, prof_s3_tf, prof_s4_tf, prof_s5_tf, prof_s6_tf,
                                prof_s7_tf, prof_s8_tf, prof_s9_tf, prof_s10_tf, prof_s11_tf, prof_s12_tf;
    /**
     * Array of proficiency slots
     */
    @FXML private TextField[] prof_slots;
    /**
     * Skill components
     */
    @FXML private TextField athletics_tf, acrobatics_tf, sleightofhand_tf, stealth_tf, arcana_tf, history_tf, investigation_tf,
                            nature_tf, religion_tf, animal_tf, insight_tf, medicine_tf, perception_tf, survival_tf, deception_tf,
                            intimidation_tf, performance_tf, persuasion_tf;
    /**
     * Array of skills
     */
    @FXML private TextField[] skills;
    /**
     * Modifier components
     */
    @FXML private TextField str_mod_tf, str_save_tf, dex_mod_tf, dex_save_tf, con_mod_tf, con_save_tf, int_mod_tf,
                            int_save_tf, wis_mod_tf, wis_save_tf, char_mod_tf, char_save_tf;
    /**
     * Array of modifiers and saves
     */
    @FXML private TextField[] mods, saves;
    /**
     * Equipped Equipment components
     */
    @FXML private TextField ee_name_s1, ee_name_s2, ee_name_s3, ee_name_s4, ee_name_s5, ee_name_s6, ee_name_s7,
                            ee_attack_s1, ee_attack_s2, ee_attack_s3, ee_attack_s4, ee_attack_s5, ee_attack_s6, ee_attack_s7,
                            ee_damage_s1, ee_damage_s2, ee_damage_s3, ee_damage_s4, ee_damage_s5, ee_damage_s6, ee_damage_s7;
    /**
     * Array of equipped equipment
     */
    @FXML private TextField[] equipped_equipment;
    /**
     * Currency components
     */
    @FXML private TextField cp_tf, sp_tf, gp_tf, pp_tf;
    /**
     * Array of currency
     */
    @FXML private TextField[] currencies;
    /**
     * Text area for passives
     */
    @FXML private TextArea passives_tf;
    /**
     * Array for text area
     */
    @FXML private TextArea[] misc;

    /**
     * Buttons
     */
    @FXML private Button save_as_button, save_button, load_button, new_button, settings_button, quit_button, randomize_button, inventory_button;
    /**
     * Window
     */
    @FXML private Scene scene;

    /**
     * ButtonManager initialization
     */
    private ButtonManager button;
    /**
     * Settings initialization
     */
    private Settings settings;
    /**
     * Inventory initialization
     */
    private Inventory inventory;
    /**
     * Check if inventory is being shown, default false
     */
    private boolean is_inventory_showing = false;

    /**
     * initializes the character sheet
     */
    public void initialize(){
        settings = new Settings(); // instantiate the settings class
        inventory = new Inventory(this); // instantiates the inventory class
        button = new ButtonManager(settings, this, inventory); // instantiates the buttonmanager class

        save_as_button.setOnAction(event -> button.saveAsCharacter(inventory)); // create save button and action
        save_button.setOnAction(event -> {button.saveCharacter(inventory);}); // create save button and action
        load_button.setOnAction(event -> button.loadCharacter(inventory)); // create load button and action
        new_button.setOnAction(event -> button.newCharacter()); // create new button and action
        settings_button.setOnAction(event -> button.userSettings()); // create setting button and action
        randomize_button.setOnAction(event -> { // create random button and action
            try {button.randomizeCharacter();} // try to randomize character
            catch (JSONException e) {throw new RuntimeException(e);} // catch errors
        });

        inventory_button.setOnAction(event -> { // create new action for the inventory button
            Stage main_stage = (Stage) scene.getWindow(); // set main stage as the controller scene window
            if (inventory == null) { // if the inventory is not null
                inventory = new Inventory(this); // create new window bound to this controller
            }

            if (inventory.isShowingInventory()) { // if inventory is showing
                inventory.hideInventory(); // hide the inventory window
                is_inventory_showing = false; // set inventory showing to false
            } else { // if its not open
                inventory.showInventory(); // show the inventory
                inventory.setX(main_stage.getX() + main_stage.getWidth()); // set x cord
                inventory.setY(main_stage.getY()); // set y cord
                is_inventory_showing = true; // set is showing to true
                if (!inventory.checkListenersAttached()) { // if there are no action listeners listening
                    inventory.addPosListeners(); // create them
                }
            }
        });

        quit_button.setOnAction(event -> button.quitProgram()); // initialize the quit button

        headers = new TextField[]{name_tf, player_tf, class_tf, race_tf, background_tf, deity_tf, level_tf, align_tf}; // create the header array
        stats = new TextField[]{ac_tf, hp_tf, in_tf, maxhp_tf, hpdie_tf, temphp_tf, movespeed_tf, passperception_tf}; // create stats array
        scores = new TextField[]{str_tf, dex_tf, con_tf, int_tf, wis_tf, char_tf}; // create score array
        prof_slots = new TextField[]{prof_s1_tf, prof_s2_tf, prof_s3_tf, prof_s4_tf, prof_s5_tf, prof_s6_tf, prof_s7_tf,
                                        prof_s8_tf, prof_s9_tf, prof_s10_tf, prof_s11_tf, prof_s12_tf}; // create prof_slots array
        skills = new TextField[]{athletics_tf, acrobatics_tf, sleightofhand_tf, stealth_tf, arcana_tf, history_tf, investigation_tf,
                                    nature_tf, religion_tf, animal_tf, insight_tf, medicine_tf, perception_tf, survival_tf, deception_tf,
                                    intimidation_tf, performance_tf, persuasion_tf}; // create skills array
        mods = new TextField[]{str_mod_tf, dex_mod_tf, con_mod_tf, int_mod_tf, wis_mod_tf, char_mod_tf}; // create mods array
        saves = new TextField[]{str_save_tf, dex_save_tf, con_save_tf, int_save_tf, wis_save_tf, char_save_tf}; // create saves array
        equipped_equipment = new TextField[]{ee_name_s1, ee_name_s2, ee_name_s3, ee_name_s4, ee_name_s5, ee_name_s6, ee_name_s7,
                                        ee_attack_s1, ee_attack_s2, ee_attack_s3, ee_attack_s4, ee_attack_s5, ee_attack_s6, ee_attack_s7,
                                        ee_damage_s1, ee_damage_s2, ee_damage_s3, ee_damage_s4, ee_damage_s5, ee_damage_s6, ee_damage_s7}; // create equipped equipment array
        currencies = new TextField[]{cp_tf, sp_tf, gp_tf, pp_tf};
        misc = new TextArea[]{passives_tf}; // create misc array


        sheet = new TextField[][]{headers, stats, scores, prof_slots, skills, equipped_equipment, currencies}; // create 2d sheet array

        for (int i = 0; i < sheet.length; i++) { // loop through sheet
            final int index = i; // create index at i for lambda function
            addListener(sheet[i], (index_index, value) -> { // add a listener
                if (index == 0) {settings.getCurrCharacter().setHeaders(index_index, value);
                }else if(index == 1){settings.getCurrCharacter().setStats(index_index, value);
                }else if(index == 2){settings.getCurrCharacter().setScores(index_index, value);
                }else if(index == 3){settings.getCurrCharacter().setProfSlots(index_index, value);
                }else if(index == 4){settings.getCurrCharacter().setSkills(index_index, value);
                }else if(index == 5){settings.getCurrCharacter().setEquippedEquipment(index_index, value);
                }else if(index == 6){settings.getCurrCharacter().setCurrencies(index_index, value);
                }else if(index == 7){settings.getCurrCharacter().setMisc(index_index, value);
                }
            });
        }

        // Add listeners to the score fields
        for (int i = 0; i < scores.length; i++) {addScoreListener(scores[i], mods[i], saves[i], i);}

        // Set initial values
        for (int i = 0; i < mods.length; i++) {
            mods[i].setText(String.valueOf(settings.getCurrCharacter().getMods(i)));
            saves[i].setText(String.valueOf(settings.getCurrCharacter().getSaves(i)));
        }

        javafx.application.Platform.runLater(this::setWindowIcon);
    }

    /**
     * Handles the loading of data to the sheet
     * @param character current character
     */
    public void loadToSheet(Character character){
        System.out.println("--------------------------------------------------"); // spacing in console
        for (int i = 0; i < sheet.length; i++) {
            for (int j = 0; j < sheet[i].length; j++) { // loop through sheets 2d array
                if (i == 0) {  // headers
                    System.out.println(headers[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getHeaders(j)); // debugging
                    sheet[i][j].setText(character.getHeaders(j)); // set text inside header field from sheet
                } else if (i == 1) {  // stats
                    System.out.println(stats[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getStats(j)); // debugging
                    sheet[i][j].setText(String.valueOf(character.getStats(j))); // set text inside stats field from sheet
                } else if (i == 2) {  // scores
                    System.out.println(scores[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getScores(j)); // debugging
                    sheet[i][j].setText(String.valueOf(character.getScores(j))); // set text inside scores field from sheet
                } else if (i == 3){ // prof slots
                    System.out.println(prof_slots[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getProfSlots(j)); // debugging
                    sheet[i][j].setText(character.getProfSlots(j)); // set text inside prof slots field from sheet
                } else if(i == 4){ // skills
                    System.out.println(skills[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getSkills(j)); // debugging
                    sheet[i][j].setText(String.valueOf(character.getSkills(j))); // set text inside skills field from sheet
                    if(sheet[i][j].getText().equals("0")){sheet[i][j].setText("");} // set anything that's 0 as an empty string
                } else if(i == 5){ // equipped equipment
                    System.out.println(equipped_equipment[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getEquippedEquipment(j)); // debugging
                    sheet[i][j].setText(character.getEquippedEquipment(j)); // set text inside equipped equipment field from sheet
                } else if(i == 6){ // currencies
                    System.out.println(currencies[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getCurrencies(j)); // debugging
                    sheet[i][j].setText((String.valueOf(character.getCurrencies(j)))); // set text inside currencies field from sheet
                } else if(i == 7) { // info
                    System.out.println(misc[j].idProperty().getValue() + ": " + settings.getCurrCharacter().getMisc(j)); // debugging
                    sheet[i][j].setText(character.getMisc(j)); // set text inside misc field from sheet
                }
            }
        }
        System.out.println("--------------------------------------------------"); // spacing in console
    }

    boolean is_updating = true; // set text fields as listening to true

    /**
     * Basic listener for all but modifier and save text fields
     * @param tf_arr text field array
     * @param update_function function to update
     */
    private void addListener(TextField[] tf_arr, BiConsumer<Integer, String> update_function) {
        if (tf_arr == null) return; // if the text field is null return early
        for (int i = 0; i < tf_arr.length; i++) { // loop through text field array
            final int index = i; // set index as i for lambda equation
            tf_arr[i].textProperty().addListener((obs, old_value, new_value) -> { // add the listener at i in array
                update_function.accept(index, new_value); // accept new value from the text field
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
    private void addScoreListener(TextField score_field, TextField mod_field, TextField save_field, int index) {
        score_field.textProperty().addListener((obs, old_val, new_val) -> { // check score value
            if (new_val.isEmpty()) return; // if its empty return early

            try { // handle errors
                int score = Integer.parseInt(new_val); // set score to parsed integer
                int mod = settings.getScoreMod(score); // modifier set to equation in settings with passed score

                settings.getCurrCharacter().setScores(index, new_val); // get current character and set scores
                settings.getCurrCharacter().setMods(index, String.valueOf(mod)); // get current character and set modifiers
                settings.getCurrCharacter().setSaves(index, String.valueOf(mod)); // get current character and set saves

                is_updating = true; // set updating to true

                javafx.application.Platform.runLater(() -> { // set following to run later on a different thread
                    mod_field.setText(String.valueOf(mod)); // set textfield as mod
                    save_field.setText(String.valueOf(mod)); // set save as mod
                });

            } catch (NumberFormatException e) { // catch errors
                javafx.application.Platform.runLater(() -> score_field.setText(old_val)); // set score text field to run later on a new thread
            } finally {
                is_updating = false; // set updating to false
            }
        });
    }

    /**
     * Getter for controllers main window
     * @return scene
     */
    public Scene getScene(){return this.scene;}

    /**
     * Gets the name text from text field
     * @return name text field text
     */
    public String getNameTF(){return this.name_tf.getText();}

    /**
     * Sets the main window scene
     * @param scene scene
     */
    public void setScene(Scene scene){this.scene = scene;}

    /**
     * Sets the window icon.
     */
    private void setWindowIcon() {
        Window window = scene.getWindow(); // set variable to scene's window
        if (window instanceof Stage) { // if its a stage
            Stage stage = (Stage) window; // cast stage to window
            stage.getIcons().add(settings.getAppIcon()); // get the icon from settings
            System.out.println("Current Window: " + window); // debugging
            System.out.println("Current Stage: " + stage); // debugging
        } else {System.out.println("Stage and Window are not the same");} // tell user window and stage are not the same
    }
}
