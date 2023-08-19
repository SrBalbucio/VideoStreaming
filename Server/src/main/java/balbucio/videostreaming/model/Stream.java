package balbucio.videostreaming.model;

import lombok.Data;
import org.json.JSONObject;
import java.util.UUID;

@Data
public class Stream {

    private String name;
    private UUID id = UUID.randomUUID();
    private FixedQueue<String> frames;
    private int fps;
    private long lastUpdate;

    public Stream(JSONObject json){
        this.fps = json.getInt("fps");
        this.name = json.getString("name");
        this.frames = new FixedQueue<>(fps*10);
    }

    public void addFrame(String frame){
        frames.add(frame);
        lastUpdate = System.currentTimeMillis();
    }

    public String getLastFrame(){
        return frames.getQueue().getLast();
    }
}
