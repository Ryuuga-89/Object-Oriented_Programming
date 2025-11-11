package oop.ex3B3;

public class TaskList {
    private final java.util.List<Task> tasks;

    public TaskList() {
        this.tasks = new java.util.ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        tasks.sort(null);
    }

    public boolean removeTask(int index) {
        if (index < 1 || index > tasks.size()) {
            return false;
        }
        tasks.remove(index - 1);
        return true;
    }

    public void printList() {
        System.out.println("Task list");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i).toString());
        }
    }
}
