<script setup>
import {ElMessage,ElMessageBox} from "element-plus";
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
function openMask() {
  // 新建诗集操作 页面稍微变灰，屏幕中间有一个诗集名输入框
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
    // 最后一步检验 向后端发请求添加诗集 检验诗集名是否有重复 然后如果成功 增添
    const formData = {
      "collection_name": addSheetName.value.trim(),
      "collection_avatar":'defaultPoems.jpg'
    }
    axios.post("/collection/addPoems",formData).then((res)=>{
      if (res.code==200) {
        ElMessage.success("成功添加到新建诗集")
        quitMask();      // 关闭新建窗口
        getList();       // 刷新诗集列表
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

function goTop() {
  window.scrollTo({ top: 0, behavior: 'smooth' });
}


const getList = () => {
  state.loading = true;
  axios.get("/users_collection/list", {
    params: {
      page: state.currentPage,
      limit: state.pageSize
    }
  }).then(res => {
    if (res.records && res.records.length > 0) {
      sheetInfo.value = res.records;
      state.total = res.total;
      state.currentPage = res.current;
      state.loading = false;
      goTop?.();
    } else {
      // 如果当前页无数据，且当前页不是第一页
      if (state.currentPage > 1) {
        state.currentPage--;
        getList(); // 重新请求上一页数据
      } else {
        // 第一页也没数据，清空列表
        sheetInfo.value = [];
        state.total = 0;
        state.loading = false;
      }
    }
  }).catch(err => {
    console.log(err);
    state.loading = false;
  });
};

// 先是发个请求获取用户的诗集数据
onMounted(()=> {
  getList();
})

function gotoDetail(id) {
  // 跳转到诗集详细页面
  router.push({name:'LookCollection',params:{collection_id:id}})
}

function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/assets/${imageName}`;
}

const changePage = (val) => {
  state.currentPage = val
  getList()
}

// 假设item是完整的诗集对象
const editForm = reactive({}); // 一开始为空

function editSheet(item) {
  state.showEditRoleDialog = true;
  // 用完整item数据初始化editForm（浅拷贝）
  Object.assign(editForm, item);
}

function confirmEdit() {
  if (editForm.collection_name.trim() === "") {
    ElMessage.error("请输入诗集名！");
    return;
  }
  if (editForm.collection_name.length > 20) {
    ElMessage.error("诗集名最多20个字！");
    return;
  }

  axios.post("/collection/update", editForm).then(res => {
    if (res.code === 200) {
      ElMessage({
        type: "success",
        duration: 2000,
        message: "修改成功"
      });
      // 清空editForm字段
      for (const key in editForm) {
        editForm[key] = '';
      }
      state.showEditRoleDialog = false;
      changePage(state.currentPage);
    } else {
      ElMessage({
        type: "error",
        message: "修改失败",
        duration: 2000
      });
    }
  }).catch(err => {
    console.error("修改异常：", err);
    ElMessage({
      type: "error",
      message: "请求失败，请稍后再试",
      duration: 2000
    });
  });
}


function cancelEdit() {
  state.showEditRoleDialog = false;
}

function deleteSheet(id) {
  ElMessageBox.confirm(
    '此操作将永久删除该诗集，是否继续？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    axios.post("/collection/delete", id).then(res => {
      if (res.code === 200) {
        ElMessage({
          message: '删除成功',
          duration: 2000,
          type: 'success',
        });
        getList(); // 重新获取列表
      } else {
        ElMessage({
          message: "删除失败",
          duration: 2000,
          type: "error",
        });
      }
    }).catch(err => {
      console.error("删除异常：", err);
      ElMessage({
        message: "请求失败，请稍后再试",
        duration: 2000,
        type: "error"
      });
    });
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
}


</script>

<template>


  <div id="app" class="container">
    <div style="margin: 5px 0">
      <el-button @click="openMask()" class="function" type="default">
        <i class="add"></i>新建诗集
      </el-button>
    </div>
    <div style="margin-top: 20px">
      <div class="header">
        <span style="width: 400px;padding-left: 20px">诗集</span>
        <span style="width: 200px;padding-left: 20px">诗集数量</span>
        <span style="width: 400px;padding-left: 20px">操作</span>
      </div>

      <div class="con" v-for="(item,index) in sheetInfo" :key="index" >

        <div style="width: 400px;display: flex;align-items: center;" >
          <img width="50" height="50"  :src=getPublicImageUrl(item.collection[0].collection_avatar)>
          <div @click="gotoDetail(item.collection_id)" class="text" style="text-indent: 15px;">{{ item.collection[0].collection_name }}</div>
        </div>
        <div style="width: 200px;">{{ item.collection[0].collection_count }}首</div>
        <div style="width: 400px;">
          <el-button size="large" @click="editSheet(item)">修改</el-button>
          <el-button size="large" type="danger" @click="deleteSheet(item.collection_id)">删除</el-button>
        </div>
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

  <el-dialog
    title="修改诗集"
    v-model="state.showEditRoleDialog"
    width="500px"
    :before-close="cancelEdit"
  >
    <div style="margin-bottom: 10px">请输入新的诗集名：</div>
    <div class="wrapper">
      <input class="addSheetInput" v-model="editForm.collection_name" />
      <span :class="{colorRed : editRemainWords < 0}"
            style="position: absolute;font-weight: 400;color: rgb(153, 153, 153);right: 18px;">
        {{ editRemainWords }}
      </span>
    </div>
    <template #footer>
      <el-button @click="cancelEdit">取消</el-button>
      <el-button type="primary" @click="confirmEdit">确认修改</el-button>
    </template>
  </el-dialog>
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
  /* background: url("http://localhost:5173/src/assets/img/total.png") no-repeat ; */
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
  padding: 0 23px;
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
