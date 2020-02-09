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

//    private String userCheck;
//    private String passCheck;

    /**
     *
     * @param user
     * @param pass
     */
    private void sendLogin(String user, String pass) {

    }

    public void setPopup(Popup popup, Parent parent) {
        this.popup = popup;
        this.parent = parent;
    }

//    @FXML
//    private void advanceScene(ActionEvent e) {
//        App.getPrimaryStage().getScene().setRoot(parent);
//    }

    /**
     * checks whether is the user entered the correct credentials
     * @return false if incorrect, true if correct
     */
    @FXML
    private boolean isAuthorized() {
        // call some database function to check if password is correct
//        String user = usernameField.getText();
//        String pass = passwordField.getText();
        boolean haveAccess = Database.checkCred(usernameField.getText(), passwordField.getText());
        if(!haveAccess) {
            System.out.println("yoloswag");
            usernameField.setPromptText("");
            passwordField.setPromptText("");
            usernameField.setStyle("-fx-border-color: red");
            passwordField.setStyle("-fx-border-color: red");
            return false;
        } else return true;
//        System.out.println("yolo swag");
//        return Database.checkCred(user, pass);
    }

    @FXML
    private void initialize() {
//        loginEnter.setDisable(true);
    }

}
