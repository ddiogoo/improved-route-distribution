package br.edu.fpaa.app;

import java.util.Comparator;
import java.util.List;

import br.edu.fpaa.caminhao.Caminhao;
import br.edu.fpaa.guloso.AlgoritmoGuloso;
import generators.GeradorDeProblemas;

public class Main {
	public static void main(String[] args) {
		executarGuloso();
	}
	
	public static void executarGuloso() {
		long tempoInicialDoPrimeiroGuloso = 0, tempoFinalDoPrimeiroGuloso = 0, tempoTotalDoPrimeiroGuloso = 0;
		long tempoInicialDoSegundoGuloso = 0, tempoFinalDoSegundoGuloso = 0, tempoTotalDoSegundoGuloso = 0;
		double tempoMedioPrimeiroGuloso, tempoMedioSegundoGuloso;
		
		int quantidadeDeExecuções = 10;
		for(int execucao = 1; execucao <= quantidadeDeExecuções; execucao++) {
			System.out.println(execucao + "º execução ===============================");
			List<int[]> sets = GeradorDeProblemas.geracaoDeRotas(10, 6, 1);
			int tamanho = sets.size() * sets.get(0).length;
			int[] todasAsRotas = new int[tamanho];
			
			int i = 0;
			for(int[] rotas : sets) {
				for(int rota : rotas) {
					todasAsRotas[i++] = rota;
				}
			}
			
			tempoInicialDoPrimeiroGuloso = System.currentTimeMillis();
			guloso1(todasAsRotas);
			tempoFinalDoPrimeiroGuloso = System.currentTimeMillis();
			System.out.println();
			tempoTotalDoPrimeiroGuloso += (tempoFinalDoPrimeiroGuloso - tempoInicialDoPrimeiroGuloso);
		}
		
		tempoMedioPrimeiroGuloso = (double)(tempoTotalDoPrimeiroGuloso / quantidadeDeExecuções);
		System.out.println("Tempo total: " + tempoTotalDoPrimeiroGuloso + " ms.");
		System.out.println("Tempo médio: " + tempoMedioPrimeiroGuloso + " ms.");
		
		for(int execucao = 1; execucao <= quantidadeDeExecuções; execucao++) {
			System.out.println(execucao + "º execução ===============================");
			List<int[]> sets = GeradorDeProblemas.geracaoDeRotas(10, 6, 1);
			int tamanho = sets.size() * sets.get(0).length;
			int[] todasAsRotas = new int[tamanho];
			
			int i = 0;
			for(int[] rotas : sets) {
				for(int rota : rotas) {
					todasAsRotas[i++] = rota;
				}
			}
			
			tempoInicialDoSegundoGuloso = System.currentTimeMillis();
			guloso2(todasAsRotas);
			tempoFinalDoSegundoGuloso = System.currentTimeMillis();
			System.out.println();
			tempoTotalDoSegundoGuloso += (tempoFinalDoSegundoGuloso - tempoInicialDoSegundoGuloso);
		}
		
		tempoMedioSegundoGuloso = (double)(tempoTotalDoSegundoGuloso / quantidadeDeExecuções);
		System.out.println("Tempo total: " + tempoTotalDoSegundoGuloso + " ms.");
		System.out.println("Tempo médio: " + tempoMedioSegundoGuloso + " ms.");
	}
	
	public static void guloso1(int[] todasAsRotas) {
		AlgoritmoGuloso guloso = new AlgoritmoGuloso(todasAsRotas, 3);
		guloso.distribuirRotasOrdenando();
		
		int ordemCaminhao = 1;
		List<Caminhao> caminhoes = guloso.obterCaminhoes();
		for(Caminhao caminhao : caminhoes) {
			System.out.print("Rotas do " + ordemCaminhao + "º caminhão: ");
			
			for(Integer rota : caminhao.getRotas()) {
				System.out.print(rota + "km; ");
			}
			System.out.println();
			
			System.out.println("Total de rotas: " + caminhao.totalDeRotas() + " km");
			ordemCaminhao++;
		}
	}
	
	public static void guloso2(int[] rotasAsRotas) {
		AlgoritmoGuloso guloso = new AlgoritmoGuloso(rotasAsRotas, 3);
		guloso.distribuirRotasParaCaminhaoComMenosRotas(new Comparator<Caminhao>() {
			@Override
			public int compare(Caminhao o1, Caminhao o2) {
				if(o1.totalDeRotas() > o2.totalDeRotas()) return 1;
				else return 0;
			}
		});
		
		int ordemCaminhao = 1;
		List<Caminhao> caminhoes = guloso.obterCaminhoes();
		for(Caminhao caminhao : caminhoes) {
			System.out.print("Rotas do " + ordemCaminhao + "º caminhão: ");
			
			for(Integer rota : caminhao.getRotas()) {
				System.out.print(rota + "km; ");
			}
			System.out.println();
			
			System.out.println("Total de rotas: " + caminhao.totalDeRotas() + " km");
			ordemCaminhao++;
		}
	}
}
