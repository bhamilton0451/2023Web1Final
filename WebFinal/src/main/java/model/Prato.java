package model;

import java.util.ArrayList;

public class Prato extends SQLItemObject {
	private String tipo;
	
	private ArrayList<String> ingredientes;
	private String imagem;
	
	
	
	public String getTipo() {
		return tipo;
	}
	public ArrayList<String> getIngredientes() {
		return ingredientes;
	}
	public String getImagem() {
		return imagem;
	}
	
	// - - -
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setAllIngredientes(ArrayList<String> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public void removeIngrediente(String ingrediente) {
		this.ingredientes.remove(ingrediente);
	}
	public void addIngrediente(String ingrediente) {
		if(ingredientes.contains(ingrediente)) {
			this.ingredientes.add(ingrediente);
		}
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	
	
	
}
