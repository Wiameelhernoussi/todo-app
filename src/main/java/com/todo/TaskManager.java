package main.java.com.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.todo.Task;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Pour générer des IDs uniques

    // Crée une nouvelle tâche
    public Task createTask(String title, String description, Task.Priority priority, LocalDate dueDate) {
        Task task = new Task(idCounter.getAndIncrement(), title, description, priority, dueDate);
        tasks.add(task);
        return task;
    }

    // Supprime une tâche par son ID
    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    // Récupère toutes les tâches
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // Retourne une copie pour éviter les modifications externes
    }

    // Marque une tâche comme terminée
    public void markTaskAsCompleted(Long id) {
        tasks.stream()
            .filter(task -> task.getId().equals(id))
            .findFirst()
            .ifPresent(task -> task.setCompleted(true));
    }

    // Vérifie si une tâche existe par son ID
    public boolean taskExists(Long id) {
        return tasks.stream()
                .anyMatch(task -> task.getId().equals(id));
    }

    // Récupère une tâche spécifique par son ID
    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Récupère les tâches non complétées
    public List<Task> getIncompleteTasks() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }

    // Récupère les tâches complétées
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .toList();
    }
}