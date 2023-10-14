import axios from 'axios'//使用ajax


axios.defaults.withCredentials = true;
axios.defaults.xsrfHeaderName = 'X-CSRFToken';
axios.defaults.xsrfCookieName = 'csrftoken';

export default {
    name: 'Api',

    /**
     * 查询指定题目的解题错误信息
     * @param problem_id
     */
    getProblemSolutionStatus(problem_id) {
        return axios({
            url: "/api-oj/api-oj/problem/" + problem_id + "/submit",
            method: 'get',
        })
    },

    /**
     * 根据题目标题查找相似提u
     * @param title 题目标题
     */
    getSimilarProblemByProblemName(title){
        return axios({
            url: "/api-oj/api-oj/problem/title/" + title ,
            method: 'get',
        })
    },

    /**
     * 获取验证码
     */
    checkVerifyCode(code) {
        return axios({
            url: "/api-oj/api-oj/checkVerify?code=" + code,
            method: 'post',
        })
    },

    registerUser(user) {
        return axios({
            url: "/api-oj/api-oj/register",
            method: 'post',
            data: require('querystring').stringify(user),
        })
    },
    /**
     *
     * @param page
     * @param per_page
     * @param resType 需求结果类型
     * @returns {AxiosPromise}
     */
    findProblemsByPagePer_PageAndResultType(page, per_page, resType) {
        return axios({
            url: "/api-oj/api-oj/problems/defunct/1?page=" + page + "&per_page=" + per_page + "&resType=" + resType,
            method: 'get',
        })
    },

    /**
     * 查询指定竞赛的题目
     * @param contestId 竞赛Id
     */
    findProblemsByContestId(contestId) {
        return axios({
            url: "/api-oj/api-oj/contest/" + contestId + "/problems",
            method: 'get',
        })
    },

    /**
     * 根据用户名查找用户
     * @param username 用户名
     */
    findUserInfoByUserName(username) {
        return axios({
            url: "/api-oj/api-oj/user?username=" + username,
            method: 'get',
        })
    },

    /**
     * 根据用户名查找用户
     * @param username 用户名
     */
    findUserInfoByToken(jwt) {
        return axios({
            url: "/api-oj/api-oj/user",
            method: 'get',
            headers:{
                "jwt":jwt,
            }
        })
    },

    /**
     * 更新头像
     * @param path 路径
     * @param jwt 令牌
     */
    updateUserHeaderImage(path,jwt){
        return axios({
            url: "/api-oj/api-oj/user_head_image?head_url="+path,
            method: 'put',
            headers:{
                "jwt":jwt,
            }
        })
    },

    /**
     * 更新用户信息
     * @param jwt 令牌
     * @param user 用户实体
     */
    updateUserProfile(jwt,user){
        return axios({
            url: "/api-oj/api-oj/user/profile",
            data:JSON.stringify(user),
            dataType: "json",
            method: 'put',
            headers:{
                "jwt":jwt,
                "Content-Type": "application/json;charset-UTF-8",
            }
        })
    },
    /**
     * 更新用户信息
     * @param jwt 令牌
     * @param user 用户实体
     */
    updateUserAccount(jwt,user){
        return axios({
            url: "/api-oj/api-oj/user/account",
            data:JSON.stringify(user),
            dataType: "json",
            method: 'put',
            headers:{
                "jwt":jwt,
                "Content-Type": "application/json;charset-UTF-8",
            }
        })
    },

    findCompileErrorInfo(jwt,solution_id){
        return axios({
            url: "/api-oj/api-oj/solution/"+solution_id+"/compile_error_info",
            method: 'get',
            headers:{
                "jwt":jwt,
            }
        })
    },

    /**
     * 重新评判
     * @param jwt
     * @param solutionId
     */
    rejudge(jwt,solutionId){
        return axios({
            url: "/api-oj/api-oj/solution/"+solutionId+"/rejudge",
            method: 'get',
            headers:{
                "jwt":jwt,
            }
        })
    },

    /**
     * 申请竞赛
     * @param contestId 竞赛Id
     * @param jwt 令牌
     */
    applyContestByContestId(contestId, jwt) {
        return axios({
            url: "/api-oj/api-oj/contest/" + contestId + "/contest_apply",
            method: 'post',
            headers: {
                "jwt": jwt,
            }
        })
    },

    /**
     * 查询制定Id题目
     * @param problem_id
     * @returns {AxiosPromise}
     */
    findProblemByProblemId(problem_id, jwt) {
        return axios({
            url: '/api-oj/api-oj/problem/' + problem_id,
            method: 'get',
            headers: {
                "jwt": jwt,
            }
        });
    },

    /**
     * 查询指定用户是否成功参加竞赛
     * @param contest_id 竞赛Id
     * @param jwt 令牌
     */
    checkIsSucceedApplyContest(contest_id, jwt) {
        return axios({
            url: '/api-oj/api-oj/contest/' + contest_id + "/access",
            method: 'get',
            headers: {
                "jwt": jwt,
            }
        })
    },

    /**
     * 提交解题
     * @param submit 解题json对象
     * @param jwt 令牌
     * @returns {AxiosPromise}
     */
    insertNewSolution(submit, jwt) {
        return axios({
            url: '/api-oj/api-oj/submit',
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
     */
    getContestSolutions(contestId, page, per_page) {
        return axios({
            url: "/api-oj/api-oj/contest/" + contestId + "/solutions?page=" + page + "&per_page=" + per_page,
            method: "get",
        })
    },

    /**
     * 获取solutions集合
     * @param page 页码
     * @param per_page 每页数量
     */
    getSolutions(page, per_page, jwt) {
        return axios({
            url: "/api-oj/api-oj/solutions?myself=0&page=" + page + "&per_page=" + per_page,
            method: "get",
            headers: {
                "jwt": jwt,
            }
        })
    },

    getRank(page,per_page){
        return axios({
            url: "/api-oj/api-oj/rank?page=" + page + "&per_page=" + per_page,
            method: "get",
        })
    },

    getSolutionsByState(state, myself, page, per_page, jwt) {
        return axios({
            url: "/api-oj/api-oj/state/" + state + "/solutions?myself=" + myself + "&page=" + page + "&per_page=" + per_page,
            method: "get",
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
        return axios({
            url: "/api-oj/api-oj/news?page=" + page + "&per_page=" + per_page,
            method: "get",
        })
    },

    /**
     * 查询标签
     * @param page 页码
     * @param per_page 每页数量
     */
    findTags(page, per_page) {
        return axios({
            url: "/api-oj/api-oj/tags?page=" + page + "&per_page=" + per_page,
            method: "get",
        });
    },

    /**
     * 根据页码和标签Id查询题目
     * @param tag_id 标签Id
     * @param page 页码
     * @param per_page 每页数量
     */
    findProblemsByTagId(tag_id, page, per_page) {
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        return axios({
            url: "/api-oj/api-oj/problems/tag/" + tag_id,
            method: "get",
            data: params,
        });
    },

    /**
     * 查询竞赛
     * @param page 页码
     * @param per_page 每页数量
     */
    findContestByPage(page, per_page) {
        return axios({
            url: "/api-oj/api-oj/contests?page=" + page + "&per_page=" + per_page,
            method: "get",
        });
    },

    /**
     * 查询指定竞赛
     * @param contestId 竞赛Id
     */
    findContestByContestId(contestId) {
        return axios({
            url: "/api-oj/api-oj/contest/" + contestId,
            method: "get",
        })
    },

    /**
     * 查询指定竞赛的新闻
     * @param contestId
     */
    findAnnosByContestId(contestId, page, per_page) {
        return axios({
            url: "/api-oj/api-oj/contest/" + contestId + "/news?page=" + page + "&per_page=" + per_page,
            method: "get",
        })
    }
}