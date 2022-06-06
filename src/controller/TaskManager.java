package controller;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static model.Status.*;

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
    List<Epic> getAllEpics();

    //	Получение списка всех подзадач определённого эпика.
    List<Subtask> getAllSubtasksEpics(Epic epic);

    //  Получение задачи любого типа по идентификатору.
    Task findTaskId(Integer id);

    Subtask findSubtaskId(Integer id);

    Epic findEpicId(Integer id);

    Task updateTask(Task changedTask);

    Subtask updateSubtask(Subtask changedSubtask);

    Epic updateEpic(Epic changedEpic);

    void deleteAllTask();

    void deleteTaskId(int id);

    void deleteAllSubtask();

    void deleteSubtaskID(int id);

    void deleteAllEpic();

    void deleteEpicId(int id);

    // Обновление статуса эпика в зависимости от статуса подзадач
    void updateStatus(Subtask subtask);

    List<Task> getHistory();

}
