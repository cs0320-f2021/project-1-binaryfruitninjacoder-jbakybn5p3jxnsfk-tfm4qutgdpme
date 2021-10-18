package edu.brown.cs.student.main.teammates;

public class Member {
  private int _id;
  private String name;
  private int commenting;
  private int testing;
  private int OOP;
  private int algorithms;
  private int teamwork;
  private int frontend;
  private String negative;

  private String positive;
  private String interests;
  private String meeting;
  private String grade;
  private int years_of_experience;
  private String horoscope;
  private String meeting_times;
  private String preferred_language;
  private String marginalized_groups;
  private String prefer_group;

  public Member(int _id, String name, int commenting, int testing, int OOP, int algorithms, int teamwork, int frontend,
                String negative, String positive, String interests, String meeting, String grade, int years_of_experience,
                String horoscope, String meeting_times, String preferred_language, String marginalized_groups, String prefer_group){
    this._id = _id;
    this.name = name;
    this.commenting = commenting;
    this.testing = testing;
    this.OOP = OOP;
    this.algorithms = algorithms;
    this.teamwork = teamwork;
    this.frontend = frontend;
    this.negative = negative;
    this.positive = positive;
    this.interests = interests;
    this.meeting = meeting;
    this.grade = grade;
    this.years_of_experience = years_of_experience;
    this.horoscope = horoscope;
    this.meeting_times = meeting_times;
    this.preferred_language = preferred_language;
    this.marginalized_groups = marginalized_groups;
    this.prefer_group = prefer_group;
  }

  /**
   * A multitude of getters for the params we need to recommend users
   * params we pass into the KD tree and Bloom filter as params are:
   * KD Tree: years of experience, commenting, testing, OOP, algorithms, frontend
   * Bloom filter: meeting, interests
   */
  public int getId() {
    return this._id;
  }

  public String getName() {
    return this.name;
  }

  public int getCommenting() {
    return this.commenting;
  }

  public int getTesting() {
    return this.testing;
  }

  public int getOOP() {
    return this.OOP;
  }

  public int getAlgorithms() {
    return this.algorithms;
  }

  public int frontend() {
    return this.frontend;
  }

  public String getMeeting() {
    return this.meeting;
  }

  public int getExperience() {
    return this.years_of_experience;
  }

  public String getInterests() {
    return this.interests;
  }

}
