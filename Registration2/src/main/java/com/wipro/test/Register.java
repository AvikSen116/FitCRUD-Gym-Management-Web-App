package com.wipro.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        response.setContentType("text/html;charset=UTF-8");
        
        try(PrintWriter prt = response.getWriter()) {
            
            prt.println("<!DOCTYPE html>");
            prt.println("<html>");
            prt.println("<head>");
            prt.println("<title>Servlet Register</title>");
            prt.println("</head>");
            prt.println("<body>");
            
            String name = request.getParameter("user_name");
            String email = request.getParameter("user_email");
            String pass = request.getParameter("user_password");
            
            prt.println("<p>Name: " + name + "</p>");
            prt.println("<p>Email: " + email + "</p>");
            prt.println("<p>Password: " + pass + "</p>");
            
            try {
                String jdbcUrl = "jdbc:mysql://localhost:3306/emp";
                String username = "root";
                String password = "Avik@116";
                
                // Load the MySQL JDBC driver (not needed in newer JDBC versions)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection
                try (Connection con = DriverManager.getConnection(jdbcUrl, username, password)) {
                    String q = "INSERT INTO USERS (name, password, email) VALUES (?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(q);
                    pst.setString(1, name);
                    pst.setString(2, pass);
                    pst.setString(3, email);
                    
                    int rowsInserted = pst.executeUpdate();
                    
                    if (rowsInserted > 0) {
                        prt.println("<h1>Congratulations " + name + "   for registering in our database!!</h1>");
                    } else {
                        prt.println("<h1>Error: Registration failed</h1>");
                    }
                }
            }
            catch(Exception se) {
                se.printStackTrace();
                prt.println("<h1>ERROR</h1>");
            }
            
            prt.println("</body>");
            prt.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
