package balbucio.videostreaming.model;

import lombok.Data;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Data
public class Stream {

    private String name;
    private UUID id = UUID.randomUUID();
    private FixedQueue<String> frames;
    private int fps;

    public Stream(JSONObject json){
        this.fps = json.getInt("fps");
        this.name = json.getString("name");
        this.frames = new FixedQueue<>(fps*10);
    }

    public void addFrame(String frame){
        frames.add(frame);
    }

    public String getLastFrame(){
        return frames.getQueue().getFirst();
    }
}
