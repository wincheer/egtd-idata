<template>
  <section>
    <scheduler :events="events" class="scheduler" @onBeforeLightbox="openTaskEdit"/>
    <!--任务编辑对话框-->
    <task-edit-form :task="selectedTask" :isShow="taskFormVis" @close="closeTaskForm"></task-edit-form>
  </section>
</template>

<script>
import Scheduler from "@/component/Scheduler.vue";
import TaskEditForm from "@/component/TaskEditForm.vue";
import { SELECT_TASK_LIST } from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  components: { Scheduler, TaskEditForm },
  data() {
    return {
      events: [],
      selectedTask: [],
      taskFormVis: false
    };
  },
  methods: {
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd hh:mm");
    },
    closeTaskForm(){
      this.taskFormVis = false;
      this.selectMyTaskList();
    },
    openTaskEdit(evt) {
      this.selectedTask = evt;
      this.taskFormVis = true;
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
