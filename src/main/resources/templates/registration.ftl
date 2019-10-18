<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
>
<head>
    <title>Spring Security Example </title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
Регистрация
{{#message}}
    {{message}}
{{/message}}

<form action="/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    {{#captchaError}}
        {{captchaError}}
    {{/captchaError}}
    <div class="g-recaptcha" data-sitekey="6LemVLYUAAAAAIOOkD4Zriu6lE7N2jDBMFAtLpg_"></div>
    <input type="hidden" name="_csrf" value="{{_csrf.token}}" />
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>