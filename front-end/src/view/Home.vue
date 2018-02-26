<template>
    <section>
      <el-row class="container">
        <el-col :span="24" class="header">
            <el-col :span="10" class="logo" :class="isCollapse?'logo-collapse-width':'logo-width'">
                <div @click="collapse" style="cursor: pointer;"><img :src="this.sysUserAvatar"/>{{isCollapse?'':this.appName}}</div>
            </el-col>
            <el-col :span="10">
            </el-col>
            <el-col :span="4" class="userinfo">
                <el-dropdown trigger="hover">
                    <span class="el-dropdown-link userinfo-inner">
                        {{loginUser.empName}} 
                        <el-badge :is-dot="latestMsgList.length!==0"></el-badge>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="openMsgCenter">消息中心<el-badge :value="latestMsgList.length" /></el-dropdown-item>
                        <el-dropdown-item divided @click.native="openChangePwd">修改密码</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                | <el-button type="text" class="userinfo-inner" @click="logout">退出登录</el-button>
            </el-col>
        </el-col>
        <el-col :span="24" class="main">
            <aside :style="{width: isCollapse?'60px':'230px'}">
                <!--导航菜单-->
                <el-menu :default-active="$route.path" class="el-menu-vertical-demo" :collapse="isCollapse" unique-opened router>
                    <el-submenu v-for="(menu,index) in $router.options.routes" :key="index" :index="index+''" v-if="!menu.hidden && allowMe(menu)">
                        <template slot="title">
                            <i :class="menu.iconCls"></i>
                            <span v-text="menu.name"></span>
                        </template>
                        <el-menu-item v-for="(item,index) in menu.children" :key="index" :index="item.path"  v-if="!item.hidden && allowMe(item)">
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
      <!-- 修改密码 -->
      <change-pwd :isShow="changePwdVis" @close="closeChangePwd" />
      <!-- 消息中心 -->
      <message-center :isShow="msgCenterVis" @close="closeMsgCenter" />
    </section>
    
</template>

<script>
import avatar from "@/assets/logo.png";
import ChangePwd from "@/view/_pagelet/ChangePwd.vue";
import MessageCenter from "@/view/_pagelet/MessageCenter.vue";
import { SELECT_MESSAGE_PAGE_LIST } from "@/config/api";
export default {
  components: { ChangePwd, MessageCenter },
  data() {
    return {
      appName: "黄埔区公安分局信息化项目管理系统",
      loginUser: this.$store.state.loginUser,
      myRoles: this.$store.state.myRoles,
      isCollapse: false,
      sysUserAvatar: avatar,
      changePwdVis: false,
      msgCenterVis: false,
      latestMsgList: []
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
          sessionStorage.removeItem("myRoles");
          this.$store.commit("clearStore");
          this.$router.push("/login");
        })
        .catch(() => {});
    },
    openChangePwd() {
      this.changePwdVis = true;
    },
    closeChangePwd() {
      this.changePwdVis = false;
    },
    openMsgCenter() {
      this.msgCenterVis = true;
    },
    closeMsgCenter() {
      this.msgCenterVis = false;
    },
    selectMyMessageList() {
      var _this = this;
      let params = {
        pageNo: 1,
        pageSize: 100000, //一个大的整数，其实就是说不分页
        filter: { to: _this.$store.state.loginUser.id, isExec: 0 }
      };
      SELECT_MESSAGE_PAGE_LIST(params).then(res => {
        if (res === "") {
          _this.$message({
            message: "获取消息列表失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.latestMsgList = res.rows;
        }
      });
    },
    allowMe(menu) {
      var allowed = false;
      //任意一个角色不在黑名单就可以显示
      for (var i = 0; i < this.myRoles.length; i++) {
        var myRole = this.myRoles[i];
        if (menu.blackList.indexOf(myRole.group_role) == -1) {
          allowed = true;
          break;
        }
      }
      return allowed;
    }
  },
  mounted() {
    this.selectMyMessageList();
  }
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
      font-size: 20px;
      padding-left: 2px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      // border-right-style: solid;
      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 10px;
      }
      .txt {
        color: #fff;
      }
    }
    .logo-width {
      width: 640px;
    }
    .logo-collapse-width {
      width: 65px;
      border-right-style: solid;
    }
    .tools {
      padding: 0px 23px;
      // width: 14px;
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