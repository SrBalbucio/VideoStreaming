package balbucio.videostreaming.manager;

import balbucio.responsivescheduler.ResponsiveScheduler;
import balbucio.videostreaming.task.WatchTask;
import lombok.Getter;
import lombok.Setter;

public class WatchManager {

    @Getter
    @Setter
    private StreamingManager streamingManager;
    private ResponsiveScheduler responsiveScheduler;
    private WatchTask watchTask;
    @Getter
    @Setter
    private String id;
    private int fps;

    public WatchManager(StreamingManager streamingManager, ResponsiveScheduler responsiveScheduler) {
        this.streamingManager = streamingManager;
        this.responsiveScheduler = responsiveScheduler;
    }

    public void watch(String id, int fps){
        this.id = id;
        this.fps = fps;
        this.watchTask = new WatchTask(this);
        responsiveScheduler.repeatTask(watchTask, 0, (1000/fps));

    }
}
