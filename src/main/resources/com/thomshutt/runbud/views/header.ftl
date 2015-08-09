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
                padding: 20px;
                border-bottom: 1px solid #E5E5E5;
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
                display: block;
                margin: 0 0 10px 0;
            }
            input, select, textarea {
                border:0;
                padding:10px;
                font-size:1.3em;
                font-family:Arial, sans-serif;
                color:#555;
                border:solid 1px #ccc;
                -webkit-border-radius: 3px;
                -moz-border-radius: 3px;
                border-radius: 3px;
                margin-top: 8px;
                margin:0 0 20px;
            }
            input, textarea {
                display: block;
                width:300px;
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
            #footer {
                width: 100%;
                border-top: 1px solid #E5E5E5;
                background-color: rgb(61, 79, 93);
            }
            #footer li {
                padding-left: 125px;
                color: white;
                text-transform: uppercase;
                border-bottom: none;
                font-size: 11px;
                padding-top: 12px;
            }
            p.label-body {
                margin-bottom: 15px;
                padding-left: 6px;
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
                font-size: 20px;
            }
            #header #logo, h1 {
                color: rgb(61, 79, 93);
            }
            #content {
                width: 960px;
                margin: auto;
                text-align: center;
                min-height: 600px;
            }
            #content2 {
                display: inline-block;
            }
            .rounded-corners {
                -webkit-border-radius: 3px;
                -moz-border-radius: 3px;
                border-radius: 3px;
            }
            .info-box {
                padding: 20px;
                margin-bottom: 20px;
                background-color: #3D4F5D;
                padding: 20px;
                margin-bottom: 20px;
                vertical-align: middle;
                text-align: left;
            }
            .info-box form {
                display: inline-block;
                float: right;
                margin: -5px 0 0 0;
            }
            .info-box p {
                color: white;
                display: inline-block;
                font-size: 24px;
            }
            .circular {
                width: 80px;
                height: 80px;
                border-radius: 40px;
                -webkit-border-radius: 40px;
                -moz-border-radius: 40px;
                background: url(http://link-to-your/image.jpg) no-repeat;
            }
            .circular-big {
                width: 160px;
                height: 160px;
                border-radius: 80px;
                -webkit-border-radius: 80px;
                -moz-border-radius: 80px;
            }
            .blurb-row {
                text-align: center;
            }
            .blurb-row p {
                width: 200px;
                font-size: 16px;
                padding-top: 10px;
            }    
            .home_subsec {
                display: inline-block; 
                padding: 10px;
                width: 274px;
                vertical-align: top;
            }
            .home_subsec h3 {
                    font-weight: unset;
            }
            .home_subsec_img {
                width: 274px; 
                height: 143px; 
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
                        <li><a href="/runs/create">Create A Run</a></li>
                        <li><a href="/users/settings">Settings</a></li>
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
