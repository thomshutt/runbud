<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <div>
        <ol>
            <#list runs as run>
                <li>${run.name?html}</li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
