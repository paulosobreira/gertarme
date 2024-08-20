package br.nnpe.gertarme.model;

import java.io.Serializable;

/**
 * @author paulo.sobreira
 * 
 */
public class TaskBean implements Serializable {
	private static final long serialVersionUID = 5906072265693469296L;
	private long horaCriacao = System.currentTimeMillis();
	private long horaTarefa = System.currentTimeMillis() + 300000;
	private long horaConclusao = 0;
	private String descricao = "";
	private String tipoAtividade = "Desenvolvimento";
	private boolean ativada = true;

	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public void setHoraCriacao(long horaCriacao) {
		this.horaCriacao = horaCriacao;
	}

	public TaskBean() {
	}

	public long getHoraConclusao() {
		return horaConclusao;
	}

	public long getHoraCriacao() {
		return horaCriacao;
	}

	public void setHoraConclusao(long horaConclusao) {
		this.horaConclusao = horaConclusao;
	}

	public TaskBean(boolean ativada, String descricao, long tarefa) {
		this.ativada = ativada;
		this.descricao = descricao;
		horaTarefa = tarefa;
	}

	public boolean isAtivada() {
		return ativada;
	}

	public void setAtivada(boolean ativada) {
		this.ativada = ativada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getHoraTarefa() {
		return horaTarefa;
	}

	public void setHoraTarefa(long horaTarefa) {
		this.horaTarefa = horaTarefa;
	}
}
