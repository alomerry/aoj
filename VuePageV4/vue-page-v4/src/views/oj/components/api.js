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
     * @returns {Array}
     */
    findProblemsByPagePer_PageAndResultType(page, per_page, resType) {
        let problems = [];
        let params = new URLSearchParams();
        params.append("page", page);
        params.append("per_page", per_page);
        params.append("resType", resType);//需求结果类型
        axios({
            url: "/api/api-oj/problems/defunct/1",
            method: 'get',
            data: params,
        }).then(res => {
            let result = res.data;
            console.log(result);
            if (result.code === 200) {
                for (let i = 0; i < result.data.total; i++) {
                    let item = result.data.results[i];
                    problems.push({
                        accepted: item.accepted,
                        click: item.click,
                        create_time: item.create_time,
                        defunct: item.defunct,
                        description: item.description,
                        hint: item.hint,
                        input: item.input,
                        memory_limit: item.memory_limit,
                        output: item.output,
                        problem_id: item.problem_id,
                        sample_input: item.sample_input,
                        sample_output: item.sample_output,
                        source: item.source,
                        submit: item.submit,
                        time_limit: item.time_limit,
                        title: item.title,
                        user_id: item.user_id
                    });
                }
            } else {
                console.log('Failed! ' + result.message);
            }
        }).catch(err => {
            console.log('An error has occurred! ' + err);
        });
        return problems;
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
    }

}
