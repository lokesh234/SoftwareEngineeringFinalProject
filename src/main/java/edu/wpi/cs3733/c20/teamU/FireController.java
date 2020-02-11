package edu.wpi.cs3733.c20.teamU;

import com.sun.javafx.css.StyleCache;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class FireController {
    @FXML
    private void fireEvent(){
        App.getPrimaryStage().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e ->{
            KeyCombination keyF = KeyCombination.keyCombination("F");
           if(keyF.match(e)){
                System.out.println("Pressed F");
                App.getPrimaryStage().setScene(App.getFireScene());
                }
            });
        }
    }