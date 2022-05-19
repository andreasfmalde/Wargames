package no.ntnu.idatg2001.wargames.model.unit;

import java.util.Objects;
import no.ntnu.idatg2001.wargames.model.Terrain;

/**
 * An abstract class that represents a unit.
 * Provides basic functionality of a unit.
 *
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public abstract class Unit {

  private final String name;
  private int health;
  private final int attack;
  private final int armor;
  private Terrain terrain;
  private final String type;

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
    this.terrain = null;
    this.type = this.getClass().getSimpleName().replace("Unit","");
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

  /**
   *
   * @return the terrain where unit fight
   */
  protected Terrain getTerrain(){
    return terrain;
  }

  /**
   *
   * @return the type of unit
   */
  public String getType(){
    return type;
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

  /**
   * Set the terrain. This will affect the attack and defence
   * bonus of each unit.
   * @param terrain Terrain enum
   */
  public void setTerrain(Terrain terrain){
    if (terrain == null){
      throw new IllegalArgumentException("Terrain can not be null");
    }
    this.terrain = terrain;
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
    // Try to set opponent health. If the new health is below 0,
    // 0 will be set.
    try{
      opponent.setHealth(newHealth);
    }catch (IllegalArgumentException e){
      opponent.setHealth(0);
    }

  }

  /**
   * Provides a better visual representation of the unit object
   * @return an informational string of the object
   */
  @Override
  public String toString(){
    return this.getClass().getSimpleName() + ", "+name+", "+health;
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
   * Different units have different attack and
   * defence bonuses based on the terrain.
   * @return Impact on the attack and defence bonus
   * from the terrain
   */
  public abstract int[] getTerrainImpact();


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
