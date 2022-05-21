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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
  private boolean simulationRun; // Keeping track of if the simulation has run, but is not finished
  private boolean firstRun; // Used to make sure copies of armies are only made at the start
  private int counter;

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

  @FXML
  private LineChart<String,Number> lineChart;

  private XYChart.Series<String,Number> armyOneChart;
  private XYChart.Series<String,Number> armyTwoChart;


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
    firstRun = false;
    counter = 0;

    armyOneChart = new XYChart.Series<>();
    armyTwoChart = new XYChart.Series<>();

    lineChart.setVerticalGridLinesVisible(false);
    lineChart.setHorizontalGridLinesVisible(false);
    lineChart.setCreateSymbols(false);
    ((NumberAxis)lineChart.getYAxis()).setLowerBound(0);
    lineChart.getYAxis().setAutoRanging(false);
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
        lineChart.getData().addAll(armyOneChart,armyTwoChart);
        // Get the simulation speed value from the spinner
        simulationSpeed = simulationSpinner.getValue();
        ((NumberAxis)lineChart.getYAxis()).setUpperBound(getMaxUnitSize()+2.0);
        armyOneChart.setName(manager.getArmyOneName());
        armyTwoChart.setName(manager.getArmyTwoName());
        // Get the battle with the two current armies
        if(!firstRun){
          battle = manager.getBattle(terrain);
        }
        firstRun = true;
        // Start a simulation loop, stepping through each attack
        timeline = new Timeline(new KeyFrame(Duration.millis(simulationSpeed),this::doStep));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        // The simulation has run
        simulationRun = true;
      }catch (IllegalStateException | IllegalArgumentException e){
        new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
      }
    }
  }

  private int getMaxUnitSize(){
    return Math.max(manager.getArmyOneUnitSize(),manager.getArmyTwoUnitSize());
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
    updateGraph();
    // Stop the timeline loop if on of the armies have won
    if(battle.isThereAWinner()){
      timeline.stop();
      timeline = null;
    }

    counter ++;
  }

  private void updateGraph(){
    armyOneChart.getData().add(new XYChart.Data<>(String.valueOf(counter), manager.getArmyOneUnitSize()));
    armyTwoChart.getData().add(new XYChart.Data<>(String.valueOf(counter), manager.getArmyTwoUnitSize()));

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

  /**
   * Choose terrain for the simulation. Hill, Forest or Plains
   * @param event used to figure out which button is pressed
   */
  @FXML
  private void terrainButtonPressed(ActionEvent event) {
    // Find which button is pressed
    Button selectedButton = (Button) event.getSource();
    // Update the terrain based on the button pressed
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
    // Reset all buttons to look unselected
    resetTerrainButtonStyle();
    // Change background color of the selected button
    selectedButton.setStyle("-fx-background-color: rgba(68,58,25,0.8)");

  }

  /**
   * Reset the style on all terrain button. So none of them
   * look selected
   */
  private void resetTerrainButtonStyle(){
    for(Button button : new Button[]{hillButton,forestButton,plainsButton}){
      button.setStyle("-fx-background-color: rgba(68,58,25,0.4)");
    }
  }

  private void resetGraph(){
    lineChart.getData().clear();
    armyOneChart.getData().clear();
    armyTwoChart.getData().clear();

  }

  /**
   * This button will stop the simulation if it hasn't finished yet.
   * @param event N/A
   */
  @FXML
  private void stopSimulation(ActionEvent event) {
    if (timeline != null){
      timeline.stop();
      simulationRun = false;
    }
  }

  /**
   * Reset the armies to their original states. The whole simulation
   * will be reset.
   */
  @FXML
  private void resetButtonPressed(){
    manager.resetBattle();
    terrain = null;
    resetTerrainButtonStyle();
    messageList.clear();
    simulationRun = false;
    firstRun = false;
    resetGraph();
  }
}
