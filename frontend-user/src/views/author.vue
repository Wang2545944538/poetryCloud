<template>
    <div class="maxbox">
        <div class="poet-container">
            <div class="poet-selector">
                <h2>选择诗人</h2>
                <div class="poet-list">
                    <button v-for="poet in poets" :key="poet.author_id" class="poet-button"
                        :class="{ active: selectedPoetId === poet.author_id }" @click="selectPoet(poet.author_id)">
                        <img :src="getPublicImageUrl(poet.author_img)" class="poet-avatar-mini">
                        {{ poet.author_name }}
                    </button>
                </div>
            </div>
            <div class="poem-grid">
                <div v-for="poem in filteredPoems" :key="poem.id" class="poem-card">
                    <div class="poem-info">
                        <h3 class="poem-name">{{ poem.title }}</h3>
                        <div class="poem-meta">
                            <span class="poem-author">{{ poem.author }}</span>
                        </div>
                        <p class="poem-content">
                            {{ poem.content }}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from "@/utils/axios.js";

let poets = ref([]);
let poems = ref([]);
let selectedPoetId = ref(null); // 响应式数据，用于跟踪当前选中的诗人ID

onMounted(() => {
    getPoetsAndSelectFirst();
    getPoems();
});

const getPoetsAndSelectFirst = async () => {
    try {
        const res = await axios.post("/author/authorList");
        poets.value = res.data;
        if (res.data.length > 0) {
            // 默认选择第一个诗人
            selectPoet(res.data[0].author_id);
        }
    } catch (error) {
        console.error("Error fetching poets:", error);
    }
}

const getPoems = async () => {
    try {
        const res = await axios.post("/poem/poemList");
        poems.value = res.data;
    } catch (error) {
        console.error("Error fetching poems:", error);
    }
}

function getPublicImageUrl(imageName) {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}

const filteredPoems = computed(() => {
    if (!selectedPoetId.value) {
        return [];
    }
    return poems.value.filter(poem => poem.author_id === selectedPoetId.value);
});

const selectPoet = (authorId) => {
    selectedPoetId.value = authorId;
};
</script>


<style scoped>
.maxbox {
    display: flex;
    justify-content: center;
    padding: 40px;
    max-width: 1400px;
    margin: 0 auto;
    align-items: center;
}

.poet-container {
    display: flex;
    flex-direction: column;
    flex: 1;
}

.poet-selector {
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 30px;
}

.poet-list {
    display: flex;
    flex-wrap: wrap;
    margin: -10px;
}

.poet-button {
    display: flex;
    align-items: center;
    color: #333;
    border: none;
    border-radius: 999px;
    padding: 10px 20px;
    margin: 10px;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s, transform 0.3s, box-shadow 0.3s;
    /* 添加 transform 和 box-shadow 的过渡效果 */
}

.poet-button.active {
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.poet-button:hover {
    transform: translateY(-5px);
    /* 减少一点位移，看起来更自然 */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    /* 增加阴影的范围和强度 */
}

.poet-avatar-mini {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    margin-right: 10px;
}

/* 诗歌卡片网格布局 */
.poem-grid {
    display: flex;
    /* 使用flex布局 */
    flex-wrap: wrap;
    /* 允许换行 */
    justify-content: space-between;
    /* 在主轴上均匀分布项目 */
    padding: 20px;
    /* 内边距 */
}

/* 单个诗歌卡片样式 */
.poem-card {
    background-color: #f9f9f9;
    /* 轻柔的背景色 */
    border-radius: 15px;
    /* 圆角边框 */
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    /* 过渡效果 */
    width: calc(25% - 30px);
    /* 卡片宽度，一行四个，减去间隙 */
    margin: 15px;
    /* 外边距 */
}

/* 鼠标悬停时的卡片样式 */
.poem-card:hover {
    transform: translateY(-5px);
    /* 略微上移 */
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
    /* 增强阴影效果 */
}

/* 诗歌信息容器样式 */
.poem-info {
    padding: 20px;
    /* 内边距 */
    text-align: center;
    /* 文本居中 */
}

/* 诗歌名称样式 */
.poem-name {
    margin-bottom: 5px;
    /* 下边距 */
    font-size: 1.6em;
    /* 字体大小 */
    color: #444;
    /* 字体颜色 */
    font-family: '楷体', serif;
    /* 字体样式 */
}

/* 诗歌元信息样式（作者等） */
.poem-meta {
    margin-bottom: 15px;
    /* 下边距 */
}

/* 作者名称样式 */
.poem-author {
    color: #777;
    /* 字体颜色 */
    font-size: 1.2em;
    /* 字体大小 */
    font-style: italic;
    /* 斜体 */
}

/* 诗歌内容样式 */
.poem-content {
    font-size: 1.4em;
    /* 字体大小 */
    color: #555;
    /* 字体颜色 */
    font-family: '宋体', serif;
    /* 字体样式 */
    white-space: pre-line;
    /* 保持空白符序列，但是允许换行符 */
    overflow: hidden;
    /* 超出部分隐藏 */
    text-overflow: ellipsis;
    /* 显示省略号 */
    display: -webkit-box;
    /* 使用弹性盒子布局 */
    -webkit-line-clamp: 1;
    /* 限制显示行数 */
    -webkit-box-orient: vertical;
    /* 垂直排列 */
    margin: 0;
    /* 清除默认边距 */
}

/* 响应式设计：小于768px时，每行显示两个卡片 */
@media (max-width: 768px) {
    .poem-card {
        width: calc(50% - 30px);
        /* 卡片宽度，一行两个，减去间隙 */
    }
}

/* 响应式设计：小于480px时，每行显示一个卡片 */
@media (max-width: 480px) {
    .poem-card {
        width: calc(100% - 30px);
        /* 卡片宽度，一行一个，减去间隙 */
    }
}
</style>