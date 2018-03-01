<template>
  <section>
      <!-- 消息中心 -->
      <el-dialog title="消息中心" :visible.sync="isShow" width="550px" :before-close="closeDialog">
        <el-tabs v-model="avtiveMsgTab" type="card">
          <el-tab-pane label="新消息" name="latest">
            <el-table :data="latestMsgList" stripe :show-header="false" highlight-current-row @current-change="rowChange">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <span>{{ props.row.body }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="来自" prop="from"></el-table-column> -->
              <el-table-column label="主题" prop="title"></el-table-column>
              <el-table-column label="时间" prop="time" :formatter="fmtDate"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button size="mini" type="primary" @click="viewDetail(scope.row)" v-if="scope.row.type!=='normal'">详情</el-button> 
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="历史消息" name="history">
            <el-table :data="historyMsgList" stripe :show-header="false" highlight-current-row>
              <el-table-column type="expand">
                <template slot-scope="props">
                  <span>{{ props.row.body }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="来自" prop="from"></el-table-column> -->
              <el-table-column label="主题" prop="title"></el-table-column>
              <el-table-column label="时间" prop="time" :formatter="fmtDate"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button size="mini" type="primary" @click="viewDetail(scope.row)" v-if="scope.row.type!=='normal'">详情</el-button> 
                  <el-button size="mini" type="danger" @click="deleteMsg(scope.row)">删除</el-button> 
                </template>
              </el-table-column>
            </el-table>
            <el-pagination layout="prev, pager, next"  :page-size="pageSize" :total="total" @current-change="pageChange" style="float:right;"/>
          </el-tab-pane>
        </el-tabs>
        <div slot="footer">
          <!-- <el-button @click="sendMsg" size="mini" type="primary" icon="el-icon-edit">发布消息</el-button> -->
          <el-button @click.native="closeDialog" size="mini" >关闭</el-button>
        </div>
      </el-dialog>
      <!--项目审批-->
      <el-dialog :title="dlgTitle" :visible.sync="dlgAuditProjecyVis" width="30%">
        <el-tabs v-model="avtiveProjectTab">
          <el-tab-pane label="项目基本信息" name="info">
            <el-form label-position="left" style="margin-left:10px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="项目名称：">
                    <span>{{project.projectName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="项目金额：">
                    <span>{{project.amount}} 万元</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <!-- <el-row>
                <el-col :span="12">
                  <el-form-item label="项目经理">
                  <span>{{project.actorStaffId}}</span>
                </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="所属部门">
                  <span>{{project.depId}} </span>
                </el-form-item>
                </el-col>
              </el-row> -->
              <el-row>
                <el-col :span="12">
                  <el-form-item label="启动日期：">
                  <span>{{project.startDate|fmtDateFilter}}</span>
                </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="结束日期：">
                    <span>{{project.endDate|fmtDateFilter}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
          </el-form>
          </el-tab-pane>
          <el-tab-pane label="项目阶段划分" name="stage">
            <el-table :data="projectStageList" highlight-current-row >
              <el-table-column label="阶段" prop="text"></el-table-column>
              <el-table-column label="开始日期" prop="start_date" :formatter="fmtDate"></el-table-column>
              <el-table-column label="结束日期" prop="end_date" :formatter="fmtDate"></el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
        <div slot="footer">
          <el-button :disabled="!selectedMsg||selectedMsg.isExec===1" @click="execMessage(3)" size="mini" type="success" icon="el-icon-check">同意</el-button>
          <el-button :disabled="!selectedMsg||selectedMsg.isExec===1" @click="refuseMessage(2)" size="mini" type="primary" icon="el-icon-close">拒绝</el-button>
          <el-button @click.native="dlgAuditProjecyVis = false" size="mini" >关闭</el-button>
        </div>
      </el-dialog>
      <!--任务确认-->
      <el-dialog :title="dlgTitle" :visible.sync="dlgConfirmTaskVis" width="30%">
        <el-tabs v-model="avtiveProjectTab">
          <el-tab-pane label="任务基本信息" name="info">
            <el-form label-position="left" style="margin-left:10px" label-width="110px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="任务名称：">{{task.text}}</el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="计划工时：">{{task.planWorkload}}</el-form-item>
                </el-col>
              </el-row>
              <!-- <el-row><el-col :span="12"></el-col><el-col :span="12"></el-col></el-row> -->
              <el-row>
                <el-col :span="12"><el-form-item label="计划开始时间：">{{task.start_date|fmtDateFilter}}</el-form-item></el-col>
                <el-col :span="12"><el-form-item label="计划结束时间：">{{task.end_date|fmtDateFilter}}</el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="任务创建人：">{{fmtEmployee(task.assignStaffId)}}</el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="任务接收人：">{{fmtEmployee(task.actorStaffId)}}</el-form-item>
                </el-col>
              </el-row>
              <el-form-item label="任务要求附件：">  
                <el-table :data="taskStandardFileList" :show-header="false" empty-text="没有任务要求文件" border @row-click="downloadFile">
                  <el-table-column prop="name"></el-table-column>
                </el-table>
              </el-form-item>
              <el-form-item label="任务结果附件：">
                <el-table :data="taskResultFileList" :show-header="false" empty-text="没有任务结果文件" border @row-click="downloadFile">
                  <el-table-column prop="name"></el-table-column>
                </el-table>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="任务过程" name="group">
            <el-form label-width="120px">
              <el-form-item label="任务创建时间：">{{task.create_date|fmtDateTimeFilter}}</el-form-item>
              <el-form-item label="实际开始时间：">{{task.real_start_date|fmtDateTimeFilter}}</el-form-item>
              <el-table :data="taskCheckList" :show-header="true" empty-text="尚未进行检查" border>
                <el-table-column label="任务检查人" prop="checker" :formatter="fmtEmp"></el-table-column>
                <el-table-column label="检查时间"  prop="checkDate" :formatter="fmtDate"></el-table-column>
                <el-table-column label="检查结果"  prop="result"></el-table-column>
              </el-table>
            </el-form>
          </el-tab-pane>
        </el-tabs>
        <div slot="footer">
          <el-button-group v-if="selectedMsg && selectedMsg.type!=='normal'">
            <el-button :disabled="selectedMsg.isRead===1" @click="execMessage(3)" size="mini" type="success" icon="el-icon-check">确认完成</el-button>
            <el-button :disabled="selectedMsg.isRead===1" @click="refuseMessage(4)" size="mini" type="danger" icon="el-icon-close">拒绝</el-button>
            <el-select size="mini" style="width:120px" v-model="nextChecker" clearable value-key="id">
              <el-option v-for="item in projectEmployeeList" :key="item.id" :label="item.empName" :value="item" />
            </el-select>
            <el-button  @click="forwardTask(2)" size="mini" type="primary" icon="el-icon-share" :disabled="selectedMsg.isRead===1 || nextChecker===''">再确认</el-button>
          </el-button-group>
          <el-button @click.native="dlgConfirmTaskVis = false" size="mini" >关闭</el-button>
        </div>
      </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_MESSAGE_PAGE_LIST,
  UPDATE_MESSAGE,
  DELETE_MESSAGE,
  SELECT_PROJECT_STAGE_LIST,
  SELECT_PROJECT,
  SELECT_PROJECT_TASK,
  SELECT_PROJECT_EMPLOYEE_LIST,
  SELECT_DOCUMENT_LIST,
  SELECT_TASK_CHECK_LIST,
  UPDATE_TASK_CHECK
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  name: "message-center",
  props: {
    isShow: false
  },
  data() {
    return {
      dlgAuditProjecyVis: false,
      dlgConfirmTaskVis: false,
      selectedMsg: {},
      avtiveMsgTab: "latest",
      avtiveProjectTab: "info",
      dlgTitle: "",
      latestMsgList: [],
      historyMsgList: [],
      total: 0, //分页 - 记录总数
      pageNo: 1, //分页 - 当前页面
      pageSize: 5, //分页 - 每页记录数
      projectStageList: [],
      project: {},
      task: {},
      taskStandardFileList: [],
      taskResultFileList: [],
      projectEmployeeList: [],
      nextChecker: "",
      taskCheckList: []
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    allowMe(menu) {
      var allowed = false;
      //任意一个角色不在黑名单就可以显示
      for (var i = 0; i < this.myRoles.length; i++) {
        var myRole = this.myRoles[i];
        if (menu.blackList.indexOf(myRole.group_role) == -1) {
          allowed = true;
          break;
        }
      }
      return allowed;
    },
    fmtDate(row, column, cellValue) {
      if (cellValue == null) return "";
      else return formatDate(new Date(cellValue), "yyyy-MM-dd hh:mm");
    },
    sendMsg() {
      this.$message("我要发布新消息了");
    },
    selectMyMessageList() {
      var _this = this;
      let params = {
        pageNo: 1,
        pageSize: 100000, //一个大的整数，其实就是说不分页
        filter: { to: _this.$store.state.loginUser.id, isExec: 0 }
      };
      SELECT_MESSAGE_PAGE_LIST(params).then(res => {
        if (res === "") {
          _this.$message({
            message: "获取消息列表失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.latestMsgList = res.rows;
        }
      });
    },
    selectHistoryMessageList() {
      var _this = this;
      let params = {
        pageNo: _this.pageNo,
        pageSize: _this.pageSize,
        filter: { to: _this.$store.state.loginUser.id, isExec: 1 }
      };
      SELECT_MESSAGE_PAGE_LIST(params).then(res => {
        if (res === "") {
          _this.$message({
            message: "获取消息列表失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.total = res.total;
          _this.historyMsgList = res.rows;
        }
      });
    },
    pageChange(pageNo) {
      this.pageNo = pageNo;
      this.selectHistoryMessageList();
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
    selectProject(projectId) {
      var _this = this;
      SELECT_PROJECT({ id: projectId }).then(res => {
        _this.project = res;
      });
    },
    selectProjectTask(taskId) {
      var _this = this;
      SELECT_PROJECT_TASK({ id: taskId }).then(res => {
        _this.task = res;
        _this.selectProjectEmployeeList(_this.task.projectId);
        _this.selectDocumentList(_this.task.id, "2");
        _this.selectDocumentList(_this.task.id, "3");
      });
    },
    selectProjectEmployeeList(projectId) {
      var _this = this;
      SELECT_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        _this.projectEmployeeList = res;
      });
    },
    selectTaskCheckList(taskId) {
      var _this = this;
      SELECT_TASK_CHECK_LIST({ taskId: taskId }).then(res => {
        _this.taskCheckList = res;
      });
    },
    selectDocumentList(projectTaskId, docCategory) {
      var _this = this;
      SELECT_DOCUMENT_LIST({
        belongTo: "task",
        sourceId: projectTaskId,
        category: docCategory
      }).then(res => {
        if (docCategory === "2") _this.taskStandardFileList = res;
        if (docCategory === "3") _this.taskResultFileList = res;
      });
    },
    downloadFile(row, event, column) {
      let link = document.createElement("a");
      link.href = base + "/download?docId=" + row.id;
      link.target = "_BLANK";
      link.click();
    },
    rowChange(row) {
      //更新消息isRead状态
      this.selectedMsg = row;
      if (row) {
        var _this = this;
        var params = Object.assign(row);
        if (row.isRead !== 1) {
          params.isRead = 1;
          UPDATE_MESSAGE(params).then(res => {
            if (res === "") {
              _this.$message({
                message: "更新消息状态失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              row.isRead = 1;
            }
          });
        }
      }
    },
    viewDetail(row) {
      this.selectedMsg = row;
      if (row.type === "audit") {
        this.dlgTitle = "审批项目计划";
        this.selectProjectStageList(row.relationId);
        this.selectProject(row.relationId);
        this.dlgAuditProjecyVis = true;
      } else {
        this.dlgTitle = "确认任务完成";
        this.selectProjectTask(row.relationId);
        this.dlgConfirmTaskVis = true;
        this.selectTaskCheckList(row.relationId);
      }
    },
    deleteMsg(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_MESSAGE({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectHistoryMessageList();
            _this.selectMyMessageList();
          });
        });
    },
    execMessage(exec, reason) {
      var _this = this;
      var params = Object.assign(_this.selectedMsg);
      params.isExec = exec;
      if (reason) params.note = reason;
      UPDATE_MESSAGE(params).then(res => {
        if (_this.selectedMsg.type === "audit") _this.dlgAuditProjecyVis = false;
        else _this.dlgConfirmTaskVis = false;
        _this.selectHistoryMessageList();
        _this.selectMyMessageList();
      });
    },
    refuseMessage(exec) {
      this.$prompt("请输入拒绝的理由", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
        this.execMessage(exec, value);
      });
    },
    forwardTask(exec) {
      this.execMessage(exec, this.nextChecker.id + "");
    },
    fmtEmployee(empId) {
      for (var emp of this.projectEmployeeList) {
        if (emp.id === empId) return emp.empName;
      }
      //return value;
    },
    fmtEmp(row, column, cellValue) {
      for (var emp of this.projectEmployeeList) {
        if (emp.id === cellValue) return emp.empName;
      }
    }
  },
  filters: {
    fmtDateFilter: function(value) {
      if (value == null) return "";
      else return formatDate(new Date(value), "yyyy-MM-dd");
    },
    fmtDateTimeFilter: function(value) {
      if (value == null) return "";
      else return formatDate(new Date(value), "yyyy-MM-dd hh:mm");
    }
  },
  watch: {
    isShow: {
      handler: function() {
        if(this.$props.isShow){
          this.selectMyMessageList();
          this.selectHistoryMessageList();
        }
      }
    }
  }
};
</script>

<style>

</style>
