package no.ntnu.idatg.wargames.model.unit.units;

/**
 * The commander unit class is a more capable
 * version of the cavalry class which it extends from.
 * The commander unit has a strong first attack, but
 * the following attacks are not as powerful. The unit
 * has a small resistance advantage in melee.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (09.02.2022)
 */
public class CommanderUnit extends CavalryUnit{
  /**
   * Constructor of the commander unit class. Used to initialize a more
   * capable version of the cavalry class.
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public CommanderUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Minimised constructor to initialize a commander unit.
   * The default values for attack and armor is:
   * Attack: 25
   * Armor: 15
   * @param name name of the unit
   * @param health starting health of the unit
   */
  public CommanderUnit(String name, int health) {
    super(name, health, 25, 15);
  }
}
