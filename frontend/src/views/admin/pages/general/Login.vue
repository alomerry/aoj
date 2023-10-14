<template>
    <div id="apps">
        <div class="sk-loading" v-if="loading">
            <div id="loading-img">
                <div class="sk-folding-cube">
                    <div class="sk-cube1 sk-cube"></div>
                    <div class="sk-cube2 sk-cube"></div>
                    <div class="sk-cube4 sk-cube"></div>
                    <div class="sk-cube3 sk-cube"></div>
                </div>
            </div>
        </div>
        <div>
            <transition name="traAni" enter-active-class="animated fadeIn" appear>
                <Row justify="center" align="middle">
                    <Col span="4" offset="10">
                        <Card style="height: 300px;margin-top: 200px;box-shadow: 0 0 25px #cac6c6;">
                            <div style="text-align:center">
                                <Form ref="formData" :model="formData" :rules="ruleData">
                                    <h2 style="margin: 30px 0 30px 0">Welcome to Online Judge</h2>
                                    <FormItem label="" prop="username">
                                        <Input v-model="formData.username" prefix="ios-contact" placeholder="Enter name"
                                               type="text" clearable
                                               style="width: 250px;"/>
                                    </FormItem>
                                    <FormItem label="" prop="pwd">
                                        <Input v-model="formData.pwd" prefix="md-key" placeholder="Enter passwd"
                                               type="password" clearable
                                               style="width: 250px;"/>
                                    </FormItem>
                                    <input v-model="formData.passwd" style="display: none;" type="hidden"/>
                                    <Button type="info" style="width: 250px" v-on:click="loginModalOk">Login</Button>
                                </Form>
                            </div>
                        </Card>
                    </Col>
                </Row>
            </transition>
        </div>
    </div>
</template>

<script>
    import {hex_md5} from '../../../../assets/md5.js'

    export default {
        name: 'Login',
        data() {
            return {
                formData: {
                    username: "",
                    pwd: "",
                },
                ruleData: {
                    username: [
                        {required: true, message: 'The name cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 5, message: 'Name no less than 5 words', trigger: 'blur'},
                    ],
                    pwd: [
                        {required: true, message: 'Password cannot be empty', trigger: 'blur'},
                        {type: 'string', min: 6, message: 'Password no less than 6 words', trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            /**
             * 登录请求
             */
            loginModalOk() {
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.passwd = hex_md5(encodeURIComponent(this.formData.pwd + 'onlinejudge'));
                        console.log("合法输入");
                        //清除passwd
                        this.$axios({
                            url: '/api-oj/api-oj/login',
                            method: 'post',
                            data: require('querystring').stringify(this.formData),
                        }).then(res => {
                            // 成功返回
                            console.info('后台返回的数据', res.data);
                            if (res.data.code === 200) {
                                this.$store.dispatch('login', res.data.data);
                                if (res.data.data.admin === true) {
                                    this.$Message.success('Success!');
                                    window.location.href = "/admin/home"
                                } else {
                                    this.$Message.error("无权访问!");
                                }
                            } else {
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
                    }
                });
            },
        },
        components: {},
        created() {
        }
    }
</script>
<style scoped>
    @import "../../../../assets/animate.css";
    @import "../../../../assets/loading.css";

    body {
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        margin: unset;
        width: 100%;
        height: 100%;
    }

    #apps {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(to right, #a8caba 0%, #5d4157 100%);
    }

    .ivu-form-item-error-tip {
        margin-left: 15px;
    }
</style>
