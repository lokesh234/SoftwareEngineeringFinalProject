package edu.wpi.cs3733.c20.teamU;

import com.sun.javafx.css.StyleCache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class FireController {
    @FXML
    private void checkout() {
        App.getPrimaryStage().setScene(App.getHomeScene());
    }
    @FXML
    private void initialize(){
        App.getHome().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getLogin().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getStart().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getPath().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getSecurity().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });

        App.getAdmin().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
    }
}
