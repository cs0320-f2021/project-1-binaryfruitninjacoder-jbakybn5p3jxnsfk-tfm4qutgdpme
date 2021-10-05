package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The REPL class for this lab.
 */
public class REPL {
//global variables to use after data from sqlite has been loaded in
  HashMap<String, Object> commands; //hashmap that stores commands from input
  KDTree builtTree; //Stores the built tree as a global variable after users is inputted
  ArrayList<INode> builtUserList; //creates a list of INodes
  HashMap<INode, Integer> builtUserMap; //hashmap maps users to their IDs
  HashMap<Integer, INode> builtIdMap; //hashmap maps IDs to users
  HashMap<Integer, String> builtHoroscopeMap; //hashmap maps IDs to the horoscope


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
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //create reader
    ErrorHandler errorHandler = new ErrorHandler(); //create errorHandler object

    while (true) { // parsing input loop
      try {
        String s = reader.readLine();
        if (s == null) { // ctrl-D (exit) would result in null input
          break;
        }

        // initialize a StringTokenizer to help parse the input, broken by space or tabs
        StringTokenizer st = new StringTokenizer(s, " \t", false);
        String[] commandArray;
        commandArray = s.split(" ");

        // if the input is not blank, get the first token (the command string)
        if (st.hasMoreTokens()) {
          String command = st.nextToken();

          switch (command) {
            case "user":  // reads in the sqlite file for ORM or API
              //if (next_cmd.equals("online")) {
              // TODO (For API ppl) do something that's related to the API pursuers, please fill this part in
              // }
              try {
//                //  (for ORM) call a method that handles reading in sqlite files
                Database allUserData = new Database(commandArray[1]);
                allUserData.populateUserListAndMap(); //call method to populate hashMaps
                builtUserList = allUserData.getUserList(); //use getters to populate hashMaps in repl
                builtTree = new KDTree(builtUserList.size(), builtUserList);
                builtUserMap = allUserData.getUserMap();
                builtIdMap = allUserData.getIdMap();
                builtHoroscopeMap = allUserData.getHoroscopeMap();
              } catch (Exception e) {
                e.printStackTrace();
              }
              break;
            case "similar":
              if (commandArray.length == 3) { //cmd0 is command, cmd1 is k, cmd2 is id
                String id = commandArray[2]; //get argument id
                Integer idInt = Integer.valueOf(id); //argument id to integer
                List<INode> nearest = builtTree.findKNearest(builtIdMap.get(idInt)); //find nearest list of INodes
                //with idInt that maps to a node
                for (INode user : nearest) { //for every user(INode) in the list of INodes
                  System.out.println(builtUserMap.get(user)); //use the user to id map to print the id's
                }
              }
              if (commandArray.length == 5) { //0 is command, 1 is k, 2 is weight, 3 height, 4 age
                String weight = commandArray[2];
                String height = commandArray[3];
                String age = commandArray[4];
                weight = weight.substring(0, weight.length() - 3); //eliminate "lbs"
                double weightNum = Double.parseDouble(weight); //change weight String to Double
                height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                double heightNum = ((Double.parseDouble(height.substring(0,1))) * 12) + Double.parseDouble(height.substring(1));
                double ageNum = Double.parseDouble(age); //age string to int
                ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum); //create new user with correctly formatted numbers
                List<INode> nearestUsers = builtTree.findKNearest(userToCompareTo); //use built tree to findKNearest
                for (INode user : nearestUsers) { //for every element in the returned list of INodes
                  System.out.println(builtUserMap.get(user));
                }
              } else {
                throw new RuntimeException("incorrect arguments");
              }
              break;
            case "classify":
              HashMap<String, Integer> horoscopeCount = new HashMap<String, Integer>(); //make an empty hashmap
              horoscopeCount.put("Aries", 0); //populate with initial horoscope values
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
              if (commandArray.length == 3) { //if the commands after similar are k and user id
                String id = commandArray[2]; //get argument id
                Integer idInt = Integer.valueOf(id); //change argument id to integer
                List<INode> nearest = builtTree.findKNearest(builtIdMap.get(idInt)); //find nearest list of INodes with the node that maps to id
                for (INode user : nearest) { //for every element in the list of INodes
                  String horoscope = builtHoroscopeMap.get(builtUserMap.get(user)); //use the user id to find horoscope
                  Integer horoscopeCurrent = horoscopeCount.get(horoscope) + 1; //add one to the horoscope of the user
                  horoscopeCount.put(horoscope, horoscopeCurrent);  //update value
                }
                for (Map.Entry<String, Integer> horoscopeString : horoscopeCount.entrySet()) { //for every kv pair in the horoscope map
                  System.out.println(horoscopeString.getKey() + ":" + horoscopeString.getValue()); //print the key and value
                }
              }
              if (commandArray.length == 5) {
                String weight = commandArray[2];
                String height = commandArray[3];
                String age = commandArray[4];
                weight = weight.substring(0, weight.length() - 3); //eliminate "lbs"
                double weightNum = Double.parseDouble(weight); //change weight String to Double
                height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                double heightNum = ((Double.parseDouble(height.substring(0,1))) * 12) + Double.parseDouble(height.substring(1));
                double ageNum = Double.parseDouble(age); //age string to int
                ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum); //create new user with correctly formatted numbers
                List<INode> nearestUsers = builtTree.findKNearest(userToCompareTo);
                for (INode user : nearestUsers) { //for every element in the list of INodes
                  String horoscope = builtHoroscopeMap.get(builtUserMap.get(user)); //use the user id to find horoscope
                  Integer horoscopeCurrent = horoscopeCount.get(horoscope) + 1;
                  horoscopeCount.put(horoscope, horoscopeCurrent);
                }
                for (Map.Entry<String, Integer> horoscopeString : horoscopeCount.entrySet()) {
                  System.out.println(horoscopeString.getKey() + ":" + horoscopeString.getValue());
                }
              }
              break;
            default:  // command unrecognized
              errorHandler.inputFormatException();
              break;
          }
        }
        }catch(IOException e){ // some kind of read error, so the repl exits
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


