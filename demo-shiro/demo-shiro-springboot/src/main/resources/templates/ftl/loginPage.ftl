<#assign ctx=springMacroRequestContext.contextPath/>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>shiro案例-登录</title>
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
            height: 300px;
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
    </style>
</head>
<body>
<div>

</div>
<div id="app" class="login-box">
    <div class="project">
        <i class="el-icon-s-flag"></i>
        <span>${ctx[1..]} - 登录</span>
    </div>
    <el-form :model="loginForm" ref="loginForm" id="loginForm" label-width="100px" :rules="rules" action=""
             method="post">
        <el-form-item label="用户名" prop="username" error="${failureInfo!}">
            <el-input type="text" name="username" v-model="loginForm.username" required="true"
                      autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" error="${failureInfo!}">
            <el-input type="password" name="password" v-model="loginForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="记住我" prop="rememberMe">
            <el-switch name="rememberMe" active-value="on" inactive-value="off" v-model="loginForm.rememberMe">
            </el-switch>
        </el-form-item>
        <el-form-item>
            <div class="login-btn">
                <el-button type="primary" @click="handLogin('loginForm')">登录</el-button>
                <el-button @click="to('${ctx}/register')">注册</el-button>
            </div>
        </el-form-item>
    </el-form>
</div>
<script>
    new Vue({
        el: '#app',
        data: {
            loginForm: {
                username: '${RequestParameters.username!}',
                password: '',
                rememberMe: '${RequestParameters.rememberMe!'off'}'
            },
            rules: {
                username: {required: true, message: "用户名不能为空", trigger: 'blur'},
                password: {required: true, message: "密码不能为空", trigger: 'blur'}
            }
        },
        methods: {
            handLogin(form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        document.querySelector("#loginForm").submit();
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