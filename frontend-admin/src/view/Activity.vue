<template>
  <el-card class="good-container">
    <template #header>
      <div class="header">
        <el-breadcrumb :separator-icon="ArrowRight">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>诗会管理</el-breadcrumb-item>
          <el-breadcrumb-item>活动管理</el-breadcrumb-item>
        </el-breadcrumb>
        <br>

      </div>
    </template>

    <!-- 添加搜索输入框 -->
    <div style="margin-bottom: 10px;">
      <!--      <el-input
                v-model="state.searchQuery"
                placeholder="请输入关键字搜索"
                style="width: 300px;"
            >
              <template #append>
                <el-button icon="Search" @click="searchUsers"></el-button>
              </template>
            </el-input>-->
      <el-button type="primary" :icon="Plus" @click="handleAdd">添加活动</el-button>
      <el-button @click="getChecked" type="primary" >选择删除</el-button>

    </div>

    <el-table
        ref="tableRef"
        :load="state.loading"
        :data="state.tableData"
        tooltip-effect="dark"
        border
        style="width: 100%;
        display:flex"
    >
      <el-table-column type="selection" width="50" fixed="left"></el-table-column>
      <el-table-column
          prop="active_id"
          label="编号"
          width="100px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="active_content"
          label="活动标题"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="active_profile"
          label="活动简介"
          align="center"
      >
        <template #default="scope">
    <span
        :title="scope.row.active_profile"
        @click="toggleExpand(scope.row)"
        :class="{ 'truncate': !state.expandedRows[scope.row.active_id] }"
        style="cursor: pointer;"
    >
      {{ state.expandedRows[scope.row.active_id] ? scope.row.active_profile : scope.row.active_profile.slice(0, 10) + '...' }}
    </span>
        </template>
      </el-table-column>

      <el-table-column label="类型" align="center">
        <template #default="scope">
          <div v-for="(option, key) in scope.row.activityType" :key="key" >
            <p  v-if="key !== 'active_type_id'">{{ option }}</p>
          </div>
        </template>
      </el-table-column>

      <el-table-column
          label="发布时间"
          align="center"
      >
        <template #default="scope">
          {{ formatDate(scope.row.active_time) }}
        </template>
      </el-table-column>

      <el-table-column
          label="图片"
          width="150px"
          align="center"
      >
        <template #default="scope">
            <img style="width: 100px; height: 100px;" :key="scope.row.active_id" :src="getPrefixedImage(scope.row.active_img)" alt="用户头像">
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-row :gutter="10" style="display: flex; justify-content: center;">
            <el-col :span="6">
              <el-button type="primary" :icon="Edit" circle @click="editRoles(scope.row)"></el-button>
            </el-col>
            <el-popconfirm title="您确定删除吗?" confirmButtonText="确定" cancelButtonText="取消" @confirm="deleteQuestion(scope.row.active_id)">
              <template #reference>
                <el-col :span="6">
                  <el-button circle :icon="Delete" type="danger" ></el-button>
                </el-col>
              </template>
            </el-popconfirm>
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
      <el-form-item label="活动标题" >
        <el-input v-model="updateActiveFrom.active_content" placeholder="请输入活动标题" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="活动内容"  >
        <el-input v-model="updateActiveFrom.active_profile" placeholder="请输入活动内容" style="width: 50%" ></el-input>
      </el-form-item>

      <el-form-item label="类型">
        <el-select v-model="updateActiveFrom.active_type_id" placeholder="请选择活动类型" style="width: 300px" >
          <el-option
              v-for="typeList in state.typeList"
              :key="typeList.active_type_id"
              :label="typeList.activity_type"
              :value="typeList.active_type_id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="图片" >
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
      </el-form-item>
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

//批量
const tableRef = ref();
const app = getCurrentInstance()
const { goTop } = app.appContext.config.globalProperties
const router = useRouter()
const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 5, // 分页大小
  showEditRoleDialog:false,
  searchQuery:'',
  expandedRows: {} ,// 用于存储展开状态的对象
  typeList:{},
  oldImg:''
})
let updateActiveFrom=reactive({
  active_content:"",
  active_img:'',
  active_profile:'',
  active_type_id:0,
  active_id:0
})
onMounted(() => {
  getQuestionBank();
  axios.post("/activity_type/list").then(res=>{
    state.typeList=res
  })
})

// 添加这个方法到 script setup 中
function formatDate(dateString) {
  if (dateString) {
    const date = new Date(dateString);
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
  }
  return '';
}
function getPrefixedImage(imageUrl) {
  // 返回带有前缀的完整图片路径
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/jieImg/${imageUrl}`;
}

// 获取轮播图列表
const getQuestionBank = () => {
  state.loading = true
  axios.get('/activity/page', {
    params: {
      page: state.currentPage,
      limit: state.pageSize
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
  router.push({ path: '/addActivity' })
}
const editRoles = (active) => {
  state.showEditRoleDialog=true
  updateActiveFrom.active_profile=active.active_profile;
  updateActiveFrom.active_content=active.active_content;
  updateActiveFrom.active_type_id=active.activityType.active_type_id;
  addForm.img=getPrefixedImage(active.active_img);
  updateActiveFrom.active_id=active.active_id
  state.oldImg = active.active_img
}
const submitQuestionBankForm=()=>{
  if (!updateActiveFrom.active_img){
    updateActiveFrom.active_img = state.oldImg
  }
  axios.post("/activity/updateActive",updateActiveFrom).then(res=>{
    if (res.code == 200) {
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

const changePage = (val) => {
  state.currentPage = val
  getQuestionBank()
}
const deleteQuestion = (id) => {
  axios.post("/activity/deleteActive?id="+id).then(res=>{
    if (res.code === 200) {
      ElMessage({
        showClose:true,
        message:"删除成功",
        duration:2000,
        type:"success",
      })
      location.reload();
    }else {
      ElMessage({
        showClose:true,
        message:"删除失败",
        duration:2000,
        type:"error",
      })
    }
  })
}

const getChecked =() =>{
  const selectAry =tableRef.value.getSelectionRows();
  console.log(selectAry)
  let id = selectAry.map(item => item.active_id);
  console.log(id)
  axios.post("/activity/deleteAllActivity",id).then(res =>{
    if (res.code == 200){
      ElMessage({
        showClose:true,
        message:"删除成功",
        type:"success",
        duration:200,
      })
      location.reload();
    }else{
      ElMessage({
        showClose:true,
        message:"删除失败",
        type:"error",
        duration:200,
      })
    }
  })

}

// 切换展开状态
const toggleExpand = (row) => {
  state.expandedRows[row.active_id] = !state.expandedRows[row.active_id];
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
  updateActiveFrom.active_img=files;
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
/* 确保按钮在同一行上对齐 */
.el-table .el-table__body .el-table__row .el-button {
  display: block;
  width: 100%;
  margin-bottom: 5px;
}

/* 如果需要调整按钮的间距或样式 */
.el-table .el-table__body .el-table__row .el-button:last-child {
  margin-bottom: 0;
}

/* 使按钮在操作列中居中显示 */
.el-table .el-table__body .el-table__row .el-row {
  display: flex;
  justify-content: center;
}
</style>
