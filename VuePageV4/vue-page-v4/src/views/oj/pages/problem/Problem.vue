<template>
    <div class="card-content">
        <div>
            <Modal v-model="reset_modal" width="360">
                <p slot="header" style="color:#ffb700;text-align:center;height:45px;overflow: unset">
                    <Icon type="ios-information-circle" size="50"></Icon>&nbsp;&nbsp;&nbsp;
                    <span style="font-size: 25px;font-weight: 500">Reset Confirm</span>
                </p>
                <div style="text-align:center">
                    <p style="font-size: 25px">Are you sure you want to reset your code?</p>
                </div>
                <div slot="footer">
                    <Button type="warning" @click="resetCode">Do</Button>
                </div>
            </Modal>
        </div>
        <Row>
            <Col span="20" style="margin-right: 10px">
                <Card>
                    <div class="problem-detail">
                        <p class="card-title">Problem Detail</p>
                        <div>
                            <label>Description</label>
                            <p v-html="problem.description"></p>
                            <label>Input</label>
                            <p v-html="problem.input"></p>
                            <label>Output</label>
                            <p v-html="problem.output"></p>
                            <Row>
                                <Col span="12">
                                    <label>Simple Input</label>
                                    <div class="oj-code">
                                        <p v-html="problem.sample_input"></p>
                                    </div>
                                </Col>
                                <Col span="12">
                                    <label>Simple Output</label>
                                    <div class="oj-code">
                                        <p v-html="problem.sample_output"></p>
                                    </div>
                                </Col>
                            </Row>
                            <label>Hit</label>
                            <p v-html="problem.hint==null?'无':problem.hint"></p>
                            <label>Source</label>
                            <p v-html="problem.source == null?'无':problem.source"></p>
                        </div>
                    </div>
                </Card>
                <br>
                <br>
                <!--CodeMirror Card-->
                <Card>
                    <div slot="title" style="margin-bottom: 32px">
                        <label for="select_lauguage"
                               style="float: left;margin-top: 7px;font-size: 15px">Language:</label>
                        <Select v-model="select_language" style="width:200px;float: left;" id="select_lauguage"
                                @on-change="onLanguageChange(select_language)">
                            <Option v-for="item in languageList" :value="item.value" :key="item.value">{{ item.label
                                }}
                            </Option>
                        </Select>
                        <Button ghost type="warning" style="float: left; margin-left: 5px"
                                @click.native="reset_modal = true">
                            <Icon type="md-refresh"/>
                        </Button>
                    </div>
                    <div slot="extra">
                        <label for="select_theme" style="margin-top: 7px;font-size: 15px">Theme:</label>
                        <Select v-model="select_theme" style="width:200px" id="select_theme"
                                @on-change="onThemeChange(select_theme)">
                            <Option v-for="item in themeList" :value="item.value" :key="item.value">{{ item.label }}
                            </Option>
                        </Select>
                    </div>
                    <div>
                        <label for="code_content" style="display: none"></label>
                        <textarea id="code_content" name="code_content" style="display: none"></textarea>
                    </div>
                    <Divider/>
                    <Row>
                        <div>
                            <Button ghost type="primary" size="large" style="float: right;margin-right: 50px"
                                    @click.native="submitCode" :disabled="loginStatus" :loading="submitLoadingFlag">
                                <Icon type="md-create"/>
                                Submit
                            </Button>
                        </div>
                    </Row>
                </Card>
            </Col>
            <Col span="3">
                <Card @click.native="updateActiveName" style="width: 226px">
                    <router-link to="/status">
                        <div style="float: left;">
                            <Icon type="md-code-working" size="30"/>
                            <span>Submissions</span>
                        </div>
                        <br>
                    </router-link>
                </Card>
                <br>
                <Card style="width: 226px">
                    <div style="float: left">
                        <Icon type="md-alert" size="24"/>&nbsp;&nbsp;
                        <span style="font-size: 15px;">Information</span>
                    </div>
                    <br>
                    <br>
                    <div class="problem-info">
                        <span class="title">ID</span><span class="content" v-text="problem.problem_id"></span>
                        <Divider/>
                        <span class="title">Time Limit</span><span class="content"
                                                                   v-text="problem.time_limit+'ms'"></span>
                        <Divider/>
                        <span class="title">Memory Limit</span><span class="content"
                                                                     v-text="problem.memory_limit+'MB'"></span>
                        <Divider/>
                        <span class="title">Create By</span><span class="content" v-text="author.nickname"></span>
                        <Divider/>
                        <!--<span class="title">Tag</span><span class="content" v-text="problem.time_limit"></span>-->
                        <!--<Divider/>-->
                    </div>
                </Card>
                <br>
                <Card style="width: 226px">
                    <div style="float: left">
                        <Icon type="ios-stats" size="24"/>&nbsp;&nbsp;
                        <span style="font-size: 15px;">Statistic</span>
                        <Button size="small" style="margin-left: 50px">Detail</Button>
                    </div>
                    <br>
                    <br>
                    <div style="width: 210px;height: 260px" id="main">
                        <!--<canvas  width="210px" height="250px"></canvas>-->
                    </div>
                </Card>
            </Col>
        </Row>
    
    </div>
</template>

<script>
    let editor_i;

    let echarts = require('echarts/lib/echarts');
    // 引入柱状图
    require('echarts/lib/chart/pie');
    // 引入提示框和标题组件
    require('echarts/lib/component/tooltip');
    require('echarts/lib/component/title');
    require('echarts/theme/macarons');

    import CodeMirror from 'codemirror'
    import 'codemirror/lib/codemirror.css'

    import "codemirror/mode/javascript/javascript"
    import "codemirror/addon/selection/active-line"
    import "codemirror/addon/edit/matchbrackets"


    import 'codemirror/theme/darcula.css'
    import 'codemirror/theme/idea.css'
    import API from "../../components/api"

    export default {
        name: "Problem",
        components: {
            CodeMirror
        },
        data() {
            return {
                themeList: [
                    {
                        value: 'darcula',
                        label: 'darcula'
                    },
                    {
                        value: 'idea',
                        label: 'idea'
                    },
                ],
                languageList: [
                    {
                        value: '0',
                        label: 'Java'
                    },
                    {
                        value: '1',
                        label: 'C'
                    },
                    {
                        value: '2',
                        label: 'C++'
                    },
                    {
                        value: '3',
                        label: 'Python'
                    },
                ],
                select_theme: 'idea',
                select_language: '2',
                submitLoadingFlag: false,
                // editor: '',
                author: '',
                problem: {
                    description: ''
                },
                reset_modal: false,
            }
        },
        computed: {
            loginStatus() {
                //TODO 服务器token验证 有效、时效
                return this.$store.state.user == null;
            }
        },
        created() {
            this.loadingProblemDetail();
        },
        mounted() {
            editor_i = CodeMirror.fromTextArea(document.getElementById("code_content"), {
                mode: "text/javascript",    //代码高亮
                styleActiveLine: true,
                lineNumbers: true,	//显示行号
                theme: "idea",	//设置主题
                matchBrackets: true,	//括号匹配
                indentUnit: 2,
                lineWrapping: true,
                direction: "ltr",
            });
            editor_i.setSize('1450px', '400px');

        },
        methods: {
            loadingProblemDetail() {
                API.findProblemByProblemId(this.$route.params.problem_id).then(res => {
                    this.problem = res.data.data.result.problem;
                    this.author = res.data.data.result.created_by;
                    let that = this;
                    setTimeout(function () {
                        that.draw();
                    }, 1000);
                }).catch(error => {
                    console.log(error);
                });
            },
            updateActiveName() {
                this.$store.dispatch('updateActiveName', 'status');
            },
            draw() {
                let ac = this.problem.accepted, wa = this.problem.submit - this.problem.accepted;
                let myChart = echarts.init(document.getElementById('main'), 'macarons');// 绘制图表
                myChart.setOption({
                    title: {},
                    legend: {
                        orient: 'horizontal',
                        data: ['AC', 'WA']
                    },
                    tooltip: {
                        show: false,
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    toolbox: {},
                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '85%',
                            center: ['50%', '60%'],
                            label: {
                                position: 'inner',
                                formatter: "{b}:{c}\n({d}%)"
                            },
                            data: [
                                {value: ac, name: 'AC', itemStyle: {color: '#19be6b'}},
                                {value: wa, name: 'WA', itemStyle: {color: 'LightPink'}}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                });
            },
            onLanguageChange() {
            },
            onThemeChange() {
                editor_i.setOption("theme", this.select_theme);
                console.log("theme修改成" + this.select_theme);
            },
            resetCode() {
                editor_i.setValue("");
                this.reset_modal = false;
            },
            submitCode() {
                this.submitLoadingFlag = true;
                let code = editor_i.getValue();
                let submit = {
                    //问题id
                    problem_id: this.problem.problem_id,
                    //结果(4:AC 0:待评测)
                    result: 0,
                    //语言
                    language: '',
                    code: code,
                    code_lenght: code.length(),
                }
            }
        }
    }
</script>

<style scoped>
    .card-content {
        margin: 20px 0 0 50px;
    }
    
    p.card-title {
        text-align: left;
        font-size: 30px;
        margin: 0 0 25px 20px;
        height: 35px;
    }
    
    .problem-detail label {
        color: #00b9ff;
        font-size: 23px;
        margin-left: 15px;
    }
    
    .problem-detail {
        text-align: left;
    }
    
    .problem-detail > div > p {
        margin-left: 30px;
        font-size: 16px;
        line-height: 40px;
        margin-bottom: 20px;
        /*width: 800px;*/
    }
    
    /*.problem-detail>div>label{
      margin-top: 20px;
    }*/
    .problem-detail >>> pre {
        margin-left: 30px;
        background-color: #d6d4df;
    }
    
    .problem-info > span.title {
        float: left;
    }
    
    .problem-info > span.content {
        float: right;
    }
    
    .ivu-divider-horizontal {
        margin: 0 0 10px 0;
    }


</style>
