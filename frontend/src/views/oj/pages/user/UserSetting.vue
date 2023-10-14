<template>
    <div style="margin: 0 150px 0 150px;text-align: left">
        <Card>
            <Layout>
                <Sider hide-trigger style="background: #fff;border-right: 1px gainsboro solid">
                    <div class="demo-avatar" style="margin:30px 0 60px 60px">
                        <Avatar size="large" style="width: 50px;height: 50px" :src="userInfo.head_img"/>
                    </div>
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']" @on-select="changeInfo">
                        <MenuItem name="Profile">
                            <Icon type="md-document"/>
                            Profile
                        </MenuItem>
                        <MenuItem name="Account">
                            <Icon type="md-chatbubbles"/>
                            Account
                        </MenuItem>
                        <!--<MenuItem name="Security">
                            <Icon type="md-heart"/>
                            Security
                        </MenuItem>-->
                    </Menu>
                </Sider>
                <Layout>
                    <Content style="padding:5px 24px 5px 35px;minHeight: 500px;background: #fff;">
                        <Form v-if="selectedItemName =='Profile'" :model="ProfileItem">
                            <label class="form-title">Avatar Setting</label>
                            <div class="form-container">
                                <FormItem>
                                    <Upload
                                            multiple
                                            type="drag"
                                            name="image"
                                            :format="['jpg','png']"
                                            action="/api-oj/api-oj/simditorImage"
                                            :on-success="updateHeadSuccess">
                                        <div style="padding: 20px 0">
                                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                                            <p>Click or drag files here to upload</p>
                                        </div>
                                    </Upload>
                                </FormItem>
                            </div>
                            <label class="form-title">Profile Setting</label>
                            <div class="form-container">
                                <Row>
                                    <Col span="9">
                                        <FormItem label="Nickname">
                                            <Input v-model="ProfileItem.nickname"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="School">
                                            <Input v-model="ProfileItem.school"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Github">
                                            <Input v-model="ProfileItem.github_url"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>

                                    </Col>
                                    <Col span="9" offset="3">
                                        <FormItem label="Blog">
                                            <Input v-model="ProfileItem.blog_url"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Personal Words">
                                            <Input v-model="ProfileItem.own_words"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Button type="info" @click="saveProfile">Save All</Button>
                            </div>
                        </Form>
                        <Form v-else-if="selectedItemName =='Account'" :model="AccountItem">
                            <Row>
                                <Col span="9">
                                    <label class="form-title">Change Password</label>
                                    <div class="form-container">
                                        <FormItem label="Old Password">
                                            <Input v-model="AccountItem.oldPwd" type="password"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="New Password">
                                            <Input v-model="AccountItem.newPwd" type="password"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Confirm New Password">
                                            <Input v-model="AccountItem.confirmNewPwd" type="password"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                    </div>
                                </Col>
                                <Col span="9" offset="3">
                                    <label class="form-title">Change Email</label>
                                    <div class="form-container">
                                        <FormItem label="New Email">
                                            <Input v-model="AccountItem.newEmail"
                                                   placeholder="Enter something..."></Input>
                                        </FormItem>
                                    </div>
                                </Col>
                            </Row>
                            <Button type="info" @click="saveAccount">Save All</Button>
                        </Form>
                        <!--<Form v-else :model="SecurityItem"></Form>-->
                    </Content>
                </Layout>
            </Layout>
        </Card>
    </div>
</template>

<script>
    import Api from "../../components/api"
    import {hex_md5} from '../../../../assets/md5.js'

    export default {
        name: "UserSetting",
        data() {
            return {
                userInfo: {
                    head_img: "",
                },

                selectedItemName: "Profile",

                ProfileItem: {
                    header_img: "",
                    nickname: "",
                    school: "",
                    github_url: "",
                    blog_url: "",
                    own_words: "",
                },
                AccountItem: {
                    username:"",
                    oldPwd: "",
                    newPwd: "",
                    confirmNewPwd: "",
                    currentPwd: "",
                    newEmail: "",
                },
                SecurityItem: {},
            }
        },
        mounted() {
            this.getInfo();
        },
        methods: {
            changeInfo(name) {
                switch (name) {
                    case "Profile": {
                        this.selectedItemName = "Profile";
                        break;
                    }
                    case "Account": {
                        this.selectedItemName = "Account";
                        break;
                    }
                    case "Security": {
                        this.selectedItemName = "Security";
                        break;
                    }
                }
            },
            saveProfile() {
                let user = {
                    nickname: this.ProfileItem.nickname,
                    school: this.ProfileItem.school,
                    github_url: this.ProfileItem.github_url,
                    blog_url: this.ProfileItem.blog_url,
                    own_words: this.ProfileItem.own_words,
                };
                Api.updateUserProfile(this.$store.state.token, user).then(res => {
                    console.log(res.data);
                    if (res.data.code == 200) {
                        this.$Message.success("save success!");
                    } else {
                        this.$Message.error("save failed!");
                    }
                }).catch(res => {
                    console.log(res);
                })
            },

            saveAccount() {
                if (this.AccountItem.oldPwd == "" || this.AccountItem.oldPwd == null) {
                    //更新邮箱
                    let user = {
                        user: {
                            email: this.AccountItem.newEmail,
                            passwd: null,
                            username:this.userInfo.username,
                        },
                        repwd: null,
                    };
                    Api.updateUserAccount(this.$store.state.token, user).then(res => {
                        console.log(res.data);
                        if(res.data.code == 200){
                            this.$Message.success("修改成功！")
                        }else{
                            this.$Message.error(res.data.message);
                        }
                    }).catch(res => {
                        console.log(res);
                    })
                } else if (!this.checkPwd(this.AccountItem.newPwd) || !this.checkPwd(this.AccountItem.confirmNewPwd) ||
                    this.AccountItem.newPwd != this.AccountItem.confirmNewPwd) {
                    this.$Message.error("信息有误！");
                }else{
                    //更新密码和邮箱
                    let user = {
                        user: {
                            email: this.AccountItem.newEmail,
                            passwd: hex_md5(encodeURIComponent(this.AccountItem.oldPwd + 'onlinejudge')),
                            username:this.userInfo.username,
                        },
                        repwd: hex_md5(encodeURIComponent(this.AccountItem.newPwd + 'onlinejudge')),
                    };
                    Api.updateUserAccount(this.$store.state.token, user).then(res => {
                        console.log(res.data);
                        if(res.data.code == 200){
                            this.$Message.success("修改成功！")
                        }else{
                            this.$Message.error(res.data.message);
                        }
                    }).catch(res => {
                        console.log(res);
                    })
                }
            },
            checkPwd(val) {
                if (val.length <= 0 || val.length > 16) {
                    return false;
                } else if (val.indexOf(666) != -1) {
                    return false;
                }else{
                    return true;
                }
            },
            //更新头像成功回调
            updateHeadSuccess(response, file, fileList) {
                this.ProfileItem.header_img = response.data.url;
                this.userInfo.head_img = this.ProfileItem.header_img;
                Api.updateUserHeaderImage(this.userInfo.head_img, this.$store.state.token).then(res => {
                    if (res.data.code == 200) {
                        this.$Message.success("update success!");
                    } else {
                        this.$Message.error("update failed!");
                    }
                }).catch(res => {
                    console.log(res);
                });
            },

            getInfo() {
                Api.findUserInfoByToken(this.$store.state.token).then(res => {
                    this.userInfo = res.data.data.user;
                    this.updateInfo();
                }).catch(res => {
                    console.log(res);
                });
            },

            updateInfo() {
                this.ProfileItem = {
                    nickname: this.userInfo.nickname,
                    school: this.userInfo.school,
                    github_url: this.userInfo.github_url,
                    blog_url: this.userInfo.blog_url,
                    own_words: this.userInfo.own_words,
                };
                this.AccountItem = {
                    oldPwd: "",
                    newPwd: "",
                    confirmNewPwd: "",
                    currentPwd: "",
                    newEmail: this.userInfo.email,
                };
            }
        }
    }
</script>

<style scoped>
    label.form-title {
        margin: 0 0 0 0px;
        font-size: 25px;
    }

    div.form-container {
        margin-top: 15px;
    }
</style>