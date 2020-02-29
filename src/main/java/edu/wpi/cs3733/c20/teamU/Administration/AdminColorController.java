package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import java.io.IOException;
import java.net.URISyntaxException;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.awt.Color;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.util.ArrayList;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  JFXButton delete;
  @FXML
  private Label user;
  @FXML
  private JFXTextField themeName;
  @FXML
  private JFXComboBox listTheme;
  private int color1R, color1G, color1B;
  private int color2R, color2G, color2B;
  private int color3R, color3G, color3B;
  private int color4R, color4G, color4B;
  private int color5R, color5G, color5B;
  ObservableList<String> themes =
      FXCollections.observableArrayList();
  String theme = "Standard";

  public void setUser(String user) {
    this.user.setText(user);
  }

  @FXML
  private void confirm() throws IOException, URISyntaxException {
    String main1, main2, main3, main4, main5;
//    String APath = System.getProperty("user.dir");
//    String cssFile = APath + "/CSS/faker.css";
//    // System.out.println(cssF);
//    System.out.println(cssFile);
//    System.out.println(App.class.getResource("/light_theme/faker.css"));
//    //CSSFileEditor fileEditor = new CSSFileEditor(App.class.getResource("/light_theme/faker.css"));
//    CSSFileEditor fileEditor = new CSSFileEditor(App.class.getResource("/light_theme/faker.css"), cssFile);
//    String theme = "";
//    System.out.println(App.getHomeScene().getStylesheets());

    CSSFileEditor fileEditor = new CSSFileEditor(App.class.getResource("/light_theme/light.css"));
    ArrayList<String> oldScheme = getCurrentColor(theme);
    if (custom.isSelected()) {

      main1 = formatter(color1);
      main2 = formatter(color2);
      main3 = formatter(color3);
      main4 = formatter(color4);
      main5 = formatter(color5);
      System.out.println("Colors: " + main1 + " " + main2 + " " + main3 + " " + main4 + " " + main5);

      if (listTheme.getSelectionModel().getSelectedItem().toString().equals("New")) {
        if(themeName.getText().isEmpty()) return;
        theme = themeName.getText();
        DatabaseWrapper.addColor(theme, convertRGBToHex(color1),
            convertRGBToHex(color2), convertRGBToHex(color3),
            convertRGBToHex(color4), convertRGBToHex(color5), "000000");
      } else {
        theme = listTheme.getSelectionModel().getSelectedItem().toString();
      }
      selectPreset(oldScheme, theme, fileEditor);
    } else if (dark.isSelected()) {
      selectPreset(oldScheme, "Dark", fileEditor);
      theme = "Dark";

    } else if (light.isSelected()) {
      selectPreset(oldScheme, "Standard", fileEditor);
      theme = "Standard";
    }
    App.setTheme(App.class.getResource("/light_theme/light.css").toExternalForm());
    update();
  }

  /**
   * function changes the color scheme of the UI
   * @param old list of old RGB values to search for
   * @param preset color scheme to swap to
   * @param fileEditor css file to edit
   * @throws IOException
   * @throws URISyntaxException
   */
  private void selectPreset(ArrayList<String> old, String preset, CSSFileEditor fileEditor)
      throws IOException, URISyntaxException {
    Colors color = null;
    String color1, color2, color3, color4, color5;
    if (preset.equals("Dark")) {
      color = DatabaseWrapper.getColor("Dark");
    } else if (preset.equals("Standard")) {
      color = DatabaseWrapper.getColor("Standard");
    } else color = DatabaseWrapper.getColor(preset);
    color1 = convertHexToRGB(color.getFirstColor());
    color2 = convertHexToRGB(color.getSecondColor());
    color3 = convertHexToRGB(color.getThirdColor());
    color4 = convertHexToRGB(color.getFourthColor());
    color5 = convertHexToRGB(color.getFifthColor());

    fileEditor.writeCSSProperty("*", old.get(0), color1);
    fileEditor.writeCSSProperty("*", old.get(1), color2);
    fileEditor.writeCSSProperty("*", old.get(2), color3);
    fileEditor.writeCSSProperty("*", old.get(3), color4);
    fileEditor.writeCSSProperty("*", old.get(4), color5);
  }

  @FXML
  private void cancel() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
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

//    BooleanBinding bind = listTheme.getSelectionModel().selectedItemProperty().isNotEqualTo("New");
//    themeName.disableProperty().bind(bind);

    custom.addEventHandler(MOUSE_CLICKED, event -> {
      if (custom.isSelected()) {
        themeName.setDisable(false);
        listTheme.setDisable(false);
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
        themeName.setDisable(true);
        themeName.clear();
        listTheme.setDisable(true);
        listTheme.getSelectionModel().clearSelection();
        color1.setDisable(true);
        color2.setDisable(true);
        color3.setDisable(true);
        color4.setDisable(true);
        color5.setDisable(true);
        light.setDisable(false);
        dark.setDisable(false);
      }
    });
    update();
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
   *
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
   *
   * @param color FFFFFF
   * @return rgb(255, 255, 255)
   */
  private String convertHexToRGB(String color) {
    int red = Color.decode("#" + color).getRed();
    int green = Color.decode("#" + color).getGreen();
    int blue = Color.decode("#" + color).getBlue();
    return RGBFormatter(red, green, blue);
  }

  /**
   * converts RGB(red, green, blue) to HEX values
   *
   * @param cc selected color value on JFXColorPicker
   * @return R: FF G: FF B: FF == FFFFFF
   */
  private String convertRGBToHex(JFXColorPicker cc) {
//    System.out.println((int) (cc.getValue().getRed() * 255));
//    System.out.println((int) (cc.getValue().getBlue() * 255));
//    System.out.println(((int) (cc.getValue().getGreen() * 255)) + "\n");
    String R = Integer.toHexString((int) (cc.getValue().getRed() * 255));
    String G = Integer.toHexString((int) (cc.getValue().getGreen() * 255));
    String B = Integer.toHexString((int) (cc.getValue().getBlue() * 255));
    return R + G + B;
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
  private void deleteColor() {
    Object delete = listTheme.getSelectionModel().getSelectedItem();
    if (delete != null) {
      if (!delete.toString().equals(theme) && !delete.toString().equals("New")) {
        DatabaseWrapper.delColor(delete.toString());
        listTheme.getSelectionModel().clearSelection();
        update();
      }
    }
  }

  private void update() {
    ArrayList<Colors> colors = DatabaseWrapper.getAllColors();
//    ObservableList<String> themes =
////        FXCollections.observableArrayList();
    listTheme.getItems().clear();
    listTheme.getItems().add("New");

    for (int i = 0; i < colors.size(); i++) {
      String theme = colors.get(i).getColorTheme();
      if (theme.equals("Dark") || theme.equals("Standard") || theme.equals("New") || theme
          .equals("Colorblind")) {
        continue;
      }
      listTheme.getItems().add(theme);
//      themes.add(colors.get(i).getColorTheme());
    }
//    listTheme.getItems().addAll(themes);
  }
}
