<template>
    <div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Contests Status</span>
            
            </div>
            <Collapse accordion @on-change="showContestInfo">
                <Panel v-for="(item) in contests" :name="item.contest.contest_id+''">
                    {{item.contest.title}}
                    <Tabs :value="user" slot="content" @on-click="changeTabs">
                        <TabPane label="User" name="user">
                            <Table :data="userDate" :columns="userCol" :loading="userTableLoadingFlag"></Table>
                            <br>
                            <Page style="float: right" show-sizer></Page>
                            <div style="margin-bottom: 180px"></div>
                        </TabPane>
                        <TabPane label="Submit" name="submit">
                            <Table :data="submitData" :columns="submitCol" :loading="userTableLoadingFlag"></Table>
                            <br>
                            <Page style="float: right;" show-sizer></Page>
                            <div style="margin-bottom: 180px"></div>
                        </TabPane>
                        <TabPane label="Problem" name="problem">
                            <Table :data="submitData" :columns="submitCol" :loading="userTableLoadingFlag"></Table>
                            <br>
                            <Page style="float: right;" show-sizer></Page>
                            <div style="margin-bottom: 180px"></div>
                        </TabPane>
                    </Tabs>
                </Panel>
            </Collapse>
        
        </Card>
    </div>
</template>

<script>
    import Api from "../../components/api"

    export default {
        name: "ContestStatus",
        data() {
            return {
                contest_id: null,
                contests: [
                    {
                        "contest": {
                            access: false,
                            contest_id: 1,
                            describes: "Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生",
                            end_at: 1569918812000,
                            max: 40,
                            now: 2,
                            organizer: "Codeforces",
                            privates: 1,
                            start_at: 1548428366000,
                            title: "Codeforces Round #534 (Div. 1)",
                            user_id: 12
                        },
                        "created_by": {
                            nickname: "admin",
                            user_id: 12,
                            username: "admin"
                        },
                    },
                ],
                page: 1,
                per_page: 10,

                userDate: [
                    {
                        correctNum: 0,
                        correctSubmittionNum: 0,
                        totalNum: 2,
                        user: {
                            access_time: 1516101502000,
                            blog_url: null,
                            disabled: false,
                            email: "zhizhiyun@cloud.com",
                            github_url: null,
                            head_img: null,
                            last_login: 1550797302000,
                            nickname: "吱吱云",
                            own_words: null,
                            passwd: "5cba13819e624f8dc0a991a7691f3f82",
                            remark: null,
                            school: "hyit",
                            session_id: "DA9D88ABC2622A8D1230F06E7667D1B7",
                            solved: 0,
                            submit: 8,
                            user_id: 9,
                            username: "cloud",
                        }

                    }
                ],
                userCol: [
                    {
                        title: "username",
                        key: "user.username",
                        align: "center",
                        render: (h, params) => {
                            return h('span', {}, params.row.user.username)
                        }
                    },
                    {
                        title: "Correct",
                        align: "center",
                        sortable: true,
                        render: (h, params) => {
                            return h('span', {}, params.row.correctNum)
                        }
                    },
                    {
                        title: "Total Submit",
                        align: "center",
                        render: (h, params) => {
                            return h('span', {}, params.row.totalNum)
                        }
                    },
                    {
                        title: "Accepted Rate",
                        align: "center",
                        sortable: true,
                        render: (h, params) => {
                            return h('span', {}, (params.row.correctSubmittionNum / params.row.totalNum).toFixed(2) * 100 + "%")
                        }
                    },
                ],
                userTableLoadingFlag: false,
                user_page: 1,
                user_per_page: 10,

                submitData: [
                    {
                        "problem": {
                            "create_by": null,
                            "created_at": null,
                            "defunct": null,
                            "description": null,
                            "display_id": null,
                            "hint": null,
                            "input": null,
                            "memory_limit": null,
                            "output": null,
                            "problem_id": 11,
                            "sample_input": null,
                            "sample_output": null,
                            "source": null,
                            "submit": null,
                            "time_limit": null,
                            "title": "陶陶摘苹果"
                        },
                        "solution": {
                            "code_lenght": 339,
                            "contest_id": 18,
                            "create_at": 1557998256000,
                            "ip": "127.0.0.1",
                            "language": 1,
                            "memory": 0,
                            "num": 0,
                            "problem_id": 11,
                            "result": 4,
                            "solution_id": "f91b38c7cf1b",
                            "time": 0,
                            "user_id": 12,
                            "valid": 1
                        },
                        "user": {
                            "disabled": false,
                            "nickname": "admin",
                            "user_id": 12,
                            "username": "admin"
                        }
                    }
                ],
                submitCol: [
                    {
                        title: "Solution Id",
                        render: (h, params) => {
                            return h("router-link", {
                                attrs: {
                                    to: ""
                                }
                            }, params.row.solution.solution_id);
                        }
                    },
                    {
                        title: "Status",
                        render: (h, params) => {
                            return h('Tag', {
                                props: this.getStatusRenderProps(params.row.solution.result),
                            }, this.statusRenderText[params.row.solution.result]);
                        }
                    },
                    {
                        title: "username",
                        render: (h, params) => {
                            return h("router-link", {
                                attrs: {
                                    to: ""
                                }
                            }, params.row.user.username);
                        }
                    },
                    {
                        title: "Problem Title",
                        render: (h, params) => {
                            return h("router-link", {
                                attrs: {
                                    to: ""
                                }
                            }, params.row.problem.title);
                        }
                    },
                    {
                        title: "Create Time",
                        render: (h, params) => {
                            return h("Time", {
                                props: {
                                    time: params.row.solution.create_at,
                                    type: "datetime",
                                }
                            });
                        }
                    }
                ],
                submit_page: 1,
                submit_per_page: 10,
                submitTableLoadingFlag: false,
                statusRenderText: ["Pending", "Waiting", "Compiling", "Judging", "Accepted", "Presentation Error",
                    "Wrong Answer", "Time Limit Exceeded", "Merrory Limit Exceeded", "Output Limit Exceeded", "Runtime Error",
                    "Compile Error", "Partial Accepted", "System Error"],

            }
        },
        methods: {
            getContests() {
                Api.getContestsByPagePer_PageAndCreator(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 200) {
                        this.contests = result.data.contests;
                    } else if (result.code === 401) {
                        this.$Message.error("签名过期,请重新登录!");
                        window.location.replace("/admin/login");
                    }
                }).catch(res => {
                    console.log(res);
                });
            },
            showContestInfo(contest_ids) {
                // console.log(contest_ids[0]);
                this.contest_id = contest_ids[0];
                this.getUserData();
                this.getSubmitionData();
            },
            changeTabs(index) {
                switch (index) {
                    case "user": {
                        // this.getUserData();
                        break;
                    }
                    case "submit": {
                        break;
                    }
                }
            },
            getUserData() {
                this.userTableLoadingFlag = true;
                Api.findContestUser(this.contest_id, this.user_page, this.user_per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code == 200) {
                        this.userDate = result.data.users;
                    } else {
                        console.log(result.message);
                    }
                    this.userTableLoadingFlag = false;
                }).catch(res => {
                    console.log(res);
                    this.userTableLoadingFlag = false;
                })
            },
            getSubmitionData() {
                this.submitTableLoadingFlag = true;
                Api.getSolutionsByContestId(this.contest_id, this.$store.state.token, this.submit_page, this.submit_per_page).then(res => {
                    let result = res.data;
                    if (result.code == 200) {
                        this.submitData = result.data.solutions;
                    } else {
                        console.log(result.message);
                    }
                    this.submitTableLoadingFlag = false;
                }).catch(res => {
                    console.log(res);
                    this.submitTableLoadingFlag = false;
                });
            },
            //提交状态Tag颜色
            getStatusRenderProps(code) {
                switch (code) {
                    case 0: {
                        return {
                            color: "blue",
                        };
                    }
                    case 1: {
                        return {
                            color: "cyan",
                        };
                    }
                    case 2: {
                        return {
                            color: "geekblue",
                        };
                    }
                    case 3: {
                        return {
                            color: "lime",
                        };
                    }
                    case 4: {
                        return {
                            color: "success",
                        };
                    }
                    case 5: {
                        return {
                            color: "yellow",
                        };
                    }
                    case 6: {
                        return {
                            color: "#FFA2D3",
                        };
                    }
                    case 7: {
                        return {
                            color: "gold",
                        };
                    }
                    case 8: {
                        return {
                            color: "orange",
                        };
                    }
                    case 9: {
                        return {
                            color: "volcano",
                        };
                    }
                    case 10: {
                        return {
                            color: "error",
                        };
                    }
                    case 11: {
                        return {
                            color: "warning",
                        };
                    }
                    case 13: {
                        return {
                            color: "green",
                        };
                    }
                    case 14: {
                        return {
                            color: "purple",
                        };
                    }
                }
            },
        },
        mounted() {
            this.getContests();
        }
    }
</script>

<style scoped>

</style>