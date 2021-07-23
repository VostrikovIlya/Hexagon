
import view.*;
import model.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private final Model model = new Model();
    private final View view = new View(model);

    public void start(Stage stage) {
        stage.setTitle("Hexagon");
        stage.setScene(view.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
