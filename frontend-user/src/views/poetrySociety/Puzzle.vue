<template>

  <el-container>
    <!-- 左侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="/user/images/img/left.jpg" alt="Left Image Description" class="full-height-image">
    </el-aside>

    <!-- 中间部分 -->
    <el-container>
      <!-- 中间的头部 Header -->
      <el-header class="header" >
        <button class="btn btn-default btn-return" @click="goBack">返回</button>
        <div class="centered-text-container">
          <div class="art-text">拼图小游戲</div>
        </div>


      </el-header>

      <!-- 中间的主体 Main -->
      <el-main >
        <div class="original-image">
          <img :src="originalImage" alt="Original Image" />
        </div>
        <div class="box" :style="{'background-image': backgroundImage}">
          <ul class="puzzle-wrap">
            <li
                :class="{'puzzle': true, 'puzzle-empty': puzzle === null}"
                v-for="(puzzle, index) in puzzles"
                :key="index"
                @click="moveFn(index)">
              <img v-if="puzzle" :src="puzzle" alt="puzzle piece" />
            </li>
          </ul>
          <button class="btn btn-warning btn-block btn-reset" @click="render">重置游戏</button>
        </div>
      </el-main>

      <!-- 中间的底部 Footer -->
      <el-footer>

      </el-footer>
    </el-container>

    <!-- 右侧 Aside -->
    <el-aside width="300px" class="full-height-aside">
      <img src="/user/images/img/right.jpg" alt="Left Image Description" class="full-height-image">
    </el-aside>
  </el-container>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import {useRoute} from "vue-router";
import routes from "@/routes/index.js";
import router from "@/routes/index.js";
const puzzles = ref([]);
const backgroundImage = ref('');
const originalImage = ref(''); // 新增一个 ref 用于存储原图的 URL
const route = useRoute();
const myImageUrl=route.query.imageUrl

function splitImage(image) {
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');
  canvas.width = canvas.height = 400;
  ctx.drawImage(image, 0, 0, 400, 400);

  const pieces = [];
  for (let y = 0; y < 4; y++) {
    for (let x = 0; x < 4; x++) {
      const pieceCanvas = document.createElement('canvas');
      pieceCanvas.width = pieceCanvas.height = 100;
      const pieceCtx = pieceCanvas.getContext('2d');
      pieceCtx.drawImage(canvas, x * 100, y * 100, 100, 100, 0, 0, 100, 100);
      pieces.push(pieceCanvas.toDataURL());
    }
  }
  pieces[15]=""
  return pieces;
}

const goBack=()=>{
  router.back(); //
}
function loadImage(url) {
  return new Promise((resolve, reject) => {
    const image = new Image();
    image.onload = () => {
      backgroundImage.value = `url(${image.src})`; // 存储原始图片的URL
      originalImage.value = image.src; // 存储原图的 URL
      resolve(image);
    };
    image.onerror = reject;
    image.src = url;
  });
}

function render() {
  puzzles.value = [];

  // 生成一个从 1 到 10 的随机整数
  const randomNumber = Math.floor(Math.random() * 7) + 1;

  loadImage(`/user/images/puzzle/${randomNumber}.webp`).then(image => {
    const pieces = splitImage(image);


    for (let i = pieces.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [pieces[i], pieces[j]] = [pieces[j], pieces[i]];
    }
    puzzles.value = pieces;


    // 清除背景，因为我们现在显示的是拼图块
    backgroundImage.value = '';
  });
}

function moveFn(index) {
  let curNum = puzzles.value[index];
  let leftNum = puzzles.value[index - 1];
  let rightNum = puzzles.value[index + 1];
  let topNum = puzzles.value[index - 4];
  let bottomNum = puzzles.value[index + 4];

  if (leftNum === '' && index % 4 !== 0) {
    puzzles.value[index - 1] = curNum;
    puzzles.value[index] = '';
  } else if (rightNum === '' && index % 4 !== 3) {
    puzzles.value[index + 1] = curNum;
    puzzles.value[index] = '';
  } else if (topNum === '') {
    puzzles.value[index - 4] = curNum;
    puzzles.value[index] = '';
  } else if (bottomNum === '') {
    puzzles.value[index + 4] = curNum;
    puzzles.value[index] = '';
  }

  passFn();
}

function passFn() {
  if (puzzles.value[15] === '') {
    const newPuzzles = puzzles.value.slice(0, 15);
    console.log('Current Puzzles:', newPuzzles); // 打印当前的拼图数组
    const isPass = newPuzzles.every((e, i) => e === i + 1);
    console.log('Is Pass:', isPass); // 打印是否通过的判断结果
    if (isPass) {
      alert('恭喜，闯关成功！');
      // 展示原始图片
      backgroundImage.value = backgroundImage.value;
    }
  }
}

onMounted(() => {
  render();
});
</script>



<style>
@import url('./assets/css/bootstrap.min.css');

body {
  font-family: Arial, "Microsoft YaHei";
}

.box {
  width: 400px;
  margin: 50px auto 0;
}

.puzzle-wrap {
  width: 400px;
  height: 400px;
  margin-bottom: 40px;
  padding: 0;
  background: #ccc;
  list-style: none;
}

.puzzle {
  float: left;
  width: 100px;
  height: 100px;
  font-size: 20px;
  background: #f90;
  text-align: center;
  line-height: 100px;
  border: 1px solid #ccc;
  box-shadow: 1px 1px 4px;
  text-shadow: 1px 1px 1px #B9B4B4;
  cursor: pointer;
}

.puzzle-empty {
  background: #ccc;
  box-shadow: inset 2px 2px 18px;
}

.btn-reset {
  box-shadow: inset 2px 2px 18px;
}

 h1 {
   color: #333;
   margin-bottom: 20px;
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


.original-image img {
  width: 150px; /* 设置原图的宽度为400px */
  height: 150px; /* 设置原图的高度为400px */

}


.header .btn-return:hover {
  background-color: #286090; /* 鼠标悬停时更深的蓝色背景 */
  border-color: #204d74; /* 鼠标悬停时更深的蓝色边框 */
}

/* 如果需要对 art-text 的布局进行调整，可以在这里修改 */
.header .art-text {
  /* 保留原有的样式 */
  display: inline-block; /* 使 div 行内显示，允许与按钮在同一行 */
  vertical-align: middle; /* 垂直居中对齐 */
}

</style>

