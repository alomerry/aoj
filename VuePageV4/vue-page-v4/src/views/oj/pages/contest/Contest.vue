<template>
    <div>
        <Row>
            <Col span="20" style="margin-right: 10px;margin-left: 30px">
                <transition mode="out-in" name="fade">
                    <Card v-if="show_flag[0]" key="0">
                        <p class="card-title" v-text="infoData.length ==0?'':infoData[0].contest.title"></p>
                        <div slot="extra">
                            <Tag type="dot" :color="status_color">
                                {{clock}}
                            </Tag>
                            <br>
                        </div>
                        <div style="width: 1200px;font-size: 15px;text-align: left;margin: 10px;padding: 7px">
                            <span v-html="infoData.length ==0?'':infoData[0].contest.describes"></span>
                        </div>
                        <Table :data="infoData" :columns="infoCol" stripe
                               :loading="infoTableLoading"></Table>
                    </Card>
                    <Card v-else-if="show_flag[1]" key="1">
                        <transition name="fade" mode="out-in">
                            <div v-if="anno_list_flag" key="first">
                                <div slot="title" style="height: 35px">
                                    <span class="card-title" style="float: left">Contest Announcements</span>
                                    <Button slot="extra" style="margin-left: 10px;float: right" @click.native="getAnnos" type="primary" :loading="anno_table_loading">
                                        Refresh
                                    </Button>
                                </div>
                                <div style="margin-top: 10px">
                                    <Row v-for="(value,index) in anno_data">
                                        <Col span="12">
                                            <div class="news-item item-name">
                                                <a @click="getNewsInfo(value.news.news_id)">{{value.news.title}}</a>
                                            </div>
                                        </Col>
                                        <Col span="5">
                                            <div class="news-item item-time">
                                                <Time :time="value.news.update_time" type="datetime"/>
                                            </div>
                                        </Col>
                                        <Col span="3">
                                            <div class="news-item item-creator">By {{value.user.username}}</div>
                                        </Col>
                                        <Divider/>
                                    </Row>
                                </div>
                                <Page :total="anno_total" :page-size="anno_per_page" show-sizer style="float: right;" @on-change="changeAnnoPage" @on-page-size-change="changeAnnoPageSize"/>
                                <br><br>
                            </div>
                            <div v-else key="second">
                                <div style="height: 35px;padding-top: 5px;text-align: left;font-size: 20px;margin: 0 0 25px 20px;">
                                    <Button icon="md-arrow-round-back" style="margin-right: 10px;float: right" :loading="loading"
                                            @click.native="back">Back
                                    </Button>
                                </div>
                                <div style="font-size: 20px;font-weight: 700;margin-bottom: 10px" v-text="anno_item.news.title"></div>
                                <div style="margin-bottom: 10px;">
                                    发布日期:
                                    <Time :time="anno_item.news.update_time" type="datetime"/>
                                    &nbsp;&nbsp;发布者:<span v-text="anno_item.user.username"></span>
                                </div>
                                <div class="simditor">
                                    <div class="simditor-markdown">
                                        <div class="simditor-body new-content">
                                            <div id="news-item-content" v-html="anno_item.news.content"></div>
                                        
                                        </div>
                                    </div>
                                </div>
                                <div style="text-align: right;">
                                    最后编辑于
                                    <Time :time="anno_item.news.update_time" type="datetime"/>
                                </div>
                            </div>
                        </transition>
                    </Card>
                    <Card v-else-if="show_flag[2]" key="2">
                        <p class="card-title">Problem List</p>
                        <div slot="extra">
                            <Row>
                                <Col span="10">
                                </Col>
                                <!--<Col span="6">
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
                                </Col>-->
                                <Col span="2">
                                    <Button style="margin-left: 10px;" :loading="problem_table_loading" @click.native="getProblems" type="primary">
                                        Refresh
                                    </Button>
                                </Col>
                            </Row>
                        </div>
                        <Table :data="problem_data" :columns="problem_col" stripe
                               :loading="problem_table_loading"></Table>
                    </Card>
                    <Card v-else-if="show_flag[3]" key="3">
                        <p class="card-title">Status</p>
                        
                        <div slot="extra">
                            <Row>
                                <!--<Col span="3" style="margin-top: 4px;margin-right: 30px">
                                    <i-switch v-model="onlyShowMe" size="large" @on-change="">
                                        <span slot="open">Mine</span>
                                        <span slot="close">All</span>
                                    </i-switch>
                                </Col>-->
                                <Col span="2" style="float: right;margin-right: 90px">
                                    <Button :loading="status_page_table_loading" @click.native="getSolutions" type="primary" style="margin-right: 60px;margin-left: 7px">
                                        Refresh
                                    </Button>
                                </Col>
                                <Col span="8" style="float: right;margin-right: 50px">
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
                            </Row>
                        </div>
                        <div style="margin-bottom: 2px;margin-top: 10px">
                            <Table :columns="status_col" :data="status_data" stripe :loading="status_page_table_loading"></Table>
                        </div>
                        <div style="float: right;margin: 5px;">
                            <Page :total="status_total" :page-size="status_per_page" show-sizer @on-change="changeStatusPage" @on-page-size-change="changeStatusPageSize"/>
                        </div>
                        <p style="margin-top: 30px"></p>
                    </Card>
                    <Card v-else-if="show_flag[4]" key="4"></Card>
                </transition>
            </Col>
            <Col span="3" style="margin-left: 10px">
                <Card>
                    <CellGroup style="text-align: left" @on-click="showContestCard">
                        <Cell title="OverView" name="home">
                            <Icon slot="icon" type="md-home" size="20"/>
                        </Cell>
                        <Cell title="Announments" name="anno" :disabled="infoCellDisabled">
                            <Icon slot="icon" type="ios-chatboxes" size="20"/>
                        </Cell>
                        <Cell title="Problems" name="problems" :disabled="infoCellDisabled">
                            <Icon slot="icon" type="ios-browsers" size="20"/>
                        </Cell>
                        <Cell title="Submissions" name="submit" :disabled="infoCellDisabled">
                            <Icon slot="icon" type="ios-create" size="20"/>
                        </Cell>
                        <Cell title="Rankings" name="rank" :disabled="infoCellDisabled">
                            <Icon slot="icon" type="ios-podium" size="20"/>
                        </Cell>
                    </CellGroup>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import Api from '../../components/api';
    import 'tar-simditor/styles/simditor.css'
    import 'tar-simditor-markdown/styles/simditor-markdown.css'

    export default {
        name: "Contest",
        data() {
            return {
                show_flag: [
                    true,//home_show_flag
                    false,//anno_show_flag
                    false,//problems_show_flag
                    false,//submit_show_flag
                    false,//rank_show_flag
                ],

                contest_id: this.$route.params.contest_id,
                infoTableLoading: false,
                apply_btn_loading: false,
                infoCol: [
                    {
                        title: "start_at",
                        align: "center",
                        render: (h, params) => {
                            return h("Time", {
                                props: {
                                    type: "datetime",
                                    time: params.row.contest.start_at,
                                }
                            });
                        }
                    },
                    {
                        title: "end_at",
                        align: "center",
                        render: (h, params) => {
                            return h("Time", {
                                props: {
                                    type: "datetime",
                                    time: params.row.contest.end_at,
                                }
                            });
                        }
                    },
                    {
                        title: "ContestType",
                        align: "center",
                        render: (h, params) => {
                            return h("span", {}, params.row.contest.privates == 0 ? "Private" : "Public")
                        }
                    },
                    {
                        title: "Creator",
                        align: "center",
                        render: (h, params) => {
                            return h("span", {}, params.row.created_by.nickname)
                        }
                    },
                    {
                        title: "Operate",
                        align: "center",
                        render: (h, params) => {
                            return h("Button", {
                                props: {
                                    type: "info",
                                    loading: this.apply_btn_loading,
                                    disabled: this.infoCellDisabled,
                                },
                                on: {
                                    click: () => {
                                        this.apply_btn_loading = true;
                                        Api.applyContestByContestId(this.contest_id, this.$store.state.token).then(res => {
                                            let result = res.data;
                                            if (result.code == 200) {
                                                this.$Message.success("Apply Success!");
                                            } else {
                                                this.$Message.error(result.message);
                                            }
                                            this.apply_btn_loading = false;
                                        }).catch(res => {
                                            console.log(res);
                                            this.apply_btn_loading = false;
                                        });
                                    },
                                }
                            }, "Apply");
                        }
                    },
                ],
                infoData: [],
                infoCellDisabled: true,
                restTime: 0,
                timer: null,

                anno_page: 1,
                anno_per_page: 10,
                anno_total: 1,
                anno_data: [],
                anno_table_loading: false,
                anno_list_flag: true,//T:列表 F:详细信息
                anno_item: null,//当前查看的公告

                onlyShowMe: false,
                status_txt: "all",
                status_page: 1,
                status_total: 1,
                status_per_page: 10,
                status_page_table_loading: false,
                //Dropdown
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

                status_col: [
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
                            return h('a', {}, params.row.solution.solution_id);
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
                            return h('a', {}, params.row.problem.title);
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
                                    to: "",
                                    // to: "/user-home" + params.row.user.username == null ? "" : "?username=" + params.row.user.username,
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
                status_data: [],
                statusRenderText: ["Pending", "Waiting", "Compiling", "Judging", "Accepted", "Presentation Error",
                    "Wrong Answer", "Time Limit Exceeded", "Merrory Limit Exceeded", "Output Limit Exceeded", "Runtime Error",
                    "Compile Error", "Partial Accepted", "System Error"],

                problem_page: 1,
                problem_per_page: 10,
                problem_data: [],
                problem_col: [
                    {
                        title: 'title',
                        key: 'title',
                        width: 600,
                        render: (h, params) => {
                            return h('router-link', {
                                style: {
                                    fontSize: '15px'
                                },
                                attrs: {
                                    // to: "/problem/" + params.row.problem_id,
                                    to: "/contest/" + this.contest_id + "/problem/" + params.row.problem_id,
                                },
                                on: {
                                    click: () => {

                                    }
                                },
                            }, params.row.title);
                        }
                    },
                    {
                        title: 'Total',
                        key: 'submit',
                    },
                    {
                        title: 'AC Rate',
                        key: 'ac_rate',
                        render: (h, params) => {
                            return h('Poptip', {
                                props: {
                                    trigger: 'hover',
                                    title: 'AC Rate',
                                    placement: 'bottom'
                                }
                            }, [
                                h('Tag', (params.row.accepted / (params.row.submit === 0 ? 1 : params.row.submit)).toFixed(2) * 100 + "%"),
                                h('div', {slot: 'content'}, [
                                    h('p', {
                                        style: {
                                            fontSize: '15px'
                                        }
                                    }, "AC:" + params.row.accepted),
                                    h('p', {
                                        style: {
                                            fontSize: '15px'
                                        }
                                    }, "Total:" + params.row.submit),
                                ]),
                            ]);
                        }
                    }
                ],
                problem_table_loading: false,
            }
        },
        methods: {
            //根据状态查询提交
            changeStatusState(state) {
                this.statusSelectedIndex = state;
                this.getSolutionsByState(state);
            },
            getSolutionsByState(state) {
                this.status_page_table_loading = true;
                Api.getSolutionsByState(state, 0, this.status_page, this.status_per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code === 200) {
                        this.status_data = result.data.solutions;
                    }
                    this.status_page_table_loading = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.status_page_table_loading = false;
                });
            },
            //获取竞赛信息
            getContest() {
                this.infoTableLoading = true;
                Api.findContestByContestId(this.contest_id).then(res => {
                    let result = res.data;
                    if (result.code == 200) {
                        this.infoData = [
                            result.data.contest,
                        ];
                        this.restTime = this.infoData[0].contest.end_at - (new Date()).getTime();
                        this.infoCellDisabled = this.infoData[0].contest.end_at < (new Date()).getTime();
                        this.setTimer();
                        // this.infoData = result.data.contest;

                    } else {
                        this.$Message.error(result.message);
                        console.log(result);
                    }
                    this.infoTableLoading = false;
                }).catch(res => {
                    console.log(res);
                    this.infoTableLoading = false;
                });
            },
            //设置倒计时
            setTimer() {
                if (this.timer != null) {
                    clearInterval(thit.timer);
                    this.timer = null;
                } else {
                    let that = this;
                    this.timer = setInterval(() => {
                        that.restTime = that.restTime - 1000;
                    }, 1000);
                }
            },
            //获取新闻信息
            getNewsInfo(id) {
                var that = this;
                for (var item of this.anno_data) {
                    if (item.news.news_id === id) {
                        that.anno_item = item;
                        break;
                    }
                }
                this.anno_list_flag = false;
            },
            back() {
                this.anno_list_flag = true;
            },
            //获取新闻
            getAnnos() {
                this.anno_table_loading = true;
                this.$Loading.start();
                Api.findAnnosByContestId(this.contest_id, this.anno_page, this.anno_per_page).then(res => {
                    let result = res.data;
                    if (result.code == 200) {
                        this.anno_data = result.data.newsLink;
                        this.anno_total = result.data.total;
                        this.$Loading.finish();
                    } else {
                        this.$Message.error(result.message);
                        this.$Loading.error();
                    }
                    this.anno_table_loading = false;
                }).catch(res => {
                    console.log(res);
                    this.$Loading.error();
                    this.anno_table_loading = false;
                });
            },
            //获取题目
            getProblems() {
                this.problem_table_loading = true;
                Api.findProblemsByContestId(this.contest_id).then(res => {
                    let result = res.data;
                    console.log(result);
                    if (result.code === 200) {
                        this.problem_data = result.data.results;
                        this.problem_table_loading = false;
                    } else {
                        console.log('Failed! ' + result.message);
                        this.problem_table_loading = false;
                    }
                }).catch(err => {
                    console.log('An error has occurred! ' + err);
                    this.problem_table_loading = false;
                });
            },
            //获取提交
            getSolutions() {
                this.status_page_table_loading = true;
                Api.getContestSolutions(this.contest_id, this.status_page, this.status_per_page).then(res => {
                    let result = res.data;
                    console.log(result);
                    if (result.code === 401) {
                        this.$Message.error("身份信息失效，请重新登录");
                    } else if (result.code == 200) {
                        this.status_data = result.data.solutions;
                        // this.status_total = result.data.total;
                    }
                    this.status_page_table_loading = false;
                }).catch(res => {
                    this.$Message.error(res.message);
                    this.status_page_table_loading = false;
                });
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
            //修改当前显示信息
            showContestCard(name) {
                if (this.infoCellDisabled) {
                    return;
                }
                switch (name) {
                    case "home": {
                        this.changeShowCard(0);
                        break;
                    }
                    case "anno": {
                        this.changeShowCard(1);
                        this.getAnnos();
                        break;
                    }
                    case "problems": {
                        if (this.infoData[0].contest.start_at > (new Date()).getTime()) {
                            this.$Message.warning("Contest haven't started!");
                            return;
                        } else {
                            this.changeShowCard(2);
                            this.getProblems();
                            break;
                        }
                    }
                    case "submit": {
                        if (this.infoData[0].contest.start_at > (new Date()).getTime()) {
                            this.$Message.warning("Contest haven't started!");
                            return;
                        } else {
                            this.changeShowCard(3);
                            this.getSolutions();
                            break;
                        }
                    }
                    case "rank": {
                        this.changeShowCard(4);
                        break;
                    }
                }
            },
            //显示卡片
            changeShowCard(inx) {
                this.show_flag = [
                    false,
                    false,
                    false,
                    false,
                    false,
                ];
                this.show_flag[inx] = true;
            },
            //修改公告页码
            changeAnnoPage(page) {
                this.anno_page = page;
                this.getAnnos();
            },
            //修改公告每页数量
            changeAnnoPageSize(pageSize) {
                this.anno_per_page = pageSize;
                this.getAnnos();
            },
            //修改提交页码
            changeStatusPage(page) {
                this.status_page = page;
                this.getSolutions();
            },
            //修改提交每页数量
            changeStatusPageSize(pageSize) {
                this.status_per_page = pageSize;
                this.getSolutions();
            }
        },
        computed: {
            //时钟
            clock: function () {
                if (this.infoData.length == 0) {
                    return "Finished";
                } else {
                    let item = this.infoData[0].contest;
                    let date = new Date().getTime();
                    if (item.start_at > date) {
                        return "Unstarted";
                    } else if (item.end_at > date) {
                        let now = this.restTime;
                        let h = Math.floor(now / 3600000);
                        now = now - h * 3600000;
                        let m = Math.floor(now / 60000);
                        now = now - m * 60000;
                        let s = Math.floor(now / 1000);
                        return h + ":" + m + ":" + s;
                    } else {
                        return "Finished";
                    }
                }

            },
            //状态颜色
            status_color: function () {
                if (this.infoData.length == 0) {
                    return "error";
                } else {
                    let item = this.infoData[0].contest;
                    let date = new Date().getTime();
                    if (item.start_at > date) {
                        return "info";
                    } else if (item.end_at > date) {
                        return "success";
                    } else {
                        return "error";
                    }
                }
            }
        },
        mounted() {
            this.getContest();
        },
        beforeDestroy() {
            if (this.timer) {
                clearInterval(this.timer);// 在Vue实例销毁前，清除我们的定时器
            }
        },

    }
</script>

<style scoped>
    
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
    {
        opacity: 0;
    }
    
    .card-content {
        margin: 10px 0 0 50px;
    }
    
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
    
    span.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
    
    div.new-content {
        text-align: left;
        /*padding: 15px 50px 0 50px;*/
        background: #f3f3f3;
    }
</style>