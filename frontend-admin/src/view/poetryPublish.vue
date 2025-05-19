<script setup>
import axios from '@/utils/axios'
import { reactive, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {Check,Close} from "@element-plus/icons-vue";

const data = reactive({
  // 未审核
  unchecked: {
    page: 1,
    limit: 8,
    total: 0,
    list: []
  },
  // 已审核
  checked: {
    page: 1,
    limit: 8,
    total: 0,
    list: []
  },
})
const tableRef = ref()

const formatStatus = (row, column, cellValue, index) => {
  if (cellValue == 0) {
    return '未审核';
  }else if (cellValue == 1) {
    return '审核通过';
  } else if (cellValue == 2) {
    return '审核拒绝';
  }else if (cellValue == 3) {
    return '已删除';
  }
  // 如果有其他状态，也可以在这里添加
  return cellValue; // 默认返回原始值
};

const loadUnchecked = () => {
  axios.get("/poem/getUncheckedPoems", {
    params: {
      page: data.unchecked.page,
      limit: data.unchecked.limit,
    }
  }).then(res => {
    data.unchecked.list = res.records
    data.unchecked.total = res.total
  })
}

const loadChecked = () => {
  axios.get("/poem/getCheckedPoems", {
    params: {
      page: data.checked.page,
      limit: data.checked.limit,
    }
  }).then(res => {
    data.checked.list = res.records
    data.checked.total = res.total
  })
}

onMounted(() => {
  loadUnchecked()
  loadChecked()
})

const changeUncheckedPage = (pg) => {
  data.unchecked.page = pg
  loadUnchecked()
}

const changeCheckedPage = (pg) => {
  data.checked.page = pg
  loadChecked()
}


const check = async (row) => {
  try {
    await axios.get(`/poem/adminPassPoem?poem_id=${row.poem_id}`);
    ElMessage.success("审核成功");
    // 延迟1秒（1000毫秒）后刷新页面
    setTimeout(() => {
      location.reload(); // 刷新当前页面
    }, 1000);
  } catch (error) {
    ElMessage.error("审核过程中出现错误");
    console.error(error);
  }
}

const close = async (row) => {
  try {
    await axios.get(`/poem/adminRejectPoem?poem_id=${row.poem_id}`);
    ElMessage.success("审核成功");
    // 延迟1秒（1000毫秒）后刷新页面
    setTimeout(() => {
      location.reload(); // 刷新当前页面
    }, 1000);
  } catch (error) {
    ElMessage.error("审核过程中出现错误");
    console.error(error);
  }
}

</script>

<template>
  <h1 style="text-align: center; margin-top: 50px; margin-bottom: 50px;">未审核诗词</h1>
  <el-table ref="tableRef" :data="data.unchecked.list" border style="width: 90%; margin: 0 auto;" v-if="data.unchecked.list.length > 0">
    <el-table-column label="序号" type="index" width="80" fixed="left" align="center"></el-table-column>
    <el-table-column label="标题" prop="title" align="center"></el-table-column>
    <el-table-column label="作者" prop="nickname" align="center"></el-table-column>
    <el-table-column label="内容" prop="content" align="center"></el-table-column>
    <el-table-column
        label="诗词状态"
        prop="status"
        align="center"
        :formatter="formatStatus"
    ></el-table-column>
    <el-table-column label="操作" width="150" align="center">
      <template #default="scope">
        <div style="display: flex; justify-content: space-around; align-items: center;">
          <el-button type="success" :icon="Check" circle @click="check(scope.row)" title="审核通过" />
          <el-button type="danger" :icon="Close" circle @click="close(scope.row)" title="审核不通过" />
        </div>
      </template>
    </el-table-column>
  </el-table>
  <el-empty v-else description="暂无未审核诗词" style="margin-top: 50px;" />

  <div class="pagination-container">
    <!--        分页插件-->
    <el-pagination
        background
        layout="total, prev, pager, next, jumper"
        :total="data.unchecked.total"
        :page-size="data.unchecked.limit"
        @current-page="data.unchecked.page"
        @current-change="changeUncheckedPage">
    </el-pagination>
  </div>

  <h1 style="text-align: center; margin-top: 50px; margin-bottom: 50px;">已审核诗词</h1>
  <el-table
      :data="data.checked.list"
      border
      style="width: 90%; margin: 0 auto;"
      v-if="data.checked.list.length > 0"
  >
    <el-table-column label="序号" type="index" width="80" fixed="left" align="center"></el-table-column>
    <el-table-column label="标题" prop="title" align="center"></el-table-column>
    <el-table-column label="作者" prop="nickname" align="center"></el-table-column>
    <el-table-column label="内容" prop="content" align="center"></el-table-column>
    <el-table-column
        label="诗词状态"
        prop="status"
        align="center"
        :formatter="formatStatus"
    ></el-table-column>
  </el-table>
  <el-empty v-else description="暂无已审核诗词" style="margin-top: 50px;" />

  <div class="pagination-container">
    <!--        分页插件-->
    <el-pagination
        background
        layout="total, prev, pager, next, jumper"
        :total="data.checked.total"
        :page-size="data.checked.limit"
        @current-page="data.checked.page"
        @current-change="changeCheckedPage">
    </el-pagination>
  </div>


</template>

<style scoped>
/* 在全局样式或组件的样式中添加以下代码 */
.el-table th {
  text-align: center; /* 设置表头文字居中 */
}

.el-table {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

/* 分页容器样式 */
.pagination-container {
  display: flex;
  justify-content: center; /* 居中对齐 */
  margin-top: 20px; /* 分页与内容之间的间距 */
  margin-bottom: 50px; /* 分页与页面底部之间的间距 */
}

</style>