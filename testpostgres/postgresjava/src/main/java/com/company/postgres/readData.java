package com.company.postgres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.testpostgres.postgresjava.HelloServlet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class readData
 */
@WebServlet("/readData")
public class readData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public readData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		HelloServlet x = new HelloServlet();
		try {
//			response.getWriter().println(x.readData());
//			Connection conn = x.getConnection();
			Connection conn = x.getConnection();
			Statement stmt = conn.createStatement();
//			String sql = "insert into public.user_name values('chandan1');";
			String sql = "select * from \"Gemini_masterlist\"";
			ResultSet rs =	stmt.executeQuery(sql);
	
			JsonObject jsobj;
			JsonArray jsobjs = new JsonArray();
			while(rs.next())
			{ jsobj = new JsonObject();
//			response.getWriter().println("worked till rs.next()");
			jsobj.addProperty("SAP_OBJECT_TYPE",rs.getString("SAPObjectType"));
			jsobj.addProperty("Description", rs.getString(2));
			jsobjs.add(jsobj);
			}
		
			
			response.getWriter().println(jsobjs.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.getWriter().println("post call is working");
		
		   String body = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    BufferedReader bufferedReader = null;

		    try {
		        InputStream inputStream = request.getInputStream();
		        if (inputStream != null) {
		            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		            char[] charBuffer = new char[128];
		            int bytesRead = -1;
		            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
		                stringBuilder.append(charBuffer, 0, bytesRead);
		            }
		        } else {
		            stringBuilder.append("");
		        }
		    } catch (IOException ex) {
		        throw ex;
		    } finally {
		        if (bufferedReader != null) {
		            try {
		                bufferedReader.close();
		            } catch (IOException ex) {
		                throw ex;
		            }
		        }
		    }

		    body = stringBuilder.toString();
		    response.getWriter().println(body);
	}

}
