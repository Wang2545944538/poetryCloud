<template>
    <div class="g-container">
        <svg class="g-svg" width="400" height="160" xmlns="http://www.w3.org/2000/svg">
            <path id="svgpath" d="M 350 40 C 1200 1000, -550 1000, 350 1960" stroke="black" fill="transparent" />
        </svg>
        <div class="ball"></div>
        <div class="point point1"></div>
        <div class="point point2"></div>
        <div class="point point3"></div>
        <h2>该朝代著名诗人</h2>
        <p class="p1" v-if="author1">
            <span>姓名：{{ author1.author_name }}</span>
            <div>简介：{{ author1.author_intro }}</div>
        </p>
        <p class="p2" v-if="author2">
            <span>姓名：{{ author2.author_name }}</span>
            <div>简介：{{ author2.author_intro }}</div>
        </p>
        <p class="p3" v-if="author3">
            <span>姓名：{{ author3.author_name }}</span>
            <div>简介：{{ author3.author_intro }}</div>
        </p>
     
    </div>
</template>

<script setup>
import axios from "@/utils/axios.js";
import headler from "../../views/headler.vue";
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const router = useRouter();
const route = useRoute();

const Authordata = reactive({
    Authorcollections: [],
});
let author1 = ref(null);
let author2 = ref(null);
let author3 = ref(null);
onMounted(async () => {
    const dynasty_id = route.params.id; // 获取从上一个页面传递来的id
    getAuthor(dynasty_id);
});
const getAuthor = (dynasty_id) => {
    axios.get("/author/findByDynastyidAuthor?id=" + dynasty_id)
        .then(res => {
            Authordata.Authorcollections = res;
            author1.value = Authordata.Authorcollections[0];
            author2.value = Authordata.Authorcollections[1];
            author3.value = Authordata.Authorcollections[2];

        })
        .catch(error => {
            console.error("Error fetching authors:", error);
        });
}

</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Righteous&display=swap');

body,
html {
    width: 100%;
    height: 100%;
    display: flex;
    overflow-x: hidden;
    font-family: Righteous;
}

.g-svg {
    position: absolute;
    top: 0;
    left: 50%;
    width: 700px;
    height: 2000px;
    transform: translate(-50%, 0);
}

.g-container {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translate(-50%, 0);
    width: 700px;
    height: 2000px;

    &::before {
        content: "";
        position: absolute;
        inset: 0 -30vw;
        background: linear-gradient(#000000, #39c5bb);
    }
}

.ball {
    position: absolute;
    width: 40px;
    height: 40px;
    clip-path: polygon(0 0, 100% 50%, 0 100%);
    offset-path: path("M 350 40 C 1200 1000, -550 1000, 350 1960");
    background: linear-gradient(270deg, #00fc1d, #39c5bb);
    animation: move 1s linear;
    animation-timeline: scroll(root);
    z-index: 10;
}

#svgpath {
    stroke-dasharray: 2222, 2222;
    animation: lineMove 3s linear;
    animation-timeline: scroll(root);
    stroke-width: 2px;
    stroke: #ff5722;
}

.point {
    position: absolute;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #ff5722;
    opacity: 0;
    animation: show 1s linear forwards;
    animation-timeline: scroll(root);

    &::before,
    &::after {
        content: "";
        position: absolute;
        inset: 0;
        border-radius: 50%;
        background: rgba(255, 255, 255, .5);
        animation: circleScale 2s infinite ease-in;
        z-index: -1;
    }

    &::after {
        animation-delay: -.5s;
    }
}

.point1 {
    top: 510px;
    left: 570px;
    animation-range: 150px 200px;
}

.point2 {
    top: 910px;
    left: 370px;
    animation-range: 400px 450px;
}

.point3 {
    top: 1410px;
    left: 66px;
    animation-range: 650px 700px;
}

h2 {
    position: absolute;
    left: 0;
    right: 0;
    text-align: center;
    top: 100px;
    font-size: 42px;
    color: #fff;

}

p {
    position: absolute;
    padding: 16px;
    border: 4px dashed #fff;
    width: 50vw;
    color: #fff;
    font-size: 24px;
    text-align: justify;
    font-style: italic;
    opacity: 0;
    animation: textShow 1s linear forwards;
    animation-timeline: scroll(root);
}

.p1 {
    --x: -500px;
    top: 410px;
    left: 60px;
    width: 400px;
    animation-range: 150px 200px;
}

.p2 {
    --x: -150px;
    width: 600px;
    top: 840px;
    left: 300px;
    animation-range: 350px 450px;
}

.p2::before {
    content: "";
    float: left;
    width: 240px;
    height: 200px;
    shape-outside: polygon(80% 0, 90% 0, 0 90%, 0 80%);
}

.p3 {
    --x: 500px;
    top: 1310px;
    left: 190px;
    width: 450px;
    animation-range: 650px 700px;
}

// .p2 {
//     --scale: .2;
//     --x: 900px;
//     top: 900px;
//     animation: show 1s linear forwards;
//     animation-timeline: scroll(root);
//     animation-range: 350px 600px;
// }

@keyframes lineMove {
    0% {
        stroke-dashoffset: 2222;
    }

    100% {
        stroke-dashoffset: 0;
    }
}

@keyframes move {
    0% {
        offset-distance: 0%;
    }

    50% {
        transform: scale(2.5);
    }

    100% {
        offset-distance: 100%;
    }
}

@keyframes circleScale {

    80%,
    100% {
        transform: scale(4);
        opacity: .1;
    }
}

@keyframes show {
    0% {
        opacity: 0;
    }

    100% {
        opacity: 1;
    }
}

@keyframes textShow {
    0% {
        transform: translate(var(--x), 0);
        opacity: 0;
    }

    100% {
        transform: translate(0, 0);
        opacity: 1;
    }
}
</style>
