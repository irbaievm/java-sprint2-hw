package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    static class Node {
        private final Task task;
        private Node last;
        private Node next;

        public Node(Task task) {
            this.task = task;
            this.last = null;
            this.next = null;
        }
    }

    private Node start = null;
    private Node end = null;
    private HashMap<Integer, Node> map = new HashMap<>();

    public InMemoryHistoryManager() {
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }


    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        linkLast(task);
    }

    private void linkLast(Task task) {
        // отдельный метод
        final Node oldNode = map.remove(task.getID());
        if (oldNode != null) {
            if (oldNode == start) {
                start = oldNode.next;

            } else if (oldNode == end) {
                end = oldNode.last;
                end.next = null;
            } else {
                oldNode.last.next = oldNode.next;
            }
        }

        final Node newNode = new Node(task);
        if (start == null) {
            start = newNode;
        } else {
            end.next = newNode;
            newNode.last = end;
        }
        end = newNode;
        map.put(task.getID(), newNode);
    }

    @Override
    public void delete(Integer id) {
        map.clear();
    }

    @Override
    public List<Task> getHistory() {
        final ArrayList<Task> tasks = new ArrayList<>();
        Node getNode = start;
        while (getNode != null) {
            tasks.add(getNode.task);
            getNode = getNode.next;
        }
        return tasks;
    }
}
