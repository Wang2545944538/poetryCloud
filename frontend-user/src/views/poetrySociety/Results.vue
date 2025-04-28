
<template>
  <el-container>
    <!-- 左侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="../../images/img/left.jpg" alt="Left Image Description" class="full-height-image">
    </el-aside>

    <!-- 中间部分 -->
    <el-container>
      <!-- 中间的头部 Header -->
      <el-header class="header">
        <button class="btn btn-default btn-return" @click="goBack">返回</button>
        <div class="centered-text-container">
          <div class="art-text">中国诗词大会 - 结果</div>
        </div>

      </el-header>

      <!-- 中间的主体 Main -->
      <el-main>
        <div class="question">
          <!-- 显示当前问题 -->
          {{ questions[currentQuestionIndex].question }}
        </div>
        <div class="options">
          <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index"  class="option">
            <div v-if="index !== 'options_id'">
              <input type="radio" :id="`option-${index}`" name="answer"  :value="option" v-model="results[currentQuestionIndex]" :disabled=true>
              <label :for="`option-${index}`" class="option-label"  :class="{ 'selected': option === results[currentQuestionIndex] }"  v-show="index !==0">{{ option }}</label>
            </div>
          </div>
        </div>
        <p >你的答案:{{results[currentQuestionIndex]}}</p>
        <p style="color: red">正确答案:{{questions[currentQuestionIndex].answer}}</p>
        <p>当前题目：{{ currentQuestionIndex + 1 }} / {{ questions.length }}</p>
        <p>正确答案数量：{{ correctCount }} / {{ questions.length }}</p>
        <p>当前积分：{{ score }}</p>
        <div class="controls">
          <button @click="prevQuestion" :disabled="currentQuestionIndex === 0">上一题</button>
          <button @click="nextQuestion" :disabled="currentQuestionIndex === questions.length - 1">下一题</button>
        </div>
      </el-main>

      <!-- 中间的底部 Footer -->
      <el-footer></el-footer>
    </el-container>

    <!-- 右侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="../../images/img/right.jpg" alt="Right Image Description" class="full-height-image">
    </el-aside>
  </el-container>
</template>

<script setup>
import { ref, onBeforeMount, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import router from "@/routes/index.js";
import axios from "@/utils/axios.js";

const route = useRoute();
const results = ref([]);
const questions = ref([]);
const resultsLoaded = ref(false);
const currentQuestionIndex = ref(0);
const correctCount = ref(0);

const score = ref(0); // 新增：积分变量

watchEffect(() => {
  if (route.query.answers) {
    try {
      results.value = JSON.parse(route.query.answers);
      questions.value = JSON.parse(route.query.questions);
      resultsLoaded.value = true;
      calculateCorrectCountAndScore();
    } catch (error) {
      console.error('解析答案失败:', error);
    }
  }
});

const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;

  }
};
const goBack=()=>{
 window.location.href='/'
  addScore()
}
const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
    calculateCorrectCountAndScore();
  }
};

// 更新此函数以同时计算正确答案数量和积分
const calculateCorrectCountAndScore = () => {
  let count = 0;
  let points = 0; // 积分初始化
  for (let i = 0; i < questions.value.length; i++) {
    if (questions.value[i].answer === results.value[i]) {
      count++;
      points += 1; // 每答对一题加10分
    }
  }
  correctCount.value = count;
  score.value = points; // 更新积分

};


const addScore=()=>{
  axios.get('/users/addScore?point='+score.value).then(res=>{

  })
}
</script>

<style scoped>
.option-label.selected {
  color: #409eff; /* 选中答案的颜色 */
  font-weight: bold;
}
h1 {
  color: #333;
  margin-bottom: 20px;
}

.question {
  font-size: 1.2em;
  margin-bottom: 20px;
}

.option {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.option-label {
  margin-left: 10px;
}

.controls {
  display: flex;
  justify-content: space-between;
  margin-top: auto;
}

.controls button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: #5cb85c;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #4cae4c;
}



.result h2 {
  margin-bottom: 10px;
}

.result p {
  margin: 5px 0;
}

.full-height-aside {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  position: relative; /* 相对定位 */
}

.full-height-image {
  width: 100%;
  height: 100vh;
  object-fit: cover;
}
.header {
  position: relative;
  background-image: url("../../images/img/header.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.header {
  display: flex; /* 使 header 成为弹性盒子 */
  justify-content: space-between; /* 子元素在主轴上两端对齐 */
  align-items: center; /* 子元素垂直居中 */
  position: relative;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.centered-text-container {
  width: calc(100% - 50px); /* 计算出除去按钮宽度的空间 */
  text-align: center; /* 文本水平居中 */
}

.art-text {
  font-family: 'Arial', sans-serif;
  font-size: 2em;
  font-weight: bold;
  letter-spacing: 2px;
  text-transform: uppercase;
  background-image: linear-gradient(120deg, #a6cdef, #333);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  padding: 10px;
  border-radius: 8px;
  text-align: center; /* 文本水平居中 */
}

.header .btn-return {
  background-color: #337ab7; /* 蓝色背景 */
  border-color: #2e6da4; /* 蓝色边框 */
  color: #fff; /* 白色文字 */
}

/* 如果需要对 art-text 的布局进行调整，可以在这里修改 */
.header .art-text {
  /* 保留原有的样式 */
  display: inline-block; /* 使 div 行内显示，允许与按钮在同一行 */
  vertical-align: middle; /* 垂直居中对齐 */
}



</style>
