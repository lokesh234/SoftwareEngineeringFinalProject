package edu.wpi.cs3733.c20.teamU;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;;


public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginEnter;

    private Popup popup;
    private Parent parent;
    private Parent home;
    private int trackLoginCount;
    private boolean didFail;

    public void setAttributes(Parent parent, Parent home, Popup popup) {
        this.popup = popup;
        this.parent = parent;
        this.home = home;
    }

    /**
     * checks whether is the user entered the correct credentials
     * @return false if incorrect, true if correct
     */
    @FXML
    private void isAuthorized() {

        boolean haveAccess = Database.checkCred(usernameField.getText(), passwordField.getText());
        if(!haveAccess) {
      //            System.out.println("yoloswag");
            if (trackLoginCount == 3) {
//                System.out.println("fail");
                trackLoginCount = 0;
                changeScene();
            }
            didFail = true;
            trackLoginCount++;
            usernameField.setPromptText("");
            passwordField.setPromptText("");
            usernameField.setStyle("-fx-border-color: red");
            passwordField.setStyle("-fx-border-color: red");
        } else {
            trackLoginCount = 0;
            didFail = false;
            changeScene();
        }
    }

    @FXML
    private void changeScene() {
        if(didFail) {
            didFail = false;
            this.home.setOpacity(1);
            this.home.setDisable(false);
            this.popup.hide();
        } else {
            // go to admin stuff scene
        }
    }

    @FXML
    private void exitPopup() {
        didFail = false;
        this.home.setOpacity(1);
        this.home.setDisable(false);
        this.popup.hide();
    }

    @FXML
    private void initialize() {
//        loginEnter.setDisable(true);
    }

}
