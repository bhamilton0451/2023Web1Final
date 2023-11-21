package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class SQLObject {
	
	private String sql_select = "";
	
	public ResultSet query(Connection con, String a, String b) 
	throws SQLException {
		
		PreparedStatement stm = con.prepareStatement(this.sql_select);
		stm.setString(1, a);
		stm.setString(2, b);
		
		return stm.executeQuery();
	}
	
	public ArrayList<ResultSet> query(Connection con, String... queries) 
	throws SQLException {
		ArrayList<ResultSet> result = new ArrayList<>();
		
		for(int L = 0; L < queries.length; L++) {
			PreparedStatement stm = con.prepareStatement(this.sql_select);
			stm.setString(L+1, queries[L]);
			
			result.add(stm.executeQuery());
		}
		
		return result;
	};
	
}
