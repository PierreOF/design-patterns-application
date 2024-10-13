package br.com.task.manager.model.Enum;

public enum TaskStatusEnum {
    PENDENTE("Pendente"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluida");

    private final String description;

    TaskStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TaskStatusEnum fromDescription(String description) {
        for (TaskStatusEnum category : TaskStatusEnum.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Descrição não encontrada: " + description);
    }
}
