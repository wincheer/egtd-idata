<template>
  <scheduler :events="events" class="scheduler" 
    @onBeforeLightbox="onBeforeLightbox" 
    @onQuickInfo="onQuickInfo"
    @onBeforeEventChanged="onBeforeEventChanged" >
  </scheduler>
</template>

<script>
import Scheduler from "@/component/Scheduler.vue";
export default {
  components: { Scheduler },
  data() {
    return {
      events: [
        {
          id: 1,
          text: "项目周例会",
          start_date: "2017-11-27 09:00",
          end_date: "2017-11-27 12:00"
        },
        {
          id: 2,
          text: "与厂商沟通设计方案需求",
          start_date: "2017-11-28 14:00",
          end_date: "2017-11-28 16:00"
        },
        {
          id: 3,
          text: "面试新员工",
          start_date: "2017-11-28 16:00",
          end_date: "2017-11-28 17:00"
        },
        {
          id: 4,
          text: "局领导项目进度汇报材料",
          start_date: "2017-11-29 9:00",
          end_date: "2017-11-30 17:00"
        },
        {
          id: 5,
          text: "为离职的同事送行",
          start_date: "2017-11-09 12:00",
          end_date: "2017-11-09 14:00"
        }
      ]
    };
  },
  methods: {
    onQuickInfo(evt) {
      this.$message("onQuickInfo = " + evt.text);
    },
    onBeforeLightbox(evt) {
      this.$message("onBeforeLightbox = " + evt.text);
    },
    onBeforeEventCreated(evt) {
      this.$message("onBeforeEventCreated = " + evt.text);
    },
    onBeforeEventChanged(ev, e, is_new, ori) {
      this.$message("onBeforeEventChanged," + ori.text+",is_new = "+ is_new);
    }
  },
  mounted() {}
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
