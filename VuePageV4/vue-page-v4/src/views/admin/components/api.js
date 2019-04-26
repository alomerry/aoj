import axios from "axios/index";


axios.defaults.xsrfHeaderName = 'X-CSRFToken';
axios.defaults.xsrfCookieName = 'csrftoken';

export default {
    name: 'Api',

    /**
     * 查询用户
     * @param page 页码
     * @param per_page 每页数量
     * @param jwt token
     */
    getUsersByPagePer_Page(page, per_page, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/admin/users",
            method: 'get',
            data: params,
            headers: {
                "jwt": jwt,
            }
        });
    },

    /**
     * 根据页码查询竞赛集
     * @param page
     * @param per_page
     * @param jwt
     */
    getContestsByPagePer_PageAndCreator(page, per_page, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/admin/contests",
            method: 'get',
            data: params,
            headers: {
                "jwt": jwt,
            }
        });
    },
    /**
     * 查询题目
     * @param page 页码
     * @param per_page 每页数量
     * @param jwt token
     */
    getProblemsByPagePer_Page(page, per_page, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/admin/problems",
            method: 'get',
            data: params,
            headers: {
                "jwt": jwt,
            }
        });
    },
    /**
     * 根据竞赛Id查询题目集
     * @param page 页码
     * @param per_page 每页数量
     * @param contest_id 竞赛Id
     * @param jwt token
     */
    getContestProblemsByPageAndContestId(page, per_page, contest_id, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/admin/contest/" + contest_id + "/problems",
            method: "get",
            data: params,
            headers: {
                "jwt": jwt,
            }
        });
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
     * 查询公开题目集
     */
    findPublicProblemsByPage(page, per_page) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/problems/defunct/1",
            method: "get",
            data: params,
        })
    },
    /**
     * 根据题目Id查询标签集合
     * @param problem_id
     * @returns {AxiosPromise}
     */
    findTagsByProblemId(problem_id) {
        return axios({
            url: "/api/api-oj/tags/problem/" + problem_id,
            method: "get",
        });
    },
    /**
     * 将指定Id的题目添加至指定Id的竞赛
     * @param contest_id 比赛Id
     * @param problem_id 题目Id
     * @param jwt token
     * @constructor
     */
    addPublicProblemToContest(contest_id, problem_id, jwt) {
        return axios({
            url: "/api/api-oj/admin/contest/" + contest_id + "/problem/" + problem_id,
            method: "post",
            headers: {
                "jwt": jwt,
            }
        });
    },

    /**
     * 将指定题目从指定比赛中删除
     * @param contest_id 竞赛Id
     * @param problem_id 题目Id
     * @param jwt 令牌
     * @constructor
     */
    deleteProblemFromContest(contest_id, problem_id, jwt) {
        return axios({
            url: "/api/api-oj/admin/contest/" + contest_id + "/problem/" + problem_id,
            method: "delete",
            headers: {
                "jwt": jwt,
            }
        });
    },

    createNewProblem(problem, tags, testcase_id, jwt) {
        let params = new URLSearchParams();
        params.append("problem", JSON.stringify(problem));
        params.append("tags", tags == null ? null : JSON.stringify(tags));
        params.append("testCaseId", testcase_id);
        return axios({
            url: "/api/api-oj/admin/problem",
            method: "post",
            data: params,
            headers: {
                "jwt": jwt,
            }
        })
    },
    /**
     * 删除指定题目
     * @param problem_id 题目Id
     * @param jwt 令牌
     * @returns {AxiosPromise}
     */
    deleteProblemByProblemId(problem_id, jwt) {
        return axios({
            url: "/api/api-oj/admin/problem/" + problem_id,
            method: "delete",
            headers: {
                "jwt": jwt,
            }
        })
    },
    /**
     * 删除指定用户
     * @param user_id 用户Id
     * @param jwt 令牌
     */
    deleteUserByUserId(user_id, jwt) {
        return axios({
            url: '/api/api-oj/admin/user/' + user_id,
            method: "delete",
            headers: {
                "jwt": jwt,
            }
        });
    },

    /**
     * 更新用户信息
     * @param user json格式的用户
     * @param jwt 令牌
     */
    updateUser(user, jwt) {
        return axios({
            url: '/api/api-oj/admin/user',
            data: JSON.stringify(user),
            method: "put",
            headers: {
                "jwt": jwt,
                "Content-Type": "application/json"
            }
        })
    },
    /**
     * 查询新闻
     * @param page 页码
     * @param per_page 每页数量
     * @param jwt 令牌
     */
    findNews(page, per_page, jwt) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api/api-oj/admin/news",
            method: "get",
            data: params,
            headers: {
                "jwt": jwt,
            }
        })
    },
    /**
     * 创建新闻
     * @param news 新闻实体
     * @param jwt 令牌
     * @returns {AxiosPromise}
     */
    createNews(news, jwt) {
        return axios({
            url: "/api/api-oj/admin/news",
            method: "post",
            data: JSON.stringify(news),
            headers: {
                "jwt": jwt,
            }
        });
    }
}
