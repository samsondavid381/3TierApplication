
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Data Entry Application</title>
    <style>
        body { background-color: black; color: lime; font-family: Arial, sans-serif; }
        h1 { color: cyan; text-align: center; }
        .form-container { margin-bottom: 20px; border: 1px solid lime; padding: 10px; }
        .form-title { color: yellow; }
        input[type="text"], input[type="number"] { background-color: #333; color: white; border: 1px solid lime; padding: 5px; margin: 5px; }
        .button { padding: 10px; margin: 5px; cursor: pointer; }
        .enter-button { background-color: green; color: white; }
        .clear-button { background-color: red; color: white; }
        #results { margin-top: 20px; border-top: 1px solid lime; padding-top: 10px; }
    </style>
</head>
<body>
    <h1>Data Entry Application</h1>
    <p>You are connected to the Project 3 Enterprise System database as a <span style="color: red;">data-entry-level</span> user.</p>
    <p>Enter the data values in a form below to add a new record to the corresponding database table.</p>

    <div class="form-container">
        <h2 class="form-title">Suppliers Record Insert</h2>
        <form action="DataEntryServlet" method="post">
            <input type="text" name="snum" placeholder="snum" required>
            <input type="text" name="sname" placeholder="sname" required>
            <input type="text" name="status" placeholder="status" required>
            <input type="text" name="city" placeholder="city" required>
            <input type="hidden" name="table" value="suppliers">
            <input type="submit" value="Enter Supplier Record Into Database" class="button enter-button">
            <input type="reset" value="Clear Data and Results" class="button clear-button">
        </form>
    </div>

    <div class="form-container">
        <h2 class="form-title">Parts Record Insert</h2>
        <form action="DataEntryServlet" method="post">
            <input type="text" name="pnum" placeholder="pnum" required>
            <input type="text" name="pname" placeholder="pname" required>
            <input type="text" name="color" placeholder="color" required>
            <input type="text" name="weight" placeholder="weight" required>
            <input type="text" name="city" placeholder="city" required>
            <input type="hidden" name="table" value="parts">
            <input type="submit" value="Enter Part Record Into Database" class="button enter-button">
            <input type="reset" value="Clear Data and Results" class="button clear-button">
        </form>
    </div>

    <div class="form-container">
        <h2 class="form-title">Jobs Record Insert</h2>
        <form action="DataEntryServlet" method="post">
            <input type="text" name="jnum" placeholder="jnum" required>
            <input type="text" name="jname" placeholder="jname" required>
            <input type="number" name="numworkers" placeholder="numworkers" required>
            <input type="text" name="city" placeholder="city" required>
            <input type="hidden" name="table" value="jobs">
            <input type="submit" value="Enter Job Record Into Database" class="button enter-button">
            <input type="reset" value="Clear Data and Results" class="button clear-button">
        </form>
    </div>

    <div class="form-container">
        <h2 class="form-title">Shipments Record Insert</h2>
        <form action="DataEntryServlet" method="post">
            <input type="text" name="snum" placeholder="snum" required>
            <input type="text" name="pnum" placeholder="pnum" required>
            <input type="text" name="jnum" placeholder="jnum" required>
            <input type="number" name="quantity" placeholder="quantity" required>
            <input type="hidden" name="table" value="shipments">
            <input type="submit" value="Enter Shipment Record Into Database" class="button enter-button">
            <input type="reset" value="Clear Data and Results" class="button clear-button">
        </form>
    </div>

    <div id="results">
        Execution Results:<br>
        <%= request.getAttribute("executionResults") != null ? request.getAttribute("executionResults") : "" %>
    </div>
</body>
</html>