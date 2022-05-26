<template>
  <div style="width: 100%; height: 100vh; background-color: darkslateblue; overflow: hidden">
    <div style="width: 400px; margin: 150px auto" >
      <div style="color: #cccccc; font-size: 30px; text-align: center">欢迎注册</div>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item prop="username">
          <el-input  v-model="form.username" placeholder="请输入用户名">
            <template #prefix>
              <el-icon class="el-input__icon"><user-filled /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input v-model="form.password" placeholder="请输入密码" show-password >
            <template #prefix>
              <el-icon class="el-input__icon"><lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input v-model="form.confirm" placeholder="请确认密码" show-password >
            <template #prefix>
              <el-icon class="el-input__icon"><lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item >
          <el-button style="width: 40%" type="primary" @click="register">注 册</el-button>
          <el-button style="width: 40%" type="primary" @click="back">返 回</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script >
import { UserFilled, Lock } from '@element-plus/icons-vue'
import request from "@/utils/request";
export default {
  name: "RegisterView",
  components: {
    UserFilled, Lock
  },
  data() {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirm: [
          { required: true, message: '请再次确认密码', trigger: 'blur' },
        ],
      }
    }
  },
  methods: {
    register() {

      if (this.form.password !==  this.form.confirm) {
        this.$message({
          type: "error",
          message: '2次密码输入不一致'
        })
        return
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/register", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功，请登录!"
              })
              this.$router.push("/login")  //跳转至登录页码
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    },
    back() {
      this.$router.push("/login")  //返回至登录页码
    }
  }
}
</script>

<style scoped>

</style>