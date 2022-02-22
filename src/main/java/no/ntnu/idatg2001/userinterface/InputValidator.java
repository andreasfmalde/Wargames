package no.ntnu.idatg2001.userinterface;

import java.util.Scanner;

public class InputValidator {
  private Scanner scanner;


  public InputValidator(){
    scanner = new Scanner(System.in);
  }

  public String getStringInput(){
    return scanner.nextLine();
  }

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
