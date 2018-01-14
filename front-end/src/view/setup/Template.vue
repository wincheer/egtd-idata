<template>
  <section>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>项目模板</span>
            <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">项目模板</el-button>
          </div>
          foo
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>模板项目阶段</span>
            <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">模板项目阶段</el-button>
          </div>
          foo
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>模板项目组</span>
            <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">模板项目组</el-button>
          </div>
          foo
        </el-card>
      </el-col>
    </el-row>
  </section>
</template>

<script>
export default { 
    props:{},
    data() {
      return{
        foo:"页面模板在这里，更多内容"
      }
    }, 
    methods: {},
    watch:{},
    computed:{},
    mounted() {}
};
</script>

<style>

</style>
