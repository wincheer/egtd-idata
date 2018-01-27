<template>
    <section>
      <el-row style="margin-top: 20px;margin-bottom: 10px;">
        <el-select v-model="selectedProject" placeholder="请选择项目" @change="onProjectChange" value-key="id">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item" />
        </el-select>
        <el-button icon="el-icon-edit" type="primary" plain @click="openTaskEdit" :disabled="caniOpen || selectedTask.parent==0"> 编辑任务</el-button>
        <el-button icon="el-icon-share" type="primary" plain @click="openTaskAdd" :disabled="caniOpen">分配子任务</el-button>
      </el-row>
      <el-row>
        <gantt :tasks="tasks" @task-selected="onSelectTask"></gantt>
      </el-row>
      <!--任务编辑对话框-->
      <el-dialog :title="taskTitle" :visible.sync="dlgTaskEditVis" width="35%" :close-on-click-modal="false">
        <el-form :model="taskObj" :rules="taskObjRules" ref="taskForm" label-width="80px" label-position="right"> 
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="任务名称" prop="text">
                <el-input v-model="taskObj.text" placeholder="新任务" :disabled="whoami.indexOf('asigner')==-1"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计划工时" prop="planWorkload">
                <el-input-number v-model="taskObj.planWorkload" :disabled="whoami.indexOf('asigner')==-1" :min="0" :max="1000" style="width:160px"></el-input-number> 小时
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="任务描述"  style="margin-right: 10px;" prop="taskDesc">
            <el-input v-model="taskObj.taskDesc" :disabled="whoami.indexOf('asigner')==-1" placeholder="填写任务要求" type="textarea"></el-input>
            <el-upload ref="uploadStandard" action="any" :disabled="whoami.indexOf('asigner')==-1" :http-request="updateTaskWithFile" :on-preview="downloadFile" :on-remove="onFileRemove" :auto-upload="false" :file-list="taskStandardFileList">
              <el-button slot="trigger" :disabled="whoami.indexOf('asigner')==-1" size="mini">任务要求附件 ...</el-button>
            </el-upload>
          </el-form-item>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="开始日期" prop="start_date">
                <el-date-picker v-model="taskObj.start_date" :disabled="whoami.indexOf('asigner')==-1" placeholder="选择开始日期"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结束日期" prop="end_date">
                <el-date-picker v-model="taskObj.end_date" :disabled="whoami.indexOf('asigner')==-1" placeholder="选择结束日期"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="创建人" prop="assignStaffId">
                <el-select v-model="taskObj.assignStaffId" disabled clearable placeholder="请选择">
                  <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="接收人"  prop="actorStaffId">
                <el-select v-model="taskObj.actorStaffId" clearable placeholder="请选择"  :disabled="whoami=='actor'">
                  <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="优先级" prop="priority">
            <el-radio-group v-model="taskObj.priority"  :disabled="whoami=='actor'">
              <el-radio-button label="100">最高</el-radio-button>
              <el-radio-button label="80">较高</el-radio-button>
              <el-radio-button label="60">普通</el-radio-button>
              <el-radio-button label="40">较低</el-radio-button>
              <el-radio-button label="20">最低</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="当前进度"  prop="progress" v-show="this.taskMode==='update'">
            <el-slider v-model="taskObj.progress" :min="0" :max="1" :step="0.2" :disabled="whoami.indexOf('actor')==-1" show-stops style="margin-left: 5px;"></el-slider>
            <el-upload ref="uploadResult" action="any" :disabled="whoami.indexOf('actor')==-1" :http-request="updateTaskWithFileResult" :on-preview="downloadFile" :on-remove="onFileRemove" :file-list="taskResultFileList">
              <el-button slot="trigger" :disabled="whoami.indexOf('actor')==-1" size="mini">任务结果附件 ...</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dlgTaskEditVis = false">取消</el-button>
          <el-button type="primary" @click="uploadFile">保存</el-button>
        </div>
      </el-dialog>
    </section>
</template>

<script>
import Gantt from "@/component/Gantt.vue";
import {
  SELECT_MY_PROJECT_LIST,
  SELECT_PROJECT_TASK_LIST,
  UPDATE_PROJECT_TASK,
  UPDATE_PROJECT_TASK_WITH_FILE,
  UPDATE_PROJECT_TASK_WITH_FILE_RESULT,
  DELETE_DOCUMENT,
  SELECT_DOCUMENT_LIST,
  SELECT_PROJECT_EMPLOYEE_LIST,
  SELECT_GROUP_EMP_LIST,
} from "@/config/api";
import { formatDate } from "@/util/date.js";
import base from "@/config/remote";
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
        priority: 60,
        state: "", //隐藏字段。任务状态：未开始，进行中，已完成待确认，已完成
        planWorkload: "", //工作量
        realWorkload: "", // 隐藏字段，实际工作量
        isDelay: "", //隐藏字段，是否延期
        delayReason: null, //隐藏字段：延期原因
        readonly: "", //隐藏字段，附加字段
        editable: "", //隐藏字段，附加字段
        type: "task", //隐藏字段，标记任务类型
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
      projectStaffList: [],
      taskStandardFileList:[],
      taskResultFileList:[],
      taskMode: "" //任务模式编辑 edit 或者分配子任务 asign,与登录者身份(whoami)配合用来检测可编辑状态
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
    selectProjectStaffList(projectId) {
      var _this = this;
      SELECT_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        _this.projectStaffList = res;
      });
    },
    selectDocumentList(projectTaskId,docCategory) {
      var _this = this;
      SELECT_DOCUMENT_LIST({
        belongTo: "task",
        sourceId: projectTaskId,
        category:docCategory
      }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目文档文件失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.contractFileList = res;
          if(docCategory==="2") _this.taskStandardFileList = res;
          if(docCategory==="3") _this.taskResultFileList = res;
        }
      });
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
    openTaskEdit() {
      this.taskMode = "update";
      this.taskObj = Object.assign(this.selectedTask);
      this.selectDocumentList(this.selectedTask.id,"2");
      this.selectDocumentList(this.selectedTask.id,"3");
      this.dlgTaskEditVis = true;
    },
    openTaskAdd() {
      this.taskMode = "add";

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
        priority: 60,
        state: 0, //隐藏字段。任务状态：未开始0，进行中1，已完成待确认2，已完成3
        planWorkload: "", //工作量
        realWorkload: "", // 隐藏字段，实际工作量
        isDelay: 0, //隐藏字段，是否延期
        delayReason: null, //隐藏字段：延期原因
        readonly: 0, //隐藏字段，附加字段
        editable: 1, //隐藏字段，附加字段
        type: "task", //隐藏字段，标记任务类型
        hasSupervisor: "0" //隐藏字段，用来扩展任务完成检查链
      };

      this.taskStandardFileList = [];
      this.taskResultFileList = [];

      this.dlgTaskEditVis = true;
    },
    ////////
    /**上传文件，同时包含表单内容 */
    uploadFile() {
      var _uploadFiles = this.$refs.uploadStandard.uploadFiles;
      if (_uploadFiles.length == 0) this.updateTask();
      else {
        for (var i = 0; i < _uploadFiles.length; i++) {
          var _uploadFile = _uploadFiles[i];
          if (_uploadFile.status != "success") {
            this.$refs.uploadStandard.submit();
            return;
          }
        }
        this.updateTask();
      }
    },
    updateTask() {
      var _this = this;
      this.$refs.taskForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_TASK(_this.taskObj).then(data => {
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
    },
    updateTaskWithFile(item) {
      var _this = this;
      this.$refs.taskForm.validate(valid => {
        if (valid) {
          var formData = new FormData();
          for (var key in _this.taskObj) {
            if(key.substr(0,1)!=="$")
              formData.append(key, _this.taskObj[key]);
          }
          formData.append("file", item.file);
          UPDATE_PROJECT_TASK_WITH_FILE(formData).then(data => {
            if (data !== 0)
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
              _this.dlgTaskEditVis = false;
              _this.selectTaskList(_this.selectedProject);
            }
          });
        }
      });
    },
    updateTaskWithFileResult(item) {
      var _this = this;
      this.$refs.taskForm.validate(valid => {
        if (valid) {
          var formData = new FormData();
          for (var key in _this.taskObj) {
            if(key.substr(0,1)!=="$")
              formData.append(key, _this.taskObj[key]);
          }
          formData.append("file", item.file);
          UPDATE_PROJECT_TASK_WITH_FILE_RESULT(formData).then(data => {
            if (data !== 0)
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
              //_this.dlgTaskEditVis = false;
              //_this.selectTaskList(_this.selectedProject);
            }
          });
        }
      });
    },
    onFileRemove(file, fileList) {
      var _this = this;
      if (file.status == "success") {
        DELETE_DOCUMENT({ id: file.id }).then(res => {
          _this.$message({ message: "删除成功", type: "success" });
          //_this.selectDocumentList(file.sourceId);
        });
      }
      this.selectedFile = null;
    },
    downloadFile(file){
      //console.log("Hello,Baby");
      let link = document.createElement("a");
      link.href = base + "/download?docId=" + file.id;
      link.target = "_BLANK"
      link.click();
    },
  },
  computed: {
    caniOpen() {
      //我可以打开吗
      return !(
        this.selectedTask.assignStaffId === this.$store.state.loginUser.id ||
        this.selectedTask.actorStaffId === this.$store.state.loginUser.id
      );
    },
    whoami() {
      //我是谁 - 任务发布者 asigner ? 任务接受者 actor?
      var myRole = [] //默认是无关者（非任务直接参与人）
      if(this.taskMode=="update"){
        if (this.selectedTask.assignStaffId === this.$store.state.loginUser.id)
          myRole.push("asigner");
        if (this.selectedTask.actorStaffId === this.$store.state.loginUser.id)
          myRole.push("actor");
      }
      else{
          myRole.push("asigner");
      }
      return myRole;
    },
    taskTitle(){
      var title = ""
      if(this.taskMode == "update") title = this.selectedTask.text + "：更新";
      else title = this.selectedTask.text + "：分配子任务";
      return title;
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
