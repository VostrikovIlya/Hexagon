import Conrol.Controller;
import View.*;
import Model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    private final View view = new View();
    private final Controller controller = new Controller(view);
    private final Model model = new Model(controller);
    public void start(Stage stage) {
        stage.setTitle("Hexagon");
        stage.setScene(view.getScene());
        stage.show();
        stage.setOnCloseRequest( (event)-> {
            Platform.exit();
            model.setEndGame(true);
            System.exit(0);
        });

        Thread thr = new Thread(model::start);
        thr.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
