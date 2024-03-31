import instance from '@/utils/request.js'

// 清除无用图片服务
export const removeImgService = () => {
    return instance.post('/removeImg')
}