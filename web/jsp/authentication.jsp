<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            display: flex;
            flex-direction: column;
            gap: 1rem;

            background-color: #fff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .login-container h2 {
            color: #4c4c4c;
            font-size: 1.125rem;
            text-align: center;
            font-weight: 500;
            text-wrap: balance;
        }

        .login-container h2 span {
            display: block;

            color: #213f52;
            font-size: 2.5rem;
            font-weight: bold;
        }

        .login-container form {
            display: flex;
            flex-direction: column;
        }

        .login-container form input {
            padding: .875rem 1rem;
            margin-bottom: .875rem;
            border: 1px solid #ccc;
            border-radius: 4px;

            font-size: 1.125rem;
        }

        .login-container form input:first-child {
            margin-top: 1rem;
        }

        .login-container form button {
            margin-top: 1rem;
            padding: 10px;
            background-color: #213f52;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1.125rem;
        }

        .login-container form button:hover {
            background-color: hsl(200 60% 25% / 1);
        }
    </style>
</head>

<body>
    <div class="body-bg light">
        <img src="assets/img/background.PNG" alt="">
    </div>

    <div class="login-container">
        <h2>Welcome to <span>Guild Master</span></h2>

        <hr>

        <form action="${pageContext.request.contextPath}/auth-handler" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</body>

</html>