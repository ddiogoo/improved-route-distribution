package App;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Algoritmos.AlgoritmoGuloso;
import Classes.Caminhao;
import Geradores.GeradorDeProblemas;

/**
 * Classe que contém o Entry Point da nossa aplicação, ou seja, para executar o
 * programa você precisa executar essa classe e o método "main" localizado nela.
 */
public class Main {
	/**
	 * Propriedade privada da classe: HashMap que guarda o tamanho do conjunto com o
	 * tempo (em ms) de todas as 10 execuções para aquele conjunto para a primeira
	 * estratégia gulosa.
	 */
	private static Map<Integer, List<Long>> hashMapPrimeiroGuloso = new HashMap<Integer, List<Long>>();

	/**
	 * Propriedade privada da classe: HashMap que guarda o tamanho do conjunto com o
	 * tempo (em ms) de todas as 10 execuções para aquele conjunto para a segunda
	 * estratégia gulosa.
	 */
	private static Map<Integer, List<Long>> hashMapSegundoGuloso = new HashMap<Integer, List<Long>>();

	/**
	 * Propriedade privada da classe: Fila de caminhões que armazena dos dados de um
	 * determinado caminhão naquela execução.
	 */
	private static List<List<Caminhao>> filaDeCaminhoes = new ArrayList<List<Caminhao>>();

	/**
	 * Propriedade privada da classe: Array unidimensional de duas posições que
	 * guarda o tempo total gasto pela primeira estratégia gulosa na primeira
	 * posição e o tempo total gasto pela segunda estratégia gulosa na segunda
	 * posição.
	 */
	private static long[] temposTotais = new long[2];

	/**
	 * Propriedade privada da classe: Para cada tamanho T de conjunto, essa variável
	 * armazena o tempo total temporariamente.
	 */
	private static long tempoTotalPorT = 0;

	/**
	 * Propriedade privada da classe: Variável auxiliar que marca o início da
	 * execução do algoritmo.
	 */
	private static long tempoInicial = 0;

	/**
	 * Propriedade privada da classe: Variável auxiliar que marca o fim da execução
	 * do algoritmo.
	 */
	private static long tempoFinal = 0;

	/**
	 * Propriedade privada da classe: Variável auxiliar que guarda o tempo total
	 * gasto na primeira estratégia gulosa.
	 */
	private static long tempoTotalPrimeiraEstrategia = 0;

	/**
	 * Propriedade privada da classe: Variável auxiliar que guarda o tempo total
	 * gasto na segunda estratégia gulosa.
	 */
	private static long tempoTotalSegundaEstrategia = 0;

	/**
	 * Propriedade privada da classe: Entry point da nossa aplicação.
	 * 
	 * @param args Argumentos passados durante a chamda da nossa função.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		executarAlgoritmoGuloso(sc);
	}

	private static void executarAlgoritmoGuloso(Scanner sc) {
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

		System.out.println();

		System.out.print("Quantos conjuntos de resultados deseja mostrar? ");
		int quantidade = sc.nextInt();
		limparBuffer(sc);

		System.out.println(
				"Deseja mostrar valores repetidos [S / N]? (Existem 10 valores repetidos para cada tamanho T): ");
		String mostrarValoresRepetidos = sc.nextLine();
		limparBuffer(sc);
		sc.close();

		boolean mostrarRepetidos = mostrarValoresRepetidos.equals("N") ? true : false;
		if (quantidade > filaDeCaminhoes.size()) {
			quantidade = filaDeCaminhoes.size();
		}
		limparTela();
		mostrarDadosDoCaminhao(quantidade, mostrarRepetidos);
	}

	// #region Utilidades
	/**
	 * "Limpa" a tela (códigos de terminal VT-100).
	 */
	private static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Limpa o buffer do teclado.
	 * 
	 * @param sc Scanner usado para ler valores do teclado.
	 */
	private static void limparBuffer(Scanner sc) {
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.isEmpty()) {
				break;
			}
		}
	}
	// #endregion

	// #region Algoritmo Guloso
	/**
	 * Executa as duas estratégias gulosas, encapsulando seu funcionamento por meio
	 * de dois métodos distintos.
	 */
	private static void executarGuloso() {
		primeiraEstrategiaGulosa();
		segundaEstrategiaGulosa();
	}

	/**
	 * Executa a primeira estratégia gulosa, aumento o tamanho de T em T e
	 * executando 10 vezes cada conjunto de tamanho T.
	 */
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

				filaDeCaminhoes.add(algoritmoGuloso.obterCaminhoes());
				algoritmoGuloso.reiniciarDadosDosCaminhoes();
			}
			hashMapPrimeiroGuloso.put(T, execucoesDaPrimeiraEstrategia);
			T += primeiroValorDeT;
		}
		temposTotais[0] = tempoTotalPrimeiraEstrategia;
	}

	/**
	 * Executa a primeira estratégia gulosa, aumento o tamanho de T em T e
	 * executando 10 vezes cada conjunto de tamanho T.
	 */
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

				filaDeCaminhoes.add(algoritmoGuloso.obterCaminhoes());
				algoritmoGuloso.reiniciarDadosDosCaminhoes();
			}
			hashMapSegundoGuloso.put(T, execucoesDaSegundaEstrategia);
			T += primeiroValorDeT;
		}
		temposTotais[1] = tempoTotalSegundaEstrategia;
	}

	/**
	 * Mostrar os dados dos 3 caminhões.
	 * 
	 * @param quantidade          Quantidade de conjuntos de 3 caminhões que serão
	 *                            mostrados.
	 * @param naoMostrarRepetidos Para 1 conjunto dentro da fila de caminhões, há 10
	 *                            resultados com o mesmo valor, pois estamos
	 *                            executando 10 vezes com o mesmo conjunto de rotas,
	 *                            esse parâmetro permite que você mostre somente o
	 *                            primeiro resultado de cada conjunto para não
	 *                            confundir.
	 */
	private static void mostrarDadosDoCaminhao(int quantidade, boolean naoMostrarRepetidos) {
		int ordemCaminhao = 1;
		int quantidadeExecucoes = 0;
		if (naoMostrarRepetidos) {
			for (int i = 0; i < filaDeCaminhoes.size(); i += 10) {
				if (quantidadeExecucoes == quantidade) {
					break;
				}
				List<Caminhao> caminhoes = filaDeCaminhoes.get(i);
				for (Caminhao caminhao : caminhoes) {
					if (caminhoes == null)
						continue;
					System.out.print("Rotas do " + ordemCaminhao + "º caminhão: ");

					for (int rota : caminhao.getRotas()) {
						System.out.print(rota + "km; ");
					}
					System.out.println();

					System.out.println("Total de rotas: " + caminhao.totalDeRotas() + " km");
					ordemCaminhao++;
				}
				ordemCaminhao = 1;
				quantidadeExecucoes++;
				System.out.println();
			}
		} else {
			for (List<Caminhao> caminhoes : filaDeCaminhoes) {
				for (Caminhao caminhao : caminhoes) {
					if (caminhoes == null)
						continue;
					System.out.print("Rotas do " + ordemCaminhao + "º caminhão: ");

					for (int rota : caminhao.getRotas()) {
						System.out.print(rota + "km; ");
					}
					System.out.println();

					System.out.println("Total de rotas: " + caminhao.totalDeRotas() + " km");
					ordemCaminhao++;
				}
				if (quantidadeExecucoes > quantidade) {
					break;
				}
				ordemCaminhao = 1;
				quantidadeExecucoes++;
				System.out.println();
			}
		}
	}

	/**
	 * Gerar um array unidimensional de rotas, com base em uma Lista de arrays
	 * unidimensionais.
	 * 
	 * @param conjuntos Lista de arrays unidimensionais do tipo inteiro.
	 * @return Array unidimensional de valores do tipo inteiro.
	 */
	private static int[] gerarArrayUnidimensionalDeRotas(List<int[]> conjuntos) {
		int[] todasAsRotas = new int[conjuntos.size() * conjuntos.get(0).length];
		int indice = 0;
		for (int[] rotas : conjuntos) {
			for (int rota : rotas) {
				todasAsRotas[indice++] = rota;
			}
		}
		return todasAsRotas;
	}
	// #endregion
}
