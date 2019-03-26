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
                                        <Input size="large" placeholder="large size" v-model="formContest.title"/>
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
                                <Simditor>
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Limits -->
                    <div class="contest-eidt contest-eidt-discribe">
                        <div class="contest-info-item">
                            <Row>
                                <Col span="5">Start Time</Col>
                                <Col span="5" offset="1">End Time</Col>
                                <Col span="5" offset="1">Visible</Col>
                                <Col span="5" offset="1">Status</Col>
                            </Row>
                        </div>
                        <div class="contest-info-item">
                            <Row>
                                <FormItem>
                                    <Col span="5">
                                        <DatePicker v-model="start_at" size="large" type="datetime" placeholder="Select date" :options="startTimeLimit"></DatePicker>
                                    </Col>
                                    <Col span="5" offset="1">
                                        <DatePicker v-model="end_at" size="large" type="datetime" placeholder="Select date" :options="false"></DatePicker>
                                    </Col>
                                    <Col span="5" offset="1">
                                        <i-switch size="default" v-model="formContest.visible">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <Col span="5" offset="1">
                                        <Badge type="success" text="Accessable"></Badge>
                                    </Col>
                                </FormItem>
                            </Row>
                        </div>
                    </div>
                    <!-- Source -->
                    <div class="contest-eidt contest-eidt-testcase">
                        <div class="contest-info-item">
                            Source
                        </div>
                        <FormItem>
                            <label>
                                <Input type="text" title=""/>
                            </label>
                        </FormItem>
                        <Button type="primary" @click="handleAdd">Save</Button>
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
                    title: '',
                    description: "",
                    visible: false,
                    end_at: null,
                    start_at: null,
                    //tag info
                    tags: null,
                },
                end_at: null,
                start_at: null,
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
                    Api.findProblemByProblemId(this.$route.params.problem_id).then(res => {
                        let tags = [];
                        Api.findTagsByProblemId(this.$route.params.problem_id).then(result => {
                            tags = result.data.data.tags;
                            this.initFormDataWithProblemAndTag(res.data.data.result.problem, tags);
                        }).catch(error => {
                            console.log(error);
                        });
                    }).catch(error => {
                        console.log(error);
                    });
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
            //上传文件
            handleUpload(file) {
                this.file_testCase = file;
                return false;
            },
            //save
            upload() {
                this.loadingStatus = true;
                setTimeout(() => {
                    this.file_testCase = null;
                    this.loadingStatus = false;
                    this.$Message.success('Success')
                }, 1000);
            },
            //初始化显示
            //TODO 修改
            initFormDataWithProblemAndTag(problem, tag) {
                if (problem == null) {
                    this.formContest = {
                        display_id: "",
                        title: '',
                        description: "",
                        input: "",
                        output: "",
                        visible: false,
                        hint: "",
                        source: "",
                        memory_limit: 64,
                        time_limit: 1000,
                        tags: null,
                    }
                } else {
                    this.formContest = {
                        display_id: problem.display_id,
                        title: problem.title,
                        description: problem.description,
                        input: problem.input,
                        output: problem.output,
                        visible: problem.defunct === "1",
                        hint: problem.hint,
                        source: problem.source,
                        memory_limit: problem.memory_limit,
                        time_limit: problem.time_limit,
                        tags: tag,
                    }
                }
            },
            handleAdd() {
                alert(this.formatDate(this.formContest.start_at));
            }
        },
        watch: {
            start_at: function (newVal, oldVal) {
                this.formContest.start_at = this.formatDate(newVal);
            },
            end_at: function (newVal, oldVal) {
                this.formContest.end_at = this.formatDate(newVal);
            },
        },
        created() {
        },
        beforeRouteEnter(to, from, next) {
            if (to.path === "/admin/contest/create") {
                console.log("reload");
                next(vm => {
                    vm.method = "Add Contest";
                    // vm.initFormDataWithProblemAndTag(null, null);
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