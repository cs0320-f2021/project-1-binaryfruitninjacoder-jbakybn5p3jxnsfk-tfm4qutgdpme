package edu.brown.cs.student.main.teammates;

import edu.brown.cs.student.main.coordinates.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Teammate implements Coordinate<Integer> {
  private final Integer id;
  private final String name;
  private final int dim;
  private final List<Double> metrics;

  public Teammate(Member member) {
    List<Double> allCoords = new ArrayList<>();
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
  }


  /**
   * Get the coordinate value at the dimension requested.
   *
   * @param dim the dimension number, from 1 to n where n is a positive integer.
   * @return a Double value, any real number.
   */
  @Override
  public Double getCoordinateVal(int dim) {
    return this.metrics.get(dim);
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
    return this.metrics;
  }
}
