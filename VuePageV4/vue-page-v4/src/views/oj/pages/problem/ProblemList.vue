<template>
    <div class="card-content">
        <Row>
            <Col span="18" style="margin-right: 10px">
                <Card>
                    <p class="card-title">Problem List</p>
                    <div slot="extra">
                        <Row>
                            <Col span="8">
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
                                <Button style="margin-left: 10px;margin-right: 60px" :loading="buttonLoading" @click.native="refresh" type="primary">
                                    Refresh
                                </Button>
                            </Col>
                        </Row>
                    </div>
                    <Table :data="tableData1" :columns="tableColumns1" stripe
                           :loading="tableLoadingIsFinish"></Table>
                </Card>
                <div style="margin:25px 10px 10px 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="totalPage" :current="current" :page-size="per_page" show-sizer show-elevator
                              @on-change="changePage"></Page>
                    </div>
                </div>
            
            </Col>
            <Col span="5">
                <Card>
                    <p slot="title">Borderless card</p>
                    <p>Content of card</p>
                    <p>Content of card</p>
                    <p>Content of card</p>
                    <Button long ghost type="info" @click.native="" style="margin-top: 15px">Pick One</Button>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    import Api from '../../components/api';

    export default {
        name: 'ProblemList',
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                current: 1,
                totalPage: 1,
                per_page: 10,
                buttonLoading: false,//表格重载'按钮'加载中状态
                tableData1: this.mockTableData1(),//表格数据
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
                ],//表格行数据
                tableLoading: true,//'表格'加载中状态
            }
        },
        methods: {
            mockTableData1() {
                let problems = [];
                problems = Api.findProblemsByPagePer_PageAndResultType(this.current, this.per_page, "simple");
                if (problems.length === 0) {
                    this.tableLoading = false;
                }
                return problems;
            },

            formatDate(date) {
                const y = date.getFullYear();
                let m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                let d = date.getDate();
                d = d < 10 ? ('0' + d) : d;
                return y + '-' + m + '-' + d;
            },

            changePage() {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server
                this.tableData1 = this.mockTableData1();
            },
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
            }
        },
        created() {
            this.current = 1;
            //ajax
            this.totalPage = 1;
            this.tableLoading = true;
            this.updateActiveClass(this.$route.path);
        },
        computed: {
            tableLoadingIsFinish() {
                // console.log((this.tableData1.length > 0 && this.tableLoading));
                return !(this.tableData1.length > 0 && this.tableLoading);
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

