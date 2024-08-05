/* Name: David Samson
Course: CNT 4714 – Summer 2024 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 1, 2024
*/
package com.example.demo;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;
public class AuthenticateServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AuthenticateServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        LOGGER.info("Login attempt for username: " + username);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties props = new Properties();
            try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/classes/systemapp.properties")) {
                props.load(input);
            }
            
            Connection conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
            
            String sql = "SELECT * FROM usercredentials WHERE login_username = ? AND login_password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            LOGGER.info("Executing SQL: " + sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                LOGGER.info("Authentication successful for username: " + username);
                HttpSession session = request.getSession();
                session.setAttribute("username", rs.getString("login_username"));
                
                String userType = rs.getString("login_username");
                LOGGER.info("User type: " + userType);
                if (userType.equals("root")) {
                    response.sendRedirect("root.jsp");
                } else if (userType.equals("client")) {
                    response.sendRedirect("client.jsp");
                } else if (userType.equals("dataentryuser")) {
                    response.sendRedirect("dataentry.jsp");
                } else if (userType.equals("theaccountant")) {
                    response.sendRedirect("accountant.jsp");
                } else {
                    LOGGER.warning("Unknown user type: " + userType);
                    response.sendRedirect("index.html?error=unknown_user");
                }
            } else {
                LOGGER.info("Authentication failed for username: " + username);
                response.sendRedirect("index.html?error=1");
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database access error", e);
            throw new ServletException("Database access error", e);
        }
    }
}