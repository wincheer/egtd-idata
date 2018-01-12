<template>
    <div>
        <el-row type="flex"  justify="center" align="middle">
            <el-col :span="16">
                <img alt="正在建设中" :src="doingIcon" width="100%" height="100%">
            </el-col>
        </el-row>
    </div>
</template>
<script>
import doing from "../assets/doing.jpg"
export default {
  data(){
      return{
          doingIcon:doing
      }
  }
}
</script>


