package com.asgarov.memorymap.util;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UiUtil {

    /**
     * Helper method to create a TextField
     *
     * @param maxWidth
     * @return
     */
    public static TextField createTextField(int maxWidth) {
        TextField textField = new TextField();
        textField.setMaxWidth(maxWidth);
        return textField;
    }

    /**
     * Helper method to create a (non editable) TextArea
     *
     * @param width
     * @param height
     * @param defaultMessage
     * @return
     */
    public static TextArea createNonEditableTextArea(int width, int height, String defaultMessage) {
        TextArea textArea = new TextArea(defaultMessage);
        textArea.setMinSize(width, height);
        textArea.setMaxSize(width, height);
        textArea.setEditable(false);
        return textArea;
    }

    /**
     * Helper method to create a Label
     *
     * @param labelText
     * @param color
     * @param font
     * @return
     */
    public static Label createLabel(String labelText, Color color, Font font) {
        Label label = new Label(labelText);
        label.setTextFill(color);
        label.setFont(font);
        return label;
    }

    /**
     * Helper method to create a button
     *
     * @param text
     * @return
     */
    public static Button createButton(String text) {
        Button button = new Button();
        button.setText(text);
        return button;
    }
}
