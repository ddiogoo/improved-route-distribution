package Classes;

import java.util.ArrayList;
import java.util.List;

public class Caminhao {
	private List<Integer> rotas;
	private Integer totalDeRotas;
	
	public Caminhao() {
		this.rotas = new ArrayList<Integer>();
		this.totalDeRotas = 0;
	}
	
	private void calcularTotal() {
		for(Integer rota : this.rotas) {
			this.totalDeRotas += rota;
		}
	}
	
	public List<Integer> getRotas() {
		return this.rotas;
	}
	
	public Integer totalDeRotas() {
		return this.totalDeRotas;
	}
	
	public void add(int rota) {
		this.rotas.add(rota);
		calcularTotal();
	}
}
