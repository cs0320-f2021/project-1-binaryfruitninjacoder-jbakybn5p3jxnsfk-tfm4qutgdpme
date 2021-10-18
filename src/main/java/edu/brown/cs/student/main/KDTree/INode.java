package edu.brown.cs.student.main.KDTree;

/**
 * A generic interface for a node in the KDTree, aiming to provide general methods of a tree node
 * & support multiple dimensions according to user discretion when user creates his/her own Node class
 */
public interface INode {

  /**
   * getting the coordinate at index dimension
   * @param index - coordinate
   */
  double getValue(int index);


  double getEuclideanDistance();

  /**
   * calculate the euclidean distance between the this node and the other node
   * @param node - the other node
   * @return distance in double
   */
  void setEuclideanDistance(INode node);

  /**
   * default toString method to be able to inspect node content
   * @return String representation of the Node
   */
  String toString();
}
