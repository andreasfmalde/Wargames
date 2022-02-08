package no.ntnu.idatg2001.units;

public class CavalryUnit extends Unit{
  /**
   * Constructor of the unit class. Used to initialize a new
   * base object with basic unit functionality to extend from.
   *
   * @param name   name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor  starting armor value of the unit
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }



  @Override
  public int getAttackBonus() {
    return 0;
  }

  @Override
  public int getResistBonus() {
    return 0;
  }
}
