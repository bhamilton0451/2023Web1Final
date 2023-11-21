package model;

import java.util.ArrayList;

public class Pedido extends SQLItemObject {
	
	String sql_select = "SELECT ? FROM pedido WHERE ?";
	
	ArrayList<Prato> pratos = new ArrayList<>();
	
	public ArrayList<Prato> getPratos() {
		return pratos;
	};
	
	public void setPratos(ArrayList<Prato> pratos) {
		this.pratos = pratos;
	}
	
	public void addPrato(Prato prato) {
		pratos.add(prato);
	}
	
	public void clearPratos() {
		pratos.clear();
	}
	
}
