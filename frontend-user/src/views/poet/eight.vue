<template>
    <div>
        <div class="title">唐宋八大家</div>
        <div class="container">
            <div class="box" v-for="poet in poets" :key="poet.name">
                <div class="img-box">
                    <img :src="getPublicImageUrl(poet.image)" :alt="poet.name">
                </div>
                <div class="text-box">
                    <div>
                        <h2>{{ poet.name }}</h2>
                        <p>{{ poet.description }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';


function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/author/${imageName}`;
}
const poets = ref([
    {
        name: '韩愈',
        image: 'hanyu.jpg',
        description: '韩愈，唐代文学家、思想家，位列“唐宋八大家”之首，以散文著称，提倡古文运动，代表作有《师说》、《马说》等。'
    },
    {
        name: '柳宗元',
        image: 'liuzongyuan.jpg',
        description: '柳宗元，唐代文学家、思想家，与韩愈并称为“韩柳”，散文成就卓著，代表作有《捕蛇者说》、《小石潭记》等。'
    },
    {
        name: '欧阳修',
        image: 'ouyangxiu.jpg',
        description: '欧阳修，北宋政治家、文学家，号醉翁，散文、诗词均有很高成就，代表作有《醉翁亭记》、《蝶恋花·庭院深深深几许》等。'
    },
    {
        name: '苏洵',
        image: 'suxun.jpg',
        description: '苏洵，北宋文学家，与其子苏轼、苏辙并称“三苏”，散文风格雄健，代表作有《六国论》等。'
    },
    {
        name: '苏轼',
        image: 'sushi.jpg',
        description: '苏轼，北宋文学家、书画家，号东坡居士，诗词、散文、书法均有很高成就，代表作有《赤壁赋》、《念奴娇·赤壁怀古》等。'
    },
    {
        name: '苏辙',
        image: 'suzhe.jpg',
        description: '苏辙，北宋文学家，苏洵之子，苏轼之弟，散文、诗词均有建树，代表作有《黄州快哉亭记》等。'
    },
    {
        name: '王安石',
        image: 'wanganshi.jpg',
        description: '王安石，北宋政治家、文学家、思想家，提出“新法”变法，散文、诗词风格独特，代表作有《泊船瓜洲》等。'
    },
    {
        name: '曾巩',
        image: 'zenggong.jpg',
        description: '曾巩，北宋文学家，散文成就显著，以平实、简练著称，代表作有《墨池记》、《越州鉴湖图序》等。'
    }

]);
</script>

<style scoped>
* {
    /* 初始化 */
    margin: 0;
    padding: 0;
    /* 元素的总宽度和高度包含内边距和边框 */
    box-sizing: border-box;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(50px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.title {
    width: 100%;
    text-align: center;
    font-size: 3em;
    color: #2c3e50;
    font-family: 'MyCustomFont', 'Helvetica Neue', Arial, sans-serif;
    line-height: 1.3;
    padding: 40px 0;
    text-shadow: 0 4px 8px rgba(0, 0, 0, 0.25);
    animation: fadeInUp 1.5s cubic-bezier(0.390, 0.575, 0.565, 1.000) forwards;
    border-bottom: 1px solid #e6e6e6;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s cubic-bezier(0.250, 0.460, 0.450, 0.940), box-shadow 0.3s ease;
    /* 改进过渡效果，增加阴影变化的过渡 */
}

.title:hover {
    transform: translateY(-10px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
    /* 悬停时增加阴影深度，使上移效果更明显 */
}

/* 响应式设计，针对小屏幕调整样式 */
@media (max-width: 768px) {
    .title {
        font-size: 2em;
        padding: 20px 0;
        text-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
        /* 小屏幕减少阴影效果，以保持清晰度 */
    }
}

.container {
    margin-top: 50px;
    width: 100%;
    position: relative;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    /* 允许换行 */
    align-items: stretch;
    /* 让子元素高度填满容器 */
    justify-content: center;
    /* 从行的开始处排列 */
}

.container .box {
    margin-bottom: 50px;
    position: relative;
    width: 21%;
    /* 每个盒子占据25%的宽度 */
    height: 275px;
    /* 你可以根据需要调整高度 */
    overflow: hidden;
    transition: 0.5s;

}

/* 你可以根据需要添加响应式设计，例如： */
@media (max-width: 768px) {
    .container .box {
        width: 50%;
        /* 在小屏幕上，每行显示两个盒子 */
    }
}

@media (max-width: 480px) {
    .container .box {
        width: 100%;
        /* 在更小的屏幕上，每行只显示一个盒子 */
    }
}

/* 鼠标移入，盒子变大 */
.container .box:hover {
    transform: scale(1.25);
    box-shadow: 0 25px 40px rgba(0, 0, 0, 0.5);
    z-index: 1;
}

.container .box .img-box {
    /* 绝对定位 */
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
}

/* 渐变遮罩（黑色——透明） 默认隐藏 */
.container .box .img-box::before {
    content: "";
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(to top, #222, transparent);
    z-index: 1;
    opacity: 0;
    transition: 0.5s;
}

/* 鼠标移入，渐变遮罩显示 */
.container .box:hover .img-box::before {
    opacity: 1;
}

.container .box .img-box img {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    /* 保持原有尺寸比例，裁切长边 */
    object-fit: cover;
}

.container .box .text-box {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    padding: 20px;
    /* 弹性布局 元素位于容器的结尾 */
    display: flex;
    align-items: flex-end;
    color: #fff;
    z-index: 2;
}

.container .box .text-box h2 {
    font-size: 20px;
    margin-bottom: 10px;
    /* 默认移出可视范围 */
    transform: translateY(200px);
    transition: 0.5s;
}

.container .box:hover .text-box h2 {
    /* 鼠标移入，移入可视范围 */
    transform: translateY(0);
    /* 设置动画延迟时间 */
    transition-delay: 0.3s;
}

.container .box .text-box p {
    font-size: 13px;
    line-height: 20px;
    /* 默认移出可视范围 */
    transform: translateY(200px);
    transition: 0.5s;
}

.container .box:hover .text-box p {
    /* 鼠标移入，移入可视范围 */
    transform: translateY(0);
    /* 设置动画延迟时间 */
    transition-delay: 0.4s;
}
</style>