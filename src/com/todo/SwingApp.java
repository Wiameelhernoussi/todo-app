package com.todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class SwingApp {
    private static TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Gestionnaire de Tâches");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Liste des tâches avec modèle
        DefaultListModel<Task> listModel = new DefaultListModel<>();
        JList<Task> taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskListRenderer());
        refreshTaskList(listModel);

        // Formulaire d'ajout
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        JComboBox<Task.Priority> priorityCombo = new JComboBox<>(Task.Priority.values());
        JTextField dateField = new JTextField(LocalDate.now().toString());

        formPanel.add(new JLabel("Titre:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Priorité:"));
        formPanel.add(priorityCombo);
        formPanel.add(new JLabel("Date (AAAA-MM-JJ):"));
        formPanel.add(dateField);

        // Boutons
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> {
            try {
                manager.createTask(
                    titleField.getText(),
                    descField.getText(),
                    (Task.Priority) priorityCombo.getSelectedItem(),
                    LocalDate.parse(dateField.getText())
                );
                refreshTaskList(listModel);
                titleField.setText("");
                descField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage());
            }
        });

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                manager.deleteTask(selected.getId());
                refreshTaskList(listModel);
            }
        });

        // Assemblage
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void refreshTaskList(DefaultListModel<Task> model) {
        model.clear();
        manager.getAllTasks().forEach(model::addElement);
    }

    // Pour un affichage personnalisé des tâches
    static class TaskListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Task task = (Task) value;
            String text = String.format("<html><b>%s</b> - %s<br><small>Priorité: %s | Date: %s | Statut: %s</small></html>",
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate(),
                task.isCompleted() ? "✅ Terminé" : "🟡 En cours"
            );
            return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
        }
    }
}