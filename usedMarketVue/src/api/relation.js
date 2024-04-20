import instance from '@/utils/request.js'

// 保存关系服务
export const saveRelationService = (userQuestion) => {
    const params = new URLSearchParams()
    for(let key in userQuestion) {
        if(key === 'questionID' || key === 'answer') {
            if(userQuestion[key] === null) params.append(key, -1)
            else params.append(key, userQuestion[key])
        }
    }
    return instance.post('/relation/saveRelation', params)
}

// 获取当前用户密保问题
export const getCurrentRelationService = () => {
    return instance.post('/relation/getCurrentRelation')
}