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
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.UnitFactory;
import no.ntnu.idatg2001.wargames.utility.FileHandler;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

public class CreateArmyController implements Initializable {

  @FXML private ComboBox typeComboBox;
  @FXML private ListView unitListView;
  @FXML private TextField unitAmount;
  @FXML private TextField unitHealth;
  @FXML private TextField unitName;
  @FXML private TextField armyName;

  private ObservableList<Unit> unitList;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    String[] values = {"InfantryUnit","RangedUnit","CavalryUnit","CommanderUnit"};
    ObservableList<String> valueList = FXCollections.observableArrayList(values);
    typeComboBox.setItems(valueList);
    unitList = FXCollections.observableArrayList();
    unitListView.setItems(unitList);
  }

  public void createArmy(ActionEvent actionEvent){
    Army army = new Army(armyName.getText());
    army.addAll(unitList);
    try{
      FileChooser chooser = new FileChooser();
      chooser.setInitialFileName(armyName.getText());
      chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));
      chooser.setInitialDirectory( new File(Paths.get(".").toAbsolutePath().normalize().toString()));
      File file = chooser.showSaveDialog(armyName.getScene().getWindow());
      if(file != null){
        FileHandler.writeArmyToFile(army,file);
      }
    }catch (IOException e){
      new Alert(Alert.AlertType.WARNING,e.getMessage()).showAndWait();
    }


  }

  public void addUnit(ActionEvent actionEvent){
    String unitType = String.valueOf(typeComboBox.getValue());
    String name = unitName.getText();
    int health = Integer.parseInt(unitHealth.getText());
    int amount = Integer.parseInt(unitAmount.getText());
    unitList.addAll(UnitFactory.createMultipleUnits(amount,unitType,name,health));
  }

  public void simulateBattlePressed(ActionEvent actionEvent) throws IOException {
    Stage stage =(Stage)unitListView.getScene().getWindow();
    Parent root = ViewLoader.getFXML("battle-window").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

}
