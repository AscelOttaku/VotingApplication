<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/failedExceptionForm.css">
    <title>Error - ${exception.status}</title>
</head>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #d47982;
        color: #721c24;
        text-align: center;
        margin: 0;
        padding: 0;
    }

    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
    }

    .card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        max-width: 500px;
        margin-bottom: 20px;
    }

    h1 {
        font-size: 2em;
        margin-bottom: 10px;
    }

    p {
        margin: 5px 0;
        font-size: 1.2em;
    }

    .back {
        display: inline-block;
        margin-top: 10px;
        padding: 10px 15px;
        background-color: #721c24;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        font-size: 1em;
        transition: background 0.3s;
    }

    .back:hover {
        background-color: #501218;
    }
</style>

<body>
<div class="container flex flex-col align-center">
    <h1>Error ${exception.status}</h1>
    <main class="flex flex-wrap align-center">
        <div class="card">
            <div class="flex flex-col align-center">
                <p><strong>Message:</strong> ${exception.message}</p>
                <p>${exception.cause!}</p>
                <p><strong>Path:</strong> ${exception.path}</p>
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