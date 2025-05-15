<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
    @select="handleSelect">
    <el-menu-item index="0">
      <img style="width: 55px" src="/images/icon/诗词大全.png" alt="Element logo" />
      <el-text class="mx-1"
        style="margin-left: 15px; font-size: 28px; color: black; font-family: 'MyCustomFont', sans-serif;">云墨诗苑</el-text>
    </el-menu-item>

    <el-menu-item index="1">首页</el-menu-item>
    <el-menu-item index="2">诗会</el-menu-item>
    <el-menu-item index="3">诗人</el-menu-item>
    <el-menu-item index="4">作品</el-menu-item>
    <el-menu-item index="5">创作</el-menu-item>
    <el-menu-item index="6">诗集</el-menu-item>
    <el-menu-item index="7">AI创作</el-menu-item>

    <template v-if="token">
      <el-dropdown trigger="click">

        <el-avatar :src="userImg()" size="medium" style="margin-top: 5px"></el-avatar>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="goToUserProfile">个人中心</el-dropdown-item>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </template>

    <!-- <template v-else class="user_icon"> -->
      <template v-else>
      <el-dropdown trigger="hover">
        <img style="width: 40px;height: 40px; margin-top: 10px; cursor: pointer; outline: none;"
          src="/images/icon/user.webp" alt="用户菜单">
        <template #dropdown>
          <el-dropdown-menu class="login_icon">
            <el-dropdown-item @click="toLogin">登录</el-dropdown-item>
            <el-dropdown-item @click="toRegister">注册</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </template>
  </el-menu>
</template>


<script lang="ts" setup>
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { localGet, localRemove, uploadImgServer, uploadImgsServer } from '../utils'

const router = useRouter();
const route = useRoute();
const activeIndex = ref('1');

let token = reactive(localGet("token"))
const avatar = reactive(localGet("avatar"))

const userImg =() => {
  // 获取存储中的用户头像URL或者返回默认头像
  if (avatar == null) {
    return 'https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/defaultUser.png';
  } else {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/${avatar}`;
  }

};

// 跳转到个人中心页面
const goToUserProfile = () => {
  router.push({ path: '/profile' });
};
// 监听路由变化来更新 activeIndex
watch(() => route.path, (newPath) => {
  // 根据路由路径更新 activeIndex 的值
  const indexMap = {
    '/home': '1',
    '/poetrySociety': '2',
    '/poet': '3',
    '/opus': '4',
    '/write': '5',
    '/poems': '6',
    '/aiTest': '7',
    // 添加其他路由与索引的映射

  };
  // 定义一个函数来检查路径是否以特定前缀开始
  const startsWith = (str, prefix) => str.slice(0, prefix.length) === prefix;

  // 检查当前路径是否以 '/authorDetail/' 开头
  if (startsWith(newPath, '/authorDetail/')) {
    activeIndex.value = '3'; // 设置为对应的索引
  }else if(startsWith(newPath, '/lookCollection/')){
    activeIndex.value = '6'; // 设置为对应的索引
  }else if(startsWith(newPath, '/showCollection/')){
    activeIndex.value = '6'; // 设置为对应的索引
  } else {
    // 查找匹配的路由，如果没有匹配，则设置 activeIndex 为 null
    activeIndex.value = indexMap[newPath] || null;
  }
}, { immediate: true });
// 退出登录
const logout = () => {
  localRemove("token");
  localRemove("avatar");
  window.location.href = '/user/'
};

// 跳转到登录页面
const toLogin = () => {
  router.push({ path: '/login' });
};

// 跳转到注册页面
const toRegister = () => {
  router.push({ path: '/register' });
};

// 处理菜单选择
const handleSelect = (key: string) => {
  // 根据选择的索引进行路由跳转
  const pathMap = {
    '1': '/home',
    '2': '/poetrySociety',
    '3': '/poet',
    '4': '/opus',
    '5': '/write',
    '6': '/poems',
    '7': '/aiTest',

    // 添加其他索引与路由的映射

  };
  const newPath = pathMap[key];
  if (newPath) {
    router.push({ path: newPath });
  }
  activeIndex.value = key; // 更新 activeIndex
};





</script>

<style>
.user_icon .el-dropdown {
  display: inline-block;
}

/* 下拉菜单基本样式 */
.el-dropdown-menu {
  background-color: #ffffff;
  /* 菜单背景颜色 */
  border: 1px solid #e4e7ed;
  /* 菜单边框 */
  border-radius: 4px;
  /* 边框圆角 */
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
  /* 阴影效果加强 */
  margin-top: 10px;
  /* 调整菜单与触发器的距离 */
}

/* 下拉菜单项样式 */
.el-dropdown-menu__item {
  color: #606266;
  /* 文字颜色 */
  font-size: 16px;
  /* 文字大小 */
  padding: 10px 20px;

  /* 最小宽度 */
  text-align: center;
  /* 文本居中 */
  transition: background-color 0.3s;
  /* 平滑过渡效果 */
}

/* 鼠标悬停时的样式 */
.el-dropdown-menu__item:hover {
  background-color: #f5f7fa;
  /* 悬停背景颜色 */
  color: #409eff;

  /* 悬停文字颜色 */
}

/* 用户图标样式 */
.user_icon img {
  width: 40px;
  height: 40px;
  margin-top: 10px;
  border: 0 none;
  /* 圆形图标 */
  background-color: #f5f5f5;
  /* 背景颜色 */
  padding: 5px;
  /* 内边距 */
  cursor: pointer;
  /* 鼠标样式 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* 添加轻微阴影 */
}

/* 下拉菜单触发器样式 */
.el-dropdown {
  display: inline-block;
  vertical-align: middle;
}

/* 修改登录注册文字居中 */
.login_icon .el-dropdown-menu__item {
  display: flex;
  align-items: center;
  /* 垂直居中 */
  justify-content: center;
  /* 水平居中 */
}

.el-menu--horizontal>.el-menu-item:nth-child(1) {
  margin-right: auto;
}

.poet {
  text-decoration: none;
}
</style>