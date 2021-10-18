package edu.brown.cs.student.main.teammates;

import edu.brown.cs.student.main.coordinates.Coordinate;
import edu.brown.cs.student.main.recommender.Item;

import java.util.ArrayList;
import java.util.List;

public class Teammate implements Coordinate<Integer>, Item {
  private final Integer id;
  private final String name;
  private final int dim;
  private final List<Integer> metrics;
  private final List<Integer> reversedMetrics;

  public Teammate(Member member) {
    List<Integer> allCoords = new ArrayList<>();
    // the coordinates array would have these dimensions in the order of:
    // yrs_Experience, commenting, algo,
    allCoords.add(member.getExperience());
    allCoords.add(member.getCommenting());
    allCoords.add(member.getAlgorithms());
    allCoords.add(member.getOOP());
    allCoords.add(member.frontend());
    allCoords.add(member.getTesting());
    this.metrics = allCoords;
    this.id = member.getId();
    this.name = member.getName();
    this.dim = allCoords.size();

    List<Integer> allCoordsRev = new ArrayList<>();
    allCoordsRev.add(member.getExperience());
    allCoordsRev.add(10 - member.getCommenting());
    allCoordsRev.add(10 - member.getAlgorithms());
    allCoordsRev.add(10 - member.getOOP());
    allCoordsRev.add(10 - member.frontend());
    allCoordsRev.add(10 - member.getTesting());
    this.reversedMetrics = allCoordsRev;
  }


  /**
   * Get the coordinate value at the dimension requested.
   *
   * @param dim the dimension number, from 1 to n where n is a positive integer.
   * @return a Double value, any real number.
   */
  @Override
  public Double getCoordinateVal(int dim) {
    return (double) this.metrics.get(dim);
  }

  public Double getCoordinateInverseVal(int dim) {
    return 10.0 - this.metrics.get(dim);
  }

  public int getIntCoordinateVal(int dim) {
    return this.metrics.get(dim);
  }

  @Override
  public List<String> getVectorRepresentation() {
    return null;
  }

  /**
   * Get the ID.
   *
   * @return id of type with which the Coordinate was created.
   */
  @Override
  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Integer getDimension() {
    return this.dim;
  }

  /**
   * Get all coordinate values of the Coordinate.
   *
   * @return a List of Double; i.e., a list of as many real numbers as there are dimensions.
   */
  @Override
  public List<Double> getCoordinates() {
    return null;
  }

  public List<Integer> getIntCoordinates() {
    return this.metrics;
  }

  public List<Integer> getIntRevCoordinates() {
    return this.reversedMetrics;
  }
}
