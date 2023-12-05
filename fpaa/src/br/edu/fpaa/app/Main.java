package br.edu.fpaa.app;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fpaa.caminhao.Caminhao;
import br.edu.fpaa.guloso.AlgoritmoGuloso;
import generators.GeradorDeProblemas;

public class Main {
	public static void main(String[] args) {
		executarGuloso();
	}
	
	public static void executarGuloso() {
		Map<Integer, Long> hm = new HashMap<Integer, Long>();
		long tempoTotal = 0;
		boolean lock = true;
		int T = 6;
		int dezVezesT = T * 10;
		
		System.out.println("Rodando Algoritmo Guloso...");
		while(lock) {
			List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(10, T, 1);
			int tamanho = conjuntos.size() * conjuntos.get(0).length;
			int[] todasAsRotas = new int[tamanho];
			
			int indice = 0;
			for(int[] rotas : conjuntos) {
				for(int rota : rotas) {
					todasAsRotas[indice++] = rota;
				}
			}
			
			if(T == dezVezesT) break;
			
			AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(todasAsRotas, 3);
			long tempoInicial = System.currentTimeMillis();
			algoritmoGuloso.distribuirRotasOrdenando();
			long tempoFinal = System.currentTimeMillis(); 
			tempoTotal += (tempoFinal - tempoInicial);
			hm.put(T, tempoTotal);
			T *= 2;
		}
		
		hm.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey() + ", Tempo (em ms): " + entry.getValue()));
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
		Comparator<Caminhao> comparador = new Comparator<Caminhao>() {
			@Override
			public int compare(Caminhao o1, Caminhao o2) {
				if(o1.totalDeRotas() > o2.totalDeRotas()) return 1;
				else return 0;
			}
		};
		guloso.distribuirRotasParaCaminhaoComMenosRotas(comparador);
		
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
