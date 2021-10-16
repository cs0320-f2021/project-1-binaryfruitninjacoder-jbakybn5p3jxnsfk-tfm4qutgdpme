//package edu.brown.cs.student.main.ORM;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.lang.reflect.Field;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Autocorrect but with databases.
// * <p>
// * Chooses to pass SQL exceptions on to the class that instantiates it.
// */
//public class Database {
//
//  private static Connection conn = null;
//  public static List<User> UserList = new ArrayList<>();
//
//  /**
//   * Instantiates the database, creating tables if necessary.
//   * Automatically loads files.
//   *
//   * @param filename file name of SQLite3 database to open.
//   * @throws SQLException if an error occurs in any SQL query.
//   */
//  public Database(String filename) throws SQLException, ClassNotFoundException {
//
//    // this line loads the driver manager class, and must be
//// present for everything else to work properly
//    Class.forName("org.sqlite.JDBC");
//
//    String urlToDB = "jdbc:sqlite:" + filename;
//    conn = DriverManager.getConnection(urlToDB);
//// these two lines tell the database to enforce foreign keys during operations, and should be present
//    Statement stat = conn.createStatement();
//    stat.executeUpdate("PRAGMA foreign_keys=ON;");
//    stat.close();
//  }
//
////  public static Field[] getDeclaredFields(User user) {
////    return user.getDeclaredFields();
////  }
//
//  //method that inserts an object into the list
//  public void insert(User u)
//      throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    PreparedStatement prep = conn.prepareStatement("INSERT INTO users (user_id, weight, bust_size, height, age, body_type, horoscope) VALUES (?)"
//      );
//    StringBuilder values = new StringBuilder();
//    Field[] userInfo = u.getClass().getDeclaredFields();
//    //System.out.println("Printing field = " + userInfo);
//    for (Field field : userInfo) {
//      Object value = field.get(u);
//      values.append(value.toString());
//    }
//    prep.setString(1, values.toString());
////       System.out.println("Value of Field "
////           + userInfo[i].getName()
////           + " is " + value);
////      System.out.println("Field = " + userInfo[i].toString());
////    }
//      prep.executeUpdate();
//      prep.close();
//    }
//
//    public void delete(User u) throws SQLException, IllegalAccessException {
//     // alternatively, I can use the DELETE FROM table_name WHERE condition;
//      PreparedStatement prep = conn.prepareStatement("DELETE FROM users (user_id, weight, bust_size, height, age, body_type, horoscope) VALUES (?)");
//      StringBuilder values = new StringBuilder();
//      Field[] userInfo = u.getClass().getDeclaredFields();
//      //System.out.println("Printing field = " + userInfo);
//      for (Field field : userInfo) {
//        Object value = field.get(u);
//        values.append(value.toString());
//      }
//      prep.setString(1, values.toString());
//
//      prep.executeUpdate();
//      prep.close();
//    }
//
//    public void where(String field, String fieldValue) throws SQLException {
//      PreparedStatement prep = conn.prepareStatement("SELECT * FROM users WHERE " + field + " = " + fieldValue);
//      ResultSet rs = prep.executeQuery();
//      if (!rs.isClosed()) {
//        while (rs.next()) {
//         UserList.add(new User(rs.getInt(0), rs.getInt(1),
//             rs.getString(2), rs.getString(3), rs.getInt(4),
//             rs.getString(5), rs.getString(6)));
//        }
//      }
//      rs.close();
//      prep.close();
//    }
//
//  public void rowToUser() throws SQLException {
//    PreparedStatement prep = conn.prepareStatement("SELECT * FROM users");
//    ResultSet rs = prep.executeQuery();
//    if (!rs.isClosed()) {
//      while (rs.next()) {
//        UserList.add(new User(rs.getInt(0), rs.getInt(1),
//            rs.getString(2), rs.getString(3), rs.getInt(4),
//            rs.getString(5), rs.getString(6)));
//      }
//    }
//    rs.close();
//    prep.close();
//  }
//
//    public void update(String fieldValue, String field, String newValue) throws SQLException {
//      PreparedStatement prep = conn.prepareStatement("UPDATE users SET " + field + " = " + newValue + " WHERE " + field + "=" + fieldValue);
//      prep.executeUpdate();
//      prep.close();
//    }
//
//    public void sql(String sql)
//        throws SQLException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
//      String[] sqlSplit = sql.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//      for (String ss : sqlSplit){
//        switch (ss) {
//          case "SELECT": {
//            PreparedStatement prep = conn.prepareStatement(sql);
//            ResultSet rs = prep.executeQuery();
//            StringBuilder values = new StringBuilder();
//            if (!rs.isClosed()) {
//              while (rs.next()) {
//                UserList.add(new User(rs.getInt(0), rs.getInt(1),
//                    rs.getString(2), rs.getString(3), rs.getInt(4),
//                    rs.getString(5), rs.getString(6)));
//              }
//            }
//            rs.close();
//            prep.close();
//            break;
//          }
//          case "INSERT": {
//            User Mandy = new User(1, 130, "34b", "6' 7\"", 20, "hourglass", "libra");
//            insert(Mandy);
//            break;
//          }
//          case "DELETE": {
//            User Mandy = new User(1, 130, "34b", "6' 7\"", 20, "hourglass", "libra");
//            delete(Mandy);
//            break;
//          }
//          case "UPDATE": {
//            PreparedStatement prep = conn.prepareStatement(sql);
//            prep.executeUpdate();
//            prep.close();
//            break;
//          }
//        }
//      }
//    }
//  }
//
//
//

package edu.brown.cs.student.main.ORM;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic Database class that can work with ORMs.
 */
public class Database {
  private String db;
  private Connection conn;

  /**
   * Constructor for the Database class.
   *
   * @param db the path to the database.
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public Database(String db) throws ClassNotFoundException, SQLException {
    this.db = db;
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDB);
  }

  /**
   * Tries to insert o into the database.
   *
   * @param o the object to be inserted.
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws SQLException
   */
  public void insert(Object o) throws IllegalArgumentException, IllegalAccessException,
      SQLException {
    insertClass(o.getClass(), o);
  }

  private void insertClass(Class<?> c, Object o) throws IllegalArgumentException,
      IllegalAccessException, SQLException {
    String tableName = c.getSimpleName().toLowerCase();
    Field[] attributes = c.getDeclaredFields();

    String attributeNames = "";
    String values = "";
    List<Object> insertValues = new ArrayList<>();
    int counter = 0;
    for (Field field : attributes) {
      field.setAccessible(true);
      counter += 1;
      attributeNames += field.getName();
      values += "?";
      insertValues.add(field.get(o));
      if (counter != attributes.length) {
        attributeNames += ", ";
        values += ", ";
      }

    }
    String sql = "INSERT INTO " + tableName + " (" + attributeNames + ") VALUES (" + values + ");";
    System.out.println(sql);
    sqlWithParams(sql, insertValues);
  }

  private void sqlWithParams(String sql, List<Object> insertValues) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(sql);
    setParameters(prep, insertValues);
    prep.executeUpdate();
  }

  private void setParameters(PreparedStatement prep, List<Object> parameters) throws SQLException {
    int counter = 1;
    for (Object o : parameters) {
      prep.setObject(counter, o);
      counter += 1;
    }
  }


  /**
   * Deletes an object from the Database.
   *
   * @param o the object to be deleted from the Database.
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws SQLException
   */
  public void delete(Object o) throws IllegalArgumentException, IllegalAccessException,
      SQLException {
    Class<?> c = o.getClass();
    String tableName = c.getSimpleName().toLowerCase();
    Field[] attributes = c.getDeclaredFields();

    int counter = 0;
    String values = "";
    List<Object> params = new ArrayList<>();
    for (Field field : attributes) {
      field.setAccessible(true);
      counter += 1;
      String fName = field.getName();

      if (counter != attributes.length) {
        values += (fName + "=? AND ");
      } else {
        values += (fName + "=?");
      }

      params.add(field.get(o));

    }

    String sql = "DELETE FROM " + tableName + " WHERE (" + values + ");";
    System.out.println(sql);
    sqlWithParams(sql, params);
  }

  /**
   * Updates an object from the database using queryParams as a guide for what to update.
   *
   * @param o           the object to be updated.
   * @param queryParams the parameters that we wish to update.
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws SQLException
   */
  public void update(Object o, Map<String, String> queryParams) throws IllegalArgumentException,
      IllegalAccessException, SQLException {
    updateClass(o.getClass(), o, queryParams);
  }

  private void updateClass(Class<?> c, Object o, Map<String, String> queryParams) throws
      IllegalArgumentException, IllegalAccessException, SQLException {
    String tableName = c.getSimpleName().toLowerCase();
    Field[] attributes = c.getDeclaredFields();

    String updationFields = "";
    String values = "";
    Set<String> keys = queryParams.keySet();
    List<Object> params = new ArrayList<>();
    int counter = 0;
    for (String key : keys) {
      counter += 1;
      if (counter != keys.size()) {
        updationFields += (key + "=? , ");
      } else {
        updationFields += (key + "=?");
      }
      params.add(queryParams.get(key));
    }

    counter = 0;
    for (Field field : attributes) {
      field.setAccessible(true);
      counter += 1;
      String fName = field.getName();

      if (counter != attributes.length) {
        values += (fName + "=? AND ");
      } else {
        values += (fName + "=?");
      }

      params.add(field.get(o));

    }

    String sql = "UPDATE " + tableName + " SET " + updationFields + " WHERE (" + values + ");";
    System.out.println(sql);
    sqlWithParams(sql, params);
  }


  /**
   * Selects and returns a list of objects from the database given a set of parameters.
   *
   * @param <T>         the type of the object we wish to return.
   * @param c           the class of the object that we are returning.
   * @param queryParams the criteria for selection.
   * @return a list of objects as retrieved from the database.
   * @throws SQLException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws SecurityException
   */
  public <T> List<T> select(Class<T> c, Map<String, String> queryParams) throws SQLException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    String tableName = c.getSimpleName().toLowerCase();
    List<Object> params = new ArrayList<>();
    Set<String> keys = queryParams.keySet();
    String wheres = "";
    int counter = 0;
    for (String key : keys) {
      counter += 1;
      params.add(queryParams.get(key));
      if (counter != keys.size()) {
        wheres += (key + "=? AND");
      } else {
        wheres += (key + "=?");
      }
    }

    String sql = "SELECT * FROM " + tableName + " WHERE " + wheres + ";";
    System.out.println(sql);
    return sqlListQuery(c, sql, params);
  }

  private <T> List<T> sqlListQuery(Class<T> c, String sql, List<Object> insertValues) throws
      SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    PreparedStatement prep = conn.prepareStatement(sql);
    setParameters(prep, insertValues);
    List<T> output = new ArrayList<>();
    ResultSet res = prep.executeQuery();
    Field[] attributes = c.getDeclaredFields();
    Map<String, String> mapper = new HashMap<>();

    while (res.next()) {
      for (Field field : attributes) {
        field.setAccessible(true);
        String fieldName = field.getName();
        int column = res.findColumn(fieldName);
        mapper.put(fieldName, res.getString(column));
      }
      T node = (T) (c.getDeclaredConstructor(Map.class).newInstance(mapper));
      output.add(node);
    }
    System.out.println(output.size());
    return output;
  }


}