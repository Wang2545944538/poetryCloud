<template>
  <div class="background_img">
    <p class="title">云墨诗苑</p>
    <p class="small_title">墨香流转千古韵，诗意盎然醉人间</p>

    <div class="box">
      <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" class="register-form">
        <h3>新用户注册</h3>
        <el-form-item  prop="nickname">
          <el-input v-model="registerForm.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item prop="username">
          <div class="input-with-message">
            <el-input
                v-model="registerForm.username"
                placeholder="请输入账号"
                @input="checkUsername"
            ></el-input>
            {{ message }}
          </div>

        </el-form-item>
        <el-form-item  prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item  prop="confirmPassword" >
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item  prop="phone">
          <div class="input-with-message">
          <el-input v-model="registerForm.phoneNumber" placeholder="请输入手机号" @input="checkPhoneNumber"></el-input>
          {{ message1 }}
          </div>
        </el-form-item>
        <el-form-item class="action-buttons">
          <el-button type="primary" @click="submitRegister" >注册</el-button>
          <router-link to="/login" class="login-link">已有账号？去登录</router-link>
        </el-form-item>
      </el-form>

    </div>

    <p class="foolter">© 2024 云墨诗苑京 ICP 证 110745 号京 ICP 备 13052560 号 - 1</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from "element-plus";
import axios from "@/utils/axios.js";
import {md5} from "js-md5";

const message = ref('');
const message1 = ref('');
// 表单数据模型
const registerForm = reactive({
  phoneNumber: '',
  nickname: '',
  username: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const registerRules = {
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '长度至少为 6 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        if (value === registerForm.password) {
          callback()
        } else {
          callback(new Error('两次输入的密码不一致'))
        }
      }, trigger: 'blur'
    }
  ]
}

// 表单引用
const registerFormRef = ref(null)
// 用户名是否已存在
const isUsernameTaken = ref(false);
//验证用户
async function checkUsername(){
  try {
    if (registerForm.username.trim() !== '') {
      // 验证用户名是否符合规则
      const isValid = validateUsername(registerForm.username);

      if (isValid) {
        const response = await axios.get('/users/checkUsername?username='+registerForm.username);
        const messageData = response.data;
        if (messageData === '该用户名已被注册') {
          message.value = messageData;
          isUsernameTaken.value = true;
        } else if (messageData === '用户名不存在') {
          message.value = '用户名可用';
          isUsernameTaken.value = false;
        } else {
          message.value = messageData || '未知错误';
          isUsernameTaken.value = false;
        }
      } else {
        message.value = '用户名不符合规则';
        isUsernameTaken.value = false;
      }
    } else {
      message.value = '';
      isUsernameTaken.value = false;
    }
  } catch (error) {
    console.error('Error checking username:', error);
    message.value = '检查用户名失败，请稍后再试。';
    isUsernameTaken.value = false;
  }
}
//验证电话号码
async function checkPhoneNumber(){
  try {
    if (registerForm.phoneNumber.trim() !== '') {
      // 验证用户名是否符合规则
      const isValid = validatePhoneNumber(registerForm.phoneNumber);

      if (isValid) {
        const response = await axios.get('/users/checkPhoneNumber?phoneNumber='+registerForm.phoneNumber);
        const messageData = response.data;
        if (messageData === '该电话号码已被注册') {
          message1.value = messageData;
          isUsernameTaken.value = true;
        } else if (messageData === '电话号码不存在') {
          message1.value = '电话号码可用';
          isUsernameTaken.value = false;
        } else {
          message1.value = messageData || '未知错误';
          isUsernameTaken.value = false;
        }
      } else {
        message1.value = '电话号码不符合规则';
        isUsernameTaken.value = false;
      }
    } else {
      message1.value = '';
      isUsernameTaken.value = false;
    }
  } catch (error) {
    console.error('Error checking phone:', error);
    message1.value = '检查电话号码失败，请稍后再试。';
    isUsernameTaken.value = false;
  }
}
// 验证用户名是否符合规则
function validateUsername(username) {
  const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
  return usernameRegex.test(username);
}
function validatePhoneNumber(phoneNumber) {
  const usernameRegex = /^1[3-9]\d{9}$/;
  return usernameRegex.test(phoneNumber);
}
const submitRegister=()=>{
  if (isUsernameTaken.value) {
    ElMessage.error('该用户名或电话号码已被注册，请尝试其他用户名');
    isUsernameTaken.value=false;
    return;
  }
  registerForm.password = md5(registerForm.password)
  axios.post("/users/register",registerForm).then(res=>{
    if (res.code === 200){
      ElMessage({
        message:"注册成功",
        type:"success",
      })
      window.location.href = '/login'
    }else {
      ElMessage({
        message:"注册失败",
        type:"error",
      })
      location.reload()
    }
  })
}

</script>

<style scoped>
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.background_img {
  background-image: url(/user/images/img/login.png);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
}

.title {
  font-size: 65px;
  font-family: 'MyCustomFont', sans-serif;
  color: transparent;
  /* 使文字颜色透明 */
  background-image: linear-gradient(to right, rgba(219, 140, 66, 0.927), rgba(255, 0, 0, 0.927));
  /* 设置渐变色背景 */
  -webkit-background-clip: text;
  /* 背景仅应用于文本 */
  background-clip: text;
  display: block;
  /* 使用块级元素确保可以居中 */
  text-align: center;
  /* 确保文本居中 */
  margin-bottom: 0;
  transition: transform 0.3s ease;
}
.title:hover{
  transform: scale(1.05);

}
.small_title {
  color: rgba(237, 73, 32, 0.927);
  font-size: 14px;
  font-family: 'MyCustomFont', sans-serif;
  text-align: center;
}

.box {
  background-size: cover;
  background-position: center;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.register-form {
  width: 300px;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2)

}
.register-form {
  width: 300px;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);

  display: flex;
  flex-direction: column;
  align-items: center;
}


.left_box p {
  font-family: 'MyCustomFont', sans-serif;
}

.left_box_img img {
  width: 150px;
  height: 150px;
  transition: transform 0.3s ease;
}
.left_box_img img:hover {
  transform: scale(1.05);
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.foolter {
  font-family: 'MyCustomFont', sans-serif;
  color: rgb(53, 53, 53);
}
.right_box_smallbox p{
  width: 35px;
  height: 35px;
  border-radius: 20px;
  background-color: rgb(237, 239, 239);
  display: flex;
  align-items: center;
  justify-content: space-around;
  transition: transform 0.3s ease;
}
.right_box_smallbox p:hover {
  transform: scale(1.05)
}
.right_box_smallbox p img{

  width: 80%;
  height: 80%;
}
.login-form {
  width: 60%;
  margin: 0 auto;
}
.action-buttons {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  justify-content: center; /* 垂直居中 */
  text-align: center; /* 文本水平居中 */

}

.action-buttons .el-button {
  width: 100%; /* 按钮宽度占满 */
  margin-bottom: 10px; /* 按钮下方的间距 */

}

.action-buttons .login-link {
  margin-top: 0;
  margin-left: 10%;
  font-size: 0.9em;
  text-decoration: none;
  color: #409eff;
  display: block;
  margin-left: 10%
}
/* .message {

} */
.input-with-message {
  display: flex;
  flex-direction: column; /* 默认为垂直方向 */
  color: #ff4d4f; /* 提示信息颜色 */
  font-size: 0.8em; /* 字体大小 */
}





</style>