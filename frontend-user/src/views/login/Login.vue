<template>
  <div class="background_img">
    <p class="title">云墨诗苑</p>
    <p class="small_title">墨香流转千古韵，诗意盎然醉人间</p>
    <div class="box">
      <div class="left_box">
        <p class="left_box_title1">打开云墨诗苑App</p>
        <p>在「个人主页」右上角打开扫一扫</p>
        <div class="left_box_img"><img src="/images/img/2021557FD3F170EBECB34AFD3CA8D65C.jpg" alt=""></div>
        <p>其他扫码方式：微信</p>
        <div>
          <el-button>下载云墨诗苑App</el-button>
          <el-button>前往小程序</el-button>
        </div>
      </div>
      <div class="right_box">
        <p>密码登录</p>
        <el-form label-position="top" :rules="state.rules" :model="state.ruleForm" ref="loginForm" class="login-form">
          <el-form-item label="账号" prop="username" class="flex-container">
            <el-input type="text" v-model.trim="state.ruleForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" class="flex-container">
            <el-input type="password" v-model.trim="state.ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button style="width: 100%" type="primary" @click="login">立即登录</el-button>
          </el-form-item>
        </el-form>
        <p>其他方式登录 <router-link to="/register" class="login-link">注册</router-link></p>
        <div class="right_box_smallbox">
          <p><img src="/images/icon/微信.png" alt=""></p>
          <p><img src="/images/icon/qq.png" alt=""></p>
          <p><img src="/images/icon/微博.png" alt=""></p>
        </div>
      </div>
    </div>

    <p class="foolter">© 2024 云墨诗苑京 ICP 证 110745 号京 ICP 备 13052560 号 - 1</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from "element-plus";
import axios from "@/utils/axios.js";
import {md5} from "js-md5";
import {localSet} from "@/utils/index.js";

const loginForm = ref(null)
const state = reactive({
  ruleForm: {
    username: '',
    password: ''
  },
  checked: true,
  rules: {
    username: [
      { required: 'true', message: '账户不能为空', trigger: 'blur' }
    ],
    password: [
      { required: 'true', message: '密码不能为空', trigger: 'blur' }
    ]
  },

})
const login=() =>{
  axios.post("/users/login",{
    username: state.ruleForm.username || '',
    password: md5(state.ruleForm.password)
  }).then(res => {
    console.log(res.data)
    if (res.code === 200) {
      localSet('token', res.data)
      const decoded = parseJwt(res.data);
      // 获取sub字段的值
      const sub = decoded.sub;
      const subObj = JSON.parse(sub.replace(/'/g, '"')); // 替换单引号为双引号
      //获取头像地址
      const avatar = subObj.avatar;
      localSet('avatar',avatar)
      window.location.href = '/'
    } else {
      ElMessage({
        showClose: true,
        message: "账号或密码错误",
        duration: 2000,
        type: "error",
      })
    }
  })
}
//解析token
function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace('-', '+').replace('_', '/');
  var payload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
  return JSON.parse(payload);
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
  width: 750px;
  height: 550px;
  background-color: white;
  display: flex;
  align-items: center;
}

.left_box {
  width: 45%;
  height: 80%;
  border-right: 1px solid rgb(186, 184, 184);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
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
.right_box{
  font-family: 'MyCustomFont', sans-serif;
  width: 55%;
  height: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
.foolter {
  font-family: 'MyCustomFont', sans-serif;
  color: rgb(53, 53, 53);
}
.right_box_smallbox{
  width: 80%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
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

.flex-container {
  display: flex;
  align-items: center;
}
.flex-container .el-form-item__label {
  margin-right: 10px;
}
.login-link {
  font-size: 0.9em;
  text-decoration: none;
  color: #409eff;

}
</style>