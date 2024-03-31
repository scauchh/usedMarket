import axios from "axios"
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/store/token.js'
import router from '@/router/index.js'

const baseURL = '/api'
const instance = axios.create({ baseURL })

// 请求拦截器
instance.interceptors.request.use(
    config => {
        const tokenStore = useTokenStore()
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config
    },
    err => {
        return Promise.reject(err)
    }
)

// 响应拦截器
instance.interceptors.response.use(
    res => {
        if (res.data.code === 0) {
            return res.data
        } else {
            ElMessage.error(res.data.message ? res.data.message : "服务异常")
            return Promise.reject(res.data.message ? res.data.message : "服务异常")
        }
    },
    err => {
        if (err.response.status === 401) {
            ElMessage.warning("请先完成登录")
            router.push('/login')
        } else {
            ElMessage.error("服务异常")
        }
        return Promise.reject(err)
    }
)

export default instance;