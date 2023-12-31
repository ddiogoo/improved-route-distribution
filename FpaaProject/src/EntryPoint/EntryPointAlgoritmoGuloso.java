package EntryPoint;

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
 * Entry point onde o algoritmo guloso será executado, o intuito dessa classe é
 * encapsular propriedades e métodos privados para a execução do algoritmo
 * guloso.
 */
public class EntryPointAlgoritmoGuloso {
    /**
     * Lê valores do teclado.
     */
    static Scanner sc = new Scanner(System.in);

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
     * Primeiro conjunto de testes do trabalho.
     */
    private static int[] primeiroConjuntoDeRotas = { 40, 36, 38, 29, 32, 28, 31, 35, 31, 30, 32, 30, 29, 39, 35, 38, 39,
            35, 32, 38, 32, 33, 29, 33, 29, 39, 28 };

    /**
     * Segundo conjunto de teste do trabalho.
     */
    private static int[] segundoConjuntoDeTeste = { 32, 51, 32, 43, 42, 30, 42, 51, 43, 51, 29, 25, 27, 32, 29, 55, 43,
            29, 32, 44, 55, 29, 53, 30, 24, 27 };

    private static List<List<Caminhao>> caminhoesDoPrimeiroConjuntoDeTeste = new ArrayList<>();
    private static List<List<Caminhao>> caminhoesDoSegundoConjuntoDeTeste = new ArrayList<>();

    /**
     * "Limpa" a tela (códigos de terminal VT-100).s
     */
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Executar o algoritmo guloso usando as duas estratégias.
     */
    public static void executarAlgoritmoGuloso() {
        System.out.println(
                "Qual parte do trabalho você deseja executar?");
        System.out.println("1) Etapas descritas na especificação do trabalho.");
        System.out.println("2) Conjuntos de teste enviado por email.");
        System.out.print("Digite o número referente a opção: ");
        int opcao = sc.nextInt();

        limparTela();
        switch (opcao) {
            case 1:
                primeiraEstrategiaGulosa();
                segundaEstrategiaGulosa();

                System.out.println("DADOS OBTIDOS AO EXECUTAR A PRIMEIRA ESTRATÉGIA (ORDENANDO O ARRAY DE ROTAS)...");
                hashMapPrimeiroGuloso.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey()
                                + ", Tempo (em ms) por execução: " + entry.getValue() + " Média (em ms): "
                                + ((double) (entry.getValue().stream().mapToLong(Long::valueOf).sum()) / 10)));

                System.out.println();
                System.out.println(
                        "DADOS OBTIDOS AO EXECUTAR A SEGUNDA ESTRATÉGIA (PROCURANDO O CAMINHÃO COM MENOR TOTAL DE ROTAS)...");
                hashMapSegundoGuloso.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> System.out.println("Quantidade de Rotas: " + entry.getKey()
                                + ", Tempo (em ms) por execução: " + entry.getValue() + " Média (em ms): "
                                + ((double) (entry.getValue().stream().mapToLong(Long::valueOf).sum()) / 10)));

                System.out.println();
                System.out.println("Tempo Total da primeira estratégia gulosa: " + temposTotais[0] + " ms");
                System.out.println("Tempo Total da segunda estratégia gulosa: " + temposTotais[1] + " ms");

                limparTela();
                mostrarDadosDoCaminhao();
                sc.close();
                break;
            case 2:
                executarComBaseNoPrimeiroConjuntoDeTestes();
                executarComBaseNoSegundoConjuntoDeTestes();
                mostrarDadosDoCaminhaoObtidosNoPrimeiroConjuntoDeTeste();
                mostrarDadosDoCaminhaoObtidosNoSegundoConjuntoDeTeste();
                break;
            default:
                System.out.println("Opção não encontrada.");
                System.exit(1);
                break;
        }
    }

    /**
     * Executa a primeira estratégia gulosa, aumento o tamanho de T em T e
     * executando 10 vezes cada conjunto de tamanho T.
     */
    private static void primeiraEstrategiaGulosa() {
        int T = 27;
        int dezVezesT = T * 10;
        boolean adicionouNaFila = false;
        while (true) {
            List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(T, 10, 0.5);
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

                if (!adicionouNaFila) {
                    filaDeCaminhoes.add(algoritmoGuloso.obterCaminhoes());
                    adicionouNaFila = true;
                }
                algoritmoGuloso.reiniciarDadosDosCaminhoes();
            }
            adicionouNaFila = false;
            hashMapPrimeiroGuloso.put(T, execucoesDaPrimeiraEstrategia);
            T += 27;
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
        int T = 27;
        int dezVezesT = T * 10;
        boolean adicionouNaFila = false;
        while (true) {
            List<int[]> conjuntos = GeradorDeProblemas.geracaoDeRotas(T, 10, 0.5);
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

                if (!adicionouNaFila) {
                    filaDeCaminhoes.add(algoritmoGuloso.obterCaminhoes());
                    adicionouNaFila = true;
                }
                algoritmoGuloso.reiniciarDadosDosCaminhoes();
            }
            adicionouNaFila = false;
            hashMapSegundoGuloso.put(T, execucoesDaSegundaEstrategia);
            T += 27;
        }
        temposTotais[1] = tempoTotalSegundaEstrategia;
    }

    /**
     * Executar as duas estratégias gulosas com base no primeiro conjunto de teste
     * enviado por email.
     */
    private static void executarComBaseNoPrimeiroConjuntoDeTestes() {
        long tempoTotal, tempoInicial, tempoFinal;

        System.out.println("EXECUTANDO ALGORITMO GULOSO COM BASE NO PRIMEIRO CONJUNTO DE TESTE...");
        System.out.println();
        AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(primeiroConjuntoDeRotas, 3);
        tempoInicial = System.nanoTime();
        algoritmoGuloso.distribuirRotasOrdenando();
        tempoFinal = System.nanoTime();

        caminhoesDoPrimeiroConjuntoDeTeste.add(algoritmoGuloso.obterCaminhoes());
        algoritmoGuloso.reiniciarDadosDosCaminhoes();

        tempoTotal = (tempoFinal - tempoInicial);
        System.out.println("Tempo da execução da primeira estratégia (ordenando): " + tempoTotal + " nano segundos.");

        Comparator<Caminhao> comparador = new Comparator<Caminhao>() {
            @Override
            public int compare(Caminhao o1, Caminhao o2) {
                if (o1.totalDeRotas() > o2.totalDeRotas())
                    return 1;
                else
                    return 0;
            }
        };

        tempoInicial = System.nanoTime();
        algoritmoGuloso.distribuirRotasParaCaminhaoComMenosRotas(comparador);
        tempoFinal = System.nanoTime();

        caminhoesDoPrimeiroConjuntoDeTeste.add(algoritmoGuloso.obterCaminhoes());
        algoritmoGuloso.reiniciarDadosDosCaminhoes();

        tempoTotal = (tempoFinal - tempoInicial);
        System.err.println("Tempo da execução da segunda estratégia (caminhão com menor total de rotas): " + tempoTotal
                + " nano segundos.");
        System.out.println();
    }

    /**
     * Executar as duas estratégias gulosas com base no segundo conjunto de teste
     * enviado por email.
     */
    private static void executarComBaseNoSegundoConjuntoDeTestes() {
        long tempoTotal, tempoInicial, tempoFinal;

        System.out.println("EXECUTANDO ALGORITMO GULOSO COM BASE NO SEGUNDO CONJUNTO DE TESTE...");
        System.out.println();
        AlgoritmoGuloso algoritmoGuloso = new AlgoritmoGuloso(segundoConjuntoDeTeste, 3);
        tempoInicial = System.nanoTime();
        algoritmoGuloso.distribuirRotasOrdenando();
        tempoFinal = System.nanoTime();

        caminhoesDoSegundoConjuntoDeTeste.add(algoritmoGuloso.obterCaminhoes());
        algoritmoGuloso.reiniciarDadosDosCaminhoes();

        tempoTotal = (tempoFinal - tempoInicial);
        System.out.println("Tempo da execução da primeira estratégia (ordenando): " + tempoTotal + " nano segundos.");

        Comparator<Caminhao> comparador = new Comparator<Caminhao>() {
            @Override
            public int compare(Caminhao o1, Caminhao o2) {
                if (o1.totalDeRotas() > o2.totalDeRotas())
                    return 1;
                else
                    return 0;
            }
        };

        tempoInicial = System.nanoTime();
        algoritmoGuloso.distribuirRotasParaCaminhaoComMenosRotas(comparador);
        tempoFinal = System.nanoTime();

        caminhoesDoSegundoConjuntoDeTeste.add(algoritmoGuloso.obterCaminhoes());
        algoritmoGuloso.reiniciarDadosDosCaminhoes();

        tempoTotal = (tempoFinal - tempoInicial);
        System.err.println("Tempo da execução da segunda estratégia (caminhão com menor total de rotas): " + tempoTotal
                + " nano segundos.");
        System.out.println();
    }

    /**
     * Mostrar os dados dos 3 caminhões.
     */
    private static void mostrarDadosDoCaminhao() {
        for (List<Caminhao> caminhoes : filaDeCaminhoes) {
            imprimirDistribuicao(caminhoes);
        }
    }

    /**
     * Mostra os dados de uma lista de caminhões.
     * 
     * @param caminhoes Lista de caminhões.
     */
    private static void mostrarDadosDoCaminhaoObtidosNoPrimeiroConjuntoDeTeste() {
        for (List<Caminhao> caminhoes : caminhoesDoPrimeiroConjuntoDeTeste) {
            imprimirDistribuicao(caminhoes);
        }
    }

    /**
     * Mostra os dados de uma lista de caminhões.
     * 
     * @param caminhoes Lista de caminhões.
     */
    private static void mostrarDadosDoCaminhaoObtidosNoSegundoConjuntoDeTeste() {
        for (List<Caminhao> caminhoes : caminhoesDoSegundoConjuntoDeTeste) {
            imprimirDistribuicao(caminhoes);
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

    /**
     * Imprime a distribuição de rotas.
     * 
     * @param caminhoes Caminhões que te
     */
    public static void imprimirDistribuicao(List<Caminhao> caminhoes) {
        for (int i = 0; i < caminhoes.size(); i++) {
            System.out.print("Caminhão " + (i + 1) + ": rotas ");
            int totalDistancia = 0;
            for (int j : caminhoes.get(i).getRotas()) {
                System.out.print(j + ", ");
                totalDistancia += j;
            }
            System.out.println("\b\b" + " - total " + totalDistancia + "km");
        }
        System.out.println("Melhor diferença: " + calcularDiferenca(caminhoes));
        System.out.println();
    }

    /**
     * Calcula a melhor diferença entre rotas.
     * 
     * @param caminhoes Lista de caminhões.
     * @return Valor inteiro que representa a diferença.
     */
    public static int calcularDiferenca(List<Caminhao> caminhoes) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (Caminhao caminhao : caminhoes) {
            int somaTotalCaminhao = caminhao.totalDeRotas();
            if (somaTotalCaminhao > max) {
                max = somaTotalCaminhao;
            }
            if (somaTotalCaminhao < min) {
                min = somaTotalCaminhao;
            }
        }

        return max - min;
    }
}
