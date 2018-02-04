<template>
  <div ref="gantt" ></div>
</template>

<script>
import "dhtmlx-gantt";
import "dhtmlx-gantt/codebase/locale/locale_cn.js";

export default {
  name: "gantt",
  props: {
    tasks: {
      type: Object,
      default() {
        return { data: [], links: [] };
      }
    }
  },
  methods: {
    myFunc: function(task) {
      if (task.priority == 1)
        return (
          "<div style='color: #d96c49;font-weight:bold;'>" +
          task.text +
          "</div>"
        );
      return task.text;
    }
  },
  mounted() {
    gantt.attachEvent("onTaskSelected", id => {
      let task = gantt.getTask(id);
      let parentTask = {}
      if(task.parent!==0) parentTask = gantt.getTask(task.parent);
      this.$emit("task-selected", task, parentTask);
    });
    gantt.attachEvent("onTaskUnselected", id => {
      let task = gantt.getTask(id);
      this.$emit("task-unselected", task);
    });

    gantt.attachEvent("onAfterTaskAdd", (id, task) => {
      this.$emit("task-updated", id, "inserted", task);
      task.progress = task.progress || 0;
      if (gantt.getSelectedId() == id) {
        this.$emit("task-selected", task);
      }
    });

    gantt.attachEvent("onAfterTaskUpdate", (id, task) => {
      this.$emit("task-updated", id, "updated", task);
    });

    gantt.attachEvent("onAfterTaskDelete", id => {
      this.$emit("task-updated", id, "deleted");
      if (!gantt.getSelectedId()) {
        this.$emit("task-selected", null);
      }
    });

    gantt.attachEvent("onAfterLinkAdd", (id, link) => {
      this.$emit("link-updated", id, "inserted", link);
    });

    gantt.attachEvent("onAfterLinkUpdate", (id, link) => {
      this.$emit("link-updated", id, "updated", link);
    });

    gantt.attachEvent("onAfterLinkDelete", (id, link) => {
      this.$emit("link-updated", id, "deleted");
    });

    gantt.attachEvent("onBeforeLightbox", id => {
      let task = gantt.getTask(id);
      this.$emit("lightbox-open", id, task);
    });
    gantt.attachEvent("onAfterLightbox", () => {
      this.$emit("lightbox-close");
    });

    // gantt.config.xml_date = "%Y-%m-%d %H:%i";
    gantt.config.xml_date = "%Y-%m-%d";

    //配合样式表，突出显示非工作日
    gantt.templates.scale_cell_class = function(date) {
      if (date.getDay() == 0 || date.getDay() == 6) {
        return "weekend";
      }
    };
    gantt.templates.task_cell_class = function(item, date) {
      if (date.getDay() == 0 || date.getDay() == 6) {
        return "weekend";
      }
    };
    //配合样式表，自定义任务的外观（根据任务类型）--甚至可以根据条件来动态改变外观
    gantt.templates.task_class = function(start, end, task) {
      switch (task.priority) {
        case 100:
          return "high";
          break;
        case 80:
          return "high";
          break;
        case 60:
          return "medium";
          break;
        case 40:
          return "low";
          break;
        case 20:
          return "low";
          break;
      }
    };
    //配合样式表，定义任务行颜色：白色进行中，红色为开始，绿色已完成
    gantt.templates.task_row_class = function(start_date, end_date, item) {
      if (item.progress == 0) return "red";
      if (item.progress >= 1) return "green";
    };
    //配置左侧列，默认显示任务名称、开始时间、持续时间
    gantt.config.columns = [
      {
        name: "wbs",
        label: "WBS",
        width: 50,
        resize: true,
        template: gantt.getWBSCode
      },
      {
        name: "text",
        label: "任务名称",
        width: "240",
        resize: true,
        tree: true,
        width: "*",
        template: this.myFunc
      },
      {
        name: "start_date",
        label: "开始日期",
        width: "80",
        resize: true,
        align: "center",
      },
      {
        name: "progress",
        label: "进度",
        width: "60",
        resize: true,
        align: "center",
        template: function(item) {
          if (item.progress >= 1) return "完成";
          if (item.progress == 0) return "未开始";
          return Math.round(item.progress * 100) + "%";
        }
      },
      // {
      //   name:"add", width:40
      // }
    ];

    gantt.config.autosize = "y";
    gantt.config.readonly = true;
    gantt.config.drag_resize = false;
    gantt.config.drag_progress = false;
    gantt.config.drag_move = false;
    gantt.config.drag_links = false;
    //gantt.config.show_chart = false; 不显示 gantt 图
    gantt.config.root_id = 0; 
    gantt.config.open_tree_initially = true; //展开全部
    gantt.init(this.$refs.gantt);
    gantt.parse(this.$props.tasks);
  },
  watch: {
    tasks: {
        handler: function () {
          gantt.clearAll(); 
          gantt.parse(this.$props.tasks);
        },
        deep: true
    }
  }
};
</script>

<style>
@import "~dhtmlx-gantt/codebase/dhtmlxgantt.css";
html,
body {
  height: 100%;
  padding: 0px;
  margin: 0px;
  overflow: hidden;
}
.red .gantt_cell,
.odd.red .gantt_cell,
.red .gantt_task_cell,
.odd.red .gantt_task_cell {
  background-color: #fde0e0;
}
.green .gantt_cell,
.odd.green .gantt_cell,
.green .gantt_task_cell,
.odd.green .gantt_task_cell {
  background-color: #bee4be;
}
.high {
  border: 2px solid #d96c49;
  color: #d96c49;
  background: #d96c49;
}
.high .gantt_task_progress {
  background: #db2536;
}

.medium {
  border: 2px solid #34c461;
  color: #34c461;
  background: #34c461;
}
.medium .gantt_task_progress {
  background: #23964d;
}

.low {
  border: 2px solid #6ba8e3;
  color: #6ba8e3;
  background: #6ba8e3;
}
.low .gantt_task_progress {
  background: #547dab;
}
.weekend {
  background: #f4f7ff;
}
/* 左侧背景 */
/* .gantt_row .gantt_cell:nth-child(1) {
  background-color: gray;
} */
.gantt_row .gantt_cell:nth-child(1) {
  background-color: orange;
}
</style>