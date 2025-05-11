<template>
  <headler />
  <el-carousel height="500px" direction="vertical" :autoplay="true" class="carousel">
    <el-carousel-item v-for="(image, index) in images" :key="index" class="carousel-item">
      <img :src="image.src" :alt="image.alt" class="carousel-image">
    </el-carousel-item>
  </el-carousel>
  <!-- 搜索容器 -->
  <div class="search-container">
    <el-input v-model="input" type="text" :prefix-icon="Search" placeholder="搜索诗词" class="search-input"/>
    <!-- 搜索按钮 -->
    <el-button type="primary" class="search-btn" @click="searchPoems">搜索</el-button>
  </div>
  <div class="filter-section">
    <div class="condition-title ">
      <span>已选:</span>
      <span v-if="selectedAuthors.length">
        作者:{{ selectedAuthors.map(id => findAuthorById(id).author_name).join('，') }}，
      </span>
      <span v-if="selectedDynasties.length">
        朝代:{{ selectedDynasties.map(id => findDynastyById(id).dynasty_name).join('，') }}，
      </span>
      <span v-if="selectedGenres.length">
        体裁:{{ selectedGenres.map(id => findGenreById(id).g_type).join('，') }}，
      </span>
      <span v-if="selectedThemes.length">
        题材:{{ selectedThemes.map(id => findThemeById(id).t_type).join('，') }}
      </span>
    </div>
    <!-- 添加显示选中筛选条件的区域 -->
    <div class="selected-filters">

      <span>
        (共筛选出 {{ state.total }} 首诗词)
      </span>
    </div>
    <el-collapse class="collapse">
      <el-collapse-item title="作者" name="1">
        <div class="options-container">
          <button class="option btn"
                  v-for="author in state.authors"
                  :key="author.author_id"
                  :class="{ active: selectedAuthors.includes(author.author_id) }"
                  @click="toggleSelection('authors', author.author_id)">
            {{ author.author_name }}
          </button>
        </div>
      </el-collapse-item>
      <el-collapse-item title="朝代" name="2">
        <div class="options-container">
          <button class="option btn"
                  v-for="dynasty in state.dynasties"
                  :key="dynasty.dynasty_id"
                  :class="{ active: selectedDynasties.includes(dynasty.dynasty_id) }"
                  @click="toggleSelection('dynasties', dynasty.dynasty_id)">
            {{ dynasty.dynasty_name }}
          </button>
        </div>
      </el-collapse-item>
      <el-collapse-item title="体裁" name="3">
        <div class="options-container">
          <button class="option btn"
                  v-for="genre in state.genres"
                  :key="genre.genre_id"
                  :class="{ active: selectedGenres.includes(genre.genre_id) }"
                  @click="toggleSelection('genres', genre.genre_id)">
            {{ genre.g_type }}
          </button>
        </div>
      </el-collapse-item>
      <el-collapse-item title="题材" name="4">
        <div class="options-container">
          <button class="option btn"
                  v-for="theme in state.themes"
                  :key="theme.theme_id"
                  :class="{ active: selectedThemes.includes(theme.theme_id) }"
                  @click="toggleSelection('themes', theme.theme_id)">
            {{ theme.t_type }}
          </button>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
  <!-- 大盒子 poem-container -->
  <div class="poem-container">
    <div class="poem-section">
      <el-card v-for="poem in state.poems" :key="poem.id" class="poem-card" @click="goToPoemDetail(poem.poem_id)">
        <el-card class="cont" shadow="hover">
          <template #header>
            <div class="title">
              <span>{{ poem.title }}</span>
              <el-button icon="el-icon-star-off" circle class="favorite-btn" @click="openCollectionModal(poem.poem_id,poem.title)" @click.stop>
                <img src="/images/img/sc1.png" alt="收藏">
              </el-button>
            </div>
          </template>
          <div class="source">
            <!-- <span>{{ poem.author.dynasty.dynasty_name || '未知朝代' }}</span> -->
            <span>{{ poem.author?.dynasty?.dynasty_name || '未知朝代' }}</span>
            <span>·</span>
            <router-link :to="{name: 'authorDetail',params: { id: poem.author.author_id }}" type="primary" class="author-link" @click.stop>{{ poem.author.author_name }}</router-link>
          </div>
          <div class="contson">
            <div>{{ poem.content }}</div>
          </div>

        </el-card>
      </el-card>
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
  <el-dialog title="收藏诗词" v-model="state.showCollectionModal" custom-class="collection-dialog">
    <el-form class="collection-form">
      <el-form-item label="所选诗词:">
        《{{ state.title }}》
      </el-form-item>
      <el-form-item label="选择诗集:">
        <el-select v-model="state.selectedCollectionId" placeholder="请选择诗集" class="collection-select">
          <el-option
              v-for="collection in state.collections"
              :key="collection.collection_id"
              :label="collection.collection_name"
              :value="collection.collection_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="doCollection()">确认收藏</el-button>
    </el-form>
  </el-dialog>
</template>

<script setup>
import headler from "../../views/headler.vue";
import {Search} from "@element-plus/icons-vue";
import { ElCard, ElButton, ElMessage, ElInput } from 'element-plus'
import {computed, onMounted, reactive, ref, watch} from "vue";
import {useRouter} from "vue-router";
import axios from "@/utils/axios.js";

const router = useRouter();

const goToPoemDetail = (poemId) => {
  // 一个路由名称为"poemDetail"，它用于显示诗歌的详细信息
  router.push({ name: 'poemDetail', params: { id: poemId } });
};

const images = [
  { src: '/user/images/img/op1.jpg', alt: '图片1'}
];

const input = ref('');

const state = reactive({
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 9, // 分页大小
  authors:[],
  dynasties:[],
  genres:[],
  themes:[],
  poems:[],
  showCollectionModal:false,
  poem_id:0,
  title:'',
  selectedCollectionId:null,
  collections:[],
  input:''
});


const changePage = (val) => {
  state.currentPage = val;
  console.log(state.currentPage)
  getPoems();
}

onMounted(() => {
  getAuthors();
  getDynasties();
  getGenres();
  getThemes();
  getPoems();
  getCollectionByUserId()
})
const getAuthors = () => {
  axios.get("/author/authorList")
      .then(res=>{
        state.authors = res.data;
      })
}

const getDynasties = () => {
  axios.get("/dynasty/dynastyList")
      .then(res=>{
        state.dynasties = res.data;
      })
}

const getGenres = () => {
  axios.get("/genre/genreList")
      .then(res=>{
        state.genres = res.data;
      })
}

const getThemes = () => {
  axios.get("/theme/themeList")
      .then(res=>{
        state.themes = res.data;
      })
}

const getPoems = () => {
  const params = {
    currentPage: state.currentPage,
    pageSize: state.pageSize,
    authorIds: selectedAuthors.value.length ? selectedAuthors.value : null,
    dynastyIds: selectedDynasties.value.length ? selectedDynasties.value : null,
    genreIds: selectedGenres.value.length ? selectedGenres.value : null,
    themeIds: selectedThemes.value.length ? selectedThemes.value : null,
    title:state.input
  };
  axios.post("/poem/getPoems",params)
      .then(res=>{
        state.poems=res.data.records;
        state.total=res.data.total;
      })
}

const searchPoems = () => {
  state.input=input.value;
  getPoems();
}

const openCollectionModal = (poem_id,title) => {
  state.showCollectionModal=true;
  state.poem_id=poem_id;
  state.title=title
}

const getCollectionByUserId = () => {
  axios.get("/collection/getCollectionByUserId")
      .then(res=>{
        state.collections=res.data;
      })
}

const doCollection = () => {
  const params = {
    poem_id:state.poem_id,
    selectedCollectionId:state.selectedCollectionId
  }
  axios.post("/collection/doCollection",params)
      .then(res => {
        if (res.code===200) {
          ElMessage({
            message: '收藏成功！',
            type: 'success',
            duration: 2000
          });
        } else {
          ElMessage({
            message: res.data,
            type: 'error',
            duration: 2000
          });
        }
        state.showCollectionModal = false;
      });
}
// 响应式数据，用于存储选中的作者、朝代、体裁、题材
const selectedAuthors = ref([]);
const selectedDynasties = ref([]);
const selectedGenres = ref([]);
const selectedThemes = ref([]);
// 切换选中状态的函数
const toggleSelection = (type, id) => {
  const selectedList = {
    authors: selectedAuthors,
    dynasties: selectedDynasties,
    genres: selectedGenres,
    themes: selectedThemes
  }[type];
  const index = selectedList.value.indexOf(id);
  if (index > -1) {
    selectedList.value.splice(index, 1);
  } else {
    selectedList.value.push(id);
  }
  console.log(selectedAuthors.value)
  getPoems();
};
// 根据筛选条件过滤诗词列表的逻辑
const filterPoems = () => {
  state.poems.value = poems.value.filter(poem => {
    return (!selectedAuthors.value.length || selectedAuthors.value.includes(poem.author_id)) &&
        (!selectedDynasties.value.length || selectedDynasties.value.includes(poem.dynasty_id)) &&
        (!selectedGenres.value.length || selectedGenres.value.includes(poem.genre_id)) &&
        (!selectedThemes.value.length || selectedThemes.value.includes(poem.theme_id));
  });
};
// 计算属性，用于返回筛选后的诗词数量
const filteredPoems = computed(() => {
  return state.poems.filter(poem => {
    return (!selectedAuthors.value.length || selectedAuthors.value.includes(poem.author_id)) &&
        (!selectedDynasties.value.length || selectedDynasties.value.includes(poem.dynasty_id)) &&
        (!selectedGenres.value.length || selectedGenres.value.includes(poem.genre_id)) &&
        (!selectedThemes.value.length || selectedThemes.value.includes(poem.theme_id));
  });
});
// 监听器，用于实时更新筛选后的诗词数量
watch([selectedAuthors, selectedDynasties, selectedGenres, selectedThemes], () => {
  filterPoems();
});
// 定义查找函数
const findAuthorById = (id) => state.authors.find(author => author.author_id === id);
const findDynastyById = (id) => state.dynasties.find(dynasty => dynasty.dynasty_id === id);
const findGenreById = (id) => state.genres.find(genre => genre.genre_id === id);
const findThemeById = (id) => state.themes.find(theme => theme.theme_id === id);

</script>

<style scoped>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
}

/* 轮播图样式 */
.carousel {
  margin-bottom: 20px;
}

.carousel-item {
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

/* 搜索框样式 */
.filter-section {
  background-color: #fff;
  border: 1px solid #e1e4e8;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 15px;
}
/* 筛选框样式 */

/* condition-title 样式优化 */
.condition-title {
  display: flex;
  align-items: center;
  padding: 10px 15px;
  background-color: #e9ecef; /* 浅灰色背景 */
  border-bottom: 1px solid #dfe2e5; /* 底部边框 */
}

.condition-title .m-0 {
  font-size: 18px;
  color: #333;
  font-weight: bold;
  margin: 0;
}

.condition-title .num {
  color: #666;
  font-size: 14px;
  margin-left: auto; /* 调整位置，使其在右侧 */
}

.options-container {
  display: flex;
  flex-wrap: wrap;
  margin: -5px;
}

.option {
  background-color: #f3f4f6;
  border: 1px solid #d1d5db;
  border-radius: 999px;
  padding: 8px 12px;
  margin: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.option:hover {
  background-color: #e5e7eb;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

/* 筛选选项的选中状态样式 */
.option.active {
  background-color: #90cdf4; /* 选中时的背景颜色 */
  color: #fff; /* 选中时的文字颜色 */
}
/* selected-filters 样式优化 */
.selected-filters {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  background-color: #f8f9fa; /* 浅灰色背景 */
  border: 1px solid #e1e4e8; /* 边框 */
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 15px;
}

.selected-filters span {
  font-size: 14px;
  color: #333;
  margin-right: 10px; /* 增加间距 */
  margin-bottom: 5px; /* 添加底部边距 */
}

.selected-filters span:last-child {
  margin-right: 0;
}

/* 添加悬停效果 */
.selected-filters span:hover {
  text-decoration: underline; /* 悬停时添加下划线 */
}

/* 诗歌区域样式 */
.poem-section {
  display: flex;         /* 使用flex布局 */
  flex-wrap: wrap;       /* 允许换行 */
  justify-content: flex-start; /* 从头开始排列 */
  padding: 20px;         /* 添加一些内边距 */
  gap: 20px;             /* 添加卡片之间的间隔 */
}
/* 诗歌内容样式 */
.contson {
  font-size: 16px; /* 设置字体大小 */
  line-height: 1.5; /* 设置行高 */
  height: 3em; /* 限制显示的行数 */
  overflow: hidden; /* 隐藏溢出的内容 */
  text-overflow: ellipsis; /* 显示省略号 */
  display: -webkit-box; /* 使用弹性盒子模型显示 */
  -webkit-line-clamp: 2; /* 限制显示的行数 */
  -webkit-box-orient: vertical; /* 设置弹性盒子的方向 */
}

.poem-card {
  position: relative; /* 为收藏按钮提供定位上下文 */
  flex: 0 0 30%;         /* 设置固定宽度，确保每行最多显示三个卡片 */
  max-width: 30%;        /* 设置最大宽度，确保在响应式布局中不超过这个宽度 */
  margin-right: 1.5%;    /* 在卡片之间添加间隔 */
  margin-bottom: 20px;   /* 在卡片下方添加间隔 */
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.3s ease; /* 添加一些过渡效果 */
}
/* “source”盒子的样式 */
.source {
  margin-bottom: 20px; /* 增加底部间隔 */
}

/* 每三个卡片之后清除右边距 */
.poem-card:nth-child(3n) {
  margin-right: 0;
}

/* 当鼠标悬停在卡片上时，可以添加一些交互效果 */
.poem-card:hover {
  transform: translateY(-5px); /* 简单的向上移动效果 */
}

/* 收藏按钮样式 */
.favorite-btn {
  position: absolute; /* 使用绝对定位 */
  top: 18px; /* 根据需要调整距离顶部的距离 */
  right: 25px; /* 根据需要调整距离右侧的距离 */
  background-color: transparent; /* 背景透明 */
  border: none; /* 无边框 */
  padding: 0; /* 无内边距 */
  cursor: pointer; /* 鼠标悬停时显示指针 */
  opacity: 0; /* 默认不显示 */
  transition: opacity 0.3s ease; /* 平滑的过渡效果 */
}

/* 当鼠标悬停在卡片上时，显示收藏按钮 */
.poem-card:hover .favorite-btn {
  opacity: 1; /* 显示按钮 */
}

/* 收藏按钮中的图片样式 */
.favorite-btn img {
  width: 24px; /* 设置图片宽度 */
  height: 24px; /* 设置图片高度 */
}
/* 大盒子 poem-container 的样式 */
.poem-container {
  background-color: #fff; /* 背景颜色 */
  border: 1px solid #e1e4e8; /* 边框 */
  border-radius: 4px; /* 圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 阴影 */
  padding: 20px; /* 内边距 */
  margin-top: 20px; /* 上边距 */
}

/* 美化收藏诗词对话框的样式 */
.collection-dialog {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.collection-form {
  padding: 20px;
}

.collection-form .el-form-item {
  margin-bottom: 20px;
}

.collection-form .el-form-item__label {
  font-weight: bold;
}

.collection-select {
  width: 100%;
}

.collection-form .el-button {
  width: 100%;
  margin-top: 20px;
}

.search-container {
  display: flex; /* 使用 flex 布局来排列输入框和按钮 */
  align-items: center; /* 垂直居中 */
  margin-bottom: 20px; /* 在搜索容器下方添加一些间隔 */
}

.search-input {
  flex: 1; /* 使输入框填充剩余空间 */
  margin-right: 10px; /* 在输入框和按钮之间添加间隔 */
}

.search-btn {
  /* 按钮样式，根据需要调整 */
}
</style>

