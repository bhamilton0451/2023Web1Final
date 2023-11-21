package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Banco;
import model.Prato;
import model.SQLItemObject;

public class HtmlStringBuilder {
	static Connection con = Banco.getConnection();
	protected static ArrayList<SQLItemObject> prato_tipo = getArrayPratoTipo();
	
	/*
	 // Fique aqui imortalizado que eu passei a noite inteira preocupado
	 // com esse código não imprimir a database, só pra depois
	 // descobrir que é porque a database estava, de fato, vazia.
	public static void main(String[] args) {
		ArrayList<SQLDBObject> prato_tipo = FetchPratoTipo();
		for(int L = 0; L < prato_tipo_size; L++) {
			System.out.println(getArrayPratos(1).get(L).getName());
		}
	}
	*/
	
	
	public static String buildTipoSelect() {
		String result = "<div id=\"cardapio_select\">" + "\r\n";
		//ArrayList<SQLDBObject> prato_tipo = getArrayPratoTipo();
			
		for(int L = 0; L < prato_tipo.size(); L++) {
			result = result + 
					HtmlStringBuilder.buildFormSelect(
					prato_tipo.get(L).getName(), 
					getArrayPratos(L+1));
		}
		
		return result + "</div><br>";
	}
	
	private static String buildFormSelect(String name, ArrayList<Prato> opt) {
		String result;
		
		result = buildFormSelectHeader(name) +
				"\r\n" + 
				buildFormSelectOptions(opt);
		
		return result;
	}
	
	private static String buildFormSelectHeader(String name) {
		String result =
		"	<select name=\"" + name + "\" id=\""+ name + "\">";
		
		return result;
	}
	
	private static String buildFormSelectOptions(ArrayList<Prato> opt) {
		String result = 
				"		<option value=\"" + "0" + "\">" + "      --      " + "</option><br> ";
		
		for(int L = 0; L < opt.size(); L++) {
			result = 
					result + "\r\n" + 
			"		<option value=\"" + String.valueOf(opt.get(L).getId()) + "\">" + 
							opt.get(L).getName() + 
			"</option><br> ";
		}	
		result = result + "\r\n" +
		"	</select>" + "<br>" + "\r\n";
				
		return result;
	}
		
	private static ArrayList<SQLItemObject> getArrayPratoTipo() {
		String sql_prato_tipo = "SELECT * FROM prato_tipo;";
		ArrayList<SQLItemObject> prato_tipo = new ArrayList<SQLItemObject>();
			
		try (PreparedStatement stmt = con.prepareStatement(sql_prato_tipo)) {
			try (ResultSet resultSet = stmt.executeQuery()) {
				while (resultSet.next()) {
					SQLItemObject obj = new SQLItemObject();
					obj.setId(resultSet.getInt("tp_id"));
					obj.setName(resultSet.getString("nome"));
					prato_tipo.add(obj);
				}
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
			
			return prato_tipo;
		}
		
	private static ArrayList<Prato> getArrayPratos(int tipo) {
		String sql_prato = "SELECT * FROM prato WHERE tipo = ?";
		ArrayList<Prato> pratos = new ArrayList<>();
			
		try(PreparedStatement stmt = con.prepareStatement(sql_prato)){
			stmt.setString(1, String.valueOf(tipo));
				
			try (ResultSet resultSet = stmt.executeQuery()) {
				while (resultSet.next()) {
					Prato prato = new Prato();
					prato.setId(resultSet.getInt("prat_id"));
					prato.setName(resultSet.getString("nome"));
					prato.setTipo(String.valueOf(resultSet.getInt("tipo")));
					pratos.add(prato);
				}
			}
				
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
			
		return pratos;
	}
	
}
