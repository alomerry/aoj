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
        })
    }
}
