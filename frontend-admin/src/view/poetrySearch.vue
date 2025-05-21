<script  lang="ts" setup>
import axios from '../utils/axios'
import {reactive, onMounted, ref, onBeforeUnmount} from 'vue'
import {ElMessage, UploadProps} from 'element-plus'
import WangEditor from 'wangeditor'
import {client,getFileNameUUID} from '../utils/alioss.js';
import {localGet} from "../utils/index.js";
import {Plus} from "@element-plus/icons-vue";





const uploadURL = (file) =>{
  let f = `${Date.parse(new Date())}`+ addForm.oriFileName;
  let fileName = 'assets/'+f;
  addForm.collection_avatar=f;
  client().put(fileName, file.file).
  then((res)=>{//此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
    addForm.img = res.url;
  })
}

const uploadURL1 = (file) =>{
  let f = `${Date.parse(new Date())}`+ editForm.oriFileName;
  let fileName = 'assets/'+f;
  editForm.collection_avatar=f;
  client().put(fileName, file.file).
  then((res)=>{//此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
    editForm.img = res.url;
  })
}


let imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {

  addForm.oriFileName = rawFile.name;

  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error(' picture must be JPG or PNG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error(' picture size can not exceed 2MB!')
    return false
  }
  return true
};

const beforeAvatarUpload1: UploadProps['beforeUpload'] = (rawFile) => {

  editForm.oriFileName = rawFile.name;

  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error(' picture must be JPG or PNG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error(' picture size can not exceed 2MB!')
    return false
  }
  return true
};

const data = reactive({
  page: 1, // 当前页码
  limit: 4, // 每页数量
  total: 0, // 总记录数
  tableData: [], // 分页的表格数据
  showAddDialog:false,
  showEditDialog:false,
  showConfirmDelete:false,
  showAddPoemsDialog:false,
  collection_id:'',
  users:[],
  poems:[],
  search:"",
  addPoems:[],
  defaultCheckedPoems:[],
})
const tableRef = ref()

// 分页跳转函数, pg是跳转的页码
const changePage = (pg) => {
  data.page = pg
  init()
}

// 钩子函数，页面加载完后自动调用
onMounted(() => {
  axios.get("/users/list1").then(res=>{
    data.users=res
    console.log("users",data.users)
  })
  axios.get("/poem/list").then(res=>{
    data.poems=res.data.records
    console.log("poems",data.poems)
  })
  init()
})


// 初始化数据的函数
const init = () => {
  axios.get("/collection/select", {
    params: {
      page: data.page,
      limit: data.limit,
    }
  }).then(res => {
    data.tableData = res.data.records
    data.total = res.data.total
    data.page=res.data.current
    data.limit = res.data.size

  })
}

function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/assets/${imageName}`;
}


const toAdd =()=>{
  data.showAddDialog=true;
 imageUrl.value= getPublicImageUrl(addForm.oriFileName)
}


let addForm=reactive({
  collection_name:'',
  collection_avatar:'',
  collection_count:0,
  user_id:"",
  img: "",
  oriFileName: '', //保存上传原始文件
})

let editForm=reactive({
  collection_id:'',
  collection_name:'',
  collection_avatar:'',
  collection_count:'',
  user_id:"",
  img: "",
  oriFileName: '', //保存上传原始文件
})

const add=()=>{

  console.log(addForm)
  axios.post("/collection/add",addForm).then(res=>{
    if (res.code === 200) {
      ElMessage({
        message:'添加成功',
        duration:2000,
        type:'success',
      })
      // 成功后刷新页面

      addForm={
        collection_name:'',
        collection_avatar:'',
        collection_count:0,
        user_id:"",
        img: "",
        oriFileName: '', //保存上传原始文件
      }
      data.showAddDialog=false
      changePage(data.page);
    } else {
      ElMessage({
        message: "添加失败",
        duration: 2000,
        type: "error",
      })
    }
  })

}

const deleteC = ()=>{
  axios.post("/collection/delete",data.collection_id).then(res=>{
    if (res.code === 200) {
      ElMessage({
        message:'删除成功',
        duration:2000,
        type:'success',
      })
      // 成功后刷新页面
      data.showConfirmDelete=false;
      data.collection_id='';
      changePage(data.page);
    } else {
      ElMessage({
        message: "删除失败",
        duration: 2000,
        type: "error",
      })
    }
  })


}

const confirmDelete=(id)=>{
    data.collection_id = id
      data.showConfirmDelete=true;
}

const returnPage=()=>{
      data.showConfirmDelete = false;
      changePage(data.page);
}

const toEdit = (id) =>{


      axios.get("/collection/findByCollection_id",{params:{collection_id:id}}).then(res=>{

        console.log(res.data)
        editForm.user_id=res.data.user_id;
        editForm.collection_name=res.data.collection_name;
        editForm.collection_avatar=res.data.collection_avatar;
        editForm.collection_id = res.data.collection_id;
        editForm.img = getPublicImageUrl(res.data.collection_avatar);
      })



      data.showEditDialog=true
}

const edit = ()=>{
  console.log(editForm)
  axios.post("/collection/update",editForm).then(res=>{
    if (res.code === 200){
      ElMessage({
        type:"success",
        duration:2000,
        message:"修改成功"
      })
      // 成功后刷新页面
      editForm={
        collection_id: '',
        collection_name:'',
        collection_avatar:'',
        collection_count:'',
        user_id:"",
        img: "",
        oriFileName: '', //保存上传原始文件
      }
      data.showEditDialog=false;
      changePage(data.page);
    }else {
      ElMessage({
        type:"error",
        message:"修改失败",
        duration:2000,
      })

    }

  })
}

const searchCollection = () =>{

  axios.get("/collection/select",{params:{
      page: data.page,
      limit: data.limit,
      collection_name:data.search}}).then(res=>{
        data.tableData = res.data.records
        data.total = res.data.total
        data.page=res.data.current
        data.limit = res.data.size
  })

}

const addPoems =(id)=>{

  data.collection_id = id
    axios.get("/collection/getAllPoemsByCollection_id",{params:{
          collection_id:id
      }}).then(res=>{
        data.defaultCheckedPoems=res.data.records
      console.log("defaultCheckedPoems", data.defaultCheckedPoems)
    })

    data.showAddPoemsDialog=true;
    axios.get("/poem/size").then(res=>{
    max.value=res.data;
    load()
  })
}

let count = ref(5)
let max = ref(0)

const load = () => {

if (max.value>count.value){
  count.value += 2;
  axios.get("/poem/list",{params:{
        limit:count.value
    }}).then(res=>{
      data.addPoems=res.data.records.map(p => ({
        ...p,
        checked:data.defaultCheckedPoems==null?false:data.defaultCheckedPoems.some(item => item.poem_id === p.poem_id) // 检查poem.id是否在defaultCheckedPoems中
      }));
      console.log("data.addPoems",data.addPoems)
  })
}}

// const editPoems=()=>{
// // 确保 data.defaultCheckedPoems 是一个数组
//   data.defaultCheckedPoems = data.defaultCheckedPoems || [];

//   data.addPoems.forEach(poem => {
//     // 如果 defaultCheckedPoems 是空数组，或者 poem 被选中且不在 defaultCheckedPoems 中
//     if (data.defaultCheckedPoems.length === 0 || (poem.checked && !data.defaultCheckedPoems.includes(poem))) {
//       data.defaultCheckedPoems.push(poem);
//       console.log("defaultCheckedPoems:", data.defaultCheckedPoems);
//     }
//   });


//   axios.post("/collection/poemsUpdate",{
//       "collection_id":data.collection_id,
//       "poems":data.defaultCheckedPoems,
//     }).then(res=>{
//       if(res.code===200){
//         ElMessage({
//           type:'success',
//           duration:2000,
//           message:"操作成功",
//           onClose() {
//               data.collection_id = '';
//               data.defaultCheckedPoems =[];
//               data.addPoems=[];
//               count.value=5;
//               data.showAddPoemsDialog=false;
//               changePage(data.page);
//           },


//         })

//       }else {
//         ElMessage({
//           type:'error',
//           duration:2000,
//           message:"操作失败",

//         })

//       }

//   })

const editPoems = () => {
  // 收集当前勾选的诗词（去重）
  const selectedPoemsMap = new Map();

  data.addPoems.forEach(poem => {
    if (poem.checked) {
      // 使用 poem_id 作为 key 去重
      selectedPoemsMap.set(poem.poem_id, poem);
    }
  });

  // 将 Map 转成去重后的数组
  const selectedPoems = Array.from(selectedPoemsMap.values());

  axios.post("/collection/poemsUpdate", {
    collection_id: data.collection_id,
    poems: selectedPoems
  }).then(res => {
    if (res.code === 200) {
      ElMessage({
        type: 'success',
        duration: 2000,
        message: "操作成功",
        onClose() {
          data.collection_id = '';
          data.defaultCheckedPoems = [];
          data.addPoems = [];
          count.value = 5;
          data.showAddPoemsDialog = false;
          changePage(data.page);
        }
      });
    } else {
      ElMessage({
        type: 'error',
        duration: 2000,
        message: "操作失败",
      });
    }
  });
}

</script>

<template>
  <div style="text-align: center; margin: 20px;">
    <input  v-model="data.search" type="text" placeholder="在这里搜索诗集..." style="margin-right: 50px; padding: 10px; width: 300px; border: 2px solid #ccc; border-radius: 5px; font-family: 'Georgia', serif; font-size: 16px; transition: border-color 0.3s;">
    <el-button @click="searchCollection" style="margin-right: 0; padding: 10px; width: 80px; border: 2px solid #ccc; border-radius: 5px; font-family: 'Georgia', serif; font-size: 16px; transition: border-color 0.3s;" >搜索</el-button>
    <el-button style="margin-left: 160px" type="primary" @click="toAdd" >增加诗集</el-button>
  </div>
  <el-table ref="tableRef" :data="data.tableData"  border style="width: 90%; margin: 0 auto; ">

    <el-table-column label="序号" type="index" width="120" fixed="left" align="center" ></el-table-column>
    <el-table-column label="诗集id"  width="120" prop="collection_id" align="center">

    </el-table-column>
    <el-table-column width="180"  label="诗集图片" prop="collection_avatar" align="center">
      <template #default="scope">
      <el-image style="width: 120px;height: 100px" :src="getPublicImageUrl(scope.row.collection_avatar)"></el-image>
      </template>
    </el-table-column>
    <el-table-column width="120"   label="诗集名称" prop="collection_name" align="center"></el-table-column>
    <el-table-column width="120" label="诗集数量" prop="collection_count"  align="center">
<!--        <el-button type="text"  @click="" >查看</el-button>-->
    </el-table-column>

    <el-table-column label="操作" width="350" align="center">
      <template #default="a">
        <div style="display: flex; justify-content: space-around; align-items: center;">
          <el-button type="primary" @click="addPoems(a.row.collection_id)" >点击在诗集中加诗</el-button>
          <el-button type="success" @click="toEdit(a.row.collection_id)" >修改</el-button>
          <el-button type="danger"  @click="confirmDelete(a.row.collection_id)" >删除</el-button>
        </div>
      </template>
    </el-table-column>
  </el-table>

  <div class="pagination-container">
    <!--        分页插件-->
    <el-pagination
        background
        layout="total, prev, pager, next, jumper"
        :total="data.total"
        :page-size="data.limit"
        @current-page="data.page"
        @current-change="changePage">
    </el-pagination>
  </div>


  <el-dialog title="新增诗集" v-model="data.showAddDialog"  style="width: 400px;height:500px">
    <el-form style="width: 100%;" label-width="80">
      <el-form-item label="诗集名称">
        <el-input placeholder="请输入诗集名称" v-model="addForm.collection_name"></el-input>
      </el-form-item>

      <el-form-item label="选择作者">
        <el-select v-model="addForm.user_id" placeholder="Select" style="width: 240px">
          <el-option
              v-for="item in data.users"
              :key="item.user_id"
              :value="item.user_id"
              :label="item.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传封面">
        <el-upload
            class="avatar-uploader"
            :http-request="uploadURL"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :show-file-list="false"
        >
          <img v-if="addForm.img" :src="addForm.img" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item >
        <el-button @click="add">确认</el-button>
      </el-form-item>
      </el-form>
  </el-dialog>


  <el-dialog title="修改诗集" v-model="data.showEditDialog"  style="width: 400px;height:500px">
    <el-form style="width: 100%;" label-width="80">
      <el-form-item label="修改名称">
        <el-input placeholder="请输入诗集名称"  v-model="editForm.collection_name"></el-input>
      </el-form-item>

      <el-form-item label="修改作者">
        <el-select v-model="editForm.user_id" placeholder="Select" style="width: 240px">
          <el-option
              v-for="item in data.users"
              :key="item.user_id"
              :value="item.user_id"
              :label="item.nickname"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="更换封面">
        <el-upload
            class="avatar-uploader"
            :http-request="uploadURL1"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload1"
            :show-file-list="false"
        >
          <img v-if="editForm.img" :src="editForm.img" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>

      <el-form-item >
        <el-button @click="edit">确认</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog
      title="确认删除"
      v-model="data.showConfirmDelete"
      width="350px"
      center
      class="confirm-delete-dialog">
    <div class="dialog-content">
      <p class="dialog-message">您确定要删除此项吗？</p>
      <el-form-item style="text-align: center;margin-left: 20px; top: 200px">
        <el-button type="danger" @click="deleteC" class="dialog-button">确认删除</el-button>
        <el-button type="primary" @click="returnPage" class="dialog-button">取消</el-button>
      </el-form-item>
    </div>
  </el-dialog>

  <el-dialog
      title="添加诗词"
      v-model="data.showAddPoemsDialog"
      width="350px"
      center
      @close="data.defaultCheckedPoems=[];data.addPoems=[];count=5"
  >
    <div style="height: 300px; overflow-y: auto;">
      <ul
          class="infinite-list"
          v-infinite-scroll="load"
          infinite-scroll-delay="100"
          infinite-scroll-distance="100"
          style="padding: 0; margin: 0;">
        <li v-for="p in data.addPoems" :key="p.id" class="infinite-list-item">
          <el-checkbox v-model="p.checked" >{{ p.title }}</el-checkbox>
        </li>
      </ul>
    </div>
    <div style="display: flex; justify-content: center; padding: 10px;">


    <el-button  style="
  background-color: #409EFF; /* Element UI的primary颜色 */
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 20px; /* 更大的圆角 */
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.3s; /* 平滑的颜色过渡效果 */"
    @click="editPoems">确认</el-button>
    </div>
  </el-dialog>



</template>

<style scoped>



.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}


.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

/*确认删除框美化*/
.confirm-delete-dialog .dialog-content {
  text-align: center;
  padding: 20px;
  font-size: 16px;
  color: #333;
}

.dialog-message {
  margin-bottom: 20px;
  font-weight: 500;
  line-height: 1.5;
  color: #555;
}

.dialog-button {
  margin: 0 10px;
  min-width: 100px;
  border-radius: 4px;
}

.dialog-button.danger {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.dialog-button.primary {
  background-color: #409EFF;
  border-color: #409EFF;
}

/*无限滚动*/
.infinite-list {
  height: 300px;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}

</style>