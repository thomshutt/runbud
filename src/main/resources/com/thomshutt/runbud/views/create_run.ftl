<#include "header.ftl">
    <form action="/runs/create" method="post">
        <input type="number" name="distance_km" value="1" />
        <input type="text" name="initiating_user_id" value="1" />
        <input type="text" name="start_location" value="London Fields" />
        <input type="text" name="description" value="Some description..." />
        <input type="submit" />
    </form>
<#include "footer.ftl">