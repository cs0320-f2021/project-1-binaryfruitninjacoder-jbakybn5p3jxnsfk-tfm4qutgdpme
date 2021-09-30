package edu.brown.cs.student.main;
import java.util.*;
import java.util.Collections;


public class User {
  int user_id;
  int weight;
  String bust_size;
  String height;
  int age;
  String body_type;
  String horoscope;


  /**
   * Constructor for User
   */

  public User(int id, int weight, String bust_size, String height, int age, String body_type,
               String horoscope){
    this.user_id = id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

}