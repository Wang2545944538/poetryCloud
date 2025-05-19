<script setup>
import {ref, computed, onMounted, reactive} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElPageHeader, ElButton } from 'element-plus';
import headler from "../../views/headler.vue";
import axios from "@/utils/axios.js";

const route = useRoute();
const router = useRouter();

const isLoggedIn = ref(false);
// 假设使用 localStorage 保存登录 token
onMounted(() => {
  isLoggedIn.value = !!localStorage.getItem("token"); // 或者是检查是否有 user 信息
});


const currentKey = ref('评析'); // 默认显示评析内容
const comment = ref(''); // 绑定输入框的值

const formattedContent = computed(() => {
  if (state.poemIfo.content) {
    return state.poemIfo.content.replace(/。/g, '。<br>');
  }
  return '';
});

const goBack = () => {
  router.go(-1);
};

const changeKey = (key) => {
  currentKey.value = key;
};

const state = reactive({
  poemIfo:[],
  comments:[]
});

const getPoemDetails = (poemId) => {
  axios.get("/poem/getPoemDetails?poemId="+poemId)
      .then(res=>{
        state.poemIfo=res.data;
      })
}
const getComments = (poemId) => {
  axios.get("/comment/getComments?poemId="+poemId)
      .then(res=>{
        state.comments=res.data;
        console.log(state.comments);
      })
}

getPoemDetails(route.params.id);
getComments(route.params.id)
const getValueContent = ()=> {
  let content = '';
  switch (currentKey.value) {
    case '评析':
      content = state.poemIfo.poeminformation.pingxi;
      break;
    case '注释':
      content = state.poemIfo.poeminformation.comments;
      break;
    case '译文':
      content = state.poemIfo.poeminformation.translation;
      break;
    case '辑评':
      content = state.poemIfo.poeminformation.jiping;
      break;
    default:
      content = '';
  }
  // 在句号后面添加换行
  return content.replace(/。/g, '。<br>');
};

const submitComment = () => {
  if (!comment.value.trim()) {
    // 如果评论为空或者只包含空白字符，则给出提示
    alert('评论内容不能为空！');
    return;
  }
  if(comment.value.length > 200){
    alert('评论内容不能超过 200 字！');
    return;
  }
  const params = {
    poem_id:route.params.id,
    content:comment.value
  }
  axios.post("/comment/submitComment",params)
      .then(res=>{
        // 刷新页面
        window.location.reload();
        // 提交后清空评论框
        comment.value = '';
      })
};
</script>

<template>
  <headler />
  <div class="poem-detail">
    <el-page-header @back="goBack" content="诗歌详情" class="page-header" />
    <div v-if="state.poemIfo" class="content">
      <h1 class="title">{{ state.poemIfo.title }}</h1>
      <div class="info">
        <span class="dynasty">{{ state.poemIfo.author.dynasty.dynasty_name }}</span>
        <span class="separator">·</span>
        <span class="author">{{ state.poemIfo.author.author_name }}</span>
      </div>
      <div class="poem-content" v-html="formattedContent"></div>
      <div class="keys">
        <el-button class="key" @click="changeKey('评析')">评析</el-button>
        <el-button class="key" @click="changeKey('注释')">注释</el-button>
        <el-button class="key" @click="changeKey('译文')">译文</el-button>
        <el-button class="key" @click="changeKey('辑评')">辑评</el-button>
      </div>
      <div class="value" v-html="getValueContent()"></div>
    </div>
    <!-- 评论区域 -->
    <div class="comments">
      <h3>评论</h3>

      <!-- 未登录提示 -->
      <div v-if="!isLoggedIn" class="not-logged-in">
        <p>请先登录后再发表评论。</p>
        <el-button @click="router.push('/login')">前往登录</el-button>
      </div>
      <!-- 已登录才显示评论框和评论列表 -->
      <div v-else>
        <div class="comment-form">
          <textarea v-model="comment" placeholder="写下你的评论..." maxlength="200"></textarea>
          <el-button @click="submitComment" class="submit-comment">提交评论</el-button>
        </div>
      </div>
      <!-- 评论列表区域（假设后端返回评论数据） -->
      <div class="comment-list">
        <!-- 评论项 -->
        <!-- <div class="comment-item" v-for="comment in state.comments"> -->
        <div class="comment-item" v-for="comment in state.comments" :key="comment.id">
          <div class="comment-user">{{comment.users.nickname}}:</div>
          <div class="comment-content">{{comment.content}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.poem-detail {
  max-width: 800px;
  margin: 40px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.page-header {
  margin-bottom: 20px;
}

.title {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 10px;
}

.info {
  font-size: 18px;
  color: #666;
  text-align: center;
  margin-bottom: 20px;
}

.separator {
  margin: 0 8px;
}

.poem-content {
  white-space: pre-line;
  line-height: 1.8;
  font-size: 24px;
  text-align: center;
  margin-bottom: 30px;
  font-family: '楷体', serif;
}

.keys {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.key {
  margin: 0 10px;
  font-size: 16px;
  color: #333;
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.key:hover {
  background-color: #e9e9e9;
}

.value {
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #eee;
  font-size: 18px;
  line-height: 1.6;
  color: #555;
  border-radius: 4px;
}

.comments {
  margin-top: 40px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.comment-form {
  height: 155px;
  margin-bottom: 20px;
}

textarea {
  width: 95%;
  height: 100px;
  padding: 12px 20px;
  font-size: 16px;
  border: 1px solid #ddd;
  margin-bottom: 10px;
  border-radius: 4px;
  resize: vertical;
}

.submit-comment {
  float: right;
  background-color: #409eff;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-comment:hover {
  background-color: #79bbff;
}

.comment-list {
  margin-top: 20px;
}

.comment-item {
  border-top: 1px solid #eee;
  padding: 10px 0;
}

.comment-user {
  font-weight: bold;
  color: #333;
}

.comment-content {
  margin-top: 5px;
  color: #666;
}
</style>
