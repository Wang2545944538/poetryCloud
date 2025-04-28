<template>
    <div class="maxbox">
        <div class="theme-container">
            <div class="theme-selector">
                <h2>选择主题</h2>
                <div class="theme-list">
                    <button v-for="theme in state.themes"
                            :key="theme.theme_id"
                            :class="{ active: selectedThemes.includes(theme.theme_id) }"
                            @click="toggleSelection('themes', theme.theme_id)" class="theme-button">
                        {{ theme.t_type }}
                    </button>
                </div>
            </div>
            <div class="poem-grid">
                <div v-for="poem in state.poems" :key="poem.id" class="poem-card" @click="goToPoemDetail(poem.poem_id)">
                    <div class="poem-info">
                        <h3 class="poem-name">{{ poem.title }}</h3>
                        <div class="poem-meta">
                            <span class="poem-author">{{ poem.author.author_name }}</span>
                        </div>
                        <p class="poem-content">
                            {{ poem.content }}
                        </p>
                    </div>
                </div>
            </div>
          <!-- 分页控件 -->
          <el-pagination
              background
              layout="prev, pager, next"
              :total="state.total"
              :page-size="state.pageSize"
              :current-page="state.currentPage"
              @current-change="changePage"
          />
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, reactive} from 'vue';
import axios from "@/utils/axios.js";
import {useRouter} from "vue-router";

const state = reactive({
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 8, // 分页大小
  poems:[],
  themes:[],
  authors:[]
});

onMounted(() => {
  getThemes();
  getPoems();
})

const changePage = (val) => {
  state.currentPage = val;
  getPoems();
}

const getThemes = () => {
  axios.get("/theme/themeList")
      .then(res=>{
        state.themes = res.data;
      })
}

const router = useRouter();
const goToPoemDetail = (poemId) => {
  // 一个路由名称为"poemDetail"，它用于显示诗歌的详细信息
  router.push({ name: 'poemDetail', params: { id: poemId } });
};

const selectedThemes = ref([]);

const getPoems = () => {
  const params = {
    currentPage: state.currentPage,
    pageSize: state.pageSize,
    authorIds: null,
    dynastyIds: null,
    genreIds: null,
    themeIds: selectedThemes.value.length ? selectedThemes.value : null,
    title:null
  };
  axios.post("/poem/getPoems",params)
      .then(res=>{
        state.poems=res.data.records;
        state.total=res.data.total;
      })
}

const toggleSelection = (type, id) => {
  const selectedList = {
    themes: selectedThemes
  }[type];
  const index = selectedList.value.indexOf(id);
  if (index > -1) {
    selectedList.value.splice(index, 1);
  } else {
    selectedList.value.push(id);
  }
  getPoems();
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

.theme-container {
    display: flex;
    flex-direction: column;
    flex: 1;
}

.theme-selector {
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 30px;
}

.theme-list {
    display: flex;
    flex-wrap: wrap;
    margin: -10px;
}

.theme-button {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #333;
    background-color: #f0f0f0;
    border: none;
    border-radius: 999px;
    padding: 10px 20px;
    margin: 10px;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s, transform 0.3s, box-shadow 0.3s;
}

.theme-button.active {
    background-color: #007bff;
    color: #fff;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.theme-button:hover {
    transform: translateY(-3px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 诗歌卡片网格布局 */
.poem-grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding: 20px;
}

/* 单个诗歌卡片样式 */
.poem-card {
    background-color: #f9f9f9;
    border-radius: 15px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    width: calc(25% - 30px);
    margin: 15px;
}

/* 鼠标悬停时的卡片样式 */
.poem-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

/* 诗歌信息容器样式 */
.poem-info {
    padding: 20px;
    text-align: center;
}

/* 诗歌名称样式 */
.poem-name {
    margin-bottom: 5px;
    font-size: 1.6em;
    color: #444;
    font-family: '楷体', serif;
}

/* 诗歌元信息样式（作者等） */
.poem-meta {
    margin-bottom: 15px;
}

/* 作者名称样式 */
.poem-author {
    color: #777;
    font-size: 1.2em;
    font-style: italic;
}

/* 诗歌内容样式 */
.poem-content {
    font-size: 1.4em;
    color: #555;
    font-family: '宋体', serif;
    white-space: pre-line;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    margin: 0;
}

/* 响应式设计：小于768px时，每行显示两个卡片 */
@media (max-width: 768px) {
    .poem-card {
        width: calc(50% - 30px);
    }
}

/* 响应式设计：小于480px时，每行显示一个卡片 */
@media (max-width: 480px) {
    .poem-card {
        width: calc(100% - 30px);
    }
}
</style>
