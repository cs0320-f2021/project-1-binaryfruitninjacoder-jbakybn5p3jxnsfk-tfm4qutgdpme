package edu.brown.cs.student.main;

public class ThreeDimNode implements INode {

  private int dimensions;
  private double[] coordinates;
  public ThreeDimNode left;
  public ThreeDimNode right;

  public ThreeDimNode(double[] coordinates) {
    this.coordinates = coordinates;
  }

  public ThreeDimNode(double weight, double height, double age) {
    this(new double[]{weight, height, age});
  }

  @Override
  public double get(int index) {
    return coordinates[index];
  }

  @Override
  public double euclideanDistance(INode node) {
    double dist = 0.0;
    ThreeDimNode otherNode = (ThreeDimNode) node;
    for (int i = 0; i < coordinates.length; i++) {
      double d = coordinates[i] - otherNode.coordinates[i];
      dist += d * d;
    }
    return Math.sqrt(dist);
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
