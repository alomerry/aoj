<template>
    <div>
        <Modal v-model="modal_falg" width="900">
            <div slot="header">
                <span style="font-size: 20px;font-weight: 400;">Add Contest Problem</span>
                <i-input v-model="modal_searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;margin-bottom: 10px"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
            </div>
            <div>
                <Table width="855" :columns="publicProblemColumns" :data="modal_selectData" stripe :loading="modal_loading"></Table>
            </div>
            <div slot="footer" style="height: 25px">
                <Page :current="1" :page-size="10" size="small" style="float: right"/>
            </div>
        </Modal>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Problems</span>
            </div>
            <div slot="extra">
                <i-input v-model="searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
                <Button icon="md-refresh" style="float: right;margin-right: 20px"
                        @click.native="getProblems"></Button>
                <Button icon="ios-trash" type="warning" style="width: auto;float: right;margin-right: 15px" @click=""
                        v-show="deleteShowFlag">Delete
                </Button>
                <br><br>
            </div>
            <Table ref="selection" :columns="columns" :data="selectData" :loading="tableLoadingFlag"
                   @on-selection-change="selectionChange" stripe>
            </Table>
            <br>
            <Button type="info" style="font-size: 10px;margin-right: 10px" to="/admin/problem/create">
                <Icon type="ios-add" size="17"/>
                create
            </Button>
            <Button type="info" style="font-size: 10px;" v-show="contest_Add" @click="addPublicProblems">
                <Icon type="ios-add" size="17"/>
                Add From Public Problem
            </Button>
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

    let _this;
    export default {
        name: "ProblemList",
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                deleteShowFlag: false,//勾选批量时显示删除按钮标记
                tableLoadingFlag: false,//表格加载中标记
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: 'Problem_id',
                        key: 'problem.problem_id',
                        width: 150,
                        sortable: true,
                        align: "center",
                        render: (h, params) => {
                            return h('span', {}, params.row.problem.problem_id)
                        },
                    },
                    {
                        title: 'Title',
                        width: 250,
                        key: 'problem.title',
                        render: (h, params) => {
                            return h('a', {
                                on: {
                                    click: () => {
                                        alert(666);
                                    }
                                }
                            }, params.row.problem.title)
                        },
                    },
                    {
                        title: 'Author',
                        width: 200,
                        key: 'created_by.username',
                        render: (h, params) => {
                            return h('span', {}, params.row.created_by.username)
                        },
                    },
                    {
                        title: 'Create Time',
                        key: 'problem.created_at',
                        sortable: true,
                        width: 250,
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.problem.created_at,
                                    type: 'datetime',
                                },
                            })
                        },
                        filters: [
                            {
                                label: 'Last year',
                                value: 1
                            },
                            {
                                label: 'Last quarter',
                                value: 2
                            },
                            {
                                label: 'Last mouth',
                                value: 3
                            },
                            {
                                label: 'Last week',
                                value: 4
                            }
                        ],
                        filterMultiple: false,
                        filterMethod(value, row) {
                            let v = row.problem.created_at;
                            if (value === 1) {
                                return v > _this.nowTime - 365 * 24 * 3600000;
                            } else if (value === 2) {
                                return v > _this.nowTime - 90 * 24 * 3600000;
                            } else if (value === 3) {
                                return v > _this.nowTime - 30 * 24 * 3600000;
                            } else if (value === 4) {
                                return v > _this.nowTime - 7 * 24 * 3600000;
                            }
                        }
                    },
                    {
                        title: 'Level',
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
                        title: "Visiable",
                        key: "problem.defunct",
                        render: (h, params) => {
                            return h(
                                'i-switch',
                                {
                                    props: {
                                        size: 'default',
                                        value: params.row.problem.defunct === '1',
                                        disabled: (params.row.created_by.user_id) === this.$store.getters.localUserId ? false : true,
                                    }
                                },
                                [
                                    h(
                                        'span',
                                        {
                                            props: {
                                                slot: 'open'
                                            }
                                        },
                                        []
                                    ),
                                    h(
                                        'span',
                                        {
                                            props: {
                                                slot: 'close'
                                            }
                                        },
                                        []
                                    ),
                                ]
                            )
                        }
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        width: 250,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                (params.row.created_by.user_id) === this.$store.getters.localUserId ?
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
                                    ])]) : null,
                                h("Tooltip", {
                                        props: {
                                            placement: "top",
                                            theme: "light",
                                            content: "下载"
                                        }
                                    }, [
                                        h('Button', {
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
                                            ]
                                        )
                                    ]
                                ),
                                (params.row.created_by.user_id) === this.$store.getters.localUserId ?
                                    h("Tooltip", {
                                            props: {
                                                placement: "top",
                                                theme: "light",
                                                content: "删除"
                                            }
                                        }, [
                                            h('Button', {
                                                    props: {
                                                        type: 'default',
                                                        size: 'large',
                                                    },
                                                    on: {
                                                        click: () => {
                                                            this.tableLoadingFlag = true;

                                                            this.$Modal.confirm({
                                                                title: 'Confirm',
                                                                content: '<p>Are you sure to delete, only no submitt can be deleted</p>',
                                                                loading: true,
                                                                onOk: () => {
                                                                    if (!this.contest_Add) {
                                                                        //删除题目
                                                                        Api.deleteProblemByProblemId(
                                                                            params.row.problem.problem_id,
                                                                            this.$store.state.token
                                                                        ).then(res => {
                                                                            let result = res.data;
                                                                            switch (result.code) {
                                                                                case 200: {
                                                                                    this.$Message.success("删除成功!");
                                                                                    this.updateDatas(params.row.problem.problem_id, null);
                                                                                    break;
                                                                                }
                                                                                case 401: {
                                                                                    this.$Message.error("签名过期,请重新登录!");
                                                                                    window.location.replace("/admin/login");
                                                                                    break;
                                                                                }
                                                                                default: {
                                                                                    this.$Message.error(result.message);
                                                                                    break;
                                                                                }
                                                                            }
                                                                            this.tableLoadingFlag = false;
                                                                            this.$Modal.remove();
                                                                        }).catch(res => {
                                                                            console.log("错误原因:" + res);
                                                                            this.tableLoadingFlag = false;
                                                                            this.$Modal.remove();
                                                                            this.$Message.success('Delete failed!');
                                                                        })
                                                                    } else {
                                                                        //从竞赛中删除题目
                                                                        Api.deleteProblemFromContest(this.contest_id,
                                                                            params.row.problem.problem_id,
                                                                            this.$store.state.token
                                                                        ).then(res => {
                                                                            let result = res.data;
                                                                            // console.log("正在从竞赛[" + this.contest_id + "]中删除题目[" + params.row.problem.problem_id + "]...")
                                                                            // console.log(result);
                                                                            switch (result.code) {
                                                                                case 200: {
                                                                                    this.$Message.success("删除成功!");
                                                                                    this.updateDatas(params.row.problem.problem_id, null);
                                                                                    break;
                                                                                }
                                                                                case 401: {
                                                                                    this.$Message.error("签名过期,请重新登录!");
                                                                                    window.location.replace("/admin/login");
                                                                                    break;
                                                                                }
                                                                                case 403: {
                                                                                    this.$Message.error(result.message);//权限不足
                                                                                    break;
                                                                                }
                                                                                case 400: {
                                                                                    this.$Message.error(result.message);//删除题目失败
                                                                                    break;
                                                                                }
                                                                            }
                                                                            this.tableLoadingFlag = false;
                                                                            this.$Modal.remove();
                                                                        }).catch(res => {
                                                                            console.log("错误原因:" + res);
                                                                            this.tableLoadingFlag = false;
                                                                            this.$Modal.remove();
                                                                            this.$Message.error('Delete !');
                                                                        })
                                                                    }
                                                                },
                                                                onCancel: () => {
                                                                    this.tableLoadingFlag = false;
                                                                    this.$Modal.remove();
                                                                },
                                                            });

                                                        }
                                                    }
                                                }, [
                                                    h('Icon', {
                                                            props: {
                                                                type: "md-trash",
                                                                size: 17,
                                                                color: "#e1a331"
                                                            }
                                                        }
                                                    ),
                                                ]
                                            )
                                        ]
                                    ) : null,
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

                publicProblemColumns: [//problem_id,submit,defunct,accepted,created_at,create_by,title,display_id
                    {
                        title: 'Problem_id',
                        key: 'problem_id',
                        width: 150,
                        align: "center",
                        render: (h, params) => {
                            return h('span', {}, params.row.problem_id)
                        },
                    },
                    {
                        title: 'Title',
                        width: 250,
                        key: 'title',
                        render: (h, params) => {
                            return h('span', {}, params.row.title)
                        },
                    },
                    {
                        title: 'Author',
                        width: 200,
                        key: 'create_by',
                        render: (h, params) => {
                            return h('span', {}, params.row.create_by)
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
                                        content: "Add"
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
                                        //将题目添加至竞赛
                                        click: () => {
                                            Api.addPublicProblemToContest(this.contest_id, params.row.problem_id, this.$store.state.token).then(res => {
                                                let result = res.data;
                                                // console.log("正在从竞赛[" + this.contest_id + "]中添加题目[" + params.row.problem_id + "]...")
                                                // console.log(result);
                                                switch (result.code) {
                                                    case 200: {
                                                        this.$Message.success("添加成功!");
                                                        this.getProblems();
                                                        break;
                                                    }
                                                    case 401: {
                                                        this.$Message.error("签名过期,请重新登录!");
                                                        window.location.replace("/admin/login");
                                                        break;
                                                    }
                                                    case 403: {
                                                        this.$Message.error(result.message);//权限不足
                                                        break;
                                                    }
                                                    case 400: {
                                                        this.$Message.error(result.message);//添加题目失败
                                                        break;
                                                    }
                                                }
                                            }).catch(res => {
                                                console.log("错误原因:" + res);
                                            })
                                        }
                                    }
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "md-add",
                                            size: 17,
                                            color: "#525252"
                                        }
                                    }),
                                ])]),
                            ]);
                        }
                    }
                ],
                modal_loading: false,
                modal_page: 1,
                modal_per_page: 10,
                modal_datas: [],
                modal_selectData: [
                    {
                        "problem_id": 2,
                        "accepted": 0,
                        "click": null,
                        "create_by": 10,
                        "created_at": 1517101561000,
                        "defunct": "1",
                        "description": null,
                        "display_id": 1,
                        "hint": null,
                        "input": null,
                        "memory_limit": null,
                        "output": null,
                        "sample_input": null,
                        "sample_output": null,
                        "source": null,
                        "submit": 0,
                        "time_limit": null,
                        "title": "A+B"
                    }
                ],
                modal_searchKeyWord: "",

                contest_Add: false,//contest 页面的添加问题
                contest_id: null,
                modal_falg: false,
            }
        },
        created: function () {
            _this = this;
            this.contest_id = this.$route.params.contest_id;
            this.contest_Add = this.contest_id == null ? false : true;

            this.debouncedModalsearchData = debounce(this.modal_searchData, 500, null);//延时加载
            this.debouncedsearchData = debounce(this.searchData, 500, null);//延时加载
        },
        mounted() {
            this.nowTime = (new Date()).getTime();//当前时间
            console.log(this.nowTime);
            this.getProblems();
            this.selectData = this.datas;//表格数据
        },
        methods: {
            //添加公开题目modal
            addPublicProblems() {
                this.modal_falg = true;
                this.getPublicProblems();
            },
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
                        if (is.created_by.username.indexOf(that.searchKeyWord) !== -1 ||
                            is.created_by.nickname.indexOf(that.searchKeyWord) !== -1 ||
                            is.problem.problem_id == that.searchKeyWord ||
                            is.problem.title.indexOf(that.searchKeyWord) !== -1
                        ) {
                            that.selectData.push(is);
                        }
                    });
                }
            },
            //搜索查询模态框表格
            modal_searchData: function () {
                console.log("开始查询:" + this.modal_searchKeyWord);
                if (this.modal_searchKeyWord === "") {
                    this.modal_selectData = this.modal_datas;
                } else {
                    this.modal_selectData = [];
                    let that = this;
                    this.modal_datas.forEach(function (is, i) {
                        let item = that.modal_datas[i];
                        if (("" + item.problem_id).indexOf(that.modal_searchKeyWord) !== -1 ||
                            item.title.indexOf(that.modal_searchKeyWord) !== -1 ||
                            (item.create_by + "").indexOf(that.modal_searchKeyWord) !== -1) {
                            that.modal_selectData.push(item);
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
            //获取题目集
            getProblems: function () {
                this.tableLoadingFlag = true;
                if (!this.contest_Add) {
                    //查询题目集合
                    Api.getProblemsByPagePer_Page(this.page, this.per_page, this.$store.state.token).then(res => {
                        let result = res.data;
                        if (result.code === 200) {
                            this.datas = result.data.problems;
                            this.selectData = this.datas;
                        } else if (result.code === 401) {
                            this.$Message.error("签名过期,请重新登录!");
                            window.location.replace("/admin/login");
                        }
                        this.tableLoadingFlag = false;
                    }).catch(res => {
                        console.log(res);
                        this.tableLoadingFlag = false;
                    });
                } else {
                    Api.getContestProblemsByPageAndContestId(this.page, this.per_page, this.contest_id, this.$store.state.token).then(res => {
                        let result = res.data;
                        console.log(result);
                        if (result.code === 200) {
                            this.datas = result.data.problems;
                            this.selectData = this.datas;
                        } else if (result.code === 401) {
                            this.$Message.error("签名过期,请重新登录!");
                            window.location.replace("/admin/login");
                        }
                        this.tableLoadingFlag = false;
                    }).catch(res => {
                        console.log(res);
                        this.tableLoadingFlag = false;
                    });
                }
            },
            //获取公开题目集
            getPublicProblems: function () {
                this.modal_loading = true;
                Api.findPublicProblemsByPage(this.modal_page, this.modal_per_page).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.modal_datas = result.data.results;
                        this.modal_selectData = this.modal_datas;
                    } else if (result.code === 401) {
                        this.$Message.error("签名过期,请重新登录!");
                        window.location.replace("/admin/login");
                    }
                    this.modal_loading = false;
                }).catch(res => {
                    console.log(res);
                    this.modal_loading = false;
                });
            },
            //修改list表格
            updateDatas(problem_id, problem) {
                if (problem != null) {
                    console.log("添加成功");
                    this.datas.splice(0, 1, problem);
                } else {
                    let unUpdatedItemIndex = this.datas.findIndex(function (currentItem) {
                        return currentItem.problem.problem_id === problem_id;
                    });
                    console.log("更新序号：" + unUpdatedItemIndex);
                    this.datas.splice(unUpdatedItemIndex, 1);
                }
            },
            //修改modal表格
            updateModalDatas() {

            },
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            },
            modal_searchKeyWord: function (newVal, oldVal) {
                this.debouncedModalsearchData();
            }
        },
        beforeRouteEnter(to, from, next) {
            if (to.path.startsWith("/admin/contest")) {
            }
            next(vm => {
                vm.contest_Add = to.params.contest_id == null ? false : true;
                vm.getProblems();
            });
        }
    }
</script>

<style scoped>
    table {
        font-size: 25px;
    }
    
    td {
        font-size: 25px;
    }
    
    form > label {
        text-align: center;
    }
    
    .form-content {
        margin: 15px 0 15px 0;
    }
    
    .ivu-form .ivu-form-item-label {
        font-size: 15px;
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