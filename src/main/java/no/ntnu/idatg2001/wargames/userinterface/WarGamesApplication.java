package no.ntnu.idatg2001.wargames.userinterface;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.army.Army;
import no.ntnu.idatg2001.wargames.model.battle.Battle;
import no.ntnu.idatg2001.wargames.model.Terrain;
import no.ntnu.idatg2001.wargames.model.unit.Unit;
import no.ntnu.idatg2001.wargames.model.unit.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.unit.units.RangedUnit;

/**
 * Class that represents a user interface
 * for the WarGames application. Handling the
 * communication between the user and the
 * simulation.
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT (23.02.22)
 */
public class WarGamesApplication {

  private final InputValidator userInput;
  private Army armyOne;
  private Army armyTwo;

  /**
   * Constructor to start the WarGames
   * applications' main run method
   */
  public WarGamesApplication(){
    userInput = new InputValidator();
    run();
  }


  /**
   * Display menu choices and take input from the user
   * @return users' menu choice
   */
  private int getMenuChoice(){
    System.out.println("Choose an action below:");
    System.out.println("-----------------------");
    System.out.println("1- Autofill armies and run simulation");
    System.out.println("2- Fill armies and units manually");
    System.out.println("3- Exit program");
    System.out.print("> ");
    return userInput.getValidInt(1,3);
  }

  /**
   * Get army names from the user and autofill
   * the armies with units. Then run the simulation.
   */
  private void autoFillOption(){
    System.out.println("===== Autofill and run =====");
    System.out.print("Name of first army: ");
    String armyOneName = userInput.getStringInput();
    System.out.print("Name of second army: ");
    String armyTwoName = userInput.getStringInput();

    System.out.println("Making armies...");
    makeArmies(armyOneName,armyTwoName);
    System.out.print("Run simulation [yes/no] ?: ");
    String choice = userInput.getValidInputString(new String[] {"yes","no"});
    if(choice.equalsIgnoreCase("yes")){
      this.simulate();
    }

  }

  /**
   * Run the battle simulation and display the
   * winning army.
   */
  private void simulate(){
    try{
      Army winner = new Battle(armyOne,armyTwo, Terrain.HILL).simulate();
      System.out.println("The winner is "+winner);
    }catch (NullPointerException | IllegalArgumentException e){
      System.err.println("Simulation failed... [CAUSE]: "+e.getMessage());
    }

  }

  /**
   * Initialize and autofill armies with the same amount of units
   * @param firstArmyName Name of the first army
   * @param secondArmyName Name of the second army
   */
  private void makeArmies(String firstArmyName, String secondArmyName){
    this.armyOne = new Army(firstArmyName);
    this.armyTwo = new Army(secondArmyName);

    // Unit types in army one
    Unit footMan = new InfantryUnit("Footman",100);
    Unit knight = new CavalryUnit("Knight",100);
    Unit archer = new RangedUnit("Archer",100);
    Unit mountainKing = new CommanderUnit("Mountain King",180);

    // Unit types in army two
    Unit grunt = new InfantryUnit("Grunt",100);
    Unit raider = new CavalryUnit("Raider",100);
    Unit spearMan = new RangedUnit("Spearman",100);
    Unit guldan = new CommanderUnit("Gul'dan",180);

    for(int i = 0;i<500;i++){
      // Adding 500 infantry units in both armies.
      this.armyOne.add(footMan);
      this.armyTwo.add(grunt);
      if(i < 100){
        // Adding 100 cavalry units in both armies.
        this.armyOne.add(knight);
        this.armyTwo.add(raider);
      }
      if(i < 200){
        // Adding 200 ranged units in both armies.
        this.armyOne.add(archer);
        this.armyTwo.add(spearMan);
      }
      if(i < 1){
        // Adding 1 commander unit in both armies.
        this.armyOne.add(mountainKing);
        this.armyTwo.add(guldan);
      }
    }

  }

  /**
   * Initialize custom armies manually filled by user
   * and run battle simulation
   */
  private void manualFill(){
    System.out.println("===== Manual fill and run =====");
    System.out.print("Name of the first army: ");
    String armyOneName = userInput.getStringInput();
    generateArmy(armyOneName,1);
    System.out.print("Name of the second army: ");
    String armyTwoName = userInput.getStringInput();
    generateArmy(armyTwoName,2);

    System.out.println("Creating armies...");

    System.out.println("Run simulation [yes/no] ?: ");
    String choice = userInput.getValidInputString(new String[] {"yes","no"});
    if(choice.equalsIgnoreCase("yes")){
      this.simulate();
    }
  }

  /**
   * Initialize a specific type of unit
   * @param name Name of the unit
   * @param health Health of the unit
   * @param attack Attack power of the unit
   * @param resistance Resistance of the unit
   * @param i Variable 'i' describes which type of unit to return
   * @return a specific initialized type of unit
   * @throws IllegalArgumentException If parameters are invalid objects will not be returned
   */
  private Unit unit(String name,int health,int attack,int resistance, int i)throws IllegalArgumentException{
    switch (i){
      case 1:
        return new RangedUnit(name,health,attack,resistance);
      case 2:
        return new CavalryUnit(name,health,attack,resistance);
      case 3:
        return new CommanderUnit(name,health,attack,resistance);
      default:
        return new InfantryUnit(name,health,attack,resistance);
    }
  }

  /**
   * Generating armies with custom types and amount of units
   * @param armyName Name of the army
   * @param armyNr Determine which army to generate
   */
  private void generateArmy(String armyName,int armyNr){
    List<Unit> unitList = new ArrayList<>();
    String[] units = {"Infantry","Ranged","Cavalry","Commander"};
    for(int i = 0; i<4;i++){
      System.out.print("How many "+units[i]+" units? ");
      int amount = userInput.getValidInt();
      System.out.print("Name of the "+units[i]+" unit: ");
      String name = userInput.getStringInput();
      System.out.print("Health: ");
      int health = userInput.getValidInt(0,1000);
      System.out.print("Attack: ");
      int attack = userInput.getValidInt(0,50);
      System.out.print("Resistance: ");
      int resist = userInput.getValidInt(0,50);
      System.out.println();

      try{
        for(int j = 0;i<amount;j++){
          unitList.add(unit(name,health,attack,resist,i));
        }
      }catch (IllegalArgumentException e){
        System.err.println(e.getMessage());
      }

    }
    if(armyNr == 1){
      this.armyOne = new Army(armyName,unitList);

    }else if(armyNr == 2){
      this.armyTwo = new Army(armyName,unitList);
    }

  }


  /**
   * Main running loop to keep the user in the
   * application. Redirects the user based on
   * the menu choices made.
   */
  private void run(){
    boolean running = true;
    System.out.println("|============================|");
    System.out.println("|          WARGAMES          |");
    System.out.println("|     An army simulation     |");
    System.out.println("|============================|");
    System.out.println();
    while (running){

      int choice = getMenuChoice();

      switch (choice){
        case 1:
          this.autoFillOption();
          break;
        case 2:
          this.manualFill();
          break;
        case 3:
          running = false;
          System.out.println("EXITING PROGRAM... bye!");
          break;
        default:
          System.err.println("Not a valid input.");
      }
    }
  }

}
