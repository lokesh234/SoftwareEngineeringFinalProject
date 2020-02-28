package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class AdminColorController {

  @FXML
  private JFXRadioButton light, dark, custom;
  @FXML
  private JFXColorPicker color1, color2, color3, color4, color5;
  @FXML
  private JFXButton cancel;
  @FXML
  private Label user;
  private int color1R, color1G, color1B;
  private int color2R, color2G, color2B;
  private int color3R, color3G, color3B;
  private int color4R, color4G, color4B;
  private int color5R, color5G, color5B;
  private String themeLight = App.class.getResource("/light_theme/lightBackup.css")
      .toExternalForm();
  private String themeDark = App.class.getResource("/light_theme/dark.css").toExternalForm();
  private String themeCustom = "themeCustom";

  public void setUser(String user) {
    this.user.setText(user);
  }

  @FXML
  private void confirm() throws IOException, URISyntaxException {
    String main1, main2, main3, main4, main5;
    CSSFileEditor fileEditor = new CSSFileEditor(App.class.getResource("/light_theme/light.css"));
    String theme = "";
//    ArrayList<String> oldScheme = getCurrentColor("Dark");
    if (custom.isSelected()) {
//      color1R = (int) (color1.getValue().getRed() * 255);
//      color1B = (int) (color1.getValue().getBlue() * 255);
//      color1G = (int) (color1.getValue().getGreen() * 255);
//      main1 = RGBFormatter(color1R, color1G, color1B);
//
//      color2R = (int) (color2.getValue().getRed() * 255);
//      color2B = (int) (color2.getValue().getBlue() * 255);
//      color2G = (int) (color2.getValue().getGreen() * 255);
//      main2 = RGBFormatter(color2R, color2G, color2B);
//
//      color3R = (int) (color3.getValue().getRed() * 255);
//      color3B = (int) (color3.getValue().getBlue() * 255);
//      color3G = (int) (color3.getValue().getGreen() * 255);
//      main3 = RGBFormatter(color3R, color3G, color3B);
//
//      color4R = (int) (color4.getValue().getRed() * 255);
//      color4B = (int) (color4.getValue().getBlue() * 255);
//      color4G = (int) (color4.getValue().getGreen() * 255);
//      main4 = RGBFormatter(color4R, color4G, color4B);
//
//      color5R = (int) (color5.getValue().getRed() * 255);
//      color5B = (int) (color5.getValue().getBlue() * 255);
//      color5G = (int) (color5.getValue().getGreen() * 255);
//      main5 = RGBFormatter(color5R, color5G, color5B);
      main1 = formatter(color1);
      main2 = formatter(color2);
      main3 = formatter(color3);
      main4 = formatter(color4);
      main5 = formatter(color5);

      DatabaseWrapper.addColor(themeCustom, convertRGBToHex(color1),
          convertRGBToHex(color2), convertRGBToHex(color3),
          convertRGBToHex(color4), convertRGBToHex(color5), "000000");

      fileEditor.writeCSSProperty("*", "-color-1: rgb(255, 249, 233)", main1);
      fileEditor.writeCSSProperty("*", "-color-2: rgb(150, 150, 150)", main2);
      fileEditor.writeCSSProperty("*", "-color-3: rgb(219, 198, 179)", main3);
      fileEditor.writeCSSProperty("*", "-color-4: rgb(184, 154, 140)", main4);
      fileEditor.writeCSSProperty("*", "-color-5: rgb(77, 77, 77)", main5);
      theme = App.class.getResource("/light_theme/faker.css").toExternalForm();
      App.setTheme(theme);
      App.setIsDark(false);
    } else if (dark.isSelected()) {
      App.setIsDark(true);
      App.setTheme(themeDark);

    } else if (light.isSelected()) {
      App.setIsDark(false);
      App.setTheme(themeLight);
    }

    cancel.fire();
  }

  /**
   * function returns formatted RGB string to find in CSS based on JFXColorPicker
   *
   * @param cc color choice
   * @return String: rgb(255, 255, 255)
   */
  private String formatter(JFXColorPicker cc) {
    int ccR = (int) (cc.getValue().getRed() * 255);
    int ccG = (int) (cc.getValue().getBlue() * 255);
    int ccB = (int) (cc.getValue().getGreen() * 255);
    return RGBFormatter(ccR, ccG, ccB);
  }

  @FXML
  private void cancel() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
  }

  /**
   * formats RGB values into a string to extrapolate data from css
   *
   * @param red   red value
   * @param green green value
   * @param blue  blue value
   * @return String: rgb(255, 255, 255)
   */
  private String RGBFormatter(int red, int green, int blue) {
    return "rgb(" + red + ", " + green + ", " + blue + ")";
  }

  /**
   * returns list of selectors to find in CSS file
   * @param theme theme to search for in color database
   * @return list of String. Ex: (1) -color-1: rgb(255, 255, 255)
   */
  private ArrayList<String> getCurrentColor(String theme) {
    ArrayList<String> arrayListColors = new ArrayList<>();
    Colors colors = DatabaseWrapper.getColor(theme);
    String color1 = colors.getFirstColor();
    String color2 = colors.getSecondColor();
    String color3 = colors.getThirdColor();
    String color4 = colors.getFourthColor();
    String color5 = colors.getFifthColor();
    arrayListColors.add("-color-1: " + convertHexToRGB(color1));
    arrayListColors.add("-color-2: " + convertHexToRGB(color2));
    arrayListColors.add("-color-3: " + convertHexToRGB(color3));
    arrayListColors.add("-color-4: " + convertHexToRGB(color4));
    arrayListColors.add("-color-5: " + convertHexToRGB(color5));
    return arrayListColors;
  }

  /**
   * converts HEX to INT for red, blue, and green
   * @param color FFFFFF
   * @return rgb(255, 255, 255)
   */
  private String convertHexToRGB(String color) {
    int red = Color.decode(color).getRed();
    int green = Color.decode(color).getBlue();
    int blue = Color.decode(color).getGreen();
    return RGBFormatter(red, green, blue);
  }

  /**
   * converts RGB(red, green, blue) to HEX values
   * @param cc selected color value on JFXColorPicker
   * @return R: FF G: FF B: FF == FFFFFF
   */
  private String convertRGBToHex(JFXColorPicker cc) {
    String R = Integer.toHexString((int) cc.getValue().getRed());
    String G = Integer.toHexString((int) cc.getValue().getGreen());
    String B = Integer.toHexString((int) cc.getValue().getGreen());
    return R + G + B;
  }

  @FXML
  private void initialize() {
    custom.setSelected(true);
    light.setDisable(true);
    dark.setDisable(true);

    ToggleGroup colorGroup = new ToggleGroup();
    light.setToggleGroup(colorGroup);
    dark.setToggleGroup(colorGroup);
    colorGroup.selectToggle(null);

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
