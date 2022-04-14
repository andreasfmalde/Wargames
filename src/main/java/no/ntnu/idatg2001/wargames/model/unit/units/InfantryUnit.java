package no.ntnu.idatg2001.wargames.model.unit.units;

import no.ntnu.idatg2001.wargames.model.battle.Terrain;
import no.ntnu.idatg2001.wargames.model.unit.Unit;

/**
 * A class that describes an infantry unit.
 * Has the default unit functionality.
 * The unit has a strength in close combat, but
 * also low on resistance if attacked.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (08.02.2022)
 */
public class InfantryUnit extends Unit {

  /**
   * Constructor of the infantry unit class. Used to initialize a new
   * object based on the unit class with extra infantry functionality.
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public InfantryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Minimised constructor to initialize an infantry unit.
   * The default values for attack and armor is:
   * Attack: 15
   * Armor: 10
   * @param name name of the unit
   * @param health starting health of the unit
   */
  public InfantryUnit(String name,int health){
    super(name,health,15,10);
  }

  /**
   * The unit has a strength in close combat, which
   * will be returned by this method
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    return 2 + getTerrainImpact()[0];
  }

  /**
   * The unit has a weak resistance bonus, which
   * will be returned by this method
   * @return resistance bonus
   */
  @Override
  public int getResistBonus() {
    return 1 + getTerrainImpact()[1];
  }

  @Override
  public int[] getTerrainImpact(){
    if(this.getTerrain() == null){
      return new int[]{0,0};
    }
    switch (this.getTerrain()){
      case FOREST:
        return new int[]{2,2};
      default:
        return new int[]{0,0};
    }

  }
}
