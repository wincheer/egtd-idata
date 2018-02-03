<template>
  <el-dialog :title="'任务：'+task.text" :visible.sync="isShow" width="40%" :close-on-click-modal="false" :before-close="closeTaskForm">
    <el-form :model="task" :rules="taskObjRules" ref="taskForm" label-width="80px" label-position="right"> 
        <el-row :gutter="10">
            <el-col :span="12">
            <el-form-item label="任务名称" prop="text">
                <el-input v-model="task.text" placeholder="新任务" :disabled="whoami.indexOf('asigner')==-1"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="计划工时" prop="planWorkload">
                <el-input-number v-model="task.planWorkload" :disabled="whoami.indexOf('asigner')==-1" :min="0" :max="1000" style="width:160px"></el-input-number> 小时
            </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="任务描述"  style="margin-right: 10px;" prop="taskDesc">
            <el-input v-model="task.taskDesc" :disabled="whoami.indexOf('asigner')==-1" placeholder="填写任务要求" type="textarea"></el-input>
            <el-upload ref="uploadStandard" action="any" :disabled="whoami.indexOf('asigner')==-1" :http-request="updateTaskWithFile" :on-preview="downloadFile" :on-remove="onFileRemove" :auto-upload="false" :file-list="taskStandardFileList">
            <el-button slot="trigger" :disabled="whoami.indexOf('asigner')==-1" size="mini">任务要求附件 ...</el-button>
            </el-upload>
        </el-form-item>
        <el-row :gutter="10">
            <el-col :span="12">
            <el-form-item label="开始日期" prop="start_date">
                <el-date-picker v-model="task.start_date" :disabled="whoami.indexOf('asigner')==-1" placeholder="选择开始日期"></el-date-picker>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="结束日期" prop="end_date">
                <el-date-picker v-model="task.end_date" :disabled="whoami.indexOf('asigner')==-1" placeholder="选择结束日期"></el-date-picker>
            </el-form-item>
            </el-col>
        </el-row>
        <el-row :gutter="10">
            <el-col :span="12">
            <el-form-item label="创建人" prop="assignStaffId">
                <el-select v-model="task.assignStaffId" disabled clearable placeholder="请选择">
                <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="接收人"  prop="actorStaffId">
                <el-select v-model="task.actorStaffId" clearable placeholder="请选择"  :disabled="whoami=='actor'">
                <el-option v-for="item in projectStaffList" :key="item.id" :label="item.empName" :value="item.id" />
                </el-select>
            </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="优先级" prop="priority">
            <el-radio-group v-model="task.priority"  :disabled="whoami=='actor'">
            <el-radio-button label="100">最高</el-radio-button>
            <el-radio-button label="80">较高</el-radio-button>
            <el-radio-button label="60">普通</el-radio-button>
            <el-radio-button label="40">较低</el-radio-button>
            <el-radio-button label="20">最低</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="当前进度"  prop="progress" v-show="taskMode==='update'">
            <el-slider v-model="task.progress" :min="0" :max="1" :step="0.2" :disabled="whoami.indexOf('actor')==-1" show-stops style="margin-left: 5px;"></el-slider>
            <el-upload ref="uploadResult" action="any" :disabled="whoami.indexOf('actor')==-1" :http-request="updateTaskWithFileResult" :on-preview="downloadFile" :on-remove="onFileRemove" :file-list="taskResultFileList">
            <el-button slot="trigger" :disabled="whoami.indexOf('actor')==-1" size="mini">任务结果附件 ...</el-button>
            </el-upload>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="closeTaskForm">取消</el-button>
    <el-button type="primary" @click="uploadFile">保存</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {
  SELECT_TASK_LIST,
  SELECT_DOCUMENT_LIST,
  SELECT_PROJECT_EMPLOYEE_LIST,
  UPDATE_PROJECT_TASK,
  DELETE_DOCUMENT,
  UPDATE_PROJECT_TASK_WITH_FILE_RESULT,
  UPDATE_PROJECT_TASK_WITH_FILE
} from "@/config/api";
import { formatDate } from "@/util/date.js";
import base from "@/config/remote";

export default {
  name: "task-edit-form",
  props: {
    isShow: false,
    task: {}
  },
  data() {
    return {
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
      taskStandardFileList: [],
      taskResultFileList: [],
      selectedFile: "",
      projectStaffList: []
    };
  },
  methods: {
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd hh:mm");
    },
    closeTaskForm() {
      this.$emit("close");
    },
    selectDocumentList(projectTaskId, docCategory) {
      var _this = this;
      if (docCategory === "2") _this.taskStandardFileList = [];
      if (docCategory === "3") _this.taskResultFileList = [];
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
          UPDATE_PROJECT_TASK(_this.task).then(data => {
            if (data == "") {
              _this.$message({
                message: "更新任务失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.$emit("close");
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
          for (var key in _this.task) {
            if (key.substr(0, 1) !== "$") formData.append(key, _this.task[key]);
          }
          formData.append("file", item.file);
          UPDATE_PROJECT_TASK_WITH_FILE(formData).then(data => {
            if (data !== 0)
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
              _this.$emit("close");
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
          for (var key in _this.task) {
            if (key.substr(0, 1) !== "$") formData.append(key, _this.task[key]);
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
      _this.projectStaffList = [];
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
        if (this.$props.task.assignStaffId === this.$store.state.loginUser.id)
          myRole.push("asigner");
        if (this.$props.task.actorStaffId === this.$store.state.loginUser.id)
          myRole.push("actor");
      } else {
        myRole.push("asigner");
      }
      return myRole;
    },
    taskMode() {
      if (this.$props.task.id) return "update";
      else return "update";
    }
  },
  mounted() {},
  watch: {
    task: {
      handler: function() {
        this.selectProjectStaffList(this.$props.task.projectId);
        this.selectDocumentList(this.$props.task.id, "2");
        this.selectDocumentList(this.$props.task.id, "3");
      },
      deep: false //如果不能正常更新,改为true
    }
  }
};
</script>

<style>

</style>

