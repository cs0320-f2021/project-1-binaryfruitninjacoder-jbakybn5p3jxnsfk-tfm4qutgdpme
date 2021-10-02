package edu.brown.cs.student.main;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class KDTree {
  // idea: when storing the user information, first store them in List<User>, when construct
  // a balanced KDTree

  private int dimensions;
  private INode root;
  private PriorityQueue<INode> boundedPriorityQueue;

  public KDTree(int dimensions, List<INode> nodes) {
    this.dimensions = dimensions;
    root = constructTree(nodes, 0, nodes.size(), 0);
  }

  private INode constructTree(List<INode> nodes, int start, int end, int index) {
    if (end <= start) return null;
    int mid = start + (end - start) / 2;
    INode node = QuickSelect.select(nodes, start, end-1, mid, new NodeComparator(index));
    index = (index +1) % dimensions;

    // TODO: [al] THIS IS NOT EXTENDABLE...BAD CODE :(
    // TODO: [al] how to make this more generic but also be able to access left & right, abstract class maybe? instead of interface
    ((ThreeDimNode)node).left = (ThreeDimNode) constructTree(nodes, start, mid, index);
    ((ThreeDimNode)node).right = (ThreeDimNode) constructTree(nodes, mid+1, end, index);
    return node;
  }

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

 enqueue curr into bpq with priority distance(curr, P)
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
   *
   * @param target
   * @return
   */
  public List<INode> findKNearest(INode target) {
    return null;
  }

  private static class NodeComparator implements Comparator<INode> {
    private final int index;

    private NodeComparator(int index) {
      this.index = index;
    }

    public int compare(INode node1, INode node2) {
      return Double.compare(node1.get(index), node2.get(index));
    }
  }
}
