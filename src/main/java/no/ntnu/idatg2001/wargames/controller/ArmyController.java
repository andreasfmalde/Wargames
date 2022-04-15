package no.ntnu.idatg2001.wargames.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ArmyController {

  @FXML
  private VBox armyMainPane;

  public void loadArmyButtonPressed(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.showOpenDialog(armyMainPane.getScene().getWindow());
  }
}
