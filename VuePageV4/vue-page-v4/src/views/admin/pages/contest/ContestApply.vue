<template>
    <div>
        <Card style="margin: 15px">
        </Card>
        <transition name="fade" mode="out-in">
            <div v-if="location_flag" key="first">
                <Card style="margin: 15px">
                    <div slot="title" style="height: 30px;padding-top: 5px">
                        <span style="font-size: 25px;font-weight: 400;">Contests</span>
                    </div>
                    <div slot="extra">
                        <Button icon="md-refresh" style="float: right;margin-right: 20px"
                                @click.native="getContests">Refresh
                        </Button>
                        <br><br>
                    </div>
                    <Table :columns="contest_col" :data="contest_data" :loading="contest_table_loading"></Table>
                </Card>
            </div>
            <div v-else key="second">
                <Card style="margin: 15px">
                    <div slot="title" style="height: 30px;padding-top: 5px">
                        <span style="font-size: 25px;font-weight: 400;">Apply</span>
                    </div>
                    <div slot="extra">
                        <Button icon="md-arrow-round-back" style="float: right;margin-right: 20px"
                                @click.native="Back">Back
                        </Button>
                        <br><br>
                    </div>
                    <Table :columns="apply_col" :data="apply_data"></Table>
                </Card>
            </div>
        </transition>
    
    </div>
</template>

<script>
    import Api from "../../components/api"

    let _this;
    export default {
        name: "ContestApply",
        data() {
            return {
                location_flag: true,//true:contest  false:apply
                contest_table_loading: false,//竞赛表加载标记
                contest_data: [],
                contest_col: [
                    {
                        title: "Contest Id",
                        align: "center",
                        key: "contest.contest_id",
                        render: (h, params) => {
                            return h("span", {}, params.row.contest.contest_id);
                        }
                    },
                    {
                        title: "Contest Name",
                        align: "center",
                        key: "contest.title",
                        render: (h, params) => {
                            return h("span", {}, params.row.contest.title);
                        }
                    },
                    {
                        title: "Number",
                        align: "center",
                        render: (h, params) => {
                            return h("Progress", {
                                props: {
                                    status: "active",
                                    "hide-info": false,
                                    "success-percent": 0,
                                    percent: (params.row.contest.now / params.row.contest.max) * 100,
                                }
                            })
                        }
                    },
                    {
                        title: "View",
                        align: "center",
                        render: (h, params) => {
                            return h("Badge", {
                                props: {
                                    count: params.row.apply_number,
                                    type: "info",
                                    "overflow-count": 999,
                                    offset: [10, 0],
                                }
                            }, [
                                h("Button", {
                                    props: {
                                        size: "large",
                                    },
                                    on: {
                                        click: () => {
                                            Api.getContestApplyByContestId(params.row.contest.contest_id, this.$store.state.token).then(res => {
                                                let result = res.data;
                                                if (result.code === 200) {
                                                    this.location_flag = false;
                                                    this.apply_data = result.data.contestApplys;
                                                } else {
                                                    this.$Message.error(result.message);
                                                }
                                            }).catch(res => {
                                                console.log(res);
                                            });
                                        }
                                    },
                                }, "View"),
                            ])
                        }
                    },
                ],
                apply_col: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: "userId",
                        align: "center",
                        key: "user.user_id",
                        render: (h, params) => {
                            return h("span", {}, params.row.user.user_id);
                        }
                    },
                    {
                        title: "username",
                        align: "center",
                        key: "user.username",
                        render: (h, params) => {
                            return h("span", {}, [
                                h("a", {
                                    on: {
                                        click: () => {
                                            alert(666);
                                        }
                                    }
                                }, params.row.user.username)
                            ]);
                        }
                    },
                    {
                        title: "apply time",
                        align: "center",
                        key: "contestApply.apply_time",
                        render: (h, params) => {
                            return h("Time", {
                                props: {
                                    type: "datetime",
                                    time: params.row.contestApply.apply_time,
                                }
                            });
                        }
                    },
                    {
                        title: "status",
                        sortable: true,
                        align: "center",
                        filters: [
                            {
                                label: 'waitting',
                                value: 0
                            },
                            {
                                label: 'joined',
                                value: 1
                            },
                            {
                                label: 'rejected',
                                value: 2
                            }
                        ],
                        key: "contestApply.status",
                        render: (h, params) => {
                            return h("Tag", {
                                props: {
                                    color: params.row.contestApply.status == 0 ? "default" :
                                        params.row.contestApply.status == 1 ? "success" : "error",
                                }
                            }, params.row.contestApply.status == 0 ? "waitting" :
                                params.row.contestApply.status == 1 ? "joined" : "rejected");
                        },
                        filterMethod(value, row) {
                            return row.contestApply.status == value;
                        }
                    },
                    {
                        title: "操作",
                        align: "center",
                        render: (h, params) => {
                            return h("div", {}, [
                                h("Button", {
                                    props: {
                                        type: "success",
                                        icon: "md-checkmark",
                                        disabled: params.row.contestApply.status == 1,
                                    },
                                    style: {
                                        marginRight: "10px",
                                    },
                                    on: {
                                        click: () => {
                                            Api.updateContestApplyStatus(params.row.contestApply.id, 1, this.$store.state.token).then(res => {
                                                let result = res.data;
                                                if (result.code === 200) {
                                                    params.row.contestApply.status = 1;
                                                    this.$Message.success("Accepted!");
                                                } else {
                                                    this.$Message.error(result.message);
                                                }
                                            }).catch(res => {
                                                console.log(res);
                                            });
                                        }
                                    }
                                }, "Agree"),
                                h("Button", {
                                    props: {
                                        icon: "md-close",
                                        type: "error",
                                        disabled: params.row.contestApply.status == 2,
                                    },
                                    on: {
                                        click: () => {
                                            Api.updateContestApplyStatus(params.row.contestApply.id, 2, this.$store.state.token).then(res => {
                                                let result = res.data;
                                                if (result.code === 200) {
                                                    params.row.contestApply.status = 2;
                                                    this.$Message.success("Rejected!");
                                                } else {
                                                    this.$Message.error(result.message);
                                                }
                                            }).catch(res => {
                                                console.log(res);
                                            });
                                        }
                                    }
                                }, "Refuse"),
                                /*h("Button", {
                                    props: {
                                        icon: "ios-cut",
                                        type: "error",
                                        disabled: params.row.contestApply.status != 0,
                                    },
                                    on: {
                                        click: () => {

                                        }
                                    }
                                }, "Refuse"),*/
                            ]);
                        },
                    }
                ],
                apply_data: [
                    /*{
                        "contest": {
                            "access": true,
                            "contest_id": 18,
                            "describes": "<p>为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>",
                            "end_at": 1558627200000,
                            "max": 2000,
                            "now": 0,
                            "organizer": "教育部高等学校计算机科学与技术教学指导委员会",
                            "privates": 1,
                            "start_at": 1557417600000,
                            "title": "蓝桥杯",
                            "user_id": 12
                        },
                        "contestApply": {
                            "contest_id": 18,
                            "id": 6,
                            "status": false,
                            "user_id": 9
                        },
                        "user": {
                            "access_time": 1516101502000,
                            "disabled": false,
                            "email": "zhizhiyun@cloud.com",
                            "last_login": 1550797302000,
                            "nickname": "吱吱云",
                            "passwd": "5cba13819e624f8dc0a991a7691f3f82",
                            "remark": null,
                            "school": "hyit",
                            "session_id": "CDB1067445E86544AF9671D9580E56D2",
                            "solved": 0,
                            "submit": 0,
                            "user_id": 9,
                            "username": "cloud"
                        }
                    }*/
                ],

            }
        },
        methods: {
            //返回
            Back() {
                this.location_flag = true;
                this.getContests();
            },
            //查询竞赛
            getContests() {
                this.contest_table_loading = true;
                Api.getContestByCreator(this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.contest_data = result.data.contests;
                        this.getApplyNums();
                    } else {
                        this.$Message.error(result.message);
                    }
                    this.contest_table_loading = false;
                }).catch(res => {
                    console.log(res);
                    this.contest_table_loading = false;
                });
            },
            //查询竞赛申请人数
            getApplyNums() {
                this.contest_data.forEach(function (item, index) {
                    Api.getApplyNumsByContestId(item.contest.contest_id, _this.$store.state.token).then(res => {
                        let result = res.data;
                        // console.log(result);
                        if (result.code === 200) {
                            item.apply_number = result.data.apply_number;
                        } else {
                            item.apply_number = 0;
                        }
                    }).catch(res => {
                        console.log(res);
                    });
                });
            },
            //同意申请
            AgreeApplication() {

            },
            //拒绝申请
            RefushApplication() {

            }
        },
        mounted() {
            this.getContests();
        },
        created() {
            _this = this;
        }
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
</style>