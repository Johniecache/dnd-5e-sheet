import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Main class that will create and handle the fxml sheet to the screen.
 */
public class DnD5eSheet extends Application{
    /**
     * Creates the stage the user will see and interact with based on the fxml file.
     * @param stage stage
     * @throws IOException IOException error handling
     */
    @Override public void start(Stage stage) throws IOException {
        try{ // handle errors with grace
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DnD5eSheet.fxml")); // set loader to the fxml sheet
            Parent root = loader.load(); // set root to the loader
            Controller controller = loader.getController(); // set controller from the main controller class
            Scene scene = new Scene(root); // instantiate new scene with root passed
            stage.setTitle("DnD 5e Sheet"); // set title
            stage.setScene(scene); // set stage with scene passed
            stage.show(); // ensure user can see it
            controller.setScene(scene); // set controller scene with passed scene
        } catch (IOException e){ // gracefully handle errors so that the program doesn't crash
            e.printStackTrace(); // console out the full error
        }

    }

    /**
     * Allows the program to run properly
     * @param args arguments
     */
    public static void main(String[] args){launch(args);} // main method to launch program
}