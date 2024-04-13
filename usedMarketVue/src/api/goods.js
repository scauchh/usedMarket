import instance from '@/utils/request.js'

// 添加新物品服务
export const addGoodsService = (goodsModel) => {
    const params = new URLSearchParams()
    for (let key in goodsModel) {
        params.append(key, goodsModel[key])
    }
    return instance.post('/goods/addGoods', params)
}

// 删除物品服务
export const deleteGoodsService = (goodsID) =>{
    const params = new URLSearchParams()
    params.append("goodsID", goodsID)
    return instance.post('/goods/deleteGoods', params)
}

// 更新物品数据服务
export const updateGoodsService = (goodsModel) => {
    const params = new URLSearchParams()
    for (let key in goodsModel) {
        params.append(key, goodsModel[key])
    }
    return instance.post('/goods/updateGoods', params)
}

// 根据ID获取物品服务
export const getGoodsByIDService = (goodsID) => {
    const params = new URLSearchParams()
    params.append("goodsID", goodsID)
    return instance.post('/goods/getGoodsByID', params)
}

// 分页获取所有物品服务
export const getAllPageGoodsService = (pageNum, pageSize, searchData) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/goods/getPageGoods', params)
}

// 分页获取当前用户的物品服务
export const getCurrentPageGoodsService = (pageNum, pageSize) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    return instance.post('/goods/getPageGoodsByID', params)
}