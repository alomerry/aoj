<template>
    <div>
        <Modal v-model="modal_falg" width="900" @on-visible-change="modal_show_change" :mask-closable="false">
            <div slot="header">
                <span style="font-size: 20px;font-weight: 400;">Create Announcement</span>
            </div>
            <div>
                <div class="anno-item">
                    <span class="item-require-red">*</span> Title
                    <Input size="large" style="margin-top: 15px" v-model="formAnno.title"></Input>
                </div>
                <div class="anno-item">
                    <div style="margin-bottom: 15px">
                        <span class="item-require-red">*</span> Content
                    </div>
                    <Simditor v-model="formAnno.content">
                    </Simditor>
                </div>
                <div class="anno-item">
                    <span style="margin-right: 10px">
                        Status
                    </span>
                    <i-switch v-model="formAnno.defunct">
                        <span slot="open">️</span>
                        <span slot="close"></span>
                    </i-switch>
                </div>
            </div>
            <div slot="footer" style="height: 35px;">
                <Button @click="modal_falg = false">Close</Button>
                <Button type="primary" @click="createAnno">Save</Button>
            </div>
        </Modal>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Announcement</span>
            </div>
            <div slot="extra">
                <Button @click="getNews">Refresh</Button>
            </div>
            <Table :columns="anno_column" :data="anno_data" :loading="anno_loading_flag"></Table>
            <Button @click="modal_falg = true" style="margin-top: 15px" type="info">Create</Button>
            <Page :total="total" :page-size="per_page" show-sizer show-elevator @on-change="changePage" @on-page-size-change="changePageSize" style="float: right;margin-top: 20px"/>
        </Card>
    </div>
</template>

<script>
    import Simditor from '../../components/Simditor'
    import Api from "../../components/api"

    export default {
        name: "announcement",
        data() {
            return {
                method: this.$route.params.contest_id === null ? null : "contest",

                page: 1,
                per_page: 10,
                total: 10,

                modal_falg: false,
                anno_loading_flag: false,
                anno_column: [
                    {
                        title: "",
                        align: 'center',
                        width: 50,
                        type: 'index',
                    },
                    {
                        title: "Title",
                        key: 'news.title',
                        align: 'center',
                        render: (h, params) => {
                            return h("span", {}, params.row.news.title);
                        }
                    },
                    {
                        title: "Create Time",
                        sortable: true,
                        key: "create_at",
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.news.create_at,
                                    type: 'datetime'
                                },
                            })
                        }
                    },
                    {
                        title: "Last Update Time",
                        key: "update_time",
                        sortable: true,
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.news.update_time,
                                    type: 'datetime'
                                },
                            })
                        }
                    },
                    {
                        title: "Anthor",
                        key: "user.username",
                        align: 'center',
                        render: (h, params) => {
                            return h("span", {}, params.row.user.username);
                        }
                    },
                    {
                        title: "Visable",
                        key: "news.defunct",
                        render: (h, params) => {
                            return h("i-switch", {
                                props: {
                                    value: params.row.news.defunct === "1" ? true : false,
                                    disabled: this.$store.getters.isLevel("Announcement_manager") ? false : (params.row.user.user_id) === this.$store.getters.localUserId ? false : true,
                                },
                                on: {
                                    "on-change": (state) => {
                                        // console.log(this);
                                        let val = "";
                                        if (state) {
                                            val = "1";
                                        } else {
                                            val = "0";
                                        }
                                        let old = params.row.news.defunct;
                                        Api.disableNews(params.row.news.news_id, val, this.$store.state.token).then(res => {
                                            let result = res.data;
                                            if (result.code == 200) {
                                                this.$Message.success("Change Successed!");
                                                params.row.news.defunct = state;
                                            } else {
                                                params.row.news.defunct = old;
                                                this.$Message.error("Change Failed!");
                                            }
                                        }).catch(res => {
                                            console.log(res);
                                        });
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: "Option",
                        align: 'center',
                        render: (h, params) => {
                            return h("div", {}, [
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "编辑"
                                    }
                                }, [h("Button", {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.modal_falg = true;
                                            this.formAnno = {
                                                news_id: params.row.news.news_id,//新闻Id
                                                title: params.row.news.title,//新闻标题
                                                content: params.row.news.content,//新闻内容
                                                defunct: params.row.news.defunct == 1 ? true : false,//公开状态
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
                                }, [h("Button", {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    on: {
                                        click: () => {
                                            this.anno_loading_flag = true;
                                            this.$Modal.confirm({
                                                title: 'Confirm',
                                                content: '<p>Are you sure to delete ?</p>',
                                                loading: true,
                                                onOk: () => {
                                                    Api.deleteNews(params.row.news.news_id, this.$store.state.token).then(res => {
                                                        let result = res.data;
                                                        if (result.code === 200) {
                                                            this.$Message.success("delete successed!");
                                                            this.updateDatas(params.row.news.news_id, null);
                                                        } else {
                                                            this.$Message.error(result.message);
                                                        }
                                                    }).catch(res => {
                                                        console.log(res);
                                                    });
                                                    this.$Modal.remove();
                                                    this.anno_loading_flag = false;
                                                },
                                                onCancel: () => {
                                                    this.$Modal.remove();
                                                    this.anno_loading_flag = false;
                                                },
                                            });
                                        }
                                    }
                                }, [h('Icon', {
                                        props: {
                                            type: "md-trash",
                                            size: 17,
                                            color: "#e1a331"
                                        }
                                    }
                                )])]),
                            ])
                        }
                    }
                ],
                anno_data: [],
                formAnno: {
                    news_id: null,//新闻Id
                    user_id: null,//用户Id
                    title: null,//新闻标题
                    content: null,//新闻内容
                    update_time: null,//更新时间
                    create_at: null,//创造时间
                    contest_id: null,//所属竞赛号
                    defunct: null,//公开状态
                },
                contest_id: this.$route.params.contest_id,
            }
        },
        components: {
            Simditor,
        },
        mounted() {
            this.getNews();
        },
        methods: {

            changePageSize(pageSize) {
                this.per_page = pageSize;
                this.getNews();
            },
            changePage(page) {
                this.page = page;
                this.getNews();
            },

            createAnno() {
                if (this.formAnno.news_id == null) {//create
                    let news = {
                        title: this.formAnno.title,//新闻标题
                        content: this.formAnno.content,//新闻内容
                        defunct: this.formAnno.defunct ? "1" : "0",//公开状态
                    };
                    if (this.contest_id != null) {
                        news.contest_id = this.contest_id;
                    }
                    Api.createNews(news, this.$store.state.token).then(res => {
                        let result = res.data;
                        if (result.code === 200) {
                            this.getNews();
                            this.$Message.success("添加成功");
                        } else {
                            this.$Message.error(result.message);
                        }
                        this.modal_falg = false;
                    }).catch(res => {
                        console.log(res);
                        this.modal_falg = false;
                    });
                } else {//update
                    let news = {
                        news_id: this.formAnno.news_id,
                        title: this.formAnno.title,//新闻标题
                        content: this.formAnno.content,//新闻内容
                        defunct: this.formAnno.defunct ? "1" : "0",//公开状态
                    };
                    Api.updateNews(news, this.$store.state.token).then(res => {
                        let result = res.data;
                        if (result.code === 200) {
                            this.getNews();
                            this.$Message.success("修改成功");
                        } else {
                            this.$Message.error(result.message);
                        }
                        this.modal_falg = false;
                    }).catch(res => {
                        console.log(res);
                        this.modal_falg = false;
                    });
                }

            },
            modal_show_change(status) {
                if (!status) {
                    this.formAnno = {
                        news_id: null,//新闻Id
                        user_id: null,//用户Id
                        title: null,//新闻标题
                        content: null,//新闻内容
                        update_time: null,//更新时间
                        create_at: null,//创造时间
                        contest_id: null,//所属竞赛号
                        defunct: false,//公开状态
                    }
                }
            },
            getNews() {
                this.anno_loading_flag = true;
                if (this.contest_id != null) {
                    //查询指定竞赛的公告
                    Api.findContestNews(this.contest_id, this.page, this.per_page, this.$store.state.token).then(res => {
                        let result = res.data;
                        console.log(result);
                        if (result.code === 200) {
                            this.anno_data = result.data.newsLink;
                            this.total = result.data.total;
                            this.addStartAndEndTimeToAnnoData();
                        } else {
                            this.$Message.error(result.message);
                        }
                        this.anno_loading_flag = false;
                    }).catch(res => {
                        console.log(res);
                        this.anno_loading_flag = false;
                    });
                } else {
                    Api.findNews(this.page, this.per_page, this.$store.state.token).then(res => {
                        /*this.anno_data = [
                            {
                                news: {
                                    news_id: 0,//新闻Id
                                    title: "dsafdas",//新闻标题
                                    content: "asdfsd",//新闻内容
                                    update_time: "2016-10-03 07:08:16",//更新时间
                                    create_at: "2016-10-03 07:08:16",//创造时间
                                    contest_id: 123,//所属竞赛号
                                    defunct: 0,//公开状态
                                },
                                user: {
                                    username: "ad",
                                    nickname: "ad",
                                }
                            }
                        ];*/
                        let result = res.data;
                        // console.log(result);
                        if (result.code === 200) {
                            this.anno_data = result.data.newsLink;
                            this.total = result.data.total;
                            this.addStartAndEndTimeToAnnoData();
                        } else {
                            this.$Message.error(result.message);
                        }
                        this.anno_loading_flag = false;
                    }).catch(res => {
                        console.log(res);
                        this.anno_loading_flag = false;
                    });
                }
            },
            //更新表格
            updateDatas(news_id, news) {
                if (news != null) {
                    console.log("添加成功");
                    this.anno_data.splice(0, 1, news);
                } else {
                    let unUpdatedItemIndex = this.anno_data.findIndex(function (currentItem) {
                        return currentItem.news.news_id === news_id;
                    });
                    console.log("更新序号：" + unUpdatedItemIndex);
                    this.anno_data.splice(unUpdatedItemIndex, 1);
                }
            },
            addStartAndEndTimeToAnnoData() {
                let that = this;
                this.anno_data.forEach(function (current, index) {
                    that.anno_data[index].create_at = that.anno_data[index].news.create_at
                    that.anno_data[index].update_time = that.anno_data[index].news.update_time
                })
            }
        },
    }
</script>

<style scoped>
    span.item-require-red {
        color: red;
    }
    
    div.anno-item {
        margin: 10px 0 20px 0;
        font-size: 15px;
        padding: 5px;
    }
</style>