package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import java.util.*;
import java.util.Collections;
import java.io.*;
import java.lang.*;
import java.util.Arrays;


public class StarHandler {

  /**
   * constructor for starHandler
   */
  public StarHandler() {

  }

  HashMap<Integer, Star> starMap = new HashMap<>();
  Double[] coordinates = new Double[3];
  ArrayList<Star> valueList = new ArrayList(starMap.values());

  /**
   * method that parses the given CSV file and stores data into a hashmap containing the star ID
   * and the star object
   * @param filename path to the CSV file
   */
  public void stars (String filename) {
    starMap = new HashMap<>();
    try (BufferedReader br2 = new BufferedReader(new FileReader(filename))) {
      String line = br2.readLine();
      while ((line = br2.readLine()) != null) {
        String[] values = line.split(",");
        Star star = new Star(values[1], Double.parseDouble(values[2]),
            Double.parseDouble(values[3]), Double.parseDouble(values[4]), Integer.parseInt(values[0]));
        starMap.put(Integer.parseInt(values[0]), star);
      }
      System.out.println("Read " + starMap.size() + " stars from " + filename);
      valueList = new ArrayList(starMap.values());
      if (starMap.isEmpty()){
        System.out.println("ERROR: no file loading");
      }
      //check if the starMap is storing stars
//      for(Star value:valueList) {
//        System.out.println("a star!");
//      }
    }
    catch (Exception e){
      System.out.println("ERROR:File not found" + e);
    }
  }

  /**
   * Class that sorts the stars by their distances
   */
  private class StarComparatorByDist implements Comparator<Star> {
    public int compare(Star a, Star b){
      return Double.compare(a.getDist(coordinates), b.getDist(coordinates));
    }
  }

  /**
   * method that prints out k numbers of star ids that are neighbours with a location with base our
   * search on
   * @param k Integer representing the number of stars we want to search for
   * @param x Double representing the x coordinate of the location we base our search on
   * @param y Double representing the y coordinate of the location we base our search on
   * @param z Double representing the z coordinate of the location we base our search on
   * @param id Integer representing the id of the star that corresponds to the coordinate of the
   *           give location, if the given location is the location of a star in the star data
   *           Would be -1 if the given location isn't the location of a star in the star data
   */
  public void naive_neighbors (int k, double x, double y, double z, int id) {
    coordinates[0] = x;
    coordinates[1] = y;
    coordinates[2] = z;
    if (k != 0) {
      Collections.sort(valueList, new StarComparatorByDist());
      // check if anything is put into the valueList
      // System.out.println(valueList.toString());
      if (id != -1){
        k = k+1;
      }
      if(k > valueList.size()){
        k = valueList.size();
      }
      //check if k is greater than £, if it is, set k to num of stars
      if (!valueList.isEmpty()) {
        for (int i = 0; i < k; i++) {
          //System.out.println("id of the lonely star is " + id);
          //find the star id of star at xyz, and don't print it
          if (((valueList.get(i)).getId()) != id) {
            System.out.println((valueList.get(i)).getId());
          }
        }
      } else {
        System.out.println("ERROR:");
      }
    }
  }


  /**
   * method that prints out k numbers of star ids that are neighbours with a star
   * @param k Integer representing the number of stars we want to search for
   * @param name String representing the name of the star we are basing our search on
   */
  public void naive_neighborsByName(int k, String name){
    double X = 0.0;
    double Y = 0.0;
    double Z = 0.0;
    int ID = -1;
    for (Star star : starMap.values()) {
      if ((star.getName()).equals(name)) {
        X = star.getX();
        Y = star.getY();
        Z = star.getZ();
        ID = star.getId();
      }
    }
    naive_neighbors(k, X, Y, Z, ID);
  }
  }