package no.ntnu.idatg2001.wargames.model.battle;

import java.util.List;
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
  /**
   * Constructor to initialize a new battle class
   * taking in two armies as parameter
   *
   * @param armyOne First army
   * @param armyTwo Second army
   * @param terrain Terrain of where the battle takes place
   */
  public Battle(Army armyOne, Army armyTwo, Terrain terrain) {
    if (armyOne == null || armyTwo == null) {
      throw new IllegalArgumentException("Armies can not be null");
    }
    if (terrain == null) {
      throw new IllegalArgumentException("Terrain can not be null.");
    }
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    // Set the terrain of the battle in both armies.
    armyOne.setTerrain(terrain);
    armyTwo.setTerrain(terrain);
    random = new Random();
  }

  /**
   * Constructor to initialize a new battle class
   * taking in a list of armies and terrain as parameter
   *
   * @param armies A list of 2 army objects. It has to contain 2 objects.
   * @param terrain Terrain of where the battle takes place
   */
  public Battle(List<Army> armies, Terrain terrain) {
    // Check to make sure the list parameter is not null
    if (armies == null){
      throw new IllegalArgumentException("Army list can not be null");
    }
    // Making sure there are two armies in the list
    if(armies.size() != 2){
      throw new IllegalArgumentException("There has to be two units in the list");
    }
    // Making sure none of the armies are null
    if (armies.get(0) == null || armies.get(1) == null) {
      throw new IllegalArgumentException("Armies can not be null");
    }

    if (terrain == null) {
      throw new IllegalArgumentException("Terrain can not be null.");
    }
    this.armyOne = armies.get(0);
    this.armyTwo = armies.get(1);
    // Set the terrain of the battle in both armies.
    armyOne.setTerrain(terrain);
    armyTwo.setTerrain(terrain);
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
      // Calling the simulate step method for the attack
      simulateStep();
    }
    // Single line if/else statement. Return the army that still has units left
    return armyOne.hasUnits() ? armyOne : armyTwo;
  }


  /**
   * Method responsible for the attack between to units. A
   * random unit from one army will attack a random unit from the other
   * army. If a unit dies, it will be removed from the army
   * @return String representation of which unit was the attacker, and the one attacked
   */
  public String simulateStep() {
    Unit defendingUnit;
    Unit attackingUnit;
    // If a unit dies in the attack, unitDied will be true.
    boolean unitDied = false;
    // Randomly picking which army will attack the other
    if (random.nextInt(2) == 0) {
      // A random unit from army one attack a random unit from army two
      defendingUnit = armyTwo.getRandom();
      attackingUnit = armyOne.getRandom();
      // Attacking unit attacks defending unit
      attackingUnit.attack(defendingUnit);

      // If the unit getting attack has no more health, it will be removed
      if (defendingUnit.getHealth() == 0) {
        armyTwo.remove(defendingUnit);
        unitDied = true;
      }
    } else {
      // A random unit from army two attacks a random unit from army one
      defendingUnit = armyOne.getRandom();
      attackingUnit = armyTwo.getRandom();
      // Attacking unit attacks defending unit
      attackingUnit.attack(defendingUnit);

      // If the unit getting attack has no more health, it will be removed
      if (defendingUnit.getHealth() == 0) {
        armyOne.remove(defendingUnit);
        unitDied = true;
      }
    }
    // String representation of which unit was the attacker, and the one attacked.
    // And a one line if/else statement to check if the unit died.
    return attackingUnit.getName() + " attacked "+defendingUnit.getName()+"."+(unitDied?" "+defendingUnit.getName()+" died.":"");
  }

  /**
   * Check if there is a winner of the battle, used together with the simulateStep method
   * @return true if one of the armies have no units left
   */
  public boolean isThereAWinner(){
    return !armyOne.hasUnits() || !armyTwo.hasUnits();
  }

  /**
   * @return a more visual representation of the battle class
   */
  @Override
  public String toString(){
    return "BATTLE: [ "+armyOne.getName() + " vs "+armyTwo.getName()+" ]";
  }
}
