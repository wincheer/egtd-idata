<template>
  <div ref="scheduler" class="dhx_cal_container">
      <div class="dhx_cal_navline">
        <div class="dhx_cal_prev_button">&nbsp;</div>
        <div class="dhx_cal_next_button">&nbsp;</div>
        <div class="dhx_cal_today_button"></div>
        <div class="dhx_cal_date"></div>
        <div class="dhx_cal_tab" name="day_tab"></div>
        <div class="dhx_cal_tab" name="week_tab"></div>
        <div class="dhx_cal_tab" name="month_tab"></div>
        <!-- <div class="dhx_cal_tab" name="year_tab"></div> -->
        <div class="dhx_cal_tab" name="agenda_tab"></div>
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
import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_year_view.js";
import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_agenda_view.js";
import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_tooltip.js";
// import "dhtmlx-scheduler/codebase/ext/dhtmlxscheduler_grid_view.js";

export default {
  name: "scheduler",
  props: {
    events: {}
  },
  methods: {},
  mounted() {
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
    scheduler.attachEvent("onEventCollision", (ev, evs) => {
      this.$emit("onEventCollision", ev, ev);
    });

    // 任务提示
    var format = scheduler.date.date_to_str("%Y-%m-%d %H:%i");
    scheduler.templates.tooltip_text = function(start, end, event) {
      return (
        "<b>任务:</b> " +
        event.text +
        "<br/><b>开始时间:</b> " +
        format(start) +
        "<br/><b>结束时间:</b> " +
        format(end)
      );
    };

    //根据任务的属性使用不同的样式表
    scheduler.templates.event_class = function(start, end, event) {
      var css = "";

      if (event.isDelay === 0) {
        if (event.state === 0) css = "init_event";
        else if (event.state === 1) css = "process_event";
        else if (event.state === 2) css = "wait_event";
        else if (event.state === 3) css = "finished_event";
      } else css = "delay_event";

      return css;
    };

    scheduler.locale.labels.year_tab = "年";
    scheduler.locale.labels.agenda_tab = "列表";
    scheduler.config.agenda_start = new Date(2000, 1, 1);
    scheduler.config.agenda_end = new Date(2030, 12, 31);

    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.autosize = "y";
    scheduler.config.details_on_create = true;
    scheduler.config.details_on_dblclick = true;
    //scheduler.config.quick_info_detached = false;
    scheduler.config.show_quick_info = false;

    // scheduler.config.readonly = true;
    scheduler.config.drag_resize = false;
    scheduler.config.drag_progress = false;
    scheduler.config.drag_move = false;

    scheduler.init(this.$refs.scheduler, new Date(), "month");
    scheduler.parse(this.$props.events, "json");
  },
  watch: {
    events: {
      handler: function() {
        scheduler.clearAll();
        scheduler.parse(this.$props.events, "json");
      },
      deep: false //如果不能正常更新,改为true
    }
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

/*event in day or week view*/
.dhx_cal_event.manager_event div {
  background-color: #009966 !important;
  color: black !important;
}
.dhx_cal_event.employee_event div {
  background-color: #ff9933 !important;
  color: black !important;
}

/*multi-day event in month view*/
.dhx_cal_event_line.init_event {
  background-color: #e7e7e7 !important;
  color: black !important;
}
.dhx_cal_event_line.process_event {
  background-color: #0e61fa !important;
  color: black !important;
}
.dhx_cal_event_line.wait_event {
  background-color: #78ce9c !important;
  color: white !important;
}
.dhx_cal_event_line.finished_event {
  background-color: #1b7013 !important;
  color: rgb(255, 255, 255) !important;
}
.dhx_cal_event_line.delay_event {
  background-color: #f16609 !important;
  color: rgb(255, 255, 255) !important;
}

/*event with fixed time, in month view*/
.dhx_cal_event_clear.manager_event {
  color: black !important;
}
.dhx_cal_event_clear.employee_event {
  color: black !important;
}
</style>