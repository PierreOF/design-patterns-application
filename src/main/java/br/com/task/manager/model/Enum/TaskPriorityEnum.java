package br.com.task.manager.model.Enum;

public enum TaskPriorityEnum {
    URGENTE("Urgente"),
    IMPORTANTE("Importante"),
    NORMAL("Normal"),
    BAIXA("Baixa");

    private String displayName;

    TaskPriorityEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}