<template>

    <el-container>
      <!-- 左侧 Aside -->
      <el-aside width="300px" class="full-height-aside">
        <img src="/images/img/left.jpg" alt="Left Image Description" class="full-height-image">
      </el-aside>

      <!-- 中间部分 -->
      <el-container>
        <!-- 中间的头部 Header -->
        <el-header class="header" >
          <div class="art-text">中国诗词大会</div>
        </el-header>

        <!-- 中间的主体 Main -->
        <el-main >
          <div class="timer">
            <span class="time-label">剩余时间：</span>
            <span class="time-value">{{ timeLeft }}</span>
            <span class="time-unit">秒</span>
          </div>
          <div class="question">
            <!-- 显示当前问题 -->
            {{ questions[currentQuestionIndex].question }}
          </div>
          <div class="options">
            <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index"  class="option">
              <div v-if="index !== 'options_id'">
              <input type="radio" :id="`option-${index}`" name="answer" v-model="selectedOption" :value="option" @change="checkAnswer">
              <label :for="`option-${index}`" class="option-label" v-show="index !==0">{{ option }}</label>
              </div>
            </div>
          </div>
          <div class="controls">
            <button @click="submitAnswers" v-if="currentQuestionIndex === questions.length - 1">提交所有答案</button>
          </div>

<!--          <div v-if="showResult" class="result">-->
<!--            <h2>结果</h2>-->
<!--            <p>您的答案：{{ selectedOption }}</p>-->
<!--            <p>正确答案：{{ correctAnswer }}</p>-->
<!--            <p>剩余时间：{{ timeLeft }}</p>-->
<!--          </div>-->

        </el-main>

        <!-- 中间的底部 Footer -->
        <el-footer>

        </el-footer>
      </el-container>

      <!-- 右侧 Aside -->
      <el-aside width="300px" class="full-height-aside">
        <img src="/images/img/right.jpg" alt="Left Image Description" class="full-height-image">
      </el-aside>
    </el-container>
</template>



<script setup>
import {ref, computed, onMounted, reactive, onBeforeMount} from 'vue';
import axios from "@/utils/axios.js";
import router from "@/routes/index.js";

// 定义状态
const currentQuestionIndex = ref(0); // 当前问题索引
const selectedOption = ref(''); // 用户选择的答案
const showResult = ref(false); // 是否显示结果
let questions = reactive([
  {
    question: '',
    options: [],
    answer: ''
  }
]);

const answers = reactive(new Array(questions.length)); // 存储用户答案
// 计算当前问题的正确答案
const correctAnswer = computed(() => questions[currentQuestionIndex.value].answer);
/*计算器*/
const timeLimit = 30; // 每题限制时间为30秒
const timeLeft = ref(timeLimit); // 剩余时间
const timerId = ref(null); // 计时器ID
// 方法


const stopTimer = () => {
  clearInterval(timerId.value);
};

const resetTimer = () => {
  timeLeft.value = timeLimit;
  stopTimer();
};

// 方法
const startTimer = () => {
  timerId.value = setInterval(() => {
    if (timeLeft.value > 0) {
      timeLeft.value--;
    } else {
      checkAnswer(); // 如果时间到了则自动提交答案并跳转到下一题
    }
  }, 1000);
};

const checkAnswer = () => {
  answers[currentQuestionIndex.value] = selectedOption.value; // 保存当前答案
  nextQuestion(); // 自动跳转到下一题
};

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.length - 1) {
    currentQuestionIndex.value++;
    selectedOption.value = '';
    resetTimer();
    startTimer();
  } else {
    // 如果是最后一题，则停止计时器并提交答案
    stopTimer();
    submitAnswers();
  }
};


//初始化计时器
resetTimer();
startTimer();

const getList= async()=>{
  await axios.post("/questionbank/list").then(res=>{
    questions=res
  })
}
const submitAnswers = () => {
  // 提交所有答案的逻辑
  console.log('所有答案已提交:', answers);
  router.push({
    path: '/results', // 假设结果页的路由名为 'Results'
    query: {
      questions: JSON.stringify(questions),
      answers: JSON.stringify(answers)
    }// 将答案字符串化后作为查询参数传递
  });
};



onBeforeMount(()=>{
  getList();
})

</script>


<style scoped>

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

.result {
  margin-top: 20px;
  text-align: left;
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
  background-image: url("/user/images/img/header.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;


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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.header-content {
  position: absolute; /* 绝对定位 */
  top: 50%; /* 顶部居中 */
  left: 50%; /* 左侧居中 */
  transform: translate(-50%, -50%); /* 文本居中 */
  z-index: 1; /* 确保文本在背景之上 */
}
.timer {
  display: flex;
  align-items: center;
  font-size: 1.2em;
  margin-bottom: 20px;
  color: #333;
}

.time-label {
  font-weight: bold;
  margin-right: 5px;
}

.time-value {
  font-size: 1.5em;
  font-weight: bold;
  color: #e74c3c; /* 颜色可以根据需要调整 */
}

.time-unit {
  font-size: 1.2em;
  margin-left: 5px;
}

</style>
