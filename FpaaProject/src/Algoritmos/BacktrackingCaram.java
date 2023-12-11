package Algoritmos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe principal que resolve o problema de distribuição de rotas entre
 * caminhões.
 */
public class BacktrackingCaram {

    /**
     * Número fixo de caminhões.
     */
    private static final int NUM_CAMINHOES = 3;

    /**
     * Melhor diferença atual entre os caminhões.
     */
    private static int melhorDiferencaAtualCaminhao = Integer.MAX_VALUE;

    /**
     * Método principal que controla a execução do programa.
     */
    public static void executarBacktracking() {
        int quantRotas = 6;
        long totalTempo = 0;

        List<int[]> rotas = new ArrayList<>();

        int[] conjuntoRotas1 = { 40, 36, 38, 29, 32, 28, 31, 35, 31, 30, 32, 30, 29, 39, 35, 38, 39, 35, 32, 38, 32, 33,
                29, 33, 29, 39, 28 };
        rotas.add(conjuntoRotas1);

        int[] conjuntoRotas2 = { 32, 51, 32, 43, 42, 30, 42, 51, 43, 51, 29, 25, 27, 32, 29, 55, 43, 29, 32, 44, 55, 29,
                53, 30, 24, 27 };
        rotas.add(conjuntoRotas2);

        for (int i = 0; i < rotas.size(); i++) {
            long inicio = System.currentTimeMillis();
            // System.out.println("\n\nConjunto " + i + ": ");
            imprimirRotas(rotas.get(i));

            // Distribuição e cálculo da melhor diferença atual
            int melhorDiferencaAtual = distribuirRotas(rotas.get(i));
            totalTempo += (System.currentTimeMillis() - inicio);

            System.out.println("Melhor diferença: " + melhorDiferencaAtual);
            System.out.println(
                    "Tamanho " + quantRotas + " foi resolvido em média em " + (totalTempo) + " ms");
        }
        if (totalTempo >= 30000) {
            System.out.println("Tamanho " + quantRotas + " não pôde ser resolvido em até 30 segundos.");
        }

        quantRotas++;
    }

    /**
     * Método que imprime o vetor de rotas.
     * 
     * @param rotas Vetor de rotas a ser impresso.
     */
    private static void imprimirRotas(int[] rotas) {
        System.out.print("\nRotas: ");
        for (int i = 0; i < rotas.length; i++) {
            System.out.print(rotas[i] + " ");
        }
        System.out.println();
    }

    /**
     * Recebe caminhões e imprime a distribuição de rotas atual por caminhão.
     * 
     * @param caminhoes Lista de caminhões e suas rotas.
     */
    private static void imprimirDistribuicao(ArrayList<Integer>[] caminhoes) {
        for (int i = 0; i < caminhoes.length; i++) {
            System.out.print("Caminhão " + (i + 1) + ": rotas ");
            int totalDistancia = 0;
            for (int j : caminhoes[i]) {
                System.out.print(j + ", ");
                totalDistancia += j;
            }
            // Remova a última vírgula e imprima a distância total
            System.out.println("\b\b" + " - total " + totalDistancia + "km");
        }
    }

    /**
     * Distribui as rotas entre os caminhões, minimizando a diferença total.
     * 
     * @param rotas Vetor de rotas a serem distribuídas.
     * @return A diferença total entre as rotas dos caminhões.
     */
    private static int distribuirRotas(int[] rotas) {
        ArrayList<Integer>[] caminhoes = new ArrayList[NUM_CAMINHOES];

        // Inicializa os caminhões
        for (int i = 0; i < NUM_CAMINHOES; i++) {
            caminhoes[i] = new ArrayList<>();
        }

        int media = calcularMedia(rotas);
        // int maxTotalRotasPorCaminhao = (int) Math.ceil((double) media * 1);

        // Distribui as rotas entre os caminhões
        for (int i = 0; i < caminhoes.length; i++) {
            caminhoes[i] = distribuir(rotas, media, caminhoes[i], 0, new ArrayList<>());
            melhorDiferencaAtualCaminhao = Integer.MAX_VALUE;
            rotas = atualizarRotas(rotas, caminhoes[i]);
        }

        // Distribui as rotas restantes para o caminhão com menor quilometragem
        if (rotas.length != 0) {
            for (int i = 0; i < rotas.length; i++) {
                verificaMenorQuilometragemCaminhao(caminhoes).add(rotas[i]);
            }
        }

        imprimirDistribuicao(caminhoes);

        // Retorna a diferença total entre as rotas dos caminhões
        return calcularDiferenca(caminhoes);
    }

    /**
     * Calcula a média das rotas.
     * 
     * @param rotas Vetor de rotas a serem calculadas.
     * @return A média das rotas.
     */
    private static int calcularMedia(int[] rotas) {
        int media = 0;
        for (int i : rotas) {
            media += i;
        }
        return media / NUM_CAMINHOES;
    }

    /**
     * Encontra o caminhão com a menor quilometragem total.
     * 
     * @param caminhoes Lista de caminhões.
     * @return O caminhão com a menor quilometragem.
     */
    private static ArrayList<Integer> verificaMenorQuilometragemCaminhao(ArrayList<Integer>[] caminhoes) {
        int menorQuilometragem = Integer.MAX_VALUE;
        ArrayList<Integer> menorCaminhao = new ArrayList<>();

        for (ArrayList<Integer> caminhao : caminhoes) {
            int somaTotalCaminhao = caminhao.stream().mapToInt(Integer::intValue).sum();
            if (somaTotalCaminhao < menorQuilometragem) {
                menorQuilometragem = somaTotalCaminhao;
                menorCaminhao = caminhao;
            }
        }
        return menorCaminhao;
    }

    /**
     * Distribui as rotas entre os caminhões.
     * 
     * @param rotas                    Vetor de rotas a serem distribuídas.
     * @param maxTotalRotasPorCaminhao Número máximo de rotas permitidas por
     *                                 caminhão.
     * @param caminhao                 Lista de rotas do caminhão atual.
     * @param indice                   Índice da rota atual.
     * @param melhorRota               Lista contendo a melhor distribuição de rotas
     *                                 atual.
     * @return A lista de rotas do caminhão com a melhor distribuição.
     */
    private static ArrayList<Integer> distribuir(int[] rotas, int maxTotalRotasPorCaminhao,
            ArrayList<Integer> caminhao, int indice, ArrayList<Integer> melhorRota) {

        int somaTotalCaminhao = caminhao.stream().mapToInt(Integer::intValue).sum();
        int diferenca = maxTotalRotasPorCaminhao - somaTotalCaminhao;

        // Atualiza a melhor rota caso a diferença seja menor
        if (diferenca > 0 && diferenca < melhorDiferencaAtualCaminhao) {
            melhorDiferencaAtualCaminhao = diferenca;
            melhorRota.clear();
            melhorRota.addAll(caminhao);
        }

        // Verifica se a soma total ultrapassa o limite ou se todas as rotas foram
        // distribuídas
        if (somaTotalCaminhao > maxTotalRotasPorCaminhao || indice == rotas.length) {
            return null;
        }

        // Adiciona a rota atual ao caminhão e chama recursivamente para a próxima rota
        caminhao.add(rotas[indice]);
        distribuir(rotas, maxTotalRotasPorCaminhao, caminhao, indice + 1, melhorRota);

        // Remove a última rota adicionada e chama recursivamente para a próxima rota
        caminhao.remove(caminhao.size() - 1);
        distribuir(rotas, maxTotalRotasPorCaminhao, caminhao, indice + 1, melhorRota);

        return melhorRota;
    }

    /**
     * Calcula a diferença total entre as rotas dos caminhões.
     * 
     * @param caminhoes Lista de caminhões.
     * @return A diferença total entre as rotas dos caminhões.
     */
    private static int calcularDiferenca(ArrayList<Integer>[] caminhoes) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (ArrayList<Integer> caminhao : caminhoes) {
            int somaTotalCaminhao = caminhao.stream().mapToInt(Integer::intValue).sum();
            if (somaTotalCaminhao > max) {
                max = somaTotalCaminhao;
            }
            if (somaTotalCaminhao < min) {
                min = somaTotalCaminhao;
            }
        }

        return max - min;
    }

    /**
     * Atualiza o vetor de rotas após a distribuição.
     * 
     * @param rotas    Vetor de rotas original.
     * @param caminhao Caminhão com as rotas distribuídas.
     * @return O vetor de rotas atualizado.
     */
    private static int[] atualizarRotas(int[] rotas, ArrayList<Integer> caminhao) {
        LinkedList<Integer> rotasList = new LinkedList<>();
        for (int i : rotas) {
            rotasList.add(i);
        }
        for (int i : caminhao) {
            rotasList.removeFirstOccurrence(i);
        }
        return rotasList.stream().mapToInt(Integer::intValue).toArray();
    }
}