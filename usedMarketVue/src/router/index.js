import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UsedMarket from '@/views/usedMarket/UsedMarket.vue'
import MyUsed from '@/views/myUsed/MyUsed.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserPasswordVue from '@/views/user/UserPassword.vue'
import CategoryVue from '@/views/manage/Category.vue'

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
            { path: '/user/password', component: UserPasswordVue },
            { path: '/manage/category', component: CategoryVue }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router