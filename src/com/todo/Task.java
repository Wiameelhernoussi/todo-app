package com.todo;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private boolean isCompleted;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Constructeur
    public Task(Long id, String title, String description, Priority priority, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Getters & Setters (générés avec VS Code : Ctrl+Shift+P > "Generate Getters and Setters")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nTitre: %s\nDescription: %s\nPriorité: %s\nDate d'échéance: %s\nStatut: %s",
            id, title, description, priority, dueDate, isCompleted ? "Terminée" : "En cours");
    }
}
