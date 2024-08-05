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

public class DataEntryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String table = request.getParameter("table");
        String result = "";

        try {
            switch (table) {
                case "suppliers":
                    result = insertSupplier(request);
                    break;
                case "parts":
                    result = insertPart(request);
                    break;
                case "jobs":
                    result = insertJob(request);
                    break;
                case "shipments":
                    result = insertShipment(request);
                    break;
                default:
                    result = "Invalid table selection.";
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        request.setAttribute("executionResults", result);
        request.getRequestDispatcher("dataentry.jsp").forward(request, response);
    }

    private Connection getConnection() throws Exception {
        Properties props = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/classes/dataentry.properties")) {
            props.load(input);
        }
        return DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
    }

    private String insertSupplier(HttpServletRequest request) throws Exception {
        String sql = "INSERT INTO suppliers (snum, sname, status, city) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getParameter("snum"));
            pstmt.setString(2, request.getParameter("sname"));
            pstmt.setString(3, request.getParameter("status"));
            pstmt.setString(4, request.getParameter("city"));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected + " supplier record(s) inserted successfully.";
        }
    }

    private String insertPart(HttpServletRequest request) throws Exception {
        String sql = "INSERT INTO parts (pnum, pname, color, weight, city) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getParameter("pnum"));
            pstmt.setString(2, request.getParameter("pname"));
            pstmt.setString(3, request.getParameter("color"));
            pstmt.setString(4, request.getParameter("weight"));
            pstmt.setString(5, request.getParameter("city"));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected + " part record(s) inserted successfully.";
        }
    }

    private String insertJob(HttpServletRequest request) throws Exception {
        String sql = "INSERT INTO jobs (jnum, jname, numworkers, city) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getParameter("jnum"));
            pstmt.setString(2, request.getParameter("jname"));
            pstmt.setInt(3, Integer.parseInt(request.getParameter("numworkers")));
            pstmt.setString(4, request.getParameter("city"));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected + " job record(s) inserted successfully.";
        }
    }

    private String insertShipment(HttpServletRequest request) throws Exception {
        String sql = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getParameter("snum"));
            pstmt.setString(2, request.getParameter("pnum"));
            pstmt.setString(3, request.getParameter("jnum"));
            pstmt.setInt(4, Integer.parseInt(request.getParameter("quantity")));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected + " shipment record(s) inserted successfully.";
        }
    }
}