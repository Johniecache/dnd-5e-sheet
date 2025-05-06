import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

/**
 * Randomizes the characters values (headers, stats, scores, etc.)
 */
public class Randomizer {
    /**
     * Instantiates a new random object of secure random class
     */
    private static final SecureRandom secure_rand = new SecureRandom();
    /**
     * Instantiates a new Map with the string and json array of names
     */
    private static final Map<String, JSONArray> names = new HashMap<>();

    static { // more statics to be added (backgrounds, levels, classes, etc.
        loadClassNames(); // load the class names
    }

    /**
     * Loads the Names based on the character class
     */
    private static void loadClassNames() {
        try (InputStream is = Randomizer.class.getClassLoader().getResourceAsStream("database/NamesDB.json")) { // try to load names database from database folder
            if (is == null) { // if the file doesnt exist
                throw new IOException("File not found: database/NamesDB.json"); // print that it doesn't exist
            }
            String content = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next(); // assign content to the stuff inside the file
            JSONObject json_object = new JSONObject(content); // create new json object to hold the string content variable

            for (Iterator<String> it = json_object.keys(); it.hasNext(); ) { // loop through the json object
                String class_name = it.next(); // assign variable to each iteration
                names.put(class_name, json_object.getJSONArray(class_name)); // put them into names map
            }
        } catch (IOException | JSONException e) { // catch any errors that occur
            System.out.println("Error loading class names: " + e.getMessage()); // print message
        }
    }

    /**
     * Tries to apply randomization to the current character from passed settings
     * @param settings settings object
     * @param controller controller object
     * @throws JSONException JSONException
     */
    public void applyRandomization(Settings settings, Controller controller) throws JSONException {
        String rand_name = randomizeNameByClass(settings); // get random name from passed variable
        int[] ability_scores = rollAbilityScores(); // get random ability scores as array by called function

        settings.getCurrCharacter().setScores(ability_scores); // pass random ability scores
        settings.getCurrCharacter().setName(rand_name); // pass random the name
    }

    /**
     * Randomizes the passed character (from settings) and gets its class then sets its name based on that class.
     * @param settings settings object
     * @return random name
     * @throws JSONException JSONException
     */
    public String randomizeNameByClass(Settings settings) throws JSONException {
        if(names.containsKey(settings.getCurrCharacter().get_Class())){ // if the character is not null
            System.out.println("Class found: " + settings.getCurrCharacter().get_Class()); // confirm the class has been found
            JSONArray names = Randomizer.names.get(settings.getCurrCharacter().get_Class()); // get original name
            String rand_name = names.getString(secure_rand.nextInt(names.length())); // set new name
            System.out.println("Random name found: " + rand_name); // confirm the random name
            return rand_name; // return random name
        } else { // if no character found
            return ""; // return empty string
        }
    }

    /**
     * Dynamic dice roller with input amount of sides
     * @param sides sides of die
     * @return random number
     */
    public static int rollDie(int sides) {
        return secure_rand.nextInt(sides) + 1; // return random number between 1 and input number
    }

    /**
     * gets 4 ranodom numbers between 1 and 6, drops lowest and adds them
     * @return largest 3 rolls added
     */
    public static int rollAbilityScore() {
        int[] rolls = new int[4]; // new array of ints to hold roll values
        for (int i = 0; i < 4; i++) { // while not all dice have been rolled
            rolls[i] = rollDie(6); // roll the dice
        }
        Arrays.sort(rolls); // sort the rolls in ascending order
        return rolls[1] + rolls[2] + rolls[3]; // add top 3 largest rolls and reruns that value added
    }

    /**
     * Rolls random ability scores as in all of them instead of one specific
     * @return scores array
     */
    public static int[] rollAbilityScores() {
        int[] scores = new int[6]; // creates new array for 6 scores
        for (int i = 0; i < 6; i++) { // loop through each score
            scores[i] = rollAbilityScore(); // set score in array as the ability score
        }
        return scores; // return all the random scores as an array
    }
}
