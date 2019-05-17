<template>
    <div style="margin: 0 150px 0 150px;text-align: left">
        <Card>
            <Layout>
                <Sider hide-trigger style="background: #fff;border-right: 1px gainsboro solid">
                    <div class="demo-avatar" style="margin:30px 0 60px 60px">
                        <Avatar size="large" style="width: 50px;height: 50px"/>
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
                                            action="//jsonplaceholder.typicode.com/posts/">
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
                                        <FormItem label="Old Password">
                                            <Input v-model="ProfileItem.nickname" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="New Password">
                                            <Input v-model="ProfileItem.school" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Confirm New Password">
                                            <Input v-model="ProfileItem.github_url" placeholder="Enter something..."></Input>
                                        </FormItem>
                                    
                                    </Col>
                                    <Col span="9" offset="3">
                                        <FormItem label="Blog">
                                            <Input v-model="ProfileItem.blog_url" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Personal Words">
                                            <Input v-model="ProfileItem.own_words" placeholder="Enter something..."></Input>
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
                                            <Input v-model="AccountItem.oldPwd" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="New Password">
                                            <Input v-model="AccountItem.newPwd" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Confirm New Password">
                                            <Input v-model="AccountItem.confirmNewPwd" placeholder="Enter something..."></Input>
                                        </FormItem>
                                    </div>
                                </Col>
                                <Col span="9" offset="3">
                                    <label class="form-title">Change Email</label>
                                    <div class="form-container">
                                        <FormItem label="Current Password">
                                            <Input v-model="AccountItem.currentPwd" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="Old Email">
                                            <Input v-model="AccountItem.oldEmail" placeholder="Enter something..."></Input>
                                        </FormItem>
                                        <FormItem label="New Email">
                                            <Input v-model="AccountItem.newEmail" placeholder="Enter something..."></Input>
                                        </FormItem>
                                    </div>
                                </Col>
                            </Row>
                        </Form>
                        <!--<Form v-else :model="SecurityItem"></Form>-->
                    </Content>
                </Layout>
            </Layout>
        </Card>
    </div>
</template>

<script>


    export default {
        name: "UserSetting",
        data() {
            return {
                useInfo: {},

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
                    oldPwd: "",
                    newPwd: "",
                    confirmNewPwd: "",
                    currentPwd: "",
                    oldEmail: null,
                    newEmail: "",
                },
                SecurityItem: {},
            }
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

            },
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