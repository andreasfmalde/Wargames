package no.ntnu.idatg2001.wargames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.army.Army;

public class GameManager {

  private static volatile GameManager instance;

  private List<Army> armies;


  private GameManager(){
    armies = new ArrayList<>();
  }

  public static GameManager getInstance(){
    if (instance == null){
      synchronized (GameManager.class){
        instance = new GameManager();
      }
    }
    return instance;
  }

  public void changeArmy(Army army, Army previousArmy){
    if(armies.contains(army)){
      throw new IllegalArgumentException("There can not be two identical armies in a battle.");
    }
    if(previousArmy != null){
      armies.remove(previousArmy);
    }

    if(armies.size() > 1){
      throw new IllegalArgumentException("Can not add army to list. There can only be two armies in the battle list.");
    }

    armies.add(army);
  }

  public List<Army> getArmies(){
    return this.armies;
  }



}
