package ps20250nguyenngocthuyduong.customcomponent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import ps20250nguyenngocthuyduong.utils.ImageResizing;
import ps20250nguyenngocthuyduong.utils.ImageUtil;

public class MyImagePanel extends JPanel {
    private Image img;

    public MyImagePanel(String img, int w, int h) {
//        this(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)).getImage(), w, h);
        this(ImageResizing.resizing(new ImageIcon(ImageUtil.getResource(img)).getImage(), w, h).getImage(), w, h);
    }

    public MyImagePanel(Image img, int w, int h) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(this), img.getHeight(this));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = ImageResizing.resizing(img, this.getWidth(), this.getHeight()).getImage();
    }
    
    public void setImg(String img) {
        this.img = ImageResizing.resizing(img, this.getWidth(), this.getHeight()).getImage();
    }
}
