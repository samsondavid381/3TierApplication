
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</title>
    <style>
        body { background-color: black; color: lime; font-family: Arial, sans-serif; }
        h1 { color: lime; text-align: center; }
        .user-input { width: 100%; height: 200px; background-color: blue; color: white; }
        .button { padding: 10px; margin: 5px; cursor: pointer; }
        #execute { background-color: green; }
        #reset { background-color: red; }
        #clear { background-color: yellow; color: black; }
        #results { margin-top: 20px; border-top: 1px solid lime; padding-top: 10px; }
        
    </style>
</head>
<body>
    <h1>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h1>
    <p>You are connected to the Project 3 Enterprise System database as a <span style="color: red;">client-level</span> user.</p>
    <p>Please enter any SQL query command in the box below.</p>
    
    <form action="ClientServlet" method="post">
        <textarea name="sqlCommand" class="user-input"></textarea><br>
        <input type="submit" name="action" value="Execute Command" id="execute" class="button">
        <input type="submit" name="action" value="Reset Form" id="reset" class="button">
        <input type="submit" name="action" value="Clear Results" id="clear" class="button">
    </form>
    
    <p>All execution results will appear below this line.</p>
    <div id="results">
        Execution Results:<br>
        <%= request.getAttribute("executionResults") != null ? request.getAttribute("executionResults") : "" %>
    </div>
</body>
</html>