<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.InventoryFull" %>

<%
    ArrayList<InventoryFull> inventoryList = (ArrayList<InventoryFull>) request.getAttribute("inventory_list");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Common Inventory</title>
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
    <h1 class="page__title">Common Inventory</h1>

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

    <a href="InventoryForm" class="add-btn">
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

    <section class="filter">
        <span class="section__title">Filters:</span>

        <form action="" class="filter-form">
            <div class="form__group vertical">
                <div class="form__group horizontal large">
                    <div class="form__control">
                        <label for="item" class="form__input-label">Item Name:</label>
                        <select name="item_name" id="item" class="form__input-field" class="form__input-field">
                            <option value="0">Excalibur</option>
                            <option value="1">Dragon Claws</option>
                        </select>
                    </div>

                    <div class="form__control">
                        <label for="owner" class="form__input-label">Owner Username:</label>
                        <input type="text" name="owner_username" id="owner" class="form__input-field"
                               placeholder="Owner Username ...">
                    </div>

                    <div class="form__group horizontal">
                        <div class="form__control">
                            <label for="min-durability" class="form__input-label">Durability:</label>
                            <input type="number" name="min_durability" id="min-durability" class="form__input-field"
                                   placeholder="Min Durability">
                        </div>

                        <div class="form__control">
                            <label for="max-durability" class="form__input-label">&nbsp;</label>
                            <input type="number" name="max_durability" id="max-durability" class="form__input-field"
                                   placeholder="Max Durability">
                        </div>
                    </div>
                </div>

                <div class="form__group horizontal large">
                    <div class="form__control">
                        <label for="type" class="form__input-label">Item Type:</label>
                        <select name="item_type" id="type" class="form__input-field">
                            <option value="0">Weapon</option>
                            <option value="1">Food</option>
                            <option value="1">Armor</option>
                        </select>
                    </div>

                    <div class="form__control">
                        <label for="rarity" class="form__input-label">Item Rarity:</label>
                        <select name="rarity" id="rarity" class="form__input-field">
                            <option value="0">Common</option>
                            <option value="1">Rare</option>
                        </select>
                    </div>

                    <div class="form__group horizontal">
                        <div class="form__control">
                            <label for="min-quantity" class="form__input-label">Quantity:</label>
                            <input type="number" name="min_quantity" id="min-quantity" class="form__input-field"
                                   placeholder="Min Quantity">
                        </div>

                        <div class="form__control">
                            <label for="max-quantity" class="form__input-label">&nbsp;</label>
                            <input type="number" name="max_quantity" id="max-quantity" class="form__input-field"
                                   placeholder="Max Quantity">
                        </div>
                    </div>
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
            for (InventoryFull inventory : inventoryList) {
        %>
        <div class="card-container card">
            <div class="card__img">
                <div class="action-container">
                    <%
                        if (inventory.getPlayerCharacterName() != null && !inventory.getPlayerCharacterName().equals("")) {
                    %>
                    <a href="InventoryRD?mode=d&&type=w&&inventory_id=<% out.print(inventory.getInventoryID()); %>"><span
                            class="fa fa-archive action-icon"></span></a>
                    <%
                        }
                    %>

                    <a href="InventoryCU?mode=u&&inventory_id=<% out.print(inventory.getInventoryID()); %>"><span
                            class="fa fa-pencil-alt action-icon"></span></a>
                    <a href="InventoryRD?mode=d&&inventory_id=<% out.print(inventory.getInventoryID()); %>"><span
                            class="fa fa-trash-alt action-icon"></span></a>
                </div>

                <%
                    String imgPath = (inventory.getImgPath() == null || inventory.getImgPath().equalsIgnoreCase("item/")) ? "item/default.jpeg" : inventory.getImgPath();
                %>
                <img src="upload/<% out.print(imgPath); %>" alt="Armor image">
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
                        <span class="card__detail-data"><% out.println(inventory.getItemName()); %></span>
                    </div>
                    <div class="card__detail-item">
                        <span class="card__detail-label">owned by</span>
                        <span class="card__detail-data">
                            <%
                                String charName = (inventory.getPlayerCharacterName() == null || inventory.getPlayerCharacterName().equalsIgnoreCase("")) ? "Unavailable" : inventory.getPlayerCharacterName();
                                out.println(charName); %>
                        </span>
                    </div>
                    <div class="card__detail-item">
                        <span class="card__detail-label">durability</span>
                        <span class="card__detail-data"><% out.println(inventory.getDurability()); %>%</span>
                    </div>
                    <div class="card__detail-group">
                        <div class="card__detail-item">
                            <span class="card__detail-label">type</span>
                            <span class="card__detail-data">
                                <%
                                    String typeName = (inventory.getTypeID() == 0) ? "None" : inventory.getTypeName();
                                    out.println(typeName);
                                %>
                            </span>
                        </div>
                        <div class="card__detail-item">
                            <span class="card__detail-label">rarity</span>
                            <span class="card__detail-data">
                                <%
                                    String rarityName = (inventory.getRarityID() == 0) ? "None" : inventory.getRarityName();
                                    out.println(rarityName);
                                %>
                            </span>
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