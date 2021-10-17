package edu.brown.cs.student.main;

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class GenHandler {

    public static void handle(String command) {
        Map<Integer, Member> memberMap = AllMembers.getAllMembers(); //get hashmap of integers to members
        int memberAmount = memberMap.size();

        StringTokenizer st1 = new StringTokenizer(command, " \t", false);
        String[] commandArray;
        commandArray = command.split(" ");
        int groupNum = memberAmount / Integer.parseInt(commandArray[2]);

        int groupCounter = 1;
        for (Map.Entry<Integer, Member> entry : memberMap.entrySet()) {
            int keyToCheck = entry.getKey();
            String commandRecommend = "recommend" + String.valueOf(groupNum) + keyToCheck;
            String idList = RecommendHandler.handleUser(commandRecommend);
            StringTokenizer newTokenizer = new StringTokenizer(command, " \t", false);
            String[] idArray;
            idArray = idList.split(",");
            StringBuilder output = new StringBuilder();
            for(String id : idArray ){
                memberMap.remove(Integer.parseInt(id));
                String student =  "group_" + String.valueOf(groupCounter) + "_student" + String.valueOf(id) + ", ";
                output.append(student);
        }
                System.out.println(output);
        }


        //divide member list length by k
        //use member list to get first element to call recommendhandler
        //call recommendhandler with k-1 nearest neighbors
        //add these to a string
        //remove all users from the member list
        //iterate through the list
    }
}
//integer member