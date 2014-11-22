package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drareeg on 21.11.14.
 * notifies bij nieuwe R3D log
 */
public class R3DWatcher {

    private List<R3DLogFolderListener> listeners;
    private final WatchService watcher;

    public R3DWatcher() throws IOException, InterruptedException {
        listeners = new ArrayList<R3DLogFolderListener>();

        FileSystem fileSystem = FileSystems.getDefault();
        Path r3dFolder = fileSystem.getPath("C:\\Riot Games\\League of Legends\\Logs\\Game - R3d Logs");
        watcher = fileSystem.newWatchService();
        r3dFolder.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);


    }

    public void startListeningForFolderChanges() {
        Thread folderMonitorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                WatchKey watchKey = null;
                try {
                    watchKey = watcher.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                    for (WatchEvent watchEvent : watchEvents) {
                        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            listeners.stream().forEach(a -> a.onNewLogFile(watchEvent.context().toString()));
                        }
                    }
                }
            }
        });
        folderMonitorThread.start();
    }

    public void addListener(R3DLogFolderListener listener) {
        listeners.add(listener);
    }
}
