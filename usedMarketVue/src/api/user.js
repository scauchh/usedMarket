import instance from '@/utils/request.js'

// 用户注册服务
export const userRegisterService = (userData) => {
    const params = new URLSearchParams();
    for (let key in userData) {
        params.append(key, userData[key])
    }
    return instance.post('/user/register', params)
}

// 用户登录服务
export const userLoginService = (userData) => {
    const params = new URLSearchParams();
    for (let key in userData) {
        params.append(key, userData[key])
    }
    return instance.post('/user/login', params)
}

// 更新用户信息服务
export const updateUserInfoService = (userData) => {
    return instance.post('/user/updateUser', userData)
}

// 获取用户信息服务
export const getUserInfoService = () => {
    return instance.post('/user/getUserInfo')
}

// 根据用户名获取用户信息服务
export const getUserInfoByIDService = (userID) => {
    const params = new URLSearchParams();
    params.append("userID", userID)
    return instance.post('/user/getUserInfoByID', params)
}