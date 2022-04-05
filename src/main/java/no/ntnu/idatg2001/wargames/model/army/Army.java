package no.ntnu.idatg2001.wargames.model.army;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;

/**
 * Class that represents an army containing different units.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (16.02.2022)
 */
public class Army {

  private String name;
  private List<Unit> units;
  private final Random random;


  /**
   * Constructor to initialize an empty army class
   * containing no units at the point of initialization.
   * @param name of the army
   */
  public Army(String name) {
    this.setName(name);
    this.units = new ArrayList<>();
    random = new Random();
  }

  /**
   * Constructor to initialize a new army object filled
   * with units provided during the initialization.
   * @param name Name of the army
   * @param units List of units to add at the start
   */
  public Army(String name, List<Unit> units) {
    this.setName(name);
    if(units == null){
      throw new IllegalArgumentException("The provided list can not be null");
    }
    this.units = units;
    random = new Random();
  }

  /**
   * Method to set the name of the army.
   * Private method only for use in the class.
   * @param name Name of the army
   */
  private void setName(String name){
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name can not be empty or blank");
    }
    this.name = name;
  }

  /**
   *
   * @return name of the army
   */
  public String getName(){
    return name;
  }

  /**
   * @return A list of all infantry units in the unit list
   */
  public List<Unit> getInfantryUnits(){
    return units.stream().filter(unit -> (unit instanceof InfantryUnit))
        .collect(Collectors.toList());
  }

  /**
   * @return A list of all ranged units in the unit list
   */
  public List<Unit> getRangedUnits(){
    return units.stream().filter(unit -> (unit instanceof RangedUnit))
        .collect(Collectors.toList());
  }

  /**
   * @return A list of all cavalry units in the unit list
   */
  public List<Unit> getCavalryUnits(){
    return units.stream()
        .filter(unit -> unit instanceof CavalryUnit)
        .filter(unit -> !(unit instanceof CommanderUnit))
        .collect(Collectors.toList());
  }

  /**
   * @return A list of all commander units in the unit list
   */
  public List<Unit> getCommanderUnits(){
    return units.stream().filter(unit -> (unit instanceof CommanderUnit))
        .collect(Collectors.toList());

  }



  /**
   * Add a new unit to the army.
   * @param unit Unit to add to the army
   */
  public void add(Unit unit){
    if(unit == null){
      throw new IllegalArgumentException("Unit object can not be null");
    }
    units.add(unit);
  }

  /**
   * Add several units to the army at once
   * @param units List of units to add to the army
   */
  public void addAll(List<Unit> units){
    if(units == null ){
      throw new IllegalArgumentException("Unit list can not be null");
    }
    this.units.addAll(units);
  }

  /**
   * Remove a unit from the army. I.e. if the unit
   * has lost all its health
   * @param unit Unit to remove from the army
   */
  public void remove(Unit unit){
    if(unit == null){
      throw new IllegalArgumentException("Unit object can not be null");
    }else if(!this.units.contains(unit)){
      throw new IllegalArgumentException("The unit is not in the list");
    }
    this.units.remove(unit);
  }

  /**
   * Method informing if there are any units in
   * the army or not
   * @return true if there are at least one unit in the army
   */
  public boolean hasUnits(){
    return !units.isEmpty();
  }

  /**
   * Get all the units currently in the army
   * @return List of all units in the army
   */
  public List<Unit> getAllUnits(){
    return this.units;
  }

  /**
   * Get a random unit from the army
   * @return a unit in the army
   */
  public Unit getRandom(){
    return units.get(random.nextInt(units.size()));
  }

  /**
   * Overriding the default toString method.
   * @return a better visual string representation of the army object
   */
  @Override
  public String toString() {
    return "[ Army: "+name+" | Units: "+units.size()+" ]";
  }

  /**
   * Overriding the default equals method.
   * Compare to army objects based on the name and unit list.
   * @param o Object to compare against
   * @return true if the objects are the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Army army = (Army) o;
    return Objects.equals(name, army.name) && Objects.equals(units, army.units);
  }

  /**
   * Overriding the default hashcode
   * @return a hashcode based on the name of the army and the unit list
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
