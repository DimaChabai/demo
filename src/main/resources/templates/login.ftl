<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<@l.login "/login"/>
<div class="container unauthenticated">
    With Facebook: <a href="/login/facebook">click here</a>
</div>
<div class="container unauthenticated">
With Google: <a href="/login/google">click here</a>
</div>
<a href="/registration">Регистрация</a>
</@c.page>