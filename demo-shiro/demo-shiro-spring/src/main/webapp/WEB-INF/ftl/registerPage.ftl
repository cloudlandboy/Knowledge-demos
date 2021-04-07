<#assign ctx=springMacroRequestContext.contextPath/>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>shiro案例-注册</title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui@2.15.0/lib/theme-chalk/index.css">
    <script src="https://unpkg.com/vue@2.6.12/dist/vue.js"></script>
    <script src="https://unpkg.com/element-ui@2.15.0/lib/index.js"></script>
    <style>
        .login-box {
            width: 350px;
            position: absolute;
            top: 0px;
            left: 0;
            bottom: 0;
            right: 0;
            margin: 180px auto;
            height: max-content;
            padding: 10px 60px 10px 10px;
            border: 1px solid #DCDFE6;
            background: rgba(255, 255, 255, .9);
            box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
        }

        .project {
            padding: 10px 0 20px;
            text-align: center;
            font-size: 24px;
            color: #409EFF;
        }

        .login-btn {
            padding-left: 40px;
        }

        body {
            background-image: url("${ctx}/static/loginBg.jpg");
            background-size: 100%;
        }

        .failure-info {
            margin: 8px;
        }
    </style>
</head>
<body>
<div>

</div>
<div id="app" class="login-box">
    <div class="project">
        <i class="el-icon-s-flag"></i>
        <span>${ctx[1..]} - 注册</span>
    </div>
    <#if failureInfo??>
        <#list failureInfo as info>
            <el-alert class="failure-info" title="${info}" type="error"></el-alert>
        </#list>
    </#if>
    <el-form :model="registerForm" ref="registerForm" id="registerForm" label-width="100px" :rules="rules" action=""
             method="post">
        <el-form-item label="用户名" prop="username">
            <el-input type="text" name="username" v-model="registerForm.username" required="true"
                      autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" name="password" v-model="registerForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePassword">
            <el-input type="password" name="rePassword" v-model="registerForm.rePassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <div class="login-btn">
                <el-button type="primary" @click="handLogin('registerForm')">注册</el-button>
                <el-button @click="to('${ctx}/login')">登录</el-button>
            </div>
        </el-form-item>
    </el-form>
</div>
<script>
    new Vue({
        el: '#app',
        data: {
            registerForm: {
                username: '',
                password: '',
                rePassword: '',
            },
            rules: {
                username: {required: true, message: "用户名不能为空", trigger: 'blur'},
                password: {required: true, message: "密码不能为空", trigger: 'blur'},
                rePassword: {required: true, message: "请确认密码！", trigger: 'blur'}
            }
        },
        methods: {
            handLogin(form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        document.querySelector("#registerForm").submit();
                    }
                });
            },
            to(url) {
                location.href = url;
            }
        }
    });
</script>
</body>
</html>