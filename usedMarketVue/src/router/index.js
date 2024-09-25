import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
// import UsedMarket from '@/views/usedMarket/UsedMarket.vue'
// import MyUsed from '@/views/myUsed/MyUsed.vue'
// import UserInfoVue from '@/views/user/UserInfo.vue'
// import UserPasswordVue from '@/views/user/UserPassword.vue'
// import CategoryVue from '@/views/manage/Category.vue'
// import UserManageVue from '@/views/manage/UserManage.vue'
// import TradeVue from '@/views/manage/Trade.vue'
// import QuestionVue from '@/views/manage/Question.vue'
// import TradeFromMeVue from '@/views/trade/TradeFromMe.vue'
// import TradeToMeVue from '@/views/trade/TradeToMe.vue'

// 配置路由
const routes = [
    {
        path: '/login',
        component: LoginVue
    },
    {
        // 修改为懒加载
        path: '/', component: LayoutVue, redirect: '/usedMarket', children: [
            { path: '/usedMarket', component: () => import('@/views/usedMarket/UsedMarket.vue') },
            { path: '/myUsed', component: () => import('@/views/myUsed/MyUsed.vue') },
            { path: '/user/info', component: () =>import('@/views/user/UserInfo.vue') },
            { path: '/user/password', component: () =>import('@/views/user/UserPassword.vue') },
            { path: '/manage/category', component: () =>import('@/views/manage/Category.vue') },
            { path: '/manage/UserManage', component: () =>import('@/views/manage/UserManage.vue') },
            { path: '/manage/Trade', component: () =>import('@/views/manage/Trade.vue') },
            { path: '/manage/Question', component: () =>import('@/views/manage/Question.vue') },
            { path: '/trade/TradeFromMe', component: () =>import('@/views/trade/TradeFromMe.vue') },
            { path: '/trade/TradeToMe', component: () =>import('@/views/trade/TradeToMe.vue') },
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router