package no.ntnu.idatg2001.wargames.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.Main;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

/**
 * Controller class for the "about/FAQ" view of the
 * application. Handling all button event on screen.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class AboutController {


  @FXML private Label headLine;

  /**
   * Exit confirmation pop-up before closing the application
   * @param event N/A
   */
  @FXML
  private void closeButtonPressed(ActionEvent event) {
    Main.exitApplication((Stage) headLine.getScene().getWindow());
  }

  /**
   * Change view to the create army view
   * @param event N/A
   * @throws IOException if no FXML is found
   */
  @FXML
  private void createArmyButtonPressed(ActionEvent event) throws IOException {
    Stage stage =(Stage)headLine.getScene().getWindow();
    Parent root = ViewLoader.getFXML("create-army").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Change view to the battle simulation view
   * @param event N/A
   * @throws IOException if no FXML is found
   */
  @FXML
  private void simulateButtonPressed(ActionEvent event) throws IOException {
    Stage stage =(Stage)headLine.getScene().getWindow();
    Parent root = ViewLoader.getFXML("battle-window").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Changing screen view from the createArmy view to the start screen view
   * @param event N/A
   * @throws IOException If no FXML file is found
   */
  @FXML
  private void goBackButtonPressed(ActionEvent event) throws IOException {
    Stage stage =(Stage)headLine.getScene().getWindow();
    Parent root = ViewLoader.getFXML("start-screen").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

}
