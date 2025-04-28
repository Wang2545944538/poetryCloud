<script setup>
import {ElMessage} from "element-plus";
import {computed, onMounted,reactive, ref} from "vue";
import axios from "@/utils/axios";

import {useRoute, useRouter} from "vue-router";
import router from "@/routes/index.js";
const addSheetName = ref('');
const sheetInfo = ref([]);
const state = reactive({
  loading: false,
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 5, // 分页大小
  showEditRoleDialog:false,

})
const route = useRoute()
function ready() {
  ElMessage.info('该功能待开发')
}
function openMask() {
  // 新建歌单操作 页面稍微变灰，屏幕中间有一个歌单名输入框
  document.getElementById('mask').style.display = 'flex'
}
function newSheet() {
  // 先检查为不为空
  if (addSheetName.value.trim() === "") {
    // 提醒为空
    ElMessage.error('请输入诗集名！')
    // 由于可能有空格 就清空
    addSheetName.value = ''
  } else if (addSheetName.value.length > 20) {
    // 歌名最多20个字
    ElMessage.error('诗集名最多20个字！！')
  } else {
    // 最后一步检验 向后端发请求添加歌单 检验歌单名是否有重复 然后如果成功 增添
    const formData = {
      "collection_name": addSheetName.value.trim(),
      "collection_avatar":'defaultPoems.jpg'
    }
    axios.post("/collection/addPoems",formData).then((res)=>{
      if (res.code==200) {
        ElMessage.success("成功添加到新建歌单")
        // 将新创建的歌单添加到列表中
        sheetInfo.value.push(newFolder);
        quitMask()
      } else {
        ElMessage.error("创建失败,诗集已存在")
      }
    }).catch(err => {
      console.log(err.data)
    })
  }


}
function quitMask() {
  document.getElementById('mask').style.display = 'none'
  addSheetName.value = ''
}
function quitQuit(event) {
  event.stopPropagation();
}

const remainWords = computed(() => {
      return 20 - addSheetName.value.trim().length
    }
)

function cancel() {
  quitMask()
}
const getList=()=>{

  axios.get("/users_collection/list", {
    params: {
      page: state.currentPage,
      limit: state.pageSize
    }}).then((res)=>{
    console.log(res.records[0].collection)
    if (res.records){
      sheetInfo.value=res.records
      state.total = res.total //总计入数
      state.currentPage = res.current
      state.loading = false
      goTop && goTop()
    }else {
      console.log("数据为空")
    }
  }).catch(err => {
    console.log(err.data)
  })
}
// 先是发个请求获取用户的歌单数据
onMounted(()=> {
  getList();
})

function gotoDetail(id) {
  // 跳转到歌单详细页面
  router.push({name:'LookCollection',params:{collection_id:id}})
}

function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/assets/${imageName}`;
}

const changePage = (val) => {
  state.currentPage = val
  getList()
}

</script>

<template>


  <div id="app" class="container">
    <div style="margin: 5px 0">
      <el-button @click="openMask()" class="function" type="default">
        <i class="add"></i>新建诗集
      </el-button>
      <el-button @click="ready()" class="function" type="default">待开发按钮</el-button>
    </div>
    <div style="margin-top: 20px">
      <div class="header">
        <span style="width: 577px;padding-left: 20px">诗集</span>
        <span style="width: 422px;padding-left: 20px">诗集数量</span>
      </div>

      <div class="con" v-for="(item,index) in sheetInfo" :key="index" >

        <div style="width: 577px;display: flex;align-items: center;" >
          <img width="50" height="50"  :src=getPublicImageUrl(item.collection[0].collection_avatar)>
          <div @click="gotoDetail(item.collection_id)" class="text" style="text-indent: 15px;">{{ item.collection[0].collection_name }}</div>
        </div>
        <div>{{ item.collection[0].collection_count }}首</div>
    </div>


    </div>
    <el-pagination
        background
        layout="prev, pager, next"
        :total="state.total"
        :page-size="state.pageSize"
        :current-page="state.currentPage"
        @current-change="changePage"
    />
  </div>
<!--创建诗集-->
  <div id="mask" ref="mask" @click="quitMask()">

    <div class="mask_content" style="" @click.stop="quitQuit()">
      <div class="mask_top">创建新诗集</div>
      <div style="margin-top: 40px;display: flex">
        <label style="margin-left: 40px;margin-right: 15px;line-height: 38px;font-size: 15px">诗集名</label>

        <div class="wrapper">
          <input class="addSheetInput" v-model="addSheetName">
          <span :class="{colorRed : remainWords < 0}" style="position: absolute;font-weight: 400;color: rgb(153, 153, 153);right: 18px;">{{ remainWords }}</span>
        </div>
      </div>
      <div style="margin-top: auto;padding: 20px 20px 0 0;height: 60px;text-align: right;">
        <el-button @click="newSheet()" class="function confirm" type="default">确认</el-button>
        <el-button @click="cancel()" class="function cancel" type="default">取消</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

.con {
  display: flex;
  padding-left: 20px;
  font-weight: 400;
  font-family: poppin, Tahoma, Arial, \5FAE\8F6F\96C5\9ED1, sans-serif;

}
.con>div {
  height: 70px;
  line-height: 70px
}
.text:hover {
  color: #e4836f;
  cursor: pointer;
}
.header {

}
.header>span {
  height: 50px;
  color: #999;
  font-size: 14px;
  line-height: 50px;
  display: inline-block;
  box-sizing: border-box;
}

.cancel {
  width: 122px!important;
}
.confirm {
  background: #ffb17b !important;
  color: white!important;
  border-color: #eaa470 !important;
  width: 122px!important;
}
.confirm:hover {
  background: #f8ac77 !important;
}
.colorRed {
  color: red!important;
}
.wrapper {
  width: 377px;
  border: 1px solid #c8cbcc;
  position: relative;
  font-size: 14px;
  line-height: 38px;
  font-weight: 300;
}
.addSheetInput {
  width: 330px;
  height: 38px;
  font-family: poppin,Tahoma,Arial,\5FAE\8F6F\96C5\9ED1,sans-serif;
  color: #000;
  border: 0;
  margin-left: 11px;
  outline: 0;
}
.mask_top {
  height: 55px;
  line-height: 55px;
  text-align: center;
  font-size: 16px;
  border-bottom: 1px solid #f2f2f2;
}
#mask{
  width: 100%;
  background-color: rgba(0, 0, 0, 0.2);
  bottom: 0;
  left: 0;
  top: 0;
  right: 0;
  position: fixed;
  z-index: 998;
  display: none;
}
.mask_content{
  background: white;
  width: 520px;
  z-index: 999;
  position: fixed;
  height: 242px;
  left: 50%;
  transform: translate(-50%,-50%);
  top: 50%;
  display: flex;
  flex-direction: column;
}
i {
  width: 32px;
  height: 32px;
  margin-right: -4px;
  margin-left: -6px;
  display: inline-block;
  background: url("http://localhost:5173/src/assets/img/total.png") no-repeat ;
}

.add {
  transform: scale(0.5);
  background-position: 0 -360px;
}

.container {
  padding: 20px calc(50vw - 600px) 100px;
}
.function {
  height: 38px;
  line-height: 38px;
  font-size: 14px;
//padding: 0 23px;
  width: 125.6px;
  box-sizing: border-box;
  border-color: #c9c9c9;
  border-radius: 0;
}
.function:hover {
  background: #ededed;
  color: black;
  border-color: #c9c9c9;
}
</style>
