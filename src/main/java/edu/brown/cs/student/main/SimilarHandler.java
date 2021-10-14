package edu.brown.cs.student.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public final class SimilarHandler {
    private static String command = "";
    private static Database data;


//    public SimilarHandler(String command, Database loadedData) {
//        this.command = command;
//       this.data = loadedData;
//    }
    private SimilarHandler() {
    }

//String
    public static String handle(String command) throws IOException, SQLException, ClassNotFoundException {
        String s = command;
        // initialize a StringTokenizer to help parse the input, broken by space or tabs
        StringTokenizer st = new StringTokenizer(s, " \t", false);
        String[] commandArray;
        commandArray = s.split(" ");
        if (commandArray.length == 3) { //cmd0 is command, cmd1 is k, cmd2 is id
            String id = commandArray[2]; //get argument id
            Integer idInt = Integer.valueOf(id); //argument id to integer
            List<INode> nearest = data.builtTree.findKNearest(data.idMap.get(idInt)); //find nearest list of INodes
            //with idInt that maps to a node
            for (INode user : nearest) { //for every user(INode) in the list of INodes
                System.out.println(data.userMap.get(user)); //use the user to id map to print the id's
            }
        }
        if (commandArray.length == 5) { //0 is command, 1 is k, 2 is weight, 3 height, 4 age
            String weight = commandArray[2];
            String height = commandArray[3];
            String age = commandArray[4];
            weight = weight.substring(0, weight.length() - 3); //eliminate "lbs"
            double weightNum = Double.parseDouble(weight); //change weight String to Double
            height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
            double heightNum = ((Double.parseDouble(height.substring(0, 1))) * 12) + Double.parseDouble(height.substring(1));
            double ageNum = Double.parseDouble(age); //age string to int
            ThreeDimNode userToCompareTo = new ThreeDimNode(weightNum, heightNum, ageNum); //create new user with correctly formatted numbers
            List<INode> nearestUsers = data.builtTree.findKNearest(userToCompareTo); //use built tree to findKNearest
            for (INode user : nearestUsers) { //for every element in the returned list of INodes
                System.out.println(data.userMap.get(user));
            }
        } else {
            throw new RuntimeException("incorrect arguments");
        }
        return "";
    }
}
