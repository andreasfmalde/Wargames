package no.ntnu.idatg2001.units;

public class CommanderUnit extends CavalryUnit{
  /**
   * Constructor of the unit class. Used to initialize a new
   * base object with basic unit functionality to extend from.
   *
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public CommanderUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }
}
