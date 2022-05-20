package no.ntnu.idatg2001.wargames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.battle.Battle;
/**
 * Game manager class working as a facade in the application.
 * Responsible to store and distribute information around the
 * different model and controller classes in the application.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class GameManager {

  private static volatile GameManager instance;

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
   * Delete observer from the observer list
   * @param observer Observer to delete
   */
  public void deleteObserver(GameObserver observer){
    observers.remove(observer);
  }

  /**
   * Get a battle of the two armies in the army list of the game manager
   * @param terrain Terrain where the battle will take place
   * @return A battle object
   */
  public Battle getBattle(Terrain terrain){
    if(armies.size() != 2){
      throw new IllegalStateException("There has to be two armies in a battle");
    }
    // Send a message to all observers to make copies of armies, before the battle starts
    for(GameObserver observer : observers){
      observer.updateCopies();
    }
    return new Battle(armies.get(0),armies.get(1),terrain);
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




}
