package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.KDTree.INode;
import edu.brown.cs.student.main.KDTree.ThreeDimNode;
import edu.brown.cs.student.main.ORM.Database;

import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassifyHandler {
  private String command = "";
  private Database data;


  public ClassifyHandler(String command, Database loadedData) {
    this.command = command;
    this.data = loadedData;
  }

  public static void handle() {
    String s = command;
    // initialize a StringTokenizer to help parse the input, broken by space or tabs
    StringTokenizer st = new StringTokenizer(s, " \t", false);
    String[] commandArray;
    commandArray = s.split(" ");
    HashMap<String, Integer> horoscopeCount = new HashMap<>(); //make an empty hashmap
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
      List<INode> nearest = this.data.builtTree.findKNearest(this.data.idMap.get(idInt)); //find nearest list of INodes with the node that maps to id
      for (INode user : nearest) { //for every element in the list of INodes
        String horoscope = this.data.horoscopeMap.get(this.data.userMap.get(user)); //use the user id to find horoscope
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
      List<INode> nearestUsers = this.data.builtTree.findKNearest(userToCompareTo);
      for (INode user : nearestUsers) { //for every element in the list of INodes
        String horoscope = this.data.horoscopeMap.get(this.data.userMap.get(user)); //use the user id to find horoscope
        Integer horoscopeCurrent = horoscopeCount.get(horoscope) + 1;
        horoscopeCount.put(horoscope, horoscopeCurrent);
      }
      for (Map.Entry<String, Integer> horoscopeString : horoscopeCount.entrySet()) {
        System.out.println(horoscopeString.getKey() + ":" + horoscopeString.getValue());
      }
    }
  }

  public static String handle(String s) {
    return "";
  }
}
