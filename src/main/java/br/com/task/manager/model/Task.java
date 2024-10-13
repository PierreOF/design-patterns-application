package br.com.task.manager.model;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String titulo;
    private String descricao;
    private String status;
    private int idUsuario;
    private String priority;
    private boolean completed;
    private final LocalDateTime creationDate;

    // Construtor principal, usado para criar uma tarefa com base nos dados do usuário
    public Task(String titulo, String descricao, String status, int idUsuario, String priority) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = idUsuario;
        this.priority = priority;
        this.completed = false; // Por padrão, a tarefa começa como não concluída
        this.creationDate = LocalDateTime.now(); // A data de criação é definida no momento da criação da tarefa
    }

    // Construtor adicional que inclui o ID da tarefa
    public Task(int id, String titulo, String descricao, String status, int idUsuario, String priority) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = idUsuario;
        this.priority = priority;
        this.completed = false;
        this.creationDate = LocalDateTime.now();
    }

    public Task(int id, String titulo, String descricao, String status, String priority, boolean completed, LocalDateTime creationDate, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = usuarioId;
        this.priority = priority;
        this.completed = completed;
        this.creationDate = creationDate;
    }

    // Getters e Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", status='" + status + '\'' +
                ", idUsuario=" + idUsuario +
                ", priority='" + priority + '\'' +
                ", completed=" + completed +
                ", creationDate=" + creationDate +
                '}';
    }
}
