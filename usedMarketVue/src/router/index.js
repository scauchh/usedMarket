import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UsedMarket from '@/views/usedMarket/UsedMarket.vue'
import MyUsed from '@/views/myUsed/MyUsed.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserPasswordVue from '@/views/user/UserPassword.vue'
import CategoryVue from '@/views/manage/Category.vue'
import UserManageVue from '@/views/manage/UserManage.vue'
import TradeVue from '@/views/manage/Trade.vue'
import TradeFromMeVue from '@/views/trade/TradeFromMe.vue'
import TradeToMeVue from '@/views/trade/TradeToMe.vue'

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
            { path: '/manage/category', component: CategoryVue },
            { path: '/manage/UserManage', component: UserManageVue },
            { path: '/manage/Trade', component: TradeVue },
            { path: '/trade/TradeFromMe', component: TradeFromMeVue },
            { path: '/trade/TradeToMe', component: TradeToMeVue },
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router