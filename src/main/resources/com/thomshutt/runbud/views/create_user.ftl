<#include "header.ftl">
    <form action="/users/create" method="post">
        <input type="text" name="name" value="name" />
        <input type="text" name="email" value="email" />
        <input type="text" name="password" value="password" />
        <input type="submit" />
    </form>
<#include "footer.ftl">