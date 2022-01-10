package model;

import java.util.Objects;

import static model.Status.NEW;

public class Task {
    private String name;
    private String description;
    protected Integer ID;
    private String status;


    public Task() {
    }

    public Task(String name, String description, Integer ID) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.status = NEW;
    }

    public Task(String name, String description, Integer ID, String status) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.status = status;
    }

    public Task(Task task) {
        this.name = task.name;
        this.description = task.description;
        this.ID = task.ID;
        this.status = task.status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(ID, task.ID) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + ID +
                ", status='" + status + '\'' +
                '}';
    }
}
