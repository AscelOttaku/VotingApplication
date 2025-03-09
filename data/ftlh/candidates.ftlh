<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="../css/candidates.css">

  <title>Vote for us!</title>
</head>

<body>
<div class="container flex flex-col align-center">
  <h1>Vote for us</h1>
  <main class="flex flex-wrap align-evenly">
    <div class="candidates-container">
      <#list candidates as candidate>
        <form action="/vote?candidate=${candidate.id}" method="GET" class="candidate-card">
          <input type="hidden" name="candidate" value="${candidate.id}">
            <div class="text-center mb-3">
              <img src="${candidate.photoUrl}" alt="Candidate" class="rounded">
            </div>
          <p>${candidate.name}</p>
          <button type="submit" class="vote">Vote</button>
        </form>
      </#list>
    </div>
  </main>

  <p>-------------------------------------------------------</p>
  <a class="back flex align-center" href="/votes">Show total votes</a>

  <p>-------------------------------------------------------</p>
  <a href="/exist" class="back flex align-center p-b" role="button" aria-pressed="true">Exist</a>
</div>
</body>
</html>
