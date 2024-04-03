import instance from '@/utils/request.js'

// 添加交易服务
export const addTradeService = (sellerID, bookID) => {
    const params = new URLSearchParams()
    params.append("sellerID", sellerID)
    params.append("bookID", bookID)
    return instance.post("/trade/addTrade", params)
}