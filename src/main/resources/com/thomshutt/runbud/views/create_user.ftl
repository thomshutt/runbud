<#include "header.ftl">
    <h1>Sign Up</h1>
    <#if errorMessage??>
        <p style="margin-bottom: 10px;">${errorMessage}</p>
    </#if>
    <form action="/users/create" method="post">
        <input type="text" name="name" value="Thom Scott" />
        <input type="text" name="email" value="thomscott@thomshutt.com" />
        <input type="password" name="password" value="password" />
        <input type="submit" value="Sign Up" />
    </form>
<#include "footer.ftl">