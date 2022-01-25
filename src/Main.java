import controller.InMemoryTaskManager;
import controller.Managers;
import controller.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import static model.Status.NEW;

public class Main {
    public static void main(String[] args) {
        final TaskManager manager = Managers.getDefaul();


        System.out.println("Создаем задачу: ");
        Task task = new Task("Задача ", "Тестовая задача", 1, NEW);
        manager.createTask(task);
        System.out.println(manager.getAllTasks());

        System.out.println("Создаем эпик: ");
        manager.createEpic( new Epic("Эпик ", "тестовый эпик", 1));
        System.out.println(manager.getAllEpic());
        System.out.println("Создаем подзадачи:");
        Subtask subtask = new Subtask("Подзадача ", "тестовая подзадача", 1, NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача ", "тестовая подзадача", 2, NEW, 1);
        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);
        System.out.println(subtask);
        System.out.println(subtask2);

        manager.findSubtaskId(subtask.getID());
        System.out.println(manager.history());

        Epic epic = (Epic) manager.getAllEpic().get(0);

        System.out.println("Статусы: ");
        epic.getSubtask().get(0).setStatus(Status.IN_PROGRESS); //меняем статус у подзадачи 1 эпика
        System.out.println("Статус должен быть IN_PROGRESS, а на деле == " + epic.getStatus());;
        epic.testStatusType();
        epic.getSubtask().get(0).setStatus(Status.DONE); //меняем статус у подзадачи 1 эпика
        epic.getSubtask().get(1).setStatus(Status.DONE); //меняем статус у подзадачи 2 эпика
        System.out.println("Статус должен быть DONE, а на деле == " + epic.getStatus());
        epic.testStatusType();
        epic.getSubtask().clear();
        System.out.println("Удалили подзадачи: getSubtask().clear()");
        System.out.println("Статус должен быть NEW, а на деле == " + epic.getStatus());
        epic.testStatusType();

        System.out.println("История: ");
        if (manager.history().isEmpty()) {
            System.out.println("метод history не работает");
        }
        else {
            System.out.println("метод history работает");
        }


    }
}
