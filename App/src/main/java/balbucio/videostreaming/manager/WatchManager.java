package balbucio.videostreaming.manager;

import balbucio.responsivescheduler.ResponsiveScheduler;

public class WatchManager {

    private StreamingManager streamingManager;
    private ResponsiveScheduler responsiveScheduler;
    private String id;
    private int fps;

    public WatchManager(StreamingManager streamingManager, ResponsiveScheduler responsiveScheduler) {
        this.streamingManager = streamingManager;
        this.responsiveScheduler = responsiveScheduler;
    }

    public void watch(String id, int fps){

    }
}
