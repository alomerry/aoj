<template>
    <div id="headerDiv">
        <Modal v-model="loginModal_flag" width="360" :mask-closable="false" shadow>
            <p slot="header" style="text-align:left">

                <span>Welcome to MOoj</span>
            </p>
            <div style="text-align:center; padding:3px">
                <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
                    <FormItem label="" prop="username">
                        <Input v-model="formValidate.username" :maxlength="15" placeholder="请输入用户名" style="width: 320px"
                               clearable type="text" prefix="ios-contact"></Input>
                        <!--<Icon type="ios-contact" size="21"/>-->
                    </FormItem>
                    <FormItem label="" prop="pwd">
                        <Input v-model="formValidate.pwd" placeholder="请输入密码" :maxlength="16" style="width: 320px"
                               clearable type="password" prefix="md-key"></Input>
                    </FormItem>
                    <input v-model="formValidate.passwd" style="display: none;" type="hidden"/>
                    <Button long type="success" @click.native="loginModalOk()" style="width: 320px">Login</Button>
                </Form>
            </div>
            <div slot="footer">
                <a style="float: left;">No account? Register now!</a>
                <a style="float: right;">Forget Password</a>
                <br>
            </div>
        </Modal>
        <Row>
            <Col span="24">
                <Menu mode="horizontal" theme="light" :active-name="active_name" shadow ref="menu">
                    <Col span="3" style="font-size: 20px">Online Judge</Col>
                    <MenuItem name="home" to="/home">
                        <Icon type="ios-paper"/>
                        Home
                    </MenuItem>
                    <MenuItem name="problems" to="/problems">
                        <Icon type="ios-people"/>
                        Problems
                    </MenuItem>
                    <MenuItem name="contents" to="/contents">
                        <Icon type="ios-stats"/>
                        Contents
                    </MenuItem>
                    <MenuItem name="status" to="/status">
                        <Icon type="ios-construct"/>
                        Status
                    </MenuItem>
                    <MenuItem name="rank" to="/rank">
                        <Icon type="ios-construct"/>
                        Rank
                    </MenuItem>
                    <MenuItem name="about" @click.native="logger" to="/about">
                        <Icon type="ios-construct"/>
                        About
                    </MenuItem>
                    <!--<Submenu name="7">
                      <template slot="title">
                        <Icon type="ios-stats"/>
                        Login
                      </template>
                      <MenuItem name="7-1">Login</MenuItem>
                      <MenuItem name="7-2">Register</MenuItem>
                    </Submenu>-->
                    <Dropdown trigger="click" style="float:right;margin-right: 50px">
                        <template v-if="user === null">
                            <a href="javascript:void(0)">
                                Login
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem @click.native="loginModal_flag = true">Login</DropdownItem>
                                <DropdownItem>Register</DropdownItem>
                            </DropdownMenu>
                        </template>
                        <template v-else>
                            <a href="javascript:void(0)">
                                {{user.nickname}}
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem>Setting</DropdownItem>
                                <DropdownItem>Messages</DropdownItem>
                                <template v-if="admin">
                                    <DropdownItem v-on:click.native="GoToAdmin">Control</DropdownItem>
                                </template>
                                <DropdownItem @click.native="logout">Logout</DropdownItem>
                            </DropdownMenu>
                        </template>
                    </Dropdown>
                </Menu>
            </Col>
        </Row>
        <br>
    </div>
</template>
<script>
    import {hex_md5} from '../../../assets/md5.js'
    import {mapActions, mapState, mapGetters} from 'vuex' //注册 action 和 state

    export default {
        name: 'Heads',

        data() {
            return {
                active_name: "home",
                user: null,
                admin: false,
                loginModal_flag: false,
                formValidate: {
                    username: '',
                    passwd: '',
                    pwd: '',
                },
                ruleValidate: {
                    username: [
                        {required: true, message: 'The name cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 5, message: 'Name no less than 5 words', trigger: 'blur'},
                    ],
                    pwd: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 6, message: 'Password no less than 6 words', trigger: 'blur'},
                    ],
                }
            }
        },

        methods: {
            /**
             * 登录请求
             */
            loginModalOk() {
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        console.log("合法输入");
                        this.formValidate.passwd = hex_md5(encodeURIComponent(this.formValidate.pwd + 'onlinejudge'));
                        //清除passwd

                        this.$axios({
                            url: '/api/api-oj/login',
                            method: 'post',
                            data: require('querystring').stringify(this.formValidate),
                        }).then(res => {
                            // 成功返回
                            console.info('后台返回的数据', res.data);
                            if (res.data.code === 200) {
                                this.loginModal_flag = false;
                                this.$store.dispatch('login', res.data.data);
                                this.user = this.$store.state.user;
                                this.admin = this.$store.state.admin;
                                this.$Message.success('Success!');
                            } else {
                                this.loginModal_flag = true;
                                this.$Message.error(res.data.message);
                            }
                        }).catch(error => {
                            // 失败返回
                            console.info('报错的信息', error);
                            this.$Message.error(error);
                        });

                    } else {
                        console.log("非法输入");
                        this.$Message.error('input Error! Please Check!');
                        this.loginModal_flag = true;
                    }
                });
            },
            /**
             * 注销请求
             */
            logout() {
                //TODO 请求注销
                this.$axios({
                    url: '/api/api-oj/logout',
                    method: 'post',
                    data: 'user_id = ' + this.user.user_id,
                }).then(res => {
                    console.info('后台返回的数据', res.data);
                    if (res.data.code === 200) {
                    } else {
                    }
                });
                this.$store.dispatch('logout');
                this.user = null;
            },
            GoToAdmin() {
                window.location.href = "/admin.html";
            },
            logger() {
                // console.log("md5:" + hex_md5(encodeURIComponent('120211onlinejudge')));
            }
        },
        watch: {
            active_class: function (value, oldValue) {
                console.log("检测到active-class变化:" + value);
                this.active_name = value;
                this.$nextTick(function () {
                    this.$refs.menu.updateActiveName();
                    console.log("更新menu成功!");
                });
            }
        },
        computed: {
            active_class() {
                return this.$store.state.menu_active_name;
            }
        },
        created() {
            this.user = this.$store.state.user;
            this.admin = this.$store.state.admin;
        }
    };
</script>
