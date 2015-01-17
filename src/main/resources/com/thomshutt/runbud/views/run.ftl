<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunView" -->
<#include "header.ftl">
    <h1>Hello, ${run.name?html}!</h1>
    <h2>Created By: ${initiatingUser.name}</h2>
    <h2>Start Location: ${run.startLocation}</h2>
    <h2>Distance: ${run.distanceKm}km</h2>
    <h2>Description: ${run.description}</h2>

    <br /><br />

    <h2>Comments:</h2>

    <#list comments as comment>
        <p>
            ${comment.userId}: ${comment.comment}
        </p>
    </#list>

    <h2>Post Comment:</h2>

    <form action="/runs/${run.runId}/comment" method="post">
          <input type="text" name="comment" value="Some comment here" />
          <input type="submit" />
    </form>
<#include "footer.ftl">