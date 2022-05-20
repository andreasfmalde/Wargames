package no.ntnu.idatg2001.wargames.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import no.ntnu.idatg2001.wargames.Main;

/**
 * Class that will make it easier to
 * load different fxml views in the project.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class ViewLoader {

  /**
   * Private constructor to avoid initialization
   * of a view loader object
   */
  private ViewLoader(){}

  /**
   * Get a fxml loader object of a fxml file
   * specified in the parameter.
   * @param filename FXML filename
   * @return FXMLLoader object
   */
  public static FXMLLoader getFXML(String filename){
    return new FXMLLoader(ViewLoader.class.getResource(filename+".fxml"));
  }

  public static Image getImage(String filename){
    return new Image(Main.class.getResource("images/"+filename+".png").toExternalForm());
  }
}
