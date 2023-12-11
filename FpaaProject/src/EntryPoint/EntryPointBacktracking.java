package EntryPoint;

import java.util.Scanner;

import Algoritmos.BacktrackingCaram;
import Algoritmos.Backtracking;

public class EntryPointBacktracking {
    private static Scanner sc = new Scanner(System.in);

    public static void executarBacktracking() {
        System.out.println(
                "Qual parte do trabalho você deseja executar?");
        System.out.println("1) Etapas descritas na especificação do trabalho.");
        System.out.println("2) Conjuntos de teste enviado por email.");
        System.out.print("Digite o número referente a opção: ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                Backtracking.executarBacktracking();
                break;
            case 2:
                BacktrackingCaram.executarBacktracking();
                break;
            default:
                throw new RuntimeException("Opção não encontrada.");
        }
    }
}