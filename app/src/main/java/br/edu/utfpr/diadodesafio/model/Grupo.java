package br.edu.utfpr.diadodesafio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grupo implements Serializable {

    private Long id;

    private String nome;

    private List<Usuario> usuarios = new ArrayList<>();


    public Grupo() {
    }

    public Grupo(Long id) {
        this.id = id;
    }

    public Grupo(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getUsuarios() { return usuarios; }

    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}
