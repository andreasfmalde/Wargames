package no.ntnu.idatg2001.wargames.model.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.Test;

class UnitFactoryTest {

  @Test
  void createUnitNormalOperationTest() {

    Unit infantry = UnitFactory.createUnit("InfantryUnit","Infantry",100);
    assertTrue(infantry instanceof InfantryUnit);
    assertEquals("Infantry",infantry.getName());
    assertEquals(100,infantry.getHealth());

    Unit ranged = UnitFactory.createUnit("RangedUnit","Ranged",100);
    assertTrue(ranged instanceof RangedUnit);
    assertEquals("Ranged",ranged.getName());
    assertEquals(100,ranged.getHealth());

    Unit cavalry = UnitFactory.createUnit("CavalryUnit","Cavalry",100);
    assertTrue(cavalry instanceof CavalryUnit);
    assertEquals("Cavalry",cavalry.getName());
    assertEquals(100,cavalry.getHealth());

    Unit commander = UnitFactory.createUnit("CommanderUnit","Commander",100);
    assertTrue(commander instanceof CommanderUnit);
    assertEquals("Commander",commander.getName());
    assertEquals(100,commander.getHealth());


  }

  @Test
  void createUnitExceptionHandlingTest(){
    try{
      Unit failUnit = UnitFactory.createUnit("NotAType","Name",200);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Illegal unit type in parameter...",e.getMessage());
    }

    try{
      Unit anotherFailUnit = UnitFactory.createUnit("InfantryUnit","Name",-200);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Health can not be less than 0",e.getMessage());
    }
  }

  @Test
  void createMultipleUnits() {
    List<Unit> infantryUnits = UnitFactory.createMultipleUnits(5,"InfantryUnit","Infantry",100);
    assertEquals(5,infantryUnits.size());
    infantryUnits.forEach(unit -> {if (!(unit instanceof InfantryUnit))fail();});

    List<Unit> cavalryUnits = UnitFactory.createMultipleUnits(10,"CavalryUnit","Cavalry",100);
    assertEquals(10,cavalryUnits.size());
    cavalryUnits.forEach(unit -> {if(!(unit instanceof CavalryUnit))fail();});
    
  }

  @Test
  void createMultipleUnitsExceptionHandlingTest(){
    try{
      List<Unit> rangedUnits = UnitFactory.createMultipleUnits(2001,"RangedUnit","Ranged",100);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Your amount of 2001 units are above the maximum limit of 2000 units to create",e.getMessage());
    }

    try{
      List<Unit> commanderUnits = UnitFactory.createMultipleUnits(-200,"CommanderUnit","Ranged",100);
      assertEquals(0,commanderUnits.size());
    }catch (IllegalArgumentException e){
      fail();
    }
  }

}