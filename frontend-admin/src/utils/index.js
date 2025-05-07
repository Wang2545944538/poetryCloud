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
  browseHistory:'浏览历史记录',
  modelList:'模块管理',
  myWorks:'我的作品',
  poetList:'诗人列表',
  poetryCategory:'诗词分类',
  poetrySearch:'诗词搜索',
  roleList:'角色管理',
  userList:'用户管理',
  userRightsManagement:'用户权限',
  introduce:"首页",
  questionBank:'题库管理',
  poetAdd:'添加诗人',
  poetryPublish:'诗词审核',
  activity:'诗会活动',
  addActivity:'添加活动'
}