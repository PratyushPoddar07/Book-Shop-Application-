package com.pratyush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String query = "DELETE FROM BOOKDATA   WHERE ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");

        // get the id of record
        int id = Integer.parseInt(req.getParameter("id"));
        
        
        
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException nfe) {
            pw.println("<h2>Invalid Book ID</h2>");
            return;
        }

        // LOAD JDBC DRIVER
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h2>Error loading JDBC driver: " + cnf.getMessage() + "</h2>");
            return;
        }

        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "pratyush143");
             PreparedStatement ps = con.prepareStatement(query)) {

        	ps.setInt(1, id);
        	
        	int count =ps.executeUpdate();
        	if(count == 1) {
        		pw.println("<h2> Record is Deleted Successfully </h2>");
        	}else {
        		pw.println("<h2> Record is  not Deleted Successfully </h2>");
        		
        	}
        	
           
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>SQL Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
        pw.println("<a href='home.html'>Home</a>");
        pw.println("<br>");
		pw.println("<a href='bookList' >Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
