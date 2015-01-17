<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunView" -->
<#include "header.ftl">
    <h2>Created By: ${initiatingUser.name?html}</h2>
    <h2>Start Location: ${run.startLocation?html}</h2>
    <h2>Distance: ${run.distanceKm?html}km</h2>
    <h2>Description: ${run.description?html}</h2>

    <br /><br />

    <h2>Comments:</h2>

    <#list comments as comment>
        <p>
            ${comment.userId?html}: ${comment.comment?html}
        </p>
    </#list>

    <h2>Post Comment:</h2>

    <form action="/runs/${run.runId}/comment" method="post">
          <input type="text" name="comment" value="Some comment here" />
          <input type="submit" />
    </form>
<#include "footer.ftl">