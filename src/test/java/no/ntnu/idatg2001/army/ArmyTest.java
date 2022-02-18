package no.ntnu.idatg2001.army;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.unit.Unit;
import no.ntnu.idatg2001.unit.units.CavalryUnit;
import no.ntnu.idatg2001.unit.units.CommanderUnit;
import no.ntnu.idatg2001.unit.units.InfantryUnit;
import no.ntnu.idatg2001.unit.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArmyTest {

  private Army norwegianArmy;
  private Army swedishArmy;
  private List<Unit> unitList;

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

  @Test
  void addToArmy(){

    // norwegian army are expected to be empty
    assertFalse(norwegianArmy.hasUnits());
    norwegianArmy.add(new RangedUnit("Ranged",100));
    // norwegian army are not empty anymore
    assertTrue(norwegianArmy.hasUnits());
    // operation to add infantry unit to the army is expected to be successful
    assertTrue(norwegianArmy.add(new InfantryUnit("Infantry",150)));
    // there are 2 units in the army
    assertEquals(2,norwegianArmy.getAllUnits().size());

    // operation to add null to the army should not be successful
    assertFalse(norwegianArmy.add(null));

    assertTrue(swedishArmy.addAll(unitList));
    // swedish army has 4 units
    assertEquals(4,swedishArmy.getAllUnits().size());

    // operation to add null as a list to the army should not be successful
    assertFalse(swedishArmy.addAll(null));

  }

  @Test
  void remove(){
    norwegianArmy.addAll(unitList);
    // norwegian army has 4 units
    assertEquals(4,norwegianArmy.getAllUnits().size());
    // operation to remove a unit already in the army, from the army is expected to be successful
    assertTrue(norwegianArmy.remove(norwegianArmy.getAllUnits().get(1)));
    // norwegian army now has 3 units
    assertEquals(3,norwegianArmy.getAllUnits().size());

    // should not be able to remove a unit not in the list
    assertFalse(norwegianArmy.remove(new CommanderUnit("not a unit in the list",10)));

    assertEquals(3,norwegianArmy.getAllUnits().size());

    // operation to remove a "null" unit is not successful
    assertFalse(norwegianArmy.remove(null));

    assertEquals(3,norwegianArmy.getAllUnits().size());

  }

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

}