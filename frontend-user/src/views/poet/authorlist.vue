<template>
    <headler />
    <router-link to="/poet" class="back">
        <img src="../../images/icon/back.webp" style="width: 35px;" alt="">
        <p class="back_text" style="">返回</p>
    </router-link>


    <div class="author-table-container">
        <el-table :data="Authordata.Authorcollections" @row-click="handleRowClick"
            style="width: 100%;">
            <el-table-column label="信息">
                <template #header>
                    <el-row>
                        <el-col :span="12">画像</el-col>
                        <el-col :span="12">姓名</el-col>
                    </el-row>
                </template>
                <template #default="scope">
                    <el-row>
                        <el-col :span="12" class="author-image">
                            <el-image :src="getPublicImageUrl(scope.row.author_img)" fit="cover" />
                        </el-col>
                        <el-col :span="12">{{ scope.row.author_name }}</el-col>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script setup>
import headler from "../../views/headler.vue";
import axios from "@/utils/axios.js";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute,useRouter } from 'vue-router';
const router = useRouter();
const route = useRoute();

const Authordata = reactive({
    Authorcollections: [],
});
onMounted(async () => {
    const author_name = route.params.author_name; // 获取从上一个页面传递来的id
    getAuthorlist(author_name);

});
const getAuthorlist = (author_name) => {
    axios.get("/author/findByNameAuthor?author_name=" + author_name)
        .then(res => {
            Authordata.Authorcollections = res.records;
        })
}
const handleRowClick = (row) => {
  // row 参数就是当前点击的行数据
  const authorId = row.author_id;
  // 这里你可以使用 authorId 做任何事情，比如跳转页面或者显示详情
  console.log('Clicked author_id:', authorId);
  // 如果你想进行页面跳转，可以使用 Vue Router
  router.push({ name: 'authorDetail', params: { id: authorId } });
}

function getPublicImageUrl(imageName) {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}
</script>
<style scoped>
.back {
    margin-top: 10px;
    margin-right: 20px;
    display: flex;
    justify-content: right;
    align-items: center;
    text-decoration: none;
}

.back_text {
    color: #000000;
    text-decoration: none;
    padding-bottom: 5px;
}

.back_text:hover {
    color: #656464;
}

.author-table-container {
    margin: 20px;
    margin-top: 0;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.el-table-column {
    /* 移除宽度限制，使列自动分配宽度 */
    width: auto !important;
}

.el-table-custom {
    border-collapse: collapse;
    font-size: 16px;
}

.el-table-custom::before {
    height: 0;
}

.author-image {
    width: 100%;
    height: 100px;
    overflow: hidden;
    display: flex;
    justify-content: left;
    align-items: center;
}

.author-image .el-image {
    max-width: 180px;
    height: 100px;
    border-radius: 4px;
}

.el-table .el-table__cell {
    padding: 12px 0;
}

.el-table .el-table__header-wrapper th {
    background-color: #f5f7fa;
    color: #909399;
    font-weight: bold;
}

.el-table .el-table__row td {
    border-bottom: 1px solid #ebeef5;
}

.el-table .el-table__row:hover td {
    background-color: #f5f7fa;
}
</style>