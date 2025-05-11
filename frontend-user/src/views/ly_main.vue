<template>
    <div class="box">
        <router-link class="mbox" to="/poetrySociety">
            <div class="minbox">
                <div class="text">
                    <div class="title">诗会</div>
                    <div class="content">穿越千年，诗意对决</div>
                </div>
                <img src="/user/images/icon/诗歌-copy.png"/>
            </div>
        </router-link>
        <router-link class="mbox" to="/poet">
            <div class="minbox">
                <div class="text">
                    <div class="title">作者</div>
                    <div class="content">探寻诗人足迹</div>
                </div>
                <img src="/user/images/icon/main2.png"/>
            </div>
        </router-link>
        <router-link class="mbox" to="/opus">
            <div class="minbox">
                <div class="text">
                    <div class="title">作品</div>
                    <div class="content">诗海拾遗珠</div>
                </div>
                <img src="/user/images/icon/main3.png"/>
            </div>
        </router-link>
        <router-link class="mbox" to="/write">
            <div class="minbox">
                <div class="text">
                    <div class="title">创作</div>
                    <div class="content">名句藏芳华</div>
                </div>
                <img src="/user/images/icon/诗词.png"/>
            </div>
        </router-link>
        <router-link class="mbox" to="/poems">
            <div class="minbox">
                <div class="text">
                    <div class="title">诗集</div>
                    <div class="content">品诗韵之魂</div>
                </div>
                <img src="/user/images/icon/main5.png"/>
            </div>
        </router-link>
    </div>

</template>
<style scoped>
.box {
    display: flex;
    flex-direction: row;
    width: 100%;
    margin-top: 20px;
    justify-content: space-around;
    align-items: center;
    /* 垂直居中 */
}
.text{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: flex-start;
}
.mbox {

    text-decoration: none;
    /* 移除下划线 */
    color: inherit;
    /* 继承父元素的字体颜色 */
    display: block;
    /* 使a标签填充整个smallbox */
    text-align: center;
    /* 文本居中 */
    padding: 10px;
    /* 添加内边距 */
    border: 1px solid #ddd;
    /* 添加边框 */
    border-radius: 5px;
    /* 圆角边框 */
    transition: transform 0.3s ease;
    /* 平滑的过渡效果 */
}

.minbox {
    width: 200px;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    /* justify-content: space-between;
    align-items: flex-start; */
}
.minbox img{
    width: 55px;
    height: 55px;
}
.mbox:hover {
    transform: translateY(-5px);
    /* 鼠标悬停时向上移动 */
}


.title {
    font-size: 18px;
    /* 增大标题字体大小 */
    font-weight: bold;
    /* 加粗标题 */
    margin-bottom: 5px;
    /* 标题与内容之间的间距 */
}

.content {
    font-size: 14px;
    /* 内容字体大小 */
    color: #666;
    /* 内容颜色 */
}
</style>