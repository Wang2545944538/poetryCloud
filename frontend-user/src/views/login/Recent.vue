<script setup>
import { onMounted, ref} from "vue";
import axios from "axios";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const active = ref(1);

let musicNum = ref(0);

let musicList = ref([])
const router = useRouter()
const route = useRoute()
function toMusicInfo(id) {
  // 跳个地址穿个id参数即可 音乐详细页面获取音乐的详细信息
  // router.push("/songDetail/" + id)
  window.open('http://localhost:5173/songDetail/' + id, '_blank');

}

onMounted(() =>{

  const account = route.query.account == null ? sessionStorage.getItem('account') : route.query.account
  let infoUrl = 'http://localhost:20000/music/recent/num?account=' + account
  let recentUrl = 'http://localhost:20000/music/recent/query?account=' + account

  // 成功后发一个请求，把用户的信息进行储存一下
  axios.get(infoUrl).then((res) => {
    musicNum.value = res.data.data
  })


  // 请求一下 获取一下用户的历史播放的歌曲数据
  axios.get(recentUrl).then((res) => {
    musicList.value = res.data.data
  })

})
function popSheet(id) {
  // 更新一下列表数据 达到立马起效的效果
  const sheetUrl = "http://localhost:20000/music/sheet/show_can_add?account=" +  sessionStorage.getItem("account") + "&musicId=" + id
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
const sheetList = ref([])
function addToSheet(sheetId,musicId) {

  // 发一个post请求，添加歌曲进歌单
  const formData = {
    sheetId: sheetId,
    musicId: musicId
  }
  const url = "http://localhost:20000/music/sheet/add_music"
  axios.post(url,
      JSON.stringify(formData)
  ).then((res)=>{
    if (res.data.flag) {
      ElMessage.success("添加成功")
      const sheetUrl = "http://localhost:20000/music/sheet/show_can_add?account=" +  sessionStorage.getItem("account") + "&musicId=" + id
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
</script>

<template>
  <div class="container">

    <div class="items">
      <!--      喜欢的歌单，视频，专辑等等暂未开放-->
      <span @click="active = 1;$router.push('/mine/like')" :class="{active: active === 1}">诗词 {{musicNum}}</span>
      <!--      <span @click="active = 2;$router.push('/mine/sheet')" :class="{active: active === 2}">歌单</span>-->
    </div>

    <!--    <div>-->
    <div v-if="musicNum !== 0" class="song-row grayBg">
      <span class="c1">歌曲</span>
      <span class="c2">歌手</span>
      <span class="c3">专辑</span>
      <span class="c4">时长</span>
    </div>

    <h1 v-if="musicNum === 0">暂无最近诗词记录，快去词库看看吧！</h1>
    <div class="song-row" style="position: relative;color: black;font-size: 15px" v-for="(item,index) in musicList" :key="index" :class="{grayBg :　index % 2 === 1}">
        <span class="index">
          {{ index + 1 }}

        </span>
      <div class="plugin" style="display: none;position: absolute;left: 400px;height: 50px;top: -11px">
        <i @click="toMusicInfo(item.id)" class="play-icon"></i>
        <el-popover placement="right" trigger="click">
          <template #reference>
            <i @click="popSheet(item.id)" class="add-icon"></i>
          </template>
          <el-table :data="sheetList">
            <el-table-column  label="诗词" >
              <template #default="scope">
                <!-- 不仅查询所有歌单 还要查询歌曲对每个歌单的归属情况 -->
                <div v-if="scope.row.canAdd" :class="{gery:　scope.row.canAdd}" style="width: 170px">{{ scope.row.name  }}</div>
                <div v-else style="color: #1e1e1e;cursor:pointer;width: 170px" @click="addToSheet(scope.row.id,item.id)">{{ scope.row.name  }}</div>
              </template>
            </el-table-column>
          </el-table>
        </el-popover>
      </div>

      <span class="c1">
          <span class="hover" @click="toMusicInfo(item.id)" style="cursor:pointer;">{{ item.name }}</span>
        </span>

      <span class="c2">{{ item.musician }}</span>
      <span class="c3">{{ item.album }}</span>
      <span class="c4">{{ item.duration }}</span>
    </div>
    <!--    </div>-->

  </div>
</template>

<style scoped>
.song-row:hover > .plugin {
  display: block!important;

}

h1 {
  font-size: 35px;
  margin-top: 80px;
  font-weight: normal;
  color: black;
  text-align: center;
  height: 40px;
  line-height: 40px;
  font-family: poppin,Tahoma,Arial,\5FAE\8F6F\96C5\9ED1,sans-serif;

}
.gery {
  color: #ababab !important;
  cursor: default!important;
}
i {
  width: 72px;
  height: 72px;
  margin-right: -4px;
  margin-left: -6px;
  display: inline-block;
  background: url("http://localhost:5173/src/assets/img/total3.png") no-repeat;
  cursor: pointer;

}
.play-icon {
  background-position: 0 0;
  transform: scale(0.5);
  margin-bottom: 2px;
}
.play-icon:hover {
  background-position: -80px 0;

}
.add-icon {
  background-position: -0px -160px;
  transform: scale(0.5);
  margin-bottom: 2px;
}
.add-icon:hover {
  background-position: -80px -160px;

}

.index {
  position: absolute;
  top: 0;
  left: 10px;
  color: #999;
  width: 36px;
}
.container {
  padding: 0 calc(50vw - 600px) 100px;
}
.items {
  color: white;
  height: 57px;
}
.items>span {
  line-height: 57px;
  margin-right: 40px;
  cursor: pointer;
}
.items>span:hover {
  color: #e96161;
}
.active {
  color: #e96161;
}
.grayBg {
  background: rgba(0,0,0,.01);
}
.song-row {
  line-height: 50px;
  color: #999;
  font-size: 14px;
  padding-left: 46px;
  position: relative;
  font-family: poppin,Tahoma,Arial,\5FAE\8F6F\96C5\9ED1,sans-serif;
}
.hover:hover {
  color: #e96161;
}
.song-row>span {
  display: inline-block;
}
.c1 {
  width: 504px;
}
.c2 {
  width: 255px;
  padding-left: 15px;
}
.c3 {
  width: 255px;
  padding-left: 15px;
}
.c4 {
  position: absolute;
  top: 0;
  right: 38px;
  width: 50px;
}
* {

}
</style>
