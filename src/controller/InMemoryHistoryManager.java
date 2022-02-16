package controller;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    static class Node {
        final Task task;
        Node last;
        Node next;

        public Node(Task task) {
            this.task = task;
            this.last = null;
            this.next = null;
        }
    }
    public InMemoryHistoryManager() {
    }

    Node start = null;
    Node end = null;
    HashMap<Integer, Node> map = new HashMap<>();

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
