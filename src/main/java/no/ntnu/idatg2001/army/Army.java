package no.ntnu.idatg2001.army;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import no.ntnu.idatg2001.unit.Unit;

public class Army {

  private String name;
  private List<Unit> units;
  private Random random;


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





}
