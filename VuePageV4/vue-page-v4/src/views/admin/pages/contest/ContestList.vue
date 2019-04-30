<template>
    <div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Contests</span>
            
            </div>
            <div slot="extra">
                <i-input v-model="searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
                <Button icon="md-refresh" style="float: right;margin-right: 20px"
                        @click.native="getContests"></Button>
                <br><br>
            </div>
            <Table ref="selection"
                   :columns="columns"
                   :data="selectData"
                   :loading="tableLoadingFlag"></Table>
            <br>
            <Page :current="page" :page-size="per_page" size="small" style="float: right"/>
            <br>
        </Card>
        
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px">
            </div>
        </Card>
    </div>
</template>

<script>
    import debounce from "lodash/debounce"
    import Api from "../../components/api"

    let _this;

    export default {
        name: "ContestList",
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                deleteShowFlag: false,
                tableLoadingFlag: false,
                columns: [
                    {
                        type: 'expand',
                        width: 60,
                        align: 'center',
                        render: (h, params) => {
                            return h("div", {}, [
                                h("p", {}, "已参赛：" + params.row.contest.now + "人"),
                                h("p", {}, "可参赛：" + (params.row.contest.max - params.row.contest.now) + "人"),
                                h("p", {}, "组织者：" + params.row.contest.organizer),
                                h("p", {}, "开始时间：" + _this.LongFormatToDateString(params.row.contest.start_at)),
                                h("p", {}, "结束时间：" + _this.LongFormatToDateString(params.row.contest.end_at)),
                            ])
                        }
                    },
                    {
                        title: 'ContestId',
                        key: 'contest.contest_id',
                        render: (h, params) => {
                            return h('span', {}, params.row.contest.contest_id)
                        },
                    },
                    {
                        title: 'Author',
                        key: 'created_by.username',
                        render: (h, params) => {
                            return h('span', {}, params.row.created_by.username);
                        }
                    },
                    {
                        title: 'Title',
                        key: 'contest.title',
                        render: (h, params) => {
                            return h('span', {}, params.row.contest.title)
                        },
                    },
                    {
                        //状态：可报名(access=1),待开始(access=0),
                        //     进行中(access=0,startTime<=nowTime<=endTime)
                        //     已结束(access=0,nowTime>endTime)
                        title: 'Status',
                        key: 'contest.access',
                        width: 200,
                        render: (h, params) => {
                            return h('Badge', {
                                props: {
                                    type: params.row.contest.access ? "primary" :
                                        (params.row.contest.start_at <= this.nowTime) && (this.nowTime <= params.row.contest.end_at) ? "success" :
                                            (this.nowTime > params.row.contest.end_at) ? "error" : "warning",
                                    text: params.row.contest.access ? "可报名" :
                                        (params.row.contest.start_at <= this.nowTime) && (this.nowTime <= params.row.contest.end_at) ? "进行中" :
                                            (this.nowTime > params.row.contest.end_at) ? "已结束" : "待开始",
                                    offset: [10, -15]
                                }
                            }, null)
                        },
                    },
                    {
                        title: 'Defunct',
                        width: 200,
                        key: 'contest.privates',//0=隐私，1=公开
                        render: (h, params) => {
                            return h('Badge', {
                                style: {
                                    fontSize: '24px',
                                },
                                props: {
                                    type: params.row.contest.privates > 0 ? "success" : "warning",
                                    text: params.row.contest.privates > 0 ? "public" : "private",
                                    offset: [10, -15]
                                }
                            }, null)
                        },
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        // width: 250,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h("Tooltip",
                                    {
                                        props: {
                                            placement: "top",
                                            theme: "light",
                                            content: "修改"
                                        }
                                    },
                                    [
                                        h('Button',
                                            {
                                                props: {
                                                    type: 'default',
                                                    size: 'large',
                                                },
                                                style: {
                                                    marginRight: '5px'
                                                },
                                                on: {
                                                    click: () => {
                                                        // this.$router.push({path: `/admin/problem/edit/${params.row.problem.problem_id}`});
                                                    }
                                                },
                                            },
                                            [
                                                h('Icon',
                                                    {
                                                        props: {
                                                            type: "ios-create",
                                                            size: 17,
                                                            // color: "#e1a331"
                                                        }
                                                    }
                                                )
                                            ]
                                        )
                                    ]
                                ),
                                h("Tooltip",
                                    {
                                        props: {
                                            placement: "top",
                                            theme: "light",
                                            content: "题目"
                                        }
                                    },
                                    [
                                        h('Button',
                                            {
                                                props: {
                                                    type: 'default',
                                                    size: 'large',
                                                    to: '/admin/contest/' + params.row.contest.contest_id + '/problems',
                                                },
                                                style: {
                                                    marginRight: '5px'
                                                },
                                                on: {
                                                    click: () => {
                                                        // this.remove(params.index)
                                                    }
                                                }
                                            },
                                            [
                                                h('Icon',
                                                    {
                                                        props: {
                                                            type: "ios-grid",
                                                            size: 17,
                                                            color: "#00e1d4"
                                                        }
                                                    }
                                                ),
                                            ]
                                        )
                                    ]
                                ),
                                h("Tooltip",
                                    {
                                        props: {
                                            placement: "top",
                                            theme: "light",
                                            content: "公告"
                                        }
                                    },
                                    [
                                        h('Button',
                                            {
                                                props: {
                                                    type: 'default',
                                                    size: 'large',
                                                    to: '/admin/contest/' + params.row.contest.contest_id + '/announcement',
                                                },
                                                style: {
                                                    marginRight: '5px'
                                                },
                                                on: {
                                                    click: () => {
                                                        // this.remove(params.index)
                                                    }
                                                }
                                            },
                                            [
                                                h('Icon',
                                                    {
                                                        props: {
                                                            type: "md-information-circle",
                                                            size: 17,
                                                            color: "#e1a331"
                                                        }
                                                    }
                                                ),
                                            ]
                                        )
                                    ]),
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "下载提交"
                                    }
                                }, [
                                    h('Button', {
                                        props: {
                                            type: 'default',
                                            size: 'large',
                                        },
                                        style: {
                                            marginRight: '5px'
                                        },
                                        on: {}
                                    }, [
                                        h('Icon', {
                                            props: {
                                                type: "ios-download",
                                                size: 17,
                                                color: "#29d87c"
                                            }
                                        }),
                                    ])
                                ]),
                            ]);
                        }
                    }
                ],
                datas: [],
                selectData: [
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
                nowTime: "2016-10-03 07:08:16",
                timer: null,//定时器修改nowtime
                page: 1,
                per_page: 10,
            }
        },
        created: function () {
            _this = this;
            this.getContests();
            this.selectData = this.datas;//表格数据
            this.debouncedsearchData = debounce(this.searchData, 500, null);//延时加载
        },
        mounted() {
            this.timer = setInterval(() => {
                _this.nowTime = (new Date()).getTime(); // 修改数据date当前时间
            }, 1000);
        },
        beforeDestroy() {
            if (this.timer) {
                clearInterval(this.timer);// 在Vue实例销毁前，清除我们的定时器
            }
        },
        methods: {
            //搜索查询表格
            searchData: function () {
                console.log("开始查询:" + this.searchKeyWord);
                if (this.searchKeyWord === "") {
                    this.selectData = this.datas;
                } else {
                    this.selectData = [];
                    let that = this;
                    this.datas.forEach(function (is, i) {
                        let item = that.datas[i];
                        if (item.username.indexOf(that.searchKeyWord) !== -1 || item.nickname.indexOf(that.searchKeyWord) !== -1) {
                            that.selectData.push(item);
                        }
                    });
                }
            },
            //字符串转date
            stringFormatToDate: function (str) {
                let time = new Date();
                //2018-08-08 09:09:09
                let y = str.substring(0, 4), m = str.substring(5, 7), d = str.substring(8, 10);
                let h = str.substring(11, 13), m_ = str.substring(14, 16), s = str.substring(17, 19);
                console.log(y + "-" + m + "-" + d + " " + h + ":" + m_ + ":" + s);
                time.setFullYear(y, m, d);
                time.setHours(h, m_, s, 0);
                return time;
            },
            //字符串转date转string
            LongFormatToDateString: function (long) {
                let date = new Date(long);
                let month = (date.getMonth() + 1);
                let day = date.getDate();
                let hour = date.getHours();
                let min = date.getMinutes();
                if (month < 10) {
                    month = "0" + month;
                }
                if (day < 10) {
                    day = "0" + day;
                }
                if (hour < 10) {
                    hour = "0" + hour;
                }
                if (min < 10) {
                    min = "0" + min;
                }
                return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + min;
            },
            //查询比赛集
            getContests: function () {
                this.tableLoadingFlag = true;
                // console.log("刷新");
                Api.getContestsByPagePer_PageAndCreator(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 200) {
                        this.datas = result.data.contests;
                        this.selectData = this.datas;
                    } else if (result.code === 401) {
                        this.$Message.error("签名过期,请重新登录!");
                        window.location.replace("/admin/login");
                    }
                    this.tableLoadingFlag = false;
                }).catch(res => {
                    this.tableLoadingFlag = false;
                });
            }
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            }
        }
    }
</script>

<style scoped>
</style>