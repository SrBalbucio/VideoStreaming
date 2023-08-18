package balbucio.videostreaming.task;

import balbucio.responsivescheduler.RSTask;
import balbucio.videostreaming.manager.RecordManager;
import balbucio.videostreaming.manager.StreamingManager;
import balbucio.videostreaming.utils.VideoUtils;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public class RecordTask extends RSTask {

    private RecordManager recordManager;
    @Override
    public void run() {
        StreamingManager streamingManager = recordManager.getStreamingManager();
        Robot robot = recordManager.getRobot();
        try {
            BufferedImage bffimage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            streamingManager.sendImageFrame(VideoUtils.imageToBase64(bffimage)); // envia a imagem para o servidor
        } catch (Exception e){
            e.printStackTrace();
            setProblem(true);
            setProblemID(1);
        }
    }
}
