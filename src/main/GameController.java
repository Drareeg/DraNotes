package main;

import ViewModel.GameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.util.Pair;
import util.Logger;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Drareeg on 21.11.14.
 */
public class GameController  implements Initializable{

    @FXML
    ListView<Pair<String, String>> listView;

    private final GameViewModel viewModel;

    public GameController(GameViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Logger.info("GameController initialize begin");
        listView.setItems(viewModel.getPlayers());
        Logger.info("GameController initialized");
    }
}
