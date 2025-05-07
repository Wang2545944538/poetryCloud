import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            redirect: '/introduce'
        },
        {
            path: '/introduce',
            name: 'introduce',
            component: () => import( '../view/Introduce.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../Login.vue')
        },
        {
            path: '/browseHistory',
            name: 'browseHistory',
            component: () => import('../view/browseHistory.vue')
        },
        {
            path: '/modelList',
            name: 'modelList',
            component: () => import('../view/modelList.vue')
        },
        {
            path: '/myWorks',
            name: 'myWorks',
            component: () => import('../view/myWorks.vue')
        },
        {
            path: '/poetList',
            name: 'poetList',
            component: () => import('../view/poetList.vue')
        },
        {
            path: '/poetAdd',
            name: 'poetAdd',
            component: () => import('../view/poetAdd.vue')
        },
        {
            path: '/poetryInformation',
            name: 'poetryInformation',
            component: () => import('../view/poetryInformation.vue')
        },
        {
            path: '/poetrySearch',
            name: 'poetrySearch',
            component: () => import('../view/poetrySearch.vue')
        },{
            path: '/roleList',
            name: 'roleList',
            component: () => import('../view/roleList.vue')
        },{
            path: '/userList',
            name: 'userList',
            component: () => import('../view/userList.vue')
        },{
            path: '/userRight',
            name: 'userRight',
            component: () => import('../view/userRight.vue')
        }
        ,
        {
            path: '/poetryPublish',
            name: 'poetryPublish',
            component: () => import('../view/poetryPublish.vue')
        },
        {
            path: '/activity',
            name: 'activity',
            component: () => import('../view/Activity.vue')
        },{
            path: '/addActivity',
            name: 'addActivity',
            component: () => import('../view/AddActivity.vue')
        },{
            path: '/questionBank',
            name: 'questionBank',
            component: () => import('../view/QuestionBank.vue')
        },{
            path: '/addQuestion',
            name: 'addQuestion',
            component: () => import('../view/AddQuestion.vue')
        },

    ]
})

export default router