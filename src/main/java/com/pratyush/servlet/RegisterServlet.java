package com.pratyush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.catalina.startup.HomesUserDatabase;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query ="INSERT INTO  BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set contenttype
		res.setContentType("text/html");
		
		//get the book info
		String bookName = req.getParameter("bookName");
		String bookEdition =req.getParameter("bookEdition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		// LOAD JDBC DRIVER
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException cnf) {
			// TODO: handle exception
			cnf.printStackTrace();
			pw.println("<h2>Error loading JDBC driver: " + cnf.getMessage() + "</h2>");
            return;
			
		}
		//generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","pratyush143")) {
			PreparedStatement ps =con.prepareStatement(query);
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			int count =ps.executeUpdate();
			if(count ==1) {
				pw.println("<h2>Record is Registered Succesfully");
			}
			else {
				pw.println("<h2> Record is not Registered Sucessfully");
			}
			
		}catch (SQLException se) {
			
			se.printStackTrace();
			pw.println("<h2> SQL Error: "+se.getMessage()+"</h2>");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			pw.println("<h2>Error: "+e.getMessage()+"</h2>");
		}
		
		pw.println("<a href='home.html' >Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList' >Book List</a>");
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
