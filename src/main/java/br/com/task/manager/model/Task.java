package br.com.task.manager.model;

public class Task {
    private int id;
    private String titulo;
    private String descricao;
    private String status;
    private int idUsuario;

    public Task(String titulo, String descricao, String status, int idUsuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = idUsuario;
    }

    public Task(int id, String titulo, String descricao, String status, int idUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() {
        return status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
