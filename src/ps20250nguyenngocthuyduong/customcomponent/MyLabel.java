package ps20250nguyenngocthuyduong.customcomponent;

import javax.swing.JLabel;
import ps20250nguyenngocthuyduong.models.MyFont;

public class MyLabel extends JLabel {
    public MyLabel() {
        setText("MyLabel");
        setFont(new MyFont().font);
    }
}
