import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * Notification put on the main controller window to show user
 */
public class Notification {
    /**
     * Instantiate Settings class
     */
    private static final Settings settings = new Settings();

    /**
     * Type of notification to determine color and icon
     */
    public enum NotificationType {SUCCESS, WARNING, ERROR}

    /**
     * Handles the showing of notifications to the main window.
     * @param window Main Window
     * @param title Title of Notification
     * @param message Message of Notification
     * @param txt_color Text Color
     * @param bg_color Background Color
     * @param opacity Opacity
     * @param type Notification type
     */
    public static void showNotification(Window window, String title, String message, Color txt_color, Color bg_color, double opacity, NotificationType type) {
        Popup popup = new Popup(); // instantiate a new popup

        Label title_label = new Label(title); // title of the label
        title_label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;"); // setting style
        title_label.setTextFill(txt_color); // fill in text color with passed color
        title_label.setWrapText(true); // set wrapped to true
        title_label.setMaxWidth(160); // maximum length
        title_label.setAlignment(Pos.CENTER_LEFT); // center text

        Label message_label = new Label(message); // text in the notification
        message_label.setTextFill(txt_color); // fill text with passed color
        message_label.setWrapText(true); // set wrapped to true
        message_label.setMaxWidth(200); // max width
        message_label.setAlignment(Pos.CENTER_LEFT); // center text

        Rectangle bg_shape = new Rectangle(300, 50, bg_color); // set shape of the notification
        bg_shape.setArcWidth(10); // width
        bg_shape.setArcHeight(10); // height
        bg_shape.setOpacity(opacity); // opacity of notification

        ImageView icon = new ImageView(); // instantiate new image
        icon.setFitWidth(20); // icon width
        icon.setFitHeight(20); // icon height

        switch (type) { // switch on notification type
            case SUCCESS: // success type
                icon.setImage(settings.getSuccessIcon()); // set icon to checkmark
                break; // break switch case
            case WARNING: // warning type
                icon.setImage(settings.getWarningIcon()); // set icon as warning
                break; // break switch case
            case ERROR: // error type
                icon.setImage(settings.getErrorIcon()); // set icon as red cross
                break; // break switch case
        }

        HBox content_box = new HBox(10, icon, new VBox(title_label, message_label)); // horizontal box to contain icon, title, message
        content_box.setAlignment(Pos.CENTER_LEFT); // center box
        content_box.setPadding(new Insets(5)); // padding

        StackPane popup_content = new StackPane(bg_shape, content_box); // new stack pane for the popup content
        popup_content.setAlignment(Pos.CENTER_LEFT); // center popup

        popup.getContent().add(popup_content); // put the content into the box
        double initial_x = window.getX() + window.getWidth() - 320; // the x position of notification for first set
        double initial_y = window.getY() + 175; // the y position of notification for first set
        popup.show(window, initial_x, initial_y); // show notification at x y position

        window.xProperty().addListener((obs, old_x, new_x) -> { // change old x cord with new x cord when moved
            popup.setX(new_x.doubleValue() + window.getWidth() - 320); // keep current position on main window
        });
        window.yProperty().addListener((obs, old_y, new_y) -> { // change old y cord with new y cord
            popup.setY(new_y.doubleValue() + 175); // keep current position on main window
        });
        window.widthProperty().addListener((obs, old_w, new_w) -> { // set old window location as new window location
            popup.setX(window.getX() + new_w.doubleValue() - 320); // keep same position on main window
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> popup.hide())); // create new timeline that sets duration of notification to 5 seconds
        timeline.play(); // play this notification
    }
}