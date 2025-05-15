<template>
    <el-carousel height="550px" direction="vertical" :autoplay="true">
        <el-carousel-item v-for="(image, index) in images" :key="index">
            <img :src="image.src" :alt="image.alt" style="width: 100%; height: 100%;">
            <!-- 添加文字显示 -->
            <div class="carousel-text"
                style="position: absolute; top: 30%; left: 25%; transform: translate(-50%, -50%); color: white; text-align: center; font-size: 2em; width: 100%;">
                {{ image.text }}
            </div>
            <div class="carousel-text"
                style="font-size: 30px; position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%); color: white; text-align: center; font-size: 2em; width: 100%;">
                {{ image.text2 }}
            </div>
        </el-carousel-item>
    </el-carousel>


</template>
<script setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'

const images = [
    { src: '/user/images/img/1.jpeg', alt: '图片1', text: '王勃-《滕王阁序》', text2: '落霞与孤鹜齐飞，秋水共长天一色' },
    { src: '/user/images/img/3.jpeg', alt: '图片2', text: '李白-《清平调·其一》', text2: '云想衣裳花想容，春风拂槛露华浓' },
    { src: '/user/images/img/2.jpeg', alt: '图片3', text: '元稹-《离思五首·其四》', text2: '曾经沧海难为水，除却巫山不是云' },
    { src: '/user/images/img/4.jpeg', alt: '图片4', text: '高适-《别董大》', text2: '莫愁前路无知己，天下谁人不识君' }
]


</script>


<style scoped>
.demonstration {
    color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}

.carousel-text {
    font-family: 'MyCustomFont', sans-serif;
    text-shadow: 2px 2px 4px #000000, 4px 4px 8px #222222;
}
</style>