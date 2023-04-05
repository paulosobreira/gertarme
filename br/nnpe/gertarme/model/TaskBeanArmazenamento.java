package br.nnpe.gertarme.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


public class TaskBeanArmazenamento implements Serializable {
    private ArrayList concluidosData = new ArrayList();
    private ArrayList pendenciasData = new ArrayList();
    private String nomeTarefas;
    private String nomeArquivo;
    private String notas = "";

    public TaskBeanArmazenamento() {
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeTarefas() {
        return nomeTarefas;
    }

    public void setNomeTarefas(String nomeTarefas) {
        this.nomeTarefas = nomeTarefas;
    }

    public ArrayList getConcluidosData() {
        return concluidosData;
    }

    public void setConcluidosData(ArrayList concluidosData) {
        this.concluidosData = concluidosData;
    }

    public ArrayList getPendenciasData() {
        return pendenciasData;
    }

    public void setPendenciasData(ArrayList pendenciasData) {
        this.pendenciasData = pendenciasData;
    }
}
