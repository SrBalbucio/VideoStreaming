package balbucio.videostreaming.task;

import balbucio.responsivescheduler.RSTask;
import balbucio.videostreaming.manager.RecordManager;
import balbucio.videostreaming.manager.StreamingManager;
import balbucio.videostreaming.model.FixedQueue;
import balbucio.videostreaming.page.RecordPage;
import balbucio.videostreaming.utils.VideoUtils;
import de.milchreis.uibooster.UiBooster;
import lombok.AllArgsConstructor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;

@AllArgsConstructor
public class RecordTask extends RSTask {

    private RecordManager recordManager;
    private RecordPage recordPage;
    private FixedQueue<Integer> buffer;
    private Date startTime = new Date();

    public RecordTask(RecordManager recordManager, UiBooster booster){
        this.recordManager = recordManager;
        this.recordPage = new RecordPage(recordManager, booster);
        this.buffer = new FixedQueue<>(5);
    }

    public void finish(){
        recordPage.dispose();
        buffer.getQueue().clear();
        startTime = null;
    }

    @Override
    public void run() {
        StreamingManager streamingManager = recordManager.getStreamingManager();
        Robot robot = recordManager.getRobot();
        try {
            BufferedImage bffimage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            buffer.add(bffimage.getData().getDataBuffer().getSize());
            recordPage.updateFrameMBs(buffer);
            recordPage.updateTimer(startTime.getTime());
            streamingManager.sendImageFrame(VideoUtils.imageToBase64(bffimage)); // envia a imagem para o servidor
        } catch (Exception e){
            e.printStackTrace();
            setProblem(true);
            setProblemID(1);
        }
    }
}
