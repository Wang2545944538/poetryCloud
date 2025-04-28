<script setup xmlns="http://www.w3.org/1999/html">
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage} from "element-plus";
import CommentArea from "@/views/songDetail/CommentArea.vue";

const route = useRoute();
const info = ref({});
const sheetList = ref([])

onMounted( () => {

  // 发个请求 添加到历史播放列表
  addToRecent()


  // 获取歌曲详细数据
  getDetail()
  // 获取可以添加的列表
  getCanAddSheet()




});
function addToRecent() {
  const formData = {
    account: sessionStorage.getItem('account'),
    musicId: route.params.id
  }
  const addRecentUrl =  "http://localhost:20000/music/recent/add"

  axios.post(addRecentUrl,
      JSON.stringify(formData)
  ).then((res)=>{
    if (res.data.flag) {
    } else {
      console.log(err.message)
    }
  })
  .catch(err => {
    console.log(err.message)
  })

}

function getCanAddSheet() {
  const sheetUrl = "http://localhost:20000/music/sheet/show_can_add?account=" +  sessionStorage.getItem("account") + "&musicId=" + route.params.id
  axios.get(sheetUrl
  ).then((res)=>{
    if (res.data.flag) {
      sheetList.value = res.data.data
    } else {
      console.log(res.data.message)
    }
  })
  .catch(err => {
    console.log(err.data)
  })
}
function getDetail() {
  const url = "http://localhost:20000/music/detail/music?musicId=" + route.params.id + "&account=" + sessionStorage.getItem("account")
  axios.get(url).then((res) => {

    if (res.data.flag) {
      info.value = res.data.data

    } else {
      console.log(res.data.message)
    }
  })
      .catch(err => {
        console.log(err.data)
      })
}
const audio = ref();

function play() {
  if (audio.value) {
    audio.value.play()
  }
}

const likeMusic = () => {
  const formData = {
    biz: 0,
    account: sessionStorage.getItem('account'),
    bizId: route.params.id
  }
  const url = "http://localhost:20000/music/like"
  axios.post(url,
      JSON.stringify(formData)
  ).then((res)=>{
    if (res.data.flag) {
      if (info.value.isLike) {
        ElMessage.success("取消喜欢成功")
        info.value.likeCount--
      } else {
        ElMessage.success("喜欢成功")
        info.value.likeCount++
      }

      info.value.isLike = !info.value.isLike
    } else {
      if (info.value.isLike) {
        ElMessage.error("取消喜欢失败")
      } else {
        ElMessage.error("喜欢失败")
      }
      ElMessage.error(res.data.message)
    }
  })
  .catch(err => {
    console.log(err.data)
  })
}





function addToSheet(id) {

  // 发一个post请求，添加歌曲进歌单
  const formData = {
    sheetId: id,
    musicId: route.params.id
  }
  const url = "http://localhost:20000/music/sheet/add_music"
  axios.post(url, JSON.stringify(formData)).then((res)=>{
    if (res.data.flag) {
      ElMessage.success("添加成功")

      // 更新一下列表数据 达到立马起效的效果
      const sheetUrl = "http://localhost:20000/music/sheet/show_can_add?account=" +  sessionStorage.getItem("account") + "&musicId=" + route.params.id
      axios.get(sheetUrl
      ).then((res)=>{
        if (res.data.flag) {
          sheetList.value = res.data.data
        } else {
          console.log(res.data.message)
        }
      })
      .catch(err => {
        console.log(err.data)
      })

    } else {
      ElMessage.error("添加失败：" + res.data.message)
      console.log(res.data.message)
    }
  })
  .catch(err => {
    console.log(err.data)
  })
}

function increaseCommentCount() {
  info.value.commentCount++
}
</script>

<template>
  <div class="container">
    <div class="top">
      <img style="margin-right: 65px" width="250" height="250" :src="info.cover">
      <div class="info">
        <h1 class="title">{{ info.name }}</h1>
        <div style="display: flex;align-items: center">
          <i class="icon-user"></i>
          <span class="musician">{{ info.musician }}</span>
        </div>
        <div class="data" style="margin-top: 8px">专辑：{{ info.album }}</div>
        <div class="data">发行时间：{{ info.creatTime }}</div>
        <div class="functions">

          <el-button @click="play()" class="function play" type="default">
            <i class="play-icon"></i>播放
          </el-button>
          <el-button @click="likeMusic()" class="function other-button" type="default">
            <i v-if="info.isLike" class="unlike-icon"></i>
            <i v-if="!info.isLike" class="like-icon"></i>
            收藏 ({{ info.likeCount }})
          </el-button>
          <el-button @click="comment()" class="function other-button" type="default">
            <i class="comment-icon"></i>评论 ({{ info.commentCount }})
          </el-button>




          <el-popover placement="right" trigger="click">
            <template #reference>
              <el-button class="function other-button" type="default">
                <i class="addSheet-icon"></i>添加
              </el-button>
            </template>
            <el-table :data="sheetList">
              <el-table-column  label="歌单" >
                <template #default="scope">
                  <!-- 不仅查询所有歌单 还要查询歌曲对每个歌单的归属情况 -->
                  <div v-if="scope.row.canAdd" :class="{gery:　scope.row.canAdd}" style="width: 170px">{{ scope.row.name  }}</div>
                  <div v-else style="color: #1e1e1e;cursor:pointer;width: 170px" @click="addToSheet(scope.row.id)">{{ scope.row.name  }}</div>
                </template>
              </el-table-column>
            </el-table>
          </el-popover>

        </div>
      </div>
    </div>

    <audio controls ref="audio" :src="info.music" type="audio/mp3" />

    <CommentArea @increaseCommentCount="increaseCommentCount" :info="info"></CommentArea>


  </div>



</template>

<style scoped>



.gery {
  color: #ababab !important;
  cursor: default!important;
}
.addSheet-icon {
  background-position: 0 -50px;
  transform: scale(0.5);
  margin-bottom: 2px;
}
.addSheet-icon {
  background-position: -40px 0;
  transform: scale(0.5);
  margin-bottom: 2px;
}
.like-icon {
  background-position: -120px 0;
  transform: scale(0.5);
  margin-bottom: 2px;
}

.unlike-icon {
  background-position: 0 -160px;
  transform: scale(0.5);
  margin-bottom: 2px;
}

.comment-icon {
  background-position: -280px -240px;
  transform: scale(0.5);
  margin-bottom: 2px;
}
.play-icon {
  transform: scale(0.5);
  width: 26px!important;
  height: 32px!important;
  background-position: -160px -440px;
  margin-bottom: 2px;
}

.functions {
  margin-top: 32px;
}

.function {
  height: 38px;
  line-height: 38px;
  color: black;
  font-size: 14px;
//padding: 0 23px;
  width: 125.6px;
  padding-top: 10px;
  box-sizing: border-box;
  border-color: #c9c9c9;
  border-radius: 0;
}

.other-button {
  width: 122px!important;
}
.other-button:hover {
  background-color: #efeeee;
}
.play {
  background: #ffb17b !important;
  color: white!important;
  border-color: #eaa470 !important;
  width: 122px!important;
}
.play:hover {
  background: #f8ac77 !important;
}


.data {
  line-height: 27px;
  height: 27px;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 25%;
  font-size: 14px;
}
.musician {
  font-size: 16px;
  color: #333;
  display: inline-block;
  font-weight: 400;
  max-width: 30%;
  overflow: hidden;
  text-overflow: ellipsis;
}
.musician:hover {
  color: #e96161;
  cursor: pointer;
}


.icon-user {
  transform: scale(0.5);
  background-position: -40px -480px;
}


.container {
  padding: 20px calc(50vw - 600px) 100px;
  background-color: #fbfbfb;
  font-family: poppin, Tahoma, Arial, 微软雅黑, sans-serif;
}

.top {
  position: relative;
  height: 250px;
  //padding-left: 305px;
  margin-top: 40px;
  margin-bottom: 35px;
  display: flex;

}
.info {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.title {
  float: left;
  font-size: 31px;
  font-weight: 400;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 70%;
  line-height: 50px;
  margin-right: 10px;
  padding-top: 13px;
}
i {
  width: 32px;
  height: 32px;
  margin-right: 0px;
  margin-left: -6px;
  display: inline-block;
  background: url("http://localhost:5173/src/assets/img/total.png") no-repeat ;
}
</style>
