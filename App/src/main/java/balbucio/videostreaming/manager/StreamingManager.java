package balbucio.videostreaming.manager;

import co.gongzh.procbridge.Client;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

public class StreamingManager {

    private String ip;
    private int port;
    private Client client;
    private String streamingID;

    public StreamingManager(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.client = new Client(ip, port);
    }

    public void registerNewStreaming(int fps, String name){
        JSONObject payload = new JSONObject();
        payload.put("fps", fps);
        payload.put("name", name);
        streamingID = (String) client.request("REGISTER_STREAM", payload);
    }

    public void sendImageFrame(String image){
        JSONObject payload = new JSONObject();
        payload.put("id", streamingID);
        payload.put("data", image);
        payload.put("time", System.currentTimeMillis());
        client.request("STREAM_FRAME", payload);
    }

    public JSONObject getStreams(){
        return (JSONObject) client.request("GET_STREAMS", new JSONObject());
    }
}
