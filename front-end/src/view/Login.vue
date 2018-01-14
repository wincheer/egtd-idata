<template>
    <el-form :model="loginForm" :rules="loginFormRules" ref="loginForm" class="login-container">
        <h3 class="title" v-text="'系统登录'"></h3>
        <el-form-item prop="staffMobile">
            <el-input type="text" placeholder="手机号" v-model="loginForm.staffMobile"></el-input>
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
        staffMobile: "",
        password: ""
      },
      loginFormRules: {
        staffMobile: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
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
            staffMobile: _this.loginForm.staffMobile,
            password: _this.loginForm.password,
          };
          LOGIN({model:loginParams,actor:0}).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({
                message: "登录失败，请检查用户名和密码是否正确。",
                type: "error"
              });
            } else {
              _this.$store.commit("setLoginUser", data);
              sessionStorage.setItem("loginUser", JSON.stringify(data));
              _this.$router.push({ path: "/todo" });
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
