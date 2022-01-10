package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtask = new ArrayList<>();


    public Epic(String name, String description, int ID) {
        super(name,description,ID);
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(newStatus());
    }

    private String newStatus() {
        return "обновленный статус";
    }


    public void createSubtask(Subtask task) {
        subtask.add(task);
    }


    public ArrayList<Subtask> getSubtask() {
        return subtask;
    }
}
