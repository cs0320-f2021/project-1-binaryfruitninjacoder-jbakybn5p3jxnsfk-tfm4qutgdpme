package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.lang.reflect.*;
import java.lang.reflect.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Autocorrect but with databases.
 * <p>
 * Chooses to pass SQL exceptions on to the class that instantiates it.
 */
public class Database {

  private static Connection conn = null;
  private static List<User> UserList = new ArrayList<>();

  /**
   * Instantiates the database, creating tables if necessary.
   * Automatically loads files.
   *
   * @param filename file name of SQLite3 database to open.
   * @throws SQLException if an error occurs in any SQL query.
   */
  Database(String filename) throws SQLException, ClassNotFoundException {

    // this line loads the driver manager class, and must be
// present for everything else to work properly
    Class.forName("org.sqlite.JDBC");

    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
// these two lines tell the database to enforce foreign keys during operations, and should be present
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    stat.close();
  }

//  public static Field[] getDeclaredFields(User user) {
//    return user.getDeclaredFields();
//  }

  //method that inserts an object into the list
  public void insert(User u)
      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
    PreparedStatement prep = conn.prepareStatement("INSERT INTO users (user_id, weight, bust_size, height, age, body_type, horoscope) VALUES (?)"
      );
    StringBuilder values = new StringBuilder();
    Field[] userInfo = u.getClass().getDeclaredFields();
    //System.out.println("Printing field = " + userInfo);
    for (Field field : userInfo) {
      Object value = field.get(u);
      values.append(value.toString());
    }
    prep.setString(1, values.toString());
//       System.out.println("Value of Field "
//           + userInfo[i].getName()
//           + " is " + value);
//      System.out.println("Field = " + userInfo[i].toString());
//    }
      prep.executeUpdate();
      prep.close();
    }

    public void delete(User u) throws SQLException, IllegalAccessException {
     // alternatively, I can use the DELETE FROM table_name WHERE condition;
      PreparedStatement prep = conn.prepareStatement("DELETE FROM users (user_id, weight, bust_size, height, age, body_type, horoscope) VALUES (?)");
      StringBuilder values = new StringBuilder();
      Field[] userInfo = u.getClass().getDeclaredFields();
      //System.out.println("Printing field = " + userInfo);
      for (Field field : userInfo) {
        Object value = field.get(u);
        values.append(value.toString());
      }
      prep.setString(1, values.toString());

      prep.executeUpdate();
      prep.close();
    }

    public void where(String field, String fieldValue) throws SQLException {
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM users WHERE " + field + " = " + fieldValue);
      ResultSet rs = prep.executeQuery();
      if (!rs.isClosed()) {
        while (rs.next()) {
         UserList.add(new User(rs.getInt(0), rs.getInt(1),
             rs.getString(2), rs.getString(3), rs.getInt(4),
             rs.getString(5), rs.getString(6)));
        }
      }
      rs.close();
      prep.close();
    }

  public void rowToUser() throws SQLException {
    PreparedStatement prep = conn.prepareStatement("SELECT * FROM users");
    ResultSet rs = prep.executeQuery();
    if (!rs.isClosed()) {
      while (rs.next()) {
        UserList.add(new User(rs.getInt(0), rs.getInt(1),
            rs.getString(2), rs.getString(3), rs.getInt(4),
            rs.getString(5), rs.getString(6)));
      }
    }
    rs.close();
    prep.close();
  }

    public void update(String fieldValue, String field, String newValue) throws SQLException {
      PreparedStatement prep = conn.prepareStatement("UPDATE users SET " + field + " = " + newValue + " WHERE " + field + "=" + fieldValue);
      prep.executeUpdate();
      prep.close();
    }

    public void sql(String sql)
        throws SQLException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
      String[] sqlSplit = sql.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
      for (String ss : sqlSplit){
        switch (ss) {
          case "SELECT": {
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            StringBuilder values = new StringBuilder();
            if (!rs.isClosed()) {
              while (rs.next()) {
                UserList.add(new User(rs.getInt(0), rs.getInt(1),
                    rs.getString(2), rs.getString(3), rs.getInt(4),
                    rs.getString(5), rs.getString(6)));
              }
            }
            rs.close();
            prep.close();
            break;
          }
          case "INSERT": {
            User Mandy = new User(1, 130, "34b", "6' 7\"", 20, "hourglass", "libra");
            insert(Mandy);
            break;
          }
          case "DELETE": {
            User Mandy = new User(1, 130, "34b", "6' 7\"", 20, "hourglass", "libra");
            delete(Mandy);
            break;
          }
          case "UPDATE": {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.executeUpdate();
            prep.close();
            break;
          }
        }
      }
    }
  }



