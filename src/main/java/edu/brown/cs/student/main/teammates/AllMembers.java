package edu.brown.cs.student.main.teammates;

import java.util.Map;

public class AllMembers {
  // Mapping student id to a Member object
  private static Map<Integer, Member> AllMembers;

  public static void addMember(Integer id, Member member) {
    AllMembers.put(id, member);
  }
  public static Map<Integer, Member> getAllMembers() {
    return AllMembers;
  }
}

