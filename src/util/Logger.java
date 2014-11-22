package util;

/**
 * Created by Drareeg on 21.11.14.
 */
public class Logger {
    private static final java.util.logging.Logger anonymousLogger;
    static {
        anonymousLogger = java.util.logging.Logger.getAnonymousLogger();
    }

    public static void info(String info){
      anonymousLogger.info(info);
    }

}
