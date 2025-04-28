<script setup>
import { ref } from 'vue';
const title = ref(''); // 初始化标题的响应式引用
const content = ref(''); // 初始化内容的响应式引用
import  axios  from '@/utils/axios'
import {setSTSToken} from "ali-oss/lib/common/utils/setSTSToken.js";

const addW = () => {
  // 验证输入
  if (!title.value || !content.value) {
    alert('标题和内容都是必填项。');
    return;
  }

  // 发送POST请求
  axios.post('/poem/addMyPoem', {
    title: title.value,
    content: content.value
  }, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    }
  })
      .then(response => {
        // 处理成功响应
        console.log('提交的数据:', {
          title: title.value,
          content: content.value
        });
        // 清空输入框
        title.value = '';
        content.value = '';
        // 例如，您可以在这里添加逻辑来处理成功的响应，例如导航到另一个页面
        window.location.href = '/write#/write'; // 直接跳转到指定页面
      })
      .catch(error => {
        // 处理错误响应
        console.error('Error submitting the form:', error);
        // 您可以在这里添加逻辑来处理错误，例如显示错误消息
        alert('提交诗词时出错。');
      });
};

const response = ref(null); // 初始化响应的响应式引用

</script>

<template>
  <body>
    <div class="container">
      <div class="tit">墨韵流淌</div>
      <input v-model="title" placeholder="标题:">
      <textarea v-model="content" placeholder="内容:" rows="4" cols="50"></textarea>
      <el-button @click="addW()">提交</el-button>
    </div>
  </body>
</template>

<style scoped>
textarea {
  font-family: Arial, sans-serif; /* 设置字体 */
  border: 1px solid #ccc; /* 设置边框样式 */
  padding: 2px; /* 设置内边距 */
  resize: vertical; /* 允许垂直方向调整大小 */
  width: 280px; /* 设置宽度 */
  height: 100px; /* 设置高度 */
}

*{
  /* 初始化 */
  margin: 0;
  padding: 0;
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg,#e3c5eb,#a9c1ed);
  /* 溢出隐藏 */
  overflow: hidden;
}
.container{
  /* 相对定位 */
  position: relative;
  z-index: 1;
  background-color: #fff;
  border-radius: 15px;
  /* 弹性布局 垂直排列 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 350px;
  height: 500px;
  /* 阴影 */
  box-shadow: 0 5px 20px rgba(0,0,0,0.1);
}
.container .tit{
  font-size: 26px;
  margin: 65px auto 70px auto;
}
.container input{
  width: 280px;
  height: 30px;
  text-indent: 8px;
  border: none;
  border-bottom: 1px solid #ddd;
  outline: none;
  margin: 12px auto;
}
.container button{
  width: 280px;
  height: 40px;
  margin: 35px auto 40px auto;
  border: none;
  background: linear-gradient(-200deg,#fac0e7,#aac2ee);
  color: #fff;
  font-weight: bold;
  letter-spacing: 8px;
  border-radius: 10px;
  cursor: pointer;
  /* 动画过渡 */
  transition: 0.5s;
}
.container button:hover{
  background: linear-gradient(-200deg,#aac2ee,#fac0e7);
  background-position-x: -280px;
}
.container span{
  font-size: 14px;
}
.container a{
  color: plum;
  text-decoration: none;
}
ul li{
  position: absolute;
  border: 1px solid #fff;
  background-color: #fff;
  width: 30px;
  height: 30px;
  list-style: none;
  opacity: 0;
}
.square li{
  top: 40vh;
  left: 60vw;
  /* 执行动画：动画名 时长 线性的 无限次播放 */
  animation: square 10s linear infinite;
}
.square li:nth-child(2){
  top: 80vh;
  left: 10vw;
  /* 设置动画延迟时间 */
  animation-delay: 2s;
}
.square li:nth-child(3){
  top: 80vh;
  left: 85vw;
  /* 设置动画延迟时间 */
  animation-delay: 4s;
}
.square li:nth-child(4){
  top: 10vh;
  left: 70vw;
  /* 设置动画延迟时间 */
  animation-delay: 6s;
}
.square li:nth-child(5){
  top: 10vh;
  left: 10vw;
  /* 设置动画延迟时间 */
  animation-delay: 8s;
}
.circle li{
  bottom: 0;
  left: 15vw;
  /* 执行动画 */
  animation: circle 10s linear infinite;
}
.circle li:nth-child(2){
  left: 35vw;
  /* 设置动画延迟时间 */
  animation-delay: 2s;
}
.circle li:nth-child(3){
  left: 55vw;
  /* 设置动画延迟时间 */
  animation-delay: 6s;
}
.circle li:nth-child(4){
  left: 75vw;
  /* 设置动画延迟时间 */
  animation-delay: 4s;
}
.circle li:nth-child(5){
  left: 90vw;
  /* 设置动画延迟时间 */
  animation-delay: 8s;
}

/* 定义动画 */
@keyframes square {
  0%{
    transform: scale(0) rotateY(0deg);
    opacity: 1;
  }
  100%{
    transform: scale(5) rotateY(1000deg);
    opacity: 0;
  }
}
@keyframes circle {
  0%{
    transform: scale(0) rotateY(0deg);
    opacity: 1;
    bottom: 0;
    border-radius: 0;
  }
  100%{
    transform: scale(5) rotateY(1000deg);
    opacity: 0;
    bottom: 90vh;
    border-radius: 50%;
  }
}

</style>