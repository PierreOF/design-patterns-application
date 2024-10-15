package br.com.task.manager.model.Enum;

public enum TaskPriorityEnum {
    URGENTE("Urgente"),
    IMPORTANTE("Importante"),
    NORMAL("Normal"),
    BAIXA("Baixa");

    private final String description;

    TaskPriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TaskPriorityEnum fromDescription(String description) {
        for (TaskPriorityEnum category : TaskPriorityEnum.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        System.out.println("Descrição não encontrada");
        return null;
    }
}