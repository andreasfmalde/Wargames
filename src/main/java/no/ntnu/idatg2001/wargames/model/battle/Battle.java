package no.ntnu.idatg2001.wargames.model.battle;

import java.util.Random;
import no.ntnu.idatg2001.wargames.model.Terrain;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;

/**
 * Class to represent a battle simulation
 * between two armies.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class Battle {

  private final Army armyOne;
  private final Army armyTwo;
  private final Random random;
  private final Terrain terrain;

  /**
   * Constructor to initialize a new battle class
   * taking in two armies as parameter
   * @param armyOne First army
   * @param armyTwo Second army
   * @param terrain Terrain of where the battle takes place
   */
  public Battle(Army armyOne, Army armyTwo,Terrain terrain){
    if (armyOne == null || armyTwo == null){
      throw new IllegalArgumentException("Armies can not be null");
    }
    if(terrain == null){
      throw new IllegalArgumentException("Terrain can not be null.");
    }
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    this.terrain = terrain;
    random = new Random();
  }

  /**
   * Main method of the class. Used to run
   * the battle simulation until one of the
   * armies has no units left
   * @return the winner of the battle (army with units left)
   */
  public Army simulate(){
    // Set the terrain of the battle in both armies.
    armyOne.setTerrain(this.terrain);
    armyTwo.setTerrain(this.terrain);
    // Checking to make sure both armies have units left
    while(armyOne.hasUnits() && armyTwo.hasUnits()){
      // Randomly picking which army will attack the other
      if(random.nextInt(2) == 0){
        Unit defendingUnit = armyTwo.getRandom();
        // A random unit from army one attack a random unit from army two
        armyOne.getRandom().attack(defendingUnit);
        // If the unit getting attack has no more health, it will be removed
        if (defendingUnit.getHealth() == 0){
          armyTwo.remove(defendingUnit);
        }
      }else{
        Unit defendingUnit = armyOne.getRandom();
        // A random unit from army two attacks a random unit from army one
        armyTwo.getRandom().attack(defendingUnit);
        // If the unit getting attack has no more health, it will be removed
        if (defendingUnit.getHealth() == 0){
          armyOne.remove(defendingUnit);
        }
      }
    }
    // Single line if/else statement. Return the army that still has units left
    return armyOne.hasUnits() ? armyOne : armyTwo;
  }

  /**
   * @return a more visual representation of the battle class
   */
  @Override
  public String toString(){
    return "BATTLE: [ "+armyOne.getName() + " vs "+armyTwo.getName()+" ]";
  }
}
