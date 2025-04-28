<script setup>

import {Plus} from "@element-plus/icons-vue";
import router from "@/routes/index.js";
const handleAdd = () => {
  router.push({ path: '/addWrite' })
}
</script>

<template>
  <div class="relative py-16">
    <div class="relative_box">
      <div class="image-container" style="width: 100%">
        <img src="@/images/write/images1.webp" alt="image1" class="rounded-image1">
        <img src="@/images/write/images3.webp" alt="image3" class="rounded-image2">
        <img src="@/images/write/images2.webp" alt="image2" class="rounded-image3">
        <img src="@/images/write/images4.webp" alt="image4" class="rounded-image4">
        <img src="@/images/write/images5.webp" alt="image5" class="rounded-image5">
      </div>
      <div class="mt-6 m-auto space-y-6 md:w-8/12 lg:w-7/12">
        <h2 class="bz1">
          心动不如行动，赶紧进行创作吧!
        </h2>
        <p class="bz2">
          加入创作中，共赏诗词之美，和诗友共享诗词的快乐时光
        </p>
        <div class="div_b1">
          <el-button class="b1" type="primary" :icon="Plus" @click="handleAdd">🔥 我要创作</el-button>
        </div>
      </div>
      <div style="height: 100px"></div>
    </div>
  </div>

</template>

<style scoped>

.bz1 {
  font-weight: bold; /* 设置h2为加粗 */
  font-style: normal; /* 设置字体样式为正常 */
  text-align: center; /* 文字居中 */
  font-family: 'Arial', sans-serif; /* 设置字体，这里使用Arial，如果没有则使用 sans-serif */
}

.bz2 {
  font-weight: normal; /* 设置p为非加粗 */
  font-style: italic; /* 设置字体样式为斜体 */
  text-align: center; /* 文字居中 */
  font-family: 'Arial', sans-serif; /* 设置字体，这里使用Arial，如果没有则使用 sans-serif */
}

.div_b1{
  display: flex; /* 使用Flexbox布局 */
  justify-content: center; /* 水平居中按钮 */
  align-items: center; /* 垂直居中按钮（如果需要） */
  height: 40px;
}

.b1 {
  margin-right: 1rem;
  background-color: black; /* 设置按钮背景颜色 */
  color: white; /* 设置按钮文字颜色 */
  border: none; /* 去除边框 */
}

.relative_box{
  width: 100%;
}

.image-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  flex-wrap: wrap; /* 允许图片换行 */
  gap: 1px; /* 调整图片之间的间距，根据需要修改 */
  height: 18vh; /* 设置容器的高度为视口高度的40%，以便垂直居中，根据需要调整 */
}

.rounded-image1 {
  width: 60px; /* 设置期望的宽度 */
  height: 60px; /* 设置期望的高度 */
  border-radius: 50%; /* 使图片变为圆形 */
  object-fit: cover; /* 确保图片覆盖圆形区域而不失真 */
}

.rounded-image2 {
  width: 80px; /* 设置期望的宽度 */
  height: 80px; /* 设置期望的高度 */
  border-radius: 50%; /* 使图片变为圆形 */
  object-fit: cover; /* 确保图片覆盖圆形区域而不失真 */
}

.rounded-image3 {
  width: 100px; /* 设置期望的宽度 */
  height: 100px; /* 设置期望的高度 */
  border-radius: 50%; /* 使图片变为圆形 */
  object-fit: cover; /* 确保图片覆盖圆形区域而不失真 */
}

.rounded-image4 {
  width: 80px; /* 设置期望的宽度 */
  height: 80px; /* 设置期望的高度 */
  border-radius: 50%; /* 使图片变为圆形 */
  object-fit: cover; /* 确保图片覆盖圆形区域而不失真 */
}

.rounded-image5 {
  width: 60px; /* 设置期望的宽度 */
  height: 60px; /* 设置期望的高度 */
  border-radius: 50%; /* 使图片变为圆形 */
  object-fit: cover; /* 确保图片覆盖圆形区域而不失真 */
}

</style>