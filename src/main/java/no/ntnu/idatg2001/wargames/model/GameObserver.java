package no.ntnu.idatg2001.wargames.model;

/**
 * Interface to represent the game observers
 * @author Andreas Follevaag Malde
 * @version 1.0 - SNAPSHOT
 */
public interface GameObserver {
  /**
   * Update state method will be called in each game
   * observer to update the state of an object
   * @param input Input string from the battle simulation
   */
  void updateState(String input);
}
