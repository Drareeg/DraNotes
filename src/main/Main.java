package main;

import LogParser.LogParser;
import ViewModel.GameViewModel;
import dto.Game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Logger;
import util.R3DLogFolderListener;
import util.R3DWatcher;

import java.io.IOException;


public class Main extends Application implements R3DLogFolderListener {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setScene(Scenes.mainScene, null);
        primaryStage.setTitle("DraNotes");

        R3DWatcher r3DWatcher = new R3DWatcher();
        r3DWatcher.addListener(this);
        r3DWatcher.startListeningForFolderChanges();

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onNewLogFile(String file) {
        GameViewModel gameViewModel = new GameViewModel();
        GameController gameController = new GameController(gameViewModel);
        setScene(Scenes.gameScene, gameController);
        Logger.info("new log file found");
        try {
            new LogParser(file, gameViewModel).parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setScene(String sceneName, Object controller) {
        Platform.runLater(() -> {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
            if (controller != null) {
                loader.setController(controller);
            }
            try {
                loader.load();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
