<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>
    <div style="width: 960px; text-align: left">
        <ol>
            <#list runs as run>
                <li>
                    <a href="/runs/${run.runId?html}">${run.runName?html}</a>
                    <p>${run.description}</p>
                </li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
