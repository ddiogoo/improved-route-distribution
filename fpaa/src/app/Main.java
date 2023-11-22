package app;

import java.util.List;

import generators.GeradorDeProblemas;
import source.GreedyAlgorithm;

public class Main {
	public static void main(String[] args) {
		greedy();
	}
	
	public static void greedy() {
		List<int[]> sets = GeradorDeProblemas.geracaoDeRotas(6, 10, 0.5);
		
		for(int[] routes : sets) {
			GreedyAlgorithm greedy = new GreedyAlgorithm(routes, 3);
			greedy.distributeRoutesWithGreedy();
			
			int[] r = greedy.getRoutes();
			for(int route : r) {
				System.out.println(route);
			}
		}
	}
}
