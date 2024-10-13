package br.com.task.manager.model.Enum;

public enum TaskStatusEnum {
    PENDENTE("Pendente"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluida");

    private String statusName;

    TaskStatusEnum(String displayName) {
        this.statusName = displayName;
    }

    public String getStatusName() {
        return statusName;
    }
}
