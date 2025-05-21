<template>
  <el-card class="good-container">
    <template #header>
      <div class="header">
        <el-breadcrumb :separator-icon="ArrowRight">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>系统管理员</el-breadcrumb-item>
          <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </template>

    <!-- 添加搜索输入框 -->
    <div style="margin-bottom: 10px;">
      <el-input
          v-model="state.searchQuery"
          placeholder="请输入搜索的名称或者账号"
          style="width: 300px;"
      >
        <template #append>
          <el-button icon="Search" @click="getUserList"></el-button>
        </template>
      </el-input>
    </div>

    <el-table
        :load="state.loading"
        :data="state.tableData"
        tooltip-effect="dark"
        style="width: 100%"
    >
      <el-table-column
          prop="user_id"
          label="用户编号"
          width="150px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="nickname"
          label="昵称"
          width="150px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="username"
          label="账号"
          width="150px"
          align="center"
      >
      </el-table-column>
      <!-- <el-table-column
          prop="money"
          label="金额"
          width="100px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="point"
          label="积分"
          width="100px"
          align="center"
      >
      </el-table-column> -->
      <el-table-column
          prop="phoneNumber"
          label="电话号码"
          width="250px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          label="头像"
          width="150px"
          align="center"
      >
        <template #default="scope">
          <img style="width: 100px; height: 100px;" :key="scope.row.user_id" :src="getPrefixedImage(scope.row.avatar)" alt="用户头像">
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-row :gutter="10" style="display: flex; justify-content: center;">
            <el-col :span="6">
              <el-button type="primary" :icon="Edit" circle @click="editRoles(scope.row)"></el-button>
            </el-col>

          </el-row>
        </template>
      </el-table-column>
      </el-table>
    <!--总数超过一页，再展示分页器-->
    <el-pagination
        background
        layout="prev, pager, next"
        :total="state.total"
        :page-size="state.pageSize"
        :current-page="state.currentPage"
        @current-change="changePage"
    />
  </el-card>

  <el-dialog v-model="state.showEditRoleDialog" title="修改活动信息" width="50%"  >
    <el-form label-width="120px"  >
      <el-form-item label="昵称" >
        <el-input v-model="updateUserFrom.nickname" placeholder="请输入昵称" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="电话号码"  >
        <el-input v-model="updateUserFrom.phoneNumber" placeholder="请输入电话号码" style="width: 50%" ></el-input>
      </el-form-item>
      <!-- <el-form-item label="金额"  >
        <el-input v-model="updateUserFrom.money" placeholder="请输入金额" style="width: 50%" ></el-input>
      </el-form-item>
      <el-form-item label="积分"  >
        <el-input v-model="updateUserFrom.point" placeholder="请输入积分" style="width: 50%" ></el-input>
      </el-form-item> -->

      <!-- <el-form-item label="图片" >
        <el-upload
            class="avatar-uploader"
            :http-request="uploadURL"
            :before-upload="handleBeforeUpload"
            :limit="8"
            :show-file-list="false"
        >
          <img
              style="width: 100px; height: 100px; border: 1px solid #e9e9e9;"
              v-if="addForm.img"
              :src="addForm.img"
              class="avatar"
          >
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item> -->
      <!-- 可以根据需要添加更多的表单项 -->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="state.showEditRoleDialog = false">取 消</el-button>
      <el-button type="primary" @click="submitQuestionBankForm">提交</el-button>
    </span>
  </el-dialog>
</template>

<script setup>
import {onMounted, reactive, getCurrentInstance, computed, ref} from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import {Plus, Delete, ArrowRight, Edit} from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import {client} from "@/utils/alioss.js";
import {localSet} from "@/utils/index.js";


const app = getCurrentInstance()
const { goTop } = app.appContext.config.globalProperties
const router = useRouter()
let oldImg=''
const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 5, // 分页大小
  showEditRoleDialog:false,
  searchQuery:'',
})
let updateUserFrom=reactive({
  nickname:"",
  avatar:'',
  phoneNumber:'',
  user_id:0,
})
onMounted(() => {
  getUserList();
})
const menuRef = ref();
// 定义一个方法来添加前缀
function getPrefixedImage(imageUrl) {
  // 返回带有前缀的完整图片路径
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/avatar/${imageUrl}`;
}
// 获取轮播图列表
const getUserList = () => {
  state.loading = true
  axios.get('/users/list', {
    params: {
      page: state.currentPage,
      limit: state.pageSize,
      username:state.searchQuery,
      nickname:state.searchQuery
    }
  }).then(res => {
    state.tableData = res.records     //加载图标
    state.total = res.total //总计入数
    state.currentPage = res.current
    state.loading = false
    goTop && goTop()
  })
}
const handleAdd = () => {
  router.push({ path: '/adduser' })
}
const editRoles = (user) => {
  state.showEditRoleDialog=true
  updateUserFrom.user_id=user.user_id
  updateUserFrom.nickname=user.nickname
  updateUserFrom.phoneNumber=user.phoneNumber
  addForm.img=getPrefixedImage(user.avatar)
  oldImg = user.avatar
}
const submitQuestionBankForm = () => {

  if (!updateUserFrom.avatar){
    updateUserFrom.avatar = oldImg
  }
  axios.post("/users/updateUser",updateUserFrom).then(res=>{
    if (res.code === 200) {
      ElMessage({
        showClose:true,
        message:"修改成功",
        duration:2000,
        type:"success",
      })
      location.reload();
    }else {
      ElMessage({
        showClose:true,
        message:"修改失败",
        duration:2000,
        type:"error",
      })
    }
  })
}

const changeRoles =() =>{
  //得到选中的角色
  state.hasRoles = menuRef.value.getCheckedKeys();
  const params = {"userid":state.userid,"roles":state.hasRoles};
  axios.post("userrole/updateRoles",params).then(res=>{
    state.showEditRoleDialog = false;
  })
}
const changePage = (val) => {
  state.currentPage = val
  getUserList()
}

const addForm = reactive({
  img:null,
  title:'',
  oriFileName:null,
})
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
const uploadURL = (file) =>{
  console.log(file)
  let files = `${Date.parse(new Date())}`+ addForm.oriFileName;
  let fileName = 'jieImg/'+files;
  updateUserFrom.avatar=files;
  client().put(fileName,file.file).then((res)=>{//此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
    addForm.img=res.url
  })
}

</script>

<style scoped>
.good-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>
