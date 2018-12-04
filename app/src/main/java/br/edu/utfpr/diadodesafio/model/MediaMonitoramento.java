package br.edu.utfpr.diadodesafio.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MediaMonitoramento implements Serializable {

    private Long id;

    private Usuario usuario;

    private LocalDate data;

    private Double mediaGeral;

    private List<Monitoramento> monitoramento = new ArrayList<>();

    public MediaMonitoramento() {
    }

    public MediaMonitoramento(Long id) {
        this.id = id;
    }

    public MediaMonitoramento(Long id, Usuario usuario, LocalDate data, Double mediaGeral) {
        this.id = id;
        this.usuario = usuario;
        this.data = data;
        this.mediaGeral = mediaGeral;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getMediaGeral() {
        return mediaGeral;
    }

    public void setMediaGeral(Double mediaGeral) {
        this.mediaGeral = mediaGeral;
    }

    public List<Monitoramento> getMonitoramento() {
        return monitoramento;
    }

    public void setMonitoramento(List<Monitoramento> monitoramento) {
        this.monitoramento = monitoramento;
    }

    @Override
    public String toString() {
        return getUsuario().getNome() + " - " + getData() + " - " + getMediaGeral() + " - " + getMonitoramento();
    }
}
