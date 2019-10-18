<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

</head>
<body>
<a href="/main">Назад</a>
<form action="/book" method="get">
    <input type="text" name="filter">
    <input type="submit" value="Filter">
</form>

<#list books as bk>
<div>
    <form action="/book/delFromList">
${bk.num} ${bk.name}
    <input type="text" name="bookId" value="${bk.num}" hidden="hidden" >
    <a  href="/img/${bk.filename} " download>Книга</a>
    <button type="submit" >Delete from list</button>
    </form>
</div>
    <#else>
    rtyuio
</#list>

</body>
</html>