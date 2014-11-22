package LogParser;

import javafx.util.Pair;
import sun.misc.Regexp;
import util.Logger;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Drareeg on 21.11.14.
 */
public class LogParser {

    private BufferedReader input;
    boolean parsingFinished = false;
    private LogParseListener listener;

    public LogParser(String path, LogParseListener listener) throws IOException {
        this.listener = listener;
        File logfile = new File("C:\\Riot Games\\League of Legends\\Logs\\Game - R3d Logs\\" + path);
        input = new BufferedReader(new FileReader(logfile));

    }

    private String getNextLineBlocking() {
        String nextLine = null;
        while (nextLine == null) {
            try {
                nextLine = input.readLine();
            } catch (IOException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {

                }
            }
        }
        return nextLine;
    }
    //000022.575|       0.0000kb|      0.0000kb added| ALWAYS| Hero Sivir(1) created for Drareeg
    //000030.969|       0.0000kb|      0.0000kb added| ALWAYS| GAMESTATE_GAMELOOP Begin
    public void parse(){
        while (!parsingFinished) {
            String nextLine = getNextLineBlocking();
            if(nextLine.contains("GAMESTATE_GAMELOOP Begin")){
                parsingFinished = true;
            }else if (nextLine.contains("created for")){
                listener.onChampAndPlayerFound(getChampAndPlayer(nextLine));
            }
        }
        Logger.info("parsing of logfile finished");
    }

    private static final Pattern pattern = Pattern.compile("Hero (.*) created for (.*)");

    public static Pair<String, String> getChampAndPlayer(String line){
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return new Pair<>(matcher.group(1),matcher.group(2));
    }
}
