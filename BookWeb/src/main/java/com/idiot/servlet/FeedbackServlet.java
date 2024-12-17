package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitFeedback")
public class FeedbackServlet extends HttpServlet {

    private static final String query = "INSERT INTO FEEDBACK (USERNAME, EMAIL, FEEDBACK) VALUES (?, ?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        // Retrieve form data
        String userName = req.getParameter("userName");
        String userEmail = req.getParameter("userEmail");
        String userFeedback = req.getParameter("userFeedback");

        Connection con = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            String url = "jdbc:mysql://localhost:3306/book?useSSL=false";
            con = DriverManager.getConnection(url, "root", "Deep@08lad04");

            // Prepare and execute SQL query
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, userName);
                ps.setString(2, userEmail);
                ps.setString(3, userFeedback);

                int count = ps.executeUpdate();
                if (count > 0) {
                    pw.println("<h2>Feedback submitted successfully!</h2>");
                } else {
                    pw.println("<h2>Failed to submit feedback. Please try again!</h2>");
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h1>Error: JDBC Driver not found!</h1>");
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h1>Error: Unable to connect to the database or execute query!</h1>");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        pw.println("<a href='home.html'><button>Home</button></a>");
    }
}
