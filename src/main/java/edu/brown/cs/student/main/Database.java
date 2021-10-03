package edu.brown.cs.student.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


    /**
     * Autocorrect but with databases.
     * <p>
     * Chooses to pass SQL exceptions on to the class that instantiates it.
     */
    public class Database {


        private static Connection conn = null;
        private static ArrayList<INode> nodes = new ArrayList<>();
        private static HashMap<INode, Integer> userMap = new HashMap<>();
        private static HashMap<Integer, INode> idMap = new HashMap<>();

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

            PreparedStatement prep;
            prep = conn.prepareStatement("");
        }


        /**
         * Reads all the rows from the database into a user list.
         *
         *
         * @throws SQLException if something goes wrong with the SQL
         */
        void populateUserListAndMap() throws SQLException {
            PreparedStatement prep = conn.prepareStatement("SELECT users.weight, users.height, users.age, users.user_id FROM users;");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                String weight = rs.getString(1);
                String height = rs.getString(2);
                String age = rs.getString(3);
                String id = rs.getString(4);
                weight = weight.substring(0, weight.length() - 2);
                double weightNum = Double.parseDouble(weight);
                height = height.charAt(0) + height.substring(3, height.length() - 1); //substring in format (510)
                double heightNum = (Double.parseDouble(String.valueOf(height.charAt(0))) * 12) + Double.parseDouble(height.substring(1, height.length())); //not sure why i need valueof
                double ageNum = Double.parseDouble(age);
                Integer idInt = Integer.parseInt(id);
                ThreeDimNode user = new ThreeDimNode(weightNum, heightNum, ageNum);
                nodes.add(user);
                userMap.put(user, idInt);
                idMap.put(idInt, user);

            }
            rs.close();
            prep.close();
        }

        void populateHoroscopeCount() throws SQLException{
            //sql query to
        }

        /**
         * getter to return user list
         * @return list of users
         */
        ArrayList<INode> getUserList(){
            return nodes;
        }
        /**
         * getter to return hashmap of users to id
         * @return userMap with user ID's mapped to users
         */
        HashMap<INode, Integer> getUserMap() {
            return userMap;
        }

        /**
         * getter to return hashmap of id to users
         * @return userMap with user ID's mapped to users
         */
        HashMap<Integer, INode> getIdMap() {
            return idMap;
        }

    }




