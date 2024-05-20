<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.PlayerFull" %>

<%
    ArrayList<PlayerFull> playerList = (ArrayList<PlayerFull>) request.getAttribute("player_list");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Players Database</title>
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
    <h1 class="page__title">Players Database</h1>

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

    <a href="PlayerForm" class="add-btn">
        <svg class="add-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
            <path id="add" d="M207.143,312v-7.143H200v-1.714h7.143V296h1.714v7.143H216v1.714h-7.143V312Z"
                  transform="translate(-200 -296)" fill="#f2f2f2"/>
        </svg>

        <span>Add Player</span>
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

    <section class="filter">
        <span class="section__title">Filters:</span>

        <form action="" class="filter-form">
            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="username" class="form__input-label">Username:</label>
                    <input type="text" name="username" id="username" class="form__input-field"
                           placeholder="Username ...">
                </div>

                <div class="form__control">
                    <label for="character-name" class="form__input-label">Character Name:</label>
                    <input type="text" name="character_name" id="character-name" class="form__input-field"
                           placeholder="Character Name ...">
                </div>

                <div class="form__group horizontal">
                    <div class="form__control">
                        <label for="min-level" class="form__input-label">Level:</label>
                        <input type="number" name="min_level" id="min-level" class="form__input-field"
                               placeholder="Min Level">
                    </div>

                    <div class="form__control">
                        <label for="max-level" class="form__input-label">&nbsp;</label>
                        <input type="number" name="max_level" id="max-level" class="form__input-field"
                               placeholder="Max Level">
                    </div>
                </div>

                <div class="form__control">
                    <label for="faction" class="form__input-label">Faction:</label>
                    <select name="faction" id="faction" class="form__input-field">
                        <option value="0">Black Dragon Adventurers</option>
                        <option value="1">The Royal Fangs</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="form__submit btn">
                <svg class="search-icon" xmlns="http://www.w3.org/2000/svg" width="14" height="14"
                     viewBox="0 0 14 14">
                    <path id="search"
                          d="M133.144,229l-5.114-5.114a4.287,4.287,0,0,1-1.36.788,5.024,5.024,0,1,1,3.013-3.082,4.936,4.936,0,0,1-.817,1.458L134,228.144ZM125,223.789a3.637,3.637,0,0,0,2.686-1.118,3.832,3.832,0,0,0,0-5.386A3.637,3.637,0,0,0,125,216.167a3.811,3.811,0,1,0,0,7.622Z"
                          transform="translate(-120 -215)" fill="#f2f2f2"/>
                </svg>
                <span>Filter</span>
            </button>
        </form>
    </section>

    <div class="sep-small">
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

    <div class="content grid-container">
        <%
            for (PlayerFull player : playerList) {
                String gender = player.getGenderName();
                String imgSrc = "assets/img/";
                String imgClass = "card__img";

                if (gender.equalsIgnoreCase("male")) {
                    imgSrc += "Male-profile.jpeg";
                    imgClass += " male";
                } else {
                    imgSrc += "Female-profile.jpeg";
                }
        %>
        <div class="card-container card">
            <div class="<% out.print(imgClass); %>">
                <div class="action-container">
                    <a href="PlayerCU?mode=u&&player_id=<% out.print(player.getPlayerID()); %>"><span
                            class="fa fa-pencil-alt action-icon"></span></a>
                    <a href="PlayerRD?mode=d&&player_id=<% out.print(player.getPlayerID()); %>"><span
                            class="fa fa-trash-alt action-icon"></span></a>
                </div>

                <%
                    String imgPath = (player.getImgPath() == null || player.getImgPath().equals("player/")) ? "player/default.jpeg" : player.getImgPath();
                %>
                <img src="upload/<% out.print(imgPath); %>" alt="Armor image">
            </div>
            <div class="card__desc">
                <span class="card__title">Player Description</span>
                <svg class="sep--small" xmlns="http://www.w3.org/2000/svg" width="84" height="5.657"
                     viewBox="0 0 84 5.657">
                    <rect id="Rectangle_3" data-name="Rectangle 3" width="4" height="4"
                          transform="translate(42) rotate(45)" fill="#fff"/>
                    <line id="Line_5" data-name="Line 5" x2="35" transform="translate(0 2.828)" fill="none"
                          stroke="#fff" stroke-width="1"/>
                    <line id="Line_6" data-name="Line 6" x2="35" transform="translate(49 2.828)" fill="none"
                          stroke="#fff" stroke-width="1"/>
                </svg>


                <div class="card__details">
                    <div class="card__detail-item">
                        <span class="card__detail-label">username</span>
                        <span class="card__detail-data">
                            <% out.print(player.getUsername()); %>
                        </span>
                    </div>
                    <div class="card__detail-item">
                        <span class="card__detail-label">character Name</span>
                        <span class="card__detail-data">
                            <% out.print(player.getCharacterName()); %>
                        </span>
                    </div>
                    <div class="card__detail-item">
                        <span class="card__detail-label">level</span>
                        <span class="card__detail-data">
                            <% out.print(player.getLevel()); %>
                        </span>
                    </div>
                    <div class="card__detail-item">
                        <span class="card__detail-label">faction</span>
                        <span class="card__detail-data">
                            <%
                                String faction = (player.getFactionName() == null) ? "None" : player.getFactionName();
                            %>
                            <% out.print(faction); %>
                        </span>
                    </div>
                </div>
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