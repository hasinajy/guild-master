<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Dashboard" %>

<%
    Dashboard dashboard = (Dashboard) request.getAttribute("dashboard-data");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/dashboard.css">
</head>

<body>
    <div class="body-bg dark">
        <img src="assets/img/background.PNG" alt="">
    </div>

    <jsp:include page="header.jsp" />

    <div class="body wrapper">
        <div class="stat-container">
            <div class="stat-group">
                <div class="solo-stat">
                    <span class="stat__label">Players Count</span>
                    <span class="stat__data"><%= dashboard.getPlayersCount() %> players</span>
                </div>

                <div class="solo-stat">
                    <span class="stat__label">Stored Items</span>
                    <span class="stat__data"><%= dashboard.getStoredItems() %> items</span>
                </div>
            </div>

            <div class="solo-stat">
                <span class="stat__label">Transactions Count</span>
                <span class="stat__data"><%= dashboard.getTransactionsCount() %> transactions</span>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>

</html>