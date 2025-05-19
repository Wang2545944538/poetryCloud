<script setup>
import Headler from "@/views/headler.vue";
import { ref, watch } from 'vue'
import axios from "axios";
import { ttsAxios } from "@/utils/axios.js"


// 当前激活的模块
const activeModule = ref(localStorage.getItem('activeModule') || 'interpretation')

// 从本地存储加载数据
const loadFromStorage = (key, defaultValue) => {
  const saved = localStorage.getItem(key);
  return saved !== null ? JSON.parse(saved) : defaultValue;
};

// 模块1：诗意解读
const poemText = ref(loadFromStorage('poemText', ''))
const interpretation = ref(loadFromStorage('interpretation', ''))
const isInterpreting = ref(false)

// 模块2：AI作诗
const poemStyle = ref(loadFromStorage('poemStyle', '五言绝句'))
const poemTitle = ref(loadFromStorage('poemTitle', ''))
const poemContent = ref(loadFromStorage('poemContent', ''))

// 模块3：藏头诗生成
const acrosticInput = ref(loadFromStorage('acrosticInput', ''))
const acrosticPoem = ref(loadFromStorage('acrosticPoem', ''))

// 模块4：诗词问答
const question = ref(loadFromStorage('question', ''))
const answer = ref(loadFromStorage('answer', ''))

// 模块5：对对子
const coupletInput = ref(loadFromStorage('coupletInput', ''))
const coupletOutput = ref(loadFromStorage('coupletOutput', ''))

// 模块6：AI朗读
const audioText = ref(loadFromStorage('audioText', ''))
const isPlaying = ref(false)
const isPaused = ref(false)
const hasAudio = ref(false)
const audioStatus = ref('准备就绪')
const audioDuration = ref(0)
const progress = ref(0)
const audioElement = ref(null)
let progressInterval = null

// 监听模块切换并保存
watch(activeModule, (newVal) => {
  localStorage.setItem('activeModule', newVal);
});

// 监听各输入字段变化并保存
watch(poemText, (newVal) => localStorage.setItem('poemText', JSON.stringify(newVal)));
watch(interpretation, (newVal) => localStorage.setItem('interpretation', JSON.stringify(newVal)));
watch(poemStyle, (newVal) => localStorage.setItem('poemStyle', JSON.stringify(newVal)));
watch(poemTitle, (newVal) => localStorage.setItem('poemTitle', JSON.stringify(newVal)));
watch(poemContent, (newVal) => localStorage.setItem('poemContent', JSON.stringify(newVal)));
watch(acrosticInput, (newVal) => localStorage.setItem('acrosticInput', JSON.stringify(newVal)));
watch(acrosticPoem, (newVal) => localStorage.setItem('acrosticPoem', JSON.stringify(newVal)));
watch(question, (newVal) => localStorage.setItem('question', JSON.stringify(newVal)));
watch(answer, (newVal) => localStorage.setItem('answer', JSON.stringify(newVal)));
watch(coupletInput, (newVal) => localStorage.setItem('coupletInput', JSON.stringify(newVal)));
watch(coupletOutput, (newVal) => localStorage.setItem('coupletOutput', JSON.stringify(newVal)));
watch(audioText, (newVal) => localStorage.setItem('audioText', JSON.stringify(newVal)));

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

// 发送TTS请求获取音频
const fetchAudio = async () => {
  if (!audioText.value.trim()) {
    audioStatus.value = '请输入要朗读的文本'
    return
  }

  audioStatus.value = '请求音频中...'
  hasAudio.value = false
  isPlaying.value = false
  isPaused.value = false

  try {
    const formData = new FormData()
    formData.append('text', audioText.value)

    const response = await ttsAxios.post('/api/tts', formData)
    console.log(response)

    audioDuration.value = response.data.duration
    audioStatus.value = `音频已就绪，时长: ${audioDuration.value.toFixed(2)}秒`

    // 处理Base64音频数据
    const audioBlob = base64ToBlob(response.data.audio, 'audio/wav')
    const audioUrl = URL.createObjectURL(audioBlob)

    if (!audioElement.value) {
      audioElement.value = new Audio(audioUrl)
      audioElement.value.onended = () => {
        stopAudio()
        audioStatus.value = '播放完成'
      }
    } else {
      // 释放之前的URL对象避免内存泄漏
      if (audioElement.value.src) {
        URL.revokeObjectURL(audioElement.value.src)
      }
      audioElement.value.src = audioUrl
    }

    hasAudio.value = true
    audioStatus.value = '准备播放'

  } catch (error) {
    console.error('TTS请求失败:', error)
    audioStatus.value = '音频请求失败'
    hasAudio.value = false
  }
}

// 播放/暂停控制
const togglePlayPause = () => {
  if (!hasAudio.value) return

  if (isPlaying.value) {
    pauseAudio()
  } else {
    playAudio()
  }
}

const playAudio = () => {
  if (!audioElement.value) return

  audioElement.value.play()
  isPlaying.value = true
  isPaused.value = false
  audioStatus.value = '播放中...'
  startProgressTracking()
}

const pauseAudio = () => {
  if (!audioElement.value) return

  audioElement.value.pause()
  isPlaying.value = false
  isPaused.value = true
  audioStatus.value = '已暂停'
  clearInterval(progressInterval)
}

const stopAudio = () => {
  if (audioElement.value) {
    audioElement.value.pause()
    audioElement.value.currentTime = 0
  }
  clearInterval(progressInterval)
  isPlaying.value = false
  isPaused.value = false
  progress.value = 0
}

// Base64转Blob的辅助函数
const base64ToBlob = (base64Data, contentType = '', sliceSize = 512) => {
  const byteCharacters = atob(base64Data)
  const byteArrays = []

  for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    const slice = byteCharacters.slice(offset, offset + sliceSize)

    const byteNumbers = new Array(slice.length)
    for (let i = 0; i < slice.length; i++) {
      byteNumbers[i] = slice.charCodeAt(i)
    }

    const byteArray = new Uint8Array(byteNumbers)
    byteArrays.push(byteArray)
  }

  return new Blob(byteArrays, { type: contentType })
}

const startProgressTracking = () => {
  clearInterval(progressInterval)
  progressInterval = setInterval(() => {
    if (audioElement.value) {
      progress.value = (audioElement.value.currentTime / audioDuration.value) * 100
    }
  }, 100)
}
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
              placeholder="输入主题(可选)"
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
        <div class="qa-input-group">
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
      <section v-if="activeModule === 'audio'" class="audio-module">
        <h2>🎧 智能语音朗读</h2>
        <div class="audio-controls">
          <textarea v-model="audioText" placeholder="输入要朗读的诗词（建议500字内）" rows="3" />
          
          <div class="button-group">
            <button
                @click="fetchAudio"
                :disabled="!audioText.trim()"
                class="fetch-button"
            >
              朗读
            </button>

            <button
                v-if="hasAudio"
                @click="togglePlayPause"
                class="play-button"
            >
              {{ isPlaying ? '⏸ 暂停' : '▶ 播放' }}
            </button>

            <button
                v-if="hasAudio && (isPlaying || isPaused)"
                @click="stopAudio"
                class="stop-button"
            >
              ⏹ 停止
            </button>
          </div>

          <div class="status">{{ audioStatus }}</div>

          <div v-if="hasAudio" class="progress-container">
            <div class="progress-bar" :style="{ width: `${progress}%` }"></div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped src="@/styles/aiTest.css"></style>
<style scoped>
/* 新增的加载中样式 */
.processing-message {
  margin-top: 15px;
  text-align: center;
  color: #666;
}

.loading-spinner {
  border: 3px solid #f3f3f3;
  border-top: 3px solid #3498db;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  animation: spin 1s linear infinite;
  margin: 10px auto;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.audio-controls {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-width: 500px;
  margin: 0 auto;
}

textarea {
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ddd;
  resize: vertical;
  min-height: 80px;
}

.button-group {
  display: flex;
  gap: 10px;
}

.fetch-button {
  padding: 10px 15px;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.fetch-button:hover {
  background-color: #0b7dda;
}

.play-button {
  padding: 10px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.play-button:hover {
  background-color: #45a049;
}

.stop-button {
  padding: 10px 15px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.stop-button:hover {
  background-color: #da190b;
}

.progress-container {
  width: 100%;
  height: 6px;
  background-color: #f0f0f0;
  border-radius: 3px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background-color: #4CAF50;
  transition: width 0.1s linear;
}

.status {
  text-align: center;
  color: #555;
  font-size: 14px;
}
</style>