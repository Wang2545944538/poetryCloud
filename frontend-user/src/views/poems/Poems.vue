<script setup>
import {computed, onMounted, reactive, ref} from 'vue';
import Headler from '@/views/headler.vue';
import axios from "../../utils/axios.js";
import router from "@/routes/index.js";

const data=reactive({
  collections:[],
})

onMounted(()=>{
  axios.get("/collection/select").then(res=>{
         data.collections=res.data.records
          console.log(data.collections)

  })

})

const classNames = [
  "img-wrapper slower0",
  "img-wrapper slower1",
  "img-wrapper slower2",
  "img-wrapper slower3",
  "img-wrapper slower4",
  "img-wrapper slower5",
  "img-wrapper slower6",
  "img-wrapper slower7",
  "img-wrapper slower8",
]; // 定义所有可能的类名

const getImageClasses = computed(() => {

    let randomIndex = Math.floor(Math.random() * 9); // 生成一个随机索引
    console.log(randomIndex)
    // 返回一个对象，包含随机生成的类名
    return classNames[randomIndex.value]; // 使用点操作符来创建一个响应式的对象

});


const getImageClasses1 = (index)=>{
  let randomIndex = Math.floor(Math.random() * 5); // 生成一个随机索引

  return classNames[randomIndex]; // 使用点操作符来创建一个响应式的对象
}
function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/assets/${imageName}`;
}




const searchQuery = ref(''); // 创建响应式数据

const handleSearch = () => {
  if (searchQuery.value.trim() !== '') {
    router.push({name:'showCollection',params:{collection_name:searchQuery.value}})

  } else {
    alert("请输入合适的诗人")
  }
};

</script>
<template>
  <Headler />
  <div
      class="external"
      style="background-image: url('src/images/img/8278cfb64ef52c5ac6938f5d735268c443073da888ee04-M0W8m6.jpeg');"
  >

    <div class="search-container">
      <input
          type="text"
          id="search-box"
          v-model="searchQuery"
          placeholder="搜索..."
      />
      <button id="search-btn" @click="handleSearch">搜索</button>
    </div>

    <div class="horizontal-scroll-wrapper">
      <template
          v-for="(item, index) in data.collections"
          :key="index">
        <router-link :to="{ name: 'LookCollection', params: { collection_id: item.collection_id } }"
                     :class="getImageClasses1(index)" style="text-decoration: none">
          <div class="">
            <img :src="getPublicImageUrl(item.collection_avatar)" alt="" />
            <div class="h2" style="font-size: 25px;text-align: center;">{{ item.collection_name }}</div>
          </div>
        </router-link>
      </template>
    </div>

  </div>
</template>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Great+Vibes&display=swap" rel="stylesheet');

h2 {
  font-family: 'Great Vibes', cursive;
  text-align: center;
  font-size: 3em; /* 根据需要调整字体大小 */
}



body {
  font-family: Hepta Slab, sans-serif;
  font-weight: 500;
}

* {
  box-sizing: border-box;
}

::-webkit-scrollbar {
  width: 1px;
  height: 1px;
}

::-webkit-scrollbar-button {
  width: 1px;
  height: 1px;
}

.external {
  width: 100vw;
  overflow: hidden;
  height: 100vh;
  background-size: cover;
  background-repeat: no-repeat;
}

.horizontal-scroll-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vh;
  transform: rotate(-90deg) translate3d(0, -100vh, 0);
  transform-origin: right top;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 0;
  height: 100vw;
  perspective: 1px;
  transform-style: preserve-3d;
  margin-top: 5rem;
  padding-bottom: 10rem;
}

.img-wrapper {
  transform: rotate(90deg);
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 40vh;
  transform-origin: 70% 70%;
 /* transform: rotate(90deg) translateZ(.1px) scale(0.9) translateX(0px) translateY(-3vh);*/
  transition: 1s;
}

.img-wrapper:hover {
  min-height: 65vh;
}



.slower0 {

 /* transform: rotate(90deg) translateZ(-.2px) scale(1.1) translateX(0%) translateY(-10vh);*/
  transform: rotate(90deg) translateZ(-.2px) scale(1.1) translateX(0%) translateY(1vh);
}

.slower1 {
  transform: rotate(90deg) translateZ(-.25px) scale(1.05) translateX(0%) translateY(2vh);
}

.slower2 {
  transform: rotate(90deg) translateZ(-.3px) scale(1.3) translateX(0%) translateY(6vh);
}

.slower3{
  transform: rotate(90deg) translateZ(-.2px) scale(1.1) translateX(0%) translateY(-4vh);
}

.slower4 {
  transform: rotate(90deg) translateZ(.15px) scale(0.8) translateX(0%) translateY(14vh);
}

.slower5 {
  transform: rotate(90deg) translateZ(.05px) scale(0.8) translateX(0%) translateY(10vh);
}

.slower6 {
  transform: rotate(90deg) translateZ(.22px) scale(0.7) translateX(-10vh) translateY(-10vh);
}

.slower7 {
  transform: rotate(90deg) translateZ(-.15px) scale(1.15) translateX(0%) translateY(0%);
}

.slower8 {
  transform: rotate(90deg) translateZ(-.2px) scale(1.1) translateX(25vh) translateY(-8vh);
}

.scroll-info,
header {
  position: absolute;
  left: 1rem;
}


header {
  bottom: 1rem;
}

header p{
  color: #241c65;
}
.scroll-info {
  top: 6rem;
}
.scroll-info{
  color: #241c65;
}

a {
  color: inherit;
  font-weight: 500;

}

h1 {
  font-weight: 300;
  font-size: 1rem;
}

.img-wrapper a {
  overflow: hidden;
  display: block;
  padding: 1vh;
  background: #efecdb;
  box-shadow: 0 10px 50px #5f2f1182;
}

.img-wrapper img {
  max-width: 45vh;
  max-height: 50vh;
  transition: .5s;
  vertical-align: top;
  filter: saturate(40%) sepia(30%) hue-rotate(5deg);
}

a:hover img {
  filter: none;
}

p {
  margin: 0;
}

.scroll-info {
  display: flex;
  align-items: center;
}

.icon svg {
  width: 50px;
  fill: currentcolor;
}

body{

  margin: 0;
  font-family: '宋体', sans-serif;
  background-color: #f5f5f5;
}

.search-container {
  position: fixed; /* 固定在顶部 */
  top: 100px; /* 向下移动100px，相当于两个位置的距离 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 向左移动自身宽度的一半，实现居中 */
  background-color: rgba(255, 255, 255, 0.5); /* 半透明背景 */
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  display: flex;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 1000; /* 确保在最顶层 */
}

#search-box {
  flex: 1;
  padding: 10px;
  border: none;
  outline: none;
  font-size: 28px;
  color: #333; /* 更显眼的文字颜色 */
  background-color: transparent; /* 输入框背景透明 */
}

#search-box::placeholder {
  color: #666; /* 占位符颜色 */
}

#search-btn {
  background-color: rgba(14, 82, 126, 0.81); /* 更显眼的按钮背景颜色 */
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
  border-radius: 0 5px 5px 0;
}

#search-btn:hover {
  background-color: #c0392b; /* 按钮悬停时的背景颜色 */
}



</style>