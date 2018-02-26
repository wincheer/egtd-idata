<template>
  <el-dialog title="修改密码" :close-on-click-modal="false" :visible.sync="isShow" width="20%" :before-close="closeDialog">
    <el-form :model="pwdObj" :rules="pwdObjRules" label-width="100px" ref="pwdForm">
        <el-form-item label="原密码：" prop="oldPwd">
        <el-input type="password" v-model="pwdObj.oldPwd" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="新密码：" prop="newPwd">
        <el-input v-model="pwdObj.newPwd" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="重输新密码：" prop="newSecPwd">
        <el-input v-model="pwdObj.newSecPwd" style="width:90%"></el-input>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click.native="closeDialog">取消</el-button>
        <el-button type="primary" @click="updatePwd">提交</el-button>
    </div>
    </el-dialog>
</template>

<script>
import { UPDATE_EMPLOYEE } from "@/config/api";
export default {
  name: "change-pwd",
  props: {
    isShow: false
  },
  data() {
    return {
      pwdObj: {
        oldPwd: "",
        newPwd: "",
        newSecPwd: ""
      },
      pwdObjRules: {
        oldPwd: [{ required: true, message: "请输入原密码", trigger: "blur" }],
        newPwd: [{ required: true, message: "请输入新密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    updatePwd: function() {
      var _this = this;
      _this.$refs.pwdForm.validate(valid => {
        if (valid) {
          if (_this.pwdObj.oldPwd != _this.$store.state.loginUser.password) {
            _this.$message({
              message: "原密码错误，重新输入！",
              type: "error"
            });
          } else {
            if (_this.pwdObj.newPwd != _this.pwdObj.newSecPwd) {
              _this.$message({
                message: "新密码两次输入的不一致，重新输入！",
                type: "warning"
              });
            } else {
              let para = {
                id: _this.$store.state.loginUser.id,
                password: _this.pwdObj.newPwd
              };
              UPDATE_EMPLOYEE(para).then(res => {
                _this.$message({
                  message: "更新密码成功，下次请用新密码登录",
                  type: "success"
                });
                _this.pwdObj = { oldPwd: "", newPwd: "", newSecPwd: "" };
                _this.closeDialog() ;
              });
            }
          }
        } else {
          return false;
        }
      });
    }
  },
  watch: {
    isShow: {
      handler: function() {}
    }
  }
};
</script>

<style>

</style>
