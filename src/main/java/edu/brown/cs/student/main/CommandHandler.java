package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class CommandHandler implements Handler {
    public static Database loadedData;
    private String command = "";


    public CommandHandler(String command) {
        this.command = command;
        CommandHandler.loadedData = loadedData;
    }

    public void handle(String commandPassed) throws IOException, SQLException, ClassNotFoundException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //create reader
        // initialize a StringTokenizer to help parse the input, broken by space or tabs
           StringTokenizer st = new StringTokenizer(commandPassed, " \t", false);
            String[] commandArray;
            commandArray = commandPassed.split(" ");
        loadedData = new Database(commandArray[1]);
        loadedData.populateUserListAndMap(); //call method to populate hashMaps in database

    }

}
