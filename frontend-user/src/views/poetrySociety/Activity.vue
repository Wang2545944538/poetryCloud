<!-- Activity.vue -->
<template>

  <div class="activity"  @click="toActivityView">
    <h3>{{queryParams.active_content=activity.active_content }}</h3>
    <img :src="getPublicImageUrl(queryParams.active_img=activity.active_img)" alt="Activity Image" />
    <div>
      <small >{{ moment(queryParams.active_time=activity.active_time).format('YYYY-MM-DD') }}</small>
    </div>
  </div>

  <div style="display:none;">
    {{queryParams.active_id = activity.active_id}}
    {{queryParams.active_profile = activity.active_profile}}
    {{queryParams.active_type = activity.active_type}}
  </div>
</template>

<script setup>
import routes from "@/routes/index.js";
import moment from "moment/moment.js";
import {client} from "@/utils/alioss.js";

defineProps({
  activity: {
    type: Object,
    required: true
  }
});

const queryParams = {
  active_id:0,
  active_content:'',
  active_time:'',
  active_type:'',
  active_img: '',
  active_profile:''
};
const toActivityView=()=>{

    routes.push({
      path: '/activityView',
      query: queryParams
    });
  // routes.push({path:"/answer"})
}
const img =(url) =>{
  return `src/images/jieImg/${url}`
}

function getPublicImageUrl(imageName) {
  return `https://yunmo1.oss-cn-fuzhou.aliyuncs.com/jieImg/${imageName}`;
}

</script>

<style scoped>
.activity {
  display: flex;
  flex-direction: column;
  //align-items: center;
  margin-bottom: 20px;
  align-items: flex-start;

}
.activity img {
  width: 100%;
  height: auto;
}
.activity h3 {
  font-family: 'Arial', sans-serif; /* 更改字体 */
  font-size: 1.2em;
  color: #333;
  margin: 0;
  text-align: left  !important;
}
</style>