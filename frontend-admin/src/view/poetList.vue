
<template>
    <el-card>
        <el-page-header style="margin-bottom: 20px;" content="诗人列表" icon="" title="诗人管理" />
        <el-table :data="Authordata.Authorcollections" style="width: 100%;">
            <el-table-column label="信息">
                <template #header>
                    <el-row>
                        <el-col :span="4">id</el-col>
                        <el-col :span="5">画像</el-col>
                        <el-col :span="3">姓名</el-col>
                        <el-col :span="3">性别</el-col>
                        <el-col :span="3">朝代</el-col>
                        <el-col :span="6">操作</el-col>
                    </el-row>
                </template>
                <template #default="scope">
                    <el-row style="display: flex; align-items: center;">
                        <el-col :span="4">{{ scope.row.author_id }}</el-col>
                        <el-col :span="5" class="author-image">
                            <el-image style="width: 100px;" :src="getPublicImageUrl(scope.row.author_img)"
                                fit="cover" />
                        </el-col>
                        <el-col :span="3">{{ scope.row.author_name }}</el-col>
                        <el-col :span="3">{{ scope.row.author_gender }}</el-col>
                        <el-col :span="3">
                            <div v-for="(option, key) in scope.row.dynasty" :key="key">
                                <p v-if="key !== 'dynasty_id'">{{ option }}</p>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <el-button @click="editAuthor(scope.row.author_id)" type="primary" :icon="Edit" circle />

                            <el-popconfirm title="您确定删除吗?" confirmButtonText="确定" cancelButtonText="取消"
                                @confirm="AuthorDelete(scope.row.author_id)">
                                <template #reference>
                                    <el-button circle :icon="Delete" type="danger"></el-button>
                                </template>
                            </el-popconfirm>
                        </el-col>
                    </el-row>

                </template>

            </el-table-column>

        </el-table>
        <div class="pagination-container">
            <el-pagination background layout="total, prev, pager, next, jumper" :total="data.total"
                :page-size="data.limit" :current-page="data.page" @current-change="changePage" />
        </div>
    </el-card>

    <el-dialog title="修改诗人信息" v-model="data.showEditAuthorDialog" style="width: 50%;height: 550px;">
        <el-form :model="currentAuthor" label-width="80px" style="width: 100%;">
            <el-form-item label="姓名">
                <el-input v-model="currentAuthor.author_name"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-radio-group v-model="currentAuthor.author_gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item style=" width: 50%;" label="画像" prop="author_img">
                <el-upload :http-request="handleUpload" :before-upload="handleBeforeUpload" :limit="1"
                    :show-file-list="false">
                    <el-image v-if="currentAuthor.author_img_url" :src="currentAuthor.author_img_url" fit="cover"
                        style="width: 110px; height: 110px;"></el-image>
                    <div v-else class="upload-icon"
                        style="width: 110px; height: 110px; line-height: 110px; text-align: center; border: 1px dashed #c0c4cc; border-radius: 6px;">
                        +
                    </div>
                </el-upload>
            </el-form-item>
            <!-- 其他需要编辑的字段... -->
            <el-form-item label="朝代" prop="dynasty_id">
                <el-select v-model="currentAuthor.dynasty_id" class="m-2" placeholder="Select">
                    <el-option v-for="item in dynastyoptions" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>


            <el-form-item label="简介">
                <el-input v-model="currentAuthor.author_intro" :rows="2" type="textarea" placeholder="输入诗人简介" />
            </el-form-item>

            <el-form-item label="流派">
                <el-input v-model="currentAuthor.author_style"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="data.showEditAuthorDialog = false">取消</el-button>
                <el-button type="primary" @click="submitEditAuthor">提交</el-button>
            </span>
        </template>
    </el-dialog>

</template>

<script setup>
import {Delete,Edit} from '@element-plus/icons-vue'
import axios from "@/utils/axios.js";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { ElMessage } from "element-plus";
import { client } from "@/utils/alioss.js";
//诗人列表
const Authordata = reactive({
    Authorcollections: [],
});
onMounted(async () => {
    fetchData();
});
const dynastyoptions = [
    { label: "先秦", value: 0 },
    { label: "秦朝", value: 1 },
    { label: "汉朝", value: 2 },
    { label: "三国", value: 3 },
    { label: "晋朝", value: 4 },
    { label: "南北朝", value: 5 },
    { label: "隋朝", value: 6 },
    { label: "唐朝", value: 7 },
    { label: "五代十国", value: 8 },
    { label: "宋朝", value: 9 },
    { label: "元朝", value: 10 },
    { label: "明朝", value: 11 },
    { label: "清朝", value: 12 }
];


// 当前正在编辑的诗人信息
let currentAuthor = reactive({
    author_name: '',
    author_gender: '男',
    dynasty_id: 0,
    author_intro: '',
    author_img: '',//用于存储图片名
    author_img_url: '',//用于存储完整的图片URL
    author_style: ''
});

// 存储诗人数据的响应式引用
// const authors = ref([]); 
const data = reactive({
  total: 0, // 总记录数
  page: 1, // 当前页码
  limit: 4, // 每页显示的记录数
  showEditAuthorDialog:false,
});

const editAuthor =(author_id)=>{
    data.showEditAuthorDialog = true;
    axios.get("/author/byIdAuthor?author_id="+author_id)
        .then(res=>{
            if (res.code === 200) {
                for (const key in currentAuthor) {
                    delete currentAuthor[key]; // 清除现有属性
                }
                Object.assign(currentAuthor, res.data); // 使用 Object.assign 来合并属性
                // 确保更新 author_img_url
                currentAuthor.author_img_url = getPublicImageUrl(currentAuthor.author_img);
            }
            console.log(currentAuthor);
        })
        .catch(error => {
            console.error('Error fetching author:', error);
        });
}

//修改诗人
const submitEditAuthor=()=>{
    axios.post("/author/submitEditAuthor",currentAuthor)
        .then(res=>{
            if (res.code === 200) {
                ElMessage({
                    message: '修改诗人成功',
                    type: 'success',
                });
        location.reload();
            } 
            else {
                ElMessage({
                    message: "修改诗人失败",
                    duration: 2000,
                    type: "error",
                });
            }
        })
        .catch(error => {
            console.error('Error adding author:', error);
        });
}
//删除诗人
const AuthorDelete = (author_id) => {
    axios.get("/author/deleteAuthor?author_id="+author_id)
        .then(res => {
            if (res.code === 200) {
                ElMessage({
                    message: '删除诗人成功',
                    type: 'success',
                });
                const index = Authordata.Authorcollections.findIndex(author => author.author_id === author_id);
                if (index !== -1) {
                    Authordata.Authorcollections.splice(index, 1);
                }
            } 
            else {
                ElMessage({
                    message: "删除诗人失败",
                    duration: 2000,
                    type: "error",
                });
            }
        })
        .catch(error => {
            console.error('Error adding author:', error);
        });
}

// 获取数据的方法
const fetchData = () => {
  axios.get(`/author/findByDynastyAuthor`, {
    params: {
      page: data.page,
      limit: data.limit,
    }
  })
  .then(res => {
    Authordata.Authorcollections = res.records; // 更新诗人列表
    // console.log(Authordata.Authorcollections);
    data.total = res.total; // 更新总记录数
  })
  .catch(error => {
    console.error('There was an error!', error);
  });
};

// 页码改变的事件处理方法
const changePage = (newPage) => {
  data.page = newPage;
  fetchData();
};
//查找图片
function getPublicImageUrl(imageName) {
    return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}


const addForm = reactive({
    img: null,
    title: '',
    oriFileName: null,
});

const handleBeforeUpload = (file) => {
    addForm.oriFileName = file.name;
    const isJPEG = file.name.endsWith('.jpeg');
    const isJPG = file.name.endsWith('.jpg');
    const isPNG = file.name.endsWith('.png');
    const isWEBP = file.name.endsWith('.webp');
    const isGIF = file.name.endsWith('.gif');
    const isLt500K = file.size / 1024 / 1024 < 5;
    if (!(isJPEG || isJPG || isPNG || isWEBP || isGIF)) {
        ElMessage({
            message: '上传图片只能是 JPEG/JPG/PNG/WEBP/GIF 格式!',
            type: 'warning',
        });
        return false;
    }
    if (!isLt500K) {
        ElMessage({
            message: '单张图片大小不能超过 5mb!',
            type: 'warning',
        });
        return false;
    }
    return true;
};

const handleUpload = async (file) => {
    
    // if (!handleBeforeUpload(file.file)) {
    //     return;
    // }
    try {
        // let files = `${Date.parse(new Date())}` + addForm.oriFileName;
        let files = addForm.oriFileName;
        let fileName = 'author/' + files;
        const result = await client().put(fileName, file.file);
        const imageUrl = `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${files}`;
        currentAuthor.author_img = addForm.oriFileName;
        currentAuthor.author_img_url = imageUrl;

        ElMessage({
            message: '上传成功',
            type: 'success',
        });
    } catch (error) {
        console.error('Upload error:', error);
        ElMessage({
            message: "上传失败",
            duration: 2000,
            type: "error",
        });
    }
};
</script>

<style scoped>
.el-col {
    height: 100%;
}
</style>