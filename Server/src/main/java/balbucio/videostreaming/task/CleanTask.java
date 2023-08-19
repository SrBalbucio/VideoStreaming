package balbucio.videostreaming.task;

import balbucio.responsivescheduler.RSTask;
import balbucio.videostreaming.StreamManager;

public class CleanTask extends RSTask {
    @Override
    public void run() {
        StreamManager.streams.forEach(s -> {
            long t  = s.getLastUpdate();
            if((System.currentTimeMillis() - t) > (1000 * 60 * 60 * 2)){
                StreamManager.streams.remove(s);
            }
        });
    }
}
