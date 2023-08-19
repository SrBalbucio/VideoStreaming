package balbucio.videostreaming.task;

import balbucio.responsivescheduler.RSTask;
import balbucio.videostreaming.manager.StreamingManager;
import balbucio.videostreaming.manager.WatchManager;
import balbucio.videostreaming.page.WatchPage;
import balbucio.videostreaming.utils.VideoUtils;

public class WatchTask extends RSTask {

    private WatchManager manager;
    private WatchPage page;

    public WatchTask(WatchManager manager){
        this.manager = manager;
        this.page = new WatchPage();
    }

    @Override
    public void run() {
        try {
            StreamingManager streamingManager = manager.getStreamingManager();
            page.setFrame(VideoUtils.base64ToImage(streamingManager.getLastFrame(manager.getId())));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
