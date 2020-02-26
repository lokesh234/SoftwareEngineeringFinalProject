package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;

public class GiftDeliveryController {
  @FXML private JFXTextField first;
  @FXML private JFXTextField last;
  @FXML private JFXTextField room;
  @FXML private JFXComboBox gift;
  @FXML private JFXButton submit;
  @FXML private JFXButton back;

  @FXML
  private void setSubmit() {
    String userFirst = first.getText();
    String userLast = last.getText();
    String userRoom = room.getText();
    String userGift = gift.getSelectionModel().getSelectedItem().toString();
    if(!checkFields()) {
      DatabaseWrapper.deliverySRAdd(userLast,userFirst,userGift,userRoom);
      Notifications.create().text("Gift Request has been submitted!").show();
      back.fire();
    }
  }

  @FXML
  private void returnToRequest() {
    clearField();
    App.getPopup().getContent().clear();
    App.getPopup().hide();
    App.getRequestPop().getContent().add(App.getRequest());
    App.getRequestPop().show(App.getPrimaryStage());
  }

  private void clearField() {
    first.clear();
    last.clear();
    room.clear();
    gift.getSelectionModel().clearSelection();
    first.setStyle("-fx-border-color:  #FFEEC9");
    last.setStyle("-fx-border-color:  #FFEEC9");
    room.setStyle("-fx-border-color:  #FFEEC9");
    gift.setStyle("-fx-border-color:  #FFEEC9");
  }

  private boolean checkFields() {
    boolean didFail = false;
    first.setStyle("-fx-border-color:  #FFEEC9");
    last.setStyle("-fx-border-color:  #FFEEC9");
    room.setStyle("-fx-border-color:  #FFEEC9");
    gift.setStyle("-fx-border-color:  #FFEEC9");
    if(first.getText().isEmpty()) {
      first.setStyle("-fx-border-color: red");
      didFail = true;
    }
    if(gift.getSelectionModel().getSelectedItem().toString().isEmpty()) {
      gift.setStyle("-fx-border-color: red");
      didFail = true;
    }
    if(last.getText().isEmpty()) {
      last.setStyle("-fx-border-color: red");
      didFail = true;
    }
    if(room.getText().isEmpty()) {
      room.setStyle("-fx-border-color: red");
      didFail = true;
    }
    return didFail;
  }
  @FXML
  private void initialize() {
    BooleanBinding blockSubmit = first.textProperty().isEmpty()
        .or(first.textProperty().isEmpty()).or(last.textProperty().isEmpty())
        .or(room.textProperty().isEmpty()).or(gift.valueProperty().isNull());
    submit.disableProperty().bind(blockSubmit);
    ObservableList<String> deliveryOptions =
        FXCollections.observableArrayList(
            "TeddyBear",
            "Chocolate",
            "Cookies"
        );
    gift.getItems().addAll(deliveryOptions);

//    submit.setDisable(true);
    BooleanBinding blockCheckBox = (first.textProperty().isEmpty())
            .or(last.textProperty().isEmpty()).or(room.textProperty().isEmpty())
            .or(gift.getSelectionModel().selectedItemProperty().isNull());
    submit.disableProperty().bind(blockCheckBox);


//    @FXML private JFXTextField first;
//    @FXML private JFXTextField last;
//    @FXML private JFXTextField room;
//    @FXML private JFXComboBox gift;
//    @FXML private JFXButton submit;
//    @FXML private JFXButton back;
  }
}
