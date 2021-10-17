package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

/**
 * The REPL class for this lab.
 */
public class REPL {
//global variables to use after data from sqlite has been loaded in
  HashMap<String, Consumer<String>> commands; //hashmap that stores commands from input
  Database loadedData;

  /**
   * instantiates a REPL.
   *
   * @param commands - a map of string command names to IReplMethod objects, which
   *                 are a wrapper for a method to be called
   */
  // create a more generic interface for Handler
  public REPL(HashMap<String, Consumer<String>> commands) {
    this.commands = commands;
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
            commands.get(args[0]).accept(args[0]);
          } else {
            System.out.println("Error: command not recognized");
          }
        } catch (Exception e) {
          System.out.println("Error: input not correct");
        }
        //this.repl.commands.put(input, new ClassifyHandler(input, new Database(input)));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }}

//  /**
//   * This run method for the REPL requires an ApiClient object.
//   *
//   */
//                       public void run() throws SQLException, IOException, ClassNotFoundException {
//    try {
//      //Iterator replIterator = this.commands.entrySet().iterator();
//
//      //while (replIterator.hasNext()) {
//      //  Map.Entry commandElement = (Map.Entry)replIterator.next();
//      //commandElement.get('(').run();
//     // }
//
////      for (Map.Entry<String, Consumer<String>> set : this.commands.entrySet()) {
////        //set.getValue();
////        set.getValue.get('(').run();
////      }
//      for (Map.Entry<String, Consumer<String>> mapElement : this.commands.entrySet()) {
//        String key = mapElement.getKey();
//        mapElement.getValue().accept(key);
//      }
//    }
//    catch (Exception e) {
//      System.out.println(
//              "ERROR: Command not recognized");
//    }
//  }
//}


