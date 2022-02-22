package no.ntnu.idatg2001.battle;

import static org.junit.jupiter.api.Assertions.*;


import no.ntnu.idatg2001.army.Army;
import no.ntnu.idatg2001.unit.Unit;
import no.ntnu.idatg2001.unit.units.CavalryUnit;
import no.ntnu.idatg2001.unit.units.CommanderUnit;
import no.ntnu.idatg2001.unit.units.InfantryUnit;
import no.ntnu.idatg2001.unit.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class to test the core functionality
 * of the battle class. Mainly the simulate
 * method.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (22.02.2022)
 */
class BattleTest {

  Army humanArmy;
  Army orcishHorde;

  /**
   * Before each test this initialization method
   * will be called to set up to armies with the
   * same types and amount of units.
   */
  @BeforeEach
  void init(){
    Unit footMan = new InfantryUnit("Footman",100);
    Unit knight = new CavalryUnit("Knight",100);
    Unit archer = new RangedUnit("Archer",100);
    Unit mountainKing = new CommanderUnit("Mountain King",180);
    humanArmy = new Army("Human Army");


    Unit grunt = new InfantryUnit("Grunt",100);
    Unit raider = new CavalryUnit("Raider",100);
    Unit spearMan = new RangedUnit("Spearman",100);
    Unit guldan = new CommanderUnit("Gul'dan",180);
    orcishHorde = new Army("Orcish horde");

    for(int i = 0;i<500;i++){
      // Adding 500 infantry units in both armies.
      humanArmy.add(footMan);
      orcishHorde.add(grunt);
      if(i < 100){
        // Adding 100 cavalry units in both armies.
        humanArmy.add(knight);
        orcishHorde.add(raider);
      }
      if(i < 200){
        // Adding 200 ranged units in both armies.
        humanArmy.add(archer);
        orcishHorde.add(spearMan);
      }
      if(i<1){
        // Adding 1 commander unit in both armies.
        humanArmy.add(mountainKing);
        orcishHorde.add(guldan);
      }
    }
  }


  /**
   * Testing if the simulation method will make
   * the orcish horde win after changing the
   * health of the human army's units to a lot less
   */
  @Test
  void simulateOrcishToWin(){
    // Making the health of the units in the human army
    // a lot less than the orcish horde
    for(Unit unit: humanArmy.getAllUnits()){
      int health = unit.getHealth();
      unit.setHealth(health<100?health:health-95);
    }

    Battle battle = new Battle(humanArmy,orcishHorde);

    // It is most likely that the Orcish horde will win
    // because the human army units have less health
    assertEquals(orcishHorde,battle.simulate());
  }


  /**
   * Testing if the simulation method will make
   * the human army win after changing the
   * health of the orcish horde's units to a lot less
   */
  @Test
  void simulateHumanToWin(){
    // Making the health of the units in the orcish horde
    // a lot less than the human army
    for(Unit unit: orcishHorde.getAllUnits()){
      int health = unit.getHealth();
      unit.setHealth(health<100?health:health-95);
    }
    Battle battle = new Battle(humanArmy,orcishHorde);

    // It is most likely that the Human army will win
    // because the orcish Horde  units have less health
    assertEquals(humanArmy,battle.simulate());


  }

}