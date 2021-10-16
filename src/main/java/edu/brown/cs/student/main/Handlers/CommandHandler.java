package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.ORM.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class CommandHandler {
  private String command = "";
  private Database loadedData;

  public CommandHandler(String command, Database loadedData) {
    this.command = command;
    this.loadedData = loadedData;
  }

  public void handle() throws IOException, SQLException, ClassNotFoundException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //create reader
    String s = this.command;
    // initialize a StringTokenizer to help parse the input, broken by space or tabs
    StringTokenizer st = new StringTokenizer(s, " \t", false);
    String[] commandArray;
    commandArray = s.split(" ");
    loadedData = new Database(commandArray[1]);
    loadedData.populateUserListAndMap(); //call method to populate hashMaps in database
  }

}
