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

public class RootServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String sqlCommand = request.getParameter("sqlCommand");
        
        if ("Execute Command".equals(action)) {
            String result = executeCommand(sqlCommand);
            request.setAttribute("executionResults", result);
        } else if ("Reset Form".equals(action)) {
        } else if ("Clear Results".equals(action)) {
            request.setAttribute("executionResults", "");
        }
        
        request.getRequestDispatcher("root.jsp").forward(request, response);
    }

    private String executeCommand(String sqlCommand) {
        StringBuilder result = new StringBuilder();
        try {
            Properties props = new Properties();
            try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/classes/root.properties")) {
                props.load(input);
            }
            Connection conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
            
            Statement stmt = conn.createStatement();
            boolean isResultSet = stmt.execute(sqlCommand);
            
            if (isResultSet) {
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                result.append("<table border='1'>");
                result.append("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    result.append("<th>").append(metaData.getColumnName(i)).append("</th>");
                }
                result.append("</tr>");
                while (rs.next()) {
                    result.append("<tr>");
                    for (int i = 1; i <= columnCount; i++) {
                        result.append("<td>").append(rs.getString(i)).append("</td>");
                    }
                    result.append("</tr>");
                }
                result.append("</table>");
                rs.close();
            } else {
                int updateCount = stmt.getUpdateCount();
                result.append(updateCount).append(" row(s) affected.");
            }
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }
}