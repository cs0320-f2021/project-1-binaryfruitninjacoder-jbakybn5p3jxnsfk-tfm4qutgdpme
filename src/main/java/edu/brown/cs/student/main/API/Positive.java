package edu.brown.cs.student.main.API;

public class Positive {
    private int id;
    private String trait;

    /**
     * constructor
     * @param _id
     * @param _trait
     */
    public Positive(int _id, String _trait){
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
