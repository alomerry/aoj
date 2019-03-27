<template>
    <div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Contests</span>
                <i-input v-model="searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
                <Button icon="md-refresh" style="float: right;margin-right: 20px"
                        @click.native="getProblems"></Button>
                <Button icon="ios-trash" type="warning" style="width: auto;float: right;margin-right: 15px"
                        @click=""
                        v-show="deleteShowFlag">Delete
                </Button>
                <br><br>
            </div>
            <Table border ref="selection" :columns="columns" :data="selectData" :loading="tableLoadingFlag"
                   @on-selection-change="selectionChange"></Table>
            <br>
            <Page :current="page" :page-size="per_page" size="small" style="float: right"/>
            <br>
        </Card>
        
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px">
            </div>
        </Card>
    </div>
</template>

<script>
    import debounce from "lodash/debounce"
    import Api from "../../components/api"
    
    export default {
        name: "ContestList",
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                deleteShowFlag: false,
                tableLoadingFlag: false,
                columns: [
                    {
                        type: 'expand',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: 'ContestId',
                        key: 'problem.problem_id',
                        render: (h, params) => {
                            return h('span', {}, params.row.problem.problem_id)
                        },
                    },
                    {
                        title: 'Title',
                        key: 'problem.title',
                        render: (h, params) => {
                            return h('span', {}, params.row.problem.title)
                        },
                    },
                    {
                        title: 'Status',
                        key: 'created_by.username',
                        render: (h, params) => {
                            return h('span', {}, params.row.created_by.username)
                        },
                    },
                    {
                        title: 'Defunct',
                        key: 'problem.defunct',
                        render: (h, params) => {
                            return h('Badge', {
                                props: {
                                    status: params.row.problem.defunct < 1 ? "default" : params.row.problem.defunct < 2 ? "success" : "error",
                                    text: params.row.problem.defunct < 1 ? "disable" : params.row.problem.defunct < 2 ? "public" : "private",
                                }
                            }, params.row.problem.defunct)
                        },
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        width: 250,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "修改"
                                    }
                                }, [h('Button', {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.$router.push({path: `/admin/problem/edit/${params.row.problem.problem_id}`});
                                        }
                                    },
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "ios-create",
                                            size: 17,
                                            // color: "#e1a331"
                                        }
                                    }),
                                ])]),
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "下载"
                                    }
                                }, [h('Button', {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {}
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "ios-download",
                                            size: 17,
                                            color: "#29d87c"
                                        }
                                    }),
                                ])]),
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "删除"
                                    }
                                }, [h('Button', {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    on: {
                                        click: () => {
                                            // this.remove(params.index)
                                        }
                                    }
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "md-trash",
                                            size: 17,
                                            color: "#e1a331"
                                        }
                                    }),
                                ])]),
                            ]);
                        }
                    }
                ],
                datas: [],
                selectData: [
                    {
                        created_by: {
                            "nickname": "就当一次路过丶",
                            "school": "hyit",
                            "user_id": 10,
                            "username": "morizunzhu"
                        },
                        problem: {
                            "create_by": 10,
                            "created_at": 1517101561000,
                            "problem_id": 2,
                            "title": "A+B",
                            "defunct": "1"
                        }
                    },
                ],
                nowTime: "2016-10-03 07:08:16",
                page: 1,
                per_page: 10,
            }
        },
        created: function () {
            _this = this;
            this.getProblems();
            this.selectData = this.datas;//表格数据
            this.debouncedsearchData = debounce(this.searchData, 500, null);//延时加载
        },
        mounted() {
            this.nowTime = (new Date()).getTime();//当前时间
        },
        methods: {
            //显示删除按钮
            selectionChange(selection) {
                this.deleteShowFlag = selection.length > 0;
            },
            //搜索查询表格
            searchData: function () {
                console.log("开始查询:" + this.searchKeyWord);
                if (this.searchKeyWord === "") {
                    this.selectData = this.datas;
                } else {
                    this.selectData = [];
                    let that = this;
                    this.datas.forEach(function (is, i) {
                        let item = that.datas[i];
                        if (item.username.indexOf(that.searchKeyWord) !== -1 || item.nickname.indexOf(that.searchKeyWord) !== -1) {
                            that.selectData.push(item);
                        }
                    });
                }
            },
            //字符串转date
            stringFormatToDate: function (str) {
                let time = new Date();
                //2018-08-08 09:09:09
                let y = str.substring(0, 4), m = str.substring(5, 7), d = str.substring(8, 10);
                let h = str.substring(11, 13), m_ = str.substring(14, 16), s = str.substring(17, 19);
                time.setFullYear(y, m, d);
                time.setHours(h, m_, s, 0);
                return time;
            },

            getContests: function () {
                this.tableLoadingFlag = true;
                console.log("刷新");
                Api.getContestsByPagePer_PageAndCreator(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    console.log(res.data);
                    if (result.code === 200) {
                        this.datas = result.data.problems;
                        this.selectData = this.datas;
                    } else if (result.code === 401) {
                        this.$Message.error("签名过期,请重新登录!");
                        window.location.replace("/admin/login");
                    }
                    this.tableLoadingFlag = false;
                }).catch(res => {

                    this.tableLoadingFlag = false;
                });
            }
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            }
        }
    }
</script>

<style scoped>

</style>