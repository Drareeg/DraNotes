package ViewModel;

import LogParser.LogParseListener;
import constant.Region;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import main.java.riotapi.RiotApi;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drareeg on 21.11.14.
 */
public class GameViewModel implements LogParseListener {

    private ObservableList<Pair<String, String>> players;

    public GameViewModel() {
        players = FXCollections.observableArrayList();
    }

    @Override
    public void onChampAndPlayerFound(Pair<String, String> pair) {
        Platform.runLater(() -> {
            players.add(pair);
        });
        Logger.info("pair found" + pair);
    }

    public ObservableList<Pair<String, String>> getPlayers() {
        return players;
    }
}
