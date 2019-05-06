<template>
    <div>
        <div class="avatar-container" style="margin-top: 170px">
            <Avatar class="avatar" icon="ios-person" size="large" shape="circle" src="https://qduoj.com/public/avatar/default.png"/>
        </div>
        <Card style="margin:130px 250px 0 250px;padding-top: 110px">
            <p class="info-uname" v-text="user == null?'':user.username"></p>
            <div style="padding: 0 150px 0 150px">
                <hr/>
                <Row style="margin-top: 20px">
                    <Col span="7">
                        <span class="info-detail-title">Solved</span><br><br>
                        <span class="info-detail-val" v-text="user == null?'':user.solved"></span>
                    </Col>
                    <Col span="1">
                        <Divider type="vertical"/>
                        <br>
                        <Divider type="vertical"/>
                        <br>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span="7">
                        <span class="info-detail-title">Submissions</span><br><br>
                        <span class="info-detail-val" v-text="user == null?'':user.submit"></span>
                    </Col>
                    <Col span="1">
                        <Divider type="vertical"/>
                        <br>
                        <Divider type="vertical"/>
                        <br>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span="7">
                        <span class="info-detail-title">school</span><br><br>
                        <span class="info-detail-val" v-text="user == null?'':user.school"></span>
                    </Col>
                </Row>
            </div>
            <br><br><br><br><br><br><br><br>
        </Card>
    </div>
</template>

<script>
    import Api from "../../components/api"

    export default {
        name: "UserHome",
        data() {
            return {
                username: this.$route.query.username,
                user: null,
            }
        },
        methods: {
            getUser() {
                if (this.username == null) {
                    this.user = this.$store.state.user;
                } else {
                    Api.findUserInfoByUserName(this.username).then(res => {
                        let result = res.data;
                        if (result.code == 200) {
                            this.user = result.data.user;
                        } else {
                            this.$Message.error(result.message);
                        }
                    }).catch(res => {
                        console.log(res);
                    });
                }
            }
        },
        mounted() {
            // console.log(this.$route);
            this.getUser();
        }
    }
</script>

<style scoped>
    p.info-uname {
        margin-bottom: 20px;
        font-size: 30px;
        font-weight: 700;
    }
    
    span.info-detail-title {
        margin-bottom: 20px;
        font-weight: 600;
    }
    
    span.info-detail-val {
        font-size: 20px;
        font-weight: 900;
    }
    
    .avatar-container {
        position: absolute;
        left: 50%;
        transform: translate(-50%);
        z-index: 1;
        top: -90px;
    }
    
    .avatar {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        box-shadow: 0 1px 1px 0;
    }
</style>