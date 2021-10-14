package edu.brown.cs.student.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;



    /**
     * Autocorrect but with databases.
     * <p>
     * Chooses to pass SQL exceptions on to the class that instantiates it.
     */
    public class Database {

        Connection conn = null;
        ArrayList<INode> userList = new ArrayList<>();
        HashMap<INode, Integer> userMap = new HashMap<>();
        HashMap<Integer, INode> idMap = new HashMap<>();
        HashMap<Integer, String> horoscopeMap = new HashMap<>();
        KDTree builtTree;

        /**
         * Instantiates the database
         * Automatically loads files.
         *
         * @param filename file name of SQLite3 database to open.
         * @throws SQLException if an error occurs in any SQL query.
         */
        Database(String filename) throws SQLException, ClassNotFoundException {
            Class.forName("org.sqlite.JDBC");
            String urlToDB = "jdbc:sqlite:" + filename; //not sure if filename is in correct format
            conn = DriverManager.getConnection(urlToDB);
            Statement stat = conn.createStatement();
            stat.executeUpdate("PRAGMA foreign_keys=ON;");
            stat.close();
        }


        /**
         * Reads all the rows from the database into a user list.
         *
         *
         * @throws SQLException if something goes wrong with the SQL
         */
        void populateUserListAndMap() throws SQLException {
            PreparedStatement prep = conn.prepareStatement("SELECT users.weight, users.height, users.age, users.user_id, users.horoscope FROM users;");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                String weight = rs.getString(1);
                String height = rs.getString(2);
                String age = rs.getString(3);
                String id = rs.getString(4);
                String horoscope = rs.getString(5);
                weight = weight.substring(0, weight.length() - 3); //eliminate "lbs"
                double weightNum = Double.parseDouble(weight); //change weight String to Double
                height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                double heightNum = ((Double.parseDouble(height.substring(0, 1))) * 12) + Double.parseDouble(height.substring(1));
                double ageNum = Double.parseDouble(age); //age string to int
                Integer idInt = Integer.parseInt(id);
                ThreeDimNode user = new ThreeDimNode(weightNum, heightNum, ageNum);
                userList.add(user);
                userMap.put(user, idInt);
                idMap.put(idInt, user);
                horoscopeMap.put(idInt, horoscope);
                builtTree = new KDTree(userList.size(), userList);
            }
            rs.close();
            prep.close();
        }}






