package model;

public class SQLItemObject extends SQLObject {
	protected int id;
	protected String name;
	
	public int getId() {
		return id;
	}
	public void setId(int a) {
		this.id = a;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String nome){
		this.name = nome;
	}
	
}
