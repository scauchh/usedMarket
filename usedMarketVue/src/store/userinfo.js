import { defineStore } from 'pinia'
import { ref } from 'vue'

// 储存用户信息
export const useUserInfoStore = defineStore('userinfo', () => {
    const info = ref({})

    const setInfo = (newInfo) => {
        info.value = newInfo
    }

    const removeInfo = () => {
        info.value = {}
    }

    return { info, setInfo, removeInfo }
}, {
    persist: true
})