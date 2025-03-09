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