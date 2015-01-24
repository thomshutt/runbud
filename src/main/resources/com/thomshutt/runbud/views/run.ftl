<#include "header.ftl">
    <h2>Created By: ${initiatingUser.name?html}</h2>
    <h2>Start Latitude: ${run.startLatitude?html}</h2>
    <h2>Start Longitude: ${run.startLongitude?html}</h2>
    <h2>Start Address: ${run.startAddress?html}</h2>
    <h2>Distance: ${run.distanceKm?html}km</h2>
    <h2>Description: ${run.description?html}</h2>
    <h2>Attendees: ${runAttendees?size + 1}</h2>

    <br /><br />

    <#if loggedIn>
        <#if userIsAttending>
             <form action="/runs/${run.runId}/unattending" method="post">
                   <input type="submit" value="I'm not attending" />
             </form>
         <#else>
             <form action="/runs/${run.runId}/attending" method="post">
                    <input type="submit" value="I'm attending" />
              </form>
         </#if>
    </#if>

    <br /><br />

    <h2>Comments:</h2>

    <#list comments as comment>
        <p>
            ${comment.userId?html}: ${comment.comment?html}
        </p>
    </#list>

    <#if loggedIn>
        <h2>Post Comment:</h2>

        <form action="/runs/${run.runId}/comment" method="post">
              <input type="text" name="comment" value="Some comment here" />
              <input type="submit" />
        </form>
    </#if>
<#include "footer.ftl">