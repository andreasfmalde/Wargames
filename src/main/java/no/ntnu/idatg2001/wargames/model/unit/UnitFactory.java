package no.ntnu.idatg2001.wargames.model.unit;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;

/**
 * Unit factory class responsible to create
 * and instantiate different unit instance objects
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class UnitFactory {

  /**
   * Private constructor to make sure there are
   * no possibility to instantiate an object of
   * the factory class
   */
  private UnitFactory(){}

  /**
   * Create a unit instance of a given type
   * specified in the parameters.
   * @param type Type of unit to create
   * @param name Name of the unit
   * @param health Health of the unit
   * @return Unit object
   */
  public static Unit createUnit(String type,String name,int health){
    switch (type){
      case "InfantryUnit":
        return new InfantryUnit(name,health);
      case "RangedUnit":
        return new RangedUnit(name,health);
      case "CavalryUnit":
        return new CavalryUnit(name,health);
      case "CommanderUnit":
        return new CommanderUnit(name,health);
      default:
        throw new IllegalArgumentException("Illegal unit type in parameter...");
    }
  }


  /**
   * Create a collection of units with the same
   * name, health and type. The first parameter specifies how many
   * units to make. The max limit is 200 units.
   * @param amount Amount of units to create
   * @param type Type of unit
   * @param name Name of unit
   * @param health Health of unit
   * @return A list collection of units of the same type, name and health
   */
  public static List<Unit> createMultipleUnits(int amount,String type,String name,int health){
    if(amount > 200){
      throw new IllegalArgumentException("Your amount of "+amount+" units are above the maximum limit of 200 units to create");
    }
    List<Unit> units = new ArrayList<>();
    for(int i = 0; i < amount; i++){
      units.add(createUnit(type,name,health));
    }
    return units;
  }

}
