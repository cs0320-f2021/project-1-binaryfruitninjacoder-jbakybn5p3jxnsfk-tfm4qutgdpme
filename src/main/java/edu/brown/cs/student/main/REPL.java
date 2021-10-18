package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Handlers.ClassifyHandler;
import edu.brown.cs.student.main.Handlers.ErrorHandler;
import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.ORM.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;

/**
 * The REPL class for this lab.
 */
public class REPL {

  Map<String, Function<String, String>> commands;
  /**
   * instantiates a REPL.
   *
   * @param commands - a map of string command names to IReplMethod objects, which
   *                 are a wrapper for a method to be called
   */
  public REPL(Map<String, Function<String, String>> commands) {
    this.commands = commands;
  }

  // Check if the specified file Exists or not
  private static boolean isPath(String str){
    File f = new File(str);
    if (f.exists())
    { return true;
    } else {
      return false;
    }
  }

  public void activate(Reader reader) {
    try (BufferedReader br = new BufferedReader(reader)) {
      String input;
      // read the INPUT by line
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] args = input.split(" ");
          if (commands.containsKey(args[0])) {
            String message;
            message = commands.get(args[0]).apply(...);
          }
          else {
            //error message
          }
        } catch (Exception e) {
          System.out.println("Error: input not correct");
        }
      }
    } catch (IOException | SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  /**
   * This run method for the REPL requires an ApiClient object.
   *
   */
//  public void run() throws SQLException, ClassNotFoundException {
//    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    ErrorHandler errorHandler = new ErrorHandler();
//
//    //not sure if I can do this
//    Database db = null;
//
//
//
//
//    while (true) { // parsing input loop
//      try {
//        String s = reader.readLine();
//        if (s == null) { // ctrl-D (exit) would result in null input
//          break;
//        }
//
//        // initialize a StringTokenizer to help parse the input, broken by space or tabs
//        StringTokenizer st = new StringTokenizer(s, " \t", false);
//        s = s.trim();
//        String[] inputs = s.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//
//
//        // if the input is not blank, get the first token (the command string)
//        if (st.hasMoreTokens()) {
//          String command = st.nextToken();
//
//
//          switch (command) {
//            case "user":  // reads in the sqlite file for ORM or API
//              String next_cmd;
//              if (st.hasMoreTokens()) {
//                next_cmd = st.nextToken();
//              } else {
//                next_cmd = "";
//              }
//              if (isPath(next_cmd)) {
//
//              }
//              break;
//            case "similar":
//              // TODO create a Handler that "prints out the user_ids of the most similar k users", either searching by user_id or
//              // weights, heights, and ages
//              break;
//            case "classify": {
////              String name;
////              if (st.hasMoreTokens()) {
////                name = st.nextToken();
////              } else {
////                name = "";
////              }
//              // TODO create a Handler to print out a horoscope comparison chart of the k most similar users [closest in euclidean distance of weights, heights, and ages]
//              //either by "similar k some_user_id" or "similar k weight height age"
//              break;
//            }
//            case "database":{
//              if (st.hasMoreTokens()) {
//                next_cmd = st.nextToken();
//              } else {
//                next_cmd = "";
//              }
//              if (isPath(next_cmd)) {
//                db = new Database(next_cmd);
//              }
//              else if (inputs[1].equals("sql")) {
//                if (db != null) {
//                  db.sql(inputs[2]);
//                }
//              }
//              System.out.println("ERROR: This file path does not exist.");
//            }
//            break;
//            case "insert":{
//             if (db != null) {
//               // if (db.getClass().getField("Conn").isOpen())
//               if (st.hasMoreTokens()) {
//                 User Mandy = new User(1, 130, "34b", "6'7", 20, "hourglass", "libra");
//                 db.insert(Mandy);
//                 next_cmd = st.nextToken();
//               } else {
//                 next_cmd = "";
//               }
//             }
//            }
//            break;
//            case "delete": {
//              if (db != null) {
//                if (st.hasMoreTokens()) {
//                  User Mandy = new User(1, 130, "34b", "6'7", 20, "hourglass", "libra");
//                  db.delete(Mandy);
//                  next_cmd = st.nextToken();
//                } else {
//                  next_cmd = "";
//                }
//              }
//            }
//            break;
//            case "select": {
//              if (db != null) {
//                db.where(inputs[1], inputs[2]);
//              }
//            }
//            break;
//            case "update": {
//              if (db != null) {
//                db.update(inputs[1], inputs[2], inputs[3]);
//              }
//            }
//            break;
//            default:  // command unrecognized
//              errorHandler.inputFormatException();
//              break;
//          }
//        }
//      } catch (IOException | IllegalAccessException | NoSuchMethodException e) { // some kind of read error, so the repl exits
//        errorHandler.parseInputException();
//        break;
//      }
//    }
//    try {
//      reader.close();
//    } catch (IOException e) {
//      errorHandler.closeReaderException();
//    }
//  }
}


