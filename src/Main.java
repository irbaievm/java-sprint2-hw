import controller.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import controller.Managers;


import static model.Status.NEW;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefaul();

        Task task01 = new Task("Пойти в магазин", "Необходимо пойти в магазин", 1, NEW);
        System.out.println("Создаем task01: ID task = " + task01.getID() + "; Name = " + task01.getName() + "; " + "Description" + " = " + task01.getDescription() + "; Status = " + task01.getStatus());
        manager.createTask(task01);
        System.out.println("task01 создан");
        System.out.println("Пробуем создать новую задачу с таким же ID = 1 ");
        Task task02 = new Task("Пойти в магазин", "Необходимо пойти в магазин", 1, NEW);
        manager.createTask(task02);
        System.out.println();
        Epic epic01 = new Epic("Выучить ин.язык", "Необходимо выучить новый язык", 1);
        System.out.println("Создаем epic01: ID epic = " + epic01.getID() + "; Name = " + epic01.getName() + "; Description = " + epic01.getDescription());
        manager.createEpic(epic01);
        System.out.println("epic01 создан");
        System.out.println();

        System.out.println("Создаем subtask01 и subtask02 к epic01");
        Subtask subtask01 = new Subtask("Составить план", "Составить план изучения", 1, NEW, 1);
        Subtask subtask02 = new Subtask("Выучить грамматику", "Необходимо выучить правила", 2, NEW, 1);
        manager.createSubtask(subtask01);
        manager.createSubtask(subtask02);
        System.out.println("Созданы subtask01 и subtask02;");
        System.out.println("");

        Epic epic02 = new Epic("Ремонт", "Необходимо сделать ремонт", 2);
        System.out.println("Создаем epic02: ID epic = " + epic02.getID() + "; Name = " + epic02.getName() + "; Description = " + epic02.getDescription());
        manager.createEpic(epic02);
        System.out.println("epic02 создан");
        System.out.println("Создаем subtask03 к epic02");
        Subtask subtask03 = new Subtask("Ремонт в одной комнате", "Необходимо сделать ремонт в " + "одной из комнат", 3, NEW, 2);
        manager.createSubtask(subtask03);
        System.out.println("subtask03 создан");
        System.out.println("");

        System.out.println("Проверка метода: getAllTasks())");
        System.out.println(manager.getAllTasks());
        System.out.println("");

        System.out.println("Проверка метода: getAllEpics())");
        System.out.print(manager.getAllEpics());
        System.out.println();

        System.out.println("Проверка метода: getAllSubtasks)");
        //  System.out.print(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Проверка метода: updateTask (меняем описание у задачи с id = 1)");
        Task task03 = new Task("Замена", "Замена", 1, NEW);
        manager.updateTask(task03);
        System.out.println(manager.getAllTasks());
        System.out.println("");

        System.out.println("Проверка метода: updateEpic (меняем описание у эпика с id = 1)");
        Epic epic03 = new Epic("Замена", "заменили название", 1);
        manager.updateEpic(epic03);
        System.out.println(manager.getAllEpics());
        System.out.println("");

        System.out.println("Проверка метода updateStatus (меняем статус у subtask01)");
        subtask01.setStatus(Status.IN_PROGRESS);
        manager.updateStatus(subtask01);
        System.out.println(manager.getAllEpics().get(0));
        System.out.println("");

        System.out.println("Проверка метода: updateSubtask (меняем subtask02( id =2 ))");
        Subtask subtask04 = new Subtask("замена1", "замена", 2, Status.IN_PROGRESS, 1);
        manager.updateSubtask(subtask04);
        subtask02.setStatus(Status.IN_PROGRESS);
        manager.updateStatus(subtask02);
        System.out.println(manager.getSubtasks());
        System.out.println("");

        System.out.println("Проверка эпиков и задач задач:");
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("Меняем статусы для подзадач");
        System.out.println(epic01.getSubtask());

        System.out.println("Статусы: ");

        Epic epic = manager.getAllEpics().get(0);

        epic.getSubtask().get(0).setStatus(Status.IN_PROGRESS); //меняем статус у подзадачи 1эпика
        System.out.println("Статус должен быть IN_PROGRESS, а на деле == " + epic.getStatus());
        ;
        epic.testStatusType();
        epic.getSubtask().get(0).setStatus(Status.DONE); //меняем статус у подзадачи 1 эпика
        epic.getSubtask().get(1).setStatus(Status.DONE); //меняем статус у подзадачи 2 эпика
        System.out.println("Статус должен быть DONE, а на деле == " + epic.getStatus());
        System.out.println(epic.getSubtask());
        epic.testStatusType();
        epic.getSubtask().clear();
        System.out.println("Удалили подзадачи: getSubtask().clear()");
        System.out.println("Статус должен быть NEW, а на деле == " + epic.getStatus());
        epic.testStatusType();

        System.out.println("");
        manager.findEpicId(1);
        manager.findSubtaskId(2);
        manager.findSubtaskId(2);


        System.out.println("Проверка истории: ");
     //   System.out.println(manager.history());
        //   System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getHistory());

    }
}
