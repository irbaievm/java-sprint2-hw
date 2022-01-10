package model;


public class Subtask extends Task {
    private Integer epicID;

    public Subtask(String name, String description, int ID, String aNew, Integer epicID) {
        super(name, description, ID, aNew);
        this.epicID = epicID;
    }


    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                " epicID=" + epicID +
                " ID=" + ID +
                '}';
    }
}