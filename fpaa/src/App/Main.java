package App;

import java.util.Scanner;

/**
 * Classe que contém o Entry Point da nossa aplicação, ou seja, para executar o
 * programa você precisa executar essa classe e o método "main" localizado nela.
 */
public class Main {
	/**
	 * Propriedade privada da classe: Entry point da nossa aplicação.
	 * 
	 * @param args Argumentos passados durante a chamda da nossa função.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Qual algoritmo você deseja executar? ");
		System.out.println("[BT] Backtracking");
		System.out.println("[AG] Algoritmo Guloso");
		System.out.print("Digite a sigla referente ao algoritmo: ");
		try {
			String algoritmo = sc.nextLine();
			limparTela();

			switch (algoritmo) {
				case "BT":
					// chamada do entry point do backtracking
					break;
				case "AG":
					EntryPointAlgoritmoGuloso.executarAlgoritmoGuloso();
					break;
				default:
					throw new RuntimeException("Não existe implementação do algoritmo especificado.");
			}
		} finally {
			sc.close();
		}
	}

	/**
	 * "Limpa" a tela (códigos de terminal VT-100).
	 */
	private static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
