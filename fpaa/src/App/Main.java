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
	private static long[] temposTotais = new long[2];
	private static long tempoTotalPorT = 0;
	private static long tempoInicial = 0;
	private static long tempoFinal = 0;
	private static long tempoTotalPrimeiraEstrategia = 0;
	private static long tempoTotalSegundaEstrategia = 0;

	public static void main(String[] args) {
		executarGuloso();

		hashMapPrimeiroGuloso.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey()
						+ ", Tempo (em ms) por execução: " + entry.getValue()));

		System.out.println();

		hashMapSegundoGuloso.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey()
						+ ", Tempo (em ms) por execução: " + entry.getValue()));

		System.out.println();
		System.out.println("Tempo Total da primeira estratégia gulosa: " + temposTotais[0] + " ms");
		System.out.println("Tempo Total da segunda estratégia gulosa: " + temposTotais[1] + " ms");
	}

	public static void executarGuloso() {
		primeiraEstrategiaGulosa();
		segundaEstrategiaGulosa();
	}

	private static void primeiraEstrategiaGulosa() {
		int T = 6;
		int dezVezesT = 6 * 10;
		int primeiroValorDeT = T;

		while (true) {
			List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(10, T, 0.5);
			int[] todasAsRotas = gerarArrayUnidimensionalDeRotas(conjuntos);

			if (T > dezVezesT)
				break;
			AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(todasAsRotas, 3);

			List<Long> execucoesDaPrimeiraEstrategia = new ArrayList<Long>();
			for (int i = 0; i < 10; i++) {
				tempoInicial = System.currentTimeMillis();
				algoritmoGuloso.distribuirRotasOrdenando();
				tempoFinal = System.currentTimeMillis();
				tempoTotalPorT = (tempoFinal - tempoInicial);
				tempoTotalPrimeiraEstrategia += tempoTotalPorT;
				execucoesDaPrimeiraEstrategia.add(tempoTotalPorT);
			}
			hashMapPrimeiroGuloso.put(T, execucoesDaPrimeiraEstrategia);
			T += primeiroValorDeT;
		}
		temposTotais[0] = tempoTotalPrimeiraEstrategia;
	}

	private static void segundaEstrategiaGulosa() {
		Comparator<Caminhao> comparador = new Comparator<Caminhao>() {
			@Override
			public int compare(Caminhao o1, Caminhao o2) {
				if (o1.totalDeRotas() > o2.totalDeRotas())
					return 1;
				else
					return 0;
			}
		};
		int T = 6;
		int dezVezesT = 6 * 10;
		int primeiroValorDeT = T;

		while (true) {
			List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(10, T, 0.5);
			int[] todasAsRotas = gerarArrayUnidimensionalDeRotas(conjuntos);

			if (T > dezVezesT)
				break;
			AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(todasAsRotas, 3);

			List<Long> execucoesDaSegundaEstrategia = new ArrayList<Long>();
			for (int i = 0; i < 10; i++) {
				tempoInicial = System.currentTimeMillis();
				algoritmoGuloso.distribuirRotasParaCaminhaoComMenosRotas(comparador);
				tempoFinal = System.currentTimeMillis();
				tempoTotalPorT = (tempoFinal - tempoInicial);
				tempoTotalSegundaEstrategia += tempoTotalPorT;
				execucoesDaSegundaEstrategia.add(tempoTotalPorT);
			}
			hashMapSegundoGuloso.put(T, execucoesDaSegundaEstrategia);
			T += primeiroValorDeT;
		}
		temposTotais[1] = tempoTotalSegundaEstrategia;
	}

	private static void mostrarDadosDoCaminhao(AlgoritmoGuloso algoritmoGuloso) {
		int ordemCaminhao = 1;
		List<Caminhao> caminhoes = algoritmoGuloso.obterCaminhoes();
		for (Caminhao caminhao : caminhoes) {
			System.out.print("Rotas do " + ordemCaminhao + "º caminhão: ");

			for (Integer rota : caminhao.getRotas()) {
				System.out.print(rota + "km; ");
			}
			System.out.println();

			System.out.println("Total de rotas: " + caminhao.totalDeRotas() + " km");
			ordemCaminhao++;
		}
	}

	private static int[] gerarArrayUnidimensionalDeRotas(List<int[]> conjuntos) {
		int tamanho = conjuntos.size() * conjuntos.get(0).length;
		int[] todasAsRotas = new int[tamanho];

		int indice = 0;
		for (int[] rotas : conjuntos) {
			for (int rota : rotas) {
				todasAsRotas[indice++] = rota;
			}
		}
		return todasAsRotas;
	}
}
