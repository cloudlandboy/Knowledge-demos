<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.7/css/layui.css">
    <style>
        .head-img {
            width: 120px;
            height: 120px;
            line-height: 120px;
            text-align: center;
            background-color: #009688;
            cursor: pointer;
            color: #fff;
            border-radius: 50%;
            margin: auto;
            background-image: url("http://api.btstu.cn/sjtx/api.php");
            background-size: 100%;
        }

        .layui-anim-rotate {
            animation-duration: 5s;
        }

        .main-info {
            margin: 50px auto;
            width: 200px;
        }

        .main-info > li {
            line-height: 40px;
        }
    </style>
</head>
<body style="background-color: #e2e2e2">
<div class="layui-container">
    <ul class="main-info">
        <li>
            <div class="head-img layui-anim layui-anim-rotate layui-anim-loop"></div>
        </li>
        <li>id：${user.id}</li>
        <li>username：${user.username}</li>
        <li>password：${user.password}</li>
        <li><a href="${pageContext.request.contextPath}/user" class="layui-btn">返回首页</a></li>
    </ul>
</div>
</body>
</html>
