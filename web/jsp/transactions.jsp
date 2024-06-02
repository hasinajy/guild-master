<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.TransactionFull" %>

<%
    ArrayList<TransactionFull> transactionList = (ArrayList<TransactionFull>) request.getAttribute("transaction_list");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transactions</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/layout.css">
    <link rel="stylesheet" href="assets/css/transactions.css">
    <link rel="stylesheet" href="assets/css/filter.css">
</head>

<body>
<div class="body-bg light">
    <img src="assets/img/background.PNG" alt="">
</div>

<jsp:include page="header.jsp"/>

<div class="body wrapper">
    <h1 class="page__title">Transaction Record</h1>

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

    <a href="TransactionCU" class="add-btn btn">
            <svg class="add-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
                <path id="add" d="M207.143,312v-7.143H200v-1.714h7.143V296h1.714v7.143H216v1.714h-7.143V312Z"
                      transform="translate(-200 -296)" fill="#f2f2f2"/>
            </svg>

            <span>Add Transaction</span>
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
            <div>
                <div class="form__control">
                    <label for="type" class="form__input-label">Transaction Type:</label>
                    <select name="transaction_type" id="type" class="form__input-field">
                        <option value="0">Deposit</option>
                        <option value="1">Withdraw</option>
                    </select>
                </div>

                <div class="form__group horizontal">
                    <div class="form__control">
                        <label for="start-date" class="form__input-label">Start Date:</label>
                        <input type="date" name="start_date" id="start-date" class="form__input-field">
                    </div>

                    <div class="form__control">
                        <label for="end-date" class="form__input-label">End Date:</label>
                        <input type="date" name="end_date" id="end-date" class="form__input-field">
                    </div>
                </div>

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

    <div class="content">
        <table class="transaction">
            <tr>
                <th class="transaction__type">Transaction Type</th>
                <th class="transaction__date">Date</th>
                <th class="transaction__item">Item Involved</th>
                <th class="transaction__owner">Owner</th>
                <th class="transaction__staff">Responsible Staff</th>
            </tr>
            <%
                for (TransactionFull transaction : transactionList) {
            %>
            <tr>
                <td class="transaction__type"><% out.println(transaction.getTransactionType()); %></td>
                <td class="transaction__date"><% out.println(transaction.getDate()); %></td>
                <td class="transaction__item"><% out.println(transaction.getItemName()); %></td>
                <td class="transaction__owner"><% out.println(transaction.getPlayerCharacterName()); %></td>
                <td class="transaction__staff"><% out.println(transaction.getStaffCharacterName()); %></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>

</html>