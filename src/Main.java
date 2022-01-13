import controller.Manager;
import model.Epic;
import model.Subtask;
import model.Task;

import static model.Status.NEW;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();


        System.out.println("Создаем задачу: ");
        Task task = new Task("Задача ", "Тестовая задача", 1, NEW);
        manager.createTask(task);
        System.out.println(manager.getAllTasks());

        System.out.println("создаем эпик: ");
        manager.createEpic( new Epic("Эпик ", "тестовый эпик", 1));
        System.out.println(manager.getAllEpic());
        System.out.println("создаем подзадачи:");
        Subtask subtask = new Subtask("Подзадача ", "тестовая подзадача", 1, NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача ", "тестовая подзадача", 2, NEW, 1);
        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);
        System.out.println(subtask);
        System.out.println(subtask2);

        Epic epic = (Epic) manager.getAllEpic().get(0);

        epic.getSubtask().get(0).setStatus("IN_PROGRESS"); //меняем статус у подзадачи 1 эпика
        System.out.println(epic.getStatus());

        System.out.println("Ошибка, статус должен быть == IN_PROGRESS, а на деле == " + epic.getStatus());
        epic.test();
/*
        System.out.println("OLD: ");
        manager.createEpic( new Epic("Эпик ", "тестовый эпик", 1));
        manager.createSubtask(new Subtask("Подзадача ", "тестовая подзадача", 1, NEW, 1));
        Epic epic = (Epic) manager.getAllEpic().get(0);
        System.out.println(epic);
*/

        //System.out.println(epic.getSubtask().get(0));
    }
}
