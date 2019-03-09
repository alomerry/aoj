<template>
    <Card>
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
                            <Input size="large" placeholder="large size"/>
                        </Col>
                        <Col span="10" offset="1">
                            <Input size="large" placeholder="large size"/>
                        </Col>
                    </Row>
                </div>
            </div>
            <div class="problem-eidt problem-eidt-discribe">
                <div class="problem-info-item">
                    Description
                </div>
                <div class="problem-info-item">
                    <Simditor>
                    </Simditor>
                </div>
            </div>

            <div class="problem-eidt problem-eidt-discribe">
                <div class="problem-info-item">
                    Input Description
                </div>
                <div class="problem-info-item">
                    <Simditor>
                    </Simditor>
                </div>
            </div>
            <div class="problem-eidt problem-eidt-discribe">
                <div class="problem-info-item">
                    Output Description
                </div>
                <div class="problem-info-item">
                    <Simditor>
                    </Simditor>
                </div>
            </div>
            <div class="problem-eidt problem-eidt-info">
                <div class="problem-info-item">
                    <Row>
                        <Col span="4">
                            Visible
                        </Col>
                        <Col span="5" offset="4">
                            Tag
                        </Col>
                    </Row>
                </div>
                <div class="problem-info-item">
                    <Row>
                        <Col span="7" style="margin-bottom: 15px;">
                            <i-switch size="default">
                                <span slot="open">️</span>
                                <span slot="close"></span>
                            </i-switch>
                        </Col>
                        <Col span="7" offset="1">
                            <Tag type="dot" closable color="green">标签一</Tag>
                            <Tag type="dot" closable color="primary">标签二</Tag>
                            <Tag type="dot" closable color="green">标签三</Tag>
                            <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签</Button>
                            <Input size="large" placeholder="large size"/>
                        </Col>
                    </Row>
                </div>
            </div>
        </div>
    </Card>
</template>

<script>
    import Simditor from '../../components/Simditor'

    export default {
        name: "ProblemEdit",
        data() {
            return {
                content: '',
            }
        },
        components: {
            Simditor,
        },
        methods: {

        }
    }
</script>

<style scoped>
    .problem-info-item {
        padding: 5px;
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
</style>