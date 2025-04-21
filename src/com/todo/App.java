package com.todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final TaskManager manager = new TaskManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🛠️ Gestionnaire de Tâches (Sans Base de Données)");
        
        while (true) {
            printMenu();
            int choice = readUserChoice();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> listTasks();
                case 3 -> deleteTask();
                case 4 -> markTaskCompleted();
                case 5 -> { 
                    scanner.close();
                    System.out.println("👋 Au revoir !");
                    return;
                }
                default -> System.out.println("❌ Option invalide.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Ajouter une tâche");
        System.out.println("2. Lister les tâches");
        System.out.println("3. Supprimer une tâche");
        System.out.println("4. Marquer comme terminée");
        System.out.println("5. Quitter");
        System.out.print("👉 Votre choix : ");
    }

    private static int readUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("❌ Entrée invalide. Réessayez.");
            return -1; // Retourne une valeur par défaut pour éviter des erreurs
        } finally {
            scanner.nextLine(); // Nettoie le buffer
        }
    }

    private static void addTask() {
        System.out.println("\n➕ Ajout d'une tâche");
        System.out.print("Titre : ");
        String title = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        Task.Priority priority = readPriority();
        LocalDate dueDate = readDueDate();

        manager.createTask(title, description, priority, dueDate);
        System.out.println("✅ Tâche ajoutée !");
    }

    private static Task.Priority readPriority() {
        while (true) {
            System.out.print("Priorité (HIGH/MEDIUM/LOW) : ");
            try {
                return Task.Priority.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Priorité invalide. Réessayez.");
            }
        }
    }

    private static LocalDate readDueDate() {
        while (true) {
            System.out.print("Date (AAAA-MM-JJ) : ");
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("❌ Format de date invalide. Réessayez.");
            }
        }
    }

    private static void listTasks() {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("\n📭 Aucune tâche enregistrée.");
            return;
        }

        System.out.println("\n📋 Liste des tâches :");
        System.out.println("ID\tTitre\tPriorité\tDate\tStatut");
        System.out.println("----------------------------------------");
        tasks.forEach(task -> {
            String status = task.isCompleted() ? "✅" : "🟡";
            System.out.printf(
                "%d\t%s\t%s\t%s\t%s\n",
                task.getId(),
                task.getTitle(),
                task.getPriority(),
                task.getDueDate(),
                status
            );
        });
    }

    private static void deleteTask() {
        System.out.print("\n❌ ID de la tâche à supprimer : ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Nettoie le buffer
        if (manager.taskExists(id)) {
            manager.deleteTask(id);
            System.out.println("🗑️ Tâche supprimée.");
        } else {
            System.out.println("❌ Tâche introuvable.");
        }
    }

    private static void markTaskCompleted() {
        System.out.print("\n✔️ ID de la tâche terminée : ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Nettoie le buffer
        if (manager.taskExists(id)) {
            manager.markTaskAsCompleted(id);
            System.out.println("🌟 Tâche marquée comme terminée.");
        } else {
            System.out.println("❌ Tâche introuvable.");
        }
    }
}