<#include "header.ftl">

    <h1>Settings</h1>

    <h2>Current Photo: </h2>

    <div class="circular" style="margin-left: 10px; vertical-align: top; background: url(${user.imageUrl?html}) no-repeat; background-position: center; display: inline-block;"></div>

    <form action="/users/settings" method="post" enctype="multipart/form-data">
        <input type="file" name="image" />
        <input type="hidden" id="fileName" name="fileName"/>
        <input type="submit" value="Upload New Image"/>
    </form>

<#include "footer.ftl">