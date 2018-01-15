<template>
  <section>
    <el-steps :active="active" finish-status="success" style="margin-top: 20px;">
      <el-step title="新建项目"></el-step>
      <el-step title="维护项目合同" description="维护合同的同时选择供应商"></el-step>
      <el-step title="配置项目组" description="当前项目的组织结构以及项目成员"></el-step>
      <el-step title="分解项目阶段" description="分解项目阶段及任务，指派责任人以及配置检查链"></el-step>
      <!-- <el-step title="指派任务" description="将分阶段任务阶段指派给责任人"></el-step> -->
      <el-step title="项目就绪"></el-step>
    </el-steps>
    <el-button style="margin-top: 12px;" @click="next" type="primary">{{step}}</el-button>
    <el-table border stripe :data="projectList" style="margin-top: 20px;" highlight-current-row>
      <el-table-column type="index" width="30"></el-table-column>
      <el-table-column prop="projectName" label="项目名称"></el-table-column>
      <el-table-column prop="createDate" label="立项时间"></el-table-column>
      <el-table-column prop="depId" label="所属部门"></el-table-column>
      <el-table-column prop="createDate" label="启动时间"></el-table-column>
      <el-table-column prop="createDate" label="结束时间"></el-table-column>
      <el-table-column label="操作" width="230">
        <template slot-scope="scope">
          <el-dropdown  trigger="click">
            <el-button size="mini" type="primary">编辑<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="openEditProject(scope.row)">项目</el-dropdown-item>
              <el-dropdown-item command="b">合同</el-dropdown-item>
              <el-dropdown-item command="c">项目组</el-dropdown-item>
              <el-dropdown-item command="d">项目阶段</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button size="mini" type="warning">冻结</el-button>
          <el-button size="mini" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑框 -->
    <el-dialog :title="projectObj.id==''?'增加项目':'编辑项目'" :visible.sync="dlgProjectEditVis" width="30%" :close-on-click-modal="false">
      <el-form :model="projectObj" :rules="projectObjRules" ref="projectObjForm" label-width="80px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input type="text" v-model="projectObj.projectName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProject">保存</el-button>
          <el-button @click="dlgProjectEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_PROJECT_LIST,
  UPDATE_PROJECT,
  DELETE_PROJECT,
} from "@/config/api";
export default {
  props: {},
  data() {
    return {
      active: 0,
      step: "新建项目",
      projectObj: {
        id:0,
        projectName:'',
        projectDesc:'',
        category:'',
        isKey:0,
        isForzen:0,
        createDate:'',
        startDate:'',
        endDate:'',
        depId:'',
        icon:'',
        amount:''
      },
      projectObjRules: {
        projectName: [{ required: true, message: "请输入项目名称", trigger: "blur" }]
      },
      selectedProject:{},
      projectList: [
        { projectName: "视频监控", depId: "信息中心", createDate: "2017-12-23" },
        { projectName: "CRM", depId: "网监", createDate: "2017-1-13" }
      ],
      dlgProjectEditVis: false
    };
  },
  methods: {
    next() {
      if (this.active == 0) this.openAddProject();
      else if (this.active == 1) this.openAddProjectContract();
      else if (this.active == 2) this.openAddProjectGroup();
      else if (this.active == 3) this.openAddProjectStage();
    },
    selectProjectList(){
      var _this = this;
      SELECT_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取项目失败，请联系系统管理员。", type: "error" });
        else {
          _this.projectList = res;
        }
      });
    },
    openAddProject() {
      this.dlgProjectEditVis = true;
    },
    openAddProjectContract() {},
    openAddProjectGroup() {},
    openAddProjectStage() {},
    openEditProject(row) {
      this.$message(row.projectName);
    },
    updateProject(){
      var _this = this;
      this.$refs.projectForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT(_this.projectObj).then(data => {
            if (data == "") {
              _this.$message({ message: "更新项目失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectProjectList();
              _this.dlgProjectEditVis = false;
            }
          });
        }
      });
    },
    delProject(){}
  },
  mounted() {
    //this.selectProjectList();
  }
};
</script>

<style>

</style>
