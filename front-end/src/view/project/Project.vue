<template>
    <section>
      <el-row style="margin-top: 20px;margin-bottom: 10px;">
        <el-select v-model="selectedProject" placeholder="请选择项目" @change="onProjectChange" value-key="id">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item" />
        </el-select>
        <el-button icon="el-icon-edit" type="primary" plain @click="openTastEdit" :disabled="selectedTask.text==undefined"> 编辑任务</el-button>
        <el-button icon="el-icon-share" type="primary" plain @click="openTastAdd" :disabled="selectedTask.text==undefined">分配子任务</el-button>
      </el-row>
      <el-row>
        <gantt :tasks="tasks" @task-selected="onSelectTask" @task-unselected="onUnelectTask"></gantt>
      </el-row>
      <!--任务编辑对话框-->
      <el-dialog :title="'项目任务：'+selectedTask.text" :visible.sync="dlgTaskEditVis" width="35%" :close-on-click-modal="false">
        <el-form :model="taskObj" :rules="taskObjRules" ref="taskForm" label-width="80px" label-position="right"> 
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="任务名称" prop="text">
                <el-input v-model="taskObj.text" placeholder="新任务"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="工作量" prop="planWorkload">
                <el-input-number v-model="selectedTask.planWorkload" :min="0" :max="1000" style="width:160px"></el-input-number> 天
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="任务描述"  style="margin-right: 10px;" prop="taskDesc">
            <el-input v-model="taskObj.taskDesc" placeholder="填写任务要求" type="textarea"></el-input>
            <!-- <el-upload ref="upload" action="any" :http-request="updateProjectContract" :on-change="onFileChange" :on-remove="onFileRemove" :auto-upload="false" :limit="1" :file-list="contractFileList">
              <el-button slot="trigger" size="mini">添加附件 ...</el-button>
            </el-upload> -->
          </el-form-item>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="开始日期" prop="start_date">
                <el-date-picker v-model="taskObj.start_date" placeholder="选择开始日期"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结束日期" prop="end_date">
                <el-date-picker v-model="taskObj.end_date" placeholder="选择结束日期"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="任务分配" prop="assignStaffId">
                <el-select v-model="taskObj.assignStaffId" clearable placeholder="请选择">
                  <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="任务执行"  prop="actorStaffId">
                <el-select v-model="taskObj.actorStaffId" clearable placeholder="请选择">
                  <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="优先级" prop="priority">
            <el-radio-group v-model="taskObj.priority">
              <el-radio-button label="1">最高</el-radio-button>
              <el-radio-button label="1">较高</el-radio-button>
              <el-radio-button label="2">普通</el-radio-button>
              <el-radio-button label="3">较低</el-radio-button>
              <el-radio-button label="3">最低</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="当前进度"  prop="progress">
            <el-slider v-model="taskObj.progress" :min="0" :max="1" :step="0.2" show-stops style="width:90%"></el-slider>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dlgTaskEditVis = false">取消</el-button>
          <el-button type="primary" @click="updateTask">保存</el-button>
        </div>
      </el-dialog>
    </section>
</template>

<script>
import Gantt from "@/component/Gantt.vue";
import {
  SELECT_MY_PROJECT_LIST,
  SELECT_PROJECT_TASK_LIST,
  UPDATE_PROJECT_TASKE,
  SELECT_PROJECT_EMPLOYEE_LIST,
  SELECT_GROUP_STAFF_LIST
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  components: { Gantt },
  data() {
    return {
      tasks: {
        data: [],
        links: []
      },
      taskObj: {
        projectId: "", //隐藏字段
        id: "", //隐藏字段
        parent: "", //隐藏字段
        text: "",
        start_date: "",
        end_date: "",
        taskDesc: "",
        assignStaffId: "",
        actorStaffId: "",
        progress: 0,
        priority: "",
        state: "", //隐藏字段。任务状态：未开始，进行中，已完成待确认，已完成
        planWorkload: "", //工作量
        realWorkload: "", // 隐藏字段，实际工作量
        isDelay: "", //隐藏字段，是否延期
        delayReason: "", //隐藏字段：延期原因
        readonly: "", //隐藏字段，附加字段
        editable: "", //隐藏字段，附加字段
        type: "task", //隐藏字段，标记任务类型
        hasSupervisor: "" //隐藏字段，用来扩展任务完成检查链
      },
      taskObjRules: {
        text: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        start_date: [
          { required: true, message: "任务开始时间不能为空", trigger: "blur" }
        ],
        end_date: [
          { required: true, message: "任务结束时间不能为空", trigger: "blur" }
        ]
      },
      myProjectList: [],
      selectedProject: {},
      selectedTask: {},
      dlgTaskEditVis: false,
      projectStaffList:[],
      groupStaffList:[]
      //isLightBoxActive: false
    };
  },
  methods: {
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
            _this.selectProjectStaffList(_this.selectedProject.id);
            // this.selectGroupStaffList(this.selectedProject.id,this.$store.state.loginUser.id);
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
    selectProjectStaffList(projectId){
      var _this = this;
      SELECT_PROJECT_EMPLOYEE_LIST({id:projectId}).then(res=>{
        _this.projectStaffList = res;
      })
    },
    selectGroupStaffList(projectId,groupId){
      var _this = this;
      SELECT_GROUP_STAFF_LIST({id:projectId}).then(res=>{
        _this.groupStaffList = res;
      })
    },
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd");
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
    onUnelectTask(task) {
      this.selectedTask = {};
    },
    openTastEdit() {
      this.taskObj = Object.assign(this.selectedTask);
      this.taskObj.assignStaffId = this.$store.state.loginUser.id;
      this.dlgTaskEditVis = true;
    },
    openTastAdd() {
      this.taskObj = {
        projectId: this.selectedProject.id,
        id: "",
        parent: this.selectedTask.id, //隐藏字段
        text: "",
        start_date: "",
        end_date: "",
        taskDesc: "",
        assignStaffId: this.$store.state.loginUser.id,
        actorStaffId: "",
        progress: 0,
        priority: "",
        state: 0, //隐藏字段。任务状态：未开始0，进行中1，已完成待确认2，已完成3
        planWorkload: "", //工作量
        realWorkload: "", // 隐藏字段，实际工作量
        isDelay: 0, //隐藏字段，是否延期
        delayReason: "", //隐藏字段：延期原因
        readonly: 0, //隐藏字段，附加字段
        editable: 1, //隐藏字段，附加字段
        type: "task", //隐藏字段，标记任务类型
        hasSupervisor: "0" //隐藏字段，用来扩展任务完成检查链
      };
      this.dlgTaskEditVis = true;
    },
    updateTask() {
      var _this = this;
      this.$refs.taskForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_TASKE(_this.taskObj).then(data => {
            if (data == "") {
              _this.$message({
                message: "更新任务失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.dlgTaskEditVis = false;
              _this.selectTaskList(_this.selectedProject);
            }
          });
        }
      });
    }
  },
  mounted() {
    this.selectMyProjectList(this.$store.state.loginUser.code);
    this.tasks = { data: [], links: [] };
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
