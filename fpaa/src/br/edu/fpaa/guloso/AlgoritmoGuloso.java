package br.edu.fpaa.guloso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.edu.fpaa.caminhao.Caminhao;

public class AlgoritmoGuloso {
	private int[] quilometragens;
	private List<Caminhao> caminhoes;
	
	public AlgoritmoGuloso(int[] quilometragens, int numeroDeCaminhoes) {
		this.quilometragens = quilometragens;
		this.caminhoes = new ArrayList<Caminhao>();
		this.carregarCaminhoes(numeroDeCaminhoes);
	}
	
	private void carregarCaminhoes(int numeroDeCaminhoes) {
		for(int i = 0; i < numeroDeCaminhoes; i++) {
			this.caminhoes.add(new Caminhao());
		}
	}

	public void reiniciarDadosDosCaminhoes() {
		int quantidadeDeCaminhoes = this.caminhoes.size();
		this.caminhoes = new ArrayList<Caminhao>();
		carregarCaminhoes(quantidadeDeCaminhoes);
	}
	
	public void distribuirRotasOrdenando() {
		Arrays.sort(this.quilometragens);
		int caminhaoAtual = 0;
		
		for(int i = 0; i < this.quilometragens.length; i++) {
			if(caminhaoAtual > this.caminhoes.size() - 1) {
				caminhaoAtual = 0;
			}
			this.caminhoes.get(caminhaoAtual++).add(this.quilometragens[i]);
		}
	}
	
	public void distribuirRotasParaCaminhaoComMenosRotas(Comparator<Caminhao> comparador) {
		for(int i = 0; i < this.quilometragens.length; i++) {
			Caminhao caminhaoComMenosRotas = this.caminhoes.stream().min(comparador).get();
			caminhaoComMenosRotas.add(this.quilometragens[i]);
		}
	}
	
	public List<Caminhao> obterCaminhoes() {
		return this.caminhoes;
	}
}
