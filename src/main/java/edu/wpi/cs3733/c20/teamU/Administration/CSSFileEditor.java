package edu.wpi.cs3733.c20.teamU.Administration;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import edu.wpi.cs3733.c20.teamU.App;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;

public class CSSFileEditor {

  URL path;
  private InputSource inputSource;
  private CSSOMParser parser;
  CSSStyleSheetImpl styleSheet;

  public CSSFileEditor(URL path) throws IOException, URISyntaxException {
    this.path = path;
    URL url = path;
    inputSource = new InputSource(new StringReader(FileUtils.readFileToString(new File(url.toURI()), "UTF-8")));
    parser = new CSSOMParser((new SACParserCSS3()));
    ErrorHandler errorHandler = new MyErrorHandler();
    parser.setErrorHandler(errorHandler);
    styleSheet = (CSSStyleSheetImpl) parser.parseStyleSheet(inputSource, null, null);

  }

  public boolean createCSS() throws IOException {

//    URI from = getClass().getResource(path).toURI();
//    URI to = getClass().getResource("/light_theme/lightCopy.css").toURI();
//    Path fromMain = Paths.get(from);
//    Path toMain = Paths.get(to);
//    Files.copy(fromMain, toMain, REPLACE_EXISTING);
    return true;
  }

  /**
   * getter for a specific declaration in CSS file
   * @param selector declaration
   * @return declaration
   */
  public CSSStyleRuleImpl getRule(String selector) {
    CSSRuleListImpl rules = (CSSRuleListImpl) this.styleSheet.getCssRules();
    CSSStyleRuleImpl rule;
    for(int i = 0; i < rules.getLength(); i++) {
      rule = (CSSStyleRuleImpl) rules.item(i);
      if(rule.getSelectorText().equals(selector)) {
        return rule;
      }
    }
    return null;
  }

  public Property getProperty(String selector, String property) {
    Property retProp = null;
    CSSStyleRuleImpl rule = getRule(selector);
    if(rule != null) {
      CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
      List props = style.getProperties();
      for(Object p : props) {
//        System.out.println(p.toString());
        if(p.toString().equals(property)) {
//          System.out.println(true);
          return (Property) p;
        }
      }
    }
    System.out.println("can't find property");
    return retProp;
  }

  public void writeCSSProperty(String selector, String property, String newValue)
      throws URISyntaxException, IOException {
    Property property1 = getProperty(selector, property);
    if(property1 != null) {
      CSSValueImpl val = new CSSValueImpl();
      val.setCssText(newValue);
//      System.out.println(property1.getValue());
      property1.setValue(val);
//      System.out.println(property1.getValue());
//      System.out.println("here");
      CSSFormat format = new CSSFormat();
      format.setRgbAsHex(true);
//      File output = new File(path.toURI());
      FileUtils.writeStringToFile(new File(path.toURI()), this.styleSheet.getCssText(format));
//      App.getHomeScene().getStylesheets().add(getClass().getResource("/light_theme/light.css").toString())
    }
  }
}
