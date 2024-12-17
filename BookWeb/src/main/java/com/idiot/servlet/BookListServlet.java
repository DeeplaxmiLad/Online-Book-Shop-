package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final String query = "SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Deep@08lad04"); 
        		PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            pw.println("<table border='1'>");
            pw.println("<tr>");
            pw.println("<th>Book Id</th>");
            pw.println("<th>Book Name</th>");
            pw.println("<th>Book Edition</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getFloat(4) + "</td>");
                pw.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<a href='home.html'><button>Home</button></a>");
        pw.println("<br><br>");
        pw.println("<br><br>");
        pw.println("<a href='Feedback.html'><button>Feedback</button></a>");
        pw.println("<br><br>");
        pw.println("<br><br>");
        pw.println("<br><br>");
        pw.println("<br><br>");
        pw.println("<style>");
        pw.println(".footer {");
        		pw.println("text-align:center;");
        		pw.println("background: black;");
        		pw.println("display:flex;");
        		pw.println("justify-content:center;");
        		pw.println("padding: 50px 0;");
        		pw.println("color: white;");
        		pw.println("font-size: 14px;");
        		pw.println("min-width: 250px;");
        		pw.println("margin-bottom: 20px;");
        		pw.println(" }");
        		pw.println(".copyright {");
        		pw.println(" text-align: center;");
        		pw.println("</style>");
        pw.println("<div class='footer'>");
        pw.println("<h3>Useful Links</h3>");
        pw.println("<ul>");
        pw.println("<li>Coupons</li>");
        pw.println("<li>Blog Post</li>");
        pw.println("<li>Return Policy</li>");
        pw.println("<li>Join Affiliate</li>");
        pw.println("</ul>");
        pw.println("<h3>Follow us</h3>");
        pw.println("<ul>");
        pw.println("<li>Facebook</li>");
        pw.println("<li>Youtube</li>");
        pw.println("<li>Instagram</li>");
        pw.println("<li>Twitterr</li>");
        pw.println("</ul>");
        pw.println("</div>");
        pw.println("<p class='copyright'>Copyright 2024 - EbookStore</p>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}