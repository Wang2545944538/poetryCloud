import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('../views/home.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/login/Login.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/login/Register.vue')
        },
        {
            path: '/poet',
            name: 'poet',
            component: () => import('../views/poet/poet.vue')
        },
        {
            path: '/profile',
            name: 'profile',
            component: () => import('../views/login/Profile.vue'), children:
                [
                    // {
                    //     path: '/profile/like',
                    //     name: 'mineLike',
                    //     component: () => import('../views/login/Like.vue')
                    // },
                    {
                        path: '/profile/sheet',
                        name: 'mineSheet',
                        component: () => import('../views/login/Sheet.vue')
                    },
                    // {
                    //     path: '/profile/recent',
                    //     name: 'mineRecent',
                    //     component: () => import('../views/login/Recent.vue')
                    // },
                    {
                        path: '/profile/MyWrite',
                        name: 'MyWrite',
                        component: () => import('../views/login/MyWrite.vue')
                    }
                ]

        },
        {
            path: '/opus',
            name: 'opus',
            component: () => import('../views/opus/opus.vue')
        },
        {
            path: '/poem/:id',
            name: 'poemDetail',
            component: () => import('../views/opus/details.vue') // 假设PoemDetail.vue是详情页面的组件
        },
        {
            path: '/poetrySociety',
            name: 'poetrySociety',
            component: () => import('../views/poetrySociety/PoetrySociety.vue')
        }, {
            path: '/answer',
            name: 'answer',
            component: () => import('../views/poetrySociety/Answer.vue')
        }, {
            path: '/results',
            name: 'results',
            component: () => import('../views/poetrySociety/Results.vue')
        }, {
            path: '/brailleIntoPoetry',
            name: 'brailleIntoPoetry',
            component: () => import('../views/poetrySociety/BrailleIntoPoetry.vue')
        },{
            path: '/activityView',
            name: 'activityView',
            component: () => import('../views/poetrySociety/ActivityView.vue')
        }, {
            path: '/poems',
            name: 'poems',
            component: () => import('../views/poems/Poems.vue')
        }, {

            path: '/lookCollection/:collection_id',
            name: 'LookCollection',
            component: () => import('../views/poems/lookCollection.vue')
        },{
        path: '/showCollection/:collection_name',
            name: 'showCollection',
            component: () => import('../views/poems/showCollection.vue')
        },

        {
            path: '/poethistory/:id',
            name: 'poethistory',
            component: () => import('../views/poet/poethistory.vue')
        },
        {
            path: '/authorlist/:author_name',
            name: 'authorlist',
            component: () => import('../views/poet/authorlist.vue')
        },
        {
            path: '/authorDetail/:id',
            name: 'authorDetail',
            component: () => import('../views/poet/authorDetail.vue')
        },

        {
            path: '/write',
            name: 'write',
            component: () => import('../views/write/write.vue')
        },
        {
            path: '/addWrite',
            name: 'addWrite',
            component: () => import('../views/write/addWrite.vue')
        },
        {
            path: '/myWrite',
            name: 'myWrite',
            component: () => import('../views/write/myWrite.vue')
        },
        {
            path: '/updateMyWrite',
            name: 'updateMyWrite',
            component: () => import('../views/write/updateMyWrite.vue')
        },

        {
            path: '/write',
            name: 'write',
            component: () => import('../views/write/write.vue')
        },  {
            path: '/puzzle',
            name: 'puzzle',
            component: () => import('../views/poetrySociety/Puzzle.vue')
        },
        {
            path: '/addWrite',
            name: 'addWrite',
            component: () => import('../views/write/addWrite.vue')
        },
        {
            path: '/myWrite',
            name: 'myWrite',
            component: () => import('../views/write/myWrite.vue')
        },
        {
            path: '/updateMyWrite',
            name: 'updateMyWrite',
            component: () => import('../views/write/updateMyWrite.vue')
        },
        {
            path: '/myWriteBody',
            name: 'myWriteBody',
            component: () => import('../views/write/myWriteBody.vue')
        },
        {
            path: '/myWriteFooter',
            name: 'myWriteFooter',
            component: () => import('../views/write/myWriteFooter.vue')
        },
    ]
})

export default router