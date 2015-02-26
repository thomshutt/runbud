<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>
    <div style="width: 960px; text-align: left">
        <ol>
            <#list runs as run>
                <li>
                    <div class="circular" style="vertical-align: top; background: url(${run.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
                    <div style="display: inline-block; padding-left: 20px; padding-top: 3px;">
                        <a href="/runs/${run.runId?html}" style="max-width: 800px;">${run.distanceKm?html}km - ${run.runName?html}</a>
                        <p style="margin-top: 5px; max-width: 800px;">${run.startTimeHours?html}:${run.startTimeMins?html} @ ${run.startAddress}</p>
                        <p style="font-style: italic; margin-top: 5px; max-width: 800px;">${run.description}</p>
                    </div>
                </li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
