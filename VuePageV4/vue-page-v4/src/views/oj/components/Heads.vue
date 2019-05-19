<template>
    <div id="headerDiv">
        <Modal v-model="loginModal_flag" width="360" :mask-closable="false" shadow>
            <p slot="header" style="text-align:left">
                <span>Welcome to MOoj</span>
            </p>
            <div style="text-align:center; padding:3px">
                <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" v-if="!register_info_flag">
                    <FormItem label="" prop="username">
                        <Input v-model="formValidate.username" :maxlength="15" placeholder="请输入用户名" style="width: 320px"
                               clearable type="text" prefix="ios-contact"></Input>
                    </FormItem>
                    
                    <FormItem label="" prop="pwd">
                        <Input v-model="formValidate.pwd" placeholder="请输入密码" :maxlength="16" style="width: 320px" clearable type="password" prefix="md-key"></Input>
                    </FormItem>
                    
                    <input v-model="formValidate.passwd" style="display: none;" type="hidden"/>
                    <Button long type="success" @click.native="loginModalOk()" style="width: 320px">Login</Button>
                </Form>
                <Form ref="registerValidate" :model="registerValidate" :rules="ruleRegisterValidate" v-else>
                    <FormItem label="" prop="username">
                        <Input v-model="registerValidate.username" :maxlength="15" placeholder="请输入用户名" style="width: 320px;point-events:none;"
                               clearable type="text" prefix="ios-contact"></Input>
                    </FormItem>
                    
                    <FormItem label="" prop="email">
                        <Input v-model="registerValidate.email" placeholder="请输入邮箱" :maxlength="30" style="width: 320px" clearable type="text" prefix="md-mail"></Input>
                    </FormItem>
                    <FormItem label="" prop="pwd">
                        <Input v-model="registerValidate.pwd" placeholder="请输入密码" :maxlength="16" style="width: 320px" clearable type="password" prefix="md-key"></Input>
                    </FormItem>
                    <FormItem label="" prop="repwd">
                        <Input v-model="registerValidate.repwd" placeholder="请重复密码" :maxlength="16" style="width: 320px" clearable type="password" prefix="md-key"></Input>
                    </FormItem>
                    <FormItem label="" prop="verifycode">
                        <Input v-model="registerValidate.verifycode" placeholder="Captcha" :maxlength="4" style="width: 200px;float: left" clearable type="text" prefix="ios-flower"></Input>
                        <div @click="changeVerifyCode">
                            <img :src="verify_path"></img>
                        </div>
                    </FormItem>
                    <input v-model="registerValidate.passwd" style="display: none;" type="hidden"/>
                    <Button long type="info" @click.native="registerModalOk()" style="width: 320px">Register</Button>
                    <Button long type="default" @click.native="register_info_flag = false;" style="width: 320px;margin-top:10px ">Already registed? Login now!</Button>
                </Form>
            </div>
            <div slot="footer">
                <div v-if="!register_info_flag">
                    <router-link style="float: left;" to="" @click.native="register_info_flag= true">No account? Register now!</router-link>
                    <router-link style="float: right;" to="" @click.native="">Forget Password</router-link>
                    <br>
                </div>
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
                    <MenuItem name="contests" to="/contests">
                        <Icon type="ios-stats"/>
                        Contents
                    </MenuItem>
                    <MenuItem name="status" to="/status">
                        <Icon type="ios-construct"/>
                        Status
                    </MenuItem>
                    <Submenu name="rank">
                        <template slot="title">
                            <Icon type="ios-construct"/>
                            Rank
                        </template>
                        <MenuItem name="2-1" to="/acm-rank">ACM Rank</MenuItem>
                    </Submenu>
                    <Submenu name="about">
                        <template slot="title">
                            <Icon type="ios-help-circle"/>
                            About
                        </template>
                        <MenuItem name="3-1" to="/about">Judger</MenuItem>
                        <MenuItem name="3-3" to="/FAQ">FAQ</MenuItem>
                    </Submenu>
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
                                <DropdownItem @click.native="loginModal_flag =true;register_info_flag = true;">Register</DropdownItem>
                            </DropdownMenu>
                        </template>
                        <template v-else>
                            <a href="javascript:void(0)">
                                {{user.nickname}}
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem>
                                    <router-link to="/user-home">Home</router-link>
                                </DropdownItem>
                                <DropdownItem>
                                    <router-link to="/setting">Settings</router-link>
                                </DropdownItem>
                                <DropdownItem>Messages</DropdownItem>
                                <template v-if="admin">
                                    <DropdownItem v-on:click.native="GoToAdmin">Control</DropdownItem>
                                </template>
                                <DropdownItem :divided="true" @click.native="logout">Logout</DropdownItem>
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
    import Api from "../components/api"
    import {hex_md5} from '../../../assets/md5.js'
    import {mapActions, mapState, mapGetters} from 'vuex' //注册 action 和 state

    export default {
        name: 'Heads',

        data() {
            return {
                active_name: "home",
                user: null,
                admin: false,
                loginModal_flag: false,//登录模态框
                register_info_flag: false,//注册模态框

                verify_path: '/api/api-oj/getVerify?' + Math.random(),

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
                },

                registerValidate: {
                    username: '',
                    passwd: '',
                    pwd: '',
                    email: '',
                    repwd: '',
                    verifycode: '',
                },
                ruleRegisterValidate: {
                    username: [
                        {required: true, message: 'The name cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 5, message: 'Name no less than 5 words', trigger: 'blur'},
                    ],
                    pwd: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 6, message: 'Password no less than 6 words', trigger: 'blur'},
                    ],
                    repwd: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 6, message: 'Password no less than 6 words', trigger: 'blur'},
                    ],
                    email: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'email', message: 'Incorrect email format', trigger: 'blur'},
                    ],
                    verifycode: [
                        {required: true, message: 'Captcha cannot be empty', trigger: 'blur'},
                    ],
                },
            }
        },

        methods: {
            changeVerifyCode() {
                this.verify_path = '/api/api-oj/getVerify?' + Math.random();
            },
            //登录请求
            loginModalOk() {
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        // console.log("合法输入");
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
            //注册请求
            registerModalOk() {
                this.$refs['registerValidate'].validate((valid) => {
                    if (valid) {
                        Api.checkVerifyCode(this.registerValidate.verifycode).then(res => {
                            if (res) {
                                if (this.judgeRegisterInfo) {
                                    this.registerValidate.passwd = hex_md5(encodeURIComponent(this.registerValidate.pwd + 'onlinejudge'));
                                    //清除passwd
                                    Api.registerUser(this.registerValidate).then(res => {
                                        let result = res.data;
                                        if (result.code === 200) {
                                            this.loginModal_flag = false;
                                            this.$store.dispatch('login', result.data);
                                            this.user = this.$store.state.user;
                                            this.admin = this.$store.state.admin;
                                            this.$Message.success('Success!');
                                            this.register_info_flag = false;
                                        } else {
                                            this.loginModal_flag = true;
                                            this.$Message.error(result.message);
                                            console.log(result.message);
                                        }
                                    }).catch(res => {

                                    });
                                }
                            } else {
                                this.$Message.error("Captcha incorrect!");
                            }
                        }).catch(res => {
                            console.log(res);
                        });

                    } else {
                        this.loginModal_flag = true;
                    }
                });
            },
            //判断注册信息
            judgeRegisterInfo() {
                if (registerValidate.pwd != registerValidate.repwd) {
                    this.$Message.error("Password not same!");
                    return false;
                } else {
                    return true;
                }
            },
            //注销请求
            logout() {
                //TODO 请求注销
                this.$axios({
                    url: '/api/api-oj/logout',
                    method: 'post',
                    data: 'user_id = ' + this.user.user_id,
                }).then(res => {
                    console.info('后台返回的数据', res.data);
                    if (res.data.code === 200) {
                        this.$store.dispatch('logout');
                        this.user = null;
                    } else {
                        this.$store.dispatch('logout');
                        this.user = null;
                        console.log(res.data.message);
                    }
                });
            },
            GoToAdmin() {
                window.location.href = "/admin.html";
            },
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
        },
        mounted() {
            this.changeVerifyCode();
        },
        destroyed() {
        }
    };
</script>
