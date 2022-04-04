package no.ntnu.idatg2001.wargames.controller;

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
