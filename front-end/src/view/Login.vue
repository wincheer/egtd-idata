<template>
    <el-form :model="loginForm" :rules="loginFormRules" ref="loginForm" class="login-container">
        <h3 class="title" v-text="'黄埔公安分局指挥中心项目管理系统'"></h3>
        <el-form-item prop="empMobile">
            <el-input type="text" placeholder="手机号" v-model="loginForm.empMobile"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" placeholder="密码" v-model="loginForm.password" @keydown.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" style="width:100%;" @click="login" :loading="logining">登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { LOGIN } from "@/config/api";
export default {
  data() {
    return {
      logining: false,
      loginForm: {
        empMobile: "",
        password: ""
      },
      loginFormRules: {
        empMobile: [
          { required: true, message: "请输入电话号码", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    login() {
      var _this = this;
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          var loginParams = {
            empMobile: _this.loginForm.empMobile,
            password: _this.loginForm.password
          };
          LOGIN({ model: loginParams, actor: 0 }).then(data => {
            _this.logining = false;
            if (data.loginUser) {
              //登录用户
              _this.$store.commit("setLoginUser", data.loginUser);
              sessionStorage.setItem(
                "loginUser",
                JSON.stringify(data.loginUser)
              );
              //登录用户的角色
              _this.$store.commit("setMyRoles", data.myRoles);
              sessionStorage.setItem("myRoles", JSON.stringify(data.myRoles));
              console.log(JSON.stringify(data.myRoles));

              _this.$router.push({ path: "/dashboard" });
            } else {
              _this.$message({
                message: "登录失败，请检查用户名和密码是否正确。",
                type: "error"
              });
            }
          });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
}
</style>
