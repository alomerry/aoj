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
                                    <span class="item-require-red">*</span> Display Id
                                </Col>
                                <Col span="5" offset="4">
                                    <span class="item-require-red">*</span> Title
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
                            <span class="item-require-red">*</span> Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor v-model="formProblem.description">
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Input Description -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            <span class="item-require-red">*</span> Input Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor v-model="formProblem.input">
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Output Description -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            <span class="item-require-red">*</span> Output Description
                        </div>
                        <div class="problem-info-item">
                            <FormItem>
                                <Simditor v-model="formProblem.output">
                                </Simditor>
                            </FormItem>
                        </div>
                    </div>
                    <!-- Limits -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            <Row>
                                <Col span="7"><span class="item-require-red">*</span> Time Limit (ms)</Col>
                                <Col span="7" offset="1"><span class="item-require-red">*</span> Memory limit (MB)</Col>
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
                                        <span class="problem-info-item" style="margin-right: 10px"><span class="item-require-red">*</span> Visible</span>
                                        <i-switch size="default" v-model="formProblem.visible">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
                                    </Col>
                                    <Col span="10">
                                        <span class="problem-info-item" style="margin-right: 10px">Tag</span>
                                        <Tag closable
                                             v-for="(item,index) in formProblem.tags"
                                             :key="item.tag_id"
                                             :name="item.tag_id"
                                             :color="color[index]"
                                             @on-close="deleteTag">
                                            {{item.tagname}}
                                        </Tag>
                                        <Poptip>
                                            <div slot="content">
                                                <Input size="small" placeholder="tag content" @on-blur="addTag" v-model="tag_input_value"/>
                                            </div>
                                            <Button icon="ios-add" size="small">添加标签
                                            </Button>
                                        
                                        </Poptip>
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
                            <Simditor v-model="formProblem.hint">
                            </Simditor>
                        </div>
                    </div>
                    <!-- Test Case -->
                    <div class="problem-eidt problem-eidt-testcase">
                        <div class="problem-info-item">
                            <span class="item-require-red">*</span> Test Case
                        </div>
                        <FormItem>
                            <Upload
                                    action="/api/api-oj/problem/test_case"
                                    name="testCase"
                                    :show-file-list="true"
                                    :show-upload-list="false"
                                    :format="['zip']"
                                    :on-format-error="fileFormatError"
                                    :on-success="uploadSucceeded"
                                    :on-error="uploadFailed">
                                <Button icon="ios-cloud-upload" color="#19be6b" type="info">Choose File</Button>
                            </Upload>
                            <span v-if="file_testCase !== null">{{ file_testCase.name }}
                                    <Icon type="md-close" size="15" @click="clearUploadFile"/>
                            </span>
                        </FormItem>
                    </div>
                    <!-- Source -->
                    <div class="problem-eidt problem-eidt-testcase">
                        <div class="problem-info-item">
                            Source
                        </div>
                        <FormItem>
                            <label>
                                <Input type="text" title=""/>
                            </label>
                        </FormItem>
                        <Button type="primary" @click="submit">Save</Button>
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
                testCase_dir_id: null,
                content: '',
                tag_input_value: '',//添加tag的input的value
                formProblem: {
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
                    //tag info
                    tags: [],
                },
                color: [
                    "default",
                    "primary",
                    "success",
                    "error",
                    "magenta",
                    "red",
                    "volcano",
                    "orange",
                    "gold",
                    "yellow",
                    "lime",
                    "green",
                    "cyan",
                    "blue",
                    "geekblue",
                    "purple",
                    "#FFA2D3",
                ],
            }
        },
        components: {
            Simditor,
        },
        mounted() {
            console.log("mounted:$route.params:" + this.$route.params.method);
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
            clearUploadFile() {
                this.file_testCase = null;
            },
            //添加tag
            addTag() {
                this.formProblem.tags.push({
                    tag_id: this.tag_input_value,
                    tagname: this.tag_input_value,
                });
                this.tag_input_value = "";
            },
            //删除标签
            deleteTag(event, tag_id) {
                let res = this.formProblem.tags.findIndex(function (currentItem, index) {
                    if (currentItem.tag_id == tag_id) {
                        return true;
                    }
                });
                // console.log(res);
                this.formProblem.tags.splice(res, 1);
            },
            initFormDataWithProblemAndTag(problem, tag) {
                if (problem == null) {
                    this.formProblem = {
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
                        tags: [],
                    }
                } else {
                    this.formProblem = {
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
            fileFormatError(file) {
                this.$Notice.warning({
                    title: 'The file format is incorrect',
                    desc: 'File format of ' + file.name + ' is incorrect, please select zip.'
                });
            },
            uploadFailed() {
                this.$Message.error("上传失败");
                this.testCase_dir_id = null;
            },
            uploadSucceeded(response) {
                console.log(response);
                if (response.code == 200) {
                    this.testCase_dir_id = response.data.testCase_dir_id;
                    this.$Message.success("success");
                } else {
                    this.$Message.error(response.message);
                }
            },
            submit() {
                if (this.testCase_dir_id == null) {
                    this.$Message.error("Please upload test case first!");
                    // return ;
                }
                if (this.formProblem.display_id == null || this.formProblem.display_id === "") {
                    this.$Message.error("Display Id is required!");
                    return;
                }
                if (this.formProblem.title == null || this.formProblem.title === "") {
                    this.$Message.error("Title is required!");
                    return;
                }
                if (this.formProblem.description == null || this.formProblem.description === "") {
                    this.$Message.error("Description is required!");
                    return;
                }
                if (this.formProblem.input == null || this.formProblem.input === "") {
                    this.$Message.error("Input Description is required!");
                    return;
                }
                if (this.formProblem.output == null || this.formProblem.output === "") {
                    this.$Message.error("Out Description is required!");
                    return;
                }

                let tags = this.formProblem.tags;
                let problem = this.formProblem;
                delete problem.tags;
                Api.createNewProblem(problem, tags.length === 0 ? null : tags, this.tag_input_value, this.$store.state.token).then(res => {
                    let result = res.data;
                    console.log(result);
                }).catch(res => {
                    console.log(res);
                });
            }
        },
        created() {
        },
        beforeRouteEnter(to, from, next) {
            if (to.path === "/admin/problem/create") {
                console.log("reload");
                next(vm => {
                    vm.method = "Add Problem";
                    vm.initFormDataWithProblemAndTag(null, null);
                });
            } else {
                next();
            }
        },
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
    
    span.item-require-red {
        color: red;
    }
</style>