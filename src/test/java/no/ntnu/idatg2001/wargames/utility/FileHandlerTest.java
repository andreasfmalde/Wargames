package no.ntnu.idatg2001.wargames.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * File-handler test class to test for basic
 * file handling functionality
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
class FileHandlerTest {

  private Army army;

  /**
   * Initialize an army object to use before each test.
   */
  @BeforeEach
  void init(){
    army = new Army("Tester Army");
    army.add(new InfantryUnit("InfantryUnit",100));
    army.add(new RangedUnit("RangedUnit",110));
    army.add(new CavalryUnit("CavalryUnit",120));
    army.add(new CommanderUnit("CommanderUnit",130));
  }


  /**
   * Method to test reading and writing to files
   * under normal operations. When all values are
   * how they are supposed to be. No exception handling.
   */
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
      sameArmy = FileHandler.getArmyFromFile(new File("src/main/resources/armyFiles/Tester-Army.csv"));
      // army and sameArmy should be the same
      assertEquals(army,sameArmy);
    }
    catch (IOException e){
      // Any problems reading a file will cause a failed test
      fail();
    }

  }

  /**
   * Testing to make sure the file handler class handles
   * problems in files in a proper way
   */
  @Test
  void testHandlingProblemsInFiles(){
    // Testing to make sure an army object will not be created if there
    // are no file with the specified filename,
    try{
      Army failArmy = FileHandler.getArmyFromFile(new File("NotAFile"));
      fail();
    }catch (IOException e){
      assertTrue(true);
    }

    // Throwing exception if the file format is not how it should be
    try{
      Army failArmy = FileHandler.getArmyFromFile(new File("src/main/resources/armyFiles/fail.csv"));
      fail();
    }catch (IOException e){
      assertEquals("There are not three unit values on the unit line",e.getMessage());
    }

  }



}