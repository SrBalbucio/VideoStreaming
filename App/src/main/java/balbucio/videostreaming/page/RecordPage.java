package balbucio.videostreaming.page;

import balbucio.videostreaming.manager.RecordManager;
import balbucio.videostreaming.model.FixedQueue;
import de.milchreis.uibooster.UiBooster;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordPage extends JFrame {

    private JButton stopRecord;
    private JButton optimizeRecord;
    private JLabel frameMbs;
    private JLabel maxFrameMbs;
    private JLabel gcFree;
    private JLabel gcUsed;
    private JLabel time;
    private RecordManager recordManager;
    private UiBooster booster;

    public RecordPage(RecordManager recordManager, UiBooster booster){
        super("Painel da Stream");
        this.recordManager = recordManager;
        this.booster = booster;
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        this.add(panel);
        this.setSize(320, 320);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Average frame size: "));
        frameMbs = new JLabel();
        p.add(frameMbs);
        panel.add(p);
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Total Frame Size: "));
        maxFrameMbs = new JLabel();
        p.add(maxFrameMbs);
        panel.add(p);
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Duração da Stream: "));
        time = new JLabel();
        p.add(time);
        panel.add(p);
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("GC Free Memory: "));
        gcFree = new JLabel();
        p.add(gcFree);
        panel.add(p);
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("GC Used Memory: "));
        gcUsed = new JLabel();
        p.add(gcUsed);
        panel.add(p);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.stopRecord = new JButton("Parar stream");
        buttons.add(stopRecord);
        stopRecord.addActionListener(e -> recordManager.stopRecord());
        this.optimizeRecord = new JButton("Otimizar stream");
        buttons.add(optimizeRecord);
        panel.add(buttons);
        this.setVisible(true);
    }

    public void updateFrameMBs(FixedQueue<Integer> sizes){
        AtomicInteger max = new AtomicInteger();
        sizes.getQueue().forEach(max::addAndGet);
        int avg = max.get() / sizes.getQueue().size();
        avg = avg / 1024;
        frameMbs.setText(avg+" KB/s");
        maxFrameMbs.setText(avg * sizes.getQueue().size()+"KB");
        gcFree.setText((Runtime.getRuntime().freeMemory() / 1024 / 1024)+" MB");
        gcUsed.setText(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024)+" MB");
    }

    public void updateTimer(long timestamp){
        long time = System.currentTimeMillis();
        long dff = time - timestamp;
        this.time.setText((dff / 1000 / 60) +"min");
    }
}
