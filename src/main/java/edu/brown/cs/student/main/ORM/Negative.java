package edu.brown.cs.student.main.ORM;

public class Negative {
    private int id;
    private String trait;

    /**
     * constructor
     * @param _id
     * @param _trait
     */
    public Negative(int _id, String _trait){
        this.id = _id;
        this.trait = _trait;
    }

    /**
     * creating the getters
     */
    public int getId(){
        return this.id;
    }

    public String getTrait(){
        return this.trait;
    }

}
