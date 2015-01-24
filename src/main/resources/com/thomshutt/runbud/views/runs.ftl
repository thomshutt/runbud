<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>
    <div>
        <ol>
            <#list runs as run>
                <li><a href="/runs/${run.runId?html}">${run.name?html}</a></li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
