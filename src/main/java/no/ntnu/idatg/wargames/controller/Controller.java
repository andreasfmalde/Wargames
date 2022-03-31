package no.ntnu.idatg.wargames.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
  @FXML
  private Label mainLabel;

  @FXML
  public void buttonPressed() {
    mainLabel.setText("This works!");
  }
}
