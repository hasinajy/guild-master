<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Faction" %>
<%@ page import="utils.NameChecker" %>

<%
    List<Faction> factionList = (List<Faction>) request.getAttribute("faction-list");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Factions</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/layout.css">
    <link rel="stylesheet" href="assets/css/filter.css">
    <link rel="stylesheet" href="assets/css/grid-container.css">
    <link rel="stylesheet" href="assets/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/fontawesome/css/solid.min.css">
</head>

<body>
<div class="body-bg light">
    <img src="assets/img/background.PNG" alt="">
</div>

<jsp:include page="header.jsp"/>

<div class="body wrapper">
    <h1 class="page__title">Factions Database</h1>

    <div class="sep--large">
        <svg class="sep--large" xmlns="http://www.w3.org/2000/svg" width="715" height="22.627"
             viewBox="0 0 715 22.627">
            <rect id="Rectangle_3" data-name="Rectangle 3" width="16" height="16"
                  transform="translate(357.5) rotate(45)" fill="#4c4c4c"/>
            <line id="Line_1" data-name="Line 1" x2="330" transform="translate(385 11.314)" fill="none"
                  stroke="#4c4c4c" stroke-width="1"/>
            <line id="Line_2" data-name="Line 2" x2="330" transform="translate(0 11.314)" fill="none"
                  stroke="#4c4c4c" stroke-width="1"/>
        </svg>

    </div>

    <%
        String username = (String) session.getAttribute("username");

        if (username != null && !username.isEmpty()) {
    %>
    <a href="${pageContext.request.contextPath}/faction-form" class="add-btn">
        <svg class="add-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
            <path id="add" d="M207.143,312v-7.143H200v-1.714h7.143V296h1.714v7.143H216v1.714h-7.143V312Z"
                  transform="translate(-200 -296)" fill="#f2f2f2"/>
        </svg>

        <span>Add Faction</span>
    </a>

    <div class="sep--small">
        <svg class="sep--small" xmlns="http://www.w3.org/2000/svg" width="455" height="22.627"
             viewBox="0 0 455 22.627">
            <rect id="Rectangle_3" data-name="Rectangle 3" width="16" height="16"
                  transform="translate(227.5) rotate(45)" fill="#4c4c4c"/>
            <line id="Line_1" data-name="Line 1" x2="200" transform="translate(255 11.314)" fill="none"
                  stroke="#4c4c4c" stroke-width="1"/>
            <line id="Line_4" data-name="Line 4" x2="200" transform="translate(0 11.314)" fill="none"
                  stroke="#4c4c4c" stroke-width="1"/>
        </svg>

    </div>
    <%
        }
    %>

    <div class="content grid-container">
        <%
            for (Faction faction : factionList) {
        %>
        <div class="card-container card single">
            <div class="card__img">
                <%
                    if (username != null && !username.isEmpty()) {
                %>
                <div class="action-container">
                    <a href="${pageContext.request.contextPath}/faction-cu?mode=u&&faction-id=<%= faction.getFactionId() %>"><span
                            class="fa fa-pencil-alt action-icon"></span></a>
                    <a href="${pageContext.request.contextPath}/factions?mode=d&&faction-id=<%= faction.getFactionId() %>"><span
                            class="fa fa-trash-alt action-icon"></span></a>
                </div>
                <%
                    }
                %>

                <%
                    String imgPath = (NameChecker.isNewImgPath(faction.getImgPath(), "faction")) ? faction.getImgPath() : "faction/default.jpeg";
                %>

                <img src="uploads/<%= imgPath %>" alt="Image of a faction">
            </div>

            <svg class="sep--small" xmlns="http://www.w3.org/2000/svg" width="84" height="5.657"
                 viewBox="0 0 84 5.657">
                <rect id="Rectangle_3" data-name="Rectangle 3" width="4" height="4"
                      transform="translate(42) rotate(45)" fill="#fff"/>
                <line id="Line_5" data-name="Line 5" x2="35" transform="translate(0 2.828)" fill="none"
                      stroke="#fff" stroke-width="1"/>
                <line id="Line_6" data-name="Line 6" x2="35" transform="translate(49 2.828)" fill="none"
                      stroke="#fff" stroke-width="1"/>
            </svg>

            <div class="card__desc">
                <span class="card__detail-label">
                <%= faction.getName() %>
                </span>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>

</html>