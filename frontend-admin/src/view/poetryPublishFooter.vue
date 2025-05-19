<script setup>
import axios from '@/utils/axios'
import { reactive, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {Check,Close} from "@element-plus/icons-vue";

const data = reactive({
  page: 1, // 当前页码
  limit: 8, // 每页数量
  total: 0, // 总记录数
  tableData: [], // 分页的表格数据
})
const tableRef = ref()

// 分页跳转函数, pg是跳转的页码
const changePage = (pg) => {
  data.page = pg
  init()
}

// 钩子函数，页面加载完后自动调用
onMounted(() => {
  init()
})

const formatStatus = (row, column, cellValue, index) => {
  if (cellValue == 1) {
    return '审核通过';
  }else if (cellValue == 2) {
    return '审核未通过';
  } else if (cellValue == 3) {
    return '已删除';
  }
  // 如果有其他状态，也可以在这里添加
  return cellValue; // 默认返回原始值
};

// 初始化数据的函数
const init = () => {
  axios.get("/poem/adminSelectAllPoem", {
    params: {
      page: data.page,
      limit: data.limit,
    }
  }).then(res => {
    data.tableData = res.records
    data.total = res.total
  })
}

// 下架
const check = async (row) => {
  try {
    await axios.get(`/poem/adminUnShelfPoem?poem_id=${row.poem_id}`);
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

// 上架
const close = async (row) => {
  try {
    await axios.get(`/poem/adminShelfPoem?poem_id=${row.poem_id}`);
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
  <el-table ref="tableRef" :data="data.tableData" border style="width: 90%; margin: 0 auto; ">
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
          <el-button type="info"  @click="check(scope.row)">下架</el-button>
          <el-button type="primary"  @click="close(scope.row)">上架</el-button>
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