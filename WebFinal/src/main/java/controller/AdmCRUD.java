package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Banco;
import model.Pedido;
import model.Prato;

@SuppressWarnings("unused")

@WebServlet("/AdminCrud")
public class AdmCRUD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String message;
	private static String display;
	private static String form;
	private static Connection con = Banco.getConnection();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		String state = request.getParameter("state");
		
		evalState(state, request);
		
		/*
		System.out.println(display);
		System.out.println(form);
		System.out.println(message);
		System.out.println(state);
		*/
		
		//request.setAttribute("msg", message);
		request.setAttribute("display", display);
		//request.setAttribute("form",  form);
		//System.out.println(display);
		
		
		
		/*
		System.out.println((String)request.getAttribute("display"));
		System.out.println((String)request.getAttribute("msg"));
		System.out.println((String)request.getAttribute("form"));
		*/
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Admin.jsp");
		dispatcher.forward(request, response);
	}
	
	public static String getDisplay() {
		return display;
	}
	
	public static String getForm() {
		return form;
	}
	
	public static String getMessage() {
		return message;
	}
	
	private void evalState(String state, HttpServletRequest request) {
		switch(state) {
			case "state_0":
				handleState0(request);
				break;
				
			case "state_1":
				handleState1(request);
				break;
					
			case "state_2":
				handleState2(request);
				break;
					
			default:
				setState0();
				break;
		}
		
	}
	
	private void handleState0(HttpServletRequest request) {
		String action = request.getParameter("menu_0");
		
		switch(action) {
			case "menu_1_see":
				setState1();
				break;
			case "menu_2_see":
				setState2();
				break;
			case "menu_3_see":
				//setState3();
				break;
		}
	}
	
	private void handleState1(HttpServletRequest request) {
		String action = request.getParameter("menu_0");
		String text = (String)request.getAttribute("input_text");
		String flag = (String)request.getAttribute("flag");
		
		if((text == null) && (flag == null)) {
			switch(action) {
				case "menu_return":
					setState0();
					break;
				case "menu_remove":
					display = displayPratos();
					form = remove_flag + form + "\r\n" + input_text;
					break;
			}
		} else {
			switch(flag) {
				case "remove":
					removePrato(text);
				default:
					display = displayPratos();
					break;
			}
			
		}
	}
	
	private void handleState2(HttpServletRequest request) {
		String action = (String)request.getAttribute("sub_menu");
		String text = (String)request.getAttribute("input_text");
		String flag = (String)request.getAttribute("flag");
		
		if((text == null) && (flag == null)) {
			switch(action) {
				case "menu_return":
					setState0();
					break;
				case "menu_remove":
					display = displayPedidos();
					form = remove_flag + form + "\r\n" + input_text;
					break;
			}
		} else {
			
			switch(flag) {
				case "remove":
					removePedido(Integer.parseInt(text));
				default:
					display = displayPedidos();
					break;
			}
			
		}
	}
	
	public static String startup() {
		return state_0 + "\r\n" + menu_0;
	}
	
	private void setState0() {
		form = state_0 + "\r\n" + menu_0;
	}
	
	private void setState1() {
		display = displayPratos();
		form = state_1 + "\r\n" + sub_menu;
	}
	
	private void setState2() {
		display = displayPedidos();
		form = state_2 + "\r\n" + sub_menu;
	}
	
	private void removePrato(String prato) {
		
		String sql = "DELETE FROM prato " + "WHERE nome = " + prato.trim() +  ";" ;
		try{
			Statement stmt = con.createStatement();
			int rowsAffected = stmt.executeUpdate(sql);
			if (rowsAffected <= 0) {
				throw new SQLException();
			}
			message = "Prato ID: " + prato.trim() + " removido.";
		} catch (SQLException e) {
			message = "Erro: Deletion Not Successful";
			e.printStackTrace();
		}
		
		return;
	}
	
	private void removePedido(int pd_id) {
		String sql = "DELETE FROM pedido " + "WHERE pd_id = " + String.valueOf(pd_id) +  ";" ;
		try{
			Statement stmt = con.createStatement();
			int rowsAffected = stmt.executeUpdate(sql);
			if (rowsAffected <= 0) {
				throw new SQLException();
			}
			message = "Pedido ID: " + String.valueOf(pd_id) + " removido.";
		} catch (SQLException e) {
			message = "Erro: Deletion Not Successful";
			e.printStackTrace();
		}
		
		return;
	}
	
	static String remove_flag = "<input type=\"hidden\" name=\"flag\" id=\"flag\" value=\"remove\">";
	
	static String state_0 = "<input type=\"hidden\" name=\"state\" value=\"state_0\">"; //Menu Default
	static String state_1 = "<input type=\"hidden\" name=\"state\" value=\"state_1\">"; //Menu Pedidos
	static String state_2 = "<input type=\"hidden\" name=\"state\" value=\"state_2\">"; //Menu Pratos
	
	static String menu_0 = "<select name=\"menu_0\">\r\n"
			+ "	<option value=\"menu_1_see\">Menu de Pratos</option>\r\n"
			+ "	<option value=\"menu_2_see\">Menu de Pedidos</option>\r\n"
			+ "</select>";
	
	static String sub_menu = "<select name=\"menu_0\">\r\n"
			+ "	<option value=\"menu_return\">Retornar</option>\r\n"
			+ "	<option value=\"menu_delete\">Remover</option>\r\n"
			+ "</select>";
	
	static String input_text = "<input type=\"text\" id=\"input_text\" name=\"input_text\"><br>\r\n"
			+ "				<label for=\"input_text\">Nome: </label>";
	
	
	
	private String displayPratos() { 
		ArrayList<Prato> pratos = getArrayAllPratos();
		String result = "<h2> - Pratos - </h2><br>";
		
		for(int K = 0; K < pratos.size(); K++) {
			result = result + "ID : " + String.valueOf(pratos.get(K).getId()) + " ";
			result = result + "Nome: " + pratos.get(K).getName() + " ";
			result = result + "Tipo: " + pratos.get(K).getTipo() + " " + "\r\n";
			result = result + "Ingredientes: "; 
			
			for(int L = 0; L < pratos.get(K).getIngredientes().size(); L++){
				result = result + "[" + pratos.get(K).getIngredientes().get(L) + "] ";
			}
			result = result + "\r\n" + "<br>";
		}
		return result;
	}
	
	private String displayPedidos() {
		ArrayList<Pedido> pedidos = getPedidos();
		System.out.println(pedidos.size());
		String result = "<h2>- Pedidos -</h2><br> "; 
		
		for(int L = 0; L < pedidos.size(); L++) {
			result = result + "ID: " + String.valueOf(pedidos.get(L).getId()) + " ";
			result = result + "Pratos: "; 
			
			for(int K = 0; K < pedidos.get(L).getPratos().size(); K++){
				result = result + "[" + pedidos.get(L).getPratos().get(K).getName() + "] ";
			}
			result = result + "\r\n" + "<br>";
		}
		
		return result;
	}
	
	private static ArrayList<Pedido> getPedidos(){
		ArrayList<Pedido> pedidos = new ArrayList<>();
		Connection con = Banco.getConnection();
		String sql = "SELECT pd_id FROM pedido";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Pedido pedido = new Pedido();
				pedido.setId(resultSet.getInt(1));
				pedido.setPratos(getPratos(pedido.getId()));
				pedidos.add(pedido);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println(pedidos);
		return pedidos;
	}
	
	public static ArrayList<Prato> getArrayAllPratos(){
		ArrayList<Prato> pratos = new ArrayList<>();
		String sql = "SELECT * FROM prato";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Prato prato = new Prato();
				prato.setId(resultSet.getInt("prat_id"));
				prato.setName(resultSet.getString("nome"));
				
				prato.setAllIngredientes(getIngredientes(con, prato.getId()));
				prato.setTipo(getTipo(con, prato.getId()));
				pratos.add(prato);
			}
		}
		catch (SQLException e) {
			e.printStackTrace(); // Trate o erro apropriadamente
		} return pratos;
	}
	
	private static ArrayList<Prato> getPratos(int pd_id) 
	throws SQLException{
			ArrayList<Prato> pratos = new ArrayList<>();
				
			pratos = getArrayPratos( 
					getPratID(pd_id)
			);
		return pratos;
	}
	
	private static ArrayList<String> getPratoName(ArrayList<Integer> prat_id)
	throws SQLException {
		ArrayList<String> pratoNomes = new ArrayList<>();	
		
		for(int L = 0; L < prat_id.size(); L++) {
			getPratoName(prat_id.get(L));
		}
		return pratoNomes;
	}
	
	private static String getPratoName(int prat_id) 
	throws SQLException {
		String sql = "SELECT nome FROM prato WHERE prat_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, String.valueOf(prat_id));
		
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			return resultSet.getString(1);
		}
		return null;
	}

	private static ArrayList<Prato> getArrayPratos(int prat_id) {
		String sql_prato = "SELECT * FROM prato WHERE prat_id = ?";
		ArrayList<Prato> pratos = new ArrayList<>();
			
		try(PreparedStatement stmt = con.prepareStatement(sql_prato)){
			stmt.setString(1, String.valueOf(prat_id));
				
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
	
	private static ArrayList<Prato> getArrayPratos(ArrayList<Integer> prat_id) {
		ArrayList<Prato> pratos = new ArrayList<>();
		ArrayList<Prato> the_cooler_pratos = new ArrayList<>();
		pratos = getArrayAllPratos();
		
		for(int L = 0; L < pratos.size(); L++) {
			loop:
			for(int K = 0; K < prat_id.size(); K++) {
				if(pratos.get(L).getId() == prat_id.get(K)) {
					the_cooler_pratos.add(pratos.get(L));
					break loop;
				}
			}
		}
		return the_cooler_pratos;
	}

	private static ArrayList<Integer> getPratID(int pd_id)
	throws SQLException {
			ArrayList<Integer> pratosID = new ArrayList<>();
			String sql = "SELECT prat_id FROM pedido_prato WHERE pd_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
				
			stmt.setString(1, String.valueOf(pd_id));
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
					pratosID.add(resultSet.getInt(1));
			}
		return pratosID;
	}
	
	private static ArrayList<String> getIngredientes(Connection con, int prat_id) 
	throws SQLException{
		ArrayList<String> ingredientesNome = new ArrayList<>();
		
		ingredientesNome = getIngName(con, 
				getIngID(con, prat_id)
		);
		return ingredientesNome;
	}
	
	private static ArrayList<String> getIngName(Connection con, ArrayList<Integer> ing_id)
	throws SQLException {
		ArrayList<String> ingredienteNomes = new ArrayList<>();
		String sql = "SELECT nome FROM ingrediente WHERE ing_id = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		for(int L = 0; L < ing_id.size(); L++) {
			stmt.setString(1, String.valueOf(ing_id.get(L)));
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				ingredienteNomes.add(resultSet.getString("nome"));
			}
		}
		return ingredienteNomes;
	}

	private static ArrayList<Integer> getIngID(Connection con, int prat_id)
	throws SQLException {
		ArrayList<Integer> ingredientesID = new ArrayList<>();
		String sql = "SELECT ing_id FROM prato_ingrediente WHERE prat_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, String.valueOf(prat_id));
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			ingredientesID.add(resultSet.getInt(1));
		} 
		return ingredientesID;
	}
	
	private static String getTipo(Connection con, int prat_id) throws SQLException{
		String tipo;
		
		tipo = getTipoName(con, getTipoID(con, prat_id));
		
		return tipo;
	}

	private static String getTipoName(Connection con, int tp_id) throws SQLException {
		String sql = "SELECT nome FROM prato_tipo WHERE tp_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, String.valueOf(tp_id));
		
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			return resultSet.getString(1);
		} 
		
		return null;
	}

	private static int getTipoID(Connection con, int prat_id) throws SQLException {
		String sql = "SELECT tipo FROM prato WHERE prat_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, String.valueOf(prat_id));
		
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return -1;
	}
	
}
