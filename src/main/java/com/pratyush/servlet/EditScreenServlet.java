package com.pratyush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA WHERE ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");

        // get the id of record
        int id = 0;
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

            // Set the parameter for the query
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pw.println("<form action='editurl?id=" + id + "' method='post'>");
                    pw.println("<table align='center'>");
                    pw.println("<tr>");
                    pw.println("<td>Book Name</td>");
                    pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
                    pw.println("</tr>");
                    pw.println("<tr>");
                    pw.println("<td>Book Edition</td>");
                    pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
                    pw.println("</tr>");
                    pw.println("<tr>");
                    pw.println("<td>Book Price</td>");
                    pw.println("<td><input type='text' name='bookPrice' value='" + rs.getString(3) + "'></td>");
                    pw.println("</tr>");
                    pw.println("<tr>");
                    pw.println("<td><input type='submit' value='Edit'></td>");
                    pw.println("<td><input type='reset' value='Cancel'></td>");
                    pw.println("</tr>");
                    pw.println("</table>");
                    pw.println("</form>");
                } else {
                    pw.println("<h2>No book found with ID " + id + "</h2>");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>SQL Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
        pw.println("<a href='home.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
