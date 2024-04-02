import instance from '@/utils/request.js'

// 添加书籍类别服务
export const addTypeService = (typeModel) => {
    const params = new URLSearchParams()
    for (let key in typeModel) {
        params.append(key, typeModel[key])
    }
    return instance.post('/type/addType', params)
}

// 删除书籍类别服务
export const deleteTypeService = (typeName) =>{
    const params = new URLSearchParams()
    params.append("typeName", typeName)
    return instance.post('/type/deleteType', params)
}

// 修改书籍类别服务
export const changeTypeService = (oldTypeModel, typeModel) => {
    const params = new URLSearchParams()
    for (let key in oldTypeModel) {
        params.append(key, oldTypeModel[key])
    }
    for (let key in typeModel) {
        params.append(key, typeModel[key])
    }
    return instance.post('/type/changeType', params)
}

// 获取所有类别服务
export const getAllTypeService = () => {
    return instance.post('/type/getAllType')
}