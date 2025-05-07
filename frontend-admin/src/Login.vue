<template>
    <div id="app">
        <Particles  id="tsparticles" :particlesInit="particlesInit"  :options="options" />
        <div class="form-container">
            <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" class="login-form">
                <h2 class="form-title">云墨诗苑后台管理系统</h2>
                <el-form-item label="账号" prop="username">
                    <el-input v-model="form.username" placeholder="请输入账号" clearable></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm" :loading="loading">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>

</template>
<script setup>
import {onBeforeUnmount, ref} from 'vue';
import { loadSlim } from "tsparticles-slim"; // if you are going to use `loadSlim`, install the "tsparticles-slim" package too.
import { useRouter, useRoute } from 'vue-router';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';
import axios from "@/utils/axios.js";
import {md5} from "js-md5";
import {localSet} from "@/utils/index.js";
import router from "@/routes/index.js";



const formRef = ref(null);
const form = ref({
    username: '',
    password: ''
});
const loading = ref(false);
const rules = ref({
    username: [
        { required: true, message: '账号不能为空', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '密码不能为空', trigger: 'blur' }
    ]
});
//先直接跳
// const route = useRouter();
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;
      axios.post("/users/login", {
        username: form.value.username,
        password: md5(form.value.password)
      }).then(res => {
        console.log(res.data);
        if (res.code === 200) {
          localSet('token', res.data);
          const decoded = parseJwt(res.data);
          const sub = decoded.sub;
          const subObj = JSON.parse(sub.replace(/'/g, '"'));
          const avatar = subObj.avatar;
          const nickname = subObj.nickname;
          localSet('nickname',nickname)
          localSet('avatar', avatar);
          // 确保 useRouter 被正确导入并使用

          router.push({ path: '/' ,params:{user_id:subObj.user_id}});
        } else {
          ElMessage({
            showClose: true,
            message: "账号或密码错误",
            duration: 2000,
            type: "error",
          });
        }
      }).catch(error => {
        console.error('登录请求失败', error);
        ElMessage({
          showClose: true,
          message: "登录请求失败",
          duration: 2000,
          type: "error",
        });
      }).finally(() => {
        loading.value = false;
      });
    } else {
      ElMessage.error('请填写完整信息');
      return false;
    }
  });
};

//解析token
function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace('-', '+').replace('_', '/');
  var payload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
  return JSON.parse(payload);
}




const particlesInit = async engine => {
    //await loadFull(engine);
    await loadSlim(engine);
};
const handleLogin = () => {
    localStorage.setItem("token", "yhx");
}

// 粒子库 options 配置
const options = {
    background: {
        color: {
            value: 'white'
        }
    },
    fpsLimit: 120,
    interactivity: {
        events: {
            onClick: {
                enable: true,
                mode: 'push'
            },
            onHover: {
                enable: true,
                mode: 'repulse'
            },
            resize: true
        },
        modes: {
            bubble: {
                distance: 400,
                duration: 2,
                opacity: 0.8,
                size: 40
            },
            push: {
                quantity: 4
            },
            repulse: {
                distance: 200,
                duration: 0.4
            }
        }
    },
    particles: {
        color: {
            value: '#39c5bb'
        },
        links: {
            color: '#39c5bb',
            distance: 150,
            enable: true,
            opacity: 0.5,
            width: 1
        },
        collisions: {
            enable: true
        },
        move: {
            direction: 'none',
            enable: true,
            outModes: {
                default: 'bounce'
            },
            random: false,
            // 增加速度值，例如从 1 增加到 3
            speed: 3,
            straight: false
        },
        number: {
            density: {
                enable: true,
                area: 800
            },
            value: 80
        },
        opacity: {
            value: 0.5
        },
        shape: {
            type: 'circle'
        },
        size: {
            value: { min: 1, max: 5 }
        }
    },
    detectRetina: true
}
</script>

<style scoped>
.form-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: no-repeat center center fixed;
    background-size: cover;
    overflow: hidden;
}

.login-form {
    z-index: 999;
    width: 100%;
    max-width: 360px;
    margin: 0 auto;
    padding: 50px 60px;
    background-color: #39c5bc98;
    border-radius: 15px;
    box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
    backdrop-filter: blur(20px);
    position: relative;
    overflow: hidden;
    transition: transform 0.5s cubic-bezier(0.2, 0.8, 0.2, 1), box-shadow 0.5s cubic-bezier(0.2, 0.8, 0.2, 1);
}
.login-form .el-form-item{
margin-right: 60px;
}
.login-form::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image: linear-gradient(135deg, #6dd5ed, #2193b0);
    z-index: -1;
    opacity: 0.5;
    transition: opacity 0.5s ease;
}

.login-form:hover {
    transform: translateY(-15px);
    box-shadow: 0 30px 40px rgba(0, 0, 0, 0.3);
}

.login-form:hover::before {
    opacity: 0.7;
}

.form-title {
    color: #fff;
    font-size: 32px;
    font-weight: 700;
    text-align: center;
    margin-bottom: 40px;
    letter-spacing: 2px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.el-form-item {
    margin-bottom: 40px;
    position: relative;
}

.el-form-item__label {
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    padding: 0 4px;
    background-color: #fff;
    color: #666;
    font-size: 14px;
    transition: all 0.3s ease;
    pointer-events: none;
}

.el-input__inner {
    padding: 20px 10px 10px;
    border: none;
    border-radius: 0;
    border-bottom: 2px solid #dcdfe6;
    font-size: 16px;
    color: #666;
    background-color: transparent;
    transition: border-color 0.3s ease;
}

.el-input__inner:focus {
    border-color: #409eff;
    box-shadow: 0 1px 0 0 #409eff;
}

.el-input__inner:focus+.el-form-item__label,
.el-input__inner:not(:placeholder-shown)+.el-form-item__label {
    top: -10px;
    font-size: 12px;
    color: #409eff;
}

.el-button {
    width: 100%;
    padding: 15px 20px;
    /* 增加按钮的内边距 */
    font-size: 18px;
    /* 增大字体大小 */
    font-weight: bold;
    /* 加粗字体 */
    letter-spacing: 1.5px;
    /* 增加字母间距 */
    background-image: linear-gradient(135deg, #6dd5ed, #2193b0);
    border: none;
    transition: background-color 0.3s ease, transform 0.3s ease;
}

.el-button:hover {
    background-image: linear-gradient(135deg, #6dd5ed, #2193b0);
    transform: translateY(-3px);
}

.el-form-item__error {
    position: absolute;
    bottom: -20px;
    left: 0;
    font-size: 12px;
    color: #f56c6c;
}

/* Add some animation to the form items */
@keyframes slideIn {
    0% {
        transform: translateY(50px);
        opacity: 0;
    }

    100% {
        transform: translateY(0);
        opacity: 1;
    }
}

.el-form-item {
    animation: slideIn 0.5s ease forwards;
}

/* Delay the animation for each form item */
.el-form-item:nth-child(1) {
    animation-delay: 0.2s;
}

.el-form-item:nth-child(2) {
    animation-delay: 0.4s;
}

.el-form-item:nth-child(3) {
    animation-delay: 0.6s;
}

.el-form-item:nth-child(4) {
    animation-delay: 0.8s;
}

/* Add some custom styles for the input placeholder */
.el-input__inner::placeholder {
    color: #999;
    font-style: italic;
}

/* Add some hover effects for the form container */
.form-container:hover .login-form {
    transform: scale(1.02);
}

/* Media queries for responsive design */
@media (max-width: 768px) {
    .login-form {
        padding: 40px 20px;
    }

    .form-title {
        font-size: 28px;
    }

    .el-button {
        padding: 12px 18px;
        font-size: 16px;
    }
}

@media (max-width: 480px) {
    .login-form {
        padding: 30px 15px;
    }

    .form-title {
        font-size: 24px;
    }

    .el-button {
        padding: 10px 15px;
        font-size: 14px;
    }
}

/* 统一设置字体颜色 */
.login-form,
.login-form .el-form-item__label,
.login-form .el-input__inner,
.login-form .el-button,
.login-form .el-form-item__error {
    color: #ffffff;
    /* 设置字体颜色为深灰色，可以根据需要调整 */
}

/* 统一设置字体为系统默认字体 */
.login-form,
.login-form .el-form-item__label,
.login-form .el-input__inner,
.login-form .el-button,
.login-form .el-form-item__error {
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}
</style>
