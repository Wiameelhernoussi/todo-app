package com.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.todo.Task.Priority;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public void createTask(String title, String description, Priority priority, LocalDate dueDate) {
        Task newTask = new Task(nextId++, title, description, priority, dueDate);
        tasks.add(newTask);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // Retourne une copie pour éviter les modifications externes
    }

    public boolean taskExists(long id) {
        return tasks.stream().anyMatch(task -> task.getId() == id);
    }

    public void deleteTask(long id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public void markTaskAsCompleted(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                break;
            }
        }
    }
}
