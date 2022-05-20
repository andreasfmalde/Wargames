package no.ntnu.idatg2001.wargames.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import no.ntnu.idatg2001.wargames.model.GameManager;
import no.ntnu.idatg2001.wargames.model.GameObserver;
import no.ntnu.idatg2001.wargames.model.Terrain;
import no.ntnu.idatg2001.wargames.model.battle.Battle;
import no.ntnu.idatg2001.wargames.view.ViewLoader;

/**
 * Battle controller class responsible for manipulating
 * the battle view in the GUI. Connecting the view and the
 * model classes.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class BattleController implements Initializable, GameObserver {


  private GameManager manager;
  private Battle battle;
  private Timeline timeline;
  private ObservableList<String> messageList;
  private Terrain terrain;
  private int simulationSpeed;
  private boolean simulationRun;

  // ----- JavaFX variables -----
  @FXML
  private VBox leftArmy;
  @FXML
  private VBox rightArmy;
  @FXML
  private ListView<String> battleListView;
  @FXML
  private Button plainsButton;
  @FXML
  private Button hillButton;
  @FXML
  private Button forestButton;
  @FXML
  private Spinner<Integer> simulationSpinner;


  /**
   * Initialization method of the battle controller class. Will be called in the start
   * to set up everything.
   * @param url N/A
   * @param resourceBundle N/A
   */
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
    // Adding battle controller as a game observer
    manager.addObserver(this);
    messageList = FXCollections.observableArrayList();
    battleListView.setItems(messageList);
    SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5,120,80,5);
    simulationSpinner.setValueFactory(spinnerFactory);
    simulationSpeed = simulationSpinner.getValue();
    simulationRun = false;
  }

  /**
   * Changing the view from the battle view to the
   * create army view
   * @param actionEvent N/A
   * @throws IOException If no view is found
   */
  @FXML
  private void createArmyButtonPressed(ActionEvent actionEvent) throws IOException {
    Stage stage =(Stage)leftArmy.getScene().getWindow();
    Parent root = ViewLoader.getFXML("create-army").load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Starting a simulation of a battle between two armies.
   * @param event N/A
   */
  @FXML
  private void simulateBattle(ActionEvent event) {
    if(!simulationRun){
      try{
        simulationSpeed = simulationSpinner.getValue();
        battle = manager.getBattle(terrain);
        timeline = new Timeline(new KeyFrame(Duration.millis(simulationSpeed),this::doStep));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        simulationRun = true;
      }catch (IllegalStateException | IllegalArgumentException e){
        new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
      }
    }
  }

  /**
   * Method responsible to call battle class simulate step method and
   * sending update to the observer classes
   * @param event N/A
   */
  private void doStep(ActionEvent event){
    String battleMessage = battle.simulateStep();
    messageList.add(battleMessage);
    battleListView.scrollTo(messageList.size()-1);
    // Send update of the battle simulation step
    manager.updateObservers(battleMessage);
    // Stop the timeline loop if on of the armies have won
    if(battle.isThereAWinner()){
      timeline.stop();
      timeline = null;
    }

  }

  /**
   * Update the battle controller based on new battle information.
   * @param input Input string from the battle simulation
   */
  @Override
  public void updateState(String input) {
    //TODO: Input here!
  }

  @Override
  public void updateCopies() {

  }

  @Override
  public void resetState() {

  }

  @FXML
  private void terrainButtonPressed(ActionEvent event) {
    Button selectedButton = (Button) event.getSource();
    switch (selectedButton.getId()){
      case "hillButton":
        terrain = Terrain.HILL;
        break;
      case "forestButton":
        terrain = Terrain.FOREST;
        break;
      case "plainsButton":
        terrain = Terrain.PLAINS;
        break;
      default:
        terrain = null;
        break;
    }
    resetTerrainButtonStyle();
    selectedButton.setStyle("-fx-background-color: rgba(68,58,25,0.8)");

  }

  private void resetTerrainButtonStyle(){
    for(Button button : new Button[]{hillButton,forestButton,plainsButton}){
      button.setStyle("-fx-background-color: rgba(68,58,25,0.4)");
    }
  }

  @FXML
  private void stopSimulation(ActionEvent event) {
    if (timeline != null){
      timeline.stop();
      simulationRun = false;
    }
  }

  @FXML
  private void resetButtonPressed(){
    manager.resetBattle();
    terrain = null;
    resetTerrainButtonStyle();
    messageList.clear();
    simulationRun = false;
  }
}
