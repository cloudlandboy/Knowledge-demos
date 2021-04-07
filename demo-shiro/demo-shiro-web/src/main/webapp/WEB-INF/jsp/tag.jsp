<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--导入自定义标签库--%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Shiro JSP标签</title>
    <style>
        li {
            color: red;
        }

        li > b {
            color: gray;
        }

        li > b > span {
            color: green;
        }
    </style>
</head>
<body>
<ul>
    <li>
        <b><span>&lt;shiro:guest&gt;</span> 用户没有身份验证时显示相应信息，即游客访问信息：</b>
        <shiro:guest>
            欢迎游客访问，<a href="${pageContext.request.contextPath}/formfilterlogin">未登录</a>
        </shiro:guest>
    </li>
    <li>
        <b><span>&lt;shiro:user&gt;</span> 用户已经身份验证/记住我登录后显示相应的信息：</b>
        <shiro:user>
            欢迎游客访问，<shiro:principal/> <a href="${pageContext.request.contextPath}/logout">注销</a>
        </shiro:user>
    </li>
    <li>
        <b><span>&lt;shiro:authenticated&gt;</span> 用户已经身份验证通过，即 Subject.login 登录成功，不是记住我登录的。</b>
        <shiro:authenticated>
            用户[<shiro:principal/>]已身份验证通过 <a href="${pageContext.request.contextPath}/logout">注销</a>
        </shiro:authenticated>
    </li>
    <li>
        <b><span>&lt;shiro:notAuthenticated&gt;</span> 用户已经身份验证通过，即没有调用 Subject.login 进行登录，包括记住我自动登录的也属于未进行身份验证。</b>
        <shiro:notAuthenticated>
            未身份验证（包括记住我）
        </shiro:notAuthenticated>
    </li>
    <shiro:authenticated>
        <li>
            <b><span>&lt;shiro:principal&gt;</span> 显示用户身份信息，默认调用 Subject.getPrincipal() 获取，即 Primary Principal。</b>
            <shiro:principal/>
        </li>
        <li>
            <b><span>&lt;shiro:principal type="java.lang.String"&gt;</span> 相当于
                Subject.getPrincipals().oneByType(String.class)。</b>
            <shiro:principal type="java.lang.String"/>
        </li>
        <li>
            <b><span>&lt;shiro:principal property="username"&gt;</span> 相当于
                ((User)Subject.getPrincipals()).getUsername()。</b>
                <%--<shiro:principal property="username"/>--%>
        </li>
        <li>
            <b><span>&lt;shiro:hasRole name="admin"&gt;</span> 如果当前 Subject 有角色将显示 body 体内容。</b>
            <shiro:hasRole name="admin">
                用户[<shiro:principal/>]拥有角色admin<br/>
            </shiro:hasRole>
        </li>
        <li>
            <b><span>&lt;shiro:hasAnyRoles name="admin,user"&gt;</span> 如果当前 Subject 有任意一个角色（或的关系）将显示 body 体内容。</b>
            <shiro:hasAnyRoles name="admin,user">
                用户[<shiro:principal/>]拥有角色admin或user<br/>
            </shiro:hasAnyRoles>
        </li>
        <li>
            <b><span>&lt;shiro:lacksRole name="abc"&gt;</span> 如果当前 Subject 没有abc角色将显示 body 体内容。</b>
            <shiro:lacksRole name="abc">
                用户[<shiro:principal/>]没有角色abc<br/>
            </shiro:lacksRole>
        </li>
        <li>
            <b><span>&lt;shiro:hasPermission name="user:create"&gt;</span> 如果当前 Subject 有权限将显示 body 体内容。</b>
            <shiro:hasPermission name="user:create">
                用户[<shiro:principal/>]拥有权限user:create<br/>
            </shiro:hasPermission>
        </li>
        <li>
            <b><span>&lt;shiro:lacksPermission name="org:create"&gt;</span> 如果当前 Subject 没有org:create权限将显示 body 体内容。</b>
            <shiro:lacksPermission name="org:create">
                用户[<shiro:principal/>]没有权限org:create<br/>
            </shiro:lacksPermission>
        </li>
    </shiro:authenticated>
</ul>
<h2>自定义标签</h2>
<shiro:authenticated>
    <ul>
        <li>
            <b><span>&lt;custom:hasAllRoles name="admin,user"&gt;</span></b>
            <custom:hasAllRoles name="admin,user">
                用户[<shiro:principal/>]拥有角色admin和user<br/>
            </custom:hasAllRoles>
        </li>
        <li>
            <b><span>&lt;custom:hasAllPermissions name="user:create,user:update"&gt;</span></b>
            <custom:hasAllPermissions name="user:create,user:update">
                用户[<shiro:principal/>]拥有权限user:create和user:update<br/>
            </custom:hasAllPermissions>
        </li>
        <li>
            <b><span>&lt;custom:hasAnyPermissions name="user:create,abc:update"&gt;</span></b>
            <custom:hasAnyPermissions name="user:create,abc:update">
                用户[<shiro:principal/>]拥有权限user:create或abc:update<br/>
            </custom:hasAnyPermissions>
        </li>
    </ul>
</shiro:authenticated>
</body>
</html>
