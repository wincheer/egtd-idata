<template>
    <section>
      <el-row style="margin-top: 20px;margin-bottom: 10px;">
        <!-- <el-col></el-col> -->
        <!-- <el-select v-model="projectObj.category" placeholder="请选择项目"></el-select> -->
        <el-select v-model="selectedProject" placeholder="请选择项目">
          <el-option v-for="item in myProjectList" :key="item.id" :label="item.projectName" :value="item.id" />
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
import {SELECT_MY_PROJECT_LIST} from "@/config/api";
export default {
  components: { Gantt },
  data() {
    return {
      tasks: {
        data: [
          { id: 1, text: "黄浦区公安分局视频项目", start_date: "2015-04-01", open: true },
          {
            id: 2,
            text: "项目启动",
            start_date: "2015-04-01",
            duration: "1",
            order: "10",
            progress: 1.0,
            parent: "1",
            open: true,
            priority:"1"
          },
          {
            id: 3,
            text: "前期调研",
            start_date: "2015-04-02",
            duration: "8",
            order: "20",
            parent: "1",
            progress: 1.0,
            open: true,
            priority: "2"
          },

          {
            id: 4,
            text: "立项资金落实",
            start_date: "2015-04-02",
            duration: "20",
            order: "30",
            parent: "1",
            progress: 1.0,
            open: true
          },
          {
            id: 5,
            text: "设计/监理单位",
            start_date: "2015-04-10",
            duration: "10",
            order: "30",
            parent: "1",
            progress: 1.0,
            open: false
          },
          {
            id: 501,
            text: "招标采购",
            start_date: "2015-04-10",
            duration: "7",
            order: "30",
            parent: "5",
            progress: 1.0,
            open: true
          },
          {
            id: 502,
            text: "合同签订",
            start_date: "2015-04-17",
            duration: "3",
            order: "30",
            parent: "5",
            progress: 1.0,
            open: true
          },
          {
            id: 6,
            text: "初步设计评审",
            start_date: "2015-04-20",
            duration: "2",
            order: "30",
            parent: "1",
            progress: 1.0,
            open: true
          },
          {
            id: 7,
            text: "承建（运维）单位",
            start_date: "2015-04-22",
            duration: "6",
            order: "30",
            parent: "1",
            progress: 1.0,
            open: false
          },
          {
            id: 701,
            text: "招标采购",
            start_date: "2015-04-22",
            duration: "5",
            order: "30",
            parent: "7",
            progress: 1.0,
            open: true
          },
          {
            id: 702,
            text: "合同签订",
            start_date: "2015-04-27",
            duration: "1",
            order: "30",
            parent: "7",
            progress: 1.0,
            open: true
          },
          {
            id: 8,
            text: "深化设计评审",
            start_date: "2015-04-28",
            duration: "2",
            order: "30",
            parent: "1",
            progress: 1.0,
            open: true
          },
          {
            id: 9,
            text: "施工建设",
            start_date: "2015-05-01",
            duration: "30",
            order: "30",
            parent: "1",
            progress: 0.8,
            open: true
          },
          {
            id: 10,
            text: "用户验收",
            start_date: "2015-06-01",
            duration: "5",
            order: "30",
            parent: "1",
            progress: 0.0,
            open: true
          },
          {
            id: 11,
            text: "第三方检测",
            start_date: "2015-06-06",
            duration: "3",
            order: "30",
            parent: "1",
            progress: 0.0,
            open: true
          },
          {
            id: 12,
            text: "技防验收",
            start_date: "2015-06-09",
            duration: "2",
            order: "30",
            parent: "1",
            progress: 0.0,
            open: true
          },
          {
            id: 13,
            text: "终验",
            start_date: "2015-06-11",
            duration: "6",
            order: "30",
            parent: "1",
            progress: 0.0,
            open: true
          }
        ],
        links: []
      },
      myProjectList:[],
      selectedProject: {},
      selectedTask: {},
      lightBoxVisible: false,
      isLightBoxActive:false
    };
  },
  methods: {
    selectMyProjectList(staffCode){
      var _this = this;
      SELECT_MY_PROJECT_LIST({ code: staffCode }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取我的项目列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.myProjectList = res;
          if(res.length) this.selectedProject = _this.myProjectList[0];
        }
      });
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
    }
  },
  mounted() {
    this.selectMyProjectList(this.$store.state.loginUser.code);
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
