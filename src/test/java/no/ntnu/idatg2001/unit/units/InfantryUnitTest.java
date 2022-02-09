package no.ntnu.idatg2001.unit.units;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class InfantryUnitTest {


  @Test
  void basicGetterTests(){
    //Checking for default attack and armor values
    InfantryUnit infantry = new InfantryUnit("Footmen",100);
    assertEquals("Footmen",infantry.getName());
    assertEquals(100,infantry.getHealth());
    assertEquals(15,infantry.getAttack());
    assertEquals(10,infantry.getArmor());

    //Checking for custom values if specified
    InfantryUnit anotherInfantry = new InfantryUnit("Knights",20,5,30);
    assertNotEquals(100,anotherInfantry.getHealth());
    assertEquals(20,anotherInfantry.getHealth());
    assertNotEquals(15,anotherInfantry.getAttack());
    assertEquals(5,anotherInfantry.getAttack());
    assertNotEquals(10,anotherInfantry.getArmor());
    assertEquals(30,anotherInfantry.getArmor());

  }


  @Test
  void setHealth(){
    InfantryUnit infantry = new InfantryUnit("Footmen",100);
    assertEquals(100,infantry.getHealth());
    infantry.setHealth(50);
    assertEquals(50,infantry.getHealth());
    assertNotEquals(100,infantry.getHealth());

    try {
      infantry.setHealth(-20);
    }catch (IllegalArgumentException e){
      System.out.println(e.getMessage());
    }
    assertNotEquals(-20,infantry.getHealth());
    assertEquals(50,infantry.getHealth());

  }

  @Test
  void attack(){
    InfantryUnit infantry = new InfantryUnit("Footmen",100);
    InfantryUnit anotherInfantry = new InfantryUnit("Knights",20,5,12);

    infantry.attack(anotherInfantry);

    assertEquals(100,infantry.getHealth());
    assertNotEquals(20,anotherInfantry.getHealth());
    // another infantry health = 20-(15+2)+(12+1)=20-17+11 = 16
    assertEquals(16,anotherInfantry.getHealth());

  }

  @Test
  void getAttackBonus() {

    InfantryUnit infantry = new InfantryUnit("Footmen",100);
    // getAttackBonus expected to always return 2
    assertEquals(2,infantry.getAttackBonus());
    assertEquals(2,infantry.getAttackBonus());
    assertNotEquals(1,infantry.getAttackBonus());
    //
    try {
      InfantryUnit failingUnit = new InfantryUnit("The Failmen", -2);
    }catch (IllegalArgumentException e){
      assertEquals("Health can not be less than 0",e.getMessage());
    }

  }

  @Test
  void getResistBonus() {
    InfantryUnit infantry = new InfantryUnit("Footmen",100);
    assertEquals(1,infantry.getResistBonus());
    assertEquals(1,infantry.getResistBonus());
    assertNotEquals(2,infantry.getResistBonus());
  }
}