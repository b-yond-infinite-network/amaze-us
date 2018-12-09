package com.shephertz.app42.paas.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shephertz.app42.paas.sample.db.DBManager;

/**
 * Servlet implementation class Log
 * This class fetches data from the database
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Home() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 *      This function fetches all data from the user table
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Map<String, Object>> result = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<!doctype html><html><head><meta charset='utf-8'><title>App42 Sample Java-MySql Application</title><link href='css/style-User-Input-Form.css' rel='stylesheet' type='text/css'></head><body><div class='App42PaaS_header_wrapper'><div class='App42PaaS_header_inner'><div class='App42PaaS_header'><div class='logo'><a href='http://app42paas.shephertz.com'><img border='0' alt='App42PaaS' src='images/logo.png'></img></a></div></div></div></div><div class='App42PaaS_body_wrapper'><div class='App42PaaS_body'><div class='App42PaaS_body_inner'><div class='contactPage_title'>");
		try {
			// select query
			String query = "select * from user";
			System.out.println("Query: " + query);
			// Database Manager Called
			DBManager db = new DBManager();
			result = db.select(query);
			// Result is then show in form of tables on UI
		if (result.size() != 0) {
			out.print("<table><thead class='table-head'><tr><td>Name</td><td>Email</td><td>Description</td></tr></thead><tbody>");
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> appData = result.get(i);
				out.print("<tr><td>" + appData.get("name") + "</td><td>"
						+ appData.get("email") + "</td><td>"
						+ appData.get("description") + "</td></tr>");
			}
			out.print("</tbody></table>");
		} else {
			out.print("<h1>No data</h1><br/><br/>");
		}
		out.print("<br/><a href='/' style='font-size: 18px;'>Create Post</a>");
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("<h2 align='center'>Error occured. See Logs.</h2><br/><br/>");
			out.print("<br/><a href='/' style='font-size: 18px;'>Back</a>");
		} catch (Exception ex) {
			ex.printStackTrace();
			out.print("<h2 align='center'>Error occured. See Logs.</h2><br/><br/>");
			out.print("<br/><a href='/' style='font-size: 18px;'>Back</a>");
		}
		out.print("</div></div></div></div></body></html>");
	}

	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub\

	}

}
