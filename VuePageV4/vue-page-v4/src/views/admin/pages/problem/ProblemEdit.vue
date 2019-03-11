<template>
    <div class="bg">
        <Card style="margin: 15px">
            <Form :model="formProblem">
                <div slot="title" style="height: 40px;padding-top: 10px">
                    <span style="font-size: 25px;font-weight: 400;">Edit Problem</span>
                    <br><br>
                </div>
                <div class="index">
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
                    <!-- Tag -->
                    <div class="problem-eidt problem-eidt-info">
                        <div class="problem-info-item">
                            <Row>
                                <Col span="10">
                                    Tag
                                </Col>
                                <Col span="2" offset="1">
                                    Visible
                                </Col>
                            </Row>
                        </div>
                        <div class="problem-info-item">
                            <Row>
                                <FormItem>
                                    <Col span="10">
                                        <Tag type="dot" closable color="green">标签一</Tag>
                                        <Tag type="dot" closable color="primary">标签二</Tag>
                                        <Tag type="dot" closable color="green">标签三</Tag>
                                        <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签
                                        </Button>
                                        <!--<Input size="large" placeholder="large size"/>-->
                                    </Col>
                                </FormItem>
                                <FormItem>
                                    <Col span="2" offset="1" style="margin-bottom: 15px;">
                                        <i-switch size="default" v-model="formProblem.visible">
                                            <span slot="open">️</span>
                                            <span slot="close"></span>
                                        </i-switch>
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

    export default {
        name: "ProblemEdit",
        data() {
            return {
                file_testCase: null,
                loadingStatus: false,//上传状态
                content: '',
                formProblem: {
                    display_id: 6,
                    title: '小红洗苹果',
                    visible: false,
                }
            }
        },
        components: {
            Simditor,
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