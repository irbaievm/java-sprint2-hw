package controller;

import model.Task;
import model.Subtask;
import model.Epic;


import static model.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager<generatorSubtaskID> {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    int generatorTaskID = 0;
    int generatorSubtaskID = 0;
    int generatorEpicID = 0;


    public Task createTask(Task task) {
        int ID = ++generatorTaskID;
        final Task value = new Task(task.getName(), task.getDescription(), ID, NEW);
        //value.setStatus(NEW);
        if (tasks.containsKey(task.getID())) {
            System.out.println("Задача с таким ID существует = " + task.getID());
            return null;
        }
        tasks.put(task.getID(), new Task(task));
        return value;

    }

    public Subtask createSubtask(Subtask subtask) {
        int ID = ++generatorSubtaskID;
        Subtask value = new Subtask(subtask.getName(), subtask.getDescription(), ID, NEW, subtask.getEpicID());
        if (subtasks.containsKey(subtask.getID())) {
            System.out.println("Задача с таким ID существует = " + subtask.getID());
            return null;
        }
        if (!epics.containsKey(subtask.getEpicID())) {
            System.out.println("Эпик с таким ID не найден: " + subtask.getEpicID());
            return null;
        }
        subtasks.put(subtask.getID(), value);
        final Epic epic = epics.get(subtask.getEpicID());
        epic.createSubtask(subtask);
        return value;
    }

    public Epic createEpic(Epic epic) {
        int ID = ++generatorEpicID;
        final Epic value = new Epic(epic.getName(), epic.getDescription(), ID);
        if (epics.containsKey(value.getID())) {
            System.out.println("Эпик с таким ID уже существует = " + epic.getID());
        } else {
            epics.put(value.getID(), value);
            return null;
        }
        return value;
    }

    //  Получение списка всех задач.
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //  Получение списка всех эпиков.
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    //	Получение списка всех подзадач определённого эпика.
    public ArrayList<Task> getAllSubtaskEpic(Task epic) {
        return getAllSubtaskEpic(epic.getID());
    }

    public ArrayList<Task> getAllSubtaskEpic(Integer epicID) {
        return new ArrayList<>();
    }



    //  Получение задачи любого типа по идентификатору.
    public Task findTaskId(Integer id) {
        return tasks.get(id);
    }

    public Task findSubtaskId(Integer id) {
        return subtasks.get(id);
    }

    public Task findEpicId(Integer id) {
        return epics.get(id);
    }

    //  Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.

    public Task updateTask(Task changedTask) {

        Task saveTask = tasks.get(changedTask.getID());
        if (saveTask == null) {
            return null;
        }
        saveTask.setDescription(changedTask.getDescription());
        saveTask.setName(changedTask.getName());
        saveTask.setStatus(changedTask.getStatus());
        return saveTask;
    }

    public Subtask updateSubtask(Subtask changedSubtask) {
        Task saveSubtask = subtasks.get(changedSubtask.getID());
        if (saveSubtask == null) {
            return null;
        }
        saveSubtask.setDescription(changedSubtask.getDescription());
        saveSubtask.setName(changedSubtask.getName());
        saveSubtask.setStatus(changedSubtask.getStatus());
        updateStatus(changedSubtask);
        return (Subtask) saveSubtask;
    }

    public Epic updateEpic(Epic changedEpic) {
        Task saveEpic = subtasks.get(changedEpic.getID());
        if (saveEpic == null) {
            return null;
        }
        saveEpic.setDescription(changedEpic.getDescription());
        saveEpic.setName(changedEpic.getName());
        saveEpic.setStatus(changedEpic.getStatus());
        return (Epic) saveEpic;
    }

    //  Удаление ранее добавленных задач — всех и по идентификатору.

    public void deleteAllTask(Task task) {
        tasks.clear();
    }

    public Task deleteTaskId(Task id) {
        return tasks.remove(id);
    }

    public void deleteAllSubtask(Subtask subtask) {
        subtasks.clear();
    }

    public Task deleteAllSubtaskID(Task id) {
        return subtasks.remove(id);
    }

    public void deleteAllEpic(Epic epic) {
        epics.clear();
    }

    public Task deleteTaskId(Epic id) {
        return epics.remove(id);
    }

    // Обновление статуса эпика в зависимости от статуса подзадач
    public void updateStatus(Subtask subtask) {
        ArrayList<Subtask> subtaskEpic = epics.get(subtask.getEpicID()).getSubtask();
        int cntDone = 0;
        int cntNEW = 0;
        for (Subtask objects : subtaskEpic) {
            if (objects.getStatus().equals(DONE) ) {
                cntDone++;
            } else if (objects.getStatus().equals(NEW)) {
                cntNEW++;
            }
        }
        if (cntDone == subtaskEpic.size()) {
            epics.get(subtask.getEpicID()).setStatus(DONE);
        } else if ( cntNEW == subtaskEpic.size()) {
            epics.get(subtask.getEpicID()).setStatus(NEW);
        } else {
            epics.get(subtask.getEpicID()).setStatus(IN_PROGRESS);
        }
    }
}














