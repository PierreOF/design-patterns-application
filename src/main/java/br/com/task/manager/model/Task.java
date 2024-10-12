package br.com.task.manager.model;

public abstract class Task {
    private String description;
    private boolean isCompleted;

    protected Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    // Método abstrato para ser implementado pelas subclasses
    public abstract void performTaskTypeSpecificOperation();
}