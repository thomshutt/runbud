<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <div>
        <ol>
            <#list runs as run>
                <li><a href="/runs/${runs.id?html}">${run.name?html}</a></li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
