<!--
css在其他文件夹
-->

<script setup>
import Headler from "@/views/headler.vue";
import { ref } from 'vue'
import axios from "axios";

// 当前激活的模块
const activeModule = ref('interpretation')

// 模块1：诗意解读
const poemText = ref('')
const interpretation = ref('')
const isInterpreting = ref(false)

// 模块2：AI作诗
const poemStyle = ref('五言绝句')
const poemTitle = ref('')  // 新增主题输入
const poemContent = ref('')

// 模块3：藏头诗生成
const acrosticInput = ref('')
const acrosticPoem = ref('')

// 模块4：诗词问答
const question = ref('')
const answer = ref('')

// 模块5：对对子
const coupletInput = ref('')
const coupletOutput = ref('')

// 模块6：AI朗读
const audioText = ref('')
const isPlaying = ref(false)
const audioPlayer = ref(null)

// 诗意解读方法
const interpretPoem = async () => {
  if (!poemText.value.trim()) {
    alert("诗词内容不能为空");
    return;
  }
  if (isInterpreting.value) return;

  isInterpreting.value = true;
  try {
    const response = await axios.post(
        '/ai/interpret',
        new URLSearchParams({ content: poemText.value }),
        {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        timeout: 60000
      }
    );
    console.log("后端返回内容：", response.data);  // 调试用
    interpretation.value = response
        || "未能获取有效解析内容";
  } catch (error) {
    interpretation.value = "请求失败：" + (error.response?.data?.error || error.message);
  } finally {
    isInterpreting.value = false;
  }
};

// AI作诗
// AI作诗方法 - 增加title参数
const generatePoem = async () => {
  if (!poemTitle.value.trim()) {
    alert("主题不能为空");
    return;
  }

  try {
    const params = new URLSearchParams();
    params.append('style', poemStyle.value);
    params.append('title', poemTitle.value);

    const response = await axios.post(
      '/ai/generate',
      params,
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
    poemContent.value = response;
  } catch (error) {
    poemContent.value = "生成失败，请重试";
  }
};

// 藏头诗生成
const generateAcrostic = async () => {
if (!acrosticInput.value.trim()) {
    alert("藏头文字不能为空");
    return;
  }
  if (acrosticInput.value.length < 4 || acrosticInput.value.length > 8) {
    alert("藏头文字长度应在4到8个字之间");
    return;
  }
  try {
    const response = await axios.post(
      '/ai/generate-acrostic',
      new URLSearchParams({ input: acrosticInput.value }),
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
    acrosticPoem.value = response;
  } catch (error) {
    acrosticPoem.value = "生成失败";
  }
};

// 诗词问答
const askQuestion = async () => {
  if (!question.value.trim()) {
    alert("问题不能为空");
    return;
  }

  try {
    const response = await axios.post(
      '/ai/poetry-qa',
      new URLSearchParams({ question: question.value }),
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
    answer.value = response;
  } catch (error) {
    answer.value = "获取答案失败：" + error.message;
  }
};

// 对对子生成
const generateCouplet = async () => {
  if (!coupletInput.value.trim()) {
    alert("上联不能为空");
    return;
  }
  if (coupletInput.value.length < 5 || coupletInput.value.length > 7) {
    alert("上联长度应在5到7个字之间");
    return;
  }

  if (!coupletInput.value) return;
  try {
    const response = await axios.post(
      '/ai/generate-couplet',
      new URLSearchParams({ topLine: coupletInput.value }),
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
    coupletOutput.value = response;
  } catch (error) {
    coupletOutput.value = "生成失败，请检查上联格式";
  }
};

// AI朗读
const toggleAudio = async () => {
  if (!audioText.value.trim()) {
    alert("朗读内容不能为空");
    return;
  }

  if (audioText.value.length > 100) {
    alert("朗读内容建议不超过100字");
    return;
  }

  if (isPlaying.value) {
    audioPlayer.value.pause();
    isPlaying.value = false;
    return;
  }

  try {
    const response = await axios.post('/ai/tts', {
      text: audioText.value
    }, { responseType: 'blob' });

    const audioBlob = new Blob([response.data], { type: 'audio/mpeg' });
    const audioUrl = URL.createObjectURL(audioBlob);
    audioPlayer.value = new Audio(audioUrl);
    audioPlayer.value.play();
    isPlaying.value = true;

    audioPlayer.value.onended = () => {
      isPlaying.value = false;
      URL.revokeObjectURL(audioUrl);
    };
  } catch (error) {
    console.error("语音合成失败:", error);
  }
};
</script>

<template>
  <Headler/>
  <div class="container">
    <!-- 左侧导航 -->
    <div class="sidebar">
      <div
          class="nav-item"
          :class="{ active: activeModule === 'interpretation' }"
          @click="activeModule = 'interpretation'"
      >
        🔍 诗意解读
      </div>
      <div
          class="nav-item"
          :class="{ active: activeModule === 'generate' }"
          @click="activeModule = 'generate'"
      >
        ✍️ AI作诗
      </div>
      <div
          class="nav-item"
          :class="{ active: activeModule === 'acrostic' }"
          @click="activeModule = 'acrostic'"
      >
        🖋️ 藏头诗
      </div>
      <div
          class="nav-item"
          :class="{ active: activeModule === 'qa' }"
          @click="activeModule = 'qa'"
      >
        ❓ 诗词问答
      </div>
      <div
          class="nav-item"
          :class="{ active: activeModule === 'couplet' }"
          @click="activeModule = 'couplet'"
      >
        🎴 对对子
      </div>
      <div
          class="nav-item"
          :class="{ active: activeModule === 'audio' }"
          @click="activeModule = 'audio'"
      >
        🎧 语音朗读
      </div>
    </div>

    <!-- 右侧内容区 -->
    <div class="content">
      <!-- 诗意解读模块 -->
      <section v-if="activeModule === 'interpretation'" class="module-card">
        <h2>🔍 AI诗意解读</h2>
        <textarea v-model="poemText" placeholder="输入待解析的诗词..." rows="4" />
        <button @click="interpretPoem" :disabled="isInterpreting">
          {{ isInterpreting ? '解析中...' : '开始解析' }}
        </button>
        <div v-if="interpretation" class="result-box">{{ interpretation }}</div>
      </section>

      <!-- AI作诗模块 -->
      <section v-if="activeModule === 'generate'" class="module-card">
        <h2>✍️ AI作诗</h2>
        <div class="style-selector">
          <select v-model="poemStyle">
            <option>五言绝句</option>
            <option>七言律诗</option>
            <option>宋词</option>
          </select>
          <input
              v-model="poemTitle"
              placeholder="输入主题"
              class="title-input"
          />
          <button @click="generatePoem">生成</button>
        </div>
        <div class="poem-output">{{ poemContent }}</div>
      </section>

      <!-- 藏头诗模块 -->
      <section v-if="activeModule === 'acrostic'" class="module-card">
        <h2>🖋️ 藏头诗生成</h2>
        <div class="input-group">
          <input v-model="acrosticInput" placeholder="输入藏头文字（4-8字）" />
          <button @click="generateAcrostic">生成</button>
        </div>
        <div class="poem-output">{{ acrosticPoem }}</div>
      </section>

      <!-- 诗词问答模块 -->
      <section v-if="activeModule === 'qa'" class="module-card">
        <h2>❓ 诗词智能问答</h2>
        <div class="qa-input-group">  <!-- 新增专用class -->
          <input
              v-model="question"
              placeholder="示例：'白日依山尽'出自哪首诗？"
              class="qa-input"
          />
          <button @click="askQuestion">提问</button>
        </div>
        <div class="answer-box" v-if="answer">
          <h3>AI解析：</h3>
          <div class="answer-content">{{ answer }}</div>
        </div>
      </section>

      <!-- 对对子模块 -->
      <section v-if="activeModule === 'couplet'" class="module-card">
        <h2>🎴 智能对对子</h2>
        <div class="couplet-group">
          <div class="input-line">
            <span class="label">上联：</span>
            <input v-model="coupletInput" placeholder="请输入上联（5-7字）" />
          </div>
          <button @click="generateCouplet">生成下联</button>
          <div class="output-line" v-if="coupletOutput">
            <span class="label">下联：</span>
            {{ coupletOutput }}
          </div>
        </div>
      </section>

      <!-- AI朗读模块 -->
      <section v-if="activeModule === 'audio'" class="module-card audio-module">
        <h2>🎧 智能语音朗读</h2>
        <div class="audio-controls">
          <textarea v-model="audioText" placeholder="输入要朗读的诗词（建议100字内）" rows="3" />
          <button @click="toggleAudio">
            {{ isPlaying ? '⏹️ 停止播放' : '▶️ 开始朗读' }}
          </button>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped src="@/styles/aiTest.css"></style>

<!--
css在其他文件夹
-->