<template>
    <div>
        <Row>
            <Col span="20" style="margin-right: 10px;margin-left: 30px">
                <Card>
                    <p class="card-title" v-text="infoData.length ==0?'':infoData[0].contest.title"></p>
                    <div slot="extra">
                        <Row>
                            <Col span="2">
                                <Tag type="dot" style="margin-left: 10px;" color="warning">
                                    <Time interval="1" type="relative" :time="restTime"></Time>
                                </Tag>
                            </Col>
                        </Row>
                    </div>
                    <div style="width: 1200px;font-size: 15px;text-align: left;margin: 10px;padding: 7px">
                        <span v-html="infoData .length ==0?'':infoData[0].contest.describes"></span>
                    </div>
                    <Table :data="infoData" :columns="infoCol" stripe
                           :loading="infoTableLoading"></Table>
                </Card>
                <!--<div style="margin:25px 10px 10px 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="totalPage" :current="page" :page-size="per_page" show-sizer show-elevator
                              @on-change=""></Page>
                    </div>
                </div>-->
            </Col>
            <!--<Col span="3">
                <Card>
                    <p slot="title" style="font-size: 18px;">Tag</p>
                    <div>
                        <Tag :color="tag_color[(item.tag_id%tag_color.length)]" v-for="(item) in tag_list" @click.native="getProblemsByTag(item.tag_id)">{{item.tagname}}</Tag>
                    </div>
                    <Button long ghost type="info" @click.native="" style="margin-top: 15px">Pick One</Button>
                </Card>
            </Col>-->
        </Row>
    </div>
</template>

<script>
    import Api from '../../components/api';

    export default {
        name: "Contest",
        data() {
            return {
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
                restTime: 0,
            }
        },
        methods: {
            getContest() {
                this.infoTableLoading = true;
                Api.findContestByContestId(this.contest_id).then(res => {
                    let result = res.data;
                    if (result.code == 200) {
                        this.infoData = [
                            result.data.contest,
                        ];
                        this.restTime = this.infoData[0].contest.end_at - (new Date()).getTime();
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
        },
        computed: {},
        mounted() {
            this.getContest();
        }
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