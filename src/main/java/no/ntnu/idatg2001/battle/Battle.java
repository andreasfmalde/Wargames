package no.ntnu.idatg2001.battle;

import java.util.Random;
import no.ntnu.idatg2001.army.Army;
import no.ntnu.idatg2001.unit.Unit;

public class Battle {

  private Army armyOne;
  private Army armyTwo;
  private Random random;

  public Battle(Army armyOne, Army armyTwo){
    if (armyOne == null || armyTwo == null){
      throw new IllegalArgumentException("Armies can not be null");
    }
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    random = new Random();
  }

  public Army simulate(){
    while(armyOne.hasUnits() && armyTwo.hasUnits()){
      if(random.nextInt(2) == 0){
        Unit defendingUnit = armyTwo.getRandom();
        armyOne.getRandom().attack(defendingUnit);
        if (defendingUnit.getHealth() == 0){
          armyTwo.remove(defendingUnit);
        }
      }else{
        Unit defendingUnit = armyOne.getRandom();
        armyTwo.getRandom().attack(defendingUnit);
        if (defendingUnit.getHealth() == 0){
          armyOne.remove(defendingUnit);
        }
      }
    }

    return armyOne.hasUnits() ? armyOne : armyTwo;
  }

  @Override
  public String toString(){
    return "BATTLE: [ "+armyOne.getName() + " vs "+armyTwo.getName()+" ]";
  }
}
