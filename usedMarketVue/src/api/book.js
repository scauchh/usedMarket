import instance from '@/utils/request.js'

// 添加新书籍服务
export const addBookService = (bookModel) => {
    const params = new URLSearchParams()
    for (let key in bookModel) {
        params.append(key, bookModel[key])
    }
    return instance.post('/book/addBook', params)
}

// 删除书籍服务
export const deleteBookService = (bookID) =>{
    const params = new URLSearchParams()
    params.append("bookID", bookID)
    return instance.post('/book/deleteBook', params)
}

// 更新书籍数据服务
export const updateBookService = (bookModel) => {
    const params = new URLSearchParams()
    for (let key in bookModel) {
        params.append(key, bookModel[key])
    }
    return instance.post('/book/updateBook', params)
}

// 根据ID获取书籍服务
export const getBookByIDService = (bookID) => {
    const params = new URLSearchParams()
    params.append("bookID", bookID)
    return instance.post('/book/getBookByID', params)
}

// 分页获取所有书籍服务
export const getAllPageBookService = (pageNum, pageSize, searchData) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    for(let key in searchData) {
        params.append(key, searchData[key])
    }
    return instance.post('/book/getPageBook', params)
}

// 分页获取当前用户的书籍服务
export const getCurrentPageBookService = (pageNum, pageSize) => {
    const params = new URLSearchParams()
    params.append("pageNum", pageNum)
    params.append("pageSize", pageSize)
    return instance.post('/book/getPageBookByID', params)
}