export function localGet (key) {
  const value = window.localStorage.getItem(key)
  try {
    return JSON.parse(window.localStorage.getItem(key))
  } catch (error) {
    return value
  }
}

export function localSet (key, value) {
  window.localStorage.setItem(key, JSON.stringify(value))
}

export function localRemove (key) {
  window.localStorage.removeItem(key)
}

// 判断内容是否含有表情字符，现有数据库不支持。
export function hasEmoji (str = '') {
  const reg = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/g;
  return str.match(reg) && str.match(reg).length
}

// 单张图片上传
export const uploadImgServer = 'http://127.0.0.1:8080/users/uploadImgs'
// 多张图片上传
export const uploadImgsServer = 'http://127.0.0.1:8080/users/uploadImgs'

//配置标题
export const pathMap = {
  login: '登录',
  home:'云墨诗苑',
  register:'注册',
  profile:'个人中心',
  poet:'作者',
  opus:'作品',
  answer:'答题',
  write:'创作',
  poetrySociety:'诗会',
  poems:'诗集',
  authorDetail:'诗人详情',
  activityView:'活动详细',
  addWrite:'我要创作',
  myWrite:'我的创作',
  solitaire:'接龙游戏',
  puzzle:'拼图',
  results:'结果',
  brailleIntoPoetry:'点词成诗',
  mineLike:'我喜欢',
  mineSheet:'我的收藏',
  aiTest:'AI创作',
}