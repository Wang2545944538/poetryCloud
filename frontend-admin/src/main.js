import { createApp } from 'vue'
import router from './routes/'
// import './style.css'
import App from './App.vue'
//导入ElementPlus组件
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Particles from 'particles.vue3'
// import axios from 'axios'

//导入图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//设置中文地区
import locale from 'element-plus/es/locale/lang/zh-cn'
// import {localRemove} from "@/utils/index.js";

const app = createApp(App)
//必须在mount之前
app.use(ElementPlus, { locale });
app.use(router);
app.use(Particles);
// app.use(axios) 
//注册ElementPlus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
};

// 在应用挂载之前设置全局事件监听器
// app.config.globalProperties.$onBeforeUnload = function() {
//     // 清除localStorage中的token
//     localRemove('token'); 
// };


app.mount('#app')
