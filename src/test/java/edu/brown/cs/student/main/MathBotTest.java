package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

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

  //write a test for really small numbers
  @Test
  public void testSmallerNumbers(){
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(0.0004, 0.00005);
    assertEquals(0.00045, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  //write a test that goes negative
  @Test
  public void testNegSubtraction() {
    MathBot matherator9004 = new MathBot();
    double output = matherator9004.subtract(18, 25);
    assertEquals(-7, output, 0.01);
  }
  //write a test for large numbers
  @Test
  public void testLargerSubtraction() {
    MathBot matherator9005 = new MathBot();
    double output = matherator9005.subtract(200000, 199999);
    assertEquals(1, output, 0.01);
  }

  //write a test for small numbers
  @Test
  public void testSmallerSubtraction() {
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.subtract(0.00004, 0.00002);
    assertEquals(0.00002, output, 0.01);
  }

  // TODO: add more unit tests of your own
}
