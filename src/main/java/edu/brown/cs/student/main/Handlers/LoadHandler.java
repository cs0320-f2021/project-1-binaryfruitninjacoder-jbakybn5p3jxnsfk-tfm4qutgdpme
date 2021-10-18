package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.ORM.User;
import edu.brown.cs.student.main.teammates.AllMembers;
import edu.brown.cs.student.main.teammates.Member;

import java.util.List;

public class LoadHandler {
  public static String handle(String command) {
    //select everyone from ORM, make them into objects
    //not sure how to integrate ORM and API data (do we just create a giant list of users?
    List<User> userList = Database.sql("SELECT * FROM inventory");
    for (int i = 0; i < userList.size(); i++){
      User u = userList.get(i);
      //populate the member class
      Member m = new Member(...);
      AllMembers.addMember(i, m);
    }

    //select every data from API
    //  ApiAggregator.getData("interests");
    return "Loaded Recommender with " + userList.size() + " students";
  }
  //returns a list of objects


  //loop through this list of objects

//    public Member(int _id, String name, int commenting, int testing, int OOP, int algorithms, int teamwork, int frontend,
//                  String negative, String positive, String interests, String meeting, String grade, int years_of_experience,
//                  String horoscope, String meeting_times, String preferred_language, String marginalized_groups, String prefer_group)


  //method that creates a single member using Interests, Negative, Positive, Skills, User


  //load in sql and api data to AllMembers class
}
