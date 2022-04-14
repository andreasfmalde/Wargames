package no.ntnu.idatg2001.wargames.model.unit.units;

import no.ntnu.idatg2001.wargames.model.unit.Unit;

/**
 * A class that describes a ranged unit.
 * Has the default unit functionality.
 * The unit has a strength to attack from long range.
 * The ability to resist an attack decreases over time until a limit is reached.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (08.02.2022)
 */
public class RangedUnit extends Unit {

  private int timesAttacked; // Variable to hold count of amount of times attacked

  /**
   * Constructor of the ranged unit class. Used to initialize a new
   * object based on the unit class with extra ranged unit functionality
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public RangedUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    timesAttacked = 0; // Default value
  }

  /**
   * Minimised constructor to initialize a ranged unit.
   * The default values for attack and armor is:
   * Attack: 15
   * Armor: 8
   * @param name name of the unit
   * @param health starting health of the unit
   */
  public RangedUnit(String name, int health) {
    super(name, health, 15, 8);
    timesAttacked = 0; // Default value
  }


  /**
   * The ranged units' strength is the ability to attack from
   * long range.
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    return 3 + getTerrainImpact()[0];
  }

  /**
   * The ability to resist an attack decreases over
   * time until a limit is reached. From there, the resistance
   * will stay the same.
    * @return resistance bonus
   */
  @Override
  public int getResistBonus() {
    if(timesAttacked == 0){
      timesAttacked ++;
      return 6 + getTerrainImpact()[1];
    }else if (timesAttacked == 1){
      timesAttacked ++;
      return 4 + getTerrainImpact()[1];
    }
    return 2 + getTerrainImpact()[1];
  }

  @Override
  public int[] getTerrainImpact(){
    if(this.getTerrain() == null){
      return new int[]{0,0};
    }
    switch(this.getTerrain()){
      case HILL:
        return new int[]{2,0};
      case FOREST:
        return new int[]{0,-1};
      default:
        return new int[]{0,0};
    }
  }

}
