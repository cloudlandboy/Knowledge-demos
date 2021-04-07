<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>权限管理</title>
    <style>
        .user-table {
            width: 900px;
            margin: 50px auto 0;
        }
    </style>
</head>
<body>
<#include "/common/head.ftl">
<div id="app">
        <el-main>
            <el-table
                    :data="tableData"
                    class="user-table" border>
                <el-table-column
                        prop="id"
                        label="ID"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="permission"
                        label="权限名"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="description"
                        label="描述"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="available"
                        label="状态"
                        width="180"
                        :formatter="isAvailable">
                </el-table-column>
                <el-table-column
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small">查看</el-button>
                        <el-button type="text" size="small">编辑</el-button>
                        <el-button type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            tableData: ${permissions},
        },
        methods: {
            isAvailable(row, column, available, index) {
                return available ? '正常' : '禁用'
            }
        }
    });
</script>
</body>
</html>