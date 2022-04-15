package no.ntnu.idatg2001.wargames;


import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

/**
 * Main class containing the main method to
 * start the application
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class Main extends Application {
  /**
   * Starting point of the application
   * @param args Not used...
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = ViewLoader.getFXML("battle-window").load();
    Scene scene = new Scene(root);
    stage.setTitle("War Games");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
