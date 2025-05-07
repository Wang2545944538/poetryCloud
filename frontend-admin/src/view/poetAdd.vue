<template>
    <el-card>
        <el-page-header content="添加诗人" icon="" title="诗人管理" />
        <el-form ref="poetRef" :model="poetfrom" :rules="poetrules" label-width="80px" class="poetAddFrom">
            <el-form-item style=" width: 50%;" label="姓名" prop="author_name">
                <el-input v-model="poetfrom.author_name" placeholder="输入诗人姓名" />
            </el-form-item>
            <el-form-item style="width: 50%;" label="性别" prop="author_gender">
                <el-select v-model="poetfrom.author_gender" class="m-2" placeholder="Select">
                    <el-option v-for="item in genderoptions" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item style="width: 50%;" label="朝代" prop="dynasty_id">
                <el-select v-model="poetfrom.dynasty_id" class="m-2" placeholder="Select">
                    <el-option v-for="item in dynastyoptions" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item style=" width: 50%;" label="简介" prop="author_name">
                <el-input v-model="poetfrom.author_intro" :rows="2" type="textarea" placeholder="输入诗人简介" />
            </el-form-item>

            <el-form-item style=" width: 50%;" label="画像" prop="author_img">
                <el-upload :http-request="handleUpload" :before-upload="handleBeforeUpload" :limit="1"
                    :show-file-list="false">
                    <el-image v-if="poetfrom.author_img_url" :src="poetfrom.author_img_url" fit="cover"
                        style="width: 110px; height: 110px;"></el-image>
                    <div v-else class="upload-icon"
                        style="width: 110px; height: 110px; line-height: 110px; text-align: center; border: 1px dashed #c0c4cc; border-radius: 6px;">
                        +
                    </div>
                </el-upload>
            </el-form-item>

            <el-form-item style=" width: 50%;" label="流派" prop="author_style">
                <el-input v-model="poetfrom.author_style" placeholder="输入诗人流派" />
            </el-form-item>
            <el-form-item>
                <el-button style=" width: 200px;" type="primary" @click="submitForm()">添加诗人</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import axios from "@/utils/axios.js";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from "element-plus";
import { client } from "@/utils/alioss.js";

const router = useRouter();

const poetRef = ref(null);
const poetfrom = reactive({
    author_name: '',
    author_gender: '男',
    dynasty_id: 0,
    author_intro: '',
    author_img: '',//用于存储图片名
    author_img_url: '',//用于存储完整的图片URL
    author_style: ''
});

const genderoptions = [
    { label: "男", value: "男" },
    { label: "女", value: "女" }
];

const dynastyoptions = ref([]);

onMounted(async () => {
    getdynasty();
});

const getdynasty = () => {
    axios.get("/dynasty/dynastyList")
        .then(res => {
            if (res.code === 200 && Array.isArray(res.data)) {
                dynastyoptions.value = res.data.map(dynasty => ({
                    label: dynasty.dynasty_name,
                    value: dynasty.dynasty_id
                }));
            }
        })
        .catch(error => {
            console.error('Error fetching dynasty list:', error);
        });
}

const poetrules = reactive({
    author_name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
    author_gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
    dynasty_id: [{ required: true, message: "请选择朝代", trigger: "blur" }],
    author_intro: [{ required: true, message: "请输入详情", trigger: "blur" }],
    author_img: [{ required: true, message: "请上传画像", trigger: "blur" }],
    author_style: [{ required: true, message: "请输入派别", trigger: "blur" }],
});

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
    if (!handleBeforeUpload(file.file)) {
        return;
    }
    try {
        // let files = `${Date.parse(new Date())}` + addForm.oriFileName;
        let files = addForm.oriFileName;
        let fileName = 'author/' + files;
        const result = await client().put(fileName, file.file);
        const imageUrl = `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${files}`;
        poetfrom.author_img = addForm.oriFileName;
        poetfrom.author_img_url = imageUrl;

        ElMessage({
            message: '上传成功',
            type: 'success',
        });
    } catch (error) {
        ElMessage({
            message: "上传失败",
            duration: 2000,
            type: "error",
        });
    }
};

const submitForm = () => {
    poetRef.value.validate((valid) => {
        if (valid) {
            if (!poetfrom.author_img) {
                ElMessage({
                    message: '请先上传画像',
                    type: 'warning',
                });
                return;
            }
            axios.post("/author/addAuthor", poetfrom)
                .then(res => {
                    if (res.code === 200) {
                        ElMessage({
                            message: '添加诗人成功',
                            type: 'success',
                        });
                        // 跳转页面
                        router.push({ path: '/poetList' });
                    } else {
                        ElMessage({
                            message: "添加诗人失败",
                            duration: 2000,
                            type: "error",
                        });
                    }
                })
                .catch(error => {
                    console.error('Error adding author:', error);
                });
        } else {
            ElMessage({
                message: '表单数据有误，请检查',
                type: 'error',
            });
        }
    });
};


</script>

<style scoped>
.poetAddFrom {
    margin-top: 30px;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;

}

.el-form-item {
    margin-top: 18px
}
</style>