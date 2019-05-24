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
                                <FormItem label="NickName" required prop="nickname">
                                    <Input size="large" v-model="formItem.nickname"
                                           placeholder="Enter something..."></Input>
                                </FormItem>
                                <FormItem label="Email" required prop="email">
                                    <Input size="large" v-model="formItem.email"
                                           placeholder="Enter something..."></Input>
                                </FormItem>
                                <FormItem label="User Type">
                                    <Select size="large" v-model="formItem.level" title="" multiple @on-change="levelOptionChanged">
                                        <Option v-for="item in levelOption" :value="item.value" :key="item.id" :disabled="item.disabled">{{ item.name }}</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="11" offset="2">
                                <FormItem label="New Password" style="text-align: center" prop="passwd">
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
                    <Button type="info" size="large" :loading="updateUserInfoLoadingFlag" @click="updateUser">确定</Button>
                </div>
            </Modal>
        </div>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px">
                <span style="font-size: 25px;font-weight: 400">User</span>
            </div>
            <div slot="extra">
                <i-input v-model="searchKeyWord" placeholder="username nickname"
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
            <Page :total="total" show-sizer
                  @on-change="changePage"
                  @on-page-size-change="changePageSize"
                  :page-size="per_page"
                  style="float: right"/>
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
    import {hex_md5} from '../../../../assets/md5.js'

    let _this = null;

    export default {
        name: "User",
        data() {
            return {
                levelOption: [
                    {
                        id: 0,
                        name: "user",
                        value: "user",
                        disabled: false,
                    },
                    {
                        id: 1,
                        name: "admin",
                        value: "admin",
                        disabled: false,
                    },
                    {
                        id: 2,
                        value: "b",
                        name: "Authority Manager",
                        disabled: false,
                    },
                    {
                        id: 3,
                        value: "c",
                        name: "Topic Adder",
                        disabled: false,
                    },
                    {
                        id: 4,
                        value: "g",
                        name: "Contest Participant",
                        disabled: false,
                    },
                    {
                        id: 5,
                        value: "h",
                        name: "Code Viewer",
                        disabled: false,
                    },
                    {
                        id: 6,
                        name: "Manual Judger",
                        value: "j",
                        disabled: false,
                    },
                    {
                        id: 7,
                        name: "Remote Judger",
                        value: "k",
                        disabled: false,
                    },
                    {
                        id: 8,
                        name: "Announcement Manager",
                        value: "l",
                        disabled: false,
                    },
                    {
                        id: 9,
                        name: "User manager",
                        value: "o",
                        disabled: false,
                    },
                    {
                        id: 10,
                        name: "Contest Organizer",
                        value: "f",
                        disabled: false,
                    },
                ],
                formItem: {
                    disabled: false,
                    nickname: '',
                    level: [],
                    api: '',
                    switch: '',
                    disableUserSwitchLoadingFlag: false,
                    passwd: '',
                    email: '',
                    user_id: '',
                },
                ruleForm: {
                    nickname: [
                        {type: 'string', min: 2, message: 'Name no less than 3 words', trigger: 'blur'},
                        {type: 'string', max: 10, message: 'Name no less than 10 words', trigger: 'blur'},
                    ],
                    email: [
                        {type: 'email', message: 'Incorrect email format', trigger: 'blur'}
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
                        type: 'index',
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
                        title: 'UserId',
                        width: 80,
                        key: 'user.user_id',
                        render: (h, params) => {
                            return h('span', {}, params.row.user.user_id)
                        },
                    },
                    {
                        title: 'NickName',
                        key: 'user.nickname',
                        width: 130,
                        render: (h, params) => {
                            return h('span', {}, params.row.user.nickname)
                        },
                    },
                    {
                        title: 'Status',
                        width: 130,
                        render: (h, params) => {
                            return h('Tag', {
                                props: {
                                    color: params.row.user.disabled ? 'error' : 'success',
                                }
                            }, params.row.user.disabled ? 'Disabled' : 'Access');
                        },
                    },
                    {
                        title: 'Create Time',
                        key: 'user.access_time',
                        width: 150,
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
                        width: 150,
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
                            let levels = this.adminLevelArray(params.row.privilege == null ? "user" : params.row.privilege.rightstr == "" ? "user" : params.row.privilege.rightstr);
                            let res = [];
                            levels.forEach(function (item) {
                                res.push(h('Tag', {}, item.name));
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
                                            this.updateUserInfoLoadingFlag = false;
                                            this.changeUserModalShowFlag = true;
                                            this.formItem = {
                                                disabled: params.row.user.disabled,
                                                nickname: params.row.user.nickname,
                                                level: this.adminLevelSimpleyArray(params.row.privilege == null ? "user" : params.row.privilege.rightstr == "" ? "user" : params.row.privilege.rightstr),
                                                api: '',
                                                switch: '',
                                                passwd: '',
                                                user_id: params.row.user.user_id,
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
                total: 10,
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
            changePageSize(pageSize) {
                this.per_page = pageSize;
                this.getUsers();
            },
            changePage(page) {
                this.page = page;
                this.getUsers();
            },
            //update user
            updateUser() {
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        this.updateUserInfoLoadingFlag = true;
                        let user = {
                            user_id: "" + this.formItem.user_id,
                            disabled: this.formItem.disabled ? "true" : "false",
                            nickname: this.formItem.nickname,
                            level: this.formatLevelToString(this.formItem.level),
                            passwd: this.formItem.passwd != "" ? hex_md5(encodeURIComponent(this.formItem.passwd + 'onlinejudge')) : "",
                            email: this.formItem.email,
                        }
                        Api.updateUser(user, this.$store.state.token).then(res => {
                            let result = res.data;
                            if (result.code === 200) {
                                this.$Message.success("修改成功!");
                                this.changeUserModalShowFlag = false;
                                this.updateUserInfoLoadingFlag = false;
                                this.getUsers();
                            } else if (result.code === 401) {
                                window.location.replace("/admin/login");
                            } else {
                                console.log(result.code);
                                this.$Message.error(result.message);
                            }
                            this.updateUserInfoLoadingFlag = false;
                        }).catch(res => {
                            console.log(res.message);
                            this.$Message.error("内部错误！修改失败！")
                            this.updateUserInfoLoadingFlag = false;
                        });

                    } else {
                        console.log("非法输入");
                        this.$Message.error('Input Error! Please Check!');
                    }
                });
            },
            //make level_arr to level_string
            formatLevelToString(arr) {
                if (arr == null || arr.length == 0 || arr.indexOf("user") > -1) {
                    console.log("low user");
                    return "";
                } else {
                    let result = "";
                    arr.forEach(function (currentValue) {
                        if (currentValue != "admin") {
                            result += currentValue;
                        }
                    });
                    return result === "" ? "admin" : "admin_" + result;
                }
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
                    levels.push({
                        name: "user",
                        value: "user"
                    });
                }
                if (level.indexOf("admin") > -1) {
                    levels.push({
                        name: "admin",
                        value: "admin"
                    });
                }
                if (level.indexOf("b") > -1) {//权限管理者 b
                    levels.push({
                        name: "Authority Manager",
                        value: "b"
                    });
                }
                if (level.indexOf("c") > -1) {//题目添加者 c
                    levels.push({
                        name: "Topic Adder",
                        value: "c"
                    });
                }
                if (level.indexOf("f") > -1) {//比赛组织者 f
                    levels.push({
                        name: "Contest Organizer",
                        value: "f"
                    });
                }
                if (level.indexOf("g") > -1) {//比赛参加者 g
                    levels.push({
                        name: "Contest Participant",
                        value: "g"
                    });
                }
                if (level.indexOf("h") > -1) {//代码查看者 h
                    levels.push({
                        name: "Code Viewer",
                        value: "h"
                    });
                }
                if (level.indexOf("j") > -1) {//手动判题者 j
                    levels.push({
                        name: "Manual Judger",
                        value: "j"
                    });
                }
                if (level.indexOf("k") > -1) {//远程判题者 k
                    levels.push({
                        name: "Remote Judger",
                        value: "k"
                    });
                }
                if (level.indexOf("l") > -1) {//公告管理者 l
                    levels.push({
                        name: "Announcement Manager",
                        value: "l"
                    });
                }
                if (level.indexOf("o") > -1) {//用户管理者 o
                    levels.push({
                        name: "User manager",
                        value: "o"
                    });
                }
                return levels;
            },
            //生成权限arr  eg admin_ol => admin o l
            adminLevelSimpleyArray(level) {//<Tag>标签一</Tag>user,admin,Authority Manager,Topic Adder,Contest Participant,Code Viewer,Manual Judger,Remote Judger,Announcement Manager,User manager
                let levels = [];
                if (level.indexOf("user") > -1) {
                    levels.push("user");
                    return levels;
                } else {
                    if (level.indexOf("_") > -1) {//多权限
                        level = level.slice(level.indexOf("_") + 1);
                        levels.push("admin");
                        return levels.concat(level.split(""));
                    } else {
                        levels.push("admin");
                        return levels;
                    }
                }
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
                        if (item.user.username.indexOf(that.searchKeyWord) !== -1 || item.user.nickname.indexOf(that.searchKeyWord) !== -1) {
                            that.selectData.push(item);
                        }
                    });
                    if (this.searchData.length == 0) {
                        console.log("本页未查询到，查询数据库");
                        Api.findSimilarUserByKeycode(this.searchKeyWord, this.$store.state.token).then(res => {
                            let result = res.data;
                            if (result.code == 200) {
                                console.log(result.data);
                            }
                        }).catch(res => {
                            console.log(res);
                        })
                    }
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
                        this.total = result.data.total;
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
                    } else if (result.code === 401) {
                        window.location.replace("/admin/login");
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
        border-color: #ff3000;
        background-color: #ff3000;
    }
    
    .ivu-switch {
        /*border: 1px solid rgb(255, 73, 73);
        background-color: rgb(255, 73, 73);*/
    }
</style>