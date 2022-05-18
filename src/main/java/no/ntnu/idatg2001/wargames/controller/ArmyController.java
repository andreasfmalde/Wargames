package no.ntnu.idatg2001.wargames.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import no.ntnu.idatg2001.wargames.model.GameManager;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.utility.FileHandler;

public class ArmyController implements Initializable {

  private Army army;
  private GameManager manager;
  @FXML private Label armyNameLabel;
  @FXML private Label infantryUnits;
  @FXML private Label cavalryUnits;
  @FXML private Label rangedUnits;
  @FXML private Label commanderUnits;
  @FXML private Label armyFileLabel;
  @FXML private VBox armyMainPane;
  @FXML private TableColumn<Unit, Integer> unitHealthColumn;
  @FXML private TableColumn<Unit, String> unitNameColumn;
  @FXML private TableColumn<Unit, String> unitTypeColumn;
  @FXML private TableView<Unit> armyTableView;

  public void loadArmyButtonPressed(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    Army previousArmy = army;
    try{
      fileChooser.setInitialDirectory(new File(Path.of(".").toAbsolutePath().normalize().toString()));
      File armyFile = fileChooser.showOpenDialog(armyMainPane.getScene().getWindow());
      army = FileHandler.getArmyFromFile(armyFile);
      manager.changeArmy(army,previousArmy);
      armyTableView.setItems((ObservableList<Unit>) army.getAllUnits());

      unitNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      unitHealthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
      unitTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

      armyNameLabel.setText(army.getName());
      infantryUnits.setText(String.valueOf(army.getInfantryUnits().size()));
      cavalryUnits.setText(String.valueOf(army.getCavalryUnits().size()));
      rangedUnits.setText(String.valueOf(army.getRangedUnits().size()));
      commanderUnits.setText(String.valueOf(army.getCommanderUnits().size()));

      armyFileLabel.setText(armyFile.getName());
    }catch (IOException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }catch (Exception e){
      //TODO: Change this alert
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    manager = GameManager.getInstance();
  }
}
