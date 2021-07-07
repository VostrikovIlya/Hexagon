import Conrol.Controller;
import View.*;
import Model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    private final View view = new View();
    private final Model model = new Model();
    private final Controller controller = new Controller(view,model);
    public void start(Stage stage) {
        stage.setTitle("Hexagon");
        stage.setScene(view.getScene());
        stage.show();
        stage.setOnCloseRequest( (event)-> {
            Platform.exit();
            model.setEndGame(true);
            System.exit(0);
        });

        Thread thr = new Thread(()->{
            model.start();
            controller.setLabelEndGame();
        });
        thr.start();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
