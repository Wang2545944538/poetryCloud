<template>
  <div class="add">

    <el-card class="add-container">
      <el-button type="primary" @click="goBack">返回</el-button><br>
      <el-form :model="state.activeFrom" :rules="state.rules" ref="goodRef" label-width="100px" class="activeFrom">
        <el-form-item label="活动标题" prop="active_content" >
          <el-input style="width: 300px" v-model="state.activeFrom.active_content" placeholder="请输入活动标题" ></el-input>
        </el-form-item>
        <el-form-item label="活动简介" prop="active_profile">
          <el-input type="text" style="width: 300px" v-model="state.activeFrom.active_profile" placeholder="请输入活动简介"></el-input>
        </el-form-item>

        <!-- 选项输入框 -->
        <el-form-item label="类型">
          <el-select v-model="state.activeFrom.active_type_id" placeholder="请选择活动类型" style="width: 300px" >
            <el-option
                v-for="typeList in state.typeList"
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
        <el-form-item>
          <el-button type="primary" @click="submitAdd()">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script setup>
import { reactive, ref, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
import axios from '../utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import {client} from "@/utils/alioss.js";


const { proxy } = getCurrentInstance()

const goodRef = ref(null)
const route = useRoute()
const router = useRouter()
const { id } = route.query


const state = reactive({
  activeFrom: {
    active_content: '',
    active_profile: '',
    active_img:'',
    active_type_id:''
  },
  typeList:{},
  content:'',
  rules: {
    active_content: [
      { required: true, message: '请输入活动标题', trigger: ['change'] }
    ],
    active_profile: [
      { required: true, message: '请输入活动内容', trigger: ['change'] }
    ],activityType: [
      { required: true, message: '选择活动类型', trigger: 'change' }
    ]
  },
})



const submitAdd = () => {
  goodRef.value.validate((valid) => {
    if (valid) {
      axios.post("activity/addActivity",state.activeFrom).then(res => {
        console.log(res)
        if (res.code == 200) {
          ElMessage({
            showClose:true,
            message:"添加成功",
            duration:2000,
            type:"success",
          })
          location.reload();
        }else {
          ElMessage({
            showClose:true,
            message:"添加失败",
            duration:2000,
            type:"error",
          })
        }
      })
    } else {
      // 表单验证失败，阻止表单提交
      ElMessage.error('请填写所有必填字段')
      return false
    }
  })
}

const goBack=()=>{
  router.back(); //
}
onMounted(()=>{
  axios.post("/activity_type/list").then(res=>{
    state.typeList=res
  })
})
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
  state.activeFrom.active_img=files;
  client().put(fileName,file.file).then((res)=>{//此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
      addForm.img=res.url
  })
}
</script>

<style scoped>
.add {
  display: flex;
}
.add-container {
  flex: 1;
  height: 100%;
}
.avatar-uploader {
  width: 100px;
  height: 100px;
  color: #ddd;
  font-size: 30px;
}
.avatar-uploader-icon {
  display: block;
  width: 100%;
  height: 100%;
  border: 1px solid #e9e9e9;
  padding: 32px 17px;
}
.avatar-uploader {
  width: 100px;
  height: 100px;
  color: #ddd;
  font-size: 30px;
}
</style>