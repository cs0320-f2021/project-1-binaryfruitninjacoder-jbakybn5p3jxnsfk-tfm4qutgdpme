package edu.brown.cs.student.main;

import java.util.Comparator;

public class ThreeDimNode implements INode {

  public double[] coordinates;
  public ThreeDimNode left;
  public ThreeDimNode right;
  private double euclideanDistanceAtt;
  // lambda notation for implement method for threedimnode class
  // .thenCompare()
  public Comparator<ThreeDimNode> byEuclideanDistance = Comparator.comparing((ThreeDimNode n) -> n.euclideanDistanceAtt);

  public ThreeDimNode(double[] coordinates) {
    this.coordinates = coordinates;
  }

  public ThreeDimNode(double weight, double height, double age) {
    this(new double[]{weight, height, age});
  }

  @Override
  public double getValue(int index) {
    return coordinates[index];
  }

  public double[] getCoordinates() {
    return this.coordinates;
  }

  // USING COMPARATOR, defined on an object not a class -->
  @Override
  public double getEuclideanDistance() {
    return euclideanDistanceAtt;
  }

  public void setEuclideanDistance(INode node) {
    double dist = 0.0;
    ThreeDimNode otherNode = (ThreeDimNode) node;
    for (int i = 0; i < coordinates.length; i++) {
      double d = coordinates[i] - otherNode.coordinates[i];
      dist += d * d;
    }
    euclideanDistanceAtt = Math.sqrt(dist);
  }


  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    for (int i = 0; i < coordinates.length; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(coordinates[i]);
    }
    sb.append(")");
    return sb.toString();
  }
}
