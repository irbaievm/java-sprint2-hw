package model;

import static model.Status.NEW;

public class Subtask extends Task {
    Epic epic;
    private Integer epicID;

    public Subtask(String name, String description, int ID, String aNew,Integer epicID) {
        super(name,description,ID,aNew);
        this.epicID = epicID;
    }pwd


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
                //", name='" + name + '\'' +
                //", description='" + description + '\'' +
                " ID=" + ID +
                //", status='" + status + '\'' +
                '}';
    }
}