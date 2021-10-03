package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

/**
 * The REPL class for this lab.
 */
public class REPL {
//global variables to use after data from sqlite has been loaded in
  HashMap<String, Object> commands;
  KDTree builtTree;
  ArrayList<INode> builtUserList;
  HashMap<INode, Integer> builtUserMap;
  HashMap<Integer, INode> builtIdMap;


  /**
   * instantiates a REPL.
   *
   * @param commands - a map of string command names to IReplMethod objects, which
   *                 are a wrapper for a method to be called
   */
  // create a more generic interface for Handler
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
              try {
//                //  (for ORM) call a method that handles reading in sqlite files
                //TODO call construct tree
                Database allUserData = new Database(next_cmd);
                allUserData.populateUserListAndMap();
                builtUserList = allUserData.getUserList();
                builtTree =  new KDTree(builtUserList.size(), builtUserList);
                builtUserMap = allUserData.getUserMap();
                builtIdMap = allUserData.getIdMap();
                //construct tree
              } catch (Exception e) {
                e.printStackTrace();
              }
              break;
            case "similar":
              LinkedList<String> arguments = new LinkedList<String>();
              while (st.hasMoreTokens()) { //if there are more commands after "similar"
                String cmd = st.nextToken(); //cmd1 is k
                arguments.add(cmd);
              }
                if (arguments.size() == 2 ) { //cmd0 is command, cmd1 is k, cmd2 is id
                  String id = arguments.get(2); //get argument id
                  Integer idInt = Integer.valueOf(id); //argument id to integer
                  List<INode> nearest = builtTree.findKNearest(builtIdMap.get(idInt)); //find nearest list of INodes
                  for (INode user : nearest) { //for every element in the list of INodes
                    System.out.println(builtUserMap.get(user)); //user the user to id map to print the id's
                  }
                if (arguments.size() == 4) { //0 is command, 1 is k, 2 is weight, 3 height, 4 age
                  String weight = arguments.get(2);
                  String height = arguments.get(3);
                  String age = arguments.get(4);
                  weight = weight.substring(0, weight.length() - 2);
                  double weightNum = Double.parseDouble(weight);
                  height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                  double heightNum = (Double.parseDouble(String.valueOf(height.charAt(0))) * 12) + Double.parseDouble(height.substring(1, height.length())); //not sure why i need valueof
                  double ageNum = Double.parseDouble(age);
                  ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum);
                  List<INode> nearestUser = builtTree.findKNearest(userToCompareTo);
                  for (INode user : nearestUser) { //for every element in the list of INodes
                    System.out.println(builtUserMap.get(user));
                  }
                }
                  break;
       //         case "classify": {
        //          String name;
        //          if (st.hasMoreTokens()) {
         //           name = st.nextToken();
         //         } else {
         //           name = "";
          //        }
                  // TODO create a Handler to print out a horoscope comparison chart of the k most similar users [closest in euclidean distance of weights, heights, and ages]
                  //either by "similar k some_user_id" or "similar k weight height age"

                 // break;
                }
                default:  // command unrecognized
                  errorHandler.inputFormatException();
                  break;
              }
          }
        } catch(IOException e){ // some kind of read error, so the repl exits
          errorHandler.parseInputException();
          break;
        }

    try {
      reader.close();
    } catch (IOException e) {
      errorHandler.closeReaderException();
    }
  }
}}


