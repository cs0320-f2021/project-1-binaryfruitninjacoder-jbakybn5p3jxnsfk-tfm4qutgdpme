package edu.brown.cs.student.main.ORM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.lang.reflect.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



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
  public Database(String filename) throws SQLException, ClassNotFoundException {

    // this line loads the driver manager class, and must be
// present for everything else to work properly
    Class.forName("org.sqlite.JDBC");

    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
// these two lines tell the database to enforce foreign keys during operations, and should be present
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    stat.close();

    if (filename.isEmpty()){
      PreparedStatement prep;
      prep = conn.prepareStatement(
          "CREATE TABLE IF NOT EXISTS user("
              + "id INTEGER,"
              + "weight DOUBLE,"
              + "bust_size STRING"
              + "height STRING"
              + "age INTEGER"
              + "body_type STRING"
              + "horoscope STRING"
              + "PRIMARY KEY (id));");
      prep.executeUpdate();
      prep.close();
    }
  }

  //method that inserts an object into the list
  /**
   * Method that inserts a row (values determined by an object) into the database
   * @param u representing a User object
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   */
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

  /**
   * Method that deletes a row (values determined by an object) from the database
   * @param u representing a User object
   * @throws SQLException
   * @throws IllegalAccessException
   */
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

  /**
   * Method that selects a few rows from the database and makes them objects
   * @param field representing the field we want to select
   * @param fieldValue representing the field values we want to select
   * @throws SQLException
   */
    public User where(String field, String fieldValue) throws SQLException {
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM users WHERE " + field + " = " + fieldValue);
      ResultSet rs = prep.executeQuery();
      User newUser = null;
      if (!rs.isClosed()) {
        while (rs.next()) {
         newUser = (new User(rs.getInt(0), rs.getDouble(1),
             rs.getString(2), rs.getString(3), rs.getInt(4),
             rs.getString(5), rs.getString(6)));
         UserList.add(newUser);
        }
      }
      rs.close();
      prep.close();
      return newUser;
    }

//    public User select(User u) throws SQLException {
//      PreparedStatement prep = conn.prepareStatement("SELECT * FROM users WHERE horoscope = 'libra'");
//      ResultSet rs = prep.executeQuery();
//      User userCompare;
//      if (!rs.isClosed()) {
//        while (rs.next()) {
//          userCompare = new User(rs.getInt(0), rs.getInt(1),
//              rs.getString(2), rs.getString(3), rs.getInt(4),
//              rs.getString(5), rs.getString(6)));
//        }
//      }
//      rs.close();
//      prep.close();
//      return userCompare;
//    }

  /**
   * Method that updates the database
   * @param fieldValue representing the old field value we want to update
   * @param field representing the field we want to update
   * @param newValue representing the new field value we want to update
   * @throws SQLException
   */
    public void update(String fieldValue, String field, String newValue) throws SQLException {
      PreparedStatement prep = conn.prepareStatement("UPDATE users SET " + field + " = " + newValue + " WHERE " + field + "=" + fieldValue);
      prep.executeUpdate();
      prep.close();
    }

  /**
   * Method that allows raw query
   * @param sql A string representing the SQL typed inside REPL in the form of database.sql("INSERT...")
   * @throws SQLException
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
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
                UserList.add(new User(rs.getInt(0), rs.getDouble(1),
                    rs.getString(2), rs.getString(3), rs.getInt(4),
                    rs.getString(5), rs.getString(6)));
              }
            }
            rs.close();
            prep.close();
            break;
          }
          case "INSERT": {
            User Mandy = new User(1, 130.0, "34b", "6' 7\"", 20, "hourglass", "libra");
            insert(Mandy);
            break;
          }
          case "DELETE": {
            User Mandy = new User(1, 130.0, "34b", "6' 7\"", 20, "hourglass", "libra");
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



