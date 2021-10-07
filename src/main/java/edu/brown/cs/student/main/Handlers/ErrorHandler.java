package edu.brown.cs.student.main.Handlers;

public class ErrorHandler {
  /**
   * Default constructor
   */
  public ErrorHandler() {
  }

  public void inputFormatException() {
    System.out.println("INPUT ERROR: We couldn't process your input, please make sure your input is in the correct format!");
  }

  public void filePathException() {
    System.out.println("FILE ERROR: The path you entered does not exist!");
  }

  public void closeReaderException() {
    System.out.println("ERROR: failed to close reader!");
  }

  public void parseInputException() {
    System.out.println("ERROR: couldn't parse your input...");
  }
}
