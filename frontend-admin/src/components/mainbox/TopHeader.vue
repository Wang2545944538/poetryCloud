<template>
    <el-header>
        <!-- <button @click="handleCollapsed">click</button> -->
        <div class="left">
            <el-icon @click="handleCollapsed">
                <Menu/>
            </el-icon>
            <span style="margin-left: 10px;">云墨诗苑后台管理系统</span>
        </div>

        <div class="right">

            <span style="text-align: center;">欢迎<span style="color:aquamarine;"> {{nickname}}</span>回来</span>
            <el-dropdown>
                <span class="el-dropdown-link" style="cursor: pointer; outline: none;">
                    <el-icon  :size="50" color="white" >
                       <el-avatar  :src="userImg()" size="medium" ></el-avatar>
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item  @click="handleLogout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </el-header>
</template>
<script setup>
import { Menu, User } from '@element-plus/icons-vue'
import { useRoute } from 'vue-router';
import router from '@/routes';
import {localGet} from "@/utils/index.js";
const route =useRoute()
const nickname = localGet('nickname')
const avatar = localGet("avatar")
const handleCollapsed = () => {
  
}
const userImg =() => {
  // 获取存储中的用户头像URL或者返回默认头像
  if (avatar == null) {
    return 'https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/defaultUser.png';
  } else {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/${avatar}`;
  }

};

const handleLogout=()=>{
    /*localGet("token")
    localGet("nickname")
    localGet("avatar")
    router.push("/login")*/
  localStorage.removeItem("token");
  localStorage.removeItem("nickname");
  localStorage.removeItem("avatar");
  router.push("/login");
}
</script>

<style lang="scss" scoped>
.el-header{
    background-color: #5591df;
    color: white;
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.left,.right{
    display: flex;
  align-items: center;
  /* 垂直居中 */
  justify-content: center;
  /* 水平居中 */
  text-align: center;
}
.left{
    i{
        margin: auto;
        cursor: pointer;
    }
}
.rigjt{
    .el-dropdown{
        margin: auto;
        border: none;
    }
}
.el-dropdown-link img {
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
</style>