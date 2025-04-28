<template>

  <el-container >
    <el-aside width="200px" class="activity-aside">

    </el-aside>

    <el-main class="activity-main">
      <el-header class="activity-header">

        <el-button class="back-button" icon="el-icon-arrow-left" circle @click="back" >返回</el-button>
        <p class="activity-content">{{ activityDetails.activeContent }}</p>
      </el-header>

        <div class="activity-media">
          <div class="activity-image">
            <img :src="activityDetails.imageUrl" alt="Activity Image" />
          </div>
          <div class="activity-text">
            <p class="activity-profile">活动简介:{{ activityDetails.activeProfile }}</p>
            <p class="activity-date">{{ activityDetails.formattedDate }}</p>
          </div>
        </div>
        <div>
          <el-button v-if="activityDetails.activeType==='答题'" class="fixed-button" @click="startActivity('答题')">开始答题</el-button>
          <el-button v-if="activityDetails.activeType==='点字成诗'" class="fixed-button" @click="startActivity('点字成诗')">开始点字成诗</el-button>
          <el-button v-if="activityDetails.activeType==='拼图'" class="fixed-button" @click="startActivity('拼图')">
            开始拼图
          </el-button>
        </div>
    </el-main>
    <!-- 如果需要另一个侧边栏，可以在这里添加 -->
    <el-aside width="200px" class="activity-aside">

    </el-aside>
  </el-container>
</template>

<script setup>
import { ref, onMounted, computed, watchEffect } from 'vue';
import moment from 'moment';
import { useRoute } from 'vue-router';
import routes from "@/routes/index.js";
import Headler from "@/views/headler.vue";

const route = useRoute();


const paths = {
  '答题': '/answer',
  '点字成诗': '/BrailleIntoPoetry',
  '拼图': '/puzzle'
};

const startActivity = (activityType) => {
  const path = paths[activityType];
  if (path) {
    routes.push({
      path,
      query: {
        imageUrl: activityDetails.value.imageUrl
      }
    });
  }
   else {
    console.error('未知的活动类型');
  }
};
const back=()=>{
  routes.push({path:'/poetrySociety'})
}

const toAnswer=()=>{
  routes.push('/answer')
}

// 创建一个响应式对象来存储活动详情
const activityDetails = ref({
  activityId: null,
  activeContent: '',
  activeTime: '',
  activeType: '',
  activeImg: '',
  activeProfile: '',
  imageUrl: computed(() => {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/jieImg/${activityDetails.value.activeImg}`;
  }),
  formattedDate: computed(() => {
    return moment(activityDetails.value.activeTime).format('YYYY-MM-DD');
  })
});

// 初始化活动详情
const initializeActivityDetails = () => {
  activityDetails.value.activityId = route.query.active_id;
  activityDetails.value.activeContent = route.query.active_content;
  activityDetails.value.activeTime = route.query.active_time;
  activityDetails.value.activeType = route.query.active_type;
  activityDetails.value.activeImg = route.query.active_img;
  activityDetails.value.activeProfile = route.query.active_profile;
};

// 当组件挂载时，初始化活动详情
onMounted(() => {
  initializeActivityDetails();
});

// 监听路由的变化
watchEffect(() => {
  initializeActivityDetails();
});
</script>

<style scoped>

/*.activity-aside {
  background-color: #d3dce6; !* 侧边栏背景色 *!
  color: #333; !* 侧边栏文字颜色 *!
}*/

.activity-main {
  background-color: #e9eef3; /* 主内容区域背景色 */
  width: 100vw;
  height: 100vh;
}

.activity-info p {
  margin: 10px 0;
  color: #666;
  font-size: 16px;
}

.activity-image img {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
  display: block;
  margin: 10px auto;
}

.activity-profile,.activity-content {
  font-style: italic;
  color: #999;
}
  .activity-date {
    color: #666;
    font-size: 16px;
  }
.activity-header {

  color: white; /* 头部文字颜色 */
  padding: 10px 20px;
  display: flex;
  align-items: center; /* 垂直居中对齐 */
}

.activity-content {
  margin: 0;
  font-size: 24px; /* 增加字体大小 */
  flex: 1; /* 允许内容区域扩展 */
  text-align: center; /* 文字居中对齐 */
}

.back-button {
  margin-right: 20px; /* 与内容区域保持一定距离 */
  font-size: 15px; /* 调整按钮内字体大小 */
  font-style: italic;
  text-align: center;
  color: black; /* 按钮文字颜色与头部文字颜色一致 */
  padding: 20px 20px; /* 调整按钮内边距 */
  cursor: pointer; /* 添加鼠标指针样式 */
}

.activity-text {
  margin-left: 105px;
}

.fixed-button {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%); /* 水平居中 */
  padding: 10px 20px;
  font-size: 16px;
  background-color: crimson;
  color: white;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
</style>


