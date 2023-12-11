package Algoritmos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import Classes.Caminhao;

/**
 * Classe que representa nosso Algoritmo Guloso, na qual pode ser executado por
 * duas estratégias que estão implementadas nessa classe.
 */
public class AlgoritmoGuloso {
	/**
	 * Todas as rotas distribuidas em um único array unidimensional.
	 */
	private int[] quilometragens;

	/**
	 * Lista de caminhões que terão rotas distribuídas para eles.
	 */
	private List<Caminhao> caminhoes;

	/**
	 * Construtor responsável por instanciar o Algoritmo Guloso recebendo rotas e o
	 * número de caminhões que irá trabalhar.
	 * 
	 * @param quilometragens    Todas as rotas que serão distribuídas.
	 * @param numeroDeCaminhoes Número de caminhões que serão usados.
	 */
	public AlgoritmoGuloso(int[] quilometragens, int numeroDeCaminhoes) {
		this.quilometragens = quilometragens;
		this.caminhoes = new ArrayList<Caminhao>();
		this.carregarCaminhoes(numeroDeCaminhoes);
	}

	/**
	 * Inicializa a Lista de caminhões com instâncias dos caminhões.
	 * 
	 * @param numeroDeCaminhoes Quantidade de caminhões que serão criados.
	 */
	private void carregarCaminhoes(int numeroDeCaminhoes) {
		for (int i = 0; i < numeroDeCaminhoes; i++) {
			this.caminhoes.add(new Caminhao());
		}
	}

	/**
	 * Reiniciar a lista de caminhões com caminhões sem rotas.
	 */
	public void reiniciarDadosDosCaminhoes() {
		int quantidadeDeCaminhoes = this.caminhoes.size();
		this.caminhoes = new ArrayList<Caminhao>();
		carregarCaminhoes(quantidadeDeCaminhoes);
	}

	/**
	 * Executa a primeira estratégia gulosa: Ordena as rotas passadas no construtor
	 * da classe, distribui para cada caminhão em ordem e voltando ao primeiro
	 * quando o último tiver uma rota atribuída a ele.
	 */
	public void distribuirRotasOrdenando() {
		Arrays.sort(this.quilometragens);
		int caminhaoAtual = 0;
		for (int i = 0; i < this.quilometragens.length; i++) {
			if (caminhaoAtual > this.caminhoes.size() - 1) {
				caminhaoAtual = 0;
			}
			this.caminhoes.get(caminhaoAtual++).add(this.quilometragens[i]);
		}
	}

	/**
	 * Executa a segunda estratégia gulosa: Obtem o caminhão com menor total de
	 * rotas e atribui para o caminhão encontrado. A condição de escolha de um
	 * caminhão é controlada pelo comparador passado por parâmetro.
	 * 
	 * @param comparador Comparador que será usado como condição de obtenção de um
	 *                   caminhão.
	 */
	public void distribuirRotasParaCaminhaoComMenosRotas(Comparator<Caminhao> comparador) {
		for (int i = 0; i < this.quilometragens.length; i++) {
			Caminhao caminhaoComMenosRotas = this.caminhoes.stream().min(comparador).get();
			caminhaoComMenosRotas.add(this.quilometragens[i]);
		}
	}

	/**
	 * Obtem todos os caminhões.
	 * 
	 * @return Lista de caminhões.
	 */
	public List<Caminhao> obterCaminhoes() {
		return this.caminhoes;
	}
}
