package Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a entidade caminhão no contexto do nosso problema, a
 * mesma terá rotas atribuídas a ele assim como o total de rotas.
 */
public class Caminhao {
	/**
	 * Propriedade privada: Lista de rotas do caminhão.
	 */
	private List<Integer> rotas;

	/**
	 * Propriedade privada: Total de rotas, ou seja, soma da lista de rotas do
	 * caminhão.
	 */
	private Integer totalDeRotas;

	public Caminhao() {
		this.rotas = new ArrayList<Integer>();
		this.totalDeRotas = 0;
	}

	/**
	 * Calculador de total de rotas do caminhão, ele soma todas as rotas atribuídas
	 * a um determinado caminhão.
	 */
	private void calcularTotal() {
		for (Integer rota : this.rotas) {
			this.totalDeRotas += rota;
		}
	}

	/**
	 * Obter todas as rotas atribuídas a um determinado caminhão.
	 * 
	 * @return Lista de valores inteiros que representam as rotas.
	 */
	public List<Integer> getRotas() {
		return this.rotas;
	}

	/**
	 * Obter o total de rotas de um caminhão.
	 * 
	 * @return Valor inteiro que representa o total de rotas.
	 */
	public Integer totalDeRotas() {
		return this.totalDeRotas;
	}

	/**
	 * Adicionar uma rota na lista de rotas do caminhão.
	 * 
	 * @param rota Rota que será adicionada no caminhão.
	 */
	public void add(int rota) {
		this.rotas.add(rota);
		calcularTotal();
	}
}
