<header class="header">
    <a href="${pageContext.request.contextPath}/dashboard" class="dropdown__link">
        <h1 class="header__logo">Guild Master</h1>
    </a>

    <nav class="header__nav nav">
        <ul>
            <li class="nav__item"><a href="${pageContext.request.contextPath}/dashboard" class="nav__link">Dashboard</a>
            </li>
            <li class="nav__item"><a href="${pageContext.request.contextPath}/transactions" class="nav__link">Transactions</a>
            </li>
            <li class="nav__item dropdown">
                    <span class="nav__link dropdown__label">
                        Storage
                        <svg class="dropdown-icon" xmlns="http://www.w3.org/2000/svg" width="8" height="4.23"
                             viewBox="0 0 8 4.23">
                            <path id="Path_4" data-name="Path 4"
                                  d="M3.4.2A1.007,1.007,0,0,1,4.6.2L7.59,2.413a1.007,1.007,0,0,1-.6,1.816H1.009a1.007,1.007,0,0,1-.6-1.816Z"
                                  transform="translate(8 4.23) rotate(180)" fill="#aaa"/>
                        </svg>
                    </span>

                <ul class="dropdown__items-container">
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/inventories"
                                                  class="dropdown__link">Inventories</a></li>
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/items"
                                                  class="dropdown__link">Items</a></li>
                </ul>
            </li>
            <li class="nav__item dropdown">
                    <span class="nav__link dropdown__label">
                        Adventurers
                        <svg class="dropdown-icon" xmlns="http://www.w3.org/2000/svg" width="8" height="4.23"
                             viewBox="0 0 8 4.23">
                            <path id="Path_4" data-name="Path 4"
                                  d="M3.4.2A1.007,1.007,0,0,1,4.6.2L7.59,2.413a1.007,1.007,0,0,1-.6,1.816H1.009a1.007,1.007,0,0,1-.6-1.816Z"
                                  transform="translate(8 4.23) rotate(180)" fill="#aaa"/>
                        </svg>
                    </span>

                <ul class="dropdown__items-container">
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/players"
                                                  class="dropdown__link">Players</a></li>
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/factions"
                                                  class="dropdown__link">Factions</a></li>
                </ul>
            </li>
            <li class="nav__item dropdown">
                    <span class="nav__link dropdown__label">
                        Properties
                        <svg class="dropdown-icon" xmlns="http://www.w3.org/2000/svg" width="8" height="4.23"
                             viewBox="0 0 8 4.23">
                            <path id="Path_4" data-name="Path 4"
                                  d="M3.4.2A1.007,1.007,0,0,1,4.6.2L7.59,2.413a1.007,1.007,0,0,1-.6,1.816H1.009a1.007,1.007,0,0,1-.6-1.816Z"
                                  transform="translate(8 4.23) rotate(180)" fill="#aaa"/>
                        </svg>
                    </span>

                <ul class="dropdown__items-container">
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/rarities"
                                                  class="dropdown__link">Rarities</a></li>
                    <li class="dropdown__item"><a href="${pageContext.request.contextPath}/types"
                                                  class="dropdown__link">Types</a></li>
                </ul>
            </li>
        </ul>

        <%
            String username = (String) session.getAttribute("username");

            if (username != null && !username.trim().isEmpty()) {
        %>
        <span id="auth-btn"><a href="${pageContext.request.contextPath}/logout">Log Out</a></span>
        <%
        } else {
        %>
        <span id="auth-btn"><a href="${pageContext.request.contextPath}/auth">Log In</a></span>
        <%
            }
        %>
    </nav>
</header>
