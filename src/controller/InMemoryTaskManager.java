package controller;

import model.Status;
import model.Task;
import model.Subtask;
import model.Epic;

import static model.Status.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private List<Task> history = new LinkedList<>();

    int generatorTaskID = 0;
    int generatorSubtaskID = 0;
    int generatorEpicID = 0;


    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    @Override
    public Task createTask(Task task) {
        int ID = ++generatorTaskID;
        final Task value = new Task(task.getName(), task.getDescription(), ID, NEW);
        if (tasks.containsKey(task.getID())) {
            System.out.println("Задача с таким ID существует = " + task.getID());
            return null;
        }
        tasks.put(task.getID(), new Task(task));
        return value;

    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        int ID = ++generatorSubtaskID;
        Subtask value = new Subtask(subtask.getName(), subtask.getDescription(), ID, Status.NEW, subtask.getEpicID());
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

    @Override
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
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //  Получение списка всех эпиков.
    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    //	Получение списка всех подзадач определённого эпика.
    @Override
    public List<Subtask> getAllSubtaskEpics(Epic epic) {
        return epic.getSubtask();
    }


    //  Получение задачи любого типа по идентификатору.
    @Override
    public Task findTaskId(Integer id) {
        final Task task = tasks.get(id);
        addHistory(task);
        return task;
    }

    @Override
    public Subtask findSubtaskId(Integer id) {
        final Subtask subTask = subtasks.get(id);
        addHistory(subTask);
        return subTask;
    }

    @Override
    public Epic findEpicId(Integer id) {
        final Epic epic = epics.get(id);
        addHistory(epic);
        return epic;
    }

    //  Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    @Override
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

    @Override
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

    @Override
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
    @Override
    public void deleteAllTask(Task task) {
        tasks.clear();
    }

    @Override
    public Task deleteTaskId(Task id) {
        return tasks.remove(id);
    }

    @Override
    public void deleteAllSubtask(Subtask subtask) {
        subtasks.clear();
    }

    @Override
    public Task deleteAllSubtaskID(Task id) {
        return subtasks.remove(id);
    }

    @Override
    public void deleteAllEpic(Epic epic) {
        epics.clear();
    }

    @Override
    public Task deleteTaskId(Epic id) {
        return epics.remove(id);
    }

    // Обновление статуса эпика в зависимости от статуса подзадач
    @Override
    public void updateStatus(Subtask subtask) {
        List<Subtask> subtaskEpic = epics.get(subtask.getEpicID()).getSubtask();
        int cntDone = 0;
        int cntNEW = 0;
        for (Subtask objects : subtaskEpic) {
            if (objects.getStatus().equals(DONE)) {
                cntDone++;
            } else if (objects.getStatus().equals(NEW)) {
                cntNEW++;
            }
        }
        if (cntDone == subtaskEpic.size()) {
            epics.get(subtask.getEpicID()).setStatus(DONE);
        } else if (cntNEW == subtaskEpic.size()) {
            epics.get(subtask.getEpicID()).setStatus(NEW);
        } else {
            epics.get(subtask.getEpicID()).setStatus(IN_PROGRESS);
        }
    }

    @Override
    public List<Task> history() {
        return history;
    }

    public void addHistory(Task task) {
        if (task == null) {
        }
        if (history.size() == 10) {
            history.remove(0); // Удалить в начале
        }
        history.add(task); // Добавить в конец
    }
}














