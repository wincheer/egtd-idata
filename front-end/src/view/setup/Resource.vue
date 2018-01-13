<template>
  <el-tabs type="card" v-model="activeName" @tab-click="handleClick" style="margin-top: 20px;">
    <el-tab-pane name="first">
       <span slot="label"><i class="el-icon-service"></i> 业主组织结构</span>
      用户管理
    </el-tab-pane>
    <el-tab-pane name="second">
      <span slot="label"><i class="el-icon-menu"></i> 供应商资源管理</span>
      配置管理
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default { 
    props:{},
    data() {
      return{
        activeName: 'second'
      }
    }, 
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    },
    watch:{},
    computed:{},
    mounted() {}
};
</script>

<style>

</style>
