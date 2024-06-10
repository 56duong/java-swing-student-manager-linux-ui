package ps20250nguyenngocthuyduong.customcomponent;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import ps20250nguyenngocthuyduong.models.MyFont;

public class MyButton extends JButton {
    private Shape shape;
    
    public MyButton() {
        setOpaque(false);
        setFont(new MyFont().font);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
    }
    
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
         super.paintComponent(g);
    }
    
    protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border instanceof LineBorder) {
            LineBorder lineBorder = (LineBorder) border;
            g.setColor(lineBorder.getLineColor());
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        }
    }
    
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10);
         }
         return shape.contains(x, y);
    }
}

