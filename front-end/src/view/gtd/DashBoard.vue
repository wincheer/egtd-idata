<template>
  <section>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 20px;">
      <el-tab-pane label="我承接的任务" name="first">
        <el-table :data="myTaskListIn" stripe>
        <el-table-column prop="text" label="任务"></el-table-column>
        <el-table-column prop="start_date" label="开始时间" :formatter="fmtDate"></el-table-column>
        <el-table-column prop="end_date" label="结束时间" :formatter="fmtDate"></el-table-column>
        <el-table-column prop="priority" label="优先级" :formatter="fmtPriority"></el-table-column>
        <el-table-column prop="progress" label="进度" sortable>
          <template slot-scope="scope">
            <el-progress :status="scope.row.state==4?'success':''" :text-inside="true" :stroke-width="18" :percentage="scope.row.progress * 100" />
          </template>
        </el-table-column>
        <el-table-column prop="projectId" label="所属项目" sortable :formatter="fmtProject"></el-table-column>
        <el-table-column prop="assignStaffId" label="任务发布人" sortable :formatter="fmtEmployee"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button 
              :disabled="scope.row.progress!=1 || scope.row.state==3 ||scope.row.state==4" 
              type="success" 
              size="mini" 
              @click="applyConfirm(scope.row)">
              {{actionInfo}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-tab-pane>
      <el-tab-pane label="我分配的任务" name="second">
        <el-table :data="myTaskListOut" stripe>
        <el-table-column prop="text" label="任务"></el-table-column>
        <el-table-column prop="start_date" label="开始时间" :formatter="fmtDate"></el-table-column>
        <el-table-column prop="end_date" label="结束时间" :formatter="fmtDate"></el-table-column>
        <el-table-column prop="priority" label="优先级" :formatter="fmtPriority"></el-table-column>
        <el-table-column prop="progress" label="进度" sortable>
          <template slot-scope="scope">
            <el-progress :text-inside="true" :stroke-width="18" :percentage="scope.row.progress * 100" />
          </template>
        </el-table-column>
        <el-table-column prop="projectId" label="所属项目" sortable :formatter="fmtProject"></el-table-column>
        <el-table-column prop="actorStaffId" label="任务承接者" sortable :formatter="fmtEmployee"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button 
              :disabled="scope.row.progress!=1 || scope.row.state!=3" 
              type="success" 
              size="mini" 
              @click="confirmTask(scope.row)">
              确认完成
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-tab-pane>
      <!-- <el-tab-pane label="工作日志" name="second">配置管理</el-tab-pane> -->
      <el-tab-pane label="项目概况" name="third">Coming Soon</el-tab-pane>
      <!-- <el-tab-pane label="人员效率" name="fourth">定时任务补偿</el-tab-pane> -->
    </el-tabs>
  </section>
</template>

<script>
import {
  SELECT_MY_TASK_LIST_IN,
  SELECT_MY_TASK_LIST_OUT,
  SELECT_EMPLOYEE_LIST,
  SELECT_PROJECT_LIST,
  UPDATE_PROJECT_TASKE
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  props: {},
  data() {
    return {
      activeName: "first",
      employeeList: [],
      projectList: [],
      myTaskList: [],
      myTaskListIn: [],
      myTaskListOut: [],
      actionInfo:"申请确认"
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    fmtDate(row, column, cellValue) {
      return formatDate(new Date(cellValue), "yyyy-MM-dd");
    },
    fmtProgress(row, column, cellValue) {
      return cellValue * 100 + "%";
    },
    fmtPriority(row, column, cellValue) {
      switch (row.priority) {
        case 100:
          return "最高";
          break;
        case 80:
          return "较高";
          break;
        case 60:
          return "普通";
          break;
        case 40:
          return "较低";
          break;
        case 20:
          return "最低";
          break;
      }
    },
    fmtProject(row, column, cellValue) {
      var projectName = cellValue;
      for (var i = 0; i < this.projectList.length; i++) {
        if (this.projectList[i].id == cellValue) {
          projectName = this.projectList[i].projectName;
          break;
        }
      }
      return projectName;
    },
    fmtEmployee(row, column, cellValue) {
      var empName = cellValue;
      for (var i = 0; i < this.employeeList.length; i++) {
        if (this.employeeList[i].id == cellValue) {
          empName = this.employeeList[i].empName;
          break;
        }
      }
      return empName;
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.assignStaffId == this.$store.state.loginUser.id) {
        return "warning-row";
      } else if (row.actorStaffId == this.$store.state.loginUser.id) {
        return "success-row";
      }
      return "";
    },
    selectStaffList() {
      var _this = this;
      SELECT_EMPLOYEE_LIST().then(res => {
        _this.employeeList = res;
      });
    },
    selectProjectList(empCode) {
      var _this = this;
      SELECT_PROJECT_LIST({ code: empCode }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取我的项目列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectList = res;
        }
      });
    },
    selectMyTaskListIn() {
      SELECT_MY_TASK_LIST_IN({ id: this.$store.state.loginUser.id }).then(
        res => {
          this.myTaskListIn = res;
        }
      );
    },
    selectMyTaskListOut() {
      SELECT_MY_TASK_LIST_OUT({ id: this.$store.state.loginUser.id }).then(
        res => {
          this.myTaskListOut = res;
        }
      );
    },
    applyConfirm(task){
      var _this = this;
      //更新任务状态为待检查(3) 未开始0，进行中1，已完成2，待检查3，确认完成4
      UPDATE_PROJECT_TASKE({id:tast.id,state:3}).then(res =>{
        _this.actionInfo = "等待确认";
      })
      this.$message("申请已发出,审核通过后会自动变更任务状态。");
    },
    confirmTask(task){
      //更新任务状态为待检查(3) 未开始0，进行中1，已完成2，待检查3，确认完成4
      UPDATE_PROJECT_TASKE({id:tast.id,state:4}).then(res =>{
        _this.actionInfo = "完成确认";
      })
      this.$message("任务已确认，状态为正式完成。");
    }
  },
  computed: {},
  mounted() {
    this.selectMyTaskListIn();
    this.selectMyTaskListOut();
    this.selectStaffList();
    this.selectProjectList();
  }
};
</script>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
