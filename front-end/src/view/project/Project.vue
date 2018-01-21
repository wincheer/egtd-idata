<template>
    <section>
      <el-row style="margin-top: 20px;margin-bottom: 10px;">
        <el-select v-model="selectedProject" placeholder="请选择项目" @change="onProjectChange" value-key="id">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item" />
        </el-select>
        <el-button icon="el-icon-edit" type="primary" plain>编辑任务</el-button>
        <el-button icon="el-icon-share" type="primary" plain>分配子任务</el-button>
      </el-row>
      <el-row>
        <gantt :tasks="tasks" @task-updated="onTaskUpdate" @task-selected="onSelectTask" @lightbox-open="onLightBoxOpen" @lightbox-close="onLightBoxClose"></gantt>
      </el-row>
      <!--任务编辑对话框-->
      <el-dialog :title="selectedTask.text" :visible.sync="lightBoxVisible" width="35%">
        <el-form :model="selectedTask" label-width="80px" label-position="right"> 
          <el-form-item label="任务名称">
            <el-input v-model="selectedTask.text" auto-complete="off" style="width:320px"></el-input>
          </el-form-item>
          <el-form-item label="开始时间">
            <el-date-picker v-model="selectedTask.start_date" type="date" style="width:160px" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="工作量">
            <el-input-number v-model="selectedTask.duration" :min="1" :max="100" style="width:160px"></el-input-number> 天
          </el-form-item>
          <el-form-item label="责任人">
            <el-select v-model="selectedTask.assigned" clearable placeholder="请选择">
              <el-option label="张三" value="30"></el-option>
              <el-option label="李四" value="20"></el-option>
              <el-option label="王五" value="10"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="优先级">
            <el-radio-group v-model="selectedTask.priority">
              <el-radio-button label="1">高</el-radio-button>
              <el-radio-button label="2">中</el-radio-button>
              <el-radio-button label="3">低</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="当前进度">
            <el-slider v-model="selectedTask.progress" :min="0" :max="1" :step="0.1" show-stops style="width:90%"></el-slider>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="lightBoxVisible = false">取消</el-button>
          <el-button type="primary" @click="lightBoxVisible = false">保存</el-button>
        </div>
      </el-dialog>
    </section>
</template>

<script>
import Gantt from "@/component/Gantt.vue";
import { SELECT_MY_PROJECT_LIST, SELECT_PROJECT_TASK_LIST } from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  components: { Gantt },
  data() {
    return {
      tasks: {
        data: [],
        links: []
      },
      myProjectList: [],
      selectedProject: {},
      selectedTask: {},
      lightBoxVisible: false,
      isLightBoxActive: false
    };
  },
  methods: {
    selectMyProjectList(staffCode) {
      var _this = this;
      SELECT_MY_PROJECT_LIST({ code: staffCode }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取我的项目列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.myProjectList = res;
          if (res.length) {
            this.selectedProject = _this.myProjectList[0];
            this.selectProjectTaskList(this.selectedProject)
          }
        }
      });
    },
    fillTaskData(taskDataList){
      for (var i = 0; i < taskDataList.length; i++) {
        taskDataList[i].start_date = this.fmtDate(taskDataList[i].start_date);
        taskDataList[i].end_date = this.fmtDate(taskDataList[i].end_date);
      }
      this.tasks.data = taskDataList;
    },
    selectProjectTaskList(project) {
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
      return formatDate(new Date(timestamp), "yyyy-MM-dd");
    },
    onSelectTask(task) {
      this.selectedTask = task;
    },
    onTaskUpdate(id, mode, task) {
      let text = task && task.text ? ` (${task.text})` : "";
      let message = `Task ${mode}: ${id} ${text}`;
      this.$message(message);
    },
    onLightBoxOpen(id, task) {
      this.isLightBoxActive = true;

      this.selectedTask = task;
      this.lightBoxVisible = true;
    },
    onLightBoxClose() {
      this.isLightBoxActive = false;
    },
    onProjectChange(project) {
      if (project) this.selectProjectTaskList(project);
    }
  },
  mounted() {
    this.selectMyProjectList(this.$store.state.loginUser.code);
    this.tasks = {data:[],links:[]};
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
