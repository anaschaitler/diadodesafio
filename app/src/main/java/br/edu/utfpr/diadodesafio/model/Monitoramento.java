package br.edu.utfpr.diadodesafio.model;

import android.provider.MediaStore;

import java.io.Serializable;
import java.time.LocalDate;

public class Monitoramento implements Serializable {

    private Long id;

    private String localizacao;

    private Usuario usuario;

    private LocalDate data;

    private Double mediaMonitora;

    private MediaMonitoramento mediaMonitoramento;

    public Monitoramento() {
    }

    public Monitoramento(Long id) {
        this.id = id;
    }

    public Monitoramento(Long id, String localizacao, Usuario usuario, LocalDate data, Double mediaMonitora) {
        this.id = id;
        this.localizacao = localizacao;
        this.usuario = usuario;
        this.data = data;
        this.mediaMonitora = mediaMonitora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
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

    public Double getMediaMonitora() {
        return mediaMonitora;
    }

    public void setMediaMonitora(Double mediaMonitora) {
        this.mediaMonitora = mediaMonitora;
    }

    public MediaMonitoramento getMediaMonitoramento() {
        return mediaMonitoramento;
    }

    public void setMediaMonitoramento(MediaMonitoramento mediaMonitoramento) {
        this.mediaMonitoramento = mediaMonitoramento;
    }
}
