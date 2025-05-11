<template>
  <headler />
  <div class="image-container">
    <div class="title">
      <img src="/images/icon/诗词诵读.png" alt="">
      <p>云墨诗苑</p>
    </div>
    <div class="search-box">
      <div class="search-input-container">
        <input type="text" v-model="searchQuery" class="search-input" placeholder="输入搜索诗人...">
        <button class="search-btn" @click="submitSearch"><img src="/images/icon/搜索-01.png" alt=""></button>
      </div>
    </div>
    <div class="image-stack">

      <div class="image" style="background-image: url('/user/images/img/xihushijingtujuan-1.jpg');"></div>
      <div class="image" style="background-image: url('/user/images/img/xihushijingtujuan-2.jpg');"></div>
      <div class="image" style="background-image: url('/user/images/img/xihushijingtujuan-3.jpg');"></div>
      <div class="image" style="background-image: url('/user/images/img/xihushijingtujuan-4.jpg');"></div>
    </div>
  </div>
  <!-- 下方可以继续放置其他模块 -->

  <poetbox />
  <!-- <eight/> -->
  <poetbutton />
</template>

<script setup>
import axios from "@/utils/axios.js";
import { ref } from 'vue';
import headler from "../../views/headler.vue";
import poetbox from "../poet/poetbox.vue";
import eight from "../poet/eight.vue";
import poetbutton from "../poet/poetbutton.vue";
import { useRouter } from 'vue-router';
const searchQuery = ref(''); // 创建一个响应式变量
const router = useRouter();

const submitSearch = () => {
    if (searchQuery.value.trim() === '') {
    alert('请输入搜索内容');
    return;
  }
  router.push({ name: 'authorlist', params: { author_name: searchQuery.value } });
};

// const submitSearch1 = () => {
//   // 当按钮被点击时，执行这个函数
//   if (searchQuery.value.trim() === '') {
//     alert('请输入搜索内容');
//     return;
//   }
//   axios.get("/author/findByNameAuthor?author_name=" + searchQuery.value)
//     .then(res => {
//       console.log(res.records);
//     })
// };
</script>
<style scoped>
body,
html {
  margin: 0;
  padding: 0;
}

.title img {
  width: 70px;
  height: 70px;
}

.title {
  display: flex;
  flex-direction: row;
  justify-content: center;

  align-items: center;
  width: 100%;
  font-family: 'MyCustomFont', sans-serif;
  font-size: 55px;
  color: white;
  top: 30%;
  text-align: center;
  position: absolute;
  z-index: 999;
}

.image::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(21, 21, 21, 0.258);
  /* 半透明黑色背景，你可以根据需要调整颜色和透明度 */
}

.image-container {
  overflow: hidden;
  width: 100%;
  height: 90vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.image-stack {
  display: flex;
  width: 400vw;
  /* 四张图片的总宽度 */
  animation: moveLeft 400s linear infinite;
  /* 动画时间可以按需调整 */
  animation-fill-mode: forwards;
  /* 保持在最后一个关键帧 */
  animation-direction: alternate;
  /* 动画结束后反向运行 */
}

.image {
  flex: 0 0 100vw;
  /* 每张图片宽度为视口宽度 */
  height: 100vh;
  background-size: cover;
  /* 背景图片覆盖整个容器 */
  background-repeat: no-repeat;
  background-position: center;
}

@keyframes moveLeft {
  0% {
    transform: translateX(0);
    /* 动画开始位置 */
  }

  100% {
    transform: translateX(-150vw);
    /* 动画结束位置，移动整个图片堆的总宽度 */
  }
}


.search-box {
  position: absolute;
  margin-top: 80px;
  z-index: 999;
  text-align: center;
  width: 500px;
  /* 根据需要调整宽度 */
  height: 60px;
  /* 根据需要调整高度 */
  top: 40%;
}

.search-input-container {
  display: flex;
  align-items: center;
  border: 1px solid #5f5f5f;
  border-radius: 25px;
  overflow: hidden;
  height: 100%;
  /* 防止内容溢出 */
}

.search-input {
  flex-grow: 1;
  /* 让输入框占据剩余空间 */
  padding: 10px 15px;
  border: none;
  /* 移除默认边框 */
  background-color: #fff;
  font-size: 20px;
  color: #494747;
  outline: none;
  height: 100%;
}

.search-input::placeholder {
  color: #575655;
}

.search-btn {
  background: #02b05f;
  border: none;
  cursor: pointer;
  padding: 0 15px;
  width: 100px;
  height: 100%;
  /* 按钮内边距 */
}

.search-btn img {

  width: 40%;
  height: 45%;

  /* 设置图片高度为按钮高度 */
  /* 根据需要调整图片宽度 */
}
</style>
