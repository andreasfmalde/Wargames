package no.ntnu.idatg.wargames.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import no.ntnu.idatg.wargames.model.army.Army;
import no.ntnu.idatg.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg.wargames.model.unit.Unit;
import no.ntnu.idatg.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg.wargames.model.unit.units.RangedUnit;

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
  public static void writeArmyToFile(Army army) throws IOException {
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
      throw new IOException(e.getMessage());
    }
  }

  /**
   * Method that will read a file if it exists and
   * create an army object based on the file
   * @param filename Name of the file to read
   * @return An army object based on the specifications in the file
   * @throws IOException Any input/output problems will have to be handled
   */
  public static Army getArmyFromFile(String filename) throws IOException {
    // Opening a file to read based on the filename
    try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/armyFiles/"+filename+".csv"))){
      //Get the first line in the file
      String line = reader.readLine();
      // Make sure only the army name is on top of the file
      if(line.split(",").length != 1)
        throw new IOException("No army name at the top of the file");
      // Initialize an army object
      Army army = new Army(line);
      // Add units to the army based on the amount of unit lines in the file
      while((line = reader.readLine())!=null){
        String[] unitLine = line.split(",");
        // Make sure all unit lines contains three values
        if(unitLine.length != 3)
          throw new IOException("There are not three unit values on the unit line");
        // Add unit to the army based on the unit values
        army.add(FileHandler.getUnit(unitLine[0].strip(),unitLine[1].strip(),unitLine[2].strip()));
      }
      // Return the army
      return army;
    }
    catch (IOException e){
      // Handle exception
      throw new IOException(e.getMessage());
    }
  }


  /**
   * Create a unit based the parameters.
   * @param classType String value to specify unit type
   * @param unitName Name of the unit
   * @param health Health of the unit
   * @return Unit of the specified type
   */
  private static Unit getUnit(String classType, String unitName, String health){
    int unitHealth = Integer.parseInt(health);
    // Return unit type based on the class type parameter
    switch (classType){
      case "InfantryUnit":
        return new InfantryUnit(unitName,unitHealth);
      case "RangedUnit":
        return  new RangedUnit(unitName,unitHealth);
      case "CavalryUnit":
        return new CavalryUnit(unitName,unitHealth);
      case "CommanderUnit":
        return new CommanderUnit(unitName,unitHealth);
      default:
        return null;
    }
  }



}
