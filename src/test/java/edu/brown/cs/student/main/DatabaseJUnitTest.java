package edu.brown.cs.student.main;

import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.ORM.User;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

public class DatabaseJUnitTest {


//  @Test
//  public void testInsert()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//      Database db = new Database("");
//      User Mandy = new User(1, 130.0, "34b", "6'7", 20, "hourglass", "libra");
//      db.insert(Mandy);
//      String userScope = Mandy.getHoroscope();
//      assertTrue(db.where("horoscope", userScope).equals(Mandy));
//      System.out.println(db.where("horoscope", userScope));
//
//  }
//
//  @Test
//  public void testDelete()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    Database db = new Database("");
//    Database db2 = new Database("");
//      User Mandy = new User(1, 130.0, "34b", "6'7", 20, "hourglass", "libra");
//      db2.insert(Mandy);
//      db2.delete(Mandy);
//      assertEquals(db, db2);
//  }
//
//  @Test
//  public void testWhere()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    Database db = new Database("");
//    User Mandy = new User(1, 130.0, "34b", "6'7", 20, "hourglass", "libra");
//    db.insert(Mandy);
//    String userScope = Mandy.getHoroscope();
//    assertTrue(db.where("horoscope", userScope).equals(Mandy));
//  }
//
//  @Test
//  public void testUpdate()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    Database db = new Database("");
//    User Mandy = new User(1, 130.0, "34b", "6'7", 20, "hourglass", "libra");
//    User NewMandy = new User(2, 130.0, "34b", "6'7", 20, "pear", "libra");
//    db.insert(Mandy);
//    String userBody = Mandy.getBody_type();
//    String userId = String.valueOf(Mandy.getId());
//
//    db.update("hourglass", "body_type", "pear" );
//    db.update("1", "id", "2" );
//    assertTrue(db.where("body_type", userBody).equals(NewMandy));
//    assertTrue(db.where("id", userId).equals(NewMandy));
//  }
//
//  @Test
//  public void testSql()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    Database db = new Database("");
//    User Mandy = new User(1, 130.0, "34b", "6'7", 20, "hourglass", "libra");
//    db.insert(Mandy);
//    String userScope = Mandy.getHoroscope();
//    db.sql("INSERT INTO users (user_id, weight, bust_size, height, age, body_type, horoscope) VALUES (1, 130.0, 34b, 6' 7, 20, hourglass, libra)");
//    assertTrue(db.where("horoscope", userScope).equals(Mandy));
//  }
//


//  @Test
//  public void testDelete()
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    Database db = new Database("");
//    User Mandy = new User(1, 130, "34b", "6'7", 20, "hourglass", "libra");
//    db.insert(Mandy);
//    db.delete(Mandy);
//
//  }

}
