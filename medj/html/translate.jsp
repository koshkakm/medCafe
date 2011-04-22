<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload CCR</title>
</head>

<body>
    <h1>Upload CCR</h1>
    <hr/>
 
   
</body>
</html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>medJ</title>
        <link rel="stylesheet" href="css/styles.css" type="text/css"/>
    </head>
    <body>
        <div id="container">
            <div id="container-inner">
                <a name="top"></a>
                <div id="header">
                    <a href="/"><img src="images/medCafe_logo.png" height="75" width="175" style="float:right;margin-right:50px;margin-top:8px;"></a>
                    <h1>
                        <a href="#">medJ</a>
                    </h1>
                    <p>
                        Bringing CCR to the web client
                    </p>
                    <div class="clear">
                    </div>
                </div>
                <!-- end header -->
                <div id="nav">
                    <ul>
                        <li>
                            <a href="index.html">Home</a>
                        </li>
                        <li>
                            <a href="http://www.ccrstandard.com/" target="_blank">CCR Home</a>
                        </li>
                    </ul>
                </div>
                <!-- end nav -->
                <div id="main">
                    <div id="content">
                        <h2>
                            Welcome to the website
                        </h2>
                        Welcome CCR founders! This site exists to show the possibilities to converting CCR XML to JSON.
                        <br/>
                        <h3>
                            Convert your CCR to JSON format
                        </h3>
                         <fieldset>
					       <legend>Upload File</legend>
					        <form action="convert" method="post" enctype="multipart/form-data">
					           <label for="filename_1">File: </label>
					           <input id="filename_1" type="file" name="filename_1" size="50"/><br/>
					            <br/>
					            <input type="submit" value="Upload CCR"/>
					        </form>
					    </fieldset>
                    </div>
                    <!-- end content -->
                    <div class="clear">
                    </div>
                </div>
                <!-- end main -->
            </div>
            <!-- end container-inner -->
        </div>
        <!-- end container -->
        <div id="footer">
            <p class="copyright">
                &copy; The MITRE Corporation 2011 - All Rights Reserved.
            </p>
        </div>
        <!-- end footer -->
    </body>
</html>
