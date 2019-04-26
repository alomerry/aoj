import axios from 'axios'//使用ajax


axios.defaults.xsrfHeaderName = 'X-CSRFToken';
axios.defaults.xsrfCookieName = 'csrftoken';

export default {
    name: 'Api',
    /**
     *
     * @param page
     * @param per_page
     * @param resType 需求结果类型
     * @returns {AxiosPromise}
     */
    findProblemsByPagePer_PageAndResultType(page, per_page, resType) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        params.append("resType", resType);//需求结果类型
        return axios({
            url: "/api/api-oj/problems/defunct/1",
            method: 'get',
            data: params,
        })
    },

    /**
     * 查询制定Id题目
     * @param problem_id
     * @returns {AxiosPromise}
     */
    findProblemByProblemId(problem_id) {
        return axios({
            url: '/api/api-oj/problem/' + problem_id,
            method: 'get',
        });
    },

    /**
     * 提交解题
     * @param submit 解题json对象
     * @param jwt 令牌
     * @returns {AxiosPromise}
     */
    insertNewSolution(submit, jwt) {
        return axios({
            url: '/api/api-oj/submit',
            data: JSON.stringify(submit),
            method: 'post',
            headers: {
                "jwt": jwt,
                "Content-Type": "application/json"
            }
        })
    },

    /**
     * 获取solutions集合
     * @param page 页码
     * @param per_page 每页数量
     * @param jwt 令牌
     * @returns {AxiosPromise}
     */
    getSolutions(page, per_page, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/solutions",
            method: "get",
            data: params,
            headers: {
                "jwt": jwt,
            }
        })
    },


    /**
     * 查询新闻
     * @param page 页码
     * @param per_page 每页数量
     */
    findNews(page, per_page) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/news",
            method: "get",
            data: params,
        })
    },
}
