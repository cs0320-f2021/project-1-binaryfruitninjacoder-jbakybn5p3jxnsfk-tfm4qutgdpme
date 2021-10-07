package edu.brown.cs.student.main.Stars;
import java.util.*;
import java.util.Collections;


public class Star {
  String properName;
  double x;
  double y;
  double z;
  int id;


  /**
   * Constructor for star
   */
  public Star(String name, double _x, double _y, double _z, int _id){
    properName = name;
    x = _x;
    y = _y;
    z = _z;
    id = _id;
  }

  /**
   * method that returns the id of the star
   * @return id of star
   */
  public int getId(){
    return id;
  }

  /**
   * method that returns the x coordinate of the star
   * @return x coordinate of star
   */
  public double getX() {
    return x;
  }

  /**
   * method that returns the y coordinate of the star
   * @return y coordinate of star
   */
  public double getY() {
    return y;
  }

  /**
   * method that returns the z coordinate of the star
   * @return z coordinate of star
   */
  public double getZ() {
    return z;
  }


  /**
   * method that returns the name of the star
   * @return name of star
   */
  public String getName(){
    return properName;
  }


  /**
   * method that calculates the distance between this.star by accessing the coordinate
   * of this.star and the given coordinate
   * @param an array of doubles representing the coordinates of a location
   * @return a double representing the distance between this star and the given coordinate
   */
  public double getDist(Double[] coordinates){
    double result = 0.0;
    double squarex = Math.pow((this.x - coordinates[0]), 2);
    double squarey = Math.pow((this.y - coordinates[1]), 2);
    double squarez = Math.pow((this.z - coordinates[2]), 2);
    result = Math.sqrt(squarex + squarey + squarez);
    return result;
  }

}