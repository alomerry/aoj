<template>
    <div class="card-content">
        <Card>
            <p class="card-title">Status</p>
            
            <div slot="extra">
                <Row>
                    <Col span="3" style="margin-top: 4px;">
                        <i-switch v-model="onlyShowMe" size="large" @on-change="changeOwner">
                            <span slot="open">Mine</span>
                            <span slot="close">All</span>
                        </i-switch>
                    </Col>
                    <Col span="8">
                        <Dropdown style="margin-top: 4px" @on-click="changeStatusState">
                            <a href="javascript:void(0)" style="color: #515a6e">
                                {{statusItem[statusSelectedIndex]}}
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem name="15">All</DropdownItem>
                                <DropdownItem name="0">Waiting</DropdownItem>
                                <DropdownItem name="1">Pending</DropdownItem>
                                <DropdownItem name="2">Compiling</DropdownItem>
                                <DropdownItem name="3">Judging</DropdownItem>
                                <DropdownItem name="4">Accepted</DropdownItem>
                                <DropdownItem name="5">Presentation Error</DropdownItem>
                                <DropdownItem name="6">Wrong Answer</DropdownItem>
                                <DropdownItem name="7">Time Limit Exceeded</DropdownItem>
                                <DropdownItem name="8">Merrory Limit Exceeded</DropdownItem>
                                <DropdownItem name="9">Output Limit Exceeded</DropdownItem>
                                <DropdownItem name="10">Runtime Error</DropdownItem>
                                <DropdownItem name="11">Compile Error</DropdownItem>
                                <DropdownItem name="13">Partial Accepted</DropdownItem>
                                <DropdownItem name="14">System Error</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </Col>
                    <Col span="8">
                        <Input v-model="searchKeyWord" placeholder="Keywords" clearable>
                            <Icon type="ios-search" slot="prefix"/>
                        </Input>
                    </Col>
                    <Col span="2">
                        <Button :loading="buttonLoading" @click.native="refresh" type="primary" style="margin-right: 60px;margin-left: 7px">
                            Refresh
                        </Button>
                    </Col>
                </Row>
            </div>
            <div style="margin-bottom: 2px;margin-top: 10px">
                <Table :columns="statusColumns" :data="statusSearchData" stripe :loading="tableLoadingFlag"></Table>
            </div>
            <p style="margin-top: 30px"></p>
        </Card>
        <div style="float: right;margin: 5px;">
            <Page :total="totalPage" :page-size="per_page" :current="page" show-sizer/>
        </div>
    </div>
</template>

<script>
    import debounce from "lodash/debounce"
    import Api from "../../components/api"

    export default {
        name: "status",
        data() {
            return {
                onlyShowMe: false,
                buttonLoading: false,
                searchKeyWord: "",//搜索框的内容
                page: 1,
                totalPage: 1,
                per_page: 10,
                tableLoadingFlag: false,
                statusColumns: [
                    {
                        title: 'When',
                        key: 'when',
                        align: 'center',
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.solution.create_at,
                                    type: 'datetime'
                                },
                            });
                        }
                    },
                    {
                        title: 'Id',
                        key: 'id',
                        align: 'center',
                        render: (h, params) => {
                            return this.$store.state.user.user_id == params.row.user.user_id ?
                                h('a', {
                                    on: {
                                        click: () => {
                                            alert(666);
                                        },
                                    }
                                }, params.row.solution.solution_id) : h('span', {}, params.row.solution.solution_id);
                        }
                    },
                    {
                        title: 'Status',
                        key: 'status',
                        align: 'center',
                        render: (h, params) => {
                            return h('Tag', {
                                props: this.getStatusRenderProps(params.row.solution.result),
                            }, this.statusRenderText[params.row.solution.result]);
                        }
                    },
                    {
                        title: 'Problem',
                        key: 'problem',
                        align: 'center',
                        render: (h, params) => {
                            return h('router-link', {
                                attrs: {
                                    to: "/problem/" + params.row.problem.problem_id,
                                },
                            }, params.row.problem.title);
                        }
                    },
                    {
                        title: 'Time',
                        key: 'time',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {}, params.row.solution.time === null ? "-" : params.row.solution.time);
                        }
                    },
                    {
                        title: 'Memory',
                        key: 'memory',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {}, params.row.solution.memory === null ? "-" : params.row.solution.memory);
                        }
                    },
                    {
                        title: 'Language',
                        key: 'language',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', {}, this.codeLanguage(params.row.solution.language));
                        }
                    },
                    {
                        title: 'Author',
                        key: 'author',
                        align: 'center',
                        render: (h, params) => {
                            return h('router-link', {
                                attrs: {
                                    to: "/user-home" + (params.row.user == null ? "" : "?username=" + params.row.user.username),
                                },
                            }, params.row.user.nickname);
                        }
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        fixed: 'right',
                        align: 'center',
                        render: (h, params) => {
                            return h('Button', {
                                props: {
                                    ghost: true,
                                    shape: "circle",
                                    type: "info",
                                    size: "small"
                                }
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
                ],
                statusSearchData: [],
                statusRenderText: ["Pending", "Waiting", "Compiling", "Judging", "Accepted", "Presentation Error",
                    "Wrong Answer", "Time Limit Exceeded", "Merrory Limit Exceeded", "Output Limit Exceeded", "Runtime Error",
                    "Compile Error", "Partial Accepted", "System Error"],

                statusSelectedIndex: 15,
                statusItem: [
                    "Waiting",
                    "Pending",
                    "Compiling",
                    "Judging",
                    "Accepted",
                    "Presentation",
                    "Wrong Answer",
                    "Time Limit Exceeded",
                    "Merrory Limit Exceeded",
                    "Output Limit Exceeded",
                    "Runtime Error",
                    "Compile Error",
                    "",
                    "Partial Accepted",
                    "System Error",
                    "All",
                ],
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
            },
            //查询提交
            getSolutions() {
                this.tableLoadingFlag = true;
                Api.getSolutions(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code === 200) {
                        this.statusData = result.data.solutions;
                        this.statusSearchData = this.statusData;
                    }
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                });
            },
            //查询自己的提交
            getOwnSolutions() {
                this.tableLoadingFlag = true;
                Api.getSolutionsByOwn(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code === 200) {
                        this.statusData = result.data.solutions;
                        this.statusSearchData = this.statusData;
                    }
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                });
            },
            //搜索查询表格
            DelaySearchTable: function () {
                if (this.searchKeyWord === "") {
                    this.statusSearchData = this.statusData;
                } else {
                    this.statusSearchData = [];
                    let that = this;
                    this.statusData.forEach(function (is, i) {
                        let item = that.statusData[i];
                        if (item.problem.title.indexOf(that.searchKeyWord) !== -1 || item.user.nickname.indexOf(that.searchKeyWord) !== -1) {
                            that.statusSearchData.push(item);
                        }
                    });
                }
            },
            codeLanguage(code) {
                switch (code) {
                    case 0: {
                        return 'Java';
                    }
                    case 1: {
                        return 'C';
                    }
                    case 2: {
                        return 'C++';
                    }
                    case 3: {
                        return 'Python';
                    }
                }
            },
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
            //查看所有提交/仅自己的提交
            changeOwner(val) {
                if (val) {//Mine
                    this.getOwnSolutions();
                } else {//All
                    this.getSolutions();
                }
            },
            //根据状态查询提交
            changeStatusState(state) {
                this.statusSelectedIndex = state;
                this.getSolutionsByState(state);
            },
            getSolutionsByState(state) {
                this.tableLoadingFlag = true;
                Api.getSolutionsByState(state, this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code === 200) {
                        this.statusData = result.data.solutions;
                        this.statusSearchData = this.statusData;
                    }
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.tableLoadingFlag = false;
                    this.buttonLoading = false;
                });
            },
            refresh() {
                this.buttonLoading = true;
                this.getSolutions();
            }
        },
        created() {
            this.updateActiveClass(this.$route.path);
            this.debouncedsearchData = debounce(this.DelaySearchTable, 500, null);//延时加载
        },
        mounted() {
            this.getSolutions();
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            }
        },
        computed: {}
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
        margin: 10px 50px 0 50px;
    }
</style>
<!--
    <Tag color="blue">blue			Pending 0
    <Tag color="cyan">cyan			Waiting 1
    <Tag color="geekblue">geekblue		Compiling 2
    <Tag color="lime">lime			Judging 3
    <Tag color="success">success		Accepted 4
    <Tag color="yellow">yellow			Presentation Error 5
    <Tag color="#FFA2D3">Custom Color		Wrong Answer 6
    <Tag color="gold">gold			Time Limit Exceeded 7
    <Tag color="orange">orange		Merrory Limit Exceeded 8
    <Tag color="volcano">volcano		Output Limit Exceeded 9
    <Tag color="error">error			Runtime Error 10
    <Tag color="warning">warning		Compile Error 11
    <Tag color="green">green			Partial Accepted 13
    <Tag color="purple">purple			System Error 14
    <Tag color="magenta">magenta
    <Tag color="red">red</Tag>
    <Tag color="default">default</Tag>
    <Tag color="primary">primary</Tag>
-->