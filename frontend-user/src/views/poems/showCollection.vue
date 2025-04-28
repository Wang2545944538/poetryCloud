<script setup>

  import {useRoute} from "vue-router";
  import Headler from "@/views/headler.vue";
  import axios from "../../utils/axios.js";
  import { onMounted, reactive, ref} from 'vue';



  const data = reactive({

    collections:[],
    poems:[],
    tuijian:[],
  })

  onMounted(()=>{
    const router = useRoute();
    const collection_name = router.params.collection_name;
    console.log(collection_name)

    axios.get("/collection/select?collection_name="+collection_name).then(res=>{

      data.collections = res.data.records;

      if (data.collections == null){

      }

    })

    axios.get("/collection/select?limit=8").then(res=>{
      data.tuijian = res.data.records;
    })

  })
  function getPublicImageUrl(imageName) {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/assets/${imageName}`;
  }

</script>

<template>
  <Headler />



  <div style="float: left; width: 30%; text-align: center; padding: 20px; box-sizing: border-box;">
    <div style="font-size: 24px; font-weight: bold; color: #333; margin-bottom: 15px;">以下诗集为搜索结果:</div>
    <template v-for="(item, index) in data.collections">
      <router-link :to="{ name: 'LookCollection', params: { collection_id: item.collection_id } }">
      <div style="font-size: 18px; color: #666; margin: 10px 0; padding: 5px; border: 1px solid #ddd;
            display: inline-block; border-radius: 5px;" >
        {{ item.collection_name }}
      </div>
      </router-link>

    </template>
    <!-- 分割线 -->
    <div style="width: 1px; background-color: #ddd; margin: 0 20px;"></div>
    <router-link :to="{ path:'/poems' }">
      <button class="back-button">点我返回</button>
    </router-link>

  </div>


  <div class="title-style ">
    推荐阅读
  </div>

  <body style="flex: 1;">
  <section>

    <template v-for="(item,index) in data.tuijian">

      <div><router-link :to="{ name: 'LookCollection', params: { collection_id: item.collection_id } }">
        <img :src="getPublicImageUrl(item.collection_avatar)" alt=""></router-link>
      </div>
    </template>

  </section>
  </body>



</template>

<style scoped>
body{
  /* 弹性布局 水平、垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;


  /* 视距 让元素看起来更有3D效果 */
  perspective: 900px;
}
section{
  top:100px;
  position: relative;
  width: 200px;
  height: 300px;
  /* 让其子元素位于3D空间中 */
  transform-style: preserve-3d;
  /* 接下来执行动画 */
  /* 动画名称 时长 线性的 无限次播放 */
  animation: rotate 30s linear infinite;
}
section:hover{
  /* 鼠标移上动画暂停 */
  animation-play-state: paused;
}
section div{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  text-align: center;
  /* 设置元素的倒影效果，below是倒影效果在元素下方，15px是元素和倒影的距离，后面的属性是设置倒影渐变 */
  -webkit-box-reflect: below 15px -webkit-linear-gradient(transparent 50%,rgba(255,255,255,0.3));
}
section div img{
  width: 100%;
  height: 100%;
}

section div:nth-child(1){
  transform: rotateY(0deg) translateZ(300px);
}
section div:nth-child(2){
  transform: rotateY(45deg) translateZ(300px);
}
section div:nth-child(3){
  transform: rotateY(90deg) translateZ(300px);
}
section div:nth-child(4){
  transform: rotateY(135deg) translateZ(300px);
}
section div:nth-child(5){
  transform: rotateY(180deg) translateZ(300px);
}
section div:nth-child(6){
  transform: rotateY(225deg) translateZ(300px);
}
section div:nth-child(7){
  transform: rotateY(270deg) translateZ(300px);
}
section div:nth-child(8){
  transform: rotateY(315deg) translateZ(300px);
}






/* 定义旋转动画 */
@keyframes rotate {
  0%{
    transform: rotateY(0deg);
  }
  100%{
    transform: rotateY(360deg);
  }
}

.title-style {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
  padding: 20px;
  font-family: 'Arial, sans-serif',serif;
}

.back-button {
  background-color: #4CAF50; /* 绿色背景 */
  border: none; /* 移除默认边框 */
  color: white; /* 白色文字 */
  padding: 10px 20px; /* 内边距 */
  text-align: center; /* 文字居中 */
  text-decoration: none; /* 移除下划线 */
  display: inline-block; /* 作为块级元素显示 */
  font-size: 16px; /* 字体大小 */
  margin: 4px 2px; /* 外边距 */
  cursor: pointer; /* 鼠标指针样式 */
  border-radius: 4px; /* 圆角边框 */
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2); /* 阴影效果 */
  transition: 0.3s; /* 过渡效果 */
}

.back-button:hover {
  background-color: #45a049; /* 鼠标悬停时背景颜色变化 */
}

</style>