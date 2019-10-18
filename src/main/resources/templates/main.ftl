<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <@l.logout/>
<a href="/users">Пользователи</a>
<div><a href="/book">Мои книги</a></div>
    <h1>Список книг</h1>

    <#list books as book>
    <div>
        ${book.num} ${book.name}
        <form action="/add/${book.num}">
        <button>Добавить</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
    </div>

</#list>
    <form method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="bookName"/>
        <input type="submit" value="Добавить"/>
    </form>
</@c.page>