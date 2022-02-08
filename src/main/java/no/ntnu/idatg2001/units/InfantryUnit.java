package no.ntnu.idatg2001.units;

/**
 * A class that describes an infantry unit.
 * Has the default unit functionality.
 * The unit has a strength in close combat, but
 * also low on resistance if attacked.
 * @author Andreas Follevaag Malde
 * @version 0.0.1 (08.02.2022)
 */
public class InfantryUnit extends Unit{

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
    return 2;
  }

  /**
   * The unit has a weak resistance bonus, which
   * will be returned by this method
   * @return resistance bonus
   */
  @Override
  public int getResistBonus() {
    return 1;
  }
}
