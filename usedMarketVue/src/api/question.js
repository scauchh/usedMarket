import instance from '@/utils/request.js'

// 添加密保问题服务
export const addQuestionService = (questionModel) => {
    const params = new URLSearchParams()
    for (let key in questionModel) {
        params.append(key, questionModel[key])
    }
    return instance.post('/question/addQuestion', params)
}

// 删除密保问题服务
export const deleteQuestionService = (questionID) =>{
    const params = new URLSearchParams()
    params.append("questionID", questionID)
    return instance.post('/question/deleteQuestion', params)
}

// 修改密保问题服务
export const changeQuestionService = (oldQuestionModel, questionModel) => {
    const params = new URLSearchParams()
    for (let key in oldQuestionModel) {
        params.append(key, oldQuestionModel[key])
    }
    for (let key in questionModel) {
        params.append(key, questionModel[key])
    }
    return instance.post('/question/changeQuestion', params)
}

// 获取所有密保问题服务
export const getAllQuestionService = () => {
    return instance.post('/question/getAllQuestion')
}