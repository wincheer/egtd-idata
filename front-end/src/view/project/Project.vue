<template>
    <section>
      <el-row style="margin-top: 20px;margin-bottom: 10px;">
        <el-select v-model="selectedProject" placeholder="请选择项目" @change="onProjectChange" value-key="id">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item"></el-option>
        </el-select>
        <el-button icon="el-icon-edit" type="primary" plain @click="openTaskEdit" :disabled="caniOpen || selectedTask.parent==0"> 编辑任务</el-button>
        <el-button icon="el-icon-share" type="primary" plain @click="openTaskAdd" :disabled="caniOpen">分配子任务</el-button>
      </el-row>
      <el-row>
        <gantt :tasks="tasks" @task-selected="onSelectTask"></gantt>
      </el-row>
      <!--任务编辑对话框-->
      <task-edit-form :task="taskObj" :isShow="taskFormVis" @close="closeTaskForm"></task-edit-form>
    </section>
</template>

<script>
import Gantt from "@/view/project/_pagelet/Gantt.vue";
import TaskEditForm from "@/view/_pagelet/TaskEditForm.vue";
import { SELECT_MY_PROJECT_LIST, SELECT_PROJECT_TASK_LIST } from "@/config/api";
import { formatDate } from "@/util/date.js";
import base from "@/config/remote";
export default {
  components: { Gantt, TaskEditForm },
  data() {
    return {
      tasks: {
        data: [],
        links: []
      },
      taskObj: {},
      myProjectList: [],
      selectedProject: {},
      selectedTask: {},
      taskFormVis: false
    };
  },
  methods: {
    closeTaskForm() {
      this.taskFormVis = false;
      this.selectTaskList(this.selectedProject);
    },
    selectMyProjectList(empCode) {
      var _this = this;
      SELECT_MY_PROJECT_LIST({ code: empCode }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取我的项目列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.myProjectList = res;
          if (res.length) {
            _this.selectedProject = _this.myProjectList[0];
            _this.selectTaskList(_this.selectedProject);
          }
        }
      });
    },
    fillTaskData(taskDataList) {
      this.tasks.data = [];
      for (var i = 0; i < taskDataList.length; i++) {
        taskDataList[i].start_date = this.fmtDate(taskDataList[i].start_date);
        taskDataList[i].end_date = this.fmtDate(taskDataList[i].end_date);
      }
      this.tasks.data = taskDataList;
    },
    selectTaskList(project) {
      var _this = this;
      SELECT_PROJECT_TASK_LIST({ id: project.id }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目任务失败，请联系系统管理员。",
            type: "error"
          });
        else {
          this.fillTaskData(res);
        }
      });
    },
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd hh:mm:ss");
    },
    onProjectChange(project) {
      if (project) {
        this.selectTaskList(project);
        this.selectedTask = {};
      }
    },
    onSelectTask(task) {
      this.selectedTask = task;
    },
    openTaskEdit() {
      this.taskObj = Object.assign({}, this.selectedTask);
      this.taskObj.start_date = Date.parse(this.taskObj.start_date);
      this.taskObj.end_date = Date.parse(this.taskObj.end_date);
      this.taskFormVis = true;
    },
    openTaskAdd() {
      this.taskObj = {
        text: "新任务",
        start_date: new Date(),
        projectId: this.selectedProject.id,
        parent: this.selectedTask.id, //隐藏字段
        assignStaffId: this.$store.state.loginUser.id,
        priority:60
      };
      this.taskFormVis = true;
    }
  },
  computed: {
    caniOpen() {
      //我可以打开吗 - 除了常规员工外，领导(r01)、项目经理、项目助理、监理也可以查看
      var isSpecial = false;
      var allMyRoles = this.$store.state.myRoles;
      for (var _role of allMyRoles) {
        if (_role.projectId === this.selectedProject.id) {
          if (
            _role.group_role === "R01" ||
            _role.group_role === "R02" ||
            _role.group_role === "R03" ||
            _role.group_role === "R04"
          ) {
            isSpecial = true;
            break;
          }
        }
      }
      return !(
        this.selectedTask.assignStaffId === this.$store.state.loginUser.id ||
        this.selectedTask.actorStaffId === this.$store.state.loginUser.id ||
        isSpecial
      );
    },
    handleMyEvent(msg) {
      console.log("订阅my-event事件：" + msg);
    }
  },
  mounted() {
    var loginUser = this.$store.state.loginUser;
    this.selectMyProjectList(loginUser.code.substr(0, 1) + loginUser.id);
    this.tasks = { data: [], links: [] };

    this.$bus.$on("my-event", this.handleMyEvent); // 订阅消息
  },
  destroyed() {
    console.log("释放订阅 my-event事件");
    this.$bus.$off("my-event", this.handleMyEvent); // 释放/取消订阅
  }
};
</script>

<style scoped>
html,
body {
  height: 100%;
  padding: 0px;
  margin: 0px;
  overflow: hidden;
}
@import "~dhtmlx-gantt/codebase/dhtmlxgantt.css";
</style>
