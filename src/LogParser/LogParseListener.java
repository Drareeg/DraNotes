package LogParser;

import javafx.util.Pair;

/**
 * Created by Drareeg on 22.11.14.
 */
public interface LogParseListener {
    public void onChampAndPlayerFound(Pair<String, String> pair);
}
