<template>
  <Headler />

  <div>
    <el-container>
      <el-aside width="200px">
<!--         活动类型选择器-->
        <el-select v-model="selectedType" placeholder="选择活动类型">
          <el-option  label="所有类型" value="all"></el-option>
          <el-option
              v-for="item in state.activity_type"
              :key="item.active_type_id"
              :label="item.activity_type"
              :value="item.active_type_id"
             ></el-option>
        </el-select>

      </el-aside>

      <el-container>
        <el-main>
          <div v-for="(activity, index) in filteredActivities" :key="index">
            <Activity :activity="activity" />
          </div>
          <div ref="sentinel" class="sentinel"></div>
        </el-main>
      </el-container>
      <el-aside width="200px"></el-aside>
    </el-container>
  </div>
</template>

<script setup>
import {ref, computed, onMounted, nextTick, reactive} from 'vue';
import Activity from './Activity.vue';
import Headler from "@/views/headler.vue";
import axios from "@/utils/axios.js";
import moment from "moment/moment.js";
//活动集合


const state = reactive({
  activities:[],
  activity_type:[],
})
const visibleActivities = ref(2);
const selectedType = ref('all'); // 默认选中“所有类型”

// 计算属性基于选择的类型过滤活动
const filteredActivities = computed(() => {
  if (selectedType.value === 'all'  ) {
    return state.activities.slice(0, visibleActivities.value);
  }
  return state.activities
      .filter(activity => activity.active_type_id == selectedType.value)
      .slice(0, visibleActivities.value);
});

const sentinel = ref(null);
const observer = new IntersectionObserver((entries) => {
  if (entries[0].isIntersecting) {
    visibleActivities.value += 2;
  }
}, {
  rootMargin: '0px',
  threshold: 1.0
});
const getActiveList=()=>{
  axios.post("/activity/list").then(res=>{
    state.activities=res;
  })

}
const getType=()=>{
  axios.post("/activity_type/list").then(res=>{
    state.activity_type=res;
    console.log(state.activity_type)
    console.log(state.activities)
  })
}
onMounted(() => {
  getActiveList();
  getType();
  nextTick(() => {
    observer.observe(sentinel.value);
  });

});
</script>

<style scoped>
.sentinel {
  height: 20px;
  /* 可以添加其他样式，比如背景色，以便在开发时更容易看到sentinel元素 */
  background-color: #eee;
}

/* 样式 */
.more-button {
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #4CAF50;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.more-button:hover {
  background-color: #45a049;
}
</style>
