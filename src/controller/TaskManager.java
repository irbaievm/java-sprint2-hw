package controller;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.HashMap;

public interface TaskManager {
    HashMap<Integer, Task> getTasks();

    void setTasks(HashMap<Integer, Task> tasks);

    HashMap<Integer, Subtask> getSubtasks();

    void setSubtasks(HashMap<Integer, Subtask> subtasks);

    HashMap<Integer, Epic> getEpics();

    void setEpics(HashMap<Integer, Epic> epics);

    Task createTask(Task task);

    Subtask createSubtask(Subtask subtask);

    Epic createEpic(Epic epic);

    //  Получение списка всех задач.
    List<Task> getAllTasks();

    //  Получение списка всех эпиков.
    List<Epic> getAllEpic();

    //	Получение списка всех подзадач определённого эпика.
    List<Subtask> getAllSubtaskEpics(Epic epic);

    //  Получение задачи любого типа по идентификатору.
    Task findTaskId(Integer id);

    Task findSubtaskId(Integer id);

    Task findEpicId(Integer id);

    Task updateTask(Task changedTask);

    Subtask updateSubtask(Subtask changedSubtask);

    Epic updateEpic(Epic changedEpic);

    void deleteAllTask(Task task);

    Task deleteTaskId(Task id);

    void deleteAllSubtask(Subtask subtask);

    Task deleteAllSubtaskID(Task id);

    void deleteAllEpic(Epic epic);

    Task deleteTaskId(Epic id);

    // Обновление статуса эпика в зависимости от статуса подзадач
    void updateStatus(Subtask subtask);
}
