import instance from '@/utils/request.js'

// 获取所有类型服务
export const getAllTypeService = () => {
    return instance.post('/type/getAllType')
}