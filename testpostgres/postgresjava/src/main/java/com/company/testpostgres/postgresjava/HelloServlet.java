package com.company.testpostgres.postgresjava;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage(), e);
		}
		try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8")) {
			writer.write("Hello World !!");
			writer.write("\n\nJDBC connection available: ");
			if (conn != null) {
//				writer.write("yes");
//				writer.write("\n\nCurrent Hana DB user:\n");
//				String userName = getCurrentUser(conn);
//				writer.write(userName);
//				writer.write("\n\nCurrent Hana schema:\n");
//				writer.write(getCurrentSchema(conn));
				Statement stmt = null;
				writer.write("worked well till the connection was created. here is the connection");
				writer.write(conn.toString());
				
				try{
					stmt = conn.createStatement();
//					String sql = "insert into public.user_name values('chandan1');";
					String sql = "select * from public.user_name";
			ResultSet rs =	stmt.executeQuery(sql);
			sql = "insert into public.user_name values('chandan')";
			stmt.executeQuery(sql);
				writer.write("successfully executed the statement");
				writer.write(rs.toString());}
				catch(Exception e)
				{writer.write("exception caught:" + e.getMessage());
				}
				
			} else {
				writer.write("no");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private String getCurrentUser(Connection conn) throws SQLException {
		String currentUser = "";
		PreparedStatement prepareStatement = conn.prepareStatement("SELECT CURRENT_USER \"current_user\" FROM DUMMY;");
		ResultSet resultSet = prepareStatement.executeQuery();
		int column = resultSet.findColumn("current_user");
		while (resultSet.next()) {
			currentUser += resultSet.getString(column);
		}
		return currentUser;
	}

	private String getCurrentSchema(Connection conn) throws SQLException {
		String currentSchema = "";
		PreparedStatement prepareStatement = conn.prepareStatement("SELECT CURRENT_SCHEMA \"current_schema\" FROM DUMMY;");
		ResultSet resultSet = prepareStatement.executeQuery();
		int column = resultSet.findColumn("current_schema");
		while (resultSet.next()) {
			currentSchema += resultSet.getString(column);
		}
		return currentSchema;
	}

	public Connection getConnection() throws SQLException {
		try {
//			Context ctx = new InitialContext();
//			Context xmlContext = (Context) ctx.lookup("java:comp/env");
//			DataSource ds = (DataSource) xmlContext.lookup("jdbc/DefaultDB");
//			Connection conn = ds.getConnection();
			Connection conn = DriverManager.getConnection("jdbc:postgresql://10.11.241.96:49703/postgres", "G2GQSQ78VBU7oJor", "N3zEk90yGoEuXQAg");
			System.out.println("Connected to database");
			return conn;
		} catch (Exception e) {
			// could happen if HDB support is not enabled
			return null;
		}
	}
		public String readData() throws SQLException {
			try {
//				
				Connection conn = getConnection();
				System.out.println("Connected to database");
				return "{'test':'chandan'}";
			} catch (Exception e) {
				// could happen if HDB support is not enabled
				return "Exception";
			}
	}
}
