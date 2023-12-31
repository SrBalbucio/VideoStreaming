package balbucio.videostreaming;

import balbucio.responsivescheduler.ResponsiveScheduler;
import balbucio.videostreaming.task.CleanTask;
import co.gongzh.procbridge.IDelegate;
import co.gongzh.procbridge.Server;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class Main implements IDelegate {
    public static void main(String[] args) {
        int port = Integer.parseInt(getArgument(args, "port"));
        new Main(port);
    }

    public static String getArgument(String[] args, String name){
        for(String string : args){
            String[] s = string.split("=");
            if(s[0].equalsIgnoreCase(name)){
                return s[1];
            }
        }
        return null;
    }

    private Server server;
    private ResponsiveScheduler scheduler;

    public Main(int port){
        this.scheduler = new ResponsiveScheduler();
        scheduler.repeatTask(new CleanTask(), 0, 1000);
        System.out.println("");
        System.out.println("STREAMING SERVER IS STARTED!");
        this.server = new Server(port, this);
        server.start();
    }

    @Override
    public @Nullable Object handleRequest(@Nullable String s, @Nullable Object o) {
        JSONObject payload = (JSONObject) o;
        if(s.equalsIgnoreCase("REGISTER_STREAM")){
            return StreamManager.registerNewStream(payload).getId().toString();
        } else if(s.equalsIgnoreCase("STREAM_FRAME")){
            StreamManager.addFrame(payload);
        } else if(s.equalsIgnoreCase("GET_FRAME")){
            return StreamManager.getLastFrame(payload);
        } else if(s.equalsIgnoreCase("GET_STREAMS")){
            return StreamManager.getStreams();
        } else if(s.equalsIgnoreCase("UNREGISTER_STREAM")){
            StreamManager.unregisterStream(payload);
        }
        return null;
    }
}