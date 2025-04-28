<script setup>
import axios from '@/utils/axios'
import { reactive, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {Delete, Edit} from "@element-plus/icons-vue";
import router from "@/routes/index.js";

const data = reactive({
  page: 1, // 当前页码
  limit: 8, // 每页数量
  total: 0, // 总记录数
  tableData: [], // 分页的表格数据
  showAddDialog: false, // 是否显示增加的对话框
  showEditDialog: false, // 是否显示编辑的对话框
})
const tableRef = ref()

// 初始化数据的函数
const init = () => {
  axios.get("/poem/selectMyNotPublishPoems", {
    params: {
      page: data.page,
      limit: data.limit,
    }
  }).then(res => {
    data.tableData = res.records
    data.total = res.total
  })
}

// 分页跳转函数, pg是跳转的页码
const changePage = (pg) => {
  data.page = pg
  init()
}

// 钩子函数，页面加载完后自动调用
onMounted(() => {
  init()
})

// 删除函数, poem_id是要删除的诗的ID
const del = (poem_id) => {
  axios.get(`/poem/deleteMyPoem`, { params: { poem_id } })
      .then(response => {
        if (response.data === 1) {
          ElMessage({
            showClose: true,
            message: "删除失败!",
            type: "success",
            duration: 1000,
            onClose: () => {
              init();
            }
          });
        } else {
          ElMessage({
            showClose: true,
            message: "删除成功!",
            type: "error",
            duration: 1000,
            onClose: () => {
              init();
            }
          });
        }
      })
      .catch(error => {
        ElMessage({
          showClose: true,
          message: "删除操作出错!",
          type: "error",
          duration: 1000
        });
        console.error("删除失败:", error);
      });
}

// 修改数据
const update = (scope) => {
  console.log(scope)
  router.push({
    path: '/updateMyWrite',
    query: {
      title: scope.row.title,
      content: scope.row.content,
      poem_id: scope.row.poem_id, // 添加诗歌ID
      status:'0',
      isself:'1',
    }
  })

}

const formatStatus = (row, column, cellValue, index) => {
  if (cellValue == 0) {
    return '未审核';
  } else if (cellValue == 2) {
    return '审核未通过';
  }
  // 如果有其他状态，也可以在这里添加
  return cellValue; // 默认返回原始值
};

</script>

<template>
  <el-table ref="tableRef" :data="data.tableData" border style="width: 90%; margin: 0 auto; ">
    <el-table-column label="序号" type="index" width="80" fixed="left" align="center"></el-table-column>
    <el-table-column label="标题" prop="title" align="center"></el-table-column>
    <el-table-column label="内容" prop="content" align="center"></el-table-column>
    <el-table-column
        label="诗词状态"
        prop="status"
        align="center"
        :formatter="formatStatus"
    ></el-table-column>
    <el-table-column label="操作" width="150">
      <template #default="scope">
        <el-button
            style="margin-top: 0; padding: 0;"
            class="icon-button"
            type="danger"
            :icon="Delete"
            circle
            @click="del(scope.row.poem_id)" />
        <el-button class="icon-button" type="info" :icon="Edit" circle @click="update(scope)" />
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