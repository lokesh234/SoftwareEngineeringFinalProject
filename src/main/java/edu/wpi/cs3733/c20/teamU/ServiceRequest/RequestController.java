package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import edu.wpi.cs3733.c20.teamR.AppointmentRequest;
import edu.wpi.cs3733.c20.teamU.App;
import foodRequest.ServiceException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import foodRequest.FoodRequest;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RequestController {
    private HashMap<JFXButton, String> customButtons = new HashMap<>();
    @FXML private JFXMasonryPane masonry;

    @FXML
    public void closeRequestScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getRequestPop().getContent().clear();
    }

    @FXML
    public void openMedicineForm(ActionEvent e){
        App.loadMedicine();
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().getContent().clear();
        App.getMedicinePop().getContent().add(App.getMedicine());
        App.getMedicinePop().show(App.getPrimaryStage());
    }

    @FXML
    public void openITRequest() {
        App.loadIT();
        App.getRequestPop().getContent().remove(0);
        App.getRequestPop().getContent().clear();
        App.getITPop().getContent().add(App.getIT());
        App.getITPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openReligiousRequest() {
        App.loadReligious();
        App.getRequestPop().getContent().remove(0);
        App.getReligiousPop().getContent().add(App.getReligious());
        App.getReligiousPop().show(App.getPrimaryStage());
    }


    @FXML
    public void openExtTransportRequest() {
        App.loadExtTransport();
        App.getRequestPop().getContent().remove(0);
        App.getExtTransportPop().getContent().add(App.getExternalTransport());
        App.getExtTransportPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openIntTransportRequest() {
        App.loadIntTransport();
        App.getRequestPop().getContent().remove(0);
        App.getIntTransportPop().getContent().add(App.getInternalTransport());
        App.getIntTransportPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openFlowerForm() {
        App.loadFlower();
        App.getRequestPop().getContent().clear();
        App.getFlowerPop().getContent().add(App.getFlower());
        App.getFlowerPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openClownRequest() {
        App.loadClown();
        App.getRequestPop().getContent().remove(0);
        App.getClownPop().getContent().add(App.getClown());
        App.getClownPop().show(App.getPrimaryStage());

    }

    @FXML
    public void openSanRequest(){
        App.loadSan();
        App.getRequestPop().getContent().remove(0);
        App.getSanRequestPop().getContent().add(App.getSanRequest());
        App.getSanRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openLangaugeRequest(){
        App.loadLanguage();
        App.getRequestPop().getContent().remove(0);
        App.getLanguageSRPop().getContent().add(App.getLanguageSR());
        App.getLanguageSRPop().show(App.getPrimaryStage());
    }

    @FXML
    private void openGiftDeliveryRequest() {
        App.loadGift();
        App.getRequestPop().getContent().clear();
        App.getPopup().getContent().add(App.getGiftDelivery());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void openMealService() throws ServiceException {
        App.getRequestPop().getContent().clear();
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.run(0, 0, 1080, 1920, "/ight_theme/light.css", null, null);
    }

    @FXML
    private void openAppointment() throws Exception {
        App.getRequestPop().getContent().clear();
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
//        FoodRequest foodRequest = new FoodRequest();
        AppointmentRequest.run(0, 0, 1080, 1920, "/light_theme/light.css", null, null);
    }

    private void clearButtons() {
        for (Map.Entry<JFXButton, String> pair : customButtons.entrySet()) {
            removeFromPath(pair.getKey());
        }
        customButtons.clear();
    }

    private EventHandler<MouseEvent> customRequestHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            App.loadGeneric();
            App.getRequestPop().getContent().clear();
            App.getRequestPop().getContent().add(App.getGeneric());
            App.getRequestPop().show(App.getPrimaryStage());
            App.getGenericController().setType(customButtons.get((JFXButton) event.getSource()));
        }
    };

    public void updateButtons() {
        clearButtons();
        File dir = new File("CustomRequests");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                String reqType = f.getName().split("\\.")[0];
                JFXButton bu = new JFXButton();
                Scanner s = null;
                try {
                    s = new Scanner(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String longName = s.nextLine();
                bu.setText(longName);
                ImageView im = new ImageView(new Image("png_files/cart-plus.png"));
                im.setFitHeight(125);
                im.setFitWidth(125);
                bu.setGraphic(im);
                bu.setFont(new Font("Segoe UI Bold", 14));
                bu.setOnMouseClicked(customRequestHandler);
                addToPath(bu);
                customButtons.put(bu, reqType);
            }
        }
        //Get list of services from the database
        //For every service that isn't a built-in request:
    }

    private void addToPath(Node e) {
        masonry.getChildren().add(e);
    }

    private void removeFromPath(Node e) {
        masonry.getChildren().remove(e);
    }

}
