package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Stars.StarDistance;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StarsJUnitTest {

  @Test
  public void testOverlappingStars() {
    StarDistance distCalculator = new StarDistance();
    Double[] pos1 = new Double[3];
    pos1[0] = 0.0;
    pos1[1] = 0.0;
    pos1[2] = 0.0;
    Double[] pos2 = new Double[3];
    pos2[0] = 0.0;
    pos2[1] = 0.0;
    pos2[2] = 0.0;
    double output = distCalculator.euclideanDistance(pos1, pos2);
    assertEquals(0.0, output, 0.01);
  }

  @Test
  public void testStarsApart() {
    StarDistance distCalculator = new StarDistance();
    Double[] pos1 = new Double[3];
    pos1[0] = 282.43485;
    pos1[1] = 0.00449;
    pos1[2] = 5.36884;
    Double[] pos2 = new Double[3];
    pos2[0] = 43.04329;
    pos2[1] = 0.00285;
    pos2[2] = -15.24144;
    double output = distCalculator.euclideanDistance(pos1, pos2);
    assertEquals(240.277, output, 0.01);
  }

  @Test
  public void testFormCoordinate() {
    StarDistance distCalculator = new StarDistance();
    Double[] output = distCalculator.formCoordinate("282.43485", "0.00449", "5.36884");
    Double[] output2 = distCalculator.formCoordinate("43.04329", "0.00285", "-15.24144");
    Double[] pos1 = new Double[3];
    pos1[0] = 282.43485;
    pos1[1] = 0.00449;
    pos1[2] = 5.36884;
    Double[] pos2 = new Double[3];
    pos2[0] = 43.04329;
    pos2[1] = 0.00285;
    pos2[2] = -15.24144;
    Assert.assertArrayEquals(pos1, output);
    Assert.assertArrayEquals(pos2, output2);
    double output3 = distCalculator.euclideanDistance(output, output2);
    assertEquals(240.277, output3, 0.01);
  }
}
