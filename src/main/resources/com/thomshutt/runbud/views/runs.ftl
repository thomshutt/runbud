<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<html>
    <body>
        <ol>
            <#list runs as run>
                <li>${run.name?html}</li>
            </#list>
        </ol>
    </body>
</html>