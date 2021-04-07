<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用户</title>
    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.7/css/layui.css">
</head>
<body style="padding: 8px">
<h1>添加用户</h1>
<form class="layui-form" action="${pageContext.request.contextPath}/user" method="post">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="username" required lay-verify="required" placeholder="请输入用户名" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="text" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit">立即添加</button>
        </div>
    </div>
</form>
</body>
</html>
