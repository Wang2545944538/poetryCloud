<script setup>
import myWriteBody from "../../views/write/myWriteBody.vue";
import myWriteFooter from "../../views/write/myWriteFooter.vue";
import headler from "../../views/headler.vue";
</script>
<template>
  <headler/>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
  <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <body>

    <div class="my1">
      <h1 class="H1">我的已发布诗集</h1>
      <myWriteBody/>
      <div class="my2">
        <h1 class="H1">我的未发布诗集</h1>
        <myWriteFooter/>
      </div>
    </div>


  </body>

</template>


<style scoped>
.my2{
  margin-top: 50px;
  width: 70%; /* 宽度 */
  height: 100%; /* 高度 */
  margin: 0 auto; /* 自动边距 */
}

.H1 {
  font-size: 30px;
  color: #f3f3f3;
  text-align: center;
  margin-top: 40px;
  margin-bottom: 10px;
  font-family: '宋体', sans-serif;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}


.my1{
  width: 100%;
  height: 100%;
}

body{
  display: flex;
  /* 方便演示，满屏居中 */
  height: 100%;
  justify-content: center;
  align-items: flex-start; /* 保持垂直排列 */
  background: linear-gradient(to right, rgba(241, 129, 149, 0.79), #23f3ec), /* 从左到右的渐变 */
  linear-gradient(to bottom, rgba(243, 58, 234, 0.84), #f4eded); /* 从上到下的渐变 */
  background-blend-mode: multiply; /* 混合模式，使两个渐变效果叠加 */
}
</style>