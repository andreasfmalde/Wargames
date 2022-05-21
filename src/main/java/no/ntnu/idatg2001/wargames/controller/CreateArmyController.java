package no.ntnu.idatg2001.wargames.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.Main;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.UnitFactory;
import no.ntnu.idatg2001.wargames.utility.FileHandler;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

/**
 * Create army controller class responsible for creating an army object
 * based on the input from the user. Connecting the view and the army model class.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class CreateArmyController implements Initializable {

  private ObservableList<Unit> unitList;

  // ----- JavaFX variables -----
  @FXML private ComboBox<String> typeComboBox;
  @FXML private ListView<Unit> unitListView;
  @FXML private TextField unitAmount;
  @FXML private TextField unitHealth;
  @FXML private TextField unitName;
  @FXML private TextField armyName;


  /**
   * Initialization method of the createArmy controller class. Will be called in the start
   * to set up everything.
   * @param url N/A
   * @param resourceBundle N/A
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    String[] values = {"InfantryUnit","RangedUnit","CavalryUnit","CommanderUnit"};
    typeComboBox.setItems(FXCollections.observableArrayList(values));
    unitList = FXCollections.observableArrayList();
    unitListView.setItems(unitList);
  }

  /**
   * Creating a CSV file based on the army made in the createArmy view.
   * THe CSV file can be used later on the in battle simulation
   * @param actionEvent N/A
   */
  @FXML
  private void createArmy(ActionEvent actionEvent){
    try{
      Army army = new Army(armyName.getText());
      army.addAll(unitList);
      FileChooser chooser = new FileChooser();
      chooser.setInitialFileName(armyName.getText().replace(" ","-"));
      chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));
      chooser.setInitialDirectory( new File(Paths.get(".").toAbsolutePath().normalize().toString()));
      File file = chooser.showSaveDialog(armyName.getScene().getWindow());
      if(file != null){
        FileHandler.writeArmyToFile(army,file);
      }
    }catch (IOException | IllegalArgumentException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }
  }

  /**
   * Adding a unit to the unit list prior to making the army object.
   * The units in the unit list will become part of the army made
   * @param actionEvent N/A
   */
  @FXML
  private void addUnit(ActionEvent actionEvent){
    try{
      String unitType = String.valueOf(typeComboBox.getValue());
      String name = unitName.getText();
      int health = Integer.parseInt(unitHealth.getText());
      int amount = Integer.parseInt(unitAmount.getText());
      unitList.addAll(UnitFactory.createMultipleUnits(amount,unitType,name,health));
    }catch (NumberFormatException e){
      new Alert(Alert.AlertType.WARNING,"Insert a valid integer number").showAndWait();
    }catch (IllegalArgumentException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }
  }

  /**
   * Changing screen view from the createArmy view to the battle simulation view
   * @param actionEvent N/A
   * @throws IOException If no FXML file is found
   */
  @FXML
  private void simulateBattlePressed(ActionEvent actionEvent) throws IOException {
    Stage stage =(Stage)unitListView.getScene().getWindow();
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
    Stage stage =(Stage)unitListView.getScene().getWindow();
    Parent root = ViewLoader.getFXML("start-screen").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Deleting a selected unit from the unit list prior to making the army
   * object.
   * @param event N/A
   */
  @FXML
  private void deleteUnit(ActionEvent event) {
    Unit unitToRemove =  unitListView.getSelectionModel().getSelectedItem();
    if(unitToRemove == null){
      new Alert(Alert.AlertType.INFORMATION,"Please select a unit from the list to remove").showAndWait();
    }else{
      unitList.remove(unitToRemove);
    }

  }

  /**
   * Close the application
   * @param event N/A
   */
  @FXML
  private void closeApplication(ActionEvent event) {
    Main.exitApplication((Stage) unitName.getScene().getWindow());
  }
}
