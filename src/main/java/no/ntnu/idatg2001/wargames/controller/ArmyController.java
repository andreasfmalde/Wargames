package no.ntnu.idatg2001.wargames.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import no.ntnu.idatg2001.wargames.model.GameObserver;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.UnitFactory;
import no.ntnu.idatg2001.wargames.utility.FileHandler;

/**
 * Army controller class responsible for changing and manipulation
 * the army views in the GUI. Connecting the view and the model classes.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class ArmyController implements Initializable, GameObserver {

  private Army army;
  private Army armyCopy;
  private GameManager manager;

  // ----- JavaFX variables -----
  @FXML private Label armyNameLabel;
  @FXML private Label infantryUnits;
  @FXML private Label cavalryUnits;
  @FXML private Label rangedUnits;
  @FXML private Label commanderUnits;
  @FXML private Label armyFileLabel;
  @FXML private Label totalUnits;
  @FXML private VBox armyMainPane;
  @FXML private TableColumn<Unit, Integer> unitHealthColumn;
  @FXML private TableColumn<Unit, String> unitNameColumn;
  @FXML private TableColumn<Unit, String> unitTypeColumn;
  @FXML private TableView<Unit> armyTableView;

  /**
   * Pressing the load army button will open a file choose to search for an
   * army file. Once a valid army file is chosen, the army view will be loaded
   * with all the information.
   * @param actionEvent N/A
   */
  @FXML
  private void loadArmyButtonPressed(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    Army previousArmy = army;
    try{
      fileChooser.setInitialDirectory(new File(Path.of(".").toAbsolutePath().normalize().toString()));
      // Get an army file from the file system
      File armyFile = fileChooser.showOpenDialog(armyMainPane.getScene().getWindow());
      // Make an army object from the file
      army = FileHandler.getArmyFromFile(armyFile);
      // Update the game manager of the newly selected army
      manager.changeArmy(army,previousArmy);
      // Update the tableview with all units in the army
      armyTableView.setItems((ObservableList<Unit>) army.getAllUnits());

      // Setting the cell values in the table view based on the name, health and type of unit
      unitNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      unitHealthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
      unitTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

      // Displaying information of the army name and amount of units to the screen.
      armyNameLabel.setText(army.getName());
      displayUnitAmount();

      // File name of the army file selected
      armyFileLabel.setText(armyFile.getName());
    }catch (IOException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }catch (Exception e){
      //TODO: Change this alert
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }

  }

  /**
   * Display the current amount of infantry, ranged, cavalry and commander units
   * in the army, to the screen.
   */
  private void displayUnitAmount(){
    infantryUnits.setText(String.valueOf(army.getInfantryUnits().size()));
    cavalryUnits.setText(String.valueOf(army.getCavalryUnits().size()));
    rangedUnits.setText(String.valueOf(army.getRangedUnits().size()));
    commanderUnits.setText(String.valueOf(army.getCommanderUnits().size()));
    totalUnits.setText(String.valueOf(army.getAllUnits().size()));
  }

  /**
   * Initialization method of the army controller class. Will be called in the start
   * to set up everything.
   * @param url N/A
   * @param resourceBundle N/A
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //Get instance of game manager class
    manager = GameManager.getInstance();
    // Add army controller as an observer
    manager.addObserver(this);
  }

  /**
   * Update the army controller based on new battle information.
   * @param input Input string from the battle simulation
   */
  @Override
  public void updateState(String input) {
    armyTableView.refresh();
    displayUnitAmount();
  }

  @Override
  public void updateCopies() {
    ObservableList<Unit> units = FXCollections.observableArrayList();
    for(Unit unit : army.getAllUnits()){
      units.add(UnitFactory.createUnit(unit.getClass().getSimpleName(),unit.getName(),unit.getHealth()));
    }
    armyCopy = new Army(army.getName(),units);
  }

  @Override
  public void resetState() {
    if(armyCopy != null){
      manager.changeArmy(armyCopy,army);
      army = armyCopy;
      armyCopy = null;
      armyTableView.setItems((ObservableList<Unit>) army.getAllUnits());
      this.updateState("");
    }

  }


}
