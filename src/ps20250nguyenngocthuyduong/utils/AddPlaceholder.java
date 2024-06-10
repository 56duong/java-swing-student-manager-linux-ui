package ps20250nguyenngocthuyduong.utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
* The AddPlaceholder class provides a static method to add a placeholder text to a JTextField.
* Placeholder text is displayed in the text field until the user enters some text in it.
* Once the user enters some text, the placeholder text is replaced by the user's input.
* If the text field loses focus without any input from the user, the placeholder text is displayed again.
*/
public class AddPlaceholder {
    /**
    * Adds a placeholder text to the specified text field.
    *
    * @param textField the JTextField to add the placeholder text to
    * @param placeholderText the text to be displayed as a placeholder
    */
    public static void addPlaceholderText(JTextField textField, String placeholderText) {
        // Save the default foreground color of the text field
        Color defaultColor = textField.getForeground();
        
        // Set the placeholder text
        textField.setText(placeholderText);

        // Add a focus listener to handle the placeholder text
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(defaultColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), 100));
                    textField.setText(placeholderText);
                }
            }
        });
    }
}
