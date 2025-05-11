<template>
    <div class="head">
        <div>
            <img src="/admin/image/icon/诗词.png" alt="logo">
            <span>云墨诗苑</span>
        </div>
    </div>
    <el-aside width="200px" class="aside">
        <!-- 侧边栏菜单 -->
        <el-menu :default-active="activeIndex" @select="handleSelect">
            <el-sub-menu v-for="item in data.models" :key="item.model_id" :index="item.model_id.toString()">

                <template #title>
                    <component style="  width: 20px;  margin-right: 8px; font-size: 18px;"
                        :is="iconMap[item.model_name]" class="menu-icon" />
                    <span>{{ item.model_name }}</span>
                </template>

                <!-- 子菜单项 -->
                <el-menu-item v-for="child in item.childMods" :key="child.model_id" :index="child.model_id.toString()"
                    @click="toView(child.url)">
                    <template #title>
                        <component v-if="iconMap[child.model_name]"
                            style="width: 20px; margin-right: 8px; font-size: 18px;" :is="iconMap[child.model_name]"
                            class="menu-icon" />
                        <span>{{ child.model_name }}</span>
                    </template>
                </el-menu-item>

            </el-sub-menu>
        </el-menu>
    </el-aside>
</template>
<script setup>
import { HomeFilled, Avatar, UserFilled, GoodsFilled, List, Edit, MessageBox, DocumentAdd, SetUp, EditPen, Finished } from '@element-plus/icons-vue'
import { Menu, User } from '@element-plus/icons-vue'
import 'element-plus/dist/index.css';
import axios from '@/utils/axios.js';
import { ref, onMounted, reactive } from 'vue'
import router from "@/routes/index.js";
import { useRoute } from "vue-router";
const route = useRoute()

const user_id = route.params.user_id;
const data = reactive({
    models: null,
})
const activeIndex = ref('') // 响应式变量，存储当前选中的菜单项索引
const handleSelect = (index, indexPath) => {
    activeIndex.value = index // 更新当前选中的菜单项索引
}

const toView = (url) => {
    console.log(url);
    router.push({ path: url });
}

// 图标映射关系
const iconMap = {
    '系统管理': HomeFilled,
    '诗词创作管理': DocumentAdd,
    '诗词库管理': List,
    '诗人管理': UserFilled,
    '个人中心管理': MessageBox,
    '诗会管理': Finished,
    '诗人列表': SetUp,
    '添加诗人': EditPen,
    // 添加更多图标映射关系
}

const props = {
    label: 'model_name',
    children: 'childModels',
}

onMounted(() => {
    axios.get("/models/getModelsById", { params: { user_id } }).then(res => {
        data.models = res.data;
    })
})
</script>

<style lang="scss" scoped>
html,
body,
#app {
    height: 100vh;
    margin: 0;
    padding: 0;
    overflow-x: hidden; // 防止body出现滚动条
}

// 最外层容器，确保使用flex布局并占满整个视口高度
.el-container {
    display: flex;
    flex-direction: column; // 假设是垂直布局
    height: 100vh;
}

// 顶部栏样式
.head {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 60px;
    width: 100%;
}

// 侧边栏样式
.el-aside {
    width: 200px;
    overflow: hidden; // 防止侧边栏出现滚动条
}

// 侧边栏菜单样式
.el-menu {
    height: 100%; // 使菜单高度充满侧边栏
    overflow-y: auto; // 如果内容超出，允许垂直滚动
}

// 主内容区域样式
.el-main {
    flex: 1; // 主内容区域填充剩余空间
    overflow: hidden; // 防止主内容区域出现滚动条
}

.head {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 60px;
    width: 100%; // 确保头部占满整个宽度
}

.head>div {
    display: flex;
    align-items: center;
}

.head img {
    width: 40px;
    height: 40px;
    margin-right: 10px;
}
</style>