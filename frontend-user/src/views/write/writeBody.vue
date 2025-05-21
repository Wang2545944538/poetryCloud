<script setup>
import {ElMessage} from "element-plus";
import  axios  from '@/utils/axios'
//reactive是vue3 的响应式函数
//onMounted 是钩子函数类似于构造函数，自动调用
import {reactive, onMounted, ref} from "vue";

const data = reactive({
  page:1, // 当前页码
  limit:15, //每页数量
  total:0,  //总记录数
  tableData:[], //  分页的表格数据
})

//初始化数据的函数
const init = () =>{
  //异步get请求
  axios.get("/poem/getAllMyPoem", {
    params:{
      page:data.page,
      limit:data.limit,
    }
  })//正确响应后的回调函数,响应内容封装到res变量中
      .then(res=>{
        //res.data 是axios封装的一个属性
        console.log(res.records);
        //把数据赋值给tableData
        data.tableData = res.records;
        data.total = res.total; //总记录数
      })
}
//分页跳转函数,pg是跳转的页码
const changePage = (pg) =>{
  data.page = pg;
  init();
}

onMounted(()=>{
  init();
})


</script>

<template>
  <div>
    <el-header class="header-1">
      <h2 style="margin-top: 100px" class="font-bold text-2xl lg:text-4xl text-gray-800 dark:text-white">诗词爱好者 💖 海棠诗山</h2>
      <h4 class="text-center2">诗词爱好者陆续创作中</h4>
    </el-header>
    <el-main>
      <div class="container">
        <div class="box" v-for="item in data.tableData" :key="item.id">
          <div class="img-box">
            <img :src="`/user/images/img/a5ab98ee28ccad512d8cc48f7e2e69a6644bd0364d391-uIae8t.png`" alt="">
          </div>
          <div class="text-box">
            <div>
              <h2>{{ item.title }}</h2>
              <h4>{{item.nickname}}</h4>
              <p>{{ item.content }}</p>
            </div>
          </div>
        </div>
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

    </el-main>
  </div>

</template>

<style scoped>
.pagination-container {
  display: flex;
  justify-content: center; /* 居中对齐 */
  margin-top: 40px; /* 上边距 */
}

.header-1 {
  text-align: center;
}


*{
  /* 初始化 */
  margin: 0;
  padding: 0;
  /* 元素的总宽度和高度包含内边距和边框 */
  box-sizing: border-box;
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg,#e7f0fd,#accbee);
}
.container {
  /* 相对定位 */
  position: relative;
  /* 弹性布局 */
  display: flex;
  /* 横向排列 */
  flex-direction: row;
  /* 允许换行 */
  flex-wrap: wrap;
  /* 水平居中 */
  justify-content: center;
  /* 间距 */
  gap: 40px;
}

.container .box{
  position: relative;
  width: 275px;
  height: 275px;
  /* 溢出隐藏 */
  overflow: hidden;
  /* 动画过渡 */
  transition: 0.5s;
}
/* 鼠标移入，盒子变大 */
.container .box:hover{
  transform: scale(1.25);
  box-shadow: 0 25px 40px rgba(0,0,0,0.5);
  z-index: 1;
}
.container .box .img-box{
  /* 绝对定位 */
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
/* 渐变遮罩（黑色——透明） 默认隐藏 */
.container .box .img-box::before{
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to top,#222,transparent);
  z-index: 1;
  opacity: 0;
  transition: 0.5s;
}
/* 鼠标移入，渐变遮罩显示 */
.container .box:hover .img-box::before{
  opacity: 1;
}
.container .box .img-box img{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  /* 保持原有尺寸比例，裁切长边 */
  object-fit: cover;
}
.container .box .text-box{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  padding: 20px;
  /* 弹性布局 元素位于容器的结尾 */
  display: flex;
  align-items: flex-end;
  color: #fff;
  z-index: 2;
}
.container .box .text-box h2{
  font-size: 20px;
  margin-bottom: 10px;
  transform: none;     /* 不再默认移出视图 */
  opacity: 1;          /* 默认完全显示 */
  transition: 0.5s;
}
.container .box:hover .text-box h2{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.3s;
}
.container .box .text-box h4{
  font-size: 20px;
  margin-bottom: 10px;
  /* 默认移出可视范围 */
  transform: none;     /* 不再默认移出视图 */
  opacity: 1;          /* 默认完全显示 */
  transition: 0.5s;
}
.container .box:hover .text-box h4{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.3s;
}
.container .box .text-box p{
  font-size: 13px;
  line-height: 20px;
  transition: 0.5s;
  transform: none;     /* 不再默认移出视图 */
  opacity: 1;          /* 默认完全显示 */
}
.container .box:hover .text-box p{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.4s;
}

</style>