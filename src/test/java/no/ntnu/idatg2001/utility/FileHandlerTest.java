package no.ntnu.idatg2001.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import no.ntnu.idatg2001.army.Army;
import no.ntnu.idatg2001.unit.units.CavalryUnit;
import no.ntnu.idatg2001.unit.units.CommanderUnit;
import no.ntnu.idatg2001.unit.units.InfantryUnit;
import no.ntnu.idatg2001.unit.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHandlerTest {

  private Army army;

  @BeforeEach
  void init(){
    army = new Army("Tester Army");
    army.add(new InfantryUnit("InfantryUnit",100));
    army.add(new RangedUnit("RangedUnit",110));
    army.add(new CavalryUnit("CavalryUnit",120));
    army.add(new CommanderUnit("CommanderUnit",130));
  }


  @Test
  void normalOperationReadingAndWritingToFileTest(){
    // Write army class to file
    try{
      FileHandler.writeArmyToFile(army);
    }
    catch (IOException e){
      // Any problems writing to the file will cause a failed test
      fail();
    }
    // Make a new uninitialized army object
    Army sameArmy;
    // Read from file and create an army object based on the file
    try {
      sameArmy = FileHandler.getArmyFromFile("Tester-Army");
      // army and sameArmy should be the same
      assertEquals(army,sameArmy);
    }
    catch (IOException e){
      // Any problems reading a file will cause a failed test
      fail();
    }

  }



}