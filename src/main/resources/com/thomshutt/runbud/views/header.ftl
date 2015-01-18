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
            input {
                display: block;
            }
            #header {
                border-bottom: 1px solid #E5E5E5;
                padding-left: 20px;
                padding-right: 20px;
                height: 50px;
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
        </style>
    </head>
    <body>
        <div id="header">
            <div class="row nav-bar">
                <div id="logo">RUNBUD</div>
                <ul class="global-nav text-right">
                    <li><a href="/">Home</a></li>
                    <li><a href="/runs">Runs</a></li>
                    <li><a href="/runs/1">Run 1</a></li>
                    <li><a href="/runs/create/new">Auto Create</a></li>
                    <li><a href="/users/login">Auto Login</a></li>
                    <li><a href="/users/logout">Auto Logout</a></li>
                    <li><a href="/runs/create">Create</a></li>
                    <li><a href="/users/create">Sign Up</a></li>
                </ul>
            </div>
        </div>
