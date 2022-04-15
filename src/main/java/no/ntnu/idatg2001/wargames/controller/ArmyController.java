package no.ntnu.idatg2001.wargames.controller;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

public class ArmyController {

  public void loadArmyButtonPressed(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.showOpenDialog(null);
  }
}
