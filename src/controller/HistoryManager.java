package controller;

import model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void delete(Integer id);

    List<Task> getHistory();


}
