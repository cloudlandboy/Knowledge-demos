<#assign ctx=springMacroRequestContext.contextPath/>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>个人信息</title>
</head>
<body>
<#include "/common/head.ftl">
<div id="info-app">
    <el-main>
        <el-tabs value="userInfo" tab-position="left" style="height: 150px">
            <el-tab-pane label="信息" name="userInfo">
                <p>用户名：${userInfo.username}</p>
            </el-tab-pane>
            <el-tab-pane label="角色" name="roles">
                <#list userInfo.roles>
                    <ul>
                        <#items as role>
                            <li>${role}</li>
                        </#items>
                    </ul>
                </#list>
            </el-tab-pane>
        </el-tabs>
    </el-main>
</div>
<script>
    new Vue({el: '#info-app'});
</script>
</body>
</html>