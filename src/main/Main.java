package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.R3DLogFolderListener;
import util.R3DWatcher;

import java.io.IOException;
import java.nio.file.*;

public class Main extends Application implements R3DLogFolderListener {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        setScene(Scenes.mainScene);
        primaryStage.setTitle("DraNotes");

        R3DWatcher r3DWatcher = new R3DWatcher();
        r3DWatcher.addListener(this);
        r3DWatcher.startListeningForFolderChanges();

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onNewLogFile() {
        setScene(Scenes.gameScene);
        //globaal bereikbaar viewmodel aanpassen met de 10 mensen
        //gamescene kent dit viewmodel
    }


    public static void setScene(String sceneName){
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
