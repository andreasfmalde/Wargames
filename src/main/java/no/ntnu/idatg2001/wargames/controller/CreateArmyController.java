package no.ntnu.idatg2001.wargames.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
  @FXML private Label unitCounter;


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
    textFieldListener(unitHealth); // Adding text listener to unit health TextField to accept digits
    textFieldListener(unitAmount); // Adding text listener to unit amount TextField to accept digits
  }

  /**
   * Creating a CSV file based on the army made in the createArmy view.
   * THe CSV file can be used later on the in battle simulation
   */
  @FXML
  private void createArmy(){
    try{
      Army army = new Army(armyName.getText());
      army.addAll(unitList);
      FileChooser chooser = new FileChooser();
      chooser.setInitialFileName(armyName.getText().replace(" ","-"));
      chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated File","*.csv"));
      chooser.setInitialDirectory( new File(Paths.get(".").toAbsolutePath().normalize().toString()));
      File file = chooser.showSaveDialog(armyName.getScene().getWindow());
      if(file != null){
        FileHandler.writeArmyToFile(army,file);
        resetScreen();
      }
    }catch (IOException | IllegalArgumentException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }
  }

  /**
   * Reset all components on screen to their original state.
   */
  private void resetScreen(){
    for(TextField field : new TextField[]{armyName,unitName,unitHealth,unitAmount}){
      field.setText("");
    }
    removeAllUnits();
    typeComboBox.getSelectionModel().clearSelection();
  }

  /**
   * Method which restricts text field input to only accept digits.
   * @param textField TextField in GUI.
   */
  private void textFieldListener(TextField textField) {
    ChangeListener<String> cl = (observableValue, oldValue, newValue) -> {
      if (!newValue.matches("\\d")) {
        textField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    };
    textField.textProperty().addListener(cl);
  }

  /**
   * Adding a unit to the unit list prior to making the army object.
   * The units in the unit list will become part of the army made
   */
  @FXML
  private void addUnit(){
    try{
      String unitType = String.valueOf(typeComboBox.getValue());
      String name = unitName.getText();
      int health = Integer.parseInt(unitHealth.getText());
      int amount = Integer.parseInt(unitAmount.getText());
      if(unitList.size() + amount > Army.getMaxLimitOfUnits()){
        throw new IllegalArgumentException("Your total number of units will exceed the max limit of "+Army.getMaxLimitOfUnits());
      }
      unitList.addAll(UnitFactory.createMultipleUnits(amount,unitType,name,health));
      unitCounter.setText(String.valueOf(unitList.size()));
    }catch (NumberFormatException e){
      new Alert(Alert.AlertType.WARNING,"Insert a valid integer number").showAndWait();
    }catch (IllegalArgumentException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }
  }

  /**
   * Changing screen view from the createArmy view to the battle simulation view
   * @throws IOException If no FXML file is found
   */
  @FXML
  private void simulateBattlePressed() throws IOException {
    Stage stage =(Stage)unitListView.getScene().getWindow();
    Parent root = ViewLoader.getFXML("battle-window").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Changing screen view from the createArmy view to the start screen view
   * @throws IOException If no FXML file is found
   */
  @FXML
  private void goBackButtonPressed() throws IOException {
    Stage stage =(Stage)unitListView.getScene().getWindow();
    Parent root = ViewLoader.getFXML("start-screen").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Deleting a selected unit from the unit list prior to making the army
   * object.
   */
  @FXML
  private void deleteUnit() {
    Unit unitToRemove =  unitListView.getSelectionModel().getSelectedItem();
    if(unitToRemove == null){
      new Alert(Alert.AlertType.INFORMATION,"Please select a unit from the list to remove").showAndWait();
    }else{
      unitList.remove(unitToRemove);
      unitCounter.setText(String.valueOf(unitList.size()));
    }

  }

  /**
   * Close the application
   */
  @FXML
  private void closeApplication() {
    Main.exitApplication((Stage) unitName.getScene().getWindow());
  }

  /**
   * Changing view to the "about" view
   * @throws IOException if no FXML is found
   */
  @FXML
  private void aboutButtonPressed()throws IOException{
    Stage stage =(Stage)unitName.getScene().getWindow();
    Parent root = ViewLoader.getFXML("about-page").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Remove all units in the unit list
   */
  @FXML
  private void removeAllUnits() {
    unitList.clear();
    unitCounter.setText("0");
  }
}
