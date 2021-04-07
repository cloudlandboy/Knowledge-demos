<#assign ctx=springMacroRequestContext.contextPath/>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>角色详情</title>
</head>
<body>
<#include "/common/head.ftl">
<div id="info-app">
    <el-tabs value="roleInfo" tab-position="left" style="height: 200px;">
        <el-tab-pane label="角色信息" name="roleInfo">
            <p>角色：${roleInfo.role}</p>
            <p>描述：${roleInfo.description}</p>
            <p>状态：${roleInfo.available?string("可用","禁用")}</p>
        </el-tab-pane>
        <el-tab-pane label="拥有权限" name="hasPermission">
            <#list roleInfo.permissions as permission>
                <p>${permission}</p>
            </#list>
        </el-tab-pane>
    </el-tabs>
</div>
<script>
    new Vue({el: '#info-app'});
</script>
</body>
</html>