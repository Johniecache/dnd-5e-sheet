import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.stage.Window;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles the managing of files (load/save)
 */
public class FileManager {
    Settings settings = new Settings(); // instantiate settings

    /**
     * Saves passed values to a .json file
     * @param file current file
     * @param character current character
     * @param window main window
     * @param inventory inventory window
     */
    public void saveToFile(File file, Character character, Window window, Inventory inventory, Spells spells, Information information) {
        try (FileWriter writer = new FileWriter(file)) { // handle errors with grace

            JSONObject headers = new JSONObject(); // create new json object for headers
            String[] header_values = character.getHeaders(); // create new array to hold values
            headers.put("name", header_values[0]); // put name in headers
            headers.put("player", header_values[1]); // put player in headers
            headers.put("class", header_values[2]); // put class in headers
            headers.put("race", header_values[3]); // put race in headers
            headers.put("background", header_values[4]); // put background in headers
            headers.put("deity", header_values[5]); // put deity in headers
            headers.put("level", header_values[6]); // put level in headers
            headers.put("alignment", header_values[7]); // put alignment in headers

            JSONObject stats = new JSONObject(); // create new json object for stats
            int[] stat_values = character.getStats(); // create new array to hold values
            stats.put("AC", stat_values[0]); // put AC in stats
            stats.put("HP", stat_values[1]); // put HP in stats
            stats.put("initiative", stat_values[2]); // put initiative in stats
            stats.put("maxHP", stat_values[3]); // put maxHP in stats
            stats.put("HPDie", stat_values[4]); // put HPDie in stats
            stats.put("tempHP", stat_values[5]); // put tempHP in stats
            stats.put("move_speed", stat_values[6]); // put mpve_speed in stats
            stats.put("passive_perception", stat_values[7]); // put passive_perception in stats

            JSONObject scores = new JSONObject(); // new json object for scores
            int[] score_values = character.getScores(); // create new array to hold values
            scores.put("str", score_values[0]); // put str in scores
            scores.put("dex", score_values[1]); // put dex in scores
            scores.put("con", score_values[2]); // put con in scores
            scores.put("int", score_values[3]); // put int in scores
            scores.put("wis", score_values[4]); // put wis in scores
            scores.put("char", score_values[5]); // put char in scores

            JSONObject prof_slots = new JSONObject(); // json object to hold prof slots
            String[] prof_slot_values = character.getProfSlots(); // create new array to hold values
            for (int i = 0; i < prof_slot_values.length; i++) {prof_slots.put("slot " + (i + 1), prof_slot_values[i]);}

            JSONObject skills = new JSONObject(); // new json object to hold skills
            int[] skill_values = character.getSkills(); // create new array to hold values
            skills.put("athletics", skill_values[0]);
            skills.put("acrobatics", skill_values[1]);
            skills.put("sleight_of_hand", skill_values[2]);
            skills.put("stealth", skill_values[3]);
            skills.put("arcana", skill_values[4]);
            skills.put("history", skill_values[5]);
            skills.put("investigation", skill_values[6]);
            skills.put("nature", skill_values[7]);
            skills.put("religion", skill_values[8]);
            skills.put("animal_handling", skill_values[9]);
            skills.put("insight", skill_values[10]);
            skills.put("medicine", skill_values[11]);
            skills.put("perception", skill_values[12]);
            skills.put("survival", skill_values[13]);
            skills.put("deception", skill_values[14]);
            skills.put("intimidation", skill_values[15]);
            skills.put("performance", skill_values[16]);
            skills.put("persuasion", skill_values[17]);

            JSONObject equipped_equipment = new JSONObject(); // new json object to hold equipped equipment
            String[] equipped_equipment_values = character.getEquippedEquipment(); // create new array to hold values
            for (int i = 0; i < equipped_equipment_values.length; i++) {equipped_equipment.put("slot " + (i + 1), equipped_equipment_values[i]);}

            JSONArray inventory_items = new JSONArray(); // new json array to hold inventory items as2d array
            String[][] inventory_data = inventory.getInventoryData(); // create new 2d array to hold values
            for (String[] row : inventory_data) {
                JSONObject item = new JSONObject();
                item.put("name", row[0]);
                item.put("attribute", row[1]);
                item.put("worth", row[2]);
                inventory_items.put(item);
            }

            JSONObject currencies = new JSONObject(); // new json object to hold currencies
            int[] currency_val = character.getCurrencies(); // create new array to hold values
            currencies.put("copper", currency_val[0]);
            currencies.put("silver", currency_val[1]);
            currencies.put("gold", currency_val[2]);
            currencies.put("platinum", currency_val[3]);

            JSONObject misc = new JSONObject(); // new json object to hold misc text areas
            String[] misc_vals = character.getMisc(); // create new array to hold values
            misc.put("passives", misc_vals[0]);

            JSONArray spell_items = new JSONArray();
            String[][] spell_data = spells.getSpellData();
            for (String[] row : spell_data) {
                JSONObject spell = new JSONObject();
                spell.put("name", row[0]);
                spell.put("level", row[1]);
                spell.put("slot", row[2]);
                spell.put("description", row[3]);
                spell_items.put(spell);
            }

            JSONObject spell_top = new JSONObject();
            String[] top_data = spells.getTopData();
            spell_top.put("ability", top_data[0]);
            spell_top.put("dc", top_data[1]);
            spell_top.put("attack", top_data[2]);

            JSONObject character_info = new JSONObject();
            String[] info_values = information.getInformationData();
            character_info.put("height", info_values[0]);
            character_info.put("weight", info_values[1]);
            character_info.put("gender", info_values[2]);
            character_info.put("age", info_values[3]);
            character_info.put("hair_color", info_values[4]);
            character_info.put("eye_color", info_values[5]);
            character_info.put("skin_color", info_values[6]);
            character_info.put("languages", info_values[7]);

            String image_path = information.getCharacterPicturePath();
            character_info.put("picture_path", image_path != null ? image_path : "");


            JSONObject json = new JSONObject(); // main json object to hold each array
            json.put("headers", headers);
            json.put("stats", stats);
            json.put("scores", scores);
            json.put("prof_slots", prof_slots);
            json.put("skills", skills);
            json.put("equipped_equipment", equipped_equipment);
            json.put("inventory", inventory_items);
            json.put("currency", currencies);
            json.put("misc", misc);
            json.put("spells", spell_items);
            json.put("spell_top", spell_top);
            json.put("information", character_info);

            writer.write(json.toString()); // write the json objects to the json file
            System.out.println("Character saved successfully: " + file.getAbsolutePath()); // show in console it was successful
            Notification.showNotification(window,"Save Successful", "Successfully saved all data to file", settings.getSuccessColor(), settings.getBackgroundColor(), 0.9, Notification.NotificationType.SUCCESS); // tell user if it was successful
        } catch (IOException | JSONException e) { // if there was an error
            e.printStackTrace(); // print stack trace to console
            Notification.showNotification(window,"Save Error", "Error saving data, check print stack trace.", settings.getErrorColor(), settings.getBackgroundColor(), 0.9, Notification.NotificationType.ERROR); // tell user and tell them to check console
        }
    }

    /**
     * Loads a selected character to the window with all their stats
     * @param file current file
     * @param window controller window
     * @param inventory current characters inventory
     * @return character data
     */
    public Character loadFromFile(File file, Window window, Inventory inventory, Spells spells, Information information) {
        try (Scanner scanner = new Scanner(new FileReader(file))) { // handle errors with grace
            StringBuilder json_builder = new StringBuilder(); // new string builder of the json file
            while (scanner.hasNextLine()) { // while scanner has more lines
                json_builder.append(scanner.nextLine()); // append them and go to next line
            }
            JSONObject json = new JSONObject(json_builder.toString()); // new json object with the string builder

            Character character = new Character("Unknown");  // temp default name
            StringBuilder missing_fields = new StringBuilder("Missing fields: "); // new missing files string builder
            boolean missing_data = false; // flag for missing data (default false)

            if (json.has("headers")) { // check json file for headers
                JSONObject headers = json.getJSONObject("headers"); // new json object to hold header values
                character.setHeaders(new String[]{ // call character and set the headers
                        headers.optString("name", "Unknown"), // get the name or if not there make it "Unknown"
                        headers.optString("player", "Unknown"),
                        headers.optString("class", "Unknown"),
                        headers.optString("race", "Unknown"),
                        headers.optString("background", "Unknown"),
                        headers.optString("deity", "Unknown"),
                        headers.optString("level", "1"),
                        headers.optString("alignment", "Neutral")
                });
            } else { // if there is no headers field
                missing_fields.append("headers, "); // add headers to missing fields
                missing_data = true; // set flag to true
                character.setHeaders(new String[]{"Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "1", "Neutral"}); // set all to unknown except level to 1
            }

            if (json.has("stats")) { // check if json object has stats
                JSONObject stats = json.getJSONObject("stats"); // if so create new object to hold stats
                character.setStats(new int[]{ // call current character to set the instance stats
                        stats.optInt("AC", 10), // check for AC field and if not there set default to 10
                        stats.optInt("HP", 10),
                        stats.optInt("initiative", 0),
                        stats.optInt("maxHP", 10),
                        stats.optInt("HPDie", 1),
                        stats.optInt("tempHP", 0),
                        stats.optInt("move_speed", 30),
                        stats.optInt("passive_perception", 10)
                });
            } else { // if there is no stats field
                missing_fields.append("stats, "); // add it to missing fields
                missing_data = true; // set flag to true
                character.setStats(new int[8]); // set all to default 0
            }

            if (json.has("scores")) { // check if json file has scores field
                JSONObject scores = json.getJSONObject("scores"); // if so create new json object to hold these values
                character.setScores(new int[]{ // call instance character to set scores
                        scores.optInt("str", 10), // if str exists use that otherwise default 10 (average)
                        scores.optInt("dex", 10),
                        scores.optInt("con", 10),
                        scores.optInt("int", 10),
                        scores.optInt("wis", 10),
                        scores.optInt("char", 10)
                });
            } else { // if there is no score field
                missing_fields.append("scores, "); // add to missing fields
                missing_data = true; // set flag to true
                character.setScores(new int[]{10, 10, 10, 10, 10, 10}); // default all to 10 (average)
            }

            if (json.has("prof_slots")) { // check if json file as prof slots
                JSONObject prof_slots = json.getJSONObject("prof_slots"); // if so then create new json object to set these
                String[] prof_slot_array = new String[12]; // new array to hold values
                for (int i = 0; i < 12; i++) {prof_slot_array[i] = prof_slots.optString("slot " + (i + 1), "");} // loop through the array
                character.setProfSlots(prof_slot_array); // set the instance character with these values
            } else { // if the field is missing
                missing_fields.append("prof_slots, "); // add to missing fields
                missing_data = true; // flag to true
                String[] default_prof = new String[12]; // default array creation
                Arrays.fill(default_prof, ""); // empty strings
                character.setProfSlots(default_prof); // set instance prof fields as empty
            }

            if (json.has("skills")) { // check if there is a skills field
                JSONObject skills = json.getJSONObject("skills"); // if so make a skill object json
                character.setSkills(new int[]{ // set instance character with these skills (default to 0)
                        skills.optInt("athletics", 0),
                        skills.optInt("acrobatics", 0),
                        skills.optInt("sleight_of_hand", 0),
                        skills.optInt("stealth", 0),
                        skills.optInt("arcana", 0),
                        skills.optInt("history", 0),
                        skills.optInt("investigation", 0),
                        skills.optInt("nature", 0),
                        skills.optInt("religion", 0),
                        skills.optInt("animal_handling", 0),
                        skills.optInt("insight", 0),
                        skills.optInt("medicine", 0),
                        skills.optInt("perception", 0),
                        skills.optInt("survival", 0),
                        skills.optInt("deception", 0),
                        skills.optInt("intimidation", 0),
                        skills.optInt("performance", 0),
                        skills.optInt("persuasion", 0)
                });
            } else { // if there is no skills field
                missing_fields.append("skills, "); // add it to the missing fields
                missing_data = true; // flag to true
                character.setSkills(new int[18]); // set default of 0
            }

            if (json.has("equipped_equipment")) { // check if the json file has equipped equipment
                JSONObject equipped_equipment = json.getJSONObject("equipped_equipment"); // if so make a new json object to hold values
                String[] equipped_equipment_array = new String[21]; // create an array that will hold values
                for (int i = 0; i < 21; i++) {equipped_equipment_array[i] = equipped_equipment.optString("slot " + (i + 1), "");} // loop through array
                character.setEquippedEquipment(equipped_equipment_array); // set this array to the instance character
            } else { // if the field doesnt exist
                missing_fields.append("equipped_equipment, "); // add to missing fields
                missing_data = true; // set flag to true
                String[] default_equipped = new String[21]; // create a default array
                Arrays.fill(default_equipped, ""); // fill default array with empty strings
                character.setEquippedEquipment(default_equipped); // set instance with empty string
            }

            if (json.has("inventory")) { // check if there is an inventory field
                JSONArray inventory_items = json.getJSONArray("inventory"); // if so create new object
                String[][] inventory_data = new String[settings.getInventoryRows()][settings.getInventoryColumns()]; // create 2d array to save these values
                for (int i = 0; i < Math.min(settings.getInventoryRows(), inventory_items.length()); i++) { // loop through inventory
                    JSONObject item = inventory_items.getJSONObject(i); // new object as the item at i
                    if (item.has("name")) inventory_data[i][0] = item.getString("name"); else inventory_data[i][0] = ""; // check for name column, if empty set default empty string
                    if (item.has("attribute")) inventory_data[i][1] = item.getString("attribute"); else inventory_data[i][1] = ""; // check for attribute column, if empty set default empty string
                    if (item.has("worth")) inventory_data[i][2] = item.getString("worth"); else inventory_data[i][2] = ""; // check for worth column, if empty set default empty string
                }
                inventory.setInventoryData(inventory_data); // set this new array as the instance values
            } else { // if there is no inventory field
                missing_fields.append("inventory, "); // add it to missing fields
                missing_data = true; // set flag to true
                inventory.setInventoryData(new String[settings.getInventoryRows()][settings.getInventoryColumns()]); // set defaults to empty string
            }

            if (json.has("currency")) { // check of currency field
                JSONObject currencies = json.getJSONObject("currency"); // if there is then make new json object
                character.setCurrencies(new int[]{ // set instance values as the values in the json file
                        currencies.optInt("copper", 0),
                        currencies.optInt("silver", 0),
                        currencies.optInt("gold", 0),
                        currencies.optInt("platinum", 0)
                });
            } else { // if there is no currency field
                missing_fields.append("currencies, "); // add it to missing fields
                missing_data = true; // set flag to true
                character.setCurrencies(new int[]{0, 0, 0, 0}); // set default to 0
            }

            if (json.has("misc")) { // check for a misc field
                JSONObject misc = json.getJSONObject("misc"); // if there is then create new object
                character.setMisc(new String[]{ // set new array as instance values
                        misc.optString("passives", "Unknown"), // default to "unknown" if not there

                });
            } else { // if there is no misc field
                missing_fields.append("misc, "); // add it to missing fields
                missing_data = true; // set flag to true
                character.setMisc(new String[]{"Unknown"}); // set default to unknown
            }

            if (json.has("spells")) {
                JSONArray spells_items = json.getJSONArray("spells"); // Get "spells" JSONArray
                String[][] spells_data = new String[settings.getSpellRows()][settings.getSpellColumns()];
                for (int i = 0; i < Math.min(settings.getSpellRows(), spells_items.length()); i++) {
                    JSONObject spell = spells_items.getJSONObject(i);
                    spells_data[i][0] = spell.optString("name", "");
                    spells_data[i][1] = spell.optString("level", "");
                    spells_data[i][2] = spell.optString("slot", "");
                    spells_data[i][3] = spell.optString("description", "");
                }
                spells.setSpellData(spells_data); // Set spell data on the 'spells' object
            } else {
                missing_fields.append("spells, ");
                missing_data = true;
                spells.setSpellData(new String[settings.getSpellRows()][settings.getSpellColumns()]); // Set defaults
            }

            if (json.has("spell_top")) { // check json file for spell_top
                JSONObject spells_top = json.getJSONObject("spell_top"); // new json object to hold spell_top values
                spells.setTopData(new String[]{ // call spells and set the top data
                        spells_top.optString("ability", "Unknown"),
                        spells_top.optString("dc", "Unknown"),
                        spells_top.optString("attack", "Unknown"),
                });
            } else { // if there is no spell_top field
                missing_fields.append("spell_top, "); // add spell_top to missing fields
                missing_data = true; // set flag to true
                spells.setTopData(new String[]{"Unknown", "Unknown", "Unknown"}); // set all to unknown
            }

            if (json.has("information")) {
                JSONObject character_info = json.getJSONObject("information");
                String[] loaded_info = new String[8];
                loaded_info[0] = character_info.optString("height", "");
                loaded_info[1] = character_info.optString("weight", "");
                loaded_info[2] = character_info.optString("gender", "");
                loaded_info[3] = character_info.optString("age", "");
                loaded_info[4] = character_info.optString("hair_color", "");
                loaded_info[5] = character_info.optString("eye_color", "");
                loaded_info[6] = character_info.optString("skin_color", "");
                loaded_info[7] = character_info.optString("languages", ""); // Languages are comma-separated

                information.setInformationData(loaded_info);

                String image_path = character_info.optString("picture_path", "");
                information.setCharacterPicture(image_path);

            } else {
                missing_fields.append("information, ");
                missing_data = true;
                // Set default values for information
                information.setInformationData(new String[]{"", "", "", "", "", "", "", ""});
                information.setCharacterPicture(null); // Clear picture if no data
            }

            if(missing_data){ // if the missing data has been flagged
                Notification.showNotification(window, "Load Warning", missing_fields.toString(), settings.getWarningColor(), settings.getBackgroundColor(), 0.9, Notification.NotificationType.WARNING); // notify user not all the fields where there
                System.out.println(missing_fields); // print fields to console
            }
            else{ // if there is no missing data
                Notification.showNotification(window, "Save Successful", "All data has been loaded.", settings.getSuccessColor(), settings.getBackgroundColor(), 0.9, Notification.NotificationType.SUCCESS); // tell user it was a successful load
                System.out.println("Save was successful"); // print fields to console
            }

            return character; // return the character

        }catch(IOException | JSONException e){ // catch any errors
            e.printStackTrace(); // print stack trace to console
            Notification.showNotification(window, "Error", "Error Loading Data, check print stack trace.", settings.getErrorColor(), settings.getBackgroundColor(), 0.9, Notification.NotificationType.ERROR); // tell user there was an error
            return null; // do not try to return character
        }
    }

    /**
     * Saves user settings (only save directory for now)
     * @param user_settings user_settings
     */
    public void saveUserSettings(UserSettings user_settings) {
        File settings_file = Paths.get("res", "user_settings.json").toFile(); // finds path of the user_settings.json in res folder
        try (FileWriter writer = new FileWriter(settings_file)) { // tries to write the new settings to the json file
            JSONObject json = new JSONObject(); // creates a new json object to hold this value
            json.put("save_directory", user_settings.getSaveDirectory().getAbsolutePath()); // put the absolute save directory in the json object
            writer.write(json.toString()); // write json object to the json file
            System.out.println("User settings saved to: " + settings_file.getAbsolutePath()); // print in console the user settings where saved
        } catch (IOException | JSONException e) { // if there are errors
            e.printStackTrace(); // print the stack trace to console
        }
    }

    /**
     * Gets the user settings based on its saved data, will user default path if nothing is saved.
     * @param user_settings user_settings
     */
    public void loadUserSettings(UserSettings user_settings) {
        File settings_file = Paths.get("res", "user_settings.json").toFile(); // gets the path of user_settings json file in the res folder
        if (!settings_file.exists()) { // if the file doesnt exist
            System.out.println("No existing user settings file found. Using defaults."); // print to console that user doesn't have any and that its using defaults
            return; // return early
        }

        try (Scanner scanner = new Scanner(new FileReader(settings_file))) { // if it does exist then scan the file
            StringBuilder json_builder = new StringBuilder(); // new string builder to hold values in json file
            while (scanner.hasNextLine()) { // while the file isn't at the end
                json_builder.append(scanner.nextLine()); // add these read lines into the string builder
            }

            JSONObject json = new JSONObject(json_builder.toString()); // new json object to hold values from builder
            String directory = json.optString("save_directory", user_settings.getSaveDirectory().getAbsolutePath()); // get the directory path
            user_settings.setSaveDirectory(new File(directory)); // set user settings into the file directory path
            System.out.println("User settings loaded from: " + settings_file.getAbsolutePath()); // print that it was successfully loaded
        } catch (IOException | JSONException e) { // catch any errors
            e.printStackTrace(); // print stack trace to console
        }
    }


}