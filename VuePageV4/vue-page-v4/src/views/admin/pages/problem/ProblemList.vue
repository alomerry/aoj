<template>
    <div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px">
                <span style="font-size: 25px;font-weight: 400">Problems</span>
                <i-input v-model="searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
                <Button icon="ios-trash" type="warning" style="width: auto;float: right;margin-right: 15px" @click=""
                        v-show="deleteShowFlag">Delete
                </Button>
                <br><br>
            </div>
            <Table border ref="selection" :columns="columns" :data="selectData"
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

    export default {
        name: "ProblemList",
        data() {
            return {
                searchKeyWord: "",//搜索框的内容
                deleteShowFlag: false,
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: 'UserName',
                        key: 'user.username',
                        render: (h, params) => {
                            return h('span', {}, params.row.user.username)
                        },
                    },
                    {
                        title: 'NickName',
                        key: 'user.nickname',
                        render: (h, params) => {
                            return h('span', {}, params.row.user.nickname)
                        },
                    },
                    {
                        title: 'Create Time',
                        key: 'user.access_time',
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.user.access_time,
                                    type: 'datetime'
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
                            let v = row.user.access_time;
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
                        title: 'Last Login',
                        key: 'user.last_login',
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.user.last_login,
                                    type: 'datetime'
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
                            let v = _this.stringFormatToDate(row.user.last_login).getTime();
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
                        title: 'Email',
                        key: 'user.email',
                        render: (h, params) => {
                            return h('span', {}, params.row.user.email)
                        },
                    },
                    {
                        title: 'Admin Level',
                        key: 'privilege.rightstr',
                        render: (h, params) => {
                            return h('span', {}, params.row.privilege.rightstr)
                        },
                        filters: [
                            {
                                label: 'Super Admin',
                                value: "Super Admin"
                            },
                            {
                                label: 'Admin',
                                value: "Admin"
                            },
                            {
                                label: 'User',
                                value: "User"
                            },
                        ],
                        filterMultiple: true,
                        filterMethod(value, row) {
                            return row.privilege.rightstr.indexOf(value) > -1;
                        }
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        width: 250,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.changeUserModalShowFlag = true;
                                        }
                                    }
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "ios-create",
                                            size: 17,
                                            // color: "#e1a331"
                                        }
                                    }),
                                ]),
                                h('Button', {
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
                                    h('Poptip', {
                                        props: {
                                            trigger: "hover",
                                            title: "Delete",
                                        }
                                    }),
                                    h('Icon', {
                                        props: {
                                            type: "md-trash",
                                            size: 17,
                                            color: "#e1a331"
                                        }
                                    }),
                                ])
                            ]);
                        }
                    }
                ],
                datas: [],
                selectData: [
                    {
                        user: {
                            username: 'John Brown',
                            nickname: '就当一次路过',
                            access_time: '2018-12-03 07:08:16',
                            last_login: '2016-10-03 07:08:16',
                            email: 'WuJinCheng',
                        },
                        privilege: {
                            defunct: "A",
                            rightstr: "admin_ce",
                            user_id: 10.
                        },
                    },
                ],
                nowTime: "2016-10-03 07:08:16",
                page: 1,
                per_page: 10,
            }
        },
        created: function () {
            _this = this;
            // this.selectData = this.datas;//表格数据
            this.getUsers();
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

            getUsers: function () {
                Api.findUsersByPagePer_Page(this.page, this.per_page, this);
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