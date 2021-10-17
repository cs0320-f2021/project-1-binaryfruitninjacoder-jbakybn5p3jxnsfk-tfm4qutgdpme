package edu.brown.cs.student.main;

import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.coordinates.Coordinate;
import edu.brown.cs.student.main.coordinates.KdTree;
import edu.brown.cs.student.main.searchAlgorithms.KdTreeSearch;
import edu.brown.cs.student.main.teammates.AllMembers;
import edu.brown.cs.student.main.teammates.Member;
import edu.brown.cs.student.main.teammates.Teammate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class RecommendHandler {
    public static String handleUser(String command) {
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
            List<Coordinate<Integer>> teammateList = new ArrayList<>(allMembers.size());
            Teammate student = new Teammate(allMembers.get(studentId));
            for (Member member : allMembers.values()) {
                teammateList.add(new Teammate(member));
            }
            // construct a KD Tree with teammateList
            KdTree<Integer> kdTree = new KdTree<>(student.getDimension(), teammateList);
            KdTreeSearch<Integer> knnSearch = new KdTreeSearch<>();
            List<Coordinate<Integer>> top2KNN = knnSearch.getNearestNeighborsResult(top2K, student, kdTree.getRoot(), true);
            List<Integer> idList = new ArrayList<>();
            for (Coordinate<Integer> neighbor : top2KNN) {
                idList.add(neighbor.getId());
            }
            return idList.toString();
        }
        else {
            return ErrorHandler.inputFormatException();
        }
    }

    public static String handleGroup(String command) {
        StringTokenizer st = new StringTokenizer(command, " \t", false);
        String[] commandArray;
        commandArray = command.split(" ");

        if (commandArray.length == 2) {

        }
        return "";
    }
}