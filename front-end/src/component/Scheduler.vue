<template>
  <div ref="scheduler" class="dhx_cal_container">
      <div class="dhx_cal_navline">
        <div class="dhx_cal_prev_button">&nbsp;</div>
        <div class="dhx_cal_next_button">&nbsp;</div>
        <div class="dhx_cal_today_button"></div>
        <div class="dhx_cal_date"></div>
        <div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>
        <div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>
        <div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
      </div>
      <div class="dhx_cal_header">
      </div>
      <div class="dhx_cal_data">
      </div>
	</div>
</template>

<script>
import "dhtmlx-scheduler";
import "dhtmlx-scheduler/codebase/locale/locale_cn.js";
import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_recurring.js";
import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_quick_info.js";

export default {
  name: "schedule",
  props: {
    events: {}
  },
  methods: {},
  mounted() {
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.details_on_create = true;
    scheduler.config.details_on_dblclick = true;
    //scheduler.config.quick_info_detached = false;
    scheduler.config.show_quick_info = false;

    scheduler.attachEvent("onQuickInfo", id => {
      let evt = scheduler.getEvent(id);
      this.$emit("onQuickInfo", evt);
    });
    scheduler.attachEvent("onBeforeLightbox", id => {
      let evt = scheduler.getEvent(id);
      this.$emit("onBeforeLightbox", evt);
    });
    scheduler.attachEvent("onBeforeEventCreated", evt => {
      this.$emit("onBeforeEventCreated", evt);
    });
    scheduler.attachEvent("onBeforeEventChanged", (ev, e, is_new, original) => {
      this.$emit("onBeforeEventChanged", ev, e, is_new, original);
    });
    scheduler.attachEvent("onEventCollision", (ev, evs)=> {
      this.$emit("onEventCollision", ev, ev);
    });

    scheduler.init(this.$refs.scheduler, new Date(), "month");
    scheduler.parse(this.$props.events, "json");
  }
};
</script>

<style>
@import "~dhtmlx-scheduler/codebase/dhtmlxscheduler.css";
html,
body {
  margin: 0px;
  padding: 0px;
  height: 100%;
  overflow: hidden;
}
</style>