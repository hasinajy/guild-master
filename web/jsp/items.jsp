<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Item" %>
<%@ page import="utils.NameChecker" %>

<%
    List<Item> itemList = (List<Item>) request.getAttribute("item-list");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Items Database</title>
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
    <h1 class="page__title">Items Database</h1>

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
    <a href="${pageContext.request.contextPath}/item-cu" class="add-btn">
        <svg class="add-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
            <path id="add" d="M207.143,312v-7.143H200v-1.714h7.143V296h1.714v7.143H216v1.714h-7.143V312Z"
                  transform="translate(-200 -296)" fill="#f2f2f2"/>
        </svg>

        <span>Add Item</span>
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

    <section class="filter">
        <span class="section__title">Filter:</span>

        <form action="${pageContext.request.contextPath}/items" class="filter-form">
            <div class="form__group horizontal large hidden">
                <div class="form__control">
                    <label for="mode" class="form__input-label">Mode:</label>
                    <input type="text" name="mode" value="s" id="mode"
                           class="form__input-field" placeholder="Mode ...">
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="item-name" class="form__input-label">Item Name:</label>
                    <input type="text" name="item-name" id="item-name" class="form__input-field"
                           placeholder="Item Name ...">
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
            for
            (
            Item
            item
            :
            itemList
            )
            {
        %>
        <div class="card-container card">
            <div class="card__img">
                <%
                    if
                    (
                    username
                    !=
                    null
                    &&
                    !
                    username
                    .
                    isEmpty
                    (
                    )
                    )
                    {
                %>
                <div class="action-container">
                    <a href="${pageContext.request.contextPath}/item-cu?mode=u&&item-id=<%= item.getItemId() %>"><span
                            class="fa fa-pencil-alt action-icon"></span></a>
                    <a href="${pageContext.request.contextPath}/items?mode=d&&item-id=<%= item.getItemId() %>"><span
                            class="fa fa-trash-alt action-icon"></span></a>
                </div>
                <%
                    }
                %>

                <%
                    String
                    imgPath
                    =
                    (
                    NameChecker
                    .
                    isNewImgPath
                    (
                    item
                    .
                    getImgPath
                    (
                    )
                    ,
                    "item"
                    )
                    )
                    ?
                    item
                    .
                    getImgPath
                    (
                    )
                    :
                    "item/default.jpeg"
                    ;
                %>

                <img src="uploads/<%= imgPath %>" alt="Armor">
            </div>

            <div class="card__desc">
                <span class="card__title">Item Description</span>
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
                        <span class="card__detail-label">name</span>
                        <span class="card__detail-data"><%= (
                            item
                            .
                            getItemId
                            (
                            )
                            ==
                            0
                            )
                            ?
                            "None"
                            :
                            item
                            .
                            getName
                            (
                            ) %></span>
                    </div>

                    <div class="card__detail-group">
                        <div class="card__detail-item">
                            <span class="card__detail-label">type</span>
                            <span class="card__detail-data"><%= (
                                item
                                .
                                getType
                                (
                                )
                                .
                                getTypeId
                                (
                                )
                                ==
                                0
                                )
                                ?
                                "None"
                                :
                                item
                                .
                                getType
                                (
                                )
                                .
                                getName
                                (
                                ) %></span>
                        </div>
                        <div class="card__detail-item">
                            <span class="card__detail-label">rarity</span>
                            <span class="card__detail-data"><%= (
                                item
                                .
                                getRarity
                                (
                                )
                                .
                                getRarityId
                                (
                                )
                                ==
                                0
                                )
                                ?
                                "None"
                                :
                                item
                                .
                                getRarity
                                (
                                )
                                .
                                getName
                                (
                                ) %></span>
                        </div>
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