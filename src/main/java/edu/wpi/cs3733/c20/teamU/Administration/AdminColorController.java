package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamU.App;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.beans.binding.BooleanBinding;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;

public class AdminColorController {

  @FXML private JFXRadioButton light;
  @FXML private JFXRadioButton dark;
  @FXML private JFXRadioButton custom;
  @FXML private JFXColorPicker color1;
  @FXML private JFXColorPicker color2;
  @FXML private JFXColorPicker color3;
  @FXML private JFXColorPicker color4;
  @FXML private JFXColorPicker color5;
  @FXML private JFXButton cancel;
  private int color1R, color1G, color1B;
  private int color2R, color2G, color2B;
  private int color3R, color3G, color3B;
  private int color4R, color4G, color4B;
  private int color5R, color5G, color5B;

  @FXML
  private void confirm() throws IOException, URISyntaxException {
    String main1, main2, main3, main4, main5;
    CSSFileEditor fileEditor = new CSSFileEditor(App.class.getResource("/light_theme/light.css"));
    if (custom.isSelected()) {
      color1R = (int) (color1.getValue().getRed() * 255);
      color1B = (int) (color1.getValue().getBlue() * 255);
      color1G = (int) (color1.getValue().getGreen() * 255);
      main1 = RGBFormatter(color1B, color1R, color1G);

      color2R = (int) (color2.getValue().getRed() * 255);
      color2B = (int) (color2.getValue().getBlue() * 255);
      color2G = (int) (color2.getValue().getGreen() * 255);
      main2 = RGBFormatter(color2B, color2R, color2G);

      color3R = (int) (color3.getValue().getRed() * 255);
      color3B = (int) (color3.getValue().getBlue() * 255);
      color3G = (int) (color3.getValue().getGreen() * 255);
      main3 = RGBFormatter(color3B, color3R, color3G);

      color4R = (int) (color4.getValue().getRed() * 255);
      color4B = (int) (color4.getValue().getBlue() * 255);
      color4G = (int) (color4.getValue().getGreen() * 255);
      main4 = RGBFormatter(color4B, color4R, color4G);

      color5R = (int) (color5.getValue().getRed() * 255);
      color5B = (int) (color5.getValue().getBlue() * 255);
      color5G = (int) (color5.getValue().getGreen() * 255);
      main5 = RGBFormatter(color5B, color5R, color5G);

//      System.out.println(main1);
//      System.out.println(main2);
//      System.out.println(main3);
//      System.out.println(main4);
//      System.out.println(main5);
      fileEditor.writeCSSProperty("*", "-color-1: rgb(255, 249, 233)", main1);
      fileEditor.writeCSSProperty("*", "-color-2: rgb(150, 150, 150)", main2);
      fileEditor.writeCSSProperty("*", "-color-3: rgb(219, 198, 179)", main3);
      fileEditor.writeCSSProperty("*", "-color-4: rgb(184, 154, 140)", main4);
      fileEditor.writeCSSProperty("*", "-color-5: rgb(77, 77, 77)", main5);
    } else if(dark.isSelected()) {
      fileEditor.writeCSSProperty("*", "-color-1: rgb(255, 249, 233)", "rgb(36, 52, 71)");
      fileEditor.writeCSSProperty("*", "-color-2: rgb(150, 150, 150)", "rgb(255, 255, 255)");
      fileEditor.writeCSSProperty("*", "-color-3: rgb(219, 198, 179)", "rgb(255, 255, 255)");
      fileEditor.writeCSSProperty("*", "-color-4: rgb(184, 154, 140)", "rgb(20, 29, 38)");
      fileEditor.writeCSSProperty("*", "-color-5: rgb(77, 77, 77)", "rgb(20, 29, 38)");
    }

//    String theme = App.class.getResource("/light_theme/light.css").toExternalForm();
    Notifications.create().text("Color Scheme Changed!").show();
    cancel.fire();

  }

  @FXML
  private void cancel() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
  }

  private String RGBFormatter(int blue, int red, int green) {
    return "rgb(" + red + ", " + green + ", " + blue + ")";
  }

  @FXML
  private void initialize() {
    custom.setSelected(true);
    light.setDisable(true);
    dark.setDisable(true);
    custom.addEventHandler(MOUSE_CLICKED, event -> {
      if (custom.isSelected()) {
        light.setDisable(true);
        dark.setDisable(true);
        color1.setDisable(false);
        color2.setDisable(false);
        color3.setDisable(false);
        color4.setDisable(false);
        color5.setDisable(false);
        light.setSelected(false);
        dark.setSelected(false);

      } else if (!custom.isSelected()) {
        color1.setDisable(true);
        color2.setDisable(true);
        color3.setDisable(true);
        color4.setDisable(true);
        color5.setDisable(true);
        light.setDisable(false);
        dark.setDisable(false);
      }
    });
  }
}
