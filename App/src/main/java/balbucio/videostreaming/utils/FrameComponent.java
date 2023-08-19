package balbucio.videostreaming.utils;

import balbucio.videostreaming.model.VideoQuality;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameComponent extends JComponent {

    private BufferedImage image;
    private VideoQuality  quality;

    public FrameComponent(BufferedImage image){
        this.image = image;
    }

    public void setFrame(BufferedImage image){
        this.image = image;
        this.repaint();
    }

    public void setQuality(VideoQuality quality){
        this.quality = quality;
    }

    @Override
    protected void paintComponent(Graphics g) {
        paint(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        paint(g);
    }

    @Override
    public void paintAll(Graphics g) {
        paint(g);
    }

    @Override
    protected void paintChildren(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.drawImage(image, 0, 0, null);
    }
}
