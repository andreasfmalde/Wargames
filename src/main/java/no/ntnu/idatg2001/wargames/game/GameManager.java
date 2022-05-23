package no.ntnu.idatg2001.wargames.game;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.army.Army;
/**
 * Game manager class working as a facade in the application.
 * Responsible to store and distribute information around the
 * different model and controller classes in the application.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class GameManager {

  private static volatile GameManager instance; // Not fully threadsafe yet, but works for us since we don't use threads in the program

  private final List<Army> armies;
  private final List<GameObserver> observers;


  /**
   * Private constructor to initialize a game manager object.
   */
  private GameManager(){
    armies = new ArrayList<>();
    observers = new ArrayList<>();
  }

  /**
   * Method to get the instance of the game manager. There will
   * only be one instance of the game manager object. This method is the only
   * way to get it.
   * @return Game manager instance
   */
  public static GameManager getInstance(){
    if (instance == null){
      synchronized (GameManager.class){
        instance = new GameManager();
      }
    }
    return instance;
  }

  /**
   * Add and change the armies in the game manager. There can only be two armies.
   * Include both the army to add and the army to replace as parameters.
   * Use null as previousArmy if there are no armies to replace.
   * @param army Army to add to the list
   * @param previousArmy Army in the list that shall be replaced. If no armies in the list, null is used
   */
  public void changeArmy(Army army, Army previousArmy){
    // The list can not contain two identical armies.
    if(armies.contains(army)){
      throw new IllegalArgumentException("There can not be two identical armies in a battle.");
    }
    //  Removing previousArmy if it exists
    if(previousArmy != null){
      armies.remove(previousArmy);
    }

    // Making sure there is only one unit at max in the list before adding another one.
    if(armies.size() > 1){
      throw new IllegalArgumentException("Can not add army to list. There can only be two armies in the battle list.");
    }

    // Adding army to the list
    armies.add(army);
  }

  /**
   * Add observer to the observer list
   * @param observer Observer to subscribe to the list
   */
  public void addObserver(GameObserver observer){
    if(observer == null || observers.contains(observer)){
      throw new IllegalArgumentException("Observer can not be null or already be in the list");
    }
    observers.add(observer);
  }


  /**
   * Get a list of the two battling armies in the army list of the game manager
   * @return A list of two armies
   */
  public List<Army> getBattlingArmies(){
    if(armies.size() != 2){
      throw new IllegalStateException("There has to be two armies in a battle");
    }
    // Send a message to all observers to make copies of armies, before the battle starts
    for(GameObserver observer : observers){
      observer.updateCopies();
    }
    return armies;
  }


  /**
   * Distribute information to all observers to reset to
   * original state
   */
  public void resetBattle(){
    if(armies.size() == 2) {
      for(GameObserver observer : observers){
        observer.resetState();
      }
    }
  }

  /**
   * Method used to update the observers in the
   * observer list.
   * @param battleInfo Battle information from the simulation step
   */
  public void updateObservers(String battleInfo){
    for(GameObserver observer : observers){
     observer.updateState(battleInfo);
    }

  }

  /**
   * Get the name of the army with units left. If both have units left or
   * none have units left, "" will be returned
   * @return Name of the winning army
   */
  public String getUnitWithArmiesLeft(){
    if (armies.get(0).hasUnits() && !armies.get(1).hasUnits()){
      return getArmyOneName();
    }else if (armies.get(1).hasUnits() && !armies.get(0).hasUnits()){
      return getArmyTwoName();
    }
    return "";
  }

  /**
   *
   * @return the amount of units in army one
   */
  public int getArmyOneUnitSize(){
    return armies.get(0).getAllUnits().size();
  }


  /**
   *
   * @return the amount of units in army two
   */
  public int getArmyTwoUnitSize(){
    return armies.get(1).getAllUnits().size();
  }

  /**
   *
   * @return army one name
   */
  public String getArmyOneName(){
    return armies.get(0).getName();
  }

  /**
   *
   * @return army two name
   */
  public String getArmyTwoName(){
    return armies.get(1).getName();
  }


}
