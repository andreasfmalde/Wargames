package no.ntnu.idatg2001.wargames.model.unit.units;

import no.ntnu.idatg2001.wargames.model.unit.Unit;

/**
 * A class describing a cavalry unit.
 * Has the default unit functionality.
 * The cavalry unit has a strong first attack, but
 * the following attacks are not as powerful. The unit
 * has a small resistance advantage in melee.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (09.02.2022)
 */
public class CavalryUnit extends Unit {

  private boolean firstAttack; // True when the unit has attacked at least one time

  /**
   * Constructor of the cavalry unit class. Used to initialize a new
   * object based on the unit class with extra cavalry unit functionality.
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    firstAttack = false; // Default value to false
  }

  /**
   * Minimised constructor to initialize a cavalry unit.
   * The default values for attack and armor is:
   * Attack: 20
   * Armor: 12
   * @param name name of the unit
   * @param health starting health of the unit
   */
  public CavalryUnit(String name, int health) {
    super(name, health, 20, 12);
    firstAttack = false; // Default value to false
  }


  /**
   * The cavalry units' strength is in the first attack.
   * The following attacks is not as powerful.
   * @return attack bonus of the cavalry unit
   */
  @Override
  public int getAttackBonus() {
    if(!firstAttack){
      firstAttack = true;
      return 6 + getTerrainImpact()[0];
    }
    return 2 + getTerrainImpact()[0];
  }

  /**
   * Not very good at resistance. Has a small
   * advantage in melee.
   * @return resistance bonus of the cavalry unit
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
      case PLAINS:
        return new int[]{2,0};
      case FOREST:
        return new int[]{0,-1};
      default:
        return new int[]{0,0};
    }
  }
}
