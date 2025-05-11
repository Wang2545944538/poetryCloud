<script setup>

import writeImage from '/images/write/images.webp';
import router from "@/routes/index.js";
import {Plus} from "@element-plus/icons-vue"; // 使用别名@指向src目录

const handleAdd = () => {
  router.push({ path: '/addWrite' })
}
const handleMy = () => {
  router.push({ path: '/myWrite' })
}
</script>

<template>
  <div class="max1">
    <div class="flex justify-between items-center">
      <!-- 右侧图片部分 -->
      <div class="image-container order-2">
        <picture>
          <source :srcset="writeImage" type="image/webp" />
          <img :src="writeImage" alt="write" class="image-size" />
        </picture>
      </div>
      <!-- 左侧文本部分 -->
      <div class="text-container order-1">
        <h1 class="h11">古韵今风，海棠诗梦间</h1>
        <p>当科技与艺术融合，诗词创作从此变得更有趣</p>
        <div>
          <el-button class="mr-4" type="primary" :icon="Plus" @click="handleAdd">🔥 我要创作</el-button>
          <el-button class="mr-5" type="primary" :icon="Plus" @click="handleMy" >📚 我的创作</el-button>

        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>


.mr-4 {
  margin-right: 1rem;
  background-color: black; /* 设置按钮背景颜色 */
  color: white; /* 设置按钮文字颜色 */
}

.mr-5 {
  margin-right: 1rem;
  background-color: white; /* 设置按钮背景颜色 */
  color: black; /* 设置按钮文字颜色 */
}

.h11 {
  font-size: 3rem; /* 设置字体大小 */
  font-family: 'PingFang SC', sans-serif; /* 设置字体 */
}

.flex {
  display: flex;
  margin-top: 0px;/* 距离容器顶部10px*/
}

.justify-between {
  width: 90%;
  justify-content: space-between;
}

.items-center {
  align-items: center;
}

.text-center {
  text-align: center;
}

.mr-4 {
  margin-right: 1rem; /* 给“我要入社”链接添加右边距 */
}

/* 定义order类来控制Flex子项的顺序 */
.order-1 {
  order: 1;
}

.order-2 {
  order: 2;
}

.max1  {
  max-width: 1300px;
  margin: 0 auto;
  padding: 0 20px;
  height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
}



/* 图片容器样式 */
.image-container {
  width: 60%; /* 设置图片容器的宽度 */
  /*居中*/
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 文本容器样式 */
.text-container {
  width: 60%; /* 设置文本容器的宽度 */
  text-align: center; /* 文本居中 */
  /* 距离容器顶部10px*/
  margin-top: -200px;

}

/* 图片大小样式 */
.image-size {
  object-fit: cover; /* 保持图片比例不变，覆盖整个容器 */
}

</style>