import instance from '@/utils/request.js'

// 添加交易服务
export const addTradeService = (sellerID, bookID) => {
    const params = new URLSearchParams()
    params.append("sellerID", sellerID)
    params.append("bookID", bookID)
    return instance.post("/trade/addTrade", params)
}

// 更新交易状态服务
export const updateTrade = (tradeID, state, notes) => {
    const params = new URLSearchParams()
    params.append("tradeID", tradeID)
    params.append("state", state)
    params.append("notes", notes)
    return instance.post("/trade/updateTrade", params)
}

// 分页获取所有交易
export const getAllTradeService = (pageNum, pageSize, searchData) =>{
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/trade/getAllTrade', params)
}

// 分页获取发起的交易服务
export const getTradeFromMeService = (pageNum, pageSize, searchData) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/trade/getTradeFromMe', params)
}

// 分页获取接收的交易服务
export const getTradeToMeService = (pageNum, pageSize, searchData) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/trade/getTradeToMe', params)
}