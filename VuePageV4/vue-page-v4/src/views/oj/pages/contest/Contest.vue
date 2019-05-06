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
                                <Page :total="10" :current="anno_page" show-sizer style="float: right;"/>
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
                    <Card v-else-if="show_flag[2]" key="2"></Card>
                    <Card v-else-if="show_flag[3]" key="3"></Card>
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

                anno_page: 1,
                anno_per_page: 10,
                anno_data: [],
                anno_table_loading: false,
                anno_list_flag: true,//T:列表 F:详细信息
                anno_item: null,//当前查看的公告
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
            },
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