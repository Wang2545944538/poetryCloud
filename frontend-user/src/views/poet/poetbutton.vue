<template>
    <div class="title">点击进入历朝著名诗人足迹</div>
    <div class="controls">
        <button class="scroll-btn" id="left-btn" @click="scrollLeft">&#10094;</button>
        <div class="history_bigbox" id="history-bigbox">
            <div v-for="dynasty in dynastydata.dynastycollections" :key="dynasty.dynasty_id" class="history_box">
                <button class="poet_button" @click="poethistory(dynasty.dynasty_id)">
                    <div class="text">
                        <p>{{ dynasty.dynasty_name }}</p>
                    </div>
                </button>
            </div>
        </div>
        <button class="scroll-btn" id="right-btn" @click="scrollRight">&#10095;</button>
    </div>
</template>
<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from "@/utils/axios.js";
const dynastydata = reactive({
    dynastycollections: [],
});
// 获取朝代
onMounted(() => {
    getdynasty();
});
const getdynasty = () => {
    axios.get("/dynasty/dynastyList")
        .then(res => {
            dynastydata.dynastycollections = res.data;
        })
        .catch(error => {
            console.error("Error fetching authors:", error);
        });
}

const router = useRouter();
const poethistory = (dynasty_id) => {
    router.push({ name: 'poethistory', params: { id: dynasty_id } });
};
const scrollRight = () => {
    const bigbox = document.getElementById('history-bigbox');
    bigbox.scrollBy({ left: 200, behavior: 'smooth' });
};

const scrollLeft = () => {
    const bigbox = document.getElementById('history-bigbox');
    bigbox.scrollBy({ left: -200, behavior: 'smooth' });
};
</script>
<style scoped>
* {
    font-family: 'MyCustomFont', 'Helvetica Neue', Arial, sans-serif;
    /* 初始化 */
    margin: 0;
    padding: 0;
    /* 元素的总宽度和高度包含内边距和边框 */
    box-sizing: border-box;
}

.controls {
    display: flex;
    align-items: center;
    overflow: hidden;
    margin-bottom: 50px;
}

.history_bigbox {
    display: flex;
    flex-wrap: nowrap;
    overflow-x: auto;
    scroll-snap-type: x mandatory;
}



.scroll-btn {
    background: none;
    border: none;
    font-size: 2em;
    cursor: pointer;
    color: #333;
}

#left-btn {
    margin-right: 10px;
}

#right-btn {
    margin-left: 10px;
}



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

.history_box {
    margin-top: 50px;
    width: 100%;
    position: relative;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    /* 允许换行 */
    align-items: stretch;
    /* 让子元素高度填满容器 */
    justify-content: center;
    /* 从行的开始处排列 */
    scroll-snap-align: start;
}
.history_bigbox::-webkit-scrollbar {
    display: none; /* 对于基于WebKit的浏览器隐藏滚动条 */
}
.poet_button img {
    width: 100%;
}

.text {
    width: 200px;
    height: 200px;
    background-image: url(/user/images/img/back.png);
    background-size: contain;
    background-position: center center;
    background-repeat: no-repeat;
    display: flex;
    align-items: center;
    justify-content: center;
}

.poet_button {

    font-size: 50px;
    margin-top: 30px;
    margin: 10px;
    border: none;
    border-radius: 10px;
    width: 200px;
    height: 200px;
    background: #e9ecef;
    color: #333;
    box-shadow:
        7px 7px 12px rgba(0, 0, 0, .4),
        -7px -7px 12px rgba(255, 255, 255, .9);
    transition: .2s all;
    box-shadow:
        7px 7px 12px rgba(0, 0, 0, .4),
        -7px -7px 12px rgba(255, 255, 255, .9),
        inset 0 0 0x rgba(255, 255, 255, .9),
        inset 0 0 0 rgba(0, 0, 0, .4);

    &:active {
        box-shadow:
            0 0 0 rgba(0, 0, 0, .4),
            0 0 0 rgba(255, 255, 255, .9),
            inset -7px -7px 12px rgba(255, 255, 255, .9),
            inset 7px 7px 12px rgba(0, 0, 0, .4);
    }
}
</style>