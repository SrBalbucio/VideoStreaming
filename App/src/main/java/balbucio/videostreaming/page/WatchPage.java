package balbucio.videostreaming.page;

import balbucio.org.ejsl.component.JImage;
import balbucio.org.ejsl.utils.ImageUtils;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WatchPage extends JFrame {

    @Getter
    private BufferedImage frame;
    private JImage image;

    public WatchPage(){
        super("Watch Stream");
        this.setLayout(new BorderLayout());
        this.image = new JImage(ImageUtils.getImage(this.getClass().getResourceAsStream("/logo.png")));
        image.setMaxSize(true);
        image.setCenter(true);
        this.add(image, BorderLayout.CENTER);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setFrame(BufferedImage img){
        image.setImage(ImageUtils.getImage(img));
    }

    public void setFrame(Image img){
        image.setImage(img);
    }
}
