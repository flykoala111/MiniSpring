<!DOCTYPE HTML>
<html>
<head>
    <title>Controller</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script src="/javaee_minispring/utils/js/jquery.min.js"></script>
    <script src="/javaee_minispring/utils/js/config.js"></script>
    <script src="/javaee_minispring/utils/js/skel.min.js"></script>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/skel-noscript.css"/>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/style.css"/>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/style-desktop.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie9.css"/><![endif]-->
    <link rel="icon" href="/javaee_minispring/utils/images/minispring.icon">
    <!--[if lte IE 8]>
    <script src="/javaee_minispring/utils/js/html5shiv.js"></script>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie8.css"/>
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie7.css"/><![endif]-->
</head>
<body>
<table width="80%" align="center">
    <tr>
        <td align="left">
            <div id="con"></div>
        </td>
    </tr>
</table>

</body>
<script>
    $.ajax({
        url:"getfunpagedata?page=controller",
        type:"post",
        success:function (text) {
            $("#con").html(text);
        }
    });
</script>
</html>