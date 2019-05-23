<template>
    <div class="bg">
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px;padding-top: 10px">
                <span style="font-size: 19px;font-weight: 400;" v-text="method"></span>
                <br><br>
            </div>
            <Form :model="formContest">
                
                <div class="index">
                    <!-- Title -->
                    <div class="contest-eidt contest-eidt-titles">
                        <div class="contest-info-item">
                            <Row>
                                <Col span="4">
                                    Title
                                </Col>
                            </Row>
                        </div>
                        <div class="contest-info-item">
                            <Row>
                                <Col span="7">
                                    <FormItem label="">
                                        <Input size="large" placeholder="title" v-model="formContest.title"/>
                                    </FormItem>
                                </Col>
                            </Row>
                        </div>
                    </div>
                    <!-- Description -->
                    <div class="contest-eidt contest-eidt-discribe">
                        <div class="contest-info-item">
                            Description
                        </div>
                        <div class="contest-info-item">
                            <FormItem>
                                <Simditor v-model="formContest.describes">
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Info -->
                    <div class="contest-eidt contest-eidt-discribe">
                        <div class="contest-info-item">
                            <Row>
                                <Col span="5">Start Time</Col>
                                <Col span="5" offset="1">End Time</Col>
                                <Col span="3" offset="1">Accessable</Col>
                                <Col span="3" offset="1">Max</Col>
                                <Col span="3" offset="1" v-if="contest_id!=null">Visible</Col>
                                <!--<Col span="5" offset="1">Status</Col>-->
                            </Row>
                        </div>
                        <div class="contest-info-item">
                            <Row>
                                <FormItem>
                                    <Col span="5">
                                        <DatePicker v-model="formContest.start_at" size="large" type="datetime"
                                                    placeholder="Select date" :options="startTimeLimit"></DatePicker>
                                    </Col>
                                    <Col span="5" offset="1">
                                        <DatePicker v-model="formContest.end_at" size="large" type="datetime"
                                                    placeholder="Select date" :options="endTimeLimit"></DatePicker>
                                    </Col>
                                    <Col span="3" offset="1">
                                        <i-switch size="default" v-model="formContest.access">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <Col span="3" offset="1">
                                        <Input placeholder="参赛人数" v-model="formContest.max"></Input>
                                    </Col>
                                    <Col span="3" offset="1" v-if="contest_id!=null">
                                        <i-switch size="default" true-value='1' false-value='0' v-model="formContest.privates">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <!--<Col span="5" offset="1">-->
                                    <!--<Badge type="success" text="Accessable"></Badge>-->
                                    <!--</Col>-->
                                </FormItem>
                            </Row>
                        </div>
                    </div>
                    <!-- Save -->
                    <div class="contest-eidt contest-eidt-testcase">
                        <div class="problem-info-item">
                            Organizer
                        </div>
                        <FormItem>
                            <label>
                                <Input type="text" title="" v-model="formContest.organizer"/>
                            </label>
                        </FormItem>
                        <Button type="primary" @click="save" :loading="loadingStatus">Save</Button>
                    </div>
                </div>
            </Form>
        </Card>
    </div>
</template>

<script>
    import Simditor from '../../components/Simditor'
    import Api from "../../components/api"

    export default {
        name: "ContestEdit",
        data() {
            return {
                method: null,//Edit模式/New模式
                file_testCase: null,
                loadingStatus: false,//上传状态
                content: '',
                contest_id: null,
                formContest: {
                    "access": false,
                    "contest_id": null,
                    "describes": null,
                    "end_at": null,
                    "max": 0,
                    "now": 0,
                    "organizer": null,
                    "privates": 0,//默认隐私，新建后才能修改
                    "start_at": null,
                    "title": null,
                    "user_id": null,
                },
                startTimeLimit: {
                    disabledDate(date) {
                        return date && date.valueOf() < Date.now();
                    }
                },
                endTimeLimit: {
                    disabledDate(date) {
                        return date && date.valueOf() < Date.now();
                    }
                }
            }
        },
        components: {
            Simditor,
        },
        mounted() {
            if (this.$route.params.contest_id != null) {
                this.method = "Edit Contest";
                Api.getContestByContestId(this.$route.params.contest_id, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code == 200) {
                        this.formContest = result.data.contest;
                        this.formContest.privates += "";
                        this.formContest.start_at = this.formatLongToDate(this.formContest.start_at);
                        this.formContest.end_at = this.formatLongToDate(this.formContest.end_at);
                    } else {
                        this.$Message.error(result.message);
                    }
                }).catch(res => {
                    console.log(res);
                });

            } else {
                this.method = "Add Contest";
            }
        },
        methods: {
            //long转date
            formatLongToDate(longDataTime) {
                let date = new Date();
                date.setTime(longDataTime);
                return date;
            },
            //标准化date转为 yyyy-mm-dd hh:mm:ss
            formatDate(date) {
                const y = date.getFullYear();

                let m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;

                let d = date.getDate();
                d = d < 10 ? ('0' + d) : d;

                let h = date.getHours();
                h = h < 10 ? ('0' + h) : h;

                let mm = date.getMinutes();
                mm = mm < 10 ? ('0' + mm) : mm;

                let s = date.getSeconds();
                s = s < 10 ? ('0' + s) : s;
                return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s;
            },
            //save
            save() {
                if (this.contest_id == null) {
                    this.createContest();
                } else {
                    this.updateContest();
                }
            },
            //更新竞赛
            updateContest() {
                if (this.formContest.max === null || this.formContest.max === "" || this.formContest.max === 0) {
                    this.$Message.error("Please input max number!");
                    return;
                } else if (this.formContest.title === null || this.formContest.title === "") {
                    this.$Message.error("Please input title!");
                    return;
                } else if (this.formContest.describes === null || this.formContest.describes === "") {
                    this.$Message.error("Please input describe!");
                    return;
                } else if (this.formContest.end_at === null || this.formContest.start_at === null) {
                    this.$Message.error("Please input start time or end time!");
                    return;
                } else if (this.formContest.end_at <= this.formContest.start_at) {
                    this.$Message.error("End time can early than start time!");
                    return;
                }
                this.loadingStatus = true;
                this.$Loading.start();
                Api.updateContest(this.formContest, this.contest_id, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.$Message.success("Update contest successed!");
                        this.$Loading.finish();
                    } else {
                        this.$Message.error(result.message);
                        this.$Loading.error();
                    }
                    this.loadingStatus = false;
                }).catch(res => {
                    console.log(res);
                    this.$Loading.error();
                    this.loadingStatus = false;
                });

            },
            //新建竞赛
            createContest() {
                if (this.formContest.max === null || this.formContest.max === "" || this.formContest.max === 0) {
                    this.$Message.error("Please input max number!");
                    return;
                } else if (this.formContest.title === null || this.formContest.title === "") {
                    this.$Message.error("Please input title!");
                    return;
                } else if (this.formContest.describes === null || this.formContest.describes === "") {
                    this.$Message.error("Please input describe!");
                    return;
                } else if (this.formContest.end_at === null || this.formContest.start_at === null) {
                    this.$Message.error("Please input start time or end time!");
                    return;
                } else if (this.formContest.end_at <= this.formContest.start_at) {
                    this.$Message.error("End time can early than start time!");
                    return;
                }
                this.loadingStatus = true;
                this.$Loading.start();
                Api.createNewContest(this.formContest, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.$Message.success("add contest successed!");
                        this.$Loading.finish();
                    } else {
                        this.$Message.error(result.message);
                        this.$Loading.error();
                    }
                    this.loadingStatus = false;
                }).catch(res => {
                    console.log(res);
                    this.$Loading.error();
                    this.loadingStatus = false;
                });
            }
        },
        created() {
        },
        beforeRouteEnter(to, from, next) {
            if (to.params.contest_id == null) {
                next(vm => {
                    vm.contest_id = null;
                    vm.formContest = {
                        "access": false,
                        "contest_id": null,
                        "describes": null,
                        "end_at": null,
                        "max": 0,
                        "now": 0,
                        "organizer": null,
                        "privates": 1,//默认公开，新建后才能修改
                        "start_at": null,
                        "title": null,
                        "user_id": null,
                    }
                });
            } else {
                next(vm => {
                    vm.contest_id = to.params.contest_id;
                });

            }
        },
    }
</script>

<style scoped>
    .contest-info-item {
        padding: 5px;
        margin-bottom: 5px;
        font-size: 17px;
    }
    
    .contest-eidt {
        margin-bottom: 15px;
    }
    
    .ivu-form-item {
        /*margin: 24px;*/
        padding: 6px 0 6px 0;
    }
    
    .ivu-switch-checked {
        border-color: #19be6b;
        background-color: #19be6b;
    }
    
    .ivu-switch {
        /*border: 1px solid rgb(255, 73, 73);
        background-color: rgb(255, 73, 73);*/
    }
    
    .bg {
        background-color: #edecec;
    }
</style>