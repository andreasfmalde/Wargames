package no.ntnu.idatg2001.army;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import no.ntnu.idatg2001.unit.Unit;

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

  public Army(String name, List<Unit> units) {
    this.setName(name);
    if(units == null){
      throw new IllegalArgumentException("The provided list can not be null");
    }
    this.units = units;
    random = new Random();
  }

  private void setName(String name){
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name can not be empty or blank");
    }
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public boolean add(Unit unit){
    if(unit == null){
      return false;
    }
    units.add(unit);
    return true;
  }

  public boolean addAll(List<Unit> units){
    if(units == null || units.isEmpty()){
      return false;
    }
    this.units.addAll(units);
    return true;
  }

  public boolean remove(Unit unit){
    if(unit != null && units.contains(unit)){
      units.remove(unit);
      return true;
    }
    return false;
  }

  public boolean hasUnits(){
    return !units.isEmpty();
  }

  public List<Unit> getAllUnits(){
    return this.units;
  }

  public Unit getRandom(){
    return units.get(random.nextInt(units.size()));
  }

  @Override
  public String toString() {
    return "[ Army: "+name+" | Units: "+units.size()+" ]";
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
