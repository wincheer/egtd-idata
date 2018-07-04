<template>
  <el-dialog :title="'【'+parentTask.text+'】批量添加子任务'" :visible.sync="isShow" width="480px" :close-on-click-modal="false" :before-close="closeBatchTaskAddForm">
    <el-input
        type="textarea"
        :autosize="{ minRows: 4, maxRows: 10}"
        placeholder="请输入新任务名称，每行一个任务"
        v-model="taskNames" />
    <span slot="footer" class="dialog-footer">
        <el-button @click="closeBatchTaskAddForm">取消</el-button>
        <el-button type="primary" @click="batchTaskAdd">批量添加</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { BATCH_TASK_INSERT } from "@/config/api";

export default {
  name: "batch-task-add-form",
  props: {
    isShow: false,
    parentTask: {}
  },
  data() {
    return {
      taskNames: ""
    };
  },
  methods: {
    fmtDate(timestamp) {
      return formatDate(new Date(timestamp), "yyyy-MM-dd hh:mm");
    },
    closeBatchTaskAddForm() {
      this.taskNames = "";
      this.$emit("close");
    },
    batchTaskAdd() {
      var _this = this;
      //调用服务器端程序，批量新建任务
      var params = {
        text: this.taskNames,
        projectId: this.$props.parentTask.projectId,
        parent:this.$props.parentTask.id,
        assignStaffId: this.$store.state.loginUser.id,
        priority: 60
      };
      BATCH_TASK_INSERT(params).then(res => {
        _this.$message({ message: "批量添加任务成功", type: "success" });
        _this.$emit("close");
      });
    }
  },
  mounted() {},
  watch: {
    isShow: {
      handler: function() {
        if (this.$props.isShow) {
          //
        }
      },
      deep: false //如果不能正常更新,改为true
    }
  }
};
</script>

<style>

</style>

