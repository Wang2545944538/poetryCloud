<script setup>
import headler from "../../views/headler.vue";
import writeHeadler from "../../views/write/writeHeadler.vue";
import writeImage from '@/images/write/images.webp'; // 使用别名@指向src目录
import writeFooter from "../../views/write/writeFooter.vue";
import writeBody from "../../views/write/writeBody.vue";


</script>

<template>
  <div class="gradient-background">
    <headler/>
    <writeHeadler/>
    <writeBody/>
    <writeFooter/>
  </div>
</template>




<style scoped>

.gradient-background {
  height: 100%; /* 确保背景覆盖整个视口 */
  background: linear-gradient(to right, rgba(241, 129, 149, 0.79), #23f3ec), /* 从左到右的渐变 */
  linear-gradient(to bottom, rgba(243, 58, 234, 0.84), #f4eded); /* 从上到下的渐变 */
  background-blend-mode: multiply; /* 混合模式，使两个渐变效果叠加 */
}

</style>