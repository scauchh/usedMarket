import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UsedMarket from '@/views/usedMarket/UsedMarket.vue'
import MyUsed from '@/views/myUsed/MyUsed.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

// 配置路由
const routes = [
    {
        path: '/login',
        component: LoginVue
    },
    {
        path: '/', component: LayoutVue, redirect: '/usedMarket', children: [
            { path: '/usedMarket', component: UsedMarket },
            { path: '/myUsed', component: MyUsed },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/resetPassword', component: UserResetPasswordVue }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router