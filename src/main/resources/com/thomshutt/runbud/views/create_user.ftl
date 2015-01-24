<#include "header.ftl">
    <h1>Sign Up</h1>
    <form action="/users/create" method="post">
        <input type="text" name="name" value="Thom Scott" />
        <input type="text" name="email" value="thomscott@thomshutt.com" />
        <input type="password" name="password" value="password" />
        <input type="submit" />
    </form>
<#include "footer.ftl">