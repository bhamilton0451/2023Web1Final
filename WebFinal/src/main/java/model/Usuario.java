package model;

public class Usuario extends SQLItemObject {
	private String senha; 
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String a) {
		this.senha = a;
	}
}