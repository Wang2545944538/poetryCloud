<template>

  <el-container>
    <!-- 左侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="../../images/img/poetryLeft.jpg" alt="Left Image Description" class="full-height-image">
    </el-aside>

    <!-- 中间部分 -->
    <el-container>
      <!-- 中间的头部 Header -->
      <el-header class="header">
        <button class="btn btn-default btn-return" @click="goBack">返回</button>
        <div class="centered-text-container">
          <div class="art-text">点字成诗</div>
        </div>


      </el-header>

      <!-- 中间的主体 Main -->
      <el-main>
        <div class="game-container">
          <div class="poem">
            <span v-for="(char, index) in poem" :key="index">{{ char }}</span>
          </div>

          <div class="characters-grid">
            <!-- 使用characters数组中的前9个元素来填充3x3网格 -->

            <button
                v-for="(char, index) in characters.slice(0, 9)"
                :key="index"
                @click="addChar(char)"
                :disabled="poem.includes(char)"
                class="character-button"
            >
              {{ char }}
            </button>
          </div>
          <div class="button-container">
            <button class="submit-button" @click="submitPoem">提交诗句</button>
          </div>
        </div>

      </el-main>

      <!-- 中间的底部 Footer -->
      <el-footer></el-footer>
    </el-container>

    <!-- 右侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="../../images/img/poetryRight.jpg" alt="Left Image Description" class="full-height-image">
    </el-aside>
  </el-container>

</template>

<script setup>
import {onMounted, ref} from 'vue';
import axios from "@/utils/axios.js";
import router from "@/routes/index.js";

let correctPoem = '春江花月夜'; // 正确的诗句
const poem = ref([]); // 当前诗句
const characters = ref(['春', '江', '花', '月', '夜', '柳', '云', '雪', '风', '雨']); // 可选汉字
const getList=()=>{
  axios.post("/wordsintopoems/list").then(res=>{
      correctPoem=res.correctPoem
    const correctPoemString = res.content; // 假设correctPoem是返回数据的一个属性
    // 将字符串转换为字符数组
    const poemArray = Array.from(correctPoemString);
    characters.value=poemArray;
  })
}
onMounted(()=>{
  getList()
})
const goBack=()=>{
  router.back(); //
}
function addChar(char) {
  if (poem.value.length < correctPoem.length) {
    poem.value.push(char);
  }
}

function submitPoem() {
  if (poem.value.length === correctPoem.length) {
    if (isCorrectPoem(poem.value)) {
      alert('恭喜你，诗句正确！');
      router.back(); //
    } else {
      alert('诗句不正确，请重试。');
    }
    poem.value = []; // 重置诗句
    shuffleCharacters(); // 重新打乱汉字顺序
  } else {
    alert('诗句未完成，请继续添加汉字。');
  }
}

function isCorrectPoem(submittedPoem) {
  return submittedPoem.join('') === correctPoem;
}

function shuffleCharacters() {
  for (let i = characters.value.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [characters.value[i], characters.value[j]] = [characters.value[j], characters.value[i]];
  }
}

shuffleCharacters(); // 初始化时打乱汉字顺序

onMounted(() => {

});
</script>

<style>
/* 整体容器样式 */
.game-container {
  font-family: '楷体', serif; /* 选择一个具有古典风格的字体 */
  color: #333; /* 文字颜色 */
  background-color: #f5f5dc; /* 背景颜色，可以选择一种淡雅的颜色 */
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* 阴影效果增加立体感 */
  text-align: center;
}

/* 诗句显示区域样式 */
.poem {
  font-size: 28px;
  line-height: 1.5;
  margin-bottom: 40px;
  letter-spacing: 2px; /* 字符间距 */
}

.characters-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3列 */
  grid-template-rows: repeat(3, 1fr); /* 3行 */
  grid-gap: 10px; /* 网格间隙 */

}

.character-button {
  padding: 10px;
  text-align: center;
  width: 50%; /* 按钮宽度占满整个网格单元 */
  height: 50%; /* 按钮高度占满整个网格单元 */
  box-sizing: border-box; /* 包括padding在内的宽度和高度 */
  aspect-ratio: 1 / 1; /* 设置宽高比为1:1，使其成为正方形 */
  background-color: #e7e7b5; /* 古典背景色 */
  cursor: pointer;
  transition: background-color 0.3s ease;
  outline: none; /* 去除默认焦点框 */
  box-shadow: inset 0 0 0 2px #dcdcdc; /* 内阴影模拟边框 */
  display: flex; /* 使用flex布局 */
  align-items: center; /* 垂直方向上居中对齐 */
  justify-content: center; /* 水平方向上居中对齐 */
}

button:disabled {
  background-color: #ccc;
}
.character-button:hover,
.character-button:focus {
  background-color: #d4d49a; /* 鼠标悬停或聚焦时的背景色 */
}

.character-button:active {
  transform: translateY(2px); /* 点击时的微小位移 */
}
.button-container {
  display: flex;
  justify-content: center; /* 使按钮居中 */
}
/* 提交按钮样式 */
.submit-button {
  margin-top: 20px;
  padding: 14px 32px;
  font-size: 20px;
  border: none;
  border-radius: 4px;
  background-color: #bada55; /* 古典按钮颜色 */
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.25);
}

.submit-button:hover,
.submit-button:focus {
  background-color: #a39e77; /* 鼠标悬停或聚焦时的背景色 */
}

.submit-button:active {
  transform: translateY(2px); /* 点击时的微小位移 */
}

/* 禁用状态下的按钮样式 */
.character-button[disabled],
.submit-button[disabled] {
  background-color: #ddd;
  cursor: not-allowed;
  opacity: 0.7;
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
*{
  margin: 0;
  padding: 0;
}
</style>
