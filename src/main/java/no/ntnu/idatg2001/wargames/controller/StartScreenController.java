package no.ntnu.idatg2001.wargames.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

/**
 * Start screen controller class responsible for manipulating
 * the start screen view in the GUI. Making sure the
 * user gets to the right view.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class StartScreenController {

  @FXML
  private AnchorPane mainPane;

  /**
   * Method that will navigate the user to the change army
   * view
   * @param event N/A
   * @throws IOException if no FXML file is found
   */
  @FXML
  private void createArmyButtonPressed(ActionEvent event) throws IOException {
    Stage stage = (Stage) mainPane.getScene().getWindow();
    Parent root = ViewLoader.getFXML("create-army").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Method that will navigate the user to the battle simulation
   * view
   * @param event N/A
   * @throws IOException if no FXML file is found
   */
  @FXML
  private void battleSimulationButtonPressed(ActionEvent event) throws IOException {
    Stage stage = (Stage) mainPane.getScene().getWindow();
    Parent root = ViewLoader.getFXML("battle-window").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Method that will navigate the user to the "about"
   * view
   * @param event N/A
   * @throws IOException if no FXML file is found
   */
  @FXML
  private void aboutButtonPressed(ActionEvent event) {
    //TODO: IMPLEMENT ABOUT PAGE
  }
}
