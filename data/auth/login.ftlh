<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="../css/forms.css">
</head>

<style>
    body {
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
        margin: 0;
        padding: 0;
    }

    body {
        background: url("../bg/bg.png") 0 0 repeat;
    }

    form {
        width: 50%;
        max-width: 500px;
        min-width: 200px;
        margin: 0 auto;
    }

    fieldset {
        border: 0;
        padding: 0;
        margin: 20px 0 0;
    }

    .legend {
        margin: 0 0 10px;
        font-size: 24px;
        text-align: center;
        display: flex;
        align-items: center;
        flex-direction: column;
        width: 100%;
    }

    .form-element {
        margin-top: 20px;
    }

    fieldset input {
        display: block;
        box-sizing: border-box;
        width: 100%;
        padding: 10px;
    }

    fieldset label {
        display: none;
    }

    .hr-line {
        border-bottom: 1px solid #ddd;
        color: #999;
        font-size: 12px;
        line-height: 0;
        margin: 20px 0;
        text-align: center;
    }

    .details {
        background-color: #fff;
        padding: 0 15px;
    }

    .register-button {
        font-family: inherit;
        font-size: 19px;
        display: block;
        width: 100%;
        padding: 15px 0;
        background-color: #2da562;
        border: 0;
        border-radius: 5px;
        text-transform: uppercase;
        font-weight: bold;
        color: white;
        cursor: pointer;
    }

    .color {
        text-decoration: none;
        color: white;
    }

    .space {
        margin-top: 26px !important;
    }

</style>

<body>
    <main>
        <form action="/login" method="POST">
            <fieldset>
                <div class="legend">
                    <p>Welcome!</p>
                    <img src="../images/1.jpg" alt="coins" style="display:block; width: 200px; height: 200px;">
                </div>
                <div class="form-element">
                    <label for="user-email">email</label>
                    <input type="email" name="email" id="user-email" placeholder="your email" required autofocus>
                </div>
                <div class="form-element">
                    <label for="user-password">password</label>
                    <input type="password" name="password" id="user-password" placeholder="your password" required>
                </div>
                <div class="hr-line">
                    <span class="details">one more step to go</span>
                </div>
                <div class="form-element">
                    <button class="register-button" type="submit">Login!</button>
                </div>
            </fieldset>
            <div class="space">
                <button type="submit" class="register-button">
                    <a class="color" href="/register">Register</a>
                </button>
            </div>
        </form>
    </main>
</body>
</html>