package no.ntnu.idatg2001.wargames;

/**
 * This class only exists to make the JAR- file.
 * The maven plugins shade and jPackage need the main file to not be extending
 * from the JavaFX Application super class.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public class MainJAR {
  /**
   * Main method which is used to be able to make the JAR-file
   * in maven.
   * @param args N/A
   */
  public static void main(String[] args) {
    Main.main(args);
  }
}
