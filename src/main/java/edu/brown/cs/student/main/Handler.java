package edu.brown.cs.student.main;

import java.io.IOException;
import java.sql.SQLException;

public interface Handler {

    public void handle(String command) throws IOException, SQLException, ClassNotFoundException ;
}
