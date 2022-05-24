package no.ntnu.idatg2001.wargames;


import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
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

  /**
   * Start method for the application. Used to launch the first
   * window of the app.
   * @param stage Primary stage
   * @throws IOException if no FXML file is found
   */
  @Override
  public void start(Stage stage) throws IOException {
    Parent root = ViewLoader.getFXML("start-screen").load();
    Scene scene = new Scene(root);
    stage.setTitle("War Games");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.getIcons().add(new Image(
        Objects.requireNonNull(getClass().getResource("images/logo.png")).toExternalForm()));

    // When trying to exit the application the exit dialog will pop up
    stage.setOnCloseRequest(windowEvent -> {
      windowEvent.consume();
      exitApplication(stage);
    });

    stage.show();
  }

  /**
   * Dialog pop-up to make sure the user want to exit the application-
   * @param stage The stage where the action event happened.
   */
  public static void exitApplication(Stage stage){
    Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel the application?");
    cancelAlert.setHeaderText("Are you sure?");
    cancelAlert.showAndWait().filter(answer-> answer == ButtonType.OK)
        .ifPresent(response -> stage.close());

  }


}
