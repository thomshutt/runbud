<#include "header.ftl">

    <#if hasImageUrl>
        <div class="circular" style="vertical-align: top; background: url(${imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>
    </#if>

    <#if hasHeaderText>
        <h1 style="text-align: center;">${headerText?html}</h1>
    </#if>

    <#if hasInformationText>
        <h2>${informationText?html}</h2>
    </#if>

<#include "footer.ftl">