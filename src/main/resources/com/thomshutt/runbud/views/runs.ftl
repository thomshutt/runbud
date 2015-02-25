<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>
    <div style="width: 960px; text-align: left">
        <ol>
            <#list runs as run>
                <li>
                    <div class="circular" style="vertical-align: top; background: url(${run.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
                    <div style="display: inline-block; padding-left: 20px;">
                        <a href="/runs/${run.runId?html}">${run.startTimeHours?html}:${run.startTimeMins?html} - ${run.distanceKm?html}km - ${run.runName?html}</a>
                        <p>${run.startAddress}</p>
                        <br />
                        <p style="font-style: italic;">${run.description}</p>
                    </div>
                </li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
