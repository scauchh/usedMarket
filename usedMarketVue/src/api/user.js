import instance from '@/utils/request.js'

// 用户注册服务
export const userRegisterService = (userData) => {
    const params = new URLSearchParams()
    for (let key in userData) {
        params.append(key, userData[key])
    }
    return instance.post('/user/register', params)
}

// 用户登录服务
export const userLoginService = (userData) => {
    const params = new URLSearchParams()
    for (let key in userData) {
        params.append(key, userData[key])
    }
    return instance.post('/user/login', params)
}

// 更新用户信息服务
export const updateUserInfoService = (userData) => {
    return instance.post('/user/updateUser', userData)
}

// 更新用户角色服务
export const updateUserRoleService = (changeModel) => {
    const params = new URLSearchParams()
    for(let key in changeModel) {
        params.append(key, changeModel[key])
    }
    return instance.post('/user/updateUserRole', params)
}

// 获取用户信息服务
export const getUserInfoService = () => {
    return instance.post('/user/getUserInfo')
}

// 获取所有用户信息
export const getAllUserInfoService = (pageNum, pageSize, searchData) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/user/getAllUserInfo', params)
}

// 根据用户名获取用户信息服务
export const getUserInfoByIDService = (userID) => {
    const params = new URLSearchParams()
    params.append("userID", userID)
    return instance.post('/user/getUserInfoByID', params)
}