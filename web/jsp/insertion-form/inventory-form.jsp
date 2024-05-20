<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Inventory" %>
<%@ page import="models.PlayerFull" %>
<%@ page import="models.Item" %>
<%@ page import="java.util.ArrayList" %>

<%
    String sectionTitle = "Inventory Insertion", btnValue = "Insert";
    String mode = request.getParameter("mode");
    Inventory updatedInventory = null;
    String inventoryID = "", itemID = "", playerID = "", durability = "";

    ArrayList<Item> itemList = (ArrayList<Item>) request.getAttribute("item_list");
    ArrayList<PlayerFull> playerList = (ArrayList<PlayerFull>) request.getAttribute("player_list");

    if (mode != null && mode.equals("u")) {
        sectionTitle = "Player Update";
        btnValue = "Update";
        updatedInventory = (Inventory) request.getAttribute("updated_inventory");

        inventoryID = String.valueOf(updatedInventory.getInventoryID());
        itemID = String.valueOf(updatedInventory.getItemID());
        playerID = String.valueOf(updatedInventory.getPlayerID());
        durability = String.valueOf(updatedInventory.getDurability());
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Insertion</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/layout.css">
    <link rel="stylesheet" href="assets/css/filter.css">
    <link rel="stylesheet" href="assets/css/grid-container.css">
    <link rel="stylesheet" href="assets/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/fontawesome/css/solid.css">
</head>

<body>
<div class="body-bg dark">
    <img src="assets/img/background.PNG" alt="">
</div>

<jsp:include page="header.jsp"/>


<div class="body wrapper">
    <h1 class="page__title">
        <%
            out.print(sectionTitle);
        %>
    </h1>

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

    <div class="entity-form">
        <form action="InventoryCU" method="post" class="filter-form filter-form--borderless">
            <%
                if (mode != null) {
            %>
            <div class="form__group horizontal large hidden">
                <div class="form__control">
                    <label for="mode" class="form__input-label">Mode:</label>
                    <input type="text" name="mode" value="<% out.print(mode); %>" id="mode"
                           class="form__input-field" placeholder="Mode ...">
                </div>
            </div>
            <%
                }
            %>

            <div class="form__group horizontal large hidden">
                <div class="form__control">
                    <label for="inventory-id" class="form__input-label">Inventory ID:</label>
                    <input type="text" name="inventory_id" value="<% out.print(inventoryID); %>" id="inventory-id"
                           class="form__input-field" placeholder="Player ID ...">
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="item" class="form__input-label">Item:</label>
                    <select name="item_id" id="item" class="form__input-field">
                        <%
                            for (Item item : itemList) {
                                String itemSelect = "";

                                if (mode != null && updatedInventory.getItemID() == item.getItemID()) {
                                    itemSelect = "selected";
                                }
                        %>
                        <option value="<% out.print(item.getItemID()); %>" <% out.print(itemSelect); %>>
                            <% out.print(item.getName()); %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="player" class="form__input-label">Player:</label>
                    <select name="player_id" id="player" class="form__input-field">
                        <%
                            for (PlayerFull player : playerList) {
                                String playerSelect = "";

                                if (mode != null && updatedInventory.getPlayerID() == player.getPlayerID()) {
                                    playerSelect = "selected";
                                }
                        %>
                        <option value="<% out.print(player.getPlayerID()); %>" <% out.print(playerSelect); %>>
                            <% out.print(player.getCharacterName()); %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="durability" class="form__input-label">Durability:</label>
                    <input type="text" name="durability" value="<% out.print(durability); %>" id="durability"
                           class="form__input-field" placeholder="Durability ...">
                </div>
            </div>

            <button type="submit" class="form__submit btn">
                <svg class="search-icon" xmlns="http://www.w3.org/2000/svg" width="14" height="14"
                     viewBox="0 0 14 14">
                    <path id="search"
                          d="M133.144,229l-5.114-5.114a4.287,4.287,0,0,1-1.36.788,5.024,5.024,0,1,1,3.013-3.082,4.936,4.936,0,0,1-.817,1.458L134,228.144ZM125,223.789a3.637,3.637,0,0,0,2.686-1.118,3.832,3.832,0,0,0,0-5.386A3.637,3.637,0,0,0,125,216.167a3.811,3.811,0,1,0,0,7.622Z"
                          transform="translate(-120 -215)" fill="#f2f2f2"/>
                </svg>
                <span>
                        <% out.print(btnValue); %>
                </span>
            </button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>

</html>