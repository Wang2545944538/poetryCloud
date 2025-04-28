<script setup>
import home from "./views/home.vue";
import {onBeforeUnmount, reactive} from "vue";
import {useRouter} from "vue-router";
import {localGet, localRemove, pathMap} from "@/utils/index.js";

const router = useRouter()
const state = reactive({
  showMenu: true,
  currentPath: '/',
  models:null,
})

router.beforeEach((to, from, next) => {
  //后面开启
  /*if (to.path == '/login') {
    // 如果路径是 /login 则正常执行
    next()
  } else {
    // 如果不是 /login，判断是否有 token
    if (!localGet('token')) {
      // 如果没有，则跳至登录页面
      next({ path: '/login' })
    } else {
      // 否则继续执行
      next()
    }
  }*/
  state.currentPath = to.path
  document.title = pathMap[to.name]
  next()
})

/*// 在 beforeunload 事件中设置标记
window.addEventListener('beforeunload', () => {
  // 设置一个标记，表明在卸载前需要清除 token
  window.unloadTokenCleanup = true;
});

// 在 unload 事件中清除 token
window.addEventListener('unload', () => {
  if (window.unloadTokenCleanup) {
    localRemove('token');
  }
});

// 在组件卸载前移除事件监听器
onBeforeUnmount(() => {
  window.removeEventListener('beforeunload', () => {});
  window.removeEventListener('unload', () => {});
});*/

</script>

<template>
  <router-view />
</template>

<style scoped>
*{
    padding: 0;
    margin: 0;
}
@font-face {
    font-family: 'MyCustomFont';
    src: url('../src/font/AlimamaDongFangDaKai-Regular.otf') format('woff2'),
         url('../src/font/AlimamaDongFangDaKai-Regular.ttf') format('woff'),
         url('../src/font/AlimamaDongFangDaKai-Regular.woff') format('truetype'),
         url('../src/font/AlimamaDongFangDaKai-Regular.woff2') format('truetype');
    font-weight: normal;
    font-style: normal;
}
</style>
