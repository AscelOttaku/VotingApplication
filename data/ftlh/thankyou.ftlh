<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="../css/candidates.css">
    <title>Thank you!!</title>
</head>

<style>
    html,
    body {
        margin: 0;
        padding: 0;
        font-size: 16px;
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
    }

    body {
        background: gainsboro;
    }

    a {
        text-decoration: none;
        color: black;
    }

    img {
        border-radius: 5px;
    }

    .back {
        width: 300px;
        padding: 10px 0px;
        background-color: black;
        color: orange;
        border-radius: 5px;
    }

    .back:hover {
        transition-duration: 0.3s;
        background-color: orange;
        color: black;
    }

    .vote {
        border: 0;
        width: 300px;
        text-transform: uppercase;
        padding: 5px 0;
        background-color: gray;
        color: black;
        font-size: 16px;
        margin: 3px 0 0 0;
        border-radius: 5px;
        cursor: pointer;
    }

    .vote:hover {
        background-color: orange;
    }

    .container {
        width: 1000px;
        margin: 0 auto;
    }

    .flex {
        display: flex;
    }

    .flex-col {
        flex-direction: column;
    }

    .flex-wrap {
        flex-wrap: wrap;
    }

    .align-evenly {
        justify-content: space-around;
    }

    .align-center {
        align-items: center;
        justify-content: center;
    }

    .card {
        flex: 300px 0 0;
        box-sizing: border-box;
        margin: 20px 0 0 0;
        padding: 10px 0;
        border-radius: 5px;
    }

    .card:hover {
        background-color: whitesmoke;
    }

    .winner {
        background-color: crimson;
    }
</style>

<body>
<div class="container flex flex-col align-center">
    <h1>${candidate.name} Thank you for your vote!</h1>
    <main class="flex flex-wrap align-center">
        <div class="card">
            <div class="flex flex-col align-center" href="/">
                <img src="/img/${candidate.photoUrl}">
                <p>Thank you for your vote!</p>
                <p>Now I have ${votingQuantityInProccent}% of all votes</p>
            </div>
        </div>
        <div>
            <p>-------------------------------------------------------</p>
            <a class="back flex align-center" href="/votes">Show total votes</a>

            <p>-------------------------------------------------------</p>
            <a class="back flex align-center" href="/">Back to main page</a>
        </div>
    </main>
</div>
</body>
</html>