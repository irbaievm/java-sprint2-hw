package controller;

public class Managers {
    public static TaskManager Default() {
        return new InMemoryTaskManager();
    }
}