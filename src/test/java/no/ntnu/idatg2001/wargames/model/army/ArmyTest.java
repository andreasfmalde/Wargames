package no.ntnu.idatg2001.wargames.model.army;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the army class to make sure
 * the core functionality work as intended.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
class ArmyTest {

  private Army norwegianArmy;
  private Army swedishArmy;
  private List<Unit> unitList;

  /**
   * Initialization method that will be called
   * before each test method. The method will
   * initialize two armies and a unit list.
   */
  @BeforeEach
  void init(){
    // Initialize army classes used for testing
    norwegianArmy = new Army("Norwegian Army");
    swedishArmy = new Army("Swedish Army");
    unitList = new ArrayList<>();
    unitList.add(new InfantryUnit("Infantry",100));
    unitList.add(new RangedUnit("Ranged",120));
    unitList.add(new CavalryUnit("Cavalry",150));
    unitList.add(new CommanderUnit("Commander",180));

  }

  /**
   * Testing method to make sure the constructor
   * of the army test works as intended.
   * Also testing for the constructor to handle
   * wrong input.
   */
  @Test
  void constructorTest(){
    Army army = new Army("Test army",unitList);
    assertEquals("Test army",army.getName());
    // test army are expected to contain 4 units
    assertEquals(4,army.getAllUnits().size());
    // norwegian and swedish army are both expected to have 0 units
    assertEquals(0,norwegianArmy.getAllUnits().size());
    assertEquals(0,swedishArmy.getAllUnits().size());

    // IllegalArgumentException expected due to name being blank
    try {
      new Army("");
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Name can not be empty or blank",e.getMessage());
    }

    // IllegalArgumentException expected due to list parameter being null
    try{
      new Army("Fail army",null);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("The provided list can not be null",e.getMessage());
    }

  }

  /**
   * Method used for testing the ability of the
   * class to add units to the army. Both the ability
   * to add a single unit, but also the ability to add
   * a list of units the army.
   */
  @Test
  void addToArmy(){

    // norwegian army are expected to be empty
    assertFalse(norwegianArmy.hasUnits());
    norwegianArmy.add(new RangedUnit("Ranged",100));
    // norwegian army are not empty anymore
    assertTrue(norwegianArmy.hasUnits());
    // operation to add infantry unit to the army is expected to be successful
    try{
      norwegianArmy.add(new InfantryUnit("Infantry",150));
    }catch (IllegalArgumentException e){
      fail();
    }
    // there are 2 units in the army
    assertEquals(2,norwegianArmy.getAllUnits().size());

    // operation to add null to the army should not be successful
    try{
      norwegianArmy.add(null);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Unit object can not be null",e.getMessage());
    }
    // Adding units to swedish army
    try{
      swedishArmy.addAll(unitList);
    }catch (IllegalArgumentException e){
      fail();
    }

    // swedish army has 4 units
    assertEquals(4,swedishArmy.getAllUnits().size());

    // operation to add null as a list to the army should not be
    try{
      swedishArmy.addAll(null);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Unit list can not be null",e.getMessage());
    }

    List<Unit> lotsOfUnits = new ArrayList<>();
    for(int i = 0; i<196;i++){
      lotsOfUnits.add(new InfantryUnit("Infantry1",125));
    }

    // Adding units to swedish army so that it contains the maximum amount of units.
    swedishArmy.addAll(lotsOfUnits);
    assertEquals(200,swedishArmy.getAllUnits().size());

    // Expecting an exception when trying to add one more unit to the army
    Unit unitForTesting = new CommanderUnit("Commander1", 200);
    try {
      swedishArmy.add(unitForTesting);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Can not add unit. Maximum amount of 200 is reached",e.getMessage());
    }
    // Adding some extra units to the list beforehand
    unitForTesting = new InfantryUnit("The infantry",135);
    for(int i = 0; i< 10;i++){
      norwegianArmy.add(unitForTesting);
    }
    // Expecting an exception when trying to add a list of units that will surpass the max amount limit
    try{
      norwegianArmy.addAll(lotsOfUnits);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Can not add units. Maximum amount of 200 will be surpassed",e.getMessage());
    }
  }


  /**
   * Testing the remove method of the army class.
   * Making sure units are removed if they
   * are in the list, and skipped if they are not
   * in the list.
   */
  @Test
  void remove(){
    norwegianArmy.addAll(unitList);
    // norwegian army has 4 units
    assertEquals(4,norwegianArmy.getAllUnits().size());
    // operation to remove a unit already in the army, from the army is expected to be successful
    try{
      norwegianArmy.remove(norwegianArmy.getAllUnits().get(1));
    }catch (Exception e){
      fail();
    }
    // norwegian army now has 3 units
    assertEquals(3,norwegianArmy.getAllUnits().size());

    // should not be able to remove a unit not in the list
    Unit unitNotInList = new CommanderUnit("not a unit in the list",10);
    try{
      norwegianArmy.remove(unitNotInList);
      fail();
    }catch (IllegalArgumentException e ){
      assertEquals("The unit is not in the list",e.getMessage());
    }

    assertEquals(3,norwegianArmy.getAllUnits().size());

    // operation to remove a "null" unit is not successful
    try {
      norwegianArmy.remove(null);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Unit object can not be null",e.getMessage());
    }

    assertEquals(3,norwegianArmy.getAllUnits().size());

  }

  /**
   * Testing the getRandom method.
   * Using the getRandom method to get three random units
   * from the army. The possibility that all the three
   * chosen units are the same, is small
   */
  @Test
  void getRandom(){
    norwegianArmy.addAll(unitList);

    // Get three random units from the army unit list
    Unit unit1 = norwegianArmy.getRandom();
    Unit unit2 = norwegianArmy.getRandom();
    Unit unit3 = norwegianArmy.getRandom();

    // There is a small chance they all are the same.
    // But in most cases they are not
    assertFalse(unit1.equals(unit2) && unit2.equals(unit3) && unit3.equals(unit1));

  }

  /**
   * Testing the equals' method to make sure
   * it compares the army objects as intended. Only comparing
   * based on the name and the unit list.
   */
  @Test
  void equals(){
    Army army = new Army("Army");
    Army sameArmy = new Army("Army");
    Army notSameArmy = new Army("Another army");

    // army and sameArmy are equals
    assertEquals(army, sameArmy);
    // army and notSameArmy are not equal because their names are different
    assertNotEquals(army,notSameArmy);
    army.addAll(unitList);
    // army and sameArmy are not the same because army now contains units
    assertNotEquals(army,sameArmy);
    sameArmy.addAll(unitList);
    // both army and sameArmy contains the same units and have the same names.
    // therefore, they are equal
    assertEquals(army,sameArmy);

  }

  /**
   * Testing the unit instance getter methods to
   * make sure they return the right unit
   * instances.
   */
  @Test
  void getUnitInstances(){
    norwegianArmy.addAll(unitList);
    // Expecting there to be 1 instance of each unit class
    assertEquals(1,norwegianArmy.getInfantryUnits().size());
    Assertions.assertEquals("Infantry",norwegianArmy.getInfantryUnits().get(0).getName());
    assertEquals(1,norwegianArmy.getRangedUnits().size());
    Assertions.assertEquals("Ranged",norwegianArmy.getRangedUnits().get(0).getName());
    assertEquals(1,norwegianArmy.getCavalryUnits().size());
    Assertions.assertEquals("Cavalry",norwegianArmy.getCavalryUnits().get(0).getName());
    assertEquals(1,norwegianArmy.getCommanderUnits().size());
    Assertions.assertEquals("Commander",norwegianArmy.getCommanderUnits().get(0).getName());
    // Adding a new class of each unit instance to the army
    norwegianArmy.add(new InfantryUnit("Infantry2",100));
    norwegianArmy.add(new RangedUnit("Ranged2",100));
    norwegianArmy.add(new CavalryUnit("Cavalry2",100));
    norwegianArmy.add(new CommanderUnit("Commander2",100));
    // Expecting there to be 2 instances of each unit class
    assertEquals(2,norwegianArmy.getInfantryUnits().size());
    assertEquals(2,norwegianArmy.getRangedUnits().size());
    assertEquals(2,norwegianArmy.getCavalryUnits().size());
    assertEquals(2,norwegianArmy.getCommanderUnits().size());
    Assertions.assertEquals("Infantry2",norwegianArmy.getInfantryUnits().get(1).getName());
    Assertions.assertEquals("Ranged2",norwegianArmy.getRangedUnits().get(1).getName());
    Assertions.assertEquals("Cavalry2",norwegianArmy.getCavalryUnits().get(1).getName());
    Assertions.assertEquals("Commander2",norwegianArmy.getCommanderUnits().get(1).getName());

  }

}