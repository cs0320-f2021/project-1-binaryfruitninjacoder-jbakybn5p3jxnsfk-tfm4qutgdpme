package edu.brown.cs.student.main.KDTree;

import com.google.common.collect.MinMaxPriorityQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KDTree<INode> implements IKDTree {
  // idea: when storing the user information, first store them in List<User>, when construct
  // a balanced KDTree
  // alternative approach KDTree interface with node without generic interface for node


  private int dimensions;
  private INode root;
  private MinMaxPriorityQueue<INode> boundedPriorityQueue;

  public KDTree(int dimensions, List<INode> nodes) {
    this.dimensions = dimensions;
    this.root = constructTree(nodes, 0, nodes.size(), 0);
  }

  private void initializeBoundedPQ(int k) {
    boundedPriorityQueue = MinMaxPriorityQueue.orderedBy(new DistanceComparator()).maximumSize(k).create();
  }

  private class DistanceComparator implements Comparator<INode> {

    @Override
    public int compare(INode o1, INode o2) {
      ThreeDimNode n1 = (ThreeDimNode) o1;
      ThreeDimNode n2 = (ThreeDimNode) o2;
      if (n1.getEuclideanDistance() > n2.getEuclideanDistance()) return 1;
      else if (n1.getEuclideanDistance() < n2.getEuclideanDistance()) return -1;
      return 0;
    }
  }

  private INode constructTree(List<INode> nodes, int start, int end, int index) {
    if (end <= start) {
      return null;
    }
    int mid = start + (end - start) / 2;
    INode node = QuickSelect.select(nodes, start, end - 1, mid, new NodeComparator(index));
    index = (index + 1) % dimensions;
    ((ThreeDimNode) node).left = (ThreeDimNode) constructTree(nodes, start, mid, index);
    ((ThreeDimNode) node).right = (ThreeDimNode) constructTree(nodes, mid + 1, end, index);
    return node;
  }

  /** check contains
   * Given a point P, start at the root of the tree. If the root node is P, return the root
   * node. If the first component of P is strictly less than the first component of the root node, then look
   * for P in the left subtree, this time comparing the second component of P. Otherwise, then the first
   * component of P is at least as large as the first component of the root node, and we descend into the
   * right subtree and next time compare the second component of P. We continue this process, cycling
   * through which component is considered at each step, until we fall off the tree or find the node in
   * question. Inserting into a kd-tree is similarly analogous to inserting into a regular BST
   */
  /**
   * check if KDTree contains a certain node
   *
   * @param node
   * @return
   */
  public boolean contains(ThreeDimNode node, ThreeDimNode target, int index) {
    // tree traversal and check contains
    if (Arrays.equals(node.getCoordinates(), target.getCoordinates())) {
      return true;
    }
    index = (index + 1) % dimensions;
    if (node.getCoordinates()[index] > target.getCoordinates()[index]) {
      return contains(node.left, target, index);
    }
    else if (node.getCoordinates()[index] <= target.getCoordinates()[index]) {
      return contains(node.right, target, index);
    }
    return false;
  }

  /**
   * terms: candidate hypersphere
   *
   * branch-and-bound: searching a large space and pruning options based on partial results
   */

  /**
   * Some pseudocode:
   * Let the test point be P = (y0, y1, ..., yk).
   Maintain a BPQ of the candidate nearest neighbors, called 'bpq'
   Set the maximum size of 'bpq' to k
   Starting at the root, execute the following procedure:
   if curr == NULL
   return

   /* Add the current point to the BPQ. Note that this is a no-op if the
   * point is not as good as the points we've seen so far.

   enqueue curr into bpq with priority euclideandistance(curr, P)
   /* Recursively search the half of the tree that contains the test point.
   if yi < curri
   recursively search the left subtree on the next axis
   else
   recursively search the right subtree on the next axis
   /* If the candidate hypersphere crosses this splitting plane, look on the
   * other side of the plane by examining the other subtree.

   if:
   bpq isn't full
   -or-
   |curri – yi| is less than the priority of the max-priority elem of bpq
   then
   recursively search the other subtree on the next axis
   */

  /**
   * @param target
   */
  public void KNNSearch(INode root, INode target, int k, int index) {
    // maintain a BPQ of the candidate's nearest k neighbors

    ThreeDimNode curr = (ThreeDimNode) root;
    // starting at the root, execute the following pattern
    if (curr == null) return;
    // add curr node to bpq, note that no-operation is proceeded if the curr node is not as good as the
    // node we've found so far, as measured by distance
    // cast :( target to ThreeDimNode in this case to set the euclidean distance between 3 nodes
    curr.setEuclideanDistance((ThreeDimNode) target);
    double distance = curr.getEuclideanDistance();
    boundedPriorityQueue.add((INode) curr);

    double dx = curr.getValue(index) - ((ThreeDimNode) target).getValue(index);
    index = (index + 1) % dimensions;
    // Recursively search the half of the tree that contains the target point
    KNNSearch((INode) (dx > 0 ? curr.left : curr.right), target, k, index);

    int count = 0;
    for (INode node : boundedPriorityQueue) {
      if (node != null) {
        count++;
      }
    }
    // If the candidate hyperspace crosses the splitting plane (boundedPQ size smaller than k, or the
    // current distance between curr node and target is smaller than the max-priority element in the
    // boundedPQ):
    // search on the other sie of the plane by examining the other subtree
    if (count < k || distance < ((ThreeDimNode) boundedPriorityQueue.peekLast()).getEuclideanDistance()){
      KNNSearch((INode) (dx > 0 ? curr.right : curr.left), target, k, index);
    }
  }

  /**
   * Call this method to fill the boundedPriorityQueue
   * @param target
   * @param k
   */
  private void kNearest(INode target, int k) {
    initializeBoundedPQ(k);
    if (this.root == null) {
      throw new IllegalStateException("ERROR: Empty Tree!");
    }
    KNNSearch(this.root, target, k, 0);
  }

  /**
   * Call to get the k nearest neighbors
   * @return
   */
  public List<INode> getKNearestNeighbors() {
    return new ArrayList<INode>(boundedPriorityQueue);
  }
  /**
   *
   */
  private class NodeComparator implements Comparator<INode> {
    private final int index;

    private NodeComparator(int index) {
      this.index = index;
    }

    @Override
    public int compare(INode o1, INode o2) {
      ThreeDimNode n1 = (ThreeDimNode) o1;
      ThreeDimNode n2 = (ThreeDimNode) o2;
      return Double.compare(n1.getValue(index), n2.getValue(index));
    }
  }
}
