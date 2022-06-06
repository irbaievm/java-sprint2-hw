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
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager history = Managers.getDefaultHistory();


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

    //создание задачи
    @Override
    public Task createTask(Task task) {
        int ID = ++generatorTaskID;
        final Task value = new Task(task.getName(), task.getDescription(), ID, NEW);
        if (tasks.containsKey(task.getID())) {
            System.out.println("Задача с таким ID существует = " + task.getID());
            return null;
        } else {
            tasks.put(task.getID(), new Task(task));
        }
        return value;

    }

    // создание эпика
    @Override
    public Epic createEpic(Epic epic) {
        int ID = ++generatorEpicID;
        final Epic value = new Epic(epic.getName(), epic.getDescription(), ID);
        if (epics.containsKey(value.getID())) {
            System.out.println("Эпик с таким ID уже существует = " + epic.getID());
        } else {
            epics.put(value.getID(), value);

        }
        return value;
    }

    //создание подзадачи
    @Override
    public Subtask createSubtask(Subtask subtask) {
        int ID = ++generatorSubtaskID;
        Subtask value = new Subtask(subtask.getName(), subtask.getDescription(), ID, NEW, subtask.getEpicID());
        if (subtasks.containsKey(subtask.getID())) {
            System.out.println("Subtask с таким ID существует = " + subtask.getID());
        }
        if (!epics.containsKey(subtask.getEpicID())) {
            System.out.println("Эпик с таким ID не найден: " + subtask.getEpicID());
        }
        subtasks.put(subtask.getID(), value);
        final Epic epic = epics.get(subtask.getEpicID());
        epic.createSubtask(subtask); //для добавления в мапу
        return value;
    }

    //  Получение списка всех задач
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //  Получение списка всех эпиков
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //	Получение списка всех подзадач определённого эпика.
    @Override
    public List<Subtask> getAllSubtasksEpics(Epic epic) {
        return epic.getSubtask();
    }

    //  Получение задачи по id

    @Override
    public Task findTaskId(Integer id) {
        final Task task = tasks.get(id);
        addHistory(task);
        return task;
    }

    //  Получение подзадачи по id
    @Override
    public Subtask findSubtaskId(Integer id) {
        final Subtask subTask = subtasks.get(id);
        addHistory(subTask);
        return subTask;
    }

    //  Получение эпика по id
    @Override
    public Epic findEpicId(Integer id) {
        final Epic epic = epics.get(id);
        addHistory(epic);
        return epic;
    }

    //  Обновление задачи любого типа по id. Новая версия объекта передаётся в виде параметра
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

    //  Обновление эпика любого типа по id. Новая версия объекта передаётся в виде параметра (new)
    @Override
    public Epic updateEpic(Epic changedEpic) {
        Epic saveEpic = epics.get(changedEpic.getID());
        if (saveEpic == null) {
            return null;
        }
        saveEpic.setDescription(changedEpic.getDescription());
        saveEpic.setName(changedEpic.getName());
        saveEpic.setStatus(changedEpic.getStatus());
        return saveEpic;
    }

    // Обновление подзадач любого типа по id. Новая версия объекта передаётся в виде параметра (new)
    @Override
    public Subtask updateSubtask(Subtask changedSubtask) {
        Subtask saveSubtask = subtasks.get(changedSubtask.getID());
        if (saveSubtask == null) {
            return null;
        }
        saveSubtask.setDescription(changedSubtask.getDescription());
        saveSubtask.setName(changedSubtask.getName());
        saveSubtask.setStatus(changedSubtask.getStatus());
        updateStatus(changedSubtask);
        return saveSubtask;
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

    //  Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void deleteAllTask() {
        for (Task task : tasks.values()) {
            history.remove(task.getID());
        }
        tasks.clear();

    }

    @Override
    public void deleteTaskId(int id) {
        history.remove(id);
        tasks.remove(id);

    }

    @Override
    public void deleteAllSubtask() {
        for (Subtask subtask : subtasks.values()) {
            history.remove(subtask.getID());
            subtasks.remove(subtask.getID());
            updateStatus(subtask);
        }
    }

    @Override
    public void deleteSubtaskID(int id) {
        history.remove(id);
        subtasks.remove(id);
        updateStatus(subtasks.get(id));
    }

    @Override
    public void deleteAllEpic() {
        for (int epicId : epics.keySet()) {
            ArrayList<Subtask> epicSubtasks = epics.get(epicId).getSubtask();//получаем все подзадачи
            for (Subtask subtask : epicSubtasks) {
                subtasks.remove(subtask.getID());
                history.remove(subtask.getID());
            }
            history.remove(epicId);
        }
        epics.clear();
    }

    @Override
    public void deleteEpicId(int id) {
        epics.remove(id);
        ArrayList<Subtask> epicSubtasks = (epics.get(id)).getSubtask();//получаем все подзадачи
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getID());
            history.remove(subtask.getID());
        }
        history.remove(id);
        epics.remove(id);
    }


    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    private void addHistory(Task task) {
        if (task == null) {
            return;
        }
        history.add(task); // Добавить в конец
    }


}