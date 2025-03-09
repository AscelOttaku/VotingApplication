<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="/data/css/candidates.css">
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
      width: 100%;
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

    .candidates-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      gap: 20px;
    }

    .candidate-card {
      width: 30%;
      box-sizing: border-box;
      padding: 10px;
      border-radius: 8px;
      background-color: white;
      border: 1px solid #ccc;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      text-align: center;
    }

    .candidate-card img {
      width: 100%;
      height: auto;
      border-radius: 50%;
    }

    .candidates-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      gap: 20px;
    }

    .candidate-card {
      width: 30%;
      box-sizing: border-box;
    }

  </style>
  <title>Vote for us!</title>
</head>

<body>
<div class="container flex flex-col align-center">
  <h1>Vote for us</h1>
  <main class="flex flex-wrap align-evenly">
    <div class="candidates-container">
      <#list candidates as candidate>
        <form action="/vote?candidate=${candidate.id}" method="GET" class="candidate-card">
            <div class="text-center mb-3">
              <img src="/img/${candidate.photoUrl}" alt="Candidate" class="rounded">
            </div>
          <p>${candidate.name}</p>
          <input name="candidateId" type="hidden" value="candidate_id">
          <button type="submit" class="vote">Vote</button>
        </form>
      </#list>
    </div>
  </main>

  <p>-------------------------------------------------------</p>
  <a class="back flex align-center" href="/votes">Show total votes</a>
</div>
</body>

</html>
