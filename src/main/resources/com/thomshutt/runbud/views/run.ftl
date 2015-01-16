<#-- @ftlvariable name="" type="com.thomshutt.runbud.views.RunView" -->
<#include "header.ftl">
    <h1>Hello, ${run.name?html}!</h1>
    <h2>Created By: ${initiatingUser.name}</h2>
    <h2>Start Location: ${run.startLocation}</h2>
    <h2>Distance: ${run.distanceKm}km</h2>
    <h2>Description: ${run.description}</h2>
<#include "footer.ftl">