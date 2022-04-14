package no.ntnu.idatg2001.wargames.model.unit;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.wargames.model.Terrain;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class to test the core functionality
 * of the unit classes. The infantry, ranged,
 * cavalry and the commander unit class are tested.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
class UnitTest {

  Unit infantry;
  Unit ranged;
  Unit cavalry;
  Unit commander;

  /**
   * Initialization method to initialize an
   * infantry, a ranged, a cavalry and a commander
   * unit before each test method is called.
   */
  @BeforeEach
  void init(){
    infantry = new InfantryUnit("Infantry",100);
    ranged = new RangedUnit("Ranged",150);
    cavalry = new CavalryUnit("Cavalry",180);
    commander = new CommanderUnit("Infantry",200);
    // All units are set to Hill terrain
    infantry.setTerrain(Terrain.HILL);
    ranged.setTerrain(Terrain.HILL);
    cavalry.setTerrain(Terrain.HILL);
    commander.setTerrain(Terrain.HILL);
  }

  /**
   * Constructor testing method. This method will
   * test to make sure all the constructors work as
   * intended. They are also tested to handle wrong
   * input.
   */
  @Test
  void constructorTest(){

    //Infantry
    assertEquals(15,infantry.getAttack());
    assertEquals(10,infantry.getArmor());
    try{
      new InfantryUnit("",100,10,30);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Name can not be blank!",e.getMessage());
    }

    // Ranged
    assertEquals(15,ranged.getAttack());
    assertEquals(8,ranged.getArmor());
    try{
      new RangedUnit("another",-10,10,30);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Health can not be less than 0",e.getMessage());
    }

    // Cavalry
    assertEquals(20,cavalry.getAttack());
    assertEquals(12,cavalry.getArmor());
    try{
      new CavalryUnit("another",100,-10,30);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Attack value can not be less than 0",e.getMessage());
    }

    // Commander
    assertEquals(25,commander.getAttack());
    assertEquals(15,commander.getArmor());
    try{
      new CommanderUnit("another",100,10,-30);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Armor value can not be less than 0",e.getMessage());
    }

  }

  /**
   * The attack test method makes sure the attack method
   * of the unit classes work as intended.
   * It also makes sure a units' health will never
   * be less than 0.
   */
  @Test
  void attack(){

    infantry.attack(ranged);
    // health = 150 - (15 + 2) + (8 + 6) = 150 - 17 + 14 = 147
    assertEquals(147,ranged.getHealth());

    ranged.attack(cavalry);
    // health = 180 - (15 + 2(HILL terrain)+3) + (12 + 1) = 180 - 18 + 13 = 173
    assertEquals(173,cavalry.getHealth());

    cavalry.attack(commander);
    // health = 200 - (20 + 6) + (15 + 1) = 200 - 26 + 16 = 190
    assertEquals(190,commander.getHealth());

    commander.attack(infantry);
    // health = 100 - (25 + 6) + (10 + 1) = 100 - 31 + 11 = 80
    assertEquals(80,infantry.getHealth());

    infantry.setHealth(10);
    commander.attack(infantry);
    // Health will never be less than 0
    // health = 10 - (25 + 2) + (10 +1)= 10 -27 + 11 = -5 (Health will be 0)
    assertEquals(0,infantry.getHealth());

  }

  /**
   * Test method for the attackBonus. Testing
   * if the method works as intended.
   */
  @Test
  void getAttackBonus(){

    // Infantry
    assertEquals(2,infantry.getAttackBonus());
    assertEquals(2,infantry.getAttackBonus());

    // Ranged
    assertEquals(5,ranged.getAttackBonus());
    assertEquals(5,ranged.getAttackBonus());

    // Cavalry
    assertEquals(6,cavalry.getAttackBonus());
    assertEquals(2,cavalry.getAttackBonus());
    assertEquals(2,cavalry.getAttackBonus());

    // Commander
    assertEquals(6,commander.getAttackBonus());
    assertEquals(2,commander.getAttackBonus());
    assertEquals(2,commander.getAttackBonus());

  }

  /**
   * Test method for the resistBonus. Testing
   * if the method works as intended.
   */
  @Test
  void getResistBonus(){

    // Infantry
    assertEquals(1,infantry.getResistBonus());
    assertEquals(1,infantry.getResistBonus());

    // Ranged
    assertEquals(6,ranged.getResistBonus());
    assertEquals(4,ranged.getResistBonus());
    assertEquals(2,ranged.getResistBonus());
    assertEquals(2,ranged.getResistBonus());

    // Cavalry
    assertEquals(1,cavalry.getResistBonus());
    assertEquals(1,cavalry.getResistBonus());

    // Commander
    assertEquals(1,commander.getResistBonus());
    assertEquals(1,commander.getResistBonus());

  }

}