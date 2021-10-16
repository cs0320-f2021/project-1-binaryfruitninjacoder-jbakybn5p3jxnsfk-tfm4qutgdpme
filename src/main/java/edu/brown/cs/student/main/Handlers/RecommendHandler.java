package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.teammates.AllMembers;
import edu.brown.cs.student.main.teammates.Member;

import java.util.Map;
import java.util.StringTokenizer;

public class RecommendHandler {
  public static String handle(String command) {
    StringTokenizer st = new StringTokenizer(command, " \t", false);
    String[] commandArray;
    commandArray = command.split(" ");

    if (commandArray.length == 3) {
      int numRecs = Integer.parseInt(commandArray[1]);
      Integer studentId = Integer.parseInt(commandArray[2]);
      int top2K = numRecs * 2;
      // 1. we use the KD tree recommender to pick top 4k KNN, in which 2K are "most similar" to the queried use
      // another 2K are the "most dissimilar" to the queried user (reverse numerical value using 10 - rating)
      Map<Integer, Member> allMembers = AllMembers.getAllMembers();

    }
    else if (commandArray.length == 2) {

    }
    return "";
  }
}
