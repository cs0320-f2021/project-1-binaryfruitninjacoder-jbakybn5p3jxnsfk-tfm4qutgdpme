package edu.brown.cs.student.main.ORM;

public class Interests {
    private int id;
    private String interest;


    /**
     * Constructor for Interests
     */
    public Interests(int _id, String _interest){
        this.id = _id;
        this.interest = _interest;
    }


    /**
     * create all the getters!
     */
    public int getId(){
        return this.id;
    }

    public String getInterest(){
        return this.interest;
    }

}
