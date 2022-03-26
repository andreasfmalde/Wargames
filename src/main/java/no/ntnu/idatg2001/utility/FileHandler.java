package no.ntnu.idatg2001.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import no.ntnu.idatg2001.army.Army;
import no.ntnu.idatg2001.unit.Unit;

/**
 * FileHandler class handling communication between
 * model classes and files. Taking care of
 * reading and writing to files.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class FileHandler {


  /**
   * Private constructor not used
   */
  private FileHandler(){}


  /**
   * Method to handle creating and writing army
   * objects to a file.
   * @param army Army object to write to a file
   */
  public static void writeArmyToFile(Army army) {
    // Making the filename based on the army name
    String filename = army.getName().replace(' ','-');
    // Creating and writing to file
    try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/armyFiles/"+filename+".csv"))){
      // Army name at the top of the file
      writer.write(army.getName()+"\n");
      // Listing all units in the army
      for(Unit unit : army.getAllUnits()){
        writer.write(unit.getClass().getSimpleName()+","+unit.getName()+","+unit.getHealth()+"\n");
      }

    }catch (IOException e){
      // Handle exception
      throw new RuntimeException(e.getMessage());
    }
  }


}
