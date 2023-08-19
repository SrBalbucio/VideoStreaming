package balbucio.videostreaming.manager;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.ResponsiveScheduler;
import balbucio.videostreaming.model.VideoQuality;
import balbucio.videostreaming.task.RecordTask;
import de.milchreis.uibooster.UiBooster;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class RecordManager {

    @Getter
    @Setter
    private StreamingManager streamingManager;
    private ResponsiveScheduler responsiveScheduler;
    @Getter
    @Setter
    private Robot robot;
    private RecordTask recordTask;
    @Getter
    @Setter
    private boolean live = false;
    @Getter
    @Setter
    private VideoQuality videoQuality = VideoQuality.NATIVO;
    @Getter
    @Setter
    private int videoFPS = 60;

    public RecordManager(StreamingManager streamingManager, ResponsiveScheduler responsiveScheduler) throws AWTException {
        this.streamingManager = streamingManager;
        this.responsiveScheduler = responsiveScheduler;
        this.robot = new Robot();
    }

    public void startStream(String name, int fps, VideoQuality quality, UiBooster booster){
        this.videoFPS = fps;
        this.videoQuality = quality;
        streamingManager.registerNewStreaming(fps, name);
        recordTask = new RecordTask(this, booster);
        responsiveScheduler.repeatTask(recordTask, 0, (1000/fps)); // divide um segundo pela quantidade de FPS
        this.live = true;
        booster.createNotification("Stream iniciada", "Neste momento você está transmitindo tela!");
    }

    public void stopRecord(){
        recordTask.finish();
        responsiveScheduler.cancelTask(recordTask);
        streamingManager.unregisterStream();
        this.live = false;
    }
}
