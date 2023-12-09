package App;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Algoritmos.AlgoritmoGuloso;
import Classes.Caminhao;
import Geradores.GeradorDeProblemas;

public class Main {
	private static Map<Integer, List<Long>> hashMapPrimeiroGuloso = new HashMap<Integer, List<Long>>();
	private static Map<Integer, List<Long>> hashMapSegundoGuloso = new HashMap<Integer, List<Long>>();
	private static Long[] temposTotais = new Long[2];
	
	public static void main(String[] args) {
		executarGuloso();
		
		hashMapPrimeiroGuloso.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey() + ", Tempo (em ms) por execução: " + entry.getValue()));
		
		System.out.println();
		System.out.println("Tempo Total de execução: " + temposTotais[0] + " ms");
	}
	
	public static void executarGuloso() {
		long tempoTotalPorT = 0, tempoTotal = 0;
		
		int T = 6;
		int	dezVezesT = 6 * 10;
		int primeiroValorDeT = T;
		
		System.out.println("Rodando Algoritmo Guloso...");
		while(true) {
			List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(10, T, 0.5);
			int tamanho = conjuntos.size() * conjuntos.get(0).length;
			int[] todasAsRotas = new int[tamanho];
			
			int indice = 0;
			for(int[] rotas : conjuntos) {
				for(int rota : rotas) {
					todasAsRotas[indice++] = rota;
				}
			}
			
			if(T > dezVezesT) break;
			AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(todasAsRotas, 3);
			
			List<Long> execucoes = new ArrayList<Long>();
			for(int i = 0; i < 10; i++) {
				long tempoInicial = System.currentTimeMillis();
				algoritmoGuloso.distribuirRotasOrdenando();
				long tempoFinal = System.currentTimeMillis(); 
				tempoTotalPorT = (tempoFinal - tempoInicial);
				tempoTotal += tempoTotalPorT;
				execucoes.add(tempoTotalPorT);
			}
			hashMapPrimeiroGuloso.put(T, execucoes);
			T += primeiroValorDeT;
		}
		temposTotais[0] = tempoTotal;
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
