<template>
    <div class="card-content">
        <Row>
            <Col span="18" style="margin-right: 10px">
                <Card>
                    <p class="card-title">Problem List</p>
                    <div slot="extra">
                        <Row>
                            <Col span="10">
                                <Input v-model="searchKeyWord" placeholder="Keywords" clearable>
                                    <Icon type="ios-search" slot="prefix"/>
                                </Input>
                            </Col>
                            <!--<Col span="6">
                                <Dropdown style="margin-top: 4px">
                                    <a href="javascript:void(0)" style="color: #515a6e">
                                        下拉菜单
                                        <Icon type="ios-arrow-down"></Icon>
                                    </a>
                                    <DropdownMenu slot="list">
                                        <DropdownItem>驴打滚</DropdownItem>
                                        <DropdownItem>炸酱面</DropdownItem>
                                        <DropdownItem disabled>豆汁儿</DropdownItem>
                                        <DropdownItem>冰糖葫芦</DropdownItem>
                                        <DropdownItem divided>北京烤鸭</DropdownItem>
                                    </DropdownMenu>
                                </Dropdown>
                            </Col>-->
                            <Col span="2">
                                <Button style="margin-left: 10px;" :loading="buttonLoading" @click.native="getProblems" type="primary">
                                    Refresh
                                </Button>
                            </Col>
                        </Row>
                    </div>
                    <Table :data="tableSearchData" :columns="tableColumns1" stripe
                           :loading="tableLoading"></Table>
                </Card>
                <div style="margin:25px 10px 10px 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="totalPage" :current="current" :page-size="per_page" show-sizer show-elevator
                              @on-change=""></Page>
                    </div>
                </div>
            </Col>
            <Col span="5">
                <Card>
                    <p slot="title" style="font-size: 18px;">Tag</p>
                    <div>
                        <Tag :color="tag_color[(item.tag_id%tag_color.length)]" v-for="(item) in tag_list" @click.native="getProblemsByTag(item.tag_id)">{{item.tagname}}</Tag>
                    </div>
                    <Button long ghost type="info" @click.native="" style="margin-top: 15px">Pick One</Button>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    import Api from '../../components/api';
    import debounce from "lodash/debounce";

    export default {
        name: 'ProblemList',
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                current: 1,
                totalPage: 1,
                per_page: 10,
                buttonLoading: false,//表格重载'按钮'加载中状态
                tableData1: [],//表格数据
                tableSearchData: [
                    {
                        accepted: 0,
                        click: null,
                        create_by: 10,
                        created_at: 1517101561000,
                        defunct: "1",
                        description: null,
                        display_id: 1,
                        hint: null,
                        input: null,
                        memory_limit: null,
                        output: null,
                        problem_id: 2,
                        sample_input: null,
                        sample_output: null,
                        source: null,
                        submit: 0,
                        time_limit: null,
                        title: "A+B"
                    },
                ],
                tableColumns1: [
                    {
                        title: 'title',
                        key: 'title',
                        width: 600,
                        render: (h, params) => {
                            return h('router-link', {
                                style: {
                                    fontSize: '15px'
                                },
                                attrs: {
                                    to: "/problem/" + params.row.problem_id,
                                },
                                on: {
                                    click: () => {

                                    }
                                },
                            }, params.row.title);
                        }
                    },
                    //todo level
                    /*{
    
                    },*/
                    {
                        title: 'Total',
                        key: 'submit',
                    },
                    {
                        title: 'AC Rate',
                        key: 'ac_rate',
                        render: (h, params) => {
                            return h('Poptip', {
                                props: {
                                    trigger: 'hover',
                                    title: 'AC Rate',
                                    placement: 'bottom'
                                }
                            }, [
                                h('Tag', (params.row.accepted / (params.row.submit === 0 ? 1 : params.row.submit)) * 100 + "%"),
                                h('div', {slot: 'content'}, [
                                    h('p', {
                                        style: {
                                            fontSize: '15px'
                                        }
                                    }, "AC:" + params.row.accepted),
                                    h('p', {
                                        style: {
                                            fontSize: '15px'
                                        }
                                    }, "Total:" + params.row.submit),
                                ]),
                            ]);
                        }
                    }
                ], //表格行数据
                tableLoading: false,//'表格'加载中状态
                tag_list: [],
                tag_color: [
                    "#FFA2D3",
                    "purple",
                    "geekblue",
                    "#ff5075",
                    "red",
                    "blue",
                    "#ffa365",
                    "cyan",
                    "green",
                    "#cb95ff",
                    "gold",
                    "lime",
                    "#8eebff",
                    "yellow",
                    "orange",
                    "volcano",
                    "#514eff",
                    "magenta",
                    "error",
                    "success",
                    "#cbff5e",
                    "primary",
                    "default",
                ],
            }
        },
        methods: {
            //搜索查询表格
            DelaySearchTable: function () {
                if (this.searchKeyWord === "") {
                    this.tableSearchData = this.tableData1;
                } else {
                    this.tableSearchData = [];
                    let that = this;
                    this.tableData1.forEach(function (is, i) {
                        let item = that.tableData1[i];
                        if (item.title.indexOf(that.searchKeyWord) !== -1) {
                            that.tableSearchData.push(item);
                        }
                    });
                }
            },
            //获取题目
            getProblems() {
                this.tableLoading = true;
                Api.findProblemsByPagePer_PageAndResultType(this.current, this.per_page, "simple").then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.tableData1 = result.data.results;
                        this.tableSearchData = this.tableData1;
                        this.tableLoading = false;
                    } else {
                        console.log('Failed! ' + result.message);
                        this.tableLoading = false;
                    }
                }).catch(err => {
                    console.log('An error has occurred! ' + err);
                    this.tableLoading = false;
                });
            },
            //格式化时间
            formatDate(date) {
                const y = date.getFullYear();
                let m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                let d = date.getDate();
                d = d < 10 ? ('0' + d) : d;
                return y + '-' + m + '-' + d;
            },
            //更新激活
            updateActiveClass(path) {
                switch (path) {
                    case "/status": {
                        this.$store.dispatch("updateActiveName", 'status');
                        break;
                    }
                    case "/home": {
                        this.$store.dispatch("updateActiveName", 'home');
                        break;
                    }
                    case "/problems": {
                        this.$store.dispatch("updateActiveName", 'problems');
                        break;
                    }
                }
            },
            //获取标签
            getTags() {
                Api.findTags(this.current, this.per_page).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.tag_list = result.data.tags;
                    } else {
                        console.log(result.message);
                    }
                }).catch(res => {
                    console.log(res);
                })
            },
            //根据标签修改题目列表
            getProblemsByTag(tag_id) {
                this.current = 1;
                this.tableLoading = true;
                Api.findProblemsByTagId(tag_id, this.current, this.per_page).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        if (result.data.problems == null) {
                            this.tableData1 = [];
                        } else {
                            this.tableData1 = result.data.problems;
                        }
                        this.tableSearchData = this.tableData1;
                        this.tableLoading = false;
                    } else {
                        this.$Message.error(result.message);
                        this.tableLoading = false;
                    }
                }).catch(res => {
                    console.log(res);
                    this.tableLoading = false;
                });
            }
        },
        created() {
            this.current = 1;
            this.totalPage = 1;
            this.updateActiveClass(this.$route.path);
            this.debouncedsearchData = debounce(this.DelaySearchTable, 500, null);//延时加载
            this.getProblems();
        },
        mounted() {
            this.getTags();
        },
        computed: {},
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            }
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

