<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunsView" -->
<#include "header.ftl">
    <h1>Runs</h1>
    <div style="width: 960px; text-align: left">
        <ol>
            <#list runs as run>
                <li>
                    <!-- img style="vertical-align: top; border: 1px solid #3D4F5D;" src="http://lorempixel.com/80/80/?random=${run.runId?html}" -->
                    <div class="circular" style="vertical-align: top; background: url(${run.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
                    <div style="display: inline-block; padding: 20px;">
                        <a href="/runs/${run.runId?html}">${run.runName?html}</a>
                        <p>${run.description}</p>
                    </div>
                </li>
            </#list>
        </ol>
    </div>
<#include "footer.ftl">
