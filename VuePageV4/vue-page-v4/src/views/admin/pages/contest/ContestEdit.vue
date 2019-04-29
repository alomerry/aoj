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
                                <Col span="3" offset="1">Visible</Col>
                                <Col span="5" offset="1">Max</Col>
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
                                        <i-switch size="default" v-model="formContest.defunct">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <Col span="3" offset="1">
                                        <Input placeholder="参赛人数" v-model="formContest.max"></Input>
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
                formContest: {
                    "access": false,
                    "contest_id": null,
                    "describes": null,
                    "end_at": null,
                    "max": 0,
                    "now": 0,
                    "organizer": null,
                    "privates": 0,
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
            console.log("mounted:$route.params:" + this.$route.params.method);
            switch (this.$route.params.method) {
                case "edit": {
                    //search,init formContest's problem and tag
                    this.method = "Edit Contest";

                    break;
                }
                case "create": {
                    this.method = "Add Contest";
                    break;
                }
            }
        },
        methods: {
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
                this.createContest();
            },
            //初始化显示
            //TODO 修改
            initFormDataWithProblemAndTag(problem, tag) {
                if (problem == null) {
                    this.formContest = {}
                } else {
                    this.formContest = {}
                }
            },
            //新建竞赛
            createContest() {
                if (this.formContest.max === null || this.formContest.max === "" || this.formContest.max ===0) {
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
                Api.createNewContest(this.formContest, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.$Message.success("add contest successed!");
                    } else {
                        this.$Message.error(result.message);
                    }
                }).catch(res => {
                    console.log(res);
                });
            }
        },
        created() {
        },
        beforeRouteEnter(to, from, next) {
            if (to.path === "/admin/contest/create") {
                console.log("reload");
                next(vm => {
                    vm.method = "Add Contest";
                });
            } else {
                next();
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