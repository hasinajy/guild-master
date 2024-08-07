<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="models.Transaction" %>
<%@ page import="models.Item" %>
<%@ page import="models.Player" %>
<%@ page import="models.TransactionType" %>

<%
    String sectionTitle = "Transaction Insertion", btnValue = "Insert";
    String mode = request.getParameter("mode");
    Transaction updatedTransaction = null;
    String transactionId = "", transactionTypeId = "", transactionDate = "", itemId = "", playerId = "";

    List<TransactionType> transactionTypeList = (List<TransactionType>) request.getAttribute("transaction-type-list");
    List<Item> itemList = (List<Item>) request.getAttribute("item-list");
    List<Player> playerList = (List<Player>) request.getAttribute("player-list");

    if (mode != null && mode.equals("u")) {
        sectionTitle = "Transaction Update";
        btnValue = "Update";
        updatedTransaction = (Transaction) request.getAttribute("updated-transaction");
        transactionId = String.valueOf(updatedTransaction.getTransactionId());
        transactionDate = updatedTransaction.getDate().toString();
        transactionTypeId = String.valueOf(updatedTransaction.getTransactionType().getTransactionTypeId());
        itemId = String.valueOf(updatedTransaction.getItem().getItemId());
        playerId = String.valueOf(updatedTransaction.getPlayer().getPlayerId());
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rarity Insertion Form</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/layout.css">
    <link rel="stylesheet" href="assets/css/filter.css">
    <link rel="stylesheet" href="assets/css/grid-container.css">
    <link rel="stylesheet" href="assets/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/fontawesome/css/solid.css">
</head>

<body>
<div class="body-bg light">
    <img src="assets/img/background.PNG" alt="">
</div>

<jsp:include page="header.jsp"/>

<div class="body wrapper">
    <h1 class="page__title">
        <%= sectionTitle %>
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
        <form action="${pageContext.request.contextPath}/transaction-cu" method="post" class="filter-form filter-form--borderless">
            <% if (mode != null) { %>
            <div class="form__group horizontal large hidden">
                <div class="form__control">
                    <label for="mode" class="form__input-label">Mode:</label>
                    <input type="text" name="mode" value="<%= mode %>" id="mode"
                           class="form__input-field" placeholder="Mode ...">
                </div>
            </div>
            <% } %>

            <div class="form__group horizontal large hidden">
                <div class="form__control">
                    <label for="transaction-id" class="form__input-label">Transaction ID:</label>
                    <input type="text" name="transaction-id" value="<%= transactionId %>"
                           id="transaction-id" class="form__input-field" placeholder="Transaction ID ...">
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="transaction-type" class="form__input-label">Transaction Type:</label>
                    <select name="transaction-type-id" id="transaction-type" class="form__input-field">
                        <%
                            for (TransactionType transactionType : transactionTypeList) {
                                String transactionTypeSelect = "";

                                if (mode != null && updatedTransaction.getTransactionType().getTransactionTypeId() == transactionType.getTransactionTypeId()) {
                                    transactionTypeSelect = "selected";
                                }
                        %>
                        <option value="<%= transactionType.getTransactionTypeId() %>" <%= transactionTypeSelect %>>
                            <%= transactionType.getName() %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="transaction-date" class="form__input-label">Transaction Date:</label>
                    <input type="date" name="transaction-date" value="<%= transactionDate %>"
                           id="transaction-date" class="form__input-field" placeholder="Transaction date ...">
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="item" class="form__input-label">Item:</label>
                    <select name="item-id" id="item" class="form__input-field">
                        <%
                            for (Item item : itemList) {
                                String itemSelect = "";

                                if (mode != null && updatedTransaction.getItem().getItemId() == item.getItemId()) {
                                    itemSelect = "selected";
                                }
                        %>
                        <option value="<%= item.getItemId() %>" <%= itemSelect %>>
                            <%= item.getName() %>
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
                    <select name="player-id" id="player" class="form__input-field">
                        <%
                            for (Player player : playerList) {
                                String playerSelect = "";

                                if (mode != null && updatedTransaction.getPlayer().getPlayerId() == player.getPlayerId()) {
                                    playerSelect = "selected";
                                }
                        %>
                        <option value="<%= player.getPlayerId() %>" <%= playerSelect %>>
                            <%= player.getName().getCharacterName() %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>

            <button type="submit" class="form__submit btn">
                <svg class="search-icon" xmlns="http://www.w3.org/2000/svg" width="14"
                     height="14" viewBox="0 0 14 14">
                    <path id="search"
                          d="M133.144,229l-5.114-5.114a4.287,4.287,0,0,1-1.36.788,5.024,5.024,0,1,1,3.013-3.082,4.936,4.936,0,0,1-.817,1.458L134,228.144ZM125,223.789a3.637,3.637,0,0,0,2.686-1.118,3.832,3.832,0,0,0,0-5.386A3.637,3.637,0,0,0,125,216.167a3.811,3.811,0,1,0,0,7.622Z"
                          transform="translate(-120 -215)" fill="#f2f2f2"/>
                </svg>
                <span>
                    <%= btnValue %>
                </span>
            </button>
        </form>
    </div>
</div>
</div>

<jsp:include page="footer.jsp"/>

<script type="text/javascript">
    const fileInput = document.getElementById('imageUpload');
    const imageElement = document.getElementById('img-display');

    fileInput.addEventListener('change', (event) => {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const reader = new FileReader();

            reader.onload = (event) => {
                const imageSrc = event.target.result;
                imageElement.src = imageSrc;
            };

            reader.readAsDataURL(selectedFile);
        } else {
            console.log('No file selected');
        }
    });
</script>
</body>

</html>