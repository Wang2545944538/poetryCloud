<template>
  <div>
    <h2 class="title">邂逅文人墨客，共赏诗词华章</h2>
    <div class="container">
      <div v-for="author in randomData" :key="author.author_id" class="card" @click="goToDetail(author.author_id)">
        <div class="img-box">
          <!-- 假设author对象中有author_img字段 -->
          <img :src="getPublicImageUrl(author.author_img)" :alt="author.author_name">
        </div>
        <div class="text-box">
          <h2>{{ author.author_name }}</h2>
          <!-- 假设author对象中有author_intro字段 -->
          <p>{{ author.author_intro }}</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import axios from "@/utils/axios.js";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const goToDetail = (author_id) => {
  router.push({ name: 'authorDetail', params: { id: author_id } });
};

const data = reactive({
  authorcollections: [],
});
// 计算属性，用于返回随机选择的六条数据
const randomData = computed(() => {
  // 先复制原数组，以避免修改原数据
  const shuffled = [...data.authorcollections];
  // 打乱数组
  shuffled.sort(() => 0.5 - Math.random());
  // 获取前六个元素
  return shuffled.slice(0, 6);
});
onMounted(() => {
  getAuthors();
});
//获取诗人
const getAuthors = () => {
  axios.get("/author/authorList")
    .then(res => {
      // 假设res.data是后端返回的数组，直接赋值给authorcollections
      data.authorcollections = res.data;
    })
    .catch(error => {
      console.error("Error fetching authors:", error);
    });
};

function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}
</script>



<style scoped>
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(50px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.title {
  width: 100%;
  text-align: center;
  font-size: 3em;
  color: #2c3e50;
  font-family: 'MyCustomFont', 'Helvetica Neue', Arial, sans-serif;
  line-height: 1.3;
  padding: 40px 0;
  text-shadow: 0 4px 8px rgba(0, 0, 0, 0.25);
  animation: fadeInUp 1.5s cubic-bezier(0.390, 0.575, 0.565, 1.000) forwards;
  border-bottom: 1px solid #e6e6e6;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s cubic-bezier(0.250, 0.460, 0.450, 0.940), box-shadow 0.3s ease;
  /* 改进过渡效果，增加阴影变化的过渡 */
}

.title:hover {
  transform: translateY(-10px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  /* 悬停时增加阴影深度，使上移效果更明显 */
}

/* 响应式设计，针对小屏幕调整样式 */
@media (max-width: 768px) {
  .title {
    font-size: 2em;
    padding: 20px 0;
    text-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
    /* 小屏幕减少阴影效果，以保持清晰度 */
  }
}


.container {
  /* 相对定位 */
  position: relative;
  /* 弹性布局 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 允许换行 */
  flex-wrap: wrap;
  padding: 30px;
}

.container .card {
  position: relative;
  max-width: 300px;
  height: 215px;
  background-color: #fff;
  margin: 30px 15px;
  padding: 20px 15px;
  border-radius: 5px;
  /* 阴影 */
  box-shadow: 0 5px 200px rgba(0, 0, 0, 0.5);
  /* 动画过渡 */
  transition: 0.3s ease-in-out;
}

.container .card:hover {
  height: 420px;
}

.container .card .img-box {
  position: relative;
  width: 260px;
  height: 260px;
  border-radius: 5px;
  /* 溢出隐藏 */
  overflow: hidden;
  top: -60px;
  left: 20px;
  /* 阴影 */
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.container .card .img-box img {
  width: 100%;
}

.container .card .text-box {
  position: relative;
  margin-top: -140px;
  padding: 10px 15px;
  text-align: center;
  color: #111;
  /* 设置元素不可见 */
  visibility: hidden;
  /* 不透明度 */
  opacity: 0;
  transition: 0.3s ease-in-out;
}

.container .card .text-box p {
  text-align: left;
  line-height: 25px;
  margin-top: 10px;
  font-size: 15px;
  color: #555;
}

.container .card:hover .text-box {
  /* 鼠标移入，设置元素可见 */
  visibility: visible;
  opacity: 1;
  margin-top: -40px;
  /* 动画延迟0.2秒 */
  transition-delay: 0.2s;
}
</style>