<#include "header.ftl">
    <h1>Log In</h1>
    <#if previousLoginFailed>
        <p style="margin-bottom: 10px;">Email or password incorrect.</p>
    </#if>
    <form action="/users/login" method="post">
        <input type="text" name="email" value="thomscott@thomshutt.com" />
        <input type="password" name="password" value="password" />
        <input type="submit" value="Log In" />
    </form>
<#include "footer.ftl">