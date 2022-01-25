package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtask = new ArrayList<>();


    public Epic(String name, String description, int ID) {
        super(name, description, ID);
    }


    public void createSubtask(Subtask task) {
        subtask.add(task);
    }


    public ArrayList<Subtask> getSubtask() {
        return subtask;
    }

    //для тестирования
    public void testStatusType() {
        int cntSize = subtask.size();
        int statusNew = 0;
        int statusDone = 0;
        for (Subtask st : subtask) {
            if (st.getStatus().equals("NEW")) {
                ++statusNew;
            } else if (st.getStatus().equals("DONE")) {
                ++statusDone;
            }
            // System.out.println("все статусы: " + st.getStatus());
        }
        System.out.println("cntSize = " + cntSize + " statusNew = " + statusNew + " statusDone = " + statusDone);
    }

    @Override
    public Status getStatus() {
        int cntSize = subtask.size();
        int statusNew = 0;
        int statusDone = 0;
        for (Subtask st : subtask) {
            if (st.getStatus().equals("NEW")) {
                ++statusNew;
            } else if (st.getStatus().equals("DONE")) {
                ++statusDone;
            }
        }
        // если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW
        if (subtask.size() == 0 || cntSize == statusNew) {
            return Status.NEW;
            // если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE
        } else if (cntSize == statusDone) {
            return Status.DONE;
        }
        // во всех остальных случаях статус должен быть IN_PROGRESS
        else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic {" +
                "name = '" + super.getName() + '\'' +
                ", description = '" + super.getDescription() + '\'' +
                ", id = " + ID +
                ", status = '" + super.getStatus() + '\'' +
                '}';
    }
}


