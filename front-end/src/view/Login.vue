<template>
    <el-form :model="loginForm" :rules="loginFormRules" ref="loginForm" class="login-container">
        <h3 class="title" v-text="'系统登录'"></h3>
        <el-form-item prop="loginName">
            <el-input type="text" placeholder="账号" v-model="loginForm.loginName"></el-input>
        </el-form-item>
        <el-form-item prop="loginPassword">
            <el-input type="password" placeholder="密码" v-model="loginForm.loginPassword" @keydown.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" style="width:100%;" @click="login" :loading="logining">登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { requestLogin } from "@/config/api";
export default {
  data() {
    return {
      logining: false,
      loginForm: {
        loginName: "",
        loginPassword: ""
      },
      loginFormRules: {
        loginName: [{ required: true, message: "请输入账号", trigger: "blur" }],
        loginPassword: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    login() {
      var _this = this;
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // this.logining = true;
          // var loginParams = {
          //   loginName: this.ruleForm2.loginName,
          //   loginPassword: this.ruleForm2.loginPassword
          // };
          // requestLogin(loginParams).then(data => {
          //   this.logining = false;
          //   if (data == "") {
          //     this.$message({
          //       message: "登录失败，请检查用户名和密码是否正确。",
          //       type: "error"
          //     });
          //   } else {
          //     _this.$store.commit("setLoginUser", data);
          //     sessionStorage.setItem("user", JSON.stringify(data));
          //     _this.$router.push({ path: "/hello" });
          //   }
          // });
          _this.$store.commit("setLoginUser", _this.loginForm);
          sessionStorage.setItem("loginUser", JSON.stringify(_this.loginForm));
          _this.$router.push({ path: "/todo" });
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
