<#include "header.ftl">
    <#if previousLoginFailed>
        <p>Email or password incorrect.</p>
    </#if>
    <form action="/users/login" method="post">
        <input type="text" name="email" value="email" />
        <input type="text" name="password" value="password" />
        <input type="submit" />
    </form>
<#include "footer.ftl">