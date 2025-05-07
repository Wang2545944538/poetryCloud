<script setup>
import { Plus } from "@element-plus/icons-vue";
import {onMounted, reactive, ref, watch} from "vue";
import axios from "@/utils/axios.js";
import {ElMessage} from "element-plus";

// 初始化响应式数据
const state = reactive({
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 9, // 分页大小
  loading: false,
  tableData: [], // 表格数据
  showEditPoemDialog:false,
  showDeletePoemDialog:false,
  showAddPoemDialog:false,
  poem_id:0,
  pf_id:0,
  authors:[],
  genres:[],
  themes:[],
  searchTitle:''
});

// 组件挂载后获取诗词列表
onMounted(() => {
  getPoemList();
  getAuthors();
  getDynasties();
  getThemes();
  getGenres();
});

// 获取诗词列表
const getPoemList = () => {
  state.loading = true;
  const params = {
    currentPage: state.currentPage,
    pageSize: state.pageSize,
    authorIds: null,
    dynastyIds: null,
    genreIds: null,
    themeIds: null,
    title:state.searchTitle
  };
  axios.post("/poem/getPoems", params)
      .then(res => {
        state.tableData = res.data.records;
        state.total = res.data.total;
        state.loading = false;
      })
      .catch(() => {
        state.loading = false;
      });
};

const getAuthors = () => {
  axios.get("/author/authorList")
      .then(res=>{
        state.authors = res.data;
      })
}

const getDynasties = () => {
  axios.get("/dynasty/dynastyList")
      .then(res=>{
        state.dynasties = res.data;
      })
}

const getGenres = () => {
  axios.get("/genre/genreList")
      .then(res=>{
        state.genres = res.data;
      })
}

const getThemes = () => {
  axios.get("/theme/themeList")
      .then(res=>{
        state.themes = res.data;
      })
}

// 切换页码
const changePage = (val) => {
  state.currentPage = val;
  getPoemList();
};

let EditPoemForm = ref({
  title: '',
  authorId: '',
  content: '',
  genreId: '',
  themeId: '',
  intro: '',
  pingxi:'',
  comments:'',
  translation:'',
  jiping:''
})

let AddPoemForm = ref({
  title: '',
  authorId: '',
  content: '',
  genreId: '',
  themeId: '',
  intro: '',
  pingxi:'',
  comments:'',
  translation:'',
  jiping:''
})

// 定义一个方法来重置表单数据
const resetEditPoemForm = () => {
  EditPoemForm.value = {
    title: '',
    authorId: '',
    content: '',
    genreId: '',
    themeId: '',
    intro: '',
    pingxi: '',
    comments: '',
    translation: '',
    jiping: ''
  };
};
const resetAddPoemForm = () => {
  AddPoemForm.value = {
    title: '',
    authorId: '',
    content: '',
    genreId: '',
    themeId: '',
    intro: '',
    pingxi: '',
    comments: '',
    translation: '',
    jiping: ''
  };
};

// 添加一个方法来关闭弹窗并清空表单数据
const closeEditPoemDialog = () => {
  state.showEditPoemDialog = false;
  resetEditPoemForm();
};

const closeAddPoemDialog = () => {
  state.showAddPoemDialog=false;
  resetAddPoemForm();
}

// 监听弹窗显示状态
watch(() => state.showEditPoemDialog,(newValue, oldValue) => {
  if (!newValue) { // 当弹窗关闭时
    resetEditPoemForm(); // 重置表单数据
  }
});
// 监听弹窗显示状态
watch(() => state.showAddPoemDialog,(newValue, oldValue) => {
  if (!newValue) { // 当弹窗关闭时
    resetAddPoemForm(); // 重置表单数据
  }
});

const getPoemDetails = (poemId) => {
  axios.get("/poem/getPoemDetails?poemId="+poemId)
      .then(res=>{
        state.pf_id=res.data.poeminformation.pf_id;
        EditPoemForm.value = {
          title: res.data.title,
          authorId: res.data.author_id,
          content: res.data.content,
          genreId: res.data.genre_id,
          themeId: res.data.theme_id,
          intro: res.data.intro,
          pingxi:res.data.poeminformation.pingxi,
          comments:res.data.poeminformation.comments,
          translation:res.data.poeminformation.translation,
          jiping:res.data.poeminformation.jiping
        };
      })
}

const editPoem = (poem_id) => {
  state.poem_id=poem_id;
  state.showEditPoemDialog=true;
  getPoemDetails(poem_id);
}

const deletePoem = (poem_id) => {
  state.poem_id=poem_id;
  state.showDeletePoemDialog=true;
  getPoemDetails(poem_id);
}
const addPoem = () => {
  state.showAddPoemDialog=true;
}

const closeDeletePoemDialog = () => {
  state.showDeletePoemDialog=false;
}

const doEdit = () => {
  const params = {
    title: EditPoemForm.value.title,
    author_id:EditPoemForm.value.authorId,
    content:EditPoemForm.value.content,
    genre_id:EditPoemForm.value.genreId,
    theme_id:EditPoemForm.value.themeId,
    intro:EditPoemForm.value.intro,
    pingxi:EditPoemForm.value.pingxi,
    comments:EditPoemForm.value.comments,
    translation:EditPoemForm.value.translation,
    jiping:EditPoemForm.value.jiping,
    poem_id:state.poem_id,
    pf_id:state.pf_id
  }
  axios.post("/poem/updatePoem",params)
      .then(res=>{
        if (res.code===200) {
          ElMessage({
            message: res.data,
            type: 'success',
            duration: 2000
          });
        } else {
          ElMessage({
            message: res.data,
            type: 'error',
            duration: 2000
          });
        }
        state.showEditPoemDialog=false;
        getPoemList();
      })
}
const doDelete = () => {
  const params = {"poem_id":state.poem_id,"pf_id":state.pf_id};
  console.log(params)
  axios.post("/poem/deletePoem",params)
      .then(res=>{
        if (res.code===200) {
          ElMessage({
            message: res.data,
            type: 'success',
            duration: 2000
          });
        } else {
          ElMessage({
            message: res.data,
            type: 'error',
            duration: 2000
          });
        }
        state.showDeletePoemDialog=false;
        getPoemList();
      })
}
const doAdd = () => {
  const params = {
    title: AddPoemForm.value.title,
    author_id:AddPoemForm.value.authorId,
    content:AddPoemForm.value.content,
    genre_id:AddPoemForm.value.genreId,
    theme_id:AddPoemForm.value.themeId,
    intro:AddPoemForm.value.intro,
    pingxi:AddPoemForm.value.pingxi,
    comments:AddPoemForm.value.comments,
    translation:AddPoemForm.value.translation,
    jiping:AddPoemForm.value.jiping
  }
  axios.post("/poem/addPoem",params)
      .then(res=>{
        if (res.code===200) {
          ElMessage({
            message: res.data,
            type: 'success',
            duration: 2000
          });
        } else {
          ElMessage({
            message: res.data,
            type: 'error',
            duration: 2000
          });
        }
        state.showAddPoemDialog=false;
        getPoemList();
      })
}

const searchTitle = ref('');

// 定义搜索方法
const searchPoemByTitle = () => {
  state.loading = true;
  state.searchTitle=searchTitle.value;
  getPoemList();
};

</script>

<template>
  <el-card class="poem-container" shadow="hover">
    <template #header>
      <div class="header d-flex justify-content-between align-items-center">
        <span class="card-title">诗词列表</span>
        <!-- 搜索框和搜索按钮 -->
        <div style="display: flex; align-items: center;">
          <el-input
              v-model="searchTitle"
              placeholder="请输入诗词题目"
              style="margin-right: 10px; width: 200px;"
          ></el-input>
          <el-button type="primary" @click="searchPoemByTitle">搜索</el-button>
        </div>
        <el-button type="primary" :icon="Plus" @click="addPoem">添加诗词</el-button>
      </div>
    </template>
    <el-table
        v-loading="state.loading"
        :data="state.tableData"
        tooltip-effect="dark"
        style="width: 100%"
        class="custom-table"
    >
      <el-table-column
          prop="title"
          label="题目"
          min-width="120"
      />
      <el-table-column
          prop="author.author_name"
          label="作者"
          min-width="80"
      />
      <el-table-column
          prop="intro"
          label="介绍"
          min-width="120"
      />
      <el-table-column
          label="操作"
          width="120"
      >
        <template #default="scope">
          <el-button type="primary" size="small" @click="editPoem(scope.row.poem_id)">编辑</el-button>
          <el-button type="danger" size="small" @click="deletePoem(scope.row.poem_id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 总数超过一页，展示分页器 -->
    <el-pagination
        background
        layout="total, prev, pager, next, jumper"
        :total="state.total"
        :page-size="state.pageSize"
        :current-page="state.currentPage"
        @current-change="changePage"
        class="custom-pagination"
    />
  </el-card>
  <el-dialog v-model="state.showEditPoemDialog" title="修改诗词信息" class="custom-dialog">
    <el-form>
      <el-form-item label="题目">
        <el-input placeholder="请输入题目" v-model="EditPoemForm.title"></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-select v-model="EditPoemForm.authorId" placeholder="请选择作者" class="Author-select">
          <el-option
              v-for="author in state.authors"
              :key="author.author_id"
              :label="author.author_name"
              :value="author.author_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <el-input placeholder="请输入内容" v-model="EditPoemForm.content"></el-input>
      </el-form-item>
      <el-form-item label="体裁">
        <el-select v-model="EditPoemForm.genreId" placeholder="请选择体裁" class="genre-select">
          <el-option
              v-for="genre in state.genres"
              :key="genre.genre_id"
              :label="genre.g_type"
              :value="genre.genre_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题材">
        <el-select v-model="EditPoemForm.themeId" placeholder="请选择题材" class="theme-select">
          <el-option
              v-for="theme in state.themes"
              :key="theme.theme_id"
              :label="theme.t_type"
              :value="theme.theme_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="简介">
        <el-input placeholder="请输入简介" v-model="EditPoemForm.intro"></el-input>
      </el-form-item>
      <el-form-item label="评析">
        <el-input placeholder="请输入评析" v-model="EditPoemForm.pingxi"></el-input>
      </el-form-item>
      <el-form-item label="注释">
        <el-input placeholder="请输入注释" v-model="EditPoemForm.comments"></el-input>
      </el-form-item>
      <el-form-item label="译文">
        <el-input placeholder="请输入译文" v-model="EditPoemForm.translation"></el-input>
      </el-form-item>
      <el-form-item label="辑评">
        <el-input placeholder="请输入辑评" v-model="EditPoemForm.jiping"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="doEdit">确认</el-button>
        <el-button @click="closeEditPoemDialog">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="state.showDeletePoemDialog" title="提示" class="custom-dialog">
    <div>确认要删除该诗吗?</div>
    <el-button @click="doDelete">确认</el-button>
    <el-button @click="closeDeletePoemDialog">取消</el-button>
  </el-dialog>

  <el-dialog v-model="state.showAddPoemDialog" title="添加诗词" class="custom-dialog">
    <el-form>
      <el-form-item label="题目">
        <el-input placeholder="请输入题目" v-model="AddPoemForm.title"></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-select v-model="AddPoemForm.authorId" placeholder="请选择作者" class="Author-select">
          <el-option
              v-for="author in state.authors"
              :key="author.author_id"
              :label="author.author_name"
              :value="author.author_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <el-input placeholder="请输入内容" v-model="AddPoemForm.content"></el-input>
      </el-form-item>
      <el-form-item label="体裁">
        <el-select v-model="AddPoemForm.genreId" placeholder="请选择体裁" class="genre-select">
          <el-option
              v-for="genre in state.genres"
              :key="genre.genre_id"
              :label="genre.g_type"
              :value="genre.genre_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题材">
        <el-select v-model="AddPoemForm.themeId" placeholder="请选择题材" class="theme-select">
          <el-option
              v-for="theme in state.themes"
              :key="theme.theme_id"
              :label="theme.t_type"
              :value="theme.theme_id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="简介">
        <el-input placeholder="请输入简介" v-model="AddPoemForm.intro"></el-input>
      </el-form-item>
      <el-form-item label="评析">
        <el-input placeholder="请输入评析" v-model="AddPoemForm.pingxi"></el-input>
      </el-form-item>
      <el-form-item label="注释">
        <el-input placeholder="请输入注释" v-model="AddPoemForm.comments"></el-input>
      </el-form-item>
      <el-form-item label="译文">
        <el-input placeholder="请输入译文" v-model="AddPoemForm.translation"></el-input>
      </el-form-item>
      <el-form-item label="辑评">
        <el-input placeholder="请输入辑评" v-model="AddPoemForm.jiping"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="doAdd">确认</el-button>
        <el-button @click="closeAddPoemDialog">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped>
.poem-container {
  margin: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.header {
  padding: 15px 20px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
}

.card-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.custom-table {
  border-collapse: collapse;
  margin-top: 20px;
}

.custom-table th,
.custom-table td {
  border-bottom: 1px solid #ebeef5;
  padding: 12px 0;
}

.custom-pagination {
  margin-top: 20px;
  text-align: right;
  padding: 10px 20px;
  background-color: #f5f7fa;
  border-top: 1px solid #ebeef5;
}

.d-flex {
  display: flex;
}

.justify-content-between {
  justify-content: space-between;
}

.align-items-center {
  align-items: center;
}

.custom-dialog .el-dialog {
  border-radius: 8px;
}

.custom-dialog .el-dialog__header {
  background-color: #f5f7fa;
  padding: 15px 20px;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.custom-dialog .el-dialog__title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.custom-dialog .el-dialog__body {
  padding: 20px;
}

.custom-dialog .el-dialog__footer {
  padding: 10px 20px 20px;
  background-color: #f5f7fa;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
}

.custom-dialog .el-form-item {
  margin-bottom: 20px;
}

.custom-dialog .el-button {
  margin-left: 10px;
}
</style>