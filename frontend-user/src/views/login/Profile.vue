<script setup>

import {onMounted, reactive, ref} from "vue";
import {useRouter,useRoute} from "vue-router";
import axios from "@/utils/axios";
import {localGet, localSet} from "@/utils/index.js";
import {ElMessage} from "element-plus";
import {client} from "@/utils/alioss.js";

// 获取头像
let avatar = localGet("avatar")

function loadInfo() {

  /*  // 看有没有参数 如果没有参数就是自己的主页
    if (route.query.account === undefined) {

    } else {
      // 查询一下该用户是否存在
      const url1 = 'http://localhost:20000/user/info/get_by_account?' + route.query.account
      // 成功后发一个请求，把用户的信息进行储存一下
      axios.get(url1).then((res1) => {
        username = res1.data.data.username
        avatar = res1.data.data.avatar === '' ? 'http://localhost:5173/src/assets/img/icon.png' : avatar
      })
    }*/
}
const route = useRoute()
const router = useRouter()
onMounted(() => {
  loadInfo()
})
function goto(location) {
  if (location === 0) {
    /*
    if (route.query.account !== undefined) {
      router.push('/profile/like?account=' + route.query.account)
    } else {
    }*/
    router.push('/profile/like')
  } else if (location === 1) {
    router.push('/profile/sheet')
  } else if (location === 2){
    router.push('/profile/recent')
  }else if (location === 4){
    router.push('/profile/MyWrite')
  }else if (location === 3){
    router.push('/')
  }
}

const addForm = reactive({
  img:null,
  title:'',
  oriFileName:null,

})
//判断图片格式
const handleBeforeUpload = (file) =>{
  // console.log("handleBeforeUpload");
  addForm.oriFileName = file.name;
  // console.log(addForm.oriFileName);
  const isJPEG = file.name.split('.')[1] === 'jpeg';
  const isJPG = file.name.split('.')[1] === 'jpg';
  const isPNG = file.name.split('.')[1] === 'png';
  const isWEBP = file.name.split('.')[1] === 'webp';
  const isGIF = file.name.split('.')[1] === 'gif';
  const isLt500K = file.size / 1024 / 1024 / 1024 / 1024 < 4;
  if (!isJPG && !isJPEG && !isPNG && !isWEBP && !isGIF) {

    ElMessage({
      message: '上传图片只能是 JPEG/JPG/PNG 格式!',
      type: 'warning',
      plain: true,
    })
  }
  if (!isLt500K) {

    ElMessage({
      message: '单张图片大小不能超过 4mb!',
      type: 'warning',
      plain: true,
    })
  }
  return (isJPEG || isJPG || isPNG || isWEBP || isGIF) && isLt500K;
}
//上传图片
const uploadURL = (file) =>{
  console.log("uploadURL");

  let files = `${Date.parse(new Date())}`+ addForm.oriFileName;
  let fileName = 'avatar/'+files;

  client().put(fileName,file.file).then((res)=>{//此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
    axios.post("/users/setAvatar?files="+files).then(res=>{
      if (res.code === 200) {
        localSet('avatar',res.data)
        ElMessage({
          message:'上传成功',
          type:'success',
        })
        // 成功后刷新页面
        location.reload();
      } else {
        ElMessage({
          message: "上传失败",
          duration: 2000,
          type: "error",
        })
      }
    })
  })
}

const toAvatar=()=>{
  if (avatar == null) {
    return 'http://localhost:5173/src/assets/img/icon.png'
  }else {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/${avatar}`
  }
}
</script>

<template>
  <div class="bg">

    <el-upload
        action=""
        :http-request="uploadURL"
        :before-upload="handleBeforeUpload"
        :limit="8"
        :show-file-list="false"
    >
      <el-image :src="toAvatar()" fit="cover" style="width: 110px; height: 110px; margin-top: 100%; border-radius: 50%;" ></el-image>
    </el-upload>


    <div class="items">
      <span @click="goto(0)" :class="{active: $route.path === '/profile/like'}">我喜欢</span>
      <span @click="goto(1)" :class="{active: $route.path === '/profile/sheet'}">我的诗集</span>
      <span @click="goto(4)" :class="{active: $route.path === '/profile/MyWrite'}">我的创作</span>
      <span @click="goto(2)" :class="{active: $route.path === '/profile/recent'}">历史记录</span>
      <span @click="goto(3)" :class="{active: $route.path === '/'}">返回主页</span>
    </div>
  </div>
  <router-view/>
</template>

<style scoped>
.bg {
  background: url('../../images/img/1.jpeg') 50% no-repeat;
  height: 380px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.items {
  color: #333333;
  background: rgba(0,0,0,0.1);
  position: absolute;
  height: 57px;
  bottom: 0;
  right: 0;
  left: 0;
  padding-left: calc(50vw - 600px);
}
.items>span {
  line-height: 57px;
  margin-right: 40px;
  cursor: pointer;
}
.items>span:hover {
  color: #e4836f;
}
.active {
  color: #e4836f;
}
</style>
