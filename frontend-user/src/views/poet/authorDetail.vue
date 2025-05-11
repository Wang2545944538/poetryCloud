<template>
    <headler />

    <el-container class="bigbox">
        <el-header class="background_img">
            <h1>{{ author.author_name }}</h1>
        </el-header>
        <div class="back" @click="goBack">
            <img src="/images/icon/back.webp" style="width: 35px;" alt="">
            <p class="back_text" style="">返回</p>
        </div>
        <el-main>
            <el-row>
                <el-col class="poet_img_box" :span="8">
                    <el-image style="width: 300px;height: 300px;" class="poet_img"
                        :src="getPublicImageUrl(author.author_img)" :alt="author.author_name"></el-image>
                </el-col>
                <el-col :span="16">
                    <div>
                        <h2>{{ author.author_name }}</h2>
                        <p>{{ author.author_intro }}</p>
                    </div>
                    <el-divider></el-divider>
                    <div>
                        <h3>代表作品</h3>
                        <ul>
                            <li v-for="poem in Poemdata.Poemcollections" :key="poem">
                                <div class="poembutton" @click="poemdetail(poem.poem_id)">
                                    {{ poem.title }}
                                </div>
                            </li>
                        </ul>
                    </div>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script setup>
import axios from "@/utils/axios.js";
import headler from "../../views/headler.vue";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElContainer, ElHeader, ElMain, ElRow, ElCol, ElImage, ElDivider } from 'element-plus';

const router = useRouter();
const goBack = () => {
    router.go(-1);
}
const poemdetail = (poem_id) => {

    router.push({ name: 'poemDetail', params: { id: poem_id } });
};
const route = useRoute();
let author = reactive({
    author_name: '',
    author_intro: '',
    author_img: ''
});
const Poemdata = reactive({
    Poemcollections: [],
});
onMounted(async () => {
    const authorId = route.params.id; // 获取从上一个页面传递来的id
    getAuthor(authorId);
    getPoems(authorId);
});
const getAuthor = (authorId) => {
    axios.get("/author/findByIdauthor?id=" + authorId)
        .then(res => {
            author.author_name = res.author_name;
            author.author_intro = res.author_intro;
            author.author_img = res.author_img;
        })
}
const getPoems = (authorId) => {
    axios.get("/author/findByIdPoems?id=" + authorId)
        .then(res => {
            Poemdata.Poemcollections = res
            console.log(Poemdata.Poemcollections);

        })
}

function getPublicImageUrl(imageName) {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}
</script>
<style scoped>
* {
    margin: 0;
    padding: 0;
}
.poembutton:hover{
color: #666;
}


.back {
    margin-top: 10px;
    margin-right: 20px;
    display: flex;
    justify-content: right;
    align-items: center;
    text-decoration: none;
}

.back_text {
    color: #000000;
    text-decoration: none;
    padding-bottom: 5px;
}

.back_text:hover {
    color: #656464;
}

.bigbox {
    width: 100%;
    height: 100%;
    font-family: 'MyCustomFont', 'Helvetica Neue', Arial, sans-serif;
}

.background_img {
    width: 100%;
    height: 30vh;
    background-image: url(/user/images/img/白露.jpg);
    background-size: cover;
    background-position: center center;
    background-repeat: no-repeat;

}

.background_img h1 {
    font-size: 70px;
    color: white;

    line-height: 30vh;
}

.title {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

.poet_img {
    border-radius: 10px;
}

.container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.card {
    width: 300px;
    margin: 10px;
    cursor: pointer;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
}

.card:hover {
    transform: translateY(-5px);
}

.img-box img {
    width: 100%;
    height: auto;
    display: block;
}

.text-box {
    padding: 15px;
}

.text-box h2 {
    font-size: 20px;
    margin-bottom: 10px;
}

.text-box p {
    font-size: 14px;
    color: #666;
}

.el-header {
    /* background-color: #b3c0d1; */
    color: #333;
    line-height: 60px;
    text-align: center;
}

.el-main {
    /* background-color: #e9eef3; */
    margin-top: 10vh;
    color: #333;
    text-align: left;
}

.poet_img_box {
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;

}

.poet_img {
    /* 添加盒子阴影 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    /* 水平偏移 垂直偏移 模糊半径 阴影颜色 */
    /* 过渡效果，使hover时的变化更平滑 */
    transition: transform 0.3s ease;
    /* 变化的属性 时间 曲线 */
}

/* hover时的样式 */
.poet_img:hover {
    /* 放大1.05倍 */
    transform: scale(1.05);
}

.el-row {
    display: flex;
    justify-content: space-around;
}

.el-image {
    width: 100%;
    height: auto;
}

.el-divider {
    margin: 20px 0;
}

ul {
    list-style-type: none;
    padding: 0;
}

ul li {
    font-size: 16px;
    margin-bottom: 10px;
}

h3 {
    font-size: 18px;
    margin-bottom: 15px;
}
</style>
