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
                                        <i-switch size="default" v-model="formProblem.defunct">
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
                                             :color="color[(index*16+23)%(color.length)]"
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
                    <!-- simple -->
                    <div class="problem-eidt problem-eidt-discribe">
                        <div class="problem-info-item">
                            <span class="item-require-red">*</span> Simple Case
                        </div>
                        <Collapse accordion>
                            <Panel name="1">
                                Simple 1
                                <div class="problem-info-item" slot="content">
                                    <Row>
                                        <Col span="11">
                                            <div style="margin-bottom: 15px">
                                                <span class="item-require-red">*</span> Simple input
                                            </div>
                                            <Input type="textarea" :rows="4" placeholder="Enter simple input" v-model="formProblem.sample_input"/>
                                        </Col>
                                        <Col span="11" offset="1">
                                            <div style="margin-bottom: 15px">
                                                <span class="item-require-red">*</span> Simple output
                                            </div>
                                            <Input type="textarea" :rows="4" placeholder="Enter simple output" v-model="formProblem.sample_output"/>
                                        </Col>
                                    </Row>
                                </div>
                            </Panel>
                        </Collapse>
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
                                <Input type="text" title="" v-model="formProblem.source"/>
                            </label>
                        </FormItem>
                        <Button type="primary" @click="submit" :loading="saveLoadingFlag">Save</Button>
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
                    defunct: false,
                    hint: "",
                    source: "",
                    memory_limit: 64,
                    time_limit: 1000,
                    sample_input: "",
                    sample_output: "",
                    //tag info
                    tags: [],
                },
                tmp_index: 0,//标签key
                saveLoadingFlag: false,//保存按钮加载标签
                color: [
                    "#FFA2D3",
                    "purple",
                    "geekblue",
                    "red",
                    "blue",
                    "cyan",
                    "green",
                    "gold",
                    "lime",
                    "yellow",
                    "orange",
                    "volcano",
                    "magenta",
                    "error",
                    "success",
                    "primary",
                    "default",
                ],
            }
        },
        components: {
            Simditor,
        },
        mounted() {
            // console.log("mounted:$route.params:" + this.$route.params.method);
            switch (this.$route.params.method) {
                case "edit": {
                    //search,init formProblem's problem and tag
                    this.method = "Edit Problem";
                    Api.findProblemByProblemId(this.$route.params.problem_id, this.$store.state.token).then(res => {
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
                    tag_id: this.tmp_index,
                    tagname: this.tag_input_value,
                });
                this.tmp_index++;
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
            //初始化页面数据
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
                    this.formProblem.display_id = problem.display_id;
                    this.formProblem.title = problem.title;
                    this.formProblem.description = problem.description;
                    this.formProblem.input = problem.input;
                    this.formProblem.output = problem.output;
                    this.formProblem.visible = problem.defunct === "1";
                    this.formProblem.hint = problem.hint;
                    this.formProblem.source = problem.source;
                    this.formProblem.memory_limit = problem.memory_limit;
                    this.formProblem.sample_input = problem.sample_input;
                    this.formProblem.sample_output = problem.sample_output;
                    this.formProblem.time_limit = problem.time_limit;
                    this.formProblem.problem_id = problem.problem_id;
                    this.formProblem.create_by = problem.create_by;
                    this.formProblem.tags = tag;
                }
            },
            //上传文件格式错误时的回调
            fileFormatError(file) {
                this.$Notice.warning({
                    title: 'The file format is incorrect',
                    desc: 'File format of ' + file.name + ' is incorrect, please select zip.'
                });
            },
            //上传文件失败时的回调
            uploadFailed() {
                this.$Message.error("上传失败");
                this.testCase_dir_id = null;
            },
            //上传文件成功时的回调
            uploadSucceeded(response) {
                console.log(response);
                if (response.code == 200) {
                    this.testCase_dir_id = response.data.testCase_dir_id;
                    this.$Message.success("upload success!");
                } else {
                    this.$Message.error(response.message);
                }
            },
            submit() {
                this.saveLoadingFlag = true;
                switch (this.$route.params.method) {
                    case "edit": {
                        this.updateProblem();
                        break;
                    }
                    case "create": {
                        this.createProblem();
                        break;
                    }
                }

            },
            //创建题目
            createProblem() {
                if (this.testCase_dir_id == null) {
                    this.$Message.error("Please upload test case first!");
                    return;
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
                if (this.formProblem.sample_input == null || this.formProblem.sample_input === "") {
                    this.$Message.error("Out Description is required!");
                    return;
                }
                if (this.formProblem.sample_output == null || this.formProblem.sample_output === "") {
                    this.$Message.error("Out Description is required!");
                    return;
                }
                this.formProblem.defunct = this.formProblem.defunct ? '1' : '3';
                let tags = this.formProblem.tags;
                let problem = this.formProblem;
                delete problem.tags;
                this.$Loading.start();
                Api.createNewProblem(problem, tags, this.testCase_dir_id, this.$store.state.token).then(res => {
                    let result = res.data;
                    console.log(result);
                    if (result.code === 200) {
                        this.$Loading.finish();
                        this.$Message.success("Create Problem Successed!");
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
                        this.testCase_dir_id = null;
                    } else {
                        this.$Loading.error();
                        this.$Message.error(result.message);
                    }
                    this.saveLoadingFlag = false;
                }).catch(res => {
                    console.log(res);
                    this.saveLoadingFlag = false;
                });
            },
            //更新题目
            updateProblem() {
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
                if (this.formProblem.sample_input == null || this.formProblem.sample_input === "") {
                    this.$Message.error("Out Description is required!");
                    return;
                }
                if (this.formProblem.sample_output == null || this.formProblem.sample_output === "") {
                    this.$Message.error("Out Description is required!");
                    return;
                }
                this.formProblem.defunct = this.formProblem.defunct ? '1' : '3';
                let tags = this.formProblem.tags;
                let problem = this.formProblem;
                problem.problem_id = this.$route.params.problem_id;
                // delete problem.tags;
                this.$Loading.start();
                Api.updateProblem(problem, tags, this.testCase_dir_id, this.$store.state.token).then(res => {
                    let result = res.data;
                    console.log(result);
                    if (result.code === 200) {
                        this.$Loading.finish();
                        this.$Message.success("Update Problem Successed!");
                    } else {
                        this.$Loading.error();
                        this.$Message.error(result.message);
                    }
                    this.saveLoadingFlag = false;
                }).catch(res => {
                    console.log(res);
                    this.saveLoadingFlag = false;
                });
            },
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