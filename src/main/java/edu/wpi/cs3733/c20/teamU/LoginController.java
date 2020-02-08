package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;

import java.awt.*;

public class LoginController {

    @FXML private TextField user;
    @FXML private TextField pass;

//    private String userCheck;
//    private String passCheck;

    /**
     *
     * @param user
     * @param pass
     */
    private void sendLogin(String user, String pass) {

    }


    /**
     * checks whether is the user entered the correct credentials
     * @return false if incorrect, true if correct
     */
    private boolean isAuthorized() {
        return false;
    }

}
