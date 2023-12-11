package EntryPoint;

import java.util.List;

import Algoritmos.Backtracking;
import Geradores.GeradorDeProblemas;

public class EntryPointBacktracking {
    // Número fixo de caminhões
    static final int NUM_CAMINHOES = 3;

    // Melhor diferença atual entre os caminhões
    static int melhorDiferencaAtualCaminhao = Integer.MAX_VALUE;

    public static void executarBacktracking() {
        int quantRotas = 6;
        int tamConjunto = 10;
        double dispersao = 0.50;

        while (true) {
            long totalTempo = 0;

            // Geração de rotas aleatórias
            List<int[]> rotas = GeradorDeProblemas.geracaoDeRotas(quantRotas, tamConjunto, dispersao);

            for (int i = 0; i < tamConjunto; i++) {
                long inicio = System.currentTimeMillis();
                System.out.println("\n\nConjunto " + i + ": ");
                Backtracking.imprimirRotas(rotas.get(i));

                // Distribuição e cálculo da melhor diferença atual
                int melhorDiferencaAtual = Backtracking.distribuirRotas(rotas.get(i));
                totalTempo += (System.currentTimeMillis() - inicio);

                System.out.println("Melhor diferença atual: " + melhorDiferencaAtual);

                if (totalTempo >= 30000)
                    break;
            }

            System.out.println(
                    "Tamanho " + quantRotas + " foi resolvido em média em " + (totalTempo / tamConjunto) + " ms");
            if (totalTempo >= 30000) {
                System.out.println("Tamanho " + quantRotas + " não pôde ser resolvido em até 30 segundos.");
                break;
            }

            quantRotas++;
        }
    }
}