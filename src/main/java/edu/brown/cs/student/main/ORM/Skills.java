package edu.brown.cs.student.main.ORM;

public class Skills {
    private int id;
    private String name;
    private int commenting;
    private int testing;
    private int OOP;
    private int algorithms;
    private int teamwork;
    private int frontend;


    /**
     * Constructor for SKills
     */

    public Skills(int _id, String _name, int _commenting, int _testing, int _OOP, int _algorithms, int _teamwork, int _frontend){
        this.id = _id;
        this.name = _name;
        this.commenting = _commenting;
        this.testing = _testing;
        this.OOP = _OOP;
        this.algorithms = _algorithms;
        this.teamwork = _teamwork;
        this.frontend = _frontend;
    }


    /**
     * create all the getters!
     * @return
     */
    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getCommenting(){
        return this.commenting;
    }


    public int getTesting(){
        return this.testing;
    }

    public int getOOP(){
        return this.OOP;
    }

    public int getAlgorithms(){
        return this.algorithms;
    }

    public int getTeamwork(){
        return this.teamwork;
    }

    public int getFrontend(){
        return this.frontend;
    }

}
