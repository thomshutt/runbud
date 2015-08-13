<#include "header.ftl">
    <h1>Log In</h1>
    <#if previousLoginFailed>
        <p style="margin-bottom: 10px;">Email or password incorrect.</p>
    </#if>
    <form action="/users/login" method="post">
        <input type="text" name="email" placeholder="email@example.com" />
        <input type="password" name="password" placeholder="password" />
        <input type="submit" value="Log In" />
    </form>
<#include "footer.ftl">
