<template>
  <el-card class="good-container">
    <template #header>
      <div class="header">
        <el-breadcrumb :separator-icon="ArrowRight">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>诗会管理</el-breadcrumb-item>
          <el-breadcrumb-item>题库管理</el-breadcrumb-item>
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
      <el-button type="primary" :icon="Plus" @click="handleAdd">添加题库</el-button>
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
          prop="questionBank_id"
          label="编号"
          width="100px"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="question"
          label="问题"
          align="center"
      >
      </el-table-column>
      <el-table-column label="选项" align="center">
        <template #default="scope">
          <div v-for="(option, key) in scope.row.options" :key="key" >
            <p  v-if="key !== 'options_id'">{{key.toUpperCase()}}:{{ option }}</p>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          prop="answer"
          label="答案"
          width="100px"
          align="center"
      >
      </el-table-column>


      <el-table-column label="操作" align="center">

        <template #default="scope">
          <el-row :gutter="10" style="display: flex; justify-content: center;">
            <el-col :span="6">
              <el-button type="primary" :icon="Edit" circle @click="editRoles(scope.row)"></el-button>
            </el-col>
            <el-popconfirm title="您确定删除吗?" confirmButtonText="确定" cancelButtonText="取消" @confirm="deleteQuestion(scope.row.questionBank_id)">
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
  <el-dialog v-model="state.showEditRoleDialog" title="修改题库信息" width="50%"  >
    <el-form label-width="120px"  >
      <el-form-item label="题目" >
        <el-input v-model="questionBankForm.question" placeholder="请输入题目" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="答案"  >
        <el-input v-model="questionBankForm.answer" placeholder="请输入答案" style="width: 50%" ></el-input>
      </el-form-item>

      <el-form-item label="选项">
        <div v-for="(option, key) in questionBankForm.options" :key="key"  >
          <div v-if="key!=='options_id'" >
          <strong>{{ key.toUpperCase() }}: </strong>
          <el-input
              v-model="questionBankForm.options[key]"
              placeholder="请输入选项内容"
              clearable
          ></el-input>
          </div>
        </div>
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
  searchQuery:''
})
let questionBankForm=reactive({
  questionBank_id:0,
  answer:'',
  question:'',
  options:[],
})
onMounted(() => {
  getQuestionBank();
})



// 获取轮播图列表
const getQuestionBank = () => {
  state.loading = true
  axios.get('/questionbank/page', {
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
  router.push({ path: '/addQuestion' })
}
const editRoles = (question) => {
  state.showEditRoleDialog=true
  questionBankForm.answer=question.answer;
  questionBankForm.question=question.question;
  questionBankForm.options=question.options;
  questionBankForm.questionBank_id=question.questionBank_id;
}
const submitQuestionBankForm=()=>{
  axios.post("/questionbank/updateQuestion",questionBankForm).then(res=>{
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
  axios.post("/questionbank/deleteQuestionBank?id="+id).then(res=>{
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
  let id = selectAry.map(item => item.questionBank_id);
  console.log(id)
  axios.post("/questionbank/deleteAll",id).then(res =>{
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



</script>

<style scoped>
.good-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}

</style>
