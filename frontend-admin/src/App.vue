<template>
  <el-container v-if="state.showMenu">
    <!-- 顶部栏，占满整个宽度 -->

    <!-- 侧边栏 -->
    <el-aside  class="aside">
      <SideMenu/>
    </el-aside>

    <!-- 垂直容器，包含侧边栏和主内容区域 -->
    <el-container direction="vertical">

      <el-header>
        <TopHeader />
      </el-header>

      <el-main>
        <div class="main">
          <router-view />
        </div>
      </el-main>
    </el-container>
    <!-- 主内容区域 -->
  </el-container>
  <div v-else>

    <router-view></router-view>
  </div>
</template>


<script setup>
import TopHeader from '@/components/mainbox/TopHeader.vue';
import SideMenu from '@/components/mainbox/SideMenu.vue';
import { useRouter } from "vue-router";
import { reactive } from "vue";
import { localGet, pathMap } from "@/utils/index.js";

const noMenu = ['/login']
const router = useRouter()
const state = reactive({
  showMenu: true,
  currentPath: '/',
  models: null,
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    // 如果路径是 /login 则正常执行
    next()
  } else {
    if (!localGet('token')) {
      // 如果没有，则跳至登录页面
      next({ path: '/login' })
    } else {
      // 否则继续执行
      next()
    }
  }
  state.showMenu = !noMenu.includes(to.path)
  state.currentPath = to.path
  document.title = pathMap[to.name]
})


</script>

<style lang="scss" scoped>
::-webkit-scrollbar {
  width: 5px;
  height: 5px;
  position: absolute;
}

::-webkit-scrollbar-thumb {
  background-color: #56a0e6;
}

::-webkit-scrollbar-track {
  background: #ddd;
}

.el-main {
  overflow: auto;
  height: calc(100vh - 60px);
}

.el-header {
  padding: 0; // 可以根据需要调整
}

.el-main {
  overflow: auto;
  height: calc(100vh - 60px);
}

.el-aside {
  height: calc(100vh - 60px); // 同上，根据实际情况调整
  overflow: auto; // 如果侧边栏内容较多，可以滚动
}
.aside {
  width: 200px!important;
  height: 100vh;
  overflow-x: hidden;
}
*{
  padding: 0;
}
</style>
