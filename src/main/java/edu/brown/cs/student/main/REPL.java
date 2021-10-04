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
  HashMap<Integer, String> builtHoroscopeMap;


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
                builtHoroscopeMap = allUserData.getHoroscopeMap();
                //construct tree
              } catch (Exception e) {
                e.printStackTrace();
              }
              builtUserList.clear();
              builtUserMap.clear();
              builtIdMap.clear();
              builtHoroscopeMap.clear();
              //TODO: clear KD tree
              break;
            case "similar":
              LinkedList<String> arguments = new LinkedList<String>();
              while (st.hasMoreTokens()) { //if there are more commands after "similar"
                String cmd = st.nextToken(); //cmd1 is k
                arguments.add(cmd);
              }
                if (arguments.size() == 3 ) { //cmd0 is command, cmd1 is k, cmd2 is id
                  String id = arguments.get(2); //get argument id
                  Integer idInt = Integer.valueOf(id); //argument id to integer
                  List<INode> nearest = builtTree.findKNearest(builtIdMap.get(idInt)); //find nearest list of INodes
                  for (INode user : nearest) { //for every element in the list of INodes
                    System.out.println(builtUserMap.get(user)); //user the user to id map to print the id's
                  } }
                  if (arguments.size() == 5) { //0 is command, 1 is k, 2 is weight, 3 height, 4 age
                    String weight = arguments.get(2);
                    String height = arguments.get(3);
                    String age = arguments.get(4);
                    weight = weight.substring(0, weight.length() - 2);
                    double weightNum = Double.parseDouble(weight);
                    height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                    double heightNum = (Double.parseDouble(String.valueOf(height.charAt(0))) * 12) + Double.parseDouble(height.substring(1, height.length())); //not sure why i need valueof
                    double ageNum = Double.parseDouble(age);
                    ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum);
                    List<INode> nearestUsers = builtTree.findKNearest(userToCompareTo);
                    for (INode user : nearestUsers) { //for every element in the list of INodes
                      System.out.println(builtUserMap.get(user));
                    } }
                    else {
                    throw new RuntimeException("incorrect arguments");
                    }
                    builtUserList.clear();
                    builtUserMap.clear();
                    builtIdMap.clear();
                    builtHoroscopeMap.clear();
                    //TODO: clear KD tree

                  break;
                  case "classify":
                    LinkedList<String> argumentsC = new LinkedList<String>();
                    HashMap<String, Integer> horoscopeCount = new HashMap<String, Integer>(); //make an empty hashmap
                    horoscopeCount.put("Aries", 0); //populate with initial values
                    horoscopeCount.put("Taurus", 0);
                    horoscopeCount.put("Gemini", 0);
                    horoscopeCount.put("Cancer", 0);
                    horoscopeCount.put("Leo", 0);
                    horoscopeCount.put("Virgo", 0);
                    horoscopeCount.put("Libra", 0);
                    horoscopeCount.put("Scorpio", 0);
                    horoscopeCount.put("Sagittarius", 0);
                    horoscopeCount.put("Capricorn", 0);
                    horoscopeCount.put("Aquarius", 0);
                    horoscopeCount.put("Pisces", 0);
                    while (st.hasMoreTokens()) { //if there are more commands after "similar"
                      String argument = st.nextToken(); //cmd1 is k
                      argumentsC.add(argument); //add to list of arguments
                      if (argumentsC.size() == 3 ) { //if the commands after similar are k and user id
                        String id = argumentsC.get(2); //get argument id
                        Integer idInt = Integer.valueOf(id); //change argument id to integer
                        List<INode> nearest = builtTree.findKNearest(builtIdMap.get(idInt)); //find nearest list of INodes with the node that maps to id
                        for (INode user : nearest) { //for every element in the list of INodes
                          String horoscope = builtHoroscopeMap.get(builtUserMap.get(user)); //use the user id to find horoscope
                          Integer horoscopeCurrent = horoscopeCount.get(horoscope) + 1;
                          horoscopeCount.put(horoscope, horoscopeCurrent);
                        }
                        for(Map.Entry<String, Integer> horoscopeString : horoscopeCount.entrySet()) {
                          System.out.println(horoscopeString.getKey() + ":" + horoscopeString.getValue());
                        }
                      }
                      if (argumentsC.size() == 5) {
                        String weight = argumentsC.get(2);
                        String height = argumentsC.get(3);
                        String age = argumentsC.get(4);
                        weight = weight.substring(0, weight.length() - 2);
                        double weightNum = Double.parseDouble(weight);
                        height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                        double heightNum = (Double.parseDouble(String.valueOf(height.charAt(0))) * 12) + Double.parseDouble(height.substring(1, height.length())); //not sure why i need valueof
                        double ageNum = Double.parseDouble(age);
                        ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum);
                        List<INode> nearestUsers = builtTree.findKNearest(userToCompareTo);
                        for (INode user : nearestUsers) { //for every element in the list of INodes
                          String horoscope = builtHoroscopeMap.get(builtUserMap.get(user)); //use the user id to find horoscope
                          Integer horoscopeCurrent = horoscopeCount.get(horoscope) + 1;
                          horoscopeCount.put(horoscope, horoscopeCurrent);
                        }
                        for(Map.Entry<String, Integer> horoscopeString : horoscopeCount.entrySet()) {
                          System.out.println(horoscopeString.getKey() + ":" + horoscopeString.getValue());
                        }
                      }
                      }
                  //either by "similar k some_user_id" or "similar k weight height age"

                  break;

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


