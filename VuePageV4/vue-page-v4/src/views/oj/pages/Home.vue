<template>
    <div class="card-content">
        <Card>
            <transition name="fade" mode="out-in">
                <div v-if="news_show === 0" key="first">
                    <Spin size="large" fix v-if="loading"></Spin>
                    <div style="height: 35px;padding-top: 5px;text-align: left;font-size: 20px;margin: 0 0 25px 20px;">
                        <span slot="title" style="font-size: 25px;font-weight: 400;" v-text="header_info.title"></span>
                        <Button icon="md-refresh" style="margin-right: 10px;float: right" :loading="loading"
                                @click.native="refresh">Refresh
                        </Button>
                    </div>
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
                <div v-else key="second">
                    <div style="height: 35px;padding-top: 5px;text-align: left;font-size: 20px;margin: 0 0 25px 20px;">
                        <Button icon="md-arrow-round-back" style="margin-right: 10px;float: right" :loading="loading"
                                @click.native="back">Back
                        </Button>
                    </div>
                    <div style="font-size: 20px;font-weight: 700;margin-bottom: 10px" v-text="news_item.news.title"></div>
                    <div style="margin-bottom: 10px;">
                        发布日期:
                        <Time :time="news_item.news.update_time" type="datetime"/>
                        &nbsp;&nbsp;发布者:<span v-text="news_item.user.username"></span>
                    </div>
                    <div class="simditor">
                        <div class="simditor-markdown">
                            <div class="simditor-body new-content">
                                <div id="news-item-content" v-html="news_item.news.content"></div>
                            
                            </div>
                        </div>
                    </div>
                    <div style="text-align: right;">
                        最后编辑于
                        <Time :time="news_item.news.update_time" type="datetime"/>
                    </div>
                </div>
            </transition>
        
        </Card>
        <div style="margin: 10px;overflow: hidden" v-show="news_show === 0">
            <div style="float: right;">
                <Page :total="total" :page-size="per_page" show-sizer show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
            </div>
        </div>
    </div>
</template>
<script>
    import Api from "../components/api"
    import 'tar-simditor/styles/simditor.css'
    import 'tar-simditor-markdown/styles/simditor-markdown.css'

    export default {
        name: 'Home',
        data() {
            return {
                total: 1,
                page: 1,
                per_page: 10,
                loading: false,
                news_show: 0,//显示新闻列表
                header_info: {
                    button_info: '',
                    title: '',
                },
                data: [],
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
            back() {
                this.news_show = 0;
            },
            refresh() {
                this.loading = true;
                this.getNews();
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
                        this.total = result.data.total;
                    } else {
                        this.$Message.error(result.message);
                    }
                    this.loading = false;
                }).catch(res => {
                    console.log(res);
                    this.loading = false;
                });
            },
            changePageSize(pageSize) {
                this.per_page = pageSize;
                this.getNews();
            },
            changePage(page) {
                this.page = page;
                this.getNews();
            }
        },
        created() {
            this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
            this.header_info.title = this.news_show === 0 ? 'Annocement' : '';
            this.updateActiveClass(this.$route.path);
        },
    }
</script>
<style scoped>
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
    {
        opacity: 0;
    }
    
    .card-content {
        margin: 10px 20px 0 20px;
        padding-left: 30px;
        padding-right: 30px;
    }
    
    .news-item {
        font-size: 15px;
        font-weight: 700;
    }
    
    div.item-name {
        text-align: left;
        margin-left: 20px;
    }
    
    div.new-content {
        text-align: left;
        /*padding: 15px 50px 0 50px;*/
        background: #f3f3f3;
    }
</style>
