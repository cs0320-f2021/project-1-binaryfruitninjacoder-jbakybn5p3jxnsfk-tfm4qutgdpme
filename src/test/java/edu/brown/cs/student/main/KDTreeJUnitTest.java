package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Handlers.MathBot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KDTreeJUnitTest {

  /**
   * 1. Make a bunch of Nodes --> put them into a list
   * 2. On creating nodes: create ThreeDimNodes --> pass in age, weight, and height
   * 3. pass them into the KD Tree to construct a KD Tree
   * 4. check the tree structure
   * 5. call
   */

  @Test
  public void test() {
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.add(19.0923, -19.0923);
    assertEquals(0, output, 0.01);
  }

}
