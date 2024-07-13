<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Rarity" %>
<%@ page import="utils.NameChecker" %>

<% String sectionTitle = "Rarity Insertion", btnValue = "Insert";
    String mode = request.getParameter("mode");
    Rarity updatedRarity = null;
    String rarityId = "", rarityName = "", imgPath = "rarity/default.jpeg";

    if (mode != null && mode.equals("u")) {
        sectionTitle = "Rarity Update";
        btnValue = "Update";
        updatedRarity = (Rarity) request.getAttribute("updated-rarity");
        rarityId = String.valueOf(updatedRarity.getRarityId());
        rarityName = updatedRarity.getName();

        if (NameChecker.isNewImgPath(updatedRarity.getImgPath(), "rarity")) {
            imgPath = updatedRarity.getImgPath();
        }
    } %>

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
        <form action="${pageContext.request.contextPath}/rarity-cu" method="post" enctype="multipart/form-data" class="filter-form filter-form--borderless">
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
                    <label for="rarity-id" class="form__input-label">Rarity ID:</label>
                    <input type="text" name="rarity-id" value="<%= rarityId %>"
                           id="rarity-id" class="form__input-field" placeholder="Rarity ID ...">
                </div>
            </div>

            <div class="form__group horizontal large form__input-field form__image-container">
                <img src="uploads/<%= imgPath %>" alt="Rarity image" id="img-display">

                <div class="form__control">
                    <label for="imageUpload" class="control__label">
                        <span class="fa-solid fa-camera"></span>
                    </label>
                    <input type="file" name="rarity-img" id="imageUpload" accept=".jpg,.jpeg,.png"
                           class="form__input-field control__input-field" hidden>
                </div>
            </div>

            <div class="form__group horizontal large">
                <div class="form__control">
                    <label for="rarity-name" class="form__input-label">Rarity Name:</label>
                    <input type="text" name="rarity-name" value="<%= rarityName %>"
                           id="rarity-name" class="form__input-field"
                           placeholder="Rarity Name ...">
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