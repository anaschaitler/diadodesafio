package br.edu.utfpr.diadodesafio.model;

import java.io.Serializable;

public class UsuarioGrupo implements Serializable {

    private Long id;

    private Usuario usuario;

    private Grupo grupo;

    public UsuarioGrupo() {
    }

    public UsuarioGrupo(Long id) {
        this.id = id;
    }

    public UsuarioGrupo(Long id, Usuario usuario, Grupo grupo) {
        this.id = id;
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return getGrupo() + " - " + getUsuario();
    }
}
