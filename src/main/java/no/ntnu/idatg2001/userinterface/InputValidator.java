package no.ntnu.idatg2001.userinterface;

import java.util.Scanner;

/**
 * Class to handle user input. Both retrieving input
 * from a user, and working as an input validator.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (23.02.2022)
 *
 */
public class InputValidator {
  private Scanner scanner;

  /**
   * Constructor to create and initialize an InputValidator object
   */
  public InputValidator(){
    scanner = new Scanner(System.in);
  }

  /**
   * @return string input from the user
   */
  public String getStringInput(){
    return scanner.nextLine();
  }


  /**
   * Get valid int from the user. Will loop and prompt the user
   * to insert a valid int until one is given.
   * @return int from the user
   */
  public int getValidInt(){
    boolean valid = false;
    int choice = 0;

    while (!valid){
      if(scanner.hasNextInt()){
        choice = scanner.nextInt();
        valid = true;
      }else{
        System.err.print("Enter valid int: ");
      }
      scanner.nextLine();
    }
    return choice;
  }

  /**
   * Get a valid int from the user. Will also check to make sure the value is
   * between the min and max value specified as parameters.
   * @param min Minimum value of the scope
   * @param max Maximum value of the scope
   * @return int between min and max value
   */
  public int getValidInt(int min, int max){
    boolean valid = false;
    int choice = 0;

    while (!valid){
      if(scanner.hasNextInt()){
        choice = scanner.nextInt();
        if(choice >= min && choice <= max){
          valid = true;
        }else{
          System.err.println("Int has to be between "+min+" and "+max);
        }

      }else{
        System.err.print("Enter valid int: ");
      }
      scanner.nextLine();
    }
    return choice;
  }

  /**
   * Get string input from the user. Will ask for a string from the
   * user until it corresponds with one of the words in the parameter.
   * @param wordList String array containing all words to compare against
   * @return string input corresponding to one of the words in the parameter
   */
  public String getValidInputString(String[] wordList){
    String value = "";
    boolean valid = false;

    // Stay in loop until a valid input is reached
    while(!valid){
      value = scanner.nextLine();

      //Checking if inputted value is in the word list
      int index = 0;
      while(index< wordList.length && !valid){
        if(value.equalsIgnoreCase(wordList[index])){
          valid = true;
        }
        index ++;
      }

      // If the inputted word is not in the word list, print error message to the user
      if(!valid){
        String words = "";
        for(String word : wordList){
          words += word + " ";
        }
        System.err.print("Invalid word. Choose between ["+words+"]: ");
      }
    }
    return value;
  }

}
