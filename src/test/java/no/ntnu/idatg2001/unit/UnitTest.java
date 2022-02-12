package no.ntnu.idatg2001.unit;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.unit.units.CavalryUnit;
import no.ntnu.idatg2001.unit.units.CommanderUnit;
import no.ntnu.idatg2001.unit.units.InfantryUnit;
import no.ntnu.idatg2001.unit.units.RangedUnit;
import org.junit.jupiter.api.Test;

class UnitTest {


  @Test
  void constructorTest(){
    Unit infantry = new InfantryUnit("Infantry",100);
    Unit ranged = new RangedUnit("Ranged",150);
    Unit cavalry = new CavalryUnit("Cavalry",180);
    Unit commander = new CommanderUnit("Infantry",200);

    //Infantry
    assertEquals(15,infantry.getAttack());
    assertEquals(10,infantry.getArmor());
    try{
      Unit anotherInfantry = new InfantryUnit("",100,10,30);
      assertEquals("Something",anotherInfantry.getName());
    }catch (IllegalArgumentException e){
      assertEquals("Name can not be blank!",e.getMessage());
    }

    // Ranged
    assertEquals(15,ranged.getAttack());
    assertEquals(8,ranged.getArmor());
    try{
      Unit anotherRanged = new RangedUnit("another",-10,10,30);
      assertEquals(-9,anotherRanged.getHealth());
    }catch (IllegalArgumentException e){
      assertEquals("Health can not be less than 0",e.getMessage());
    }

    // Cavalry
    assertEquals(20,cavalry.getAttack());
    assertEquals(12,cavalry.getArmor());
    try{
      Unit anotherCavalry = new CavalryUnit("another",100,-10,30);
      assertEquals(-9,anotherCavalry.getHealth());
    }catch (IllegalArgumentException e){
      assertEquals("Attack value can not be less than 0",e.getMessage());
    }

    // Commander
    assertEquals(25,commander.getAttack());
    assertEquals(15,commander.getArmor());
    try{
      Unit anotherCommander = new CommanderUnit("another",100,10,-30);
      assertEquals(-9,anotherCommander.getHealth());
    }catch (IllegalArgumentException e){
      assertEquals("Armor value can not be less than 0",e.getMessage());
    }

  }

  @Test
  void attack(){
    Unit infantry = new InfantryUnit("Infantry",100);
    Unit ranged = new RangedUnit("Ranged",150);
    Unit cavalry = new CavalryUnit("Cavalry",180);
    Unit commander = new CommanderUnit("Infantry",200);

    infantry.attack(ranged);
    // health = 150 - (15 + 2) + (8 + 6) = 150 - 17 + 14 = 147
    assertEquals(147,ranged.getHealth());

    ranged.attack(cavalry);
    // health = 180 - (15 + 3) + (12 + 1) = 180 - 18 + 13 = 175
    assertEquals(175,cavalry.getHealth());

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

  @Test
  void getAttackBonus(){
    Unit infantry = new InfantryUnit("Infantry",100);
    Unit ranged = new RangedUnit("Ranged",150);
    Unit cavalry = new CavalryUnit("Cavalry",180);
    Unit commander = new CommanderUnit("Infantry",200);

    // Infantry
    assertEquals(2,infantry.getAttackBonus());
    assertEquals(2,infantry.getAttackBonus());

    // Ranged
    assertEquals(3,ranged.getAttackBonus());
    assertEquals(3,ranged.getAttackBonus());

    // Cavalry
    assertEquals(6,cavalry.getAttackBonus());
    assertEquals(2,cavalry.getAttackBonus());
    assertEquals(2,cavalry.getAttackBonus());

    // Commander
    assertEquals(6,commander.getAttackBonus());
    assertEquals(2,commander.getAttackBonus());
    assertEquals(2,commander.getAttackBonus());

  }

  @Test
  void getResistBonus(){
    Unit infantry = new InfantryUnit("Infantry",100);
    Unit ranged = new RangedUnit("Ranged",150);
    Unit cavalry = new CavalryUnit("Cavalry",180);
    Unit commander = new CommanderUnit("Infantry",200);

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