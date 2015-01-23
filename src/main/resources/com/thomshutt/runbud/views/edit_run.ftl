<#include "header.ftl">
    <form action="/runs/edit" method="post">
        <input type="number" name="distance_km" value="${run.distanceKm}" />
        <input type="text" name="initiating_user_id" value="${run.initiatingUserId}" />
        <input type="text" name="start_location" value="${run.startLocation}" />
        <input type="text" name="description" value="${run.description}" />
        <input type="hidden" name="runId" value="${run.runId}" />
        <input type="submit" />
    </form>
<#include "footer.ftl">