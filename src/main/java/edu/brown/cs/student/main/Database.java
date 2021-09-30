package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
  // private static List<User> UserList = new ArrayList<>();

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

  //method that inserts an object into the list
  public void insert(User u) throws SQLException, ClassNotFoundException {
    PreparedStatement prep =
      prep = conn.prepareStatement("INSERT INTO users VALUES (?)"
      );
    String values = new String();
    prep.setString(1, values);

    //  String values = u.getDeclaredFields();  write this method
     // prep.setString(1, values);

      prep.executeUpdate();
      prep.close();
    }
  }



