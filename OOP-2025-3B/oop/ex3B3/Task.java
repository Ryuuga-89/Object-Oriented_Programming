package oop.ex3B3;

public class Task implements Comparable<Task> {
    String description;
    int priority;

    public Task(int priority, String description) {
        this.priority = priority;
        this.description = description;
    }

    @Override
    public String toString() {
        return priority + " " + description;
    }

    @Override
    public int compareTo(Task other) {
        if (this.priority == other.priority) {
            return this.description.compareTo(other.description);
        }
        
        return this.priority - other.priority;
    }
}
