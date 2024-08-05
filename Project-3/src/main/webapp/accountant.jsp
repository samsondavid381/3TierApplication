/* Name: David Samson
Course: CNT 4714 – Summer 2024 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 1, 2024
*/
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</title>
    <style>
        body { background-color: black; color: lime; font-family: Arial, sans-serif; }
        h1 { color: #00FF00; text-align: center; }
        .command-list { background-color: #333; padding: 20px; margin: 20px 0; }
        .command-list label { display: block; margin: 10px 0; color: #00BFFF; }
        .button { padding: 10px; margin: 5px; cursor: pointer; }
        #execute { background-color: green; color: white; }
        #clear { background-color: red; color: white; }
        #results { margin-top: 20px; border-top: 1px solid lime; padding-top: 10px; }
    </style>
</head>
<body>
    <h1>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h1>
    <p>You are connected to the Project 3 Enterprise System database as an <span style="color: red;">accountant-level</span> user.</p>
    <p>Please select the operation you would like to perform from the list below.</p>

    <form action="AccountantServlet" method="post" class="command-list">
        <label>
            <input type="radio" name="command" value="maxStatus" required>
            Get The Maximum Status Value Of All Suppliers (Returns a maximum value)
        </label>
        <label>
            <input type="radio" name="command" value="totalWeight">
            Get The Total Weight Of All Parts (Returns a sum)
        </label>
        <label>
            <input type="radio" name="command" value="totalShipments">
            Get The Total Number of Shipments (Returns the current number of shipments)
        </label>
        <label>
            <input type="radio" name="command" value="maxWorkers">
            Get The Name And Number Of Workers Of The Job With The Most Workers (Returns two values)
        </label>
        <label>
            <input type="radio" name="command" value="supplierList">
            List The Name And Status Of Every Supplier (Returns a list of supplier names with status)
        </label>
        
        <input type="submit" value="Execute Command" id="execute" class="button">
        <input type="reset" value="Clear Results" id="clear" class="button">
    </form>

    <p>All execution results will appear below this line.</p>
    <div id="results">
        Execution Results:<br>
        <%= request.getAttribute("executionResults") != null ? request.getAttribute("executionResults") : "" %>
    </div>
</body>
</html>