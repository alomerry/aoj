<template>
    <div class="bg">
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px;padding-top: 10px">
                <span style="font-size: 19px;font-weight: 400;" v-text="method"></span>
                <br><br>
            </div>
            <Form :model="formProblem">

                <div class="index">
                    <!-- Title -->
                    <div class="problem-eidt problem-eidt-titles">
                        <div class="problem-info-item">
                            <Row>
                                <Col span="4">
                                    Display Id
                                </Col>
                                <Col span="5" offset="4">
                                    Title
                                </Col>
                            </Row>
                        </div>
                        <div class="problem-info-item">
                            <Row>
                                <Col span="7">
                                    <FormItem label="">
                                        <Input size="large" placeholder="large size" v-model="formProblem.display_id"/>
                                    </FormItem>
                                </Col>
                                <Col span="10" offset="1">
                                    <FormItem label="">
                                        <Input size="large" placeholder="large size" v-model="formProblem.title"/>
                                    </FormItem>
                                </Col>
                            </Row>
                        </div>
                    </div>
                    <!-- Description -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor>
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Input Description -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            Input Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor>
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Output Description -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            Output Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor>
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Limits -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            <Row>
                                <Col span="7">Time Limit (ms)</Col>
                                <Col span="7" offset="1">Memory limit (MB)</Col>
                            </Row>
                        </div>
                        <div class="problem-info-item">
                            <Row>
                                <FormItem>
                                    <Col span="7">
                                        <Input size="large" placeholder="large size" v-model="formProblem.time_limit"/>
                                    </Col>
                                    <Col span="7" offset="1">
                                        <Input size="large" placeholder="large size" v-model="formProblem.memory_limit"/>
                                    </Col>
                                </FormItem>
                            </Row>
                        </div>
                    </div>
                    <!-- Tag -->
                    <div class="problem-eidt problem-eidt-info">
                        <div class="problem-info-item">
                            <Row>
                                <FormItem>
                                    <Col style="margin-bottom: 15px;" span="3">
                                        <span class="problem-info-item" style="margin-right: 10px">Visible</span>
                                        <i-switch size="default" v-model="formProblem.visible">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <Col span="10">
                                        <span class="problem-info-item" style="margin-right: 10px">Tag</span>
                                        <Tag type="dot" closable color="green" :key="item.tag_id"
                                             v-for="item in formProblem.tags">
                                            {{item.tagname}}
                                        </Tag>
                                        <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签
                                        </Button>
                                    </Col>
                                </FormItem>
                            </Row>
                        </div>
                    </div>
                    <!--Hint-->
                    <div class="problem-eidt problem-eidt-hit">
                        <div class="problem-info-item">
                            Hint
                        </div>
                        <div class="problem-info-item">
                            <Simditor>
                            </Simditor>
                        </div>
                    </div>
                    <!-- Test Case -->
                    <div class="problem-eidt problem-eidt-testcase">
                        <div class="problem-info-item">
                            Test Case
                        </div>
                        <FormItem>
                            <Upload
                                    :before-upload="handleUpload"
                                    action="//jsonplaceholder.typicode.com/posts/">
                                <Button icon="ios-cloud-upload" color="#19be6b" type="info">Choose File</Button>
                            </Upload>
                        </FormItem>
                        <p v-if="file_testCase !== null">Upload file: {{ file_testCase.name }}
                            <Button type="text" @click="upload" :loading="loadingStatus">{{ loadingStatus ? 'Uploading'
                                : 'Click to upload' }}
                            </Button>
                        </p>
                    </div>
                    <!-- Test Case -->
                    <div class="problem-eidt problem-eidt-testcase">
                        <div class="problem-info-item">
                            Source
                        </div>
                        <FormItem>
                            <Input type="text" title=""/>
                        </FormItem>
                        <Button type="primary">Save</Button>
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
        name: "ProblemEdit",
        data() {
            return {
                method: null,//Edit模式/New模式
                file_testCase: null,
                loadingStatus: false,//上传状态
                content: '',
                formProblem: {
                    display_id: "",
                    title: '',
                    description: "",
                    input: "",
                    output: "",
                    visible: false,
                    hint: "",
                    source: "",
                    memory_limit:64,
                    time_limit:1000,
                    //tag info
                    tags: null,
                }
            }
        },
        components: {
            Simditor,
        },
        mounted() {
            console.log("this.$route.params:" + this.$route.params.method);
            switch (this.$route.params.method) {
                case "edit": {
                    //search,init formProblem's problem and tag
                    this.method = "Edit Problem";
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
                    this.method = "Add Problem";
                    break;
                }
            }
        },
        methods: {
            handleUpload(file) {
                this.file_testCase = file;
                return false;
            },
            upload() {
                this.loadingStatus = true;
                setTimeout(() => {
                    this.file_testCase = null;
                    this.loadingStatus = false;
                    this.$Message.success('Success')
                }, 1000);
            },
            initFormDataWithProblemAndTag(problem, tag) {
                this.formProblem = {
                    display_id: problem.display_id,
                    title: problem.title,
                    description: problem.description,
                    input: problem.input,
                    output: problem.output,
                    visible: problem.defunct === "1",
                    hint: problem.hint,
                    source: problem.source,
                    memory_limit:problem.memory_limit,
                    time_limit:problem.time_limit,
                    tags: tag,
                }
            },
            handleAdd() {

            }
        }
    }
</script>

<style scoped>
    .problem-info-item {
        padding: 5px;
        margin-bottom: 5px;
        font-size: 17px;
    }

    .problem-eidt {
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