package no.ntnu.idatg2001.wargames.model.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.Test;

/**
 * Class used to test the unit factory and
 * all its functionality
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
class UnitFactoryTest {

  /**
   * Testing to make sure the createUnit method works
   * as it should under normal operations.
   */
  @Test
  void createUnitNormalOperationTest() {
    // Infantry unit
    Unit infantry = UnitFactory.createUnit("InfantryUnit","Infantry",100);
    assertTrue(infantry instanceof InfantryUnit);
    assertEquals("Infantry",infantry.getName());
    assertEquals(100,infantry.getHealth());
    // Ranged unit
    Unit ranged = UnitFactory.createUnit("RangedUnit","Ranged",100);
    assertTrue(ranged instanceof RangedUnit);
    assertEquals("Ranged",ranged.getName());
    assertEquals(100,ranged.getHealth());
    // Cavalry unit
    Unit cavalry = UnitFactory.createUnit("CavalryUnit","Cavalry",100);
    assertTrue(cavalry instanceof CavalryUnit);
    assertEquals("Cavalry",cavalry.getName());
    assertEquals(100,cavalry.getHealth());
    // Commander unit
    Unit commander = UnitFactory.createUnit("CommanderUnit","Commander",100);
    assertTrue(commander instanceof CommanderUnit);
    assertEquals("Commander",commander.getName());
    assertEquals(100,commander.getHealth());


  }

  /**
   * Testing to make sure the createUnit method handles
   * exceptions as it should.
   */
  @Test
  void createUnitExceptionHandlingTest(){
    //Expecting exception when type parameter is invalid
    try{
      Unit failUnit = UnitFactory.createUnit("NotAType","Name",200);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Illegal unit type in parameter...",e.getMessage());
    }

    // Expecting exception when name or health in unit is invalid
    try{
      Unit anotherFailUnit = UnitFactory.createUnit("InfantryUnit","Name",-200);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Health can not be less than 0",e.getMessage());
    }
  }

  /**
   * Testing to make sure the createMultipleUnits method works
   * as it should under normal operations.
   */
  @Test
  void createMultipleUnits() {
    // Creating 5 infantry units
    List<Unit> infantryUnits = UnitFactory.createMultipleUnits(5,"InfantryUnit","Infantry",100);
    assertEquals(5,infantryUnits.size());
    // Testing that all units in the list are instances of CavalryUnit
    infantryUnits.forEach(unit -> {if (!(unit instanceof InfantryUnit))fail();});
    // Creating 10 cavalry units
    List<Unit> cavalryUnits = UnitFactory.createMultipleUnits(10,"CavalryUnit","Cavalry",100);
    assertEquals(10,cavalryUnits.size());
    // Testing that all units in the list are instances of CavalryUnit
    cavalryUnits.forEach(unit -> {if(!(unit instanceof CavalryUnit))fail();});

  }

  /**
   * Testing to make sure the createMultipleUnits method handles
   * exceptions as it should.
   */
  @Test
  void createMultipleUnitsExceptionHandlingTest(){
    // Maximum amount of units to create is 2000.
    try{
      List<Unit> rangedUnits = UnitFactory.createMultipleUnits(2001,"RangedUnit","Ranged",100);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Your amount of 2001 units are above the maximum limit of 2000 units to create",e.getMessage());
    }

    // If a negative amount of units is entered, 0 units will be returned.
    try{
      List<Unit> commanderUnits = UnitFactory.createMultipleUnits(-200,"CommanderUnit","Ranged",100);
      assertEquals(0,commanderUnits.size());
    }catch (IllegalArgumentException e){
      fail();
    }
  }

}