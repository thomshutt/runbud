<html>
    <head>
        <title>Runbud</title>
        <style type="text/css">
            body, div, p {
                margin: 0;
		        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
                font-size: 14px;
                line-height: 1.5em;
            }
            ul, ol {
                margin: 0px;
                padding: 0px;
            }
            li {
                list-style: outside none none;
            }
            a {
                color: #007FB6;
                cursor: pointer;
                text-decoration: none;
                transition: border 0.1s ease 0s, box-shadow 0.1s ease 0s, background-color 0.1s ease 0s, opacity 0.1s ease 0s;
            }
            h1 {
                text-align: left;
            }
            label {
                font-size:1.3em;
                font-family:Arial, sans-serif;
                color:#333;
                padding-left: 4px;
            }
            input {
                display: block;
                border:0;
                padding:10px;
                font-size:1.3em;
                font-family:Arial, sans-serif;
                color:#555;
                border:solid 1px #ccc;
                margin:0 0 20px;
                width:300px;
                -webkit-border-radius: 3px;
                -moz-border-radius: 3px;
                border-radius: 3px;
                margin-top: 8px;
            }
            input[type="submit"] {
                cursor: pointer;
                color: #333;
            }
            #header {
                border-bottom: 1px solid #E5E5E5;
                padding-left: 20px;
                padding-right: 20px;
                height: 50px;
                margin-bottom: 20px;
            }
            .row {
                margin: 0px auto;
                max-width: 1148px;
                box-sizing: border-box;
            }
            .text-right {
                text-align: right;
            }
            ul.global-nav > li {
                border: medium none;
                display: inline-block;
                font-weight: 400;
                margin: 0px;
                padding: 0px;
                font-size: 14px;
            }
            ul.global-nav > li > a {
                box-sizing: border-box;
                padding: 19px 1em;
                display: block;
                line-height: 1em;
            }
            ul.global-nav a {
                color: #999;
            }
            ul.global-nav a:hover {
                color: rgb(61, 79, 93);
            }
            #header #logo {
                margin: 9px 0px 0px;
                float: left;
                color: rgb(61, 79, 93);
                font-size: 20px;
            }
            #content {
                width: 960px;
                margin: auto;
                text-align: center;
            }
            #content2 {
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <div id="header">
            <div class="row nav-bar">
                <div id="logo">RUNBUD</div>
                <ul class="global-nav text-right">
                    <li><a href="/">Home</a></li>
                    <li><a href="/runs">Runs</a></li>
                    <#if loggedIn>
                        <li><a href="/runs/create">Create</a></li>
                        <li><a href="/users/logout">Log Out</a></li>
                    <#else>
                        <li><a href="/users/login">Log In</a></li>
                        <li><a href="/users/create">Sign Up</a></li>
                     </#if>
                </ul>
            </div>
        </div>

        <div id="content">
            <div id="content2">
