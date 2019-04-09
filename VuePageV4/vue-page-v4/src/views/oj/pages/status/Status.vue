<template>
    <div class="card-content">
        <Card>
            <p class="card-title">Status</p>
            <Button slot="extra" style="margin-right: 60px" :loading="buttonLoading" @click.native="refresh">
                ReSet
            </Button>
            <div style="margin-bottom: 2px;margin-top: 10px">
                <Table :columns="statusColumns" stripe></Table>
            </div>
            <div style="float: right;margin: 5px;">
                <Page :total="totalPage" :page-size="per_page" :current="current" show-sizer/>
            </div>
            <p style="margin-top: 30px"></p>
        </Card>
    </div>
</template>

<script>
    export default {
        name: "status",
        data() {
            return {
                buttonLoading: false,
                current: 1,
                totalPage: 1,
                per_page: 10,
                statusColumns: [
                    {
                        title: 'When',
                        key: 'when',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.create_at);
                        }
                    },
                    {
                        title: 'Id',
                        key: 'id',
                        align: 'center',
                        render: (h, params) => {
                            return h('a', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.solution_id);
                        }
                    },
                    {
                        title: 'Status',
                        key: 'status',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.result);
                        }
                    },
                    {
                        title: 'Problem',
                        key: 'problem',
                        align: 'center',
                        render: (h, params) => {
                            return h('a', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.problem.title);
                        }
                    },
                    {
                        title: 'Time',
                        key: 'time',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.time);
                        }
                    },
                    {
                        title: 'Memory',
                        key: 'memory',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.memory);
                        }
                    },
                    {
                        title: 'Language',
                        key: 'language',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.solution.language);
                        }
                    },
                    {
                        title: 'Author',
                        key: 'author',
                        align: 'center',
                        render: (h, params) => {
                            return h('a', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.user.username);
                        }
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        fixed: 'right',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, "Rejudge");
                        }
                    },
                ],
                statusData: [
                    {
                        problem: {
                            problem_id: 7,
                            title: "方阵填数"
                        },
                        solution: {
                            code_lenght: 110,
                            contest_id: null,
                            create_at: 1554760701000,
                            ip: "127.0.0.1",
                            judgetime: null,
                            language: 2,
                            memory: 0,
                            num: 0,
                            problem_id: 7,
                            result: 0,
                            solution_id: "dsfs25ess",
                            time: null,
                            user_id: 10,
                            valid: 1
                        },
                        user: {
                            nickname: "就当一次路过丶",
                            user_id: 10,
                            username: "morizunzhu"
                        }
                    }
                ]
            }
        },
        methods: {
            updateActiveClass(path) {
                switch (path) {
                    case "/status": {
                        this.$store.dispatch("updateActiveName", 'status');
                        break;
                    }
                    case "/home": {
                        this.$store.dispatch("updateActiveName", 'home');
                        break;
                    }
                    case "/problems": {
                        this.$store.dispatch("updateActiveName", 'problems');
                        break;
                    }
                }
            }
        },
        created() {
            this.updateActiveClass(this.$route.path);
        }
    }
</script>

<style scoped>
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 0 20px;
        height: 35px;
    }
    
    .card-content {
        margin: 10px 20px 0 20px;
    }
</style>
