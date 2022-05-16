package no.ntnu.idatg2001.wargames.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.model.GameManager;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

public class BattleController implements Initializable {

  private GameManager manager;

  @FXML
  private VBox leftArmy;
  @FXML
  private VBox rightArmy;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      Parent leftArmyNode = ViewLoader.getFXML("army").load();
      Parent rightArmyNode = ViewLoader.getFXML("army").load();
      leftArmy.getChildren().add(leftArmyNode);
      rightArmy.getChildren().add(rightArmyNode);
    } catch (IOException e) {
      e.printStackTrace();
    }
    manager = GameManager.getInstance();
  }


  public void createArmyButtonPressed(ActionEvent actionEvent) throws IOException {
    Stage stage =(Stage)leftArmy.getScene().getWindow();
    Parent root = ViewLoader.getFXML("create-army").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void showInfo(ActionEvent event) {
    List<Army> armies = manager.getArmies();
    new Alert(Alert.AlertType.INFORMATION,armies.get(0).getName()+" - "+armies.get(1).getName()).showAndWait();
  }
}
