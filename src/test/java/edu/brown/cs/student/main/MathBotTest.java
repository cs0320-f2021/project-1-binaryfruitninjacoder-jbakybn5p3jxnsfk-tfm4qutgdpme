package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import edu.brown.cs.student.main.Handlers.MathBot;
import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void testNegative() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(0, -19.0923);
    assertEquals(-19.0923, output, 0.01);
  }

  @Test
  public void testZero() {
    MathBot matherator9004 = new MathBot();
    double output = matherator9004.add(0, 0);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void testSubtractionDecimal() {
    MathBot matherator9005 = new MathBot();
    double output = matherator9005.subtract(0, -19.09);
    assertEquals(19.09, output, 0.01);
  }

  @Test
  public void testZeroSum() {
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.add(19.0923, -19.0923);
    assertEquals(0, output, 0.01);
  }
}
