package Algoritmos;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe principal que resolve o problema de distribuição de rotas entre
 * caminhões.
 */
public class Backtracking {

    // Número fixo de caminhões
    static final int NUM_CAMINHOES = 3;

    // Melhor diferença atual entre os caminhões
    static int melhorDiferencaAtualCaminhao = Integer.MAX_VALUE;

    /**
     * Método que imprime o vetor de rotas.
     * 
     * @param rotas Vetor de rotas a ser impresso.
     */
    public static void imprimirRotas(int[] rotas) {
        System.out.println("Rotas: ");
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
    public static void imprimirDistribuicao(ArrayList<Integer>[] caminhoes) {
        for (int i = 0; i < caminhoes.length; i++) {
            System.out.print("Caminhão " + i + ": ");
            for (int j : caminhoes[i]) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * Distribui as rotas entre os caminhões, minimizando a diferença total.
     * 
     * @param rotas Vetor de rotas a serem distribuídas.
     * @return A diferença total entre as rotas dos caminhões.
     */
    public static int distribuirRotas(int[] rotas) {
        ArrayList<Integer>[] caminhoes = new ArrayList[NUM_CAMINHOES];

        // Inicializa os caminhões
        for (int i = 0; i < NUM_CAMINHOES; i++) {
            caminhoes[i] = new ArrayList<>();
        }

        int media = calcularMedia(rotas);
        int maxTotalRotasPorCaminhao = (int) Math.ceil((double) media * 1.02);

        // Distribui as rotas entre os caminhões
        for (int i = 0; i < caminhoes.length; i++) {
            caminhoes[i] = distribuir(rotas, maxTotalRotasPorCaminhao, caminhoes[i], 0, new ArrayList<>());
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
    public static int calcularMedia(int[] rotas) {
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
    public static ArrayList<Integer> verificaMenorQuilometragemCaminhao(ArrayList<Integer>[] caminhoes) {
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
    public static ArrayList<Integer> distribuir(int[] rotas, int maxTotalRotasPorCaminhao,
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
    public static int calcularDiferenca(ArrayList<Integer>[] caminhoes) {
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
    public static int[] atualizarRotas(int[] rotas, ArrayList<Integer> caminhao) {
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
