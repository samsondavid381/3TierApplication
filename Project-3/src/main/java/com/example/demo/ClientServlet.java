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

public class ClientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String sqlCommand = request.getParameter("sqlCommand");
        
        if ("Execute Command".equals(action)) {
            String results = executeCommand(sqlCommand);
            request.setAttribute("executionResults", results);
        } else if ("Reset Form".equals(action)) {
        } else if ("Clear Results".equals(action)) {
            request.setAttribute("executionResults", "");
        }
        
        request.getRequestDispatcher("client.jsp").forward(request, response);
    }
private String executeCommand(String sqlCommand) {
    StringBuilder results = new StringBuilder();
    try {
        Properties props = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/classes/client.properties")) {
            props.load(input);
        }
        
        Connection conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        
        Statement stmt = conn.createStatement();
        boolean isResultSet = stmt.execute(sqlCommand);
        
        if (isResultSet) {
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            results.append("<table border='1'><tr>");
            for (int i = 1; i <= columnCount; i++) {
                results.append("<th>").append(metaData.getColumnName(i)).append("</th>");
            }
            results.append("</tr>");
            while (rs.next()) {
                results.append("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    results.append("<td>").append(rs.getString(i)).append("</td>");
                }
                results.append("</tr>");
            }
            results.append("</table>");
            
            rs.close();
        } else {
            int updateCount = stmt.getUpdateCount();
            results.append(updateCount).append(" row(s) affected.");
        }
        
        stmt.close();
        conn.close();
    } catch (Exception e) {
        results.append("Error: ").append(e.getMessage());
    }
    return results.toString();
}

}