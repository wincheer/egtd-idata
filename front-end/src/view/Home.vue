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
                        <img :src="this.sysUserAvatar" /> {{loginUser.empName}} 
                        <el-badge :is-dot="latestMsgList.length!==0"></el-badge>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="dlgMsgListVis = true">消息中心<el-badge :value="latestMsgList.length" /></el-dropdown-item>
                        <el-dropdown-item divided @click.native="pwdFormVisible = true">修改密码</el-dropdown-item>
                        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
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
      <!--消息中心-->
      <el-dialog title="消息中心" :visible.sync="dlgMsgListVis" width="30%">
        <el-tabs v-model="avtiveMsgTab" type="card">
          <el-tab-pane label="新消息" name="latest">
            <el-table :data="latestMsgList" stripe :show-header="false" highlight-current-row @current-change="rowChange">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <span>{{ props.row.body }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="来自" prop="from"></el-table-column> -->
              <el-table-column label="主题" prop="title"></el-table-column>
              <el-table-column label="时间" prop="time" :formatter="fmtDate"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button size="mini" type="primary" @click="viewDetail(scope.row)" v-if="scope.row.type!=='normal'">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <!-- <el-tab-pane label="历史消息" name="history">TODO</el-tab-pane> -->
        </el-tabs>
        <div slot="footer">
          <!-- <el-button @click="sendMsg" size="mini" type="primary" icon="el-icon-edit">发布消息</el-button> -->
          <el-button @click.native="dlgMsgListVis = false" size="mini" >关闭</el-button>
        </div>
      </el-dialog>
      <!--项目审批-->
      <el-dialog :title="dlgTitle" :visible.sync="dlgAuditProjecyVis" width="30%">
        <el-tabs v-model="avtiveProjectTab">
          <el-tab-pane label="项目基本信息" name="info">
            <el-form label-position="left" style="margin-left:10px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="项目名称：">
                    <span>{{project.projectName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="项目金额：">
                    <span>{{project.amount}} 万元</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <!-- <el-row>
                <el-col :span="12">
                  <el-form-item label="项目经理">
                  <span>{{project.actorStaffId}}</span>
                </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="所属部门">
                  <span>{{project.depId}} </span>
                </el-form-item>
                </el-col>
              </el-row> -->
              <el-row>
                <el-col :span="12">
                  <el-form-item label="启动日期：">
                  <span>{{project.startDate|fmtDateFilter}}</span>
                </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="结束日期：">
                    <span>{{project.endDate|fmtDateFilter}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
          </el-form>
          </el-tab-pane>
          <el-tab-pane label="项目阶段划分" name="stage">
            <el-table :data="projectStageList" highlight-current-row >
              <el-table-column label="阶段" prop="text"></el-table-column>
              <el-table-column label="开始日期" prop="start_date" :formatter="fmtDate"></el-table-column>
              <el-table-column label="结束日期" prop="end_date" :formatter="fmtDate"></el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
        <div slot="footer">
          <el-button @click="auditProject(3)" size="mini" type="success" icon="el-icon-check">同意</el-button>
          <el-button @click="auditProject(2)" size="mini" type="primary" icon="el-icon-close">拒绝</el-button>
          <el-button @click.native="dlgAuditProjecyVis = false" size="mini" >关闭</el-button>
        </div>
      </el-dialog>
      <!--任务确认-->
      <!-- <el-dialog :title="dlgTitle" :visible.sync="dlgConfirmTaskVis" width="30%">
        <el-tabs v-model="avtiveProjectTab">
          <el-tab-pane label="项目基本信息" name="info">
          </el-tab-pane>
          <el-tab-pane label="项目组" name="group">TODO</el-tab-pane>
          <el-tab-pane label="项目阶段划分" name="stage">
            
          </el-tab-pane>
        </el-tabs>
        <div slot="footer">
          <el-button @click="auditProject(3)" size="mini" type="success" icon="el-icon-check">同意</el-button>
          <el-button @click="auditProject(2)" size="mini" type="primary" icon="el-icon-close">拒绝</el-button>
          <el-button @click.native="dlgConfirmTaskVis = false" size="mini" >关闭</el-button>
        </div>
      </el-dialog> -->
    </section>
    
</template>

<script>
import avatar from "@/assets/logo.png";
import {
  UPDATE_EMPLOYEE,
  SELECT_MESSAGE_LIST,
  UPDATE_MESSAGE,
  SELECT_PROJECT_STAGE_LIST,
  SELECT_PROJECT
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  data() {
    return {
      appName: "项目管理软件",
      loginUser: this.$store.state.loginUser,
      myRoles: this.$store.state.myRoles,
      isCollapse: false,
      sysUserAvatar: avatar,
      pwdFormVisible: false,
      dlgMsgListVis: false,
      dlgAuditProjecyVis: false,
      dlgConfirmTaskVis: false,
      pwdObj: {
        oldPwd: "",
        newPwd: "",
        newSecPwd: ""
      },
      pwdObjRules: {
        oldPwd: [{ required: true, message: "请输入原密码", trigger: "blur" }],
        newPwd: [{ required: true, message: "请输入新密码", trigger: "blur" }]
      },
      selectedMsg: {},
      avtiveMsgTab: "latest",
      avtiveProjectTab: "info",
      dlgTitle: "",
      latestMsgList: [],
      historyMsgList: [],
      projectStageList: [],
      project: {}
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
    updatePwd: function() {
      var _this = this;
      _this.$refs.pwdForm.validate(valid => {
        if (valid) {
          if (_this.pwdObj.oldPwd != _this.$store.state.loginUser.password) {
            _this.$message({
              message: "原密码错误，重新输入！",
              type: "error"
            });
          } else {
            if (_this.pwdObj.newPwd != _this.pwdObj.newSecPwd) {
              _this.$message({
                message: "新密码两次输入的不一致，重新输入！",
                type: "warning"
              });
            } else {
              let para = {
                id: _this.$store.state.loginUser.id,
                password: _this.pwdObj.newPwd
              };
              UPDATE_EMPLOYEE(para).then(res => {
                _this.$message({
                  message: "更新密码成功，下次请用新密码登录",
                  type: "success"
                });
                _this.pwdObj = { oldPwd: "", newPwd: "", newSecPwd: "" };
                _this.pwdFormVisible = false;
              });
            }
          }
        } else {
          return false;
        }
      });
    },
    allowMe(menu) {
      var allowed = false;
      //任意一个角色不在黑名单就可以显示
      for (var i = 0; i < this.myRoles.length; i++) {
        var myRole = this.myRoles[i];
        if (menu.blackList.indexOf(myRole) == -1) {
          allowed = true;
          break;
        }
      }
      return allowed;
    },
    fmtDate(row, column, cellValue) {
      if (cellValue == null) return "";
      else return formatDate(new Date(cellValue), "yyyy-MM-dd hh:mm");
    },
    sendMsg() {
      this.$message("我要发布新消息了");
    },
    selectMyMessageList() {
      var _this = this;
      SELECT_MESSAGE_LIST({
        to: this.$store.state.loginUser.id,
        isExec: 0,
      }).then(res => {
        if (res === "") {
          _this.$message({
            message: "获取消息列表失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.latestMsgList = res;
        }
      });
    },
    selectProjectStageList(projectId) {
      var _this = this;
      SELECT_PROJECT_STAGE_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目阶段失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectStageList = res;
        }
      });
    },
    selectProject(projectId) {
      var _this = this;
      SELECT_PROJECT({ id: projectId }).then(res => {
        _this.project = res;
      });
    },
    rowChange(row) {
      //更新消息isRead状态
      this.selectedMsg = row;
      var _this = this;
      var params = { id: row.id, isRead: 1 };
      if (row.isRead !== 1) {
        UPDATE_MESSAGE(params).then(res => {
          if (res === "") {
            _this.$message({
              message: "更新消息状态失败，请联系系统管理员。",
              type: "error"
            });
          } else {
            row.isRead = 1;
          }
        });
      }
    },
    auditProject(exec) {
      var _this = this;
      var params = Object.assign(_this.selectedMsg);
      params.isExec = exec;
      UPDATE_MESSAGE(params).then(res => {
        if (res === "") {
          _this.$message({
            message: "更新消息状态失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.dlgAuditProjecyVis = false;
          _this.selectMyMessageList();
        }
      });
    },
    confirmTask() {},
    viewDetail(row) {
      if (row.type === "audit") {
        this.dlgTitle = "审批项目计划";
        this.selectProjectStageList(row.relationId);
        this.selectProject(row.relationId);
        this.dlgAuditProjecyVis = true;
      } else if (row.type === "confirm") {
        this.dlgTitle = "确认任务完成";
        this.dlgConfirmTaskVis = true;
      }
    }
  },
  computed: {},
  filters: {
    fmtDateFilter: function(value) {
      if (value == null) return "";
      else return formatDate(new Date(value), "yyyy-MM-dd");
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