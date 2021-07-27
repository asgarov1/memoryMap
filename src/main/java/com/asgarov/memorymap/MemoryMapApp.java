package com.asgarov.memorymap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.asgarov.memorymap.constants.Constants.IO_DEVS_BLOCK_SIZE;
import static com.asgarov.memorymap.util.Formatter.*;
import static com.asgarov.memorymap.util.UiUtil.*;

public class MemoryMapApp extends Application {
    public static final String DEFAULT_TEXT = "Please provide your inputs in K (so instead of 32K just input 32)";

    private final VBox root = new VBox(20);
    private final TextArea mainTextArea = createNonEditableTextArea(360, 40, DEFAULT_TEXT);
    private final TextField totalMemoryInput = createTextField(75);
    private final TextField romInput = createTextField(75);
    private final TextField ramInput = createTextField(75);
    private final TextField flashMemoryInput = createTextField(75);
    private final TextField numberOfIoDevicesInput = createTextField(75);

    /**
     * Main method to run the class
     *
     * @param args -
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * An override of the parent JavaFX Application class
     * Here I define main components and display logic
     *
     * @param primaryStage -
     */
    @Override
    public void start(Stage primaryStage) {
        setDefaultValues();
        //create labels for Shape and Color
        Label totalMemoryLabel = createLabel("Total:", Color.BLACK, Font.font("Arial", 15));
        Label romLabel = createLabel("ROM:", Color.BLACK, Font.font("Arial", 15));
        Label ramLabel = createLabel("RAM:", Color.BLACK, Font.font("Arial", 15));
        Label flashMemoryLabel = createLabel("FLASH:", Color.BLACK, Font.font("Arial", 15));

        Label numberOfIosLabel = createLabel("Number of IO Devices:", Color.BLACK, Font.font("Arial", 15));

        // Create Button and define event handling to it (on action -> processAction)
        Button calculateButton = createButton("Calculate");
        calculateButton.setOnAction(this::processAction);


        // Defining HBox to hold all the input components (2 labels and 2 textFiels)
        HBox rowOne = new HBox(10);
        rowOne.setAlignment(Pos.TOP_CENTER);
        rowOne.getChildren().addAll(totalMemoryLabel, totalMemoryInput, romLabel, romInput);

        HBox rowTwo = new HBox(10);
        rowTwo.setAlignment(Pos.BOTTOM_CENTER);
        rowTwo.getChildren().addAll(ramLabel, ramInput, flashMemoryLabel, flashMemoryInput);

        HBox rowThree = new HBox(10);
        rowThree.setAlignment(Pos.BOTTOM_CENTER);
        rowThree.getChildren().addAll(numberOfIosLabel, numberOfIoDevicesInput);

        // main component to hold all the other components
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(mainTextArea, rowOne, rowTwo, rowThree, calculateButton, new Polygon(0.0, 0.0));

        // Primary scene defined
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Memory Mapping Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setDefaultValues() {
        totalMemoryInput.setText("1024");
        romInput.setText("128");
        ramInput.setText("512");
        flashMemoryInput.setText("0");
        numberOfIoDevicesInput.setText("3");
    }

    /**
     * Event handling method for the display button
     * Display correspoding error messages in the main text area in case something was filled wrong
     *
     * @param event -
     */
    private void processAction(ActionEvent event) {
        try {
            int lastChildIndex = root.getChildren().size() - 1;
            TextArea answer = createNonEditableTextArea(300, 150, calculateMemoryMap());
            root.getChildren().set(lastChildIndex, answer);
            resetDisplayText();
        } catch (IllegalArgumentException exception) {
            displayError(exception.getMessage());
        }

    }

    private String calculateMemoryMap() {
        long totalMemory = Long.parseLong(totalMemoryInput.getText()) * 1024;
        long rom = Long.parseLong(romInput.getText()) * 1024;
        long ram = Long.parseLong(ramInput.getText()) * 1024;
        long flashDrive = Long.parseLong(flashMemoryInput.getText()) * 1024;
        long numberOfDevices = Long.parseLong(numberOfIoDevicesInput.getText());

        StringBuilder result = new StringBuilder();
        result.append(getRomLine(rom));
        result.append(getRamLine(rom, ram));
        result.append(getFlashDriveLine(rom, ram, flashDrive));

        result.append(getUnusedLine((rom + ram + flashDrive), totalMemory, numberOfDevices == 0 ? 0 : IO_DEVS_BLOCK_SIZE));

        if (numberOfDevices != 0) {
            result.append(getIoDevsLine(totalMemory, IO_DEVS_BLOCK_SIZE));
            result.append(getDetailedIoDevs(numberOfDevices, totalMemory));
        }

        return result.toString();
    }

    /**
     * Helper method that reset the display text to default message and style
     */
    private void resetDisplayText() {
        mainTextArea.setText(DEFAULT_TEXT);
        mainTextArea.setStyle("-fx-text-fill: black;");
    }

    /**
     * Helper method that display the error text with corresponding style
     */
    private void displayError(String errorMessage) {
        mainTextArea.setText(errorMessage);
        mainTextArea.setStyle("-fx-text-fill: red;");
    }
}
