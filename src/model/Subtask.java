package model;


public class Subtask extends Task {
    private Integer epicID;

    public Subtask(String name, String description, int ID, Status aNew, Integer epicID) {
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
        return "Subtask {" +
                "name = '" + super.getName() + '\'' +
                ", description = '" + super.getDescription() + '\'' +
                ", id = " + ID +
                ", status = '" + super.getStatus() + '\'' +
                ", EpicID = " + epicID +
                '}';
    }
}