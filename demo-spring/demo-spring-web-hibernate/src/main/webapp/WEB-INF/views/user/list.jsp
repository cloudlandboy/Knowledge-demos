<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>用户列表</title>
    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.7/css/layui.css">
</head>
<body style="padding: 8px">
<a href="${pageContext.request.contextPath}/user/add" class="layui-btn">添加用户</a>
<table class="layui-table">
    <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr ondblclick="info('${user.id}')">
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>
                <a href="${pageContext.request.contextPath}/user/edit/${user.id}" class="layui-btn">编辑</a>
                <a href="javascript:del('${user.id}')" class="layui-btn layui-btn-danger">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
<script src="https://www.layuicdn.com/layer-v3.1.1/layer.js"></script>
<script>

    function del(id) {
        layer.confirm('确认删除？', {icon: 3, title: '删除提示'}, function (index) {

            fetch("${pageContext.request.contextPath}/user/" + id, {
                method: "delete"
            }).then(res => {
                if (res.ok) {
                    layer.alert('删除成功！', function (index) {
                        layer.close(index);
                        location.reload();
                    });
                } else {
                    layer.msg("删除失败!", {icon: 2})
                }
            })
            layer.close(index);
        });
    }

    function info(id) {
        location.href = "${pageContext.request.contextPath}/user/" + id;
    }
</script>
</html>
