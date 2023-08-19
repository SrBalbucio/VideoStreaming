package balbucio.videostreaming;

import balbucio.org.ejsl.utils.ImageUtils;
import balbucio.responsivescheduler.ResponsiveScheduler;
import balbucio.videostreaming.manager.RecordManager;
import balbucio.videostreaming.manager.StreamingManager;
import balbucio.videostreaming.manager.WatchManager;
import balbucio.videostreaming.model.VideoQuality;
import balbucio.videostreaming.utils.VideoUtils;
import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import de.milchreis.uibooster.model.ListElement;
import de.milchreis.uibooster.model.UiBoosterOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws AWTException {
        new Main();
    }

    private UiBooster ui;
    private ResponsiveScheduler responsiveScheduler;
    private RecordManager recordManager;
    private StreamingManager streamingManager;
    private WatchManager watchManager;

    public Main() throws AWTException {
        this.ui = new UiBooster(UiBoosterOptions.Theme.DARK_THEME, "logo.png");
        ui.createTrayMenu("VideoStreaming", "logo.png")
                .withPopupMenu()
                .addMenu("Stop Stream", this::stopRecord)
                .addMenu("Exit", () -> { System.exit(0);} );
        this.responsiveScheduler = new ResponsiveScheduler();
        Form f = ui.createForm("Video Streaming")
                .addLabel("Conecte-se a um servidor para poder transmitir e assistir lives!")
                .addText("IP:", "localhost")
                .addText("Port:", "25365").show();
        this.streamingManager = new StreamingManager(f.getByIndex(1).asString(), f.getByIndex(2).asInt());
        this.recordManager = new RecordManager(streamingManager, responsiveScheduler);
        this.watchManager = new WatchManager(streamingManager, responsiveScheduler);
        f = ui.createForm("Video Streaming")
                .addLabel("Selecione uma opção:")
                .addButton("Assistir a uma transmissão", this::watchStream)
                .addButton("Transmitir tela", this::startRecord).show();

    }

    public void watchStream(){
        JSONObject payload = streamingManager.getStreams();
        JSONArray data = payload.getJSONArray("data");
        ArrayList<ListElement> element = new ArrayList<>();
        data.forEach(o -> {
            try {
                JSONObject json = (JSONObject) o;
                element.add(new ListElement(json.getString("name"), "FPS: " + json.getInt("fps"), ImageUtils.getImage(VideoUtils.base64ToImage(json.getString("lastframe")))));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        ListElement result = ui.showList("Escolha uma das streams para assistir", "Lista de Stream", element.toArray(new ListElement[element.size()]));

    }
    public void startRecord(){
        Form f = ui.createForm("Configure sua Stream")
                .addLabel("Atenção: Para o melhor desempenho mantenha Nativo 60hz")
                .addText("Nome da Stream:", System.getProperty("user.name"))
                .addSelection("Qualidade:", Arrays.asList("480p", "720p", "1080p", "Nativo"))
                .addSlider("Quant. de frames por segundo: (hz)", 15, 240, 60, 30, 30)
                .addCheckbox("Forçar o client a ver mais frames").show();
        String q = f.getByIndex(2).asString();
        recordManager.startStream(f.getByIndex(1).asString(), f.getByIndex(3).asInt(), VideoQuality.valueOf(q.contains("p") ? "V_"+q : q));
    }

    public void stopRecord(){
        recordManager.stopRecord();
    }
}