<script setup>

import {Delete,Edit} from '@element-plus/icons-vue'
import router from "@/routes/index.js";
import  axios  from '@/utils/axios'
//reactive是vue3 的响应式函数
//onMounted 是钩子函数类似于构造函数，自动调用
import {reactive, onMounted,ref} from "vue";
import {ElMessage} from "element-plus";

// 修改
const update = (item) => {
  console.log(item)
  router.push({
    path:'/updateMyWrite',
    query: {
      title: item.title,
      content: item.content,
      poem_id: item.poem_id, // 添加诗歌ID
      status:'1',
      isself:'1',
    }
  })
}


const data = reactive({
  page:1, // 当前页码
  limit:6, //每页数量
  total:0,  //总记录数
  tableData:[], //  分页的表格数据
  showAddDialog:false,  //是否显示增加的对话框
})

//初始化数据的函数
const init = () =>{
  // 确保 data.tableData 已经被定义
  if (!data.tableData) {
    data.tableData = [];
  }


  // 异步get请求
  axios.get("/poem/getMyPoem", {
    params:{
      page:data.page,
      limit:data.limit,
    }
  })//正确响应后的回调函数,响应内容封装到res变量中
      .then(res=>{
        // 确保 data.tableData 已经被定义
        if (!data.tableData) {
          data.tableData = [];
        }
        // 把数据赋值给tableData
        data.tableData = res.records;
        data.total = res.total; // 总记录数
      })
}
//分页跳转函数,pg是跳转的页码
const changePage = (pg) =>{
  data.page = pg;
  init();
}

onMounted(()=>{
  // 确保 data.tableData 已经被定义
  if (!data.tableData) {
    data.tableData = [];
  }
  init();
})

// 删除函数, poem_id是要删除的诗的ID
const del = (poem_id) => {
  axios.get(`/poem/deleteMyPoem`, { params: { poem_id } })
      .then(response => {
        console.log('响应数据:', response.datadata); // 打印响应数据
        if (response.data === 1) { // Check if response data equals 1
          ElMessage({
            showClose: true,
            message: "删除失败!",
            type: "success",
            duration: 1000,
            onClose: () => {
              init();
            }
          });
        } else {
          ElMessage({
            showClose: true,
            message: "删除成功!",
            type: "error",
            duration: 1000,
            onClose: () => {
              init();
            }
          });
        }
      })
      .catch(error => {
        ElMessage({
          showClose: true,
          message: "删除操作出错!",
          type: "error",
          duration: 1000
        });
        console.error("删除失败:", error);
      });
}

</script>

<template>
    <div class="container">
      <div class="row" v-for="n in 3" :key="n">
        <figure class="col" v-for="item in data.tableData.slice((n-1)*2, n*2)" :key="item.id">
          <img :src="`https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/${item.avatar}`" alt="">
          <figcaption>
            <h2><span>{{ item.title }}</span></h2>
            <p class="button-container">
              <el-button
                  class="icon-button"
                  type="info"
                  :icon="Edit"
                  circle
                  @click="update(item)" />
              <el-button
                  style="margin: 0; margin-top: 15px; padding: 0;"
                  class="icon-button"
                  type="danger"
                  :icon="Delete"
                  circle
                  @click="del(item.poem_id)" />
            </p>
            <h4 class="h4">{{item.content}}</h4>
          </figcaption>
        </figure>
      </div>
      <div class="pagination-container">
        <!--        分页插件-->
        <el-pagination
            background
            layout="total, prev, pager, next, jumper"
            :total="data.total"
            :page-size="data.limit"
            @current-page="data.page"
            @current-change="changePage">
        </el-pagination>
      </div>
    </div>

</template>


<style scoped>


/* 分页容器样式 */
.pagination-container {
  display: flex;
  justify-content: center; /* 居中对齐 */
  margin-top: 20px; /* 分页与内容之间的间距 */
  margin-bottom: 50px; /* 分页与页面底部之间的间距 */
}
/* 定义每行的样式 */
.row {
  display: flex;
  justify-content: center; /* 居中对齐 */
  margin-bottom: 1em;
  width: 80%;
}

/* 定义每个列的样式 */
.col {
  flex: 0 0 50%; /* 每个元素占据24%的宽度，留出一点间隔 */
  max-width: 80%; /* 设置最大宽度 */
  margin-right: 1%; /* 间隔 */
  margin-bottom: 1em; /* 如果换行，则添加底部间距 */
}

/* 最后一个元素不保留间隔 */
.col:last-child {
  margin-right: 0; /* 最后一个元素不保留间隔 */
}



/* 当鼠标悬停在容器上时，显示 .h4 元素 */
.container:hover .h4 {
  opacity: 1;
}

.h4 {
  /* 绝对定位，相对于最近的已定位祖先元素进行定位 */
  position: absolute;
  /* 从左侧开始定位 */
  left: 20px;
  /* 从右侧开始定位 */
  right: 70px;
  /* 从底部开始定位 */
  bottom: 150px;
  /* 外边距设置为0 */
  margin: 0;
  /* 内边距设置为0 */
  padding: 0;
  /* 设置字体大小 */
  font-size: 20px;
  /* 设置字体加粗 */
  font-weight: bold;
  /* 设置字体颜色为白色 */
  color: #fff;
  /* 设置文字阴影效果 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  /* 初始状态为完全透明 */
  opacity: 0;
  /* 过渡效果，使透明度变化平滑 */
  transition: opacity 0.5s ease-in-out;
}

*{
  margin: 0;
  padding: 0;
}
body{
  /* 方便演示，满屏居中 */
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

}
.container{
  /* 弹性布局 水平+垂直居中 允许换行 */
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
  width: 100%;
  max-width: 1200px; /* 可以根据需要调整最大宽度 */
  margin: 0 auto; /* 水平居中 */
  margin-top: 50px;
}
.button-container{
  display:flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

}
figure{
  /* 相对定位 */
  position: relative;
  margin: 10px 1%;
  width: 480px;
  height: 360px;
  /* 溢出隐藏 */
  overflow: hidden;
  background-color: #34495e;
}
figcaption{
  color: #fff;
}
/* 黑色边框 */
figcaption::before,
figcaption::after{
  content: "";
  width: 200%;
  height: 200%;
  position: absolute;
  border-style: solid;
  border-color: #101010;
  /* 设置过渡 */
  transition: transform 0.35s;
  /* 这里需要设置元素不对鼠标事件做出反应,图标的悬停样式才会有效 */
  pointer-events: none;
}
/* 右、下边框 */
figcaption::before{
  right: 0;
  bottom: 0;
  border-width: 0 70px 60px 0;
  /* 默认移出可视区域 */
  transform: translate(70px,60px);
}
/* 左、上边框 */
figcaption::after{
  top: 0;
  left: 0;
  border-width: 15px 0 0 15px;
  /* 默认移出可视区域 */
  transform: translate(-15px,-15px);
}

/* 图片样式 */
figure img{
  width: 100%; /* 图片宽度占满整个父元素 */
  height: 100%; /* 图片高度占满整个父元素 */
  object-fit: cover; /* 图片填充模式，保持图片比例覆盖整个容器 */
  position: absolute; /* 绝对定位，相对于最近的已定位祖先元素进行定位 */
  left: 0; /* 图片左边界与父元素左边界对齐 */
  top: 0; /* 图片上边界与父元素上边界对齐 */
  /* 过渡效果 */
  transition: 0.35s; /* 为图片的变化添加0.35秒的过渡效果 */
}

/* 标题 */
figure h2{
  position: absolute;
  left: 0;
  bottom: 0;
  width: 50%;
  height: 60px;
  line-height: 60px;
  text-align: center;
  letter-spacing: 5px;
  font-weight: 300;
  text-shadow: 0 2px 0 rgba(0,0,0,0.5);
  transform: translate(100%,0);
  transition: transform 0.35s;
}
figure h2 span{
  font-weight: 700;
}
/* 功能图标区域 */
figure p {
  position: absolute;
  top: 25px;
  right: 0;
  display: flex;
  flex-direction: column;
  width: 70px;
  justify-content: center;
  align-items: center;
  opacity: 0; /* 初始时不显示按钮 */
  transition: opacity 0.35s; /* 设置透明度的过渡效果 */
}
/* 设置按钮的通用样式 */
.icon-button {
  margin-top: 8px; /* 添加按钮之间的间距 */
}
/* 图标 */
figure p i{
  margin-bottom: 30px;
  font-size: 22px !important;
  cursor: pointer;
  /* 默认隐藏 */
  transform: translate(90px,0);
  opacity: 0;
  /* 过渡 */
  transition: opacity 0.35s,transform 0.35s;
}
/* 接下来是鼠标悬停时的样式变化 */
figure:hover figcaption::before,
figure:hover figcaption::after{
  transform: translate(0,0);
}
/* 当鼠标悬停在图片上时，改变图片的透明度和尺寸 */
figure:hover img{
  opacity: 0.8; /* 将图片的透明度设置为80% */
  width: 95%; /* 将图片的宽度设置为95% */
  height: 95%; /* 将图片的高度设置为95% */
}
figure:hover h2,
figure:hover p i{
  transform: translate(0,0);
}
figure:hover p i{
  opacity: 1;
}
/* 设置各个图标的动画延迟(实现逐个进入) */
figure:hover p i:nth-child(1){
  transition-delay: 0.025s;
}
figure:hover p i:nth-child(2){
  transition-delay: 0.05s;
}
figure:hover p i:nth-child(3){
  transition-delay: 0.075s;
}
figure:hover p i:nth-child(4){
  transition-delay: 0.1s;
}
figure p i:hover{
  color: #f3cf3f;
}
/* 鼠标悬停时的样式变化 */
figure:hover p {
  opacity: 1; /* 鼠标悬停时显示按钮 */
}

</style>