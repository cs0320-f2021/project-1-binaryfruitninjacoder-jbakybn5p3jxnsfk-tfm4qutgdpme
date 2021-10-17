package edu.brown.cs.student.main.Handlers;

public class ErrorHandler {
  /**
   * Default constructor
   */
  public ErrorHandler() {
  }

  public static String inputFormatException() {
    return "INPUT ERROR: We couldn't process your input, please make sure your input is in the correct format!";
  }

  public static String filePathException() {
    return "FILE ERROR: The path you entered does not exist!";
  }

  public static String closeReaderException() {
    return "ERROR: failed to close reader!";
  }

  public static String parseInputException() {
    return "ERROR: couldn't parse your input...";
  }
}
