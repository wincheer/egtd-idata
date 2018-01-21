<template>
    <section>
      <el-row class="container">
        <el-col :span="24" class="header">
            <el-col :span="10" class="logo" :class="isCollapse?'logo-collapse-width':'logo-width'">
                {{isCollapse?'':this.appName}}
            </el-col>
            <el-col :span="10">
                <div class="tools" @click="collapse">
                    <i class="el-icon-menu"></i>
                </div>
            </el-col>
            <el-col :span="4" class="userinfo">
                <el-dropdown trigger="hover">
                    <span class="el-dropdown-link userinfo-inner">
                        <img :src="this.sysUserAvatar" /> {{this.$store.state.loginUser.empName}}
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="pwdFormVisible = true">修改密码</el-dropdown-item>
                        <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-col>
        </el-col>
        <el-col :span="24" class="main">
            <aside :style="{width: isCollapse?'60px':'230px'}">
                <!--导航菜单-->
                <el-menu :default-active="$route.path" class="el-menu-vertical-demo" :collapse="isCollapse" unique-opened router>
                    <el-submenu v-for="(menu,index) in $router.options.routes" :key="index" :index="index+''"  v-if="!menu.hidden">
                        <template slot="title">
                            <i :class="menu.iconCls"></i>
                            <span v-text="menu.name"></span>
                        </template>
                        <el-menu-item v-for="(item,index) in menu.children" :key="index" :index="item.path">
                            <i :class="item.iconCls"></i>
                            <span v-text="item.name"></span>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </aside>
            <section class="content-container">
                <div class="grid-content bg-purple-light">
                    <el-col :span="24" class="breadcrumb-container">
                        <strong class="title">{{$route.name}}</strong>
                        <el-breadcrumb separator="/" class="breadcrumb-inner">
                            <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
                                {{ item.name }}
                            </el-breadcrumb-item>
                        </el-breadcrumb>
                    </el-col>
                    <el-col :span="24" class="content-wrapper">
                        <transition name="fade" mode="out-in">
                            <router-view></router-view>
                        </transition>
                    </el-col>
                </div>
            </section>
        </el-col>
      </el-row>

      <el-dialog title="修改密码" :close-on-click-modal="false" :visible.sync="pwdFormVisible" width="20%">
        <el-form :model="pwdObj" :rules="pwdObjRules" label-width="100px" ref="pwdForm">
          <el-form-item label="原密码：" prop="oldPwd">
            <el-input type="password" v-model="pwdObj.oldPwd" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="新密码：" prop="newPwd">
            <el-input v-model="pwdObj.newPwd" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="重输新密码：" prop="newSecPwd">
            <el-input v-model="pwdObj.newSecPwd" style="width:90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="pwdFormVisible = false">取消</el-button>
          <el-button type="primary" @click="updatePwd">提交</el-button>
        </div>
      </el-dialog>
    </section>
    
</template>

<script>
import avatar from "@/assets/logo.png";
import { UPDATE_EMPLOYEE } from "../config/api";
export default {
  data() {
    return {
      appName: "eGTD - iData",
      loginUser: {},
      isCollapse: false,
      sysUserAvatar: avatar,

      pwdFormVisible: false,
      pwdObj: {
        oldPwd: "",
        newPwd: "",
        newSecPwd: ""
      },
      pwdObjRules: {
        oldPwd: [{ required: true, message: "请输入原密码", trigger: "blur" }],
        newPwd: [{ required: true, message: "请输入新密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    //折叠导航栏
    collapse: function() {
      this.isCollapse = !this.isCollapse;
    },
    //退出登录
    logout: function() {
      var _this = this;
      this.$confirm("确认退出吗?", "提示", {})
        .then(() => {
          sessionStorage.removeItem("loginUser");
          this.$router.push("/login");
        })
        .catch(() => {});
    },
    updatePwd: function() {
      var _this = this;
      _this.$refs.pwdForm.validate(valid => {
        if (valid) {
          if (_this.pwdObj.oldPwd != _this.$store.state.loginUser.password) {
            _this.$message({message: "原密码错误，重新输入！",type: "error"});
          } else {
            if (_this.pwdObj.newPwd != _this.pwdObj.newSecPwd) {
              _this.$message({message: "新密码两次输入的不一致，重新输入！",type: "warning"});
            } else {
              let para = {
                id: _this.$store.state.loginUser.id,
                password: _this.pwdObj.newPwd
              };
              UPDATE_EMPLOYEE(para).then(res => {
                _this.$message({message: "更新密码成功，下次请用新密码登录",type: "success"});
                // _this.loginUser.password = _this.pwdObj.newPwd;
                // // _this.$store.commit("setLoginUser", _this.loginUser);
                // sessionStorage.setItem("loginUser", JSON.stringify(_this.loginUser));
                _this.pwdObj = {oldPwd: "",newPwd: "",newSecPwd: ""};
                _this.pwdFormVisible = false;
              });
            }
          }
        } else {
          return false;
        }
      });
    }
  },
  mounted() {}
};
</script>

<style scoped lang="scss">
.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  height: 100%;
  width: 100%;
  .header {
    height: 60px;
    line-height: 60px;
    background: #20a0ff;
    color: #fff;
    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;
      .userinfo-inner {
        cursor: pointer;
        color: #fff;
        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }
    .logo {
      //width:230px;
      height: 60px;
      font-size: 22px;
      padding-left: 20px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      border-right-style: solid;
      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 18px;
      }
      .txt {
        color: #fff;
      }
    }
    .logo-width {
      width: 230px;
    }
    .logo-collapse-width {
      width: 65px;
    }
    .tools {
      padding: 0px 23px;
      width: 14px;
      height: 60px;
      line-height: 60px;
      cursor: pointer;
    }
  }
  .main {
    display: flex;
    position: absolute;
    top: 60px;
    bottom: 0px;
    overflow: hidden;
    width: 100%;
    aside {
      .el-menu {
        height: 100%;
      }
      .collapsed {
        width: 60px;
        .item {
          position: relative;
        }
        .submenu {
          position: absolute;
          top: 0px;
          left: 60px;
          z-index: 99999;
          height: auto;
          display: none;
        }
      }
    }
    .content-container {
      flex: 1;
      overflow-y: scroll;
      padding: 20px;
      .breadcrumb-container {
        .title {
          width: 200px;
          float: left;
          color: #475669;
        }
        .breadcrumb-inner {
          float: right;
        }
      }
      .content-wrapper {
        width: 100%;
        height: 100%;
        background-color: #fff;
        box-sizing: border-box;
      }
    }
  }
}
</style>