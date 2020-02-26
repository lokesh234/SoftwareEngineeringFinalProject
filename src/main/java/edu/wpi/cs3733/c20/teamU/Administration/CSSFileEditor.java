package edu.wpi.cs3733.c20.teamU.Administration;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;

public class CSSFileEditor {

  URL path;
  String SPath;
  private InputSource inputSource;
  private CSSOMParser parser;
  CSSStyleSheetImpl styleSheet;

  public CSSFileEditor(URL path, String SPath) throws IOException, URISyntaxException {
    this.path = path;
    this.SPath = SPath;
    URL url = path;
    File urPath = new File(SPath);
    URI uriPath = urPath.toURI();
    System.out.println(uriPath);
    //inputSource = new InputSource(new StringReader(FileUtils.readFileToString(new File(url.toURI()), "UTF-8")));
    inputSource = new InputSource(new StringReader(SPath));
    //inputSource = new InputSource(new StringReader(FileUtils.readFileToString(new File(uriPath),"UTF-8")));
    parser = new CSSOMParser((new SACParserCSS3()));
    ErrorHandler errorHandler = new MyErrorHandler();
    parser.setErrorHandler(errorHandler);
    styleSheet = (CSSStyleSheetImpl) parser.parseStyleSheet(inputSource, null, null);

  }

  public void returnFileToOriginal() {
    try {
      FileReader original = new FileReader(getClass().getResource("/light_theme/lightCopy.css").toString());
      BufferedReader bufferedReader = new BufferedReader(original);
      FileWriter fileWriter = new FileWriter(path.toString(), true);
      String s;
      while (((s = bufferedReader.readLine()) != null)) {
        fileWriter.write(s);
        fileWriter.flush();
      }
      bufferedReader.close();
      fileWriter.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    System.out.println("rule : " + rule);
    if(rule != null) {
      CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
      List props = style.getProperties();
      for(Object p : props) {
        if(p.toString().equals(property)) {
//          System.out.println(p.toString());
          return (Property) p;
        }
      }
    }
//    System.out.println("can't find property");
    return retProp;
  }

  public void writeCSSProperty(String selector, String property, String newValue)
      throws URISyntaxException, IOException {
    Property property1 = getProperty(selector, property);
    System.out.println(selector);
    System.out.println(property);
    System.out.println(newValue);
    System.out.println(property1);
    if(property1 != null) {
      System.out.println("made it for: " + newValue);
      CSSValueImpl val = new CSSValueImpl();
      val.setCssText(newValue);
      System.out.println(property1.getValue());
      property1.setValue(val);
      System.out.println(property1.getValue());
      CSSFormat format = new CSSFormat();
      format.setRgbAsHex(true);
      System.out.println("HERE : " + this.styleSheet.getCssText(format));
      FileUtils.writeStringToFile(new File(SPath), this.styleSheet.getCssText(format));
      //FileUtils.writeStringToFile(new File(path.toURI()), this.styleSheet.getCssText(format));
    }
//    System.out.println("Failed to edit...");
  }
}
