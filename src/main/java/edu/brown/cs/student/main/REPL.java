package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * The REPL class for this lab.
 */
public class REPL {

  HashMap<String, Object> commands;
  /**
   * instantiates a REPL.
   *
   * @param commands - a map of string command names to IReplMethod objects, which
   *                 are a wrapper for a method to be called
   */
  // TODO: create a more generic interface for Handler
  public REPL(HashMap<String, Object> commands) {
    this.commands = commands;
  }

  /**
   * This run method for the REPL requires an ApiClient object.
   *
   */
  public void run() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ErrorHandler errorHandler = new ErrorHandler();

    while (true) { // parsing input loop
      try {
        String s = reader.readLine();
        if (s == null) { // ctrl-D (exit) would result in null input
          break;
        }

        // initialize a StringTokenizer to help parse the input, broken by space or tabs
        StringTokenizer st = new StringTokenizer(s, " \t", false);

        // if the input is not blank, get the first token (the command string)
        if (st.hasMoreTokens()) {
          String command = st.nextToken();

          switch (command) {
            case "user":  // reads in the sqlite file for ORM or API
              String next_cmd;
              if (st.hasMoreTokens()) {
                next_cmd = st.nextToken();
              } else {
                next_cmd = "";
              }
              if (next_cmd.equals("online")) {
                // TODO (For API ppl) do something that's related to the API pursuers, please fill this part in
              }
//              try {
//                // TODO (for ORM) call a method that handles reading in sqlite files
//              } catch (IOException e) {
//                errorHandler.filePathException();
//              }
              break;
            case "similar":
              // TODO create a Handler that "prints out the user_ids of the most similar k users", either searching by user_id or
              // weights, heights, and ages
              break;
            case "classify": {
//              String name;
//              if (st.hasMoreTokens()) {
//                name = st.nextToken();
//              } else {
//                name = "";
//              }
              // TODO create a Handler to print out a horoscope comparison chart of the k most similar users [closest in euclidean distance of weights, heights, and ages]
              //either by "similar k some_user_id" or "similar k weight height age"

              break;
            }
            default:  // command unrecognized
              errorHandler.inputFormatException();
              break;
          }
        }
      } catch (IOException e) { // some kind of read error, so the repl exits
        errorHandler.parseInputException();
        break;
      }
    }
    try {
      reader.close();
    } catch (IOException e) {
      errorHandler.closeReaderException();
    }
  }
}


