<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>

    <div class="info-box">
        <p>Near to</p>
        <form action="/runs" method="post">
            <input type="text" name="address" value="${address}" style="display: inline-block; margin-right: 15px; width: 510px;" />
            <input type="submit" value="Search" style="margin: 0px; display: inline-block; " />
        </form>
    </div>

    <div style="width: 960px; text-align: left">

        <#if 0 < newRuns?size>
            <h2>Later Today</h2>
            <ol>
                <#list newRuns as run>
                    <li>
                        <div class="circular" style="vertical-align: top; background: url(${run.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
                        <div style="display: inline-block; padding-left: 20px; padding-top: 3px;">
                            <a href="/runs/${run.runId?html}" style="max-width: 800px;">${run.distanceKm?html}km - ${run.runName?html}</a>
                            <p style="margin-top: 5px; max-width: 800px;">${run.startTimeHours?string["00"]}:${run.startTimeMins?string["00"]} @ ${run.startAddress} (${run.distanceFromUserStartPoint} miles away)</p>
                            <p style="font-style: italic; margin-top: 5px; max-width: 800px;">${run.description}</p>
                        </div>
                    </li>
                </#list>
            </ol>
        </#if>

        <#if 0 < oldRuns?size>
            <h2>Already Happened</h2>
            <ol>
                <#list oldRuns as run>
                    <li>
                        <div class="circular" style="vertical-align: top; background: url(${run.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
                        <div style="display: inline-block; padding-left: 20px; padding-top: 3px;">
                            <a href="/runs/${run.runId?html}" style="max-width: 800px;">${run.distanceKm?html}km - ${run.runName?html}</a>
                            <p style="margin-top: 5px; max-width: 800px;">${run.startTimeHours?string["00"]}:${run.startTimeMins?string["00"]} @ ${run.startAddress}</p>
                            <p style="font-style: italic; margin-top: 5px; max-width: 800px;">${run.description}</p>
                        </div>
                    </li>
                </#list>
            </ol>
        </#if>

    </div>
<#include "footer.ftl">
