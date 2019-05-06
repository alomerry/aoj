<template>
    <div>
        <Row>
            <Col span="20" style="margin-right: 10px;margin-left: 30px">
                <Card v-show="show_flag[0]">
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
                <Card v-show="show_flag[1]">
                    
                    <p class="card-title">Contest Announcements</p>
                    <div slot="extra">
                        <Row>
                            <Col span="2">
                                <Button style="margin-left: 10px;" @click.native="" type="primary">
                                    Refresh
                                </Button>
                            </Col>
                        </Row>
                    </div>
                </Card>
                <Card v-show="show_flag[2]"></Card>
                <Card v-show="show_flag[3]"></Card>
                <Card v-show="show_flag[4]"></Card>
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
                        <Cell title="Problems" name="problem" :disabled="infoCellDisabled">
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
                ],
                infoData: [],
                infoCellDisabled: true,
                restTime: 0,
                timer: null,
            }
        },
        methods: {
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
            //获取新闻
            getAnnos() {
            
            },
            //修改当前显示信息
            showContestCard(name) {
                switch (name) {
                    case "home": {
                        this.changeShowCard(0);
                        break;
                    }
                    case "anno": {
                        this.changeShowCard(1);
                        break;
                    }
                    case "problems": {
                        this.changeShowCard(2);
                        break;
                    }
                    case "submit": {
                        this.changeShowCard(3);
                        break;
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
    .card-content {
        margin: 10px 0 0 50px;
    }
    
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
</style>