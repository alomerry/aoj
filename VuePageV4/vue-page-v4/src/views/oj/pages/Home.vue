<template>
    <div class="card-content">
        <Card>
            <p class="card-title" v-text="header_info.title"></p>
            <Button slot="extra" style="margin-right: 60px" :loading="loading" @click.native="refresh">{{header_info.button_info}}</Button>
            <div v-if="news_show === 0">
                <Row v-if="data == null">
                    <p>No Announcements</p>
                </Row>
                <div v-else>
                    <Row v-for="(value,index) in data">
                        <Col span="16">
                            <div class="news-item item-name">
                                <a @click="getNewsInfo(value.news.news_id)">{{value.news.title}}</a></div>
                        </Col>
                        <Col span="5">
                            <div class="news-item item-time">
                                <Time :time="value.news.update_time" type="datetime"/>
                            </div>
                        </Col>
                        <Col span="3">
                            <div class="news-item item-creator">{{value.user.username}}</div>
                        </Col>
                        <Divider/>
                    </Row>
                </div>
            </div>
            <div v-else>
                <div style="font-size: 20px;font-weight: 700;margin-bottom: 10px" v-text="news_item.news.title"></div>
                <div style="margin-bottom: 10px;">
                    发布日期:
                    <Time :time="news_item.news.update_time" type="datetime"/>
                    &nbsp;&nbsp;发布者:<span v-text="news_item.user.username"></span>
                </div>
                <div id="news-item-content" v-html="news_item.news.content"></div>
                <div style="text-align: right;">
                    最后编辑于
                    <Time :time="news_item.news.update_time" type="datetime"/>
                </div>
            </div>
        </Card>
        <div style="margin: 10px;overflow: hidden" v-show="news_show === 0">
            <div style="float: right;">
                <Page :total="totalPage" :current="page" :page-size="per_page" show-sizer show-elevator></Page>
            </div>
        </div>
    </div>
</template>
<script>
    import Api from "../components/api"

    export default {
        name: 'Home',
        data() {
            return {
                totalPage: 1,
                page: 1,
                per_page: 10,
                loading: false,
                news_show: 0,//显示新闻列表
                header_info: {
                    button_info: '',
                    title: '',
                },
                data: [
                    {
                        "news": {
                            "content": "<p>Mo Online Judge 已初步完成<br></p>",
                            "contest_id": null,
                            "create_at": 1556248318000,
                            "defunct": "1",
                            "news_id": 1,
                            "title": "Mo Online Judge 已初步完成",
                            "update_time": 1556252560000,
                            "user_id": 12
                        },
                        "user": {
                            "access_time": 1421407109000,
                            "disabled": false,
                            "email": "wu1jin2cheng3@live.cn",
                            "last_login": 1550710912000,
                            "nickname": "admin",
                            "passwd": "5cba13819e624f8dc0a991a7691f3f82",
                            "remark": null,
                            "school": "hyit",
                            "session_id": "B8D8076F6F313633BE47BD8D7F071A36",
                            "solved": 0,
                            "submit": 0,
                            "user_id": 12,
                            "username": "admin"
                        }
                    }
                ],
                news_item: {
                    "news": {
                        "content": "<p>Mo Online Judge 已初步完成<br></p>",
                        "contest_id": null,
                        "create_at": 1556248318000,
                        "defunct": "1",
                        "news_id": 1,
                        "title": "Mo Online Judge 已初步完成",
                        "update_time": 1556252560000,
                        "user_id": 12
                    },
                    "user": {
                        "access_time": 1421407109000,
                        "disabled": false,
                        "email": "wu1jin2cheng3@live.cn",
                        "last_login": 1550710912000,
                        "nickname": "admin",
                        "passwd": "5cba13819e624f8dc0a991a7691f3f82",
                        "remark": null,
                        "school": "hyit",
                        "session_id": "B8D8076F6F313633BE47BD8D7F071A36",
                        "solved": 0,
                        "submit": 0,
                        "user_id": 12,
                        "username": "admin"
                    }
                },
            }
        },
        mounted() {
            this.getNews();
        },
        methods: {
            refresh() {
                if (this.news_show === 1) {
                    this.news_show = 0;
                } else {
                    this.loading = true;
                }
                this.getNews();
                this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
                this.header_info.title = this.news_show === 0 ? 'Annocement' : '';
            },
            getNewsInfo(id) {
                var that = this;
                for (var item of this.data) {
                    if (item.news.news_id === id) {
                        that.news_item = item;
                        break;
                    }
                }
                this.news_show = 1;
                this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
                this.header_info.title = this.news_show === 0 ? 'Annocement' : this.news_item.name;
            },
            updateActiveClass(path) {
                switch (path) {
                    case "/status": {
                        this.$store.dispatch("updateActiveName", 'status');
                        break;
                    }
                    case "/home": {
                        this.$store.dispatch("updateActiveName", 'home');
                        break;
                    }
                    case "/problems": {
                        this.$store.dispatch("updateActiveName", 'problems');
                        break;
                    }
                }
            },
            getNews() {
                this.loading = true;
                Api.findNews(this.page, this.per_page).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 200) {
                        this.data = result.data.newsLink;
                    } else {
                        this.$Message.error(result.message);
                    }
                    this.loading = false;
                }).catch(res => {
                    console.log(res);
                    this.loading = false;
                });
            },
        },
        created() {
            this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
            this.header_info.title = this.news_show === 0 ? 'Annocement' : '';
            this.updateActiveClass(this.$route.path);
        }
    }
</script>
<style scoped>
    .card-content {
        margin: 10px 20px 0 20px;
        padding-left: 30px;
        padding-right: 30px;
    }
    
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 25px 20px;
        height: 35px;
    }
    
    .news-item {
        font-size: 15px;
        font-weight: 700;
    }
    
    div.item-name {
        text-align: left;
        margin-left: 20px;
    }
</style>
