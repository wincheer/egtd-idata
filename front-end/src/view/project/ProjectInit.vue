<template>
  <section>
    <el-steps style="margin-top: 20px;">
      <el-step title="新建项目" :status="projectStatus"></el-step>
      <el-step title="维护项目合同" :status="projectContractStatus" description="维护合同的同时选择供应商"></el-step>
      <el-step title="配置项目组" :status="projectGroupStatus" description="当前项目的组织结构以及项目成员"></el-step>
      <el-step title="分解项目阶段" :status="projectStageStatus" description="分解项目阶段及任务，指派责任人以及配置检查链"></el-step>
      <el-step title="项目就绪" :status="projectReadyStatus"></el-step>
    </el-steps>

    <el-button style="margin-top: 12px;" @click="openAddProject" type="primary" ref="btnCreateProject">新建项目</el-button>
    <el-dropdown  trigger="click" @command="createProjectFromTpl">
      <el-button type="success">从模板新建项目<i class="el-icon-arrow-down el-icon--right"></i></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="item in tplList" :key="item.id" :command="item.id">{{item.tplName}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-table border stripe :data="projectList" style="margin-top: 20px;" highlight-current-row @current-change="onProjectChange" ref="projectTable">
      <el-table-column type="index" width="30"></el-table-column>
      <el-table-column prop="projectName" label="项目名称">
        <template slot-scope="scope">
        <el-button type="text" :disabled="scope.row.ownerId !== $store.state.loginUser.id" @click="openEditProject(scope.row)">{{ scope.row.projectName }}</el-button>
      </template>
      </el-table-column>
      <el-table-column prop="createDate" label="立项时间" :formatter="fmtDate"></el-table-column>
      <el-table-column prop="depId" label="所属部门"  :formatter="fmtDep"></el-table-column>
      <el-table-column prop="actorStaffId" label="项目经理" :formatter="fmtEmp"></el-table-column>
      <el-table-column prop="startDate" label="启动时间" :formatter="fmtDate"></el-table-column>
      <el-table-column prop="endDate" label="结束时间" :formatter="fmtDate"></el-table-column>
      <el-table-column label="操作" width="260">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <el-button size="mini" type="primary" :disabled="scope.row.ownerId !== $store.state.loginUser.id">编辑<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <!-- <el-dropdown-item @click.native="openEditProject(scope.row)">项目</el-dropdown-item> -->
              <el-dropdown-item @click.native="openProjectContractList(scope.row)">合同</el-dropdown-item>
              <el-dropdown-item @click.native="openProjectGroupList(scope.row)">项目组</el-dropdown-item>
              <el-dropdown-item @click.native="openProjectStageList(scope.row)">项目阶段</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button size="mini" type="danger" @click="delProject(scope.row)" :disabled="scope.row.ownerId !== $store.state.loginUser.id">删除</el-button>
          <!-- <el-button size="mini" type="warning" @click="applyAudit(scope.row)" v-if="projectReadyStatus === 'success' && (scope.row.ownerId === $store.state.loginUser.id) && scope.row.auditState==='init'">申请审批</el-button> -->
          <el-dropdown trigger="click" @command="applyAudit" v-if="projectReadyStatus === 'success' && (scope.row.ownerId === $store.state.loginUser.id) && scope.row.auditState==='init'">
            <el-button size="mini" type="warning" >申请审批<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="item in projectLeaders" :key="item.id" :command="item.id">{{item.empName}}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <!-- 项目编辑框 -->
    <project-edit-form :project="projectObj" :isShow="dlgProjectEditVis" @close="dlgProjectEditVis=false" @updateSuccess="selectProjectList"/>
    <!--项目合同-->
    <project-contract :projectId="selectedProject.id" :isShow="dlgProjectContractListVis" @close="dlgProjectContractListVis=false"/>
    <!--项目组-->
    <project-group :projectId="selectedProject.id" :isShow="dlgProjectGroupListVis"  @close="dlgProjectGroupListVis=false"/>
    <!--项目阶段-->
    <project-stage :projectId="selectedProject.id" :isShow="dlgProjectStageListVis"  @close="dlgProjectStageListVis=false"/>
  </section>
</template>

<script>
import {
  SELECT_ALL_DEP_EMP_LIST,
  SELECT_PROJECT_LIST,
  DELETE_PROJECT,
  SELECT_OWNER_PROJECT_EMPLOYEE_LIST,
  SELECT_DEP_TREE_LIST,
  UPDATE_MESSAGE,
  SELECT_TPL_PROJECT_LIST,
  CREATE_PROJECT_FROM_TPL,
  SELECT_PROJECT_LEADER_LIST,
  SELECT_PROJECT_CONTRACT_LIST,
  SELECT_PROJECT_GROUP_TREE_LIST,
  SELECT_PROJECT_STAGE_LIST
} from "@/config/api";
import ProjectEditForm from "@/view/project/_pagelet/ProjectEditForm.vue";
import ProjectContract from "@/view/project/_pagelet/ProjectContract.vue";
import ProjectGroup from "@/view/project/_pagelet/ProjectGroup.vue";
import ProjectStage from "@/view/project/_pagelet/ProjectStage.vue";
import { formatDate } from "@/util/date.js";
import { getNodePath, getNode } from "@/util/treeUtil.js";
export default {
  components: { ProjectEditForm, ProjectContract, ProjectGroup, ProjectStage },
  data() {
    return {
      allDepEmpList: [],
      depTreeList: [],
      projectObj: {},
      selectedProject: {},
      projectList: [],
      projectContractList: [],
      projectGroupTreeList: [],
      projectStaffList: [],
      groupStaffList: [],
      projectStageList: [],
      dlgProjectEditVis: false,
      dlgProjectContractEditVis: false,
      dlgProjectContractListVis: false,
      dlgProjectGroupListVis: false,
      dlgProjectGroupEditVis: false,
      dlgProjectStaffEditVis: false,
      dlgProjectStageListVis: false,
      dlgProjectStageEditVis: false,
      tplList: [],
      projectLeaders: []
    };
  },
  methods: {
    selectDepTreeList() {
      var _this = this;
      SELECT_DEP_TREE_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取组织结构失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.depTreeList = res;
        }
      });
    },
    selectAllDepEmpList() {
      var _this = this;
      SELECT_ALL_DEP_EMP_LIST({}).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取业主员工列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.allDepEmpList = res;
        }
      });
    },
    selectTplProjectList() {
      var _this = this;
      SELECT_TPL_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目模板失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.tplList = res;
        }
      });
    },
    selectProjectList() {
      var _this = this;
      SELECT_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectList = res;
        }
      });
    },
    selectProjectLeaderList(projectId) {
      var _this = this;
      SELECT_PROJECT_LEADER_LIST({ id: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目领导，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectLeaders = res;
        }
      });
    },
    selectProjectContractList(projectId) {
      var _this = this;
      SELECT_PROJECT_CONTRACT_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目合同失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectContractList = res;
        }
      });
    },
    selectProjectGroupTreeList(projectId) {
      var _this = this;
      SELECT_PROJECT_GROUP_TREE_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目组失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectGroupTreeList = res;
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
    selectOwnerProjectEmpList(projectId) {
      var _this = this;
      SELECT_OWNER_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取甲方项目组成员失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.ownerProjectEmpList = res;
        }
      });
    },
    openAddProject() {
      this.projectObj = {};
      this.dlgProjectEditVis = true;
    },
    openEditProject(row) {
      this.projectObj = Object.assign(row);
      this.dlgProjectEditVis = true;
    },
    openProjectContractList(row) {
      this.dlgProjectContractListVis = true;
    },
    openProjectGroupList(row) {
      this.dlgProjectGroupListVis = true;
    },
    openProjectStageList(row) {
      this.dlgProjectStageListVis = true;
    },
    delProject(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectList();
          });
        });
    },
    onProjectChange(row) {
      if (row) {
        this.selectedProject = row;
        //查询相关表的list，用以判断当前项目的完整程度......
        this.selectProjectContractList(row.id);
        this.selectProjectGroupTreeList(row.id);
        this.selectProjectStageList(row.id);
        //填充项目相关的供应商
        //this.selectEmployeeGroup(row.id);
        this.selectProjectLeaderList(row.id);
      } else this.selectedProject = {};

      this.$refs.projectTable.setCurrentRow(row);
    },

    fmtDate(row, column, cellValue) {
      if (cellValue == null) return "";
      else return formatDate(new Date(cellValue), "yyyy-MM-dd");
    },
    fmtEmp(row, column, cellValue) {
      for (var i = 0; i < this.allDepEmpList.length; i++) {
        if (this.allDepEmpList[i].id == cellValue)
          return this.allDepEmpList[i].empName;
      }
      return cellValue;
    },
    fmtDep(row, column, cellValue) {
      var node = getNode(this.depTreeList, cellValue);
      if (node) return node.label;
    },
    authCheck(conmponentName) {
      const authList = [{ name: "btnCreateProject", whiteList: ["R00"] }];

      var conmponent = this.$refs[conmponentName];
      var whiteList = [];
      for (var auth of authList) {
        if (auth.name === conmponentName) {
          whiteList = auth.whiteList;
          break;
        }
      }
      var denied = true;
      var myRoles = this.$store.state.myRoles;
      //任意一个角色在白名单就不拒绝
      for (var i = 0; i < myRoles.length; i++) {
        var myRole = myRoles[i];
        if (whiteList.indexOf(myRole.group_role) != -1) {
          denied = false;
          break;
        }
      }
      //return denied;
      conmponent.disabled = denied;
    },
    applyAudit(leaderId) {
      var _this = this;
      var params = {
        from: this.$store.state.loginUser.id,
        to: leaderId, //后台确定
        toScope: "actor",
        title: "项目计划审批",
        body:
          this.$store.state.loginUser.empName +
          "已完成项目 【" +
          this.selectedProject.projectName +
          "】的项目计划，请您进行审批",
        type: "audit",
        relationId: this.selectedProject.id
      };
      UPDATE_MESSAGE(params).then(data => {
        if (data === "") {
          _this.$message({
            message: "申请审批失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.$notify({
            title: "发送成功",
            message: "您的申请已发出，请等待审批结果。",
            type: "success"
          });
          _this.selectProjectList();
        }
      });
    },
    createProjectFromTpl(command) {
      var _this = this;
      this.$prompt("请输入项目名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
        var params = {
          tplId: command,
          projectName: value,
          empId: _this.$store.state.loginUser.id
        };
        CREATE_PROJECT_FROM_TPL(params).then(res => {
          _this.$notify({
            title: "创建项目成功",
            message: "项目根据模板创建成功，请自行维护项目属性和组成员",
            type: "success"
          });
          _this.selectProjectList();
        });
      });
    }
  },
  computed: {
    projectStatus() {
      if (this.selectedProject.id) return "finish";
      else return "wait";
    },
    projectContractStatus() {
      if (this.selectedProject.id && this.projectContractList.length != 0)
        return "finish";
      else return "wait";
    },
    projectGroupStatus() {
      if (this.selectedProject.id && this.projectGroupTreeList.length != 0)
        return "finish";
      else return "wait";
    },
    projectStageStatus() {
      if (this.selectedProject.id && this.projectStageList.length != 0)
        return "finish";
      else return "wait";
    },
    projectReadyStatus() {
      if (
        this.selectedProject.id &&
        this.projectContractList.length != 0 &&
        this.projectGroupTreeList.length != 0 &&
        this.projectStageList.length != 0
      )
        return "success";
      else return "wait";
    }
  },
  mounted() {
    this.selectProjectList();
    this.selectAllDepEmpList(); //业主的所有员工
    this.selectDepTreeList(); //业主的所有部门
    this.selectTplProjectList(); // 项目模板
  }
};
</script>

<style>

</style>
