<template>
  <div class="add">

    <el-card class="add-container">
      <el-button type="primary" @click="goBack">返回</el-button><br>
      <el-form :model="state.questionFrom" :rules="state.rules" ref="goodRef" label-width="100px" class="questionFrom">
        <el-form-item label="题目" prop="question" >
          <el-input style="width: 300px" v-model="state.questionFrom.question" placeholder="请输入题目" ></el-input>
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input type="text" style="width: 300px" v-model="state.questionFrom.answer" placeholder="请输入答案"></el-input>
        </el-form-item>

        <!-- 选项输入框 -->
        <el-form-item label="选项" v-for="(option, index) in state.questionFrom.options" :key="index" :prop="'options.' + index">
          <el-input style="width: 300px" v-model="state.questionFrom.options[index]"   placeholder="请输入选项内容"></el-input>
          <el-button type="primary" @click="addOption()">添加选项</el-button>
          <el-button @click.prevent="removeOption(index)" v-if="state.questionFrom.options.length > 1">删除</el-button>
        </el-form-item>
        <el-form-item>

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


const { proxy } = getCurrentInstance()

const goodRef = ref(null)
const route = useRoute()
const router = useRouter()
const { id } = route.query


const state = reactive({
  questionFrom: {
    question: '',
    answer: '',
    options: ['', ''] // 初始化两个选项
  },
  content:'',
  rules: {
    question: [
      { required: true, message: '请输入题目', trigger: ['change'] }
    ],
    answer: [
      { required: true, message: '请输入答案', trigger: ['change'] }
    ],options: [
      { required: true, message: '请至少填写一个选项', trigger: 'change' }
    ]
  },
})
// 添加选项
const addOption = () => {
  state.questionFrom.options.push('')
}

// 删除选项
const removeOption = (index) => {
  if (state.questionFrom.options.length > 1) {
    state.questionFrom.options.splice(index, 1)
  }
}

const submitAdd = () => {
  goodRef.value.validate((valid) => {
    if (valid) {

      const optionsObject = {}

      const letters = 'abcdefg';
      state.questionFrom.options.forEach((option, index) => {
        if (index < letters.length) {
          optionsObject[letters[index]] = option;
        }
      });


      // 创建一个新的表单数据对象
      const formData = {
        questionBank_id:'', // 假设 questionBank_id 是可选的
        question: state.questionFrom.question,
        answer: state.questionFrom.answer,
        options: optionsObject
      };

      axios.post("questionbank/addQuestion", formData).then(res => {
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
</style>