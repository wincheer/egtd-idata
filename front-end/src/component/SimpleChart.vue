<template>
  <ve-pie :data="chartData"></ve-pie>
</template>

<script>
import { SELECT_TASK_STATE_COUNT_LIST } from "@/config/api";
export default {
  name: "simple-chart",
  props: {
      taskParams:{}
  },
  data() {
    return {
      chartData: {
        columns: ["state", "state_count"],
        rows: []
      },
      foo: "bar"
    };
  },
  methods: {
    selectChartData(param) {
      var _this = this
      SELECT_TASK_STATE_COUNT_LIST(param).then(res=>{
          _this.chartData.rows = res;
      })
    }
  },
  mounted() {
  },
  watch: {
    taskParams: {
      handler: function() {
          this.selectChartData(this.$props.taskParams);
      },
      deep: false //如果不能正常更新,改为true
    }
  }
};
</script>

<style>

</style>
