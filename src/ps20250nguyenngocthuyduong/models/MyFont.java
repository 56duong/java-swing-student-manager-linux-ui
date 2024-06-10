package ps20250nguyenngocthuyduong.models;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
* The MyFont class is responsible for loading a custom font and creating two instances
* of it: one regular and one bold. The font used is Nunito Sans SemiBold, and it is
* loaded from a font file located in the resources/fonts directory.
*/
public class MyFont {
    public Font font, fontBold;

    /**
    * Default constructor that creates instances of the loaded font in regular and bold styles,
    * with a default size of 14.
    */
    public MyFont() {
        try {
            String url = "/ps20250nguyenngocthuyduong/resources/fonts/NunitoSans-SemiBold.ttf";
            
            InputStream is = getClass().getResourceAsStream(url);
            this.font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 14f);
            
            InputStream is1 = getClass().getResourceAsStream(url);
            this.fontBold = Font.createFont(Font.TRUETYPE_FONT, is1).deriveFont(Font.BOLD, 14f);
        }
        catch(FontFormatException | IOException e) {
        }
    }
    
    /**
    * Constructor that creates instances of the loaded font in regular and bold styles,
    * with a custom size specified by the size parameter.
    * 
    * @param size an integer specifying the size of the font
    */
    public MyFont(int size) {
        try {
            String url = "/ps20250nguyenngocthuyduong/resources/fonts/NunitoSans-SemiBold.ttf";
            
            InputStream is = getClass().getResourceAsStream(url);
            this.font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, size);
            
            InputStream is1 = getClass().getResourceAsStream(url);
            this.fontBold = Font.createFont(Font.TRUETYPE_FONT, is1).deriveFont(Font.BOLD, size);
        }
        catch(FontFormatException | IOException e) {
        }
    }
}
