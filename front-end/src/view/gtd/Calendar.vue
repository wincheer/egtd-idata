<template>
  <section>
    <scheduler :events="events" class="scheduler" @onBeforeLightbox="openTaskEdit"/>
    <!--任务编辑对话框-->
    <el-dialog :title="'任务：'+selectedTask.text" :visible.sync="dlgTaskEditVis" width="40%" :close-on-click-modal="false">
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
import Scheduler from "@/component/Scheduler.vue";
import {
  SELECT_TASK_LIST,
  SELECT_DOCUMENT_LIST,
  SELECT_PROJECT_EMPLOYEE_LIST,
  UPDATE_PROJECT_TASK,
  DELETE_DOCUMENT,
  UPDATE_PROJECT_TASK_WITH_FILE_RESULT
} from "@/config/api";
import { formatDate } from "@/util/date.js";
import base from "@/config/remote";
export default {
  components: { Scheduler },
  data() {
    return {
      events: [],
      selectedTask: [],
      dlgTaskEditVis: false,
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
        type: "task" //隐藏字段，标记任务类型
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
      taskMode: "update",
      taskStandardFileList: [],
      taskResultFileList: [],
      selectedFile: "",
      projectStaffList: []
    };
  },
  methods: {
    openTaskEdit(evt) {
      this.selectedTask = evt;
      this.taskObj = Object.assign(evt);
      this.taskMode == "update";
      this.selectProjectStaffList(this.selectedTask.projectId);
      this.selectDocumentList(this.selectedTask.id, "2");
      this.selectDocumentList(this.selectedTask.id, "3");
      this.dlgTaskEditVis = true;
    },
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd hh:mm");
    },
    selectMyTaskList() {
      var _this = this;
      SELECT_TASK_LIST({ actorStaffId: this.$store.state.loginUser.id }).then(
        res => {
          for (var i = 0; i < res.length; i++) {
            res[i].start_date = _this.fmtDate(res[i].start_date);
            res[i].end_date = _this.fmtDate(res[i].end_date);
          }
          _this.events = res;
        }
      );
    },
    selectDocumentList(projectTaskId, docCategory) {
      var _this = this;
      SELECT_DOCUMENT_LIST({
        belongTo: "task",
        sourceId: projectTaskId,
        category: docCategory
      }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目文档文件失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.contractFileList = res;
          if (docCategory === "2") _this.taskStandardFileList = res;
          if (docCategory === "3") _this.taskResultFileList = res;
        }
      });
    },
    downloadFile(file) {
      //console.log("Hello,Baby");
      let link = document.createElement("a");
      link.href = base + "/download?docId=" + file.id;
      link.target = "_BLANK";
      link.click();
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
    uploadFile() {
      var _uploadFiles = this.$refs.uploadStandard.uploadFiles;

      if (_uploadFiles.length === 0) this.updateTask();
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
              _this.selectMyTaskList();
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
            if (key.substr(0, 1) !== "$")
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
            if (key.substr(0, 1) !== "$")
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
    selectProjectStaffList(projectId) {
      var _this = this;
      SELECT_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        _this.projectStaffList = res;
      });
    }
  },
  computed: {
    whoami() {
      //我是谁 - 任务发布者 asigner ? 任务接受者 actor?
      var myRole = []; //默认是无关者（非任务直接参与人）
      if (this.taskMode == "update") {
        if (this.selectedTask.assignStaffId === this.$store.state.loginUser.id)
          myRole.push("asigner");
        if (this.selectedTask.actorStaffId === this.$store.state.loginUser.id)
          myRole.push("actor");
      } else {
        myRole.push("asigner");
      }
      return myRole;
    }
  },
  mounted() {
    this.selectMyTaskList();
  }
};
</script>

<style scoped>
@import "~dhtmlx-scheduler/codebase/dhtmlxscheduler.css";
.scheduler {
  overflow: hidden;
  position: relative;
  top: 0px;
  bottom: 0px;
  height: 700px;
}
</style>
