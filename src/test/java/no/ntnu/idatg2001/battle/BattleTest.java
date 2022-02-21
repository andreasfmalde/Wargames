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

class BattleTest {

  Army humanArmy;
  Army orcishHorde;

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
      humanArmy.add(footMan);
      orcishHorde.add(grunt);
      if(i < 100){
        humanArmy.add(knight);
        orcishHorde.add(raider);
      }
      if(i < 200){
        humanArmy.add(archer);
        orcishHorde.add(spearMan);
      }
      if(i<1){
        humanArmy.add(mountainKing);
        orcishHorde.add(guldan);
      }
    }
  }

  @Test
  void simulate(){
    Battle battle = new Battle(humanArmy,orcishHorde);
    Army winner = battle.simulate();
    Army winnerAgain = battle.simulate();
    System.out.println(winner);
    System.out.println(winnerAgain);
  }

}