package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.HsqlException;

import model.Banco;
import model.Usuario;

@SuppressWarnings("unused")

@WebServlet("/FazerPedido")
public class FazerPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String message;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		ArrayList<String> valores = getPedidoValores(request);
		
		try {
			if(valores.stream().allMatch(i -> i.equals("0"))) {
				message = "Pedido inválido!" + "\r\n" + 
				"Escolha pelo menos um prato.";
				throw new Exception();
			} processPedido(valores);
		} 
		
		catch(SQLException exp) {
			message = "Encontramos um erro com seu pedido!" + "\r\n"
					+ "Tente novamente depois.";
			exp.printStackTrace();
		} 
		
		catch(Exception exp) {
			exp.printStackTrace();
		} 
		
		finally {
			request.setAttribute("msg", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Landing.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	private void processPedido(ArrayList<String> valores) throws SQLException {
		Connection con = Banco.getConnection();

		Statement stmt = con.createStatement();
		String pd_id = String.valueOf(insertPedidoSQL(stmt));
		insertPedidoPratosSQL(stmt, pd_id, valores);
		message = "Pedido realizado com sucesso! "
				+ "Seu ID é: " + String.valueOf(pd_id);
	}

	//Pega os valores do form
	private ArrayList<String> getPedidoValores(HttpServletRequest request) {
		ArrayList<String> valores = new ArrayList<>();
		
		for(int L = 0; L < HtmlStringBuilder.prato_tipo.size(); L++) {
			valores.add(
				request.getParameter(HtmlStringBuilder.prato_tipo.get(L).getName()
				)
			);
			/*
			System.out.println(
				HtmlStringBuilder.prato_tipo.get(L).getName() + ": " + valores.get(L)
			);
			*/
		}return valores;
	}

	//Faz a query INSERT e retorna o ID do pedido
	private synchronized int insertPedidoSQL(Statement stmt) throws SQLException {
		Connection con = Banco.getConnection();
		ResultSet res = stmt.executeQuery("SELECT MAX(pd_id) FROM pedido AS id;");
		res.next();
		
		if( 0 < stmt.executeUpdate("INSERT INTO pedido(pd_id) VALUES (DEFAULT);")){
			int a = 1 + res.getInt(1);
			return a;
		} else {
			throw new SQLException("makePedidoSQL / Update Failure");
		}
		
	}

	private void insertPedidoPratosSQL(Statement stmt, String pd_id, ArrayList<String> valores) 
	throws SQLException {
		
		if(pd_id.equals("-1")) {
			throw new SQLException("makePedidoPratosSQL / pd_id = -1");
		}
		
		for(int L = 0; L < valores.size(); L++) {
			String sql_pedido_items = "INSERT INTO pedido_prato(pd_id, prat_id) "
			+ "VALUES (" + pd_id + ", " + valores.get(L) + ");";
			
			if(0 >= stmt.executeUpdate(sql_pedido_items)) {
				removePedidoItems(stmt, pd_id, valores);
				throw new SQLException("makePedidoPratosSQL / Item " + L + " failed update");
			};
		}
	}
	
	private void removePedidoItems(Statement stmt, String pd_id, ArrayList<String> valores)
	throws SQLException {
		String sql_pedido = "DELETE FROM pedido WHERE pd_id = " + pd_id;
		String sql_pedido_prato = "DELETE FROM pedido_prato WHERE pd_id = " + pd_id;
		
		System.out.println("delet :3");
		
		if(	!(stmt.executeUpdate (sql_pedido) <= 0) ||
			!(stmt.executeUpdate (sql_pedido_prato) <= 0)
		) {
			throw new SQLException("!!! Catastrophic SQL Failure !!!");
		}
	}
	
	
	
}
