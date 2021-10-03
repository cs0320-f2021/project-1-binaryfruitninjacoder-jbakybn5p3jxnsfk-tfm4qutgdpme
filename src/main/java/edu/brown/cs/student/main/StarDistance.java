package edu.brown.cs.student.main;

public class StarDistance {
  /**
   * Default constructor
   */
  public StarDistance() {

  }

  /**
   * Method that calculates the Euclidean distance between two coordinates in 3D space
   *
   * @param coordinate1 - Double[3]
   * @param coordinate2 - Double[3]
   * @return distance in double
   */
  public double euclideanDistance(Double[] coordinate1, Double[] coordinate2) {
    return Math.sqrt(Math.pow(coordinate1[0] - coordinate2[0], 2)
        + Math.pow(coordinate1[1] - coordinate2[1], 2)
        + Math.pow(coordinate1[2] - coordinate2[2], 2));
  }

  /**
   * form a [x,y,z] coordinate from three numerical strings
   *
   * @param s1 - a numerical String representing X
   * @param s2 - a numerical String representing Y
   * @param s3 - a numerical String representing Z
   * @return Double[3] representing the coordinate [X,Y,Z]
   */
  public Double[] formCoordinate(String s1, String s2, String s3) {
    Double[] coordinate = new Double[3];
    double X = Double.parseDouble(s1);
    double Y = Double.parseDouble(s2);
    double Z = Double.parseDouble(s3);
    coordinate[0] = X;
    coordinate[1] = Y;
    coordinate[2] = Z;
    return coordinate;
  }

}
