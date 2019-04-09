<template>
    <div class="card-content">
        <Card>
            <p class="card-title">Status</p>
            
            <div slot="extra">
                <Row>
                    <Col span="3" style="margin-top: 4px;">
                        <i-switch v-model="onlyShowMe" size="large" @on-change="">
                            <span slot="open">Mine</span>
                            <span slot="close">All</span>
                        </i-switch>
                    </Col>
                    <Col span="6">
                        <Dropdown style="margin-top: 4px">
                            <a href="javascript:void(0)" style="color: #515a6e">
                                下拉菜单
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem>驴打滚</DropdownItem>
                                <DropdownItem>炸酱面</DropdownItem>
                                <DropdownItem disabled>豆汁儿</DropdownItem>
                                <DropdownItem>冰糖葫芦</DropdownItem>
                                <DropdownItem divided>北京烤鸭</DropdownItem>
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
            <Page :total="totalPage" :page-size="per_page" :current="current" show-sizer/>
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
                current: 1,
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
            getSolutions() {
                this.tableLoadingFlag = true;
                Api.getSolutions(this.current, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code === 200) {
                        this.statusData = result.data.solutions;
                        this.statusSearchData = this.statusData;
                    }
                    this.tableLoadingFlag = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.tableLoadingFlag = false;
                });
                this.tableLoadingFlag = false;
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
        },
        created() {
            this.updateActiveClass(this.$route.path);
            this.getSolutions();
            this.debouncedsearchData = debounce(this.DelaySearchTable, 500, null);//延时加载
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            }
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
        margin: 10px 50px 0 50px;
    }
</style>
