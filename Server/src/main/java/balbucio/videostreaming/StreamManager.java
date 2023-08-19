package balbucio.videostreaming;

import balbucio.videostreaming.model.Stream;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class StreamManager {

    public static CopyOnWriteArrayList<Stream> streams = new CopyOnWriteArrayList<>();

    public static Stream registerNewStream(JSONObject payload){
        Stream stream = new Stream(payload);
        System.out.println("Nova stream registrada com o ID "+stream.getId().toString()+"!");
        streams.add(stream);
        return stream;
    }

    public static void unregisterStream(JSONObject payload){
        String id = payload.getString("id");
        Stream stream  = streams.stream().filter(s -> s.getId().toString().equalsIgnoreCase(id)).findFirst().orElse(null);
        if(stream != null){
            streams.remove(stream);
        }
    }

    public static void addFrame(JSONObject payload){
        String id = payload.getString("id");
        Stream stream = streams.stream().filter(s -> s.getId().toString().equalsIgnoreCase(id)).findFirst().orElse(null);
        if(stream != null){
            stream.addFrame(payload.getString("data"));
        }
    }

    public static String getLastFrame(JSONObject payload){
        String id = payload.getString("id");
        Stream stream = streams.stream().filter(s -> s.getId().toString().equalsIgnoreCase(id)).findFirst().orElse(null);
        if(stream != null) {
            return stream.getLastFrame();
        }
        return null;
    }

    public static JSONObject getStreams(){
        JSONObject payload = new JSONObject();
        JSONArray array = new JSONArray();
        streams.forEach(s -> {
            JSONObject info = new JSONObject();
            info.put("id", s.getId().toString());
            info.put("fps", s.getFps());
            info.put("name", s.getName());
            info.put("lastframe", s.getLastFrame());
            array.put(info);
        });
        payload.put("data", array);
        return payload;
    }
}
