package no.ntnu.idatg2001.wargames.model.battle;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.Terrain;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class to test the core functionality
 * of the battle class. Mainly the simulate
 * method.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
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

    for(int i = 0;i<100;i++){
      // Adding 100 infantry units in both armies.
      humanArmy.add(footMan);
      orcishHorde.add(grunt);
      if(i < 20){
        // Adding 20 cavalry units in both armies.
        humanArmy.add(knight);
        orcishHorde.add(raider);
      }
      if(i < 60){
        // Adding 60 ranged units in both armies.
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
   * Testing to make sure the battle constructor
   * work as intended
   */
  @Test
  void constructorTest(){
    List<Army> armies = new ArrayList<>();
    armies.add(humanArmy);
    armies.add(orcishHorde);

    try{
      // Expecting battle constructor to succeed
      new Battle(armies,Terrain.PLAINS);
      assertTrue(true);
    }catch (IllegalArgumentException e){
      fail();
    }

    try{
      // Expecting battle constructor to succeed
      new Battle(humanArmy,orcishHorde,Terrain.FOREST);
      assertTrue(true);
    }catch (IllegalArgumentException e){
      fail();
    }

    try{
      // Expecting battle constructor to fail
      new Battle(null,humanArmy,Terrain.FOREST);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Armies can not be null",e.getMessage());
    }
    // Adding a third army to the army list
    armies.add(humanArmy);
    try{
      // Expecting battle constructor to fail
      new Battle(armies,Terrain.FOREST);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("There has to be two units in the list",e.getMessage());
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

    Battle battle = new Battle(humanArmy,orcishHorde, Terrain.FOREST);

    // It is most likely that the Orcish horde will win
    // because the human army units have less health
    Assertions.assertEquals(orcishHorde,battle.simulate());
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
    Battle battle = new Battle(humanArmy,orcishHorde,Terrain.FOREST);

    // It is most likely that the Human army will win
    // because the orcish Horde  units have less health
    Assertions.assertEquals(humanArmy,battle.simulate());


  }

}