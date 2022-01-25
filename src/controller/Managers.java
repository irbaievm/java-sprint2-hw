package controller;

public class Managers {
    public static TaskManager getDefaul() {
        return new InMemoryTaskManager();
    }
}