<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="../css/votes.css">
    <title>Who's winning?</title>
</head>

<style>
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
        color:orange;
        border-radius: 5px;
    }
    .back:hover {
        transition-duration: 0.3s;
        background-color: orange;
        color:black;
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

    .candidate-card {
        background-color: white;
        border: 1px solid #ccc;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        padding: 10px;
        text-align: center;
    }

    .candidates-container {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }

    .candidate-card {
        width: 30%;
        box-sizing: border-box;
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
    <h1>Vote Quantity</h1>
    <main class="flex flex-wrap align-evenly">
        <div class="candidates-container">
            <#list candidatesAndVotes as candidate, votes>
                <form action="/vote?candidate=${candidate.id}" method="GET" class="candidate-card">
                    <div class="text-center mb-3">
                        <img src="/img/${candidate.photoUrl}" alt="Candidate" class="rounded">
                        <p>${candidate.name} has votes ${votes}% of all votes</p>
                    </div>
                    <p>${candidate.name}</p>
                </form>
            </#list>
        </div>
    </main>

    <p>-------------------------------------------------------</p>
    <a class="back flex align-center" href="/">Back to main page</a>
</div>
</body>
</html>