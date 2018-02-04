<template>
  <section>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 20px;">
      <el-tab-pane label="项目概况" name="third">
        <el-select v-model="selectedProject" placeholder="请选择项目" @change="onProjectChange" value-key="id">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item" />
        </el-select>
        <simple-chart :taskParams="taskParams"></simple-chart>
      </el-tab-pane>
      <el-tab-pane label="我承接的任务" name="first">
        <el-table :data="myTaskListIn" stripe>
        <el-table-column prop="text" label="任务"></el-table-column>
        <el-table-column prop="start_date" label="开始时间" sortable :formatter="fmtDate"></el-table-column>
        <el-table-column prop="end_date" label="结束时间" sortable :formatter="fmtDate"></el-table-column>
        <el-table-column prop="priority" label="优先级" sortable :formatter="fmtPriority"></el-table-column>
        <el-table-column prop="progress" label="进度" sortable>
          <template slot-scope="scope">
            <el-progress :status="scope.row.state==3?'success':''" :text-inside="true" :stroke-width="18" :percentage="scope.row.progress * 100" />
          </template>
        </el-table-column>
        <el-table-column prop="projectId" label="所属项目" sortable :formatter="fmtProject"></el-table-column>
        <el-table-column prop="assignStaffId" label="任务发布人" sortable :formatter="fmtEmployee"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="viewTask(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-tab-pane>
      <el-tab-pane label="我分配的任务" name="second">
        <el-table :data="myTaskListOut" stripe>
        <el-table-column prop="text" label="任务"></el-table-column>
        <el-table-column prop="start_date" label="开始时间" sortable :formatter="fmtDate"></el-table-column>
        <el-table-column prop="end_date" label="结束时间" sortable :formatter="fmtDate"></el-table-column>
        <el-table-column prop="priority" label="优先级" sortable :formatter="fmtPriority"></el-table-column>
        <el-table-column prop="progress" label="进度" sortable>
          <template slot-scope="scope">
            <el-progress :text-inside="true" :stroke-width="18" :percentage="scope.row.progress * 100" />
          </template>
        </el-table-column>
        <el-table-column prop="projectId" label="所属项目" sortable :formatter="fmtProject"></el-table-column>
        <el-table-column prop="actorStaffId" label="任务承接者" sortable :formatter="fmtEmployee"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
             <el-button type="primary" size="mini" @click="viewTask(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-tab-pane>
    </el-tabs>
    <!--任务编辑对话框-->
    <task-edit-form :task="selectedTask" :isShow="taskFormVis" @close="closeTaskForm"></task-edit-form>
  </section>
</template>

<script>
import TaskEditForm from "@/component/TaskEditForm.vue";
import SimpleChart from "@/component/SimpleChart.vue";
import {
  SELECT_MY_TASK_LIST_IN,
  SELECT_MY_TASK_LIST_OUT,
  SELECT_EMPLOYEE_LIST,
  SELECT_PROJECT_LIST,
  UPDATE_PROJECT_TASK,
  SELECT_MY_PROJECT_LIST
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  components: { TaskEditForm,SimpleChart },
  data() {
    return {
      selectedTask: [],
      taskFormVis: false,
      taskParams:{id:0},
      selectedProject:{},

      activeName: "third",
      employeeList: [],
      myProjectList: [],
      myTaskList: [],
      myTaskListIn: [],
      myTaskListOut: [],
      actionInfo:"申请确认"
    };
  },
  methods: {
    closeTaskForm(){
      this.taskFormVis = false;
      //this.selectMyTaskList();
    },
    onProjectChange(project) {
      if (project) {
       this.taskParams = {projectId:project.id}
      }
    },
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
      for (var i = 0; i < this.myProjectList.length; i++) {
        if (this.myProjectList[i].id == cellValue) {
          projectName = this.myProjectList[i].projectName;
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
          _this.myProjectList = res;
          if (res.length) {
            _this.selectedProject = _this.myProjectList[0];
            _this.taskParams = {projectId:_this.selectedProject.id}
          }
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
    viewTask(task){
      this.selectedTask = task;
      this.taskFormVis = true;
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
