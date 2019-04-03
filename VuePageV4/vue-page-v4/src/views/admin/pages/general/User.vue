<template>
    <div>
        <div>
            <Modal v-model="changeUserModalShowFlag" title="Custom width" width="1000" shadow>
                <div slot="title">
                    <p>Customize width</p>
                </div>
                <div class="form-content">
                    <Form ref="form" :model="formItem" :rules="ruleForm" :label-width="120">
                        <Row>
                            <Col span="10">
                                <FormItem label="NickName" required prop="vnickname">
                                    <Input size="large" v-model="formItem.name"
                                           placeholder="Enter something..."></Input>
                                </FormItem>
                                <FormItem label="Email" required prop="vemail">
                                    <Input size="large" v-model="formItem.email"
                                           placeholder="Enter something..."></Input>
                                </FormItem>
                                <FormItem label="User Type">
                                    <Select size="large" v-model="formItem.level" title="" multiple @on-change="levelOptionChanged">
                                        <Option v-for="item in levelOption" :value="item.name" :key="item.id" :disabled="item.disabled">{{ item.name }}</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="11" offset="2">
                                <FormItem label="New Password" style="text-align: center" prop="vpasswd">
                                    <Input size="large" v-model="formItem.passwd"
                                           placeholder="Enter something..."></Input>
                                </FormItem>
                                <FormItem label="Problem Permission">
                                    <Select size="large" v-model="formItem.problem_permission" title="">
                                        <Option value="beijing">New York</Option>
                                        <Option value="shanghai">London</Option>
                                        <Option value="shenzhen">Sydney</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="7" offset="2">
                                <FormItem label="666">
                                    <i-switch v-model="formItem.switch" size="default">
                                        <span slot="open">️</span>
                                        <span slot="close"></span>
                                    </i-switch>
                                </FormItem>
                            </Col>
                            <Col span="7">
                                <FormItem label="Open API">
                                    <i-switch v-model="formItem.api" size="default">
                                        <span slot="open">️</span>
                                        <span slot="close"></span>
                                    </i-switch>
                                </FormItem>
                            </Col>
                            <Col span="7">
                                <FormItem label="Is Disabled">
                                    <i-switch v-model="formItem.disabled" size="default" @on-change="disableUser" :loading="formItem.disableUserSwitchLoadingFlag">
                                        <span slot="open"></span>
                                        <span slot="close"></span>
                                    </i-switch>
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </div>
                <div slot="footer">
                    <Button type="info" ghost size="large" @click="changeUserModalShowFlag=false">关闭</Button>
                    <Button type="info" size="large" :loading="updateUserInfoLoadingFlag" @click="">确定</Button>
                </div>
            </Modal>
        </div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px">
                <span style="font-size: 25px;font-weight: 400">User</span>
                <i-input v-model="searchKeyWord" placeholder="Keywords"
                         style="width: auto;float: right;margin-right: 50px;"
                         clearable>
                    <Icon type="ios-search" slot="prefix"/>
                </i-input>
                <!--<Button icon="ios-trash" type="warning" style="width: auto;float: right;margin-right: 15px" @click=""
                        v-show="deleteShowFlag">Delete
                </Button>-->
                <Button icon="md-refresh" style="float: right;margin-right: 20px"
                        @click.native="getUsers"></Button>
                <Button icon="ios-trash" type="warning" style="width: auto;float: right;margin-right: 15px" @click=""
                        v-show="deleteShowFlag">Delete
                </Button>
                <br><br>
            </div>
            <Table border ref="selection" :columns="columns" :data="selectData" :loading="loading"
                   @on-selection-change="selectionChange"></Table>
            <br>
            <Page :current="page" :page-size="per_page" size="small" style="float: right"/>
            <br>
        </Card>
        
        <Card style="margin: 15px">
            <div slot="title" style="height: 40px">
            </div>
        </Card>
        <Modal v-model="deleteUserModal.ModalFlag">
            <span slot="header" style="color:#f60;font-size: 21px;">
                <Icon type="ios-information-circle" size="30"></Icon>
                <span>&nbsp;Delete Confirmation</span>
            </span>
            <div style="font-size: 17px">
                <p>Sure to delete this user?</p>
                <p>This action cannot be undone!</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" :loading="deleteUserModal.ButtonLoadingFlag" @click="deleteUser">Delete</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import debounce from "lodash/debounce"
    import Api from "../../components/api"

    let _this = null;

    export default {
        name: "User",
        data() {
            return {
                levelOption: [
                    {
                        id: 0,
                        name: "user",
                        disabled: false,
                    },
                    {
                        id: 1,
                        name: "admin",
                        disabled: false,
                    },
                    {
                        id: 2,
                        name: "Authority Manager",
                        disabled: false,
                    },
                    {
                        id: 3,
                        name: "Topic Adder",
                        disabled: false,
                    },
                    {
                        id: 4,
                        name: "Contest Participant",
                        disabled: false,
                    },
                    {
                        id: 5,
                        name: "Code Viewer",
                        disabled: false,
                    },
                    {
                        id: 6,
                        name: "Manual Judger",
                        disabled: false,
                    },
                    {
                        id: 7,
                        name: "Remote Judger",
                        disabled: false,
                    },
                    {
                        id: 8,
                        name: "Announcement Manager",
                        disabled: false,
                    },
                    {
                        id: 9,
                        name: "User manager",
                        disabled: false,
                    },
                ],
                formItem: {
                    disabled: false,
                    name: '',
                    level: [],
                    api: '',
                    switch: '',
                    disableUserSwitchLoadingFlag: false,
                    passwd: '',
                    email: '',
                },
                ruleForm: {
                    vnickname: [
                        {required: true, message: 'The name cannot be empty', trigger: 'blur'}
                    ],
                    vemail: [
                        {required: true, message: 'Mailbox cannot be empty', trigger: 'blur'},
                        {type: 'email', message: 'Incorrect email format', trigger: 'blur'}
                    ],
                    vpasswd: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 6, message: 'Password no less than 6 words', trigger: 'blur'},
                    ],
                },
                deleteShowFlag: false,
                searchKeyWord: "",//搜索框的内容
                changeUserModalShowFlag: false,//修改用户信息模态框
                updateUserInfoLoadingFlag: false,//模态框确定按钮loading标记
                deleteUserModal: {
                    ButtonLoadingFlag: false,//删除用户的模态框确认删除的加载标记
                    ModalFlag: false,//删除用户的模态框显示标记
                    user_id: null,
                },
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: 'UserId',
                        width: 120,
                        key: 'user.user_id',
                        render: (h, params) => {
                            return h('span', {}, params.row.user.user_id)
                        },
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
                            let v = row.user.last_login;
                            //let v = _this.stringFormatToDate(row.user.last_login).getTime();
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
                            let levels = this.adminLevelArray(params.row.privilege != null ? params.row.privilege.rightstr : "user");
                            let res = [];
                            levels.forEach(function (item) {
                                res.push(h('Tag', {}, item));
                            });
                            return [
                                res,
                            ];
                        },
                        filters: [//[a]bc[d][e]fgh[i]jkl[m][n]opq[r][s]t[u]vwxyz
                            {
                                label: 'Announcement_manager',//公告管理者 l
                                value: "l"
                            },
                            {
                                label: 'User_manager',//用户管理者 o
                                value: "o"
                            },
                            {
                                label: 'Manual_judger',//手动判题者 j
                                value: "j"
                            },
                            {
                                label: 'Remote_judger',//远程判题者 k
                                value: "k"
                            },
                            {
                                label: 'Contest_participant',//比赛参加者 g
                                value: "g"
                            },
                            {
                                label: 'Code_viewer',//代码查看者 h
                                value: "h"
                            },
                            {
                                label: 'Contest_organizer',//比赛组织者 f
                                value: "f"
                            },
                            {
                                label: 'Topic_adder',//题目添加者 c
                                value: "c"
                            },
                            {
                                label: 'Authority_manager',//权限管理者 b
                                value: "b"
                            },
                            {
                                label: 'Admin',
                                value: "admin"
                            },
                            {
                                label: 'User',
                                value: "user"
                            },
                        ],
                        filterMultiple: true,
                        filterMethod(value, row) {
                            if (row.privilege == null) {
                                return "user".indexOf(value) > -1;
                            } else {
                                return row.privilege.rightstr.indexOf(value) > -1;
                            }
                        }
                    },
                    {
                        title: 'Option',
                        key: 'option',
                        width: 150,
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
                                            this.changeUserModalShowFlag = true;
                                            this.formItem = {
                                                disabled: params.row.user.disabled,
                                                name: params.row.user.nickname,
                                                level: this.adminLevelArray(params.row.privilege == null ? "user" : params.row.privilege.rightstr),
                                                api: '',
                                                switch: '',
                                                passwd: '',
                                                email: params.row.user.email,
                                            }
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
                                            this.deleteUserModal.user_id = params.row.user.user_id;
                                            this.deleteUserModal.ModalFlag = true;
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
                loading: false,//表格加载状态
                datas: [],
                selectData: [
                    {
                        privilege: {
                            "defunct": "A",
                            "rightstr": "admin",
                            "user_id": 10
                        },
                        user: {
                            "access_time": 1547637507000,
                            email: "morizunzhu@163.com",
                            "last_login": 1550710906000,
                            nickname: "就当一次路过丶",
                            "passwd": "5cba13819e624f8dc0a991a7691f3f82",
                            "remark": null,
                            "school": "hyit",
                            "session_id": "D4FAD0CEE3C3C10E166A3B0B2EC69C23",
                            "solved": 0,
                            "submit": 0,
                            "user_id": 10,
                            username: "morizunzhu",
                            disabled: false,
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
            this.selectData = this.datas;//表格数据
            this.getUsers();
            this.debouncedsearchData = debounce(this.searchData, 500, null);//延时加载
        },
        mounted() {
            this.nowTime = (new Date()).getTime();//当前时间
        },
        methods: {
            //update user
            updateUser() {

            },
            //switch is-disabled user
            disableUser(val) {
                this.formItem.disabled = val;
            },
            //modal-select 选中的Option变化
            levelOptionChanged(val) {
                if (val.length === 0) {
                    this.levelOption.forEach(function (currentValue, index) {
                        currentValue.disabled = false;
                    });
                    return;
                } else if (val.includes('user')) {
                    this.levelOption.forEach(function (currentValue, index) {
                        if (currentValue.id != 0) {
                            currentValue.disabled = true;
                        }
                    });
                } else {//if (val.length > 0)
                    this.levelOption[0].disabled = true;
                }

            },
            //生成权限arr
            adminLevelArray(level) {//<Tag>标签一</Tag>user,admin,Authority Manager,Topic Adder,Contest Participant,Code Viewer,Manual Judger,Remote Judger,Announcement Manager,User manager
                let levels = [];
                if (level.indexOf("user") > -1) {
                    levels.push("user");
                }
                if (level.indexOf("admin") > -1) {
                    levels.push("admin");
                }
                if (level.indexOf("b") > -1) {//权限管理者 b
                    levels.push("Authority Manager");
                }
                if (level.indexOf("c") > -1) {//题目添加者 c
                    levels.push("Topic Adder");
                }
                if (level.indexOf("f") > -1) {//比赛组织者 f
                    levels.push("Contest Organizer");
                }
                if (level.indexOf("g") > -1) {//比赛参加者 g
                    levels.push("Contest Participant");
                }
                if (level.indexOf("h") > -1) {//代码查看者 h
                    levels.push("Code Viewer");
                }
                if (level.indexOf("j") > -1) {//手动判题者 j
                    levels.push("Manual Judger");
                }
                if (level.indexOf("k") > -1) {//远程判题者 k
                    levels.push("Remote Judger");
                }
                if (level.indexOf("l") > -1) {//公告管理者 l
                    levels.push("Announcement Manager");
                }
                if (level.indexOf("o") > -1) {//用户管理者 o
                    levels.push("User manager");
                }
                return levels;
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
            //向后台查询数据
            getUsers: function () {
                this.loading = true;
                Api.getUsersByPagePer_Page(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 200) {
                        this.datas = result.data.users;
                        this.selectData = result.data.users;
                    } else {
                        console.log('Failed! ' + result.message);
                        this.$Message.error(result.message);
                        if (result.code === 401) {
                            window.location.href = "/admin/login";
                        }
                    }
                    this.loading = false;
                }).catch(err => {
                    console.log('An error has occurred! ' + err);
                    this.loading = false;
                });
            },
            //update user list
            updateDatas(user_id, user) {
                if (user != null) {
                    console.log("添加成功");
                    this.datas.splice(0, 1, user);
                } else {
                    let unUpdatedItemIndex = this.datas.findIndex(function (currentItem) {
                        return currentItem.user.user_id === user_id;
                    });
                    console.log("更新序号：" + unUpdatedItemIndex);
                    this.datas.splice(unUpdatedItemIndex, 1);
                }
                this.selectData = this.datas;
            },
            //
            deleteUser() {
                this.deleteUserModal.ButtonLoadingFlag = true;
                Api.deleteUserByUserId(this.deleteUserModal.user_id, this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.$Message.success("删除成功!");
                        this.updateDatas(this.deleteUserModal.user_id, null);
                    } else {
                        console.log("错误代码:" + result.code);
                        this.$Message.error(result.message);
                    }
                    this.deleteUserModal.ButtonLoadingFlag = false;
                }).catch(res => {
                    console.log(res);
                    this.deleteUserModal.ButtonLoadingFlag = false;
                });
                this.deleteUserModal.ModalFlag = false;
            }
        },
        watch: {
            searchKeyWord: function (newVal, oldVal) {
                this.debouncedsearchData();
            },
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