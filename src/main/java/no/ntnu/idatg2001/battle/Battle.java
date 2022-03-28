package no.ntnu.idatg2001.battle;

import java.util.Random;
import no.ntnu.idatg2001.army.Army;
import no.ntnu.idatg2001.unit.Unit;

/**
 * Class to represent a battle simulation
 * between two armies.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (23.02.22)
 */
public class Battle {

  private final Army armyOne;
  private final Army armyTwo;
  private final Random random;

  /**
   * Constructor to initialize a new battle class
   * taking in two armies as parameter
   * @param armyOne First army
   * @param armyTwo Second army
   */
  public Battle(Army armyOne, Army armyTwo){
    if (armyOne == null || armyTwo == null){
      throw new IllegalArgumentException("Armies can not be null");
    }
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    random = new Random();
  }

  /**
   * Main method of the class. Used to run
   * the battle simulation until one of the
   * armies has no units left
   * @return the winner of the battle (army with units left)
   */
  public Army simulate(){
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
