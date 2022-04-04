package no.ntnu.idatg2001.wargames.model.unit;

import java.util.Objects;

/**
 * An abstract class that represents a unit.
 * Provides basic functionality of a unit.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (23.02.2022)
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
    else if(attack < 0){
      throw new IllegalArgumentException("Attack value can not be less than 0");
    }
    else if(armor < 0){
      throw new IllegalArgumentException("Armor value can not be less than 0");
    }

    this.name = name;
    this.setHealth(health);
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
    if(opponent == null){
      throw new NullPointerException("Opponent unit object can not be null");
    }
    int newHealth = opponent.getHealth() - (this.attack + this.getAttackBonus())+
        (opponent.getArmor() + opponent.getResistBonus());
    opponent.setHealth(Math.max(newHealth, 0));

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


  /**
   * Equals method that will compare unit objects
   * based on their fields. Same field values and underclasses
   * result in same objects.
   * @param o Object to compare against
   * @return true / false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return health == unit.health && attack == unit.attack && armor == unit.armor &&
        Objects.equals(name, unit.name);
  }

  /**
   * @return hashcode based on the unit name, health,
   * attack value and armour value
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, health, attack, armor);
  }
}
