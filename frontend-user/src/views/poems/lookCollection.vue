<script setup>


import {onMounted, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import axios from "@/utils/axios.js";
import Headler from "@/views/headler.vue";
import {ElMessage} from "element-plus";




const data = reactive({
  name,
  collection:[] ,
  poems:[],
  fill:"transparent",
  page:1,
  limit:3,
  total:0,
})
const route = useRoute()
let collection_id='';
onMounted(()=>{
  collection_id=route.params.collection_id;
  console.log(collection_id)
  axios.get("collection/select",{params:{collection_id:collection_id}}).then(res=>{
    data.collection=res.data.records
    data.name = data.collection[0].collection_name

  })
  changePage(1)
})

const changeStar=()=>{
  // data.fill="gold";
  console.log(collection_id)
  axios.post("collection/addLike?collection_id="+collection_id).then(res=>{

    if(res.code===200){
    ElMessage({
      showClose:true,
      message:"添加成功！",
      type:"success",
      duration:1000,
    })}else {
      ElMessage({
        message:"收藏失败,该诗集已收藏！",
        type:"error",
        duration:1000,

      })
    }

  })
}

const changePage=(page)=>{
  data.page=page;
  axios.get("collection/getAllPoemsByCollection_id",{params:{collection_id:collection_id,
    limit:data.limit,page:data.page}}).then(res=>{
    data.poems=res.data.records
    data.page=res.data.current
    data.total=res.data.total
    data.limit = res.data.size
})

}


</script>

<template >
<Headler />

  <div
      class="external"
      style="background-image: url('src/images/img/poems_1.png')">
<div>

    <h1 style="text-align: center;">
      <router-link :to="{name:'poems'}">
      <button  class="back-button">返回诗集</button></router-link>
      {{data.name}}

      <svg @click="changeStar" width="100" height="100" viewBox="-10 -10 100 100" xmlns="http://www.w3.org/2000/svg">
        <polygon points="50,15 61,35 85,35 66,50 72,75 50,60 28,75 34,50 15,35 39,35" :fill= "data.fill" stroke="black"/>
      </svg>
    </h1>
  </div>
<div class="c1">

    <div class="container"
         v-for="(item, index) in data.poems"
         :key="index" >
      <div class="card">

        <h2 style="text-align: center">
          <router-link style="color: black;text-decoration: none"
                       :to="{ name: 'poemDetail', params: { id: item.poem_id } }">{{item.title}}</router-link></h2>
        <div class="img-box">
          <p>{{item.content}}</p>
        </div>
        <div class="text-box">

          <p>{{item.intro}}</p>
        </div>
      </div>
    </div>


</div>
    <div class="pagination-container">
      <el-pagination
          background
          :page-size="data.limit"
          layout="total, prev, pager, next, jumper"
          :total="data.total"
          @current-page="data.page = $event"
          @current-change="changePage"
      />
    </div>
</div>




  
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Amatic+SC:wght@400;700&display=swap');

h1 {
  font-family: 'Amatic SC', cursive;
  text-align: center;
  font-size: 5em; /* 根据需要调整 */
}

.external {
  width: 100vw;

  overflow-y: hidden;
  overflow-x: auto;
  height: 100vh;
  background-size: cover;
  background-repeat: no-repeat;

}

*{
  /* 初始化 */
  margin: 0;
  padding: 0;
  overflow: hidden;
}
.c1{
  /* 弹性布局，主轴方向为水平 */
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.container {

  /* 项目在主轴上的对齐方式，这里设置为两边对齐 */
  justify-content: space-between;
  /* 项目在交叉轴上的对齐方式，这里设置为居中 */
  align-items: center;
  /* 取消换行，所有卡片都在一行显示 */

  padding: 30px;
}

.container .card {
  /* 设置卡片宽度，根据需要调整 */
  width: 300px;
  /* 删除高度，因为现在卡片会根据内容自动调整高度 */
  height: auto;
  /* 其他样式保持不变 */
  margin: 15px;
  padding: 20px 15px;
  border-radius: 5px;
  box-shadow: 0 5px 20px rgba(0,0,0,0.5);
  transition: 0.3s ease-in-out;
}

.container .card:hover {
  /* 删除高度变化，如果需要卡片在悬停时展开，可以重新设置高度或者使用其他效果 */
}

.container .card .img-box {
  /* 修改定位和尺寸，确保图片框适应卡片 */
  position: relative;
  width: 100%;
  height: auto;
  border-radius: 5px;
  overflow: hidden;
  top: 0;
  left: 0;
  box-shadow: 0 5px 20px rgba(0,0,0,0.2);
  z-index: 1;
}

.container .card .img-box img {
  width: 100%;
  height: auto; /* 确保图片保持其比例 */
}

.container .card .text-box {
  /* 修改文本框的定位和可见性 */
  position: relative;
  margin-top: 20px; /* 根据需要调整与图片的距离 */
  padding: 10px 15px;
  text-align: center;
  color: #111;
  visibility: visible; /* 默认可见 */
  opacity: 1; /* 默认不透明 */
  transition: 0.3s ease-in-out;
}

.container .card .text-box p {
  text-align: left;
  line-height: 25px;
  margin-top: 10px;
  font-size: 15px;
  color: #555;
}

/* 如果不需要卡片在悬停时展开，可以删除以下样式 */
.container .card:hover .text-box {
  /* 鼠标移入时不需要改变可见性和透明度，因为已经是默认状态 */
  margin-top: 0; /* 保持默认位置 */
  transition-delay: 0s; /* 移除延迟 */
}

.back-button {
  float: left;
  background: linear-gradient(to right, #ac725d, #c3a774); /* 渐变背景 */
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
  background: linear-gradient(to right, #ffa500, #ff7f50); /* 鼠标悬停时背景颜色变化 */
}

/* 字体样式 */
.back-button {
  font-family: 'Roboto', sans-serif; /* 使用Roboto字体 */
  font-weight: 300; /* 字体粗细 */
  letter-spacing: 1px; /* 字符间距 */
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.el-pagination {
  background-color: transparent; /* 设置背景为透明 */
  padding: 10px;
  box-shadow: none; /* 移除阴影 */
}

.el-pagination .el-pagination__total {
  color: #333;
  margin-right: 10px;
}

.el-pagination .el-button {
  background-color: rgba(0, 123, 255, 0.8); /* 半透明蓝色 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 12px;
  transition: background-color 0.3s;
}

.el-pagination .el-button:hover {
  background-color: rgba(0, 86, 179, 0.8); /* 半透明悬停效果 */
}

.el-pagination .el-button--disabled {
  background-color: rgba(224, 224, 224, 0.6); /* 半透明禁用状态 */
  color: #aaa;
}

</style>