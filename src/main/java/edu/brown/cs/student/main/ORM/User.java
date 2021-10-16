package edu.brown.cs.student.main.ORM;


import org.checkerframework.checker.regex.qual.Regex;

public class User {
  private int user_id;
  private double weight;
  private String bust_size;
  private String height;
  private int age;
  private String body_type;
  private String horoscope;


  /**
   * Constructor for User
   */

  public User(int id, double weight, String bust_size, String height, int age, String body_type,
               String horoscope){
    this.user_id = id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

  /**
   * create all the getters!
   * @return
   */
  public int getId() {
    return this.user_id;
  }

  public double getWeight() {
    return this.weight;
  }


  public double getHeight() {
    // "6' 9""
    double a = 0.3048;
    double b = 0.0254;
    String[] metrics = this.height.split("\\D+");
    return Integer.parseInt(metrics[0]) * a + Integer.parseInt(metrics[1]) * b;
  }

  public double getAge() {
    return this.age;
  }

  public String getBust() {
    return this.bust_size;
  }

  public String getHoroscope() {
    return this.horoscope;
  }

  public String getBody_type() {
    return this.body_type;
  }

  @Override
  public boolean equals(Object user) {

    // If the object is compared with itself then return true
    if (user == this) {
      return true;
    }
    /* Check if user is an instance of Complex or not
     "null instanceof [type]" also returns false */
    if (!(user instanceof User)) {
      return false;
    }

    // typecast user to User so that we can compare data members
    User c = (User) user;

    // Compare the data members and return accordingly
    return this.getId() == c.getId() && this.getWeight() == c.getWeight() &&
        this.getBust().equals(c.getBust()) &&
        this.getHeight() == c.getHeight() && this.getAge() == c.getAge();
  }
}