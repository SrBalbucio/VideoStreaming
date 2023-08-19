package balbucio.videostreaming.page;

import balbucio.org.ejsl.component.JImage;
import balbucio.org.ejsl.utils.ImageUtils;
import balbucio.videostreaming.utils.FrameComponent;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WatchPage extends JFrame {

    @Getter
    private BufferedImage frame;
    private FrameComponent image;

    public WatchPage(){
        super("Watch Stream");
        this.setLayout(new BorderLayout());
        this.image = new FrameComponent(ImageUtils.toBufferedImage(ImageUtils.getImage(this.getClass().getResourceAsStream("/logo.png"))));
        this.add(image, BorderLayout.CENTER);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void setFrame(BufferedImage img){
        image.setFrame(img);
        this.revalidate();
        this.repaint();
    }

}
