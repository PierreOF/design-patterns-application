package br.com.task.manager.model;

import br.com.task.manager.model.Enum.TaskPriorityEnum;
import br.com.task.manager.model.Enum.TaskStatusEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String titulo;
    private String descricao;
    private TaskStatusEnum status;
    private int idUsuario;
    private TaskPriorityEnum priority;
    private final LocalDateTime creationDate;

    public Task(String titulo, String descricao, int idUsuario, TaskPriorityEnum priority) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = TaskStatusEnum.PENDENTE;
        this.idUsuario = idUsuario;
        this.priority = priority;
        this.creationDate = LocalDateTime.now();
    }

    public Task(int id, String titulo, String descricao, TaskStatusEnum status, int idUsuario, TaskPriorityEnum priority) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = idUsuario;
        this.priority = priority;
        this.creationDate = LocalDateTime.now();
    }

    public Task(int id, String titulo, String descricao, TaskStatusEnum status, TaskPriorityEnum priority, LocalDateTime creationDate, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = usuarioId;
        this.priority = priority;
        this.creationDate = creationDate;
    }

    public Task(String description, TaskPriorityEnum priority, LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TaskPriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityEnum priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm");
        String formattedDate = creationDate.format(formatter);

        return "==========================\n" +
                "ID:                " + id + "\n" +
                "Título:            " + titulo + "\n" +
                "Descrição:         " + descricao + "\n" +
                "Status:            " + status + "\n" +
                "Prioridade:        " + priority + "\n" +
                "Data de Criação:   " + formattedDate + "\n" +
                "==========================";
    }
}
