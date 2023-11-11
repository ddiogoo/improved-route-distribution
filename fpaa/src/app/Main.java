package app;

import java.util.List;

import generators.GeradorDeProblemas;

public class Main {
	public static void main(String[] args) {
		List<int[]> conjunto = GeradorDeProblemas.geracaoDeRotas(2, 6, 10);
		
		int i = 1;
		for(int[] c : conjunto) {
			System.out.println("Elementos na " + i + "º posição do conjunto: ");
			for(int v : c) {
				System.out.print(v + "; ");
			}
			System.out.println();
			i++;
		}
	}
}
