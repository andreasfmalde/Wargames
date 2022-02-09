package no.ntnu.idatg2001.unit;

/**
 * An abstract class that represents a unit.
 * Provides basic functionality of a unit.
 *
 * @author Andreas Follevaag Malde
 * @version 0.0.1 (08.02.2022)
 */
public abstract class Unit {

  private String name;
  private int health;
  private int attack;
  private int armor;

  /**
   * Constructor of the unit class. Used to initialize a new
   * base object with basic unit functionality to extend from.
   * @param name name of the unit
   * @param health starting health of the unit
   * @param attack starting attack value of the unit
   * @param armor starting armor value of the unit
   */
  protected Unit(String name, int health, int attack, int armor) {
    if(name.isBlank() || name.isEmpty()){
      throw new IllegalArgumentException("Name can not be blank!");
    }
    this.name = name;
    this.setHealth(health);
    //TODO: Update constructor to check for valid attack and armor values
    this.attack = attack;
    this.armor = armor;
  }

  // ----- Getter methods -----

  /**
   *
   * @return the name of the unit
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return the health of the unit
   */
  public int getHealth() {
    return health;
  }

  /**
   *
   * @return the attack value of the unit
   */
  public int getAttack() {
    return attack;
  }

  /**
   *
   * @return the armor value of the unit
   */
  public int getArmor() {
    return armor;
  }

  // ----- Setter method -----

  /**
   * Set the health of the unit. The health will decrease
   * under attack
   * @param health updated health of the unit
   */
  public void setHealth(int health) {
    if(health < 0){
      throw new IllegalArgumentException("Health can not be less than 0");
    }
    this.health = health;
  }

  // ----- Class methods -----

  /**
   * Attack another unit. The amount of attack power
   * and opponent armor will decide the opponents' health.
   * @param opponent the unit that is attacked
   */
  public void attack(Unit opponent){
    int newHealth = opponent.getHealth() - (this.attack + this.getAttackBonus())+
        (opponent.getArmor() + opponent.getResistBonus());
    opponent.setHealth(newHealth);
  }

  /**
   * Provides a better visual representation of the unit object
   * @return an informational string of the object
   */
  @Override
  public String toString(){
    return "[ "+name+" | Current Health: "+health+" | Attack: "+attack+" | Armor: "+armor+" ]";
  }


  // ----- Abstract methods -----

  /**
   * A bonus under attack. Will be added when the unit attacks
   * another unit
   * @return attack bonus
   */
  public abstract int getAttackBonus();

  /**
   * Bonus to resist or decrease damage under an attack.
   * Will be added when the unit is attacked.
   * @return Resistance bonus.
   */
  public abstract int getResistBonus();

}
