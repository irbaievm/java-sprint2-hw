package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    private Node first = null; //ссылка на 1 элемент
    private Node last = null; // ссылка на последний
    private HashMap<Integer, Node> nodeMap = new HashMap<>(); //ссылку на id задачи и ноду

    public InMemoryHistoryManager() {
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node end) {
        this.last = end;
    }

    @Override
    public List<Task> getHistory() {
        final ArrayList<Task> tasks = new ArrayList<>();
        Node getNode = first;
        while (getNode != null) {
            tasks.add(getNode.task);
            getNode = getNode.next;
        }
        return tasks;
    }

    @Override
    public void add(Task task) {

        if (task == null) {
            return;
        }
        linkLast(task);//вставляем таску в самый конец
    }

    @Override
    public void remove(Integer id) {
        nodeMap.remove(id);


    }

    private void linkLast(Task task) {
        // отдельный метод
        final Node oldNode = nodeMap.remove(task.getID());//удаляем дубль
        if (oldNode != null) {
            if (oldNode == first) {
                first = oldNode.next;

            } else if (oldNode == last) {
                last = oldNode.prev;
                last.next = null;
            } else {
                oldNode.prev.next = oldNode.next;
            }
        }

        final Node newNode = new Node(task);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        nodeMap.put(task.getID(), newNode);
    }

    private void removeNode(Node node) {
        //  в начале
        if (node == first) {
            if (node == last) {
                first = null;
                last = null;
                return;
            }

            final Node newFirst = first.next;
            newFirst.prev = null;
            first = newFirst;
            return;
        }
        // в конце
        if (node == last) {
            last = node.prev;
            node.next = null;
            return;
        }
        // в середине
        final Node prev = node.prev;
        final Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    public static class Node {
        private final Task task;
        private Node prev;
        private Node next;

        public Node(Task task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }
    }
}