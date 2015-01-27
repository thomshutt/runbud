<#include "header.ftl">

    <h1>${run.runName?html}</h1>

    <div class="info-box">
        <#if loggedIn>
            <#if userIsAttending>
                <p>
                     You're attending this run!
                </p>
                <form action="/runs/${run.runId}/unattending" method="post">
                    <input type="submit" value="I can't make it anymore" style="margin: 0px;" />
                </form>
            <#else>
                <p>
                     Want to be part of this run?
                </p>
                <form action="/runs/${run.runId}/attending" method="post">
                    <input type="submit" value="I'll be there!" style="margin: 0px;" />
                </form>
             </#if>
        </#if>
    </div>

    <div id="googleMap" style="width: 500px; height: 400px; display: inline-block; margin-right: 20px;"></div>

    <div style="display: inline-block; vertical-align: top; text-align: left; width: 435px; overflow: hidden;">
            <label>Created By</label>
            <p class="label-body">${initiatingUser.name?html}</p>

            <label>Start Address</label>
            <p class="label-body">${run.startAddress?html}</p>

            <label>Distance</label>
            <p class="label-body">${run.distanceKm?html}km</p>

            <label>Number Of Runners</label>
            <p class="label-body">${runAttendees?size + 1}</p>

            <label>Description</label>
            <p class="label-body">${run.description?html}</p>
    </div>

    <br /><br />

    <h1>Comments:</h1>

    <#list comments as comment>
        <p>
            ${comment.userId?html}: ${comment.comment?html}
        </p>
    </#list>

    <#if loggedIn>
        <h2>Post Comment:</h2>

        <form action="/runs/${run.runId}/comment" method="post">
              <input type="text" name="comment" value="Some comment here" />
              <input type="submit" value="Post Comment" />
        </form>
    </#if>

    <script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src='//maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
    <script src="/assets/js/locationpicker.jquery.js"></script>

    <script>
        $('#googleMap').locationpicker({
            draggable: true,
            draggableMarker: false,
            radius: 0,
            location: {
                latitude: ${run.startLatitude},
                longitude: ${run.startLongitude}
            }
        });
    </script>
<#include "footer.ftl">
